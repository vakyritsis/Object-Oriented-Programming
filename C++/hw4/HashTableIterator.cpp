#include "HashTable.hpp"
#include "HashTableException.hpp"
#include <stdio.h>

using namespace std;

HashTable::Iterator::Iterator(const HashTable *t) {
	ht = t;

	int i = 0;
	while(i<t->capacity && t->isAvailable(i)){
		i++;
	}
    position = i;
	curr = &t->table[i];
}

HashTable::Iterator::Iterator(const HashTable *t, bool start) {
    ht = t;
    if(start == true) {
        int i=0;
        while(i<t->capacity && t->isAvailable(i)){
            i++;
        }
        position = i;
        curr = &t->table[i];
    }
    else {
        curr = &(t->table[t->capacity]);
		position = t->capacity;
    }
}

HashTable::Iterator::Iterator(const Iterator &it) {
	ht = it.ht;
    position = it.position;
	curr = it.curr;

}

HashTable::Iterator& HashTable::Iterator::operator=(const HashTable::Iterator &it) {
	ht = it.ht;
    position = it.position;
	curr = it.curr;

	return *this;
}

HashTable::Iterator HashTable::Iterator::operator++() {
	Iterator it (*this); 

	int i = position + 1;
	while(i<it.ht->capacity && it.ht->isAvailable(i)){
		i++;
	}
	curr = &(it.ht->table[i]);
	position = i;

	it = *this;
	return it;
}

HashTable::Iterator HashTable::Iterator::operator++(int a) {
	Iterator it (*this); 


	int i = position + 1;

	while(i<it.ht->capacity && it.ht->isAvailable(i)){
		i++;
	}
	position = i ;
	
	curr = &(it.ht->table[i]);
	

	return it;
	
}

bool HashTable::Iterator::operator==(const HashTable::Iterator &it) const {
	if(it.curr == curr && it.ht == ht && it.position == position)
		return true;
	else 
		return false;
}

bool HashTable::Iterator::operator!=(const HashTable::Iterator &it) const {
	if(it.curr != curr || it.ht != ht || it.position != position)
		return true;
	else 
		return false;
}

const string& HashTable::Iterator::operator*() {
	
	return *(ht->table[position]);
}

const string* HashTable::Iterator::operator->() {
	return ht->table[position];
}

int HashTable::Iterator::pos() const {
	return position;
}

