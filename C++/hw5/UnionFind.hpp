#ifndef _UNION_FIND_HPP_
#define _UNION_FIND_HPP_

#include <map>
using namespace std;

template <typename T>
class UnionFind {


    public:
        map <T, T> parent;
        map <T, int> rank;
        list<T> vertices;
        UnionFind(list<T> vertices);
        T find(T a);
        void unite(T a, T b);
};

template <typename T>
    UnionFind<T>::UnionFind(list<T> vertices) {
        this->vertices = vertices;
        for(auto it : vertices) {
            rank[it] = 1;
            parent[it];
        }
    }

template <typename T>
    T UnionFind<T>::find(T a) {
        T init{};

        if(parent[a] == init)
            return a;

        return parent[a] = find(parent[a]);
    }

template <typename T>
    void UnionFind<T>::unite(T a, T b) {
        T s1 = find(a);
        T s2 = find(b);

        if(s1 != s2) {
            if(rank[s1] < rank[s2]) {
                parent[s1] = s2;
                rank[s2] += rank[s1];
            }
            else {
                parent[s2] = s1;
                rank[s1] += rank[s2];
            }
        }
    }

#endif
