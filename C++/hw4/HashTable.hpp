#include <iostream>

using namespace std;

#ifndef __HASHTABLE_H__
#define __HASHTABLE_H__

class HashTable {
  
    protected:
        int size;
        int capacity;
        string **table;
        static unsigned long getHashCode(const char *str);

        bool isEmpty(int pos) const;
        bool isTomb(int pos) const;
        bool isAvailable(int pos) const;
    
    public:  
        HashTable(int capacity=8);
        HashTable(const HashTable &ht);
        ~HashTable();
        int getSize() const;
        int getCapacity() const;
    
        bool contains(const string &s) const;
        bool contains(const char *s) const;
        string print() const;

        virtual bool add(const string &s);
        virtual bool add(const char *s);
        virtual bool remove(const string &s);
        virtual bool remove(const char *s);
    
        HashTable& operator = (const HashTable &ht);
  
        HashTable& operator += (const string& str);
        HashTable& operator += (const char* s);
        HashTable& operator -= (const string& str);
        HashTable& operator -= (const char* s);
        
        HashTable operator + (const string& str) const;
        HashTable operator + (const char* s) const;
        HashTable operator - (const string& str) const;
        HashTable operator - (const char* s) const;

        friend std::ostream& operator<<(std::ostream &stream, const HashTable &t);

    
    class Iterator {
    	string **curr;
    	const HashTable *ht;
    	int position;  
      
    	public:    
			Iterator(const HashTable *t);
			Iterator(const HashTable *t, bool start);
			Iterator(const Iterator &it);
			Iterator& operator=(const Iterator &it);
			Iterator operator++();
			Iterator operator++(int a);
			bool operator==(const Iterator &it) const ;
			bool operator!=(const Iterator &it) const;
			const string& operator*();
			const string* operator->();
			int pos() const;
    	};


    

    HashTable::Iterator begin() const;
    HashTable::Iterator end() const;

};

#endif
