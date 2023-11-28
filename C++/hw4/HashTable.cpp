#include "HashTable.hpp"
#include "HashTableException.hpp"
#include <stdio.h>
#include <string.h>

using namespace std;

unsigned long HashTable::getHashCode(const char *str) {
    unsigned long hash = 97;
    int c;

    while ((c = *(str++)) != '\0')
        hash = ((hash << 5) + hash) + c; /* hash * 33 + c */

    return hash;
} 
// for this 3 methods be careful for pos >= capacity
bool HashTable::isEmpty(int pos) const {
    if(pos >= capacity)
        return false;
    else if(table[pos] == NULL)
        return true;
    else 
        return false;
}

bool HashTable::isTomb(int pos) const {
    if(pos >= capacity)
        return false;
    else if(table[pos] == reinterpret_cast<string*>(0x9999))
        return true;
    else 
        return false;
}

bool HashTable::isAvailable(int pos) const {
    return table[pos] == NULL || table[pos] == reinterpret_cast<string*>(0x9999);
}

HashTable::HashTable(int capacity) {
    std::bad_alloc ex;
	table = new (nothrow) string*[capacity + 1];
    int i;

	if(capacity < 0 || table == NULL){
		throw ex;
	}

	for(i = 0; i < capacity; i++) {
        table[i] = NULL;

	}


	this->size = 0;
	this->capacity = capacity;
}
// need to alloc new string for the copy constructor
// need to check in for loop if the ht.table[i] != null or 0x9999 and create
// new string 
HashTable::HashTable(const HashTable &ht) {
    std::bad_alloc ex;
    int i;
    size = ht.size;
	capacity = ht.capacity;

	table = new (nothrow) string*[capacity + 1];

	if(table == NULL){
		throw ex;
	}

    for(i = 0; i < capacity; i++) {
        if(ht.table[i] != NULL && ht.table[i] != reinterpret_cast<string*>(0x9999)) {
        
            table[i] = new string(*ht.table[i]);

        }
        else if(ht.table[i] == NULL)
            table[i] = NULL;
        else if(ht.table[i] == reinterpret_cast<string*>(0x9999))
            table[i] = reinterpret_cast<string*>(0x9999);
    }
}

HashTable::~HashTable() {

    for(int i = 0; i < capacity; i++) {
        if(table[i] != NULL && table[i] != reinterpret_cast<string*>(0x9999)) {
            delete (table[i]);
        }
    }
    if(table != NULL)
	    delete [] table;
}

int HashTable::getSize() const { 
    return size; 
}

int HashTable::getCapacity() const {
    return capacity; 
}

bool HashTable::contains(const string &s) const {
    return contains(s.c_str());
}

bool HashTable::contains(const char *s) const {
    int startPos = getHashCode(s) % capacity;
    int i = startPos;
    
    do {
        if(table[i] != NULL && table[i] != reinterpret_cast<string*>(0x9999) && (*table[i]).compare(s) == 0)
            return true;

        if(isEmpty(i))
            return false;

        i = (i + 1) % capacity;
    }
    while(i != startPos);
    
    return false;
}

string HashTable::print() const {
    string str;
    char buf[128];
    
    for(int i=0; i<capacity; i++) {
        if( !isAvailable(i) ) {
            sprintf(buf, "%2d. -%s-\n", i, (*table[i]).c_str());
            str.append(buf);
        }
    }

    sprintf(buf, " --- CAPACITY: %d, SIZE: %d ---\n", capacity, size);
    str.append(buf);
    return str;
    }


bool HashTable::add(const string &s) {
    return HashTable::add(s.c_str());
}

bool HashTable::add(const char *s) {

    if(strcmp(s, "") == 0) {
        return false;
    } 

    int startPos = getHashCode(s) % capacity;
    int i = startPos;

    do {
        if( table[i] != NULL && table[i] != reinterpret_cast<string*>(0x9999) && (*table[i]).compare(s) == 0)
            return false;

        if(isAvailable(i)) {
            string str(s);
            table[i] = new string(str);
            size++;
            return true;
        }
        i = (i + 1) % capacity;
        
    }
    while(i != startPos);
    throw HashTableException();
}

bool HashTable::remove(const string &s) {
    return HashTable::remove(s.c_str());
}

bool HashTable::remove(const char *s) {

    if(contains(s) == false)
        return false;

    int startPos = getHashCode(s) % capacity;

    //dont need two variables because the string is definatelly in the HashTable
    while(1) {     
        if(table[startPos] != NULL && table[startPos] != reinterpret_cast<string*>(0x9999) && (*table[startPos]).compare(s) == 0) 
            break;

        startPos = (startPos + 1) % capacity;

    }
    delete table[startPos];
    table[startPos] = reinterpret_cast<string*>(0x9999);
    size--;
    return true;
}

HashTable& HashTable::operator=(const HashTable &ht) {
    // delete old values
    for(int i = 0; i < capacity; i++) {
        if(table[i] != NULL && table[i] != reinterpret_cast<string*>(0x9999)) {
            delete (table[i]);
        }
    }
    if(table != NULL)
	    delete [] table;

    //assign new values
    size = ht.size;
    capacity = ht.capacity;
    
    std::bad_alloc ex;
    table = new(nothrow) string*[capacity + 1];
    if(table == NULL)
        throw ex;

    for(int i = 0; i < capacity; i++) {
        if(ht.table[i] != NULL && ht.table[i] != reinterpret_cast<string*>(0x9999)) {
        
            table[i] = new string(*ht.table[i]);

        }
        else if(ht.table[i] == NULL)
            table[i] = NULL;
        else if(ht.table[i] == reinterpret_cast<string*>(0x9999))
            table[i] = reinterpret_cast<string*>(0x9999);
    }

    return *this;
}

HashTable& HashTable::operator+=(const string &str) {
    this->add(str);

    return *this;
}

HashTable& HashTable::operator+=(const char*s) {
    this->add(s);

    return *this;
}

HashTable& HashTable::operator-=(const string &str) {
    this->remove(str);

    return *this;
}

HashTable& HashTable::operator-=(const char*s) {
    this->remove(s);

    return *this;
}

HashTable HashTable::operator + (const string& str) const {
    HashTable new_ht(*this);

    new_ht.add(str);

    return new_ht;
}

HashTable HashTable::operator + (const char* s) const {
    HashTable new_ht(*this);

    new_ht.add(s);

    return new_ht;
}

HashTable HashTable::operator - (const string& str) const {
    HashTable new_ht(*this);

    new_ht.remove(str);

    return new_ht;
}

HashTable HashTable::operator - (const char* s) const {
    HashTable new_ht(*this);

    new_ht.remove(s);

    return new_ht;
}


std::ostream& operator<<(std::ostream &os, HashTable &t) {
	return os.write(t.print().c_str(), t.print().length());
}

HashTable::Iterator HashTable::begin() const {
	return Iterator(this);
}

HashTable::Iterator HashTable::end() const {
	return Iterator(this, false);
}




