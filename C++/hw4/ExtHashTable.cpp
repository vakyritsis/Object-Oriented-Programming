#include <iostream>
#include <cstdlib>
#include <string.h>

#include "ExtHashTable.hpp"

ExtHashTable::ExtHashTable( double upper_bound_ratio, double lower_bound_ratio, int size) : HashTable(size){
	this->upper_bound_ratio = upper_bound_ratio;
	this->lower_bound_ratio = lower_bound_ratio;
}

ExtHashTable::ExtHashTable(const ExtHashTable &t) : HashTable(t) {
	this->upper_bound_ratio = t.upper_bound_ratio;
	this->lower_bound_ratio = t.lower_bound_ratio;
}

void ExtHashTable::rehash() {

	if(size == 0) {
        return;
    }
	if(((double)size/capacity) > upper_bound_ratio){
		actual_rehash(capacity*2);

	}
	else if(((double)size/capacity) < lower_bound_ratio){
		actual_rehash(capacity/2);
	}

}

void ExtHashTable::actual_rehash(int new_capacity) {
	ExtHashTable ext(upper_bound_ratio, lower_bound_ratio, new_capacity);
	
	for(int i = 0; i < capacity; i++) {
		if(!isAvailable(i)) {
			ext.HashTable::add(*(table[i]));
		}
	}

	for(int i = 0; i < capacity; i++) {
		if(!isAvailable(i))
			delete table[i];
	}

	delete []table;

	table = new string*[new_capacity+1];
	for(int i = 0; i < new_capacity; i++) {
		table[i] = NULL;
	}

	capacity = new_capacity;

	for(int i = 0; i < new_capacity; i++) {
		if(!ext.isAvailable(i)) {
			table[i] = new string(*ext.table[i]);
		}
	}

	cout << "--> Size: " << this->size << ", New capacity: " << this->capacity << endl;
}


bool ExtHashTable::add(const string &str) {
	return add(str.c_str());
}

bool ExtHashTable::add(const char *s) {
	 

	if(!HashTable::contains(s)){
		rehash();
		this->HashTable::add(s);
		return true;
	}
	return false;	
}

bool ExtHashTable::remove(const string &str) {
	return remove(str.c_str());
}

bool ExtHashTable::remove(const char *s) {

	if(this->HashTable::remove(s)){
		rehash();
		return true;
	}
	return false;
}

ExtHashTable & ExtHashTable::operator=(const ExtHashTable &t) {

	if(table != NULL){
		for(int i=0; i<capacity; i++){
    	    if(!isAvailable(i))
                delete table[i];
        }

		delete [] table;
	}

	this->upper_bound_ratio = t.upper_bound_ratio;
	this->lower_bound_ratio = t.lower_bound_ratio;
	this->size = t.getSize();
	this->capacity = t.getCapacity();
	this->table = new string*[t.capacity + 1];

	for(int i=0; i < capacity; i++){
		        if(!t.isAvailable(i))
            this->table[i] = new string(*(t.table[i]));
        else
            table[i] = t.table[i];
	}

	

	return *this;
}

ExtHashTable ExtHashTable::operator+(const string &str) const{
	ExtHashTable newExtHT(*this);

	newExtHT.add(str);

	return newExtHT;
}

ExtHashTable ExtHashTable::operator+(const char* s) const{
	ExtHashTable newExtHT(*this);

	newExtHT.add(s);

	return newExtHT;
}

ExtHashTable ExtHashTable::operator-(const string &str) const{
	ExtHashTable newExtHT(*this);

	newExtHT.remove(str);

	return newExtHT;
}

ExtHashTable ExtHashTable::operator-(const char *s) const{
	ExtHashTable newExtHT(*this);

	newExtHT.remove(s);

	return newExtHT;
}

ExtHashTable& ExtHashTable::operator += (const string &str) {
	this->add(str);

	return *this;
}

ExtHashTable& ExtHashTable::operator += (const char* s) {
	this->add(s);

	return *this;
}

ExtHashTable& ExtHashTable::operator -= (const string &str) {
	this->remove(str);

	return *this;
}

ExtHashTable& ExtHashTable::operator -= (const char *s) {
	this->remove(s);

	return *this;
}

ExtHashTable ExtHashTable::operator+(const ExtHashTable &t) const {
	ExtHashTable newHashTable (*this);

	for(int i=0; i < t.capacity; i++){
		if(!t.isAvailable(i)){
			newHashTable.add(*(t.table[i]));
		}
	}
	return newHashTable;
}

ExtHashTable& ExtHashTable::operator+=(const ExtHashTable &t) {

	for(int i=0; i<t.capacity; i++){
		if(!t.isAvailable(i)){
			this->add(*(t.table[i]));
		}
	}
	return *this;
}

