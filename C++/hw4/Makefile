CFLAGS=-Wall -g -std=c++11 -fsanitize=address

all: HashTable.o HashTableIterator.o ExtHashTable.o
	g++ ${CFLAGS} HashTable.o HashTableIterator.o ExtHashTable.o -o exe

HashTable.o: HashTable.cpp
	g++ ${CFLAGS} HashTable.cpp -c 

HashTableIterator.o: HashTableIterator.cpp
	g++ ${CFLAGS} HashTableIterator.cpp -c 

ExtHashTable.o: ExtHashTable.cpp
	g++ ${CFLAGS} ExtHashTable.cpp -c 

