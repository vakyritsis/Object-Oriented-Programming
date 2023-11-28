/* for this implementation we are using adjacency list
~ if the graph is deirected the list of each vertex is showing us the vertices 
we are pointing at, not all the connections
~ if the graph is undirected then the list of each vertex is showing us the conncetion
between the vertices
*/

#ifndef _GRAPH_HPP_ 
#define _GRAPH_HPP_

#include <list>
#include <iostream>
#include <iterator>
#include <algorithm>
#include <queue>
#include <bits/stdc++.h>
#include <limits.h>
#include "UnionFind.hpp"

using namespace std;

template<typename T>
struct Edge {
    T from;
    T to;
    int dist;
    Edge(T f, T t, int d): from(f), to(t), dist(d) {
    }
    bool operator<(const Edge<T>& e) const {
        return dist < e.dist;
    }
    bool operator>(const Edge<T>& e) const {
        return dist > e.dist;
    }
    template<typename U>
    friend std::ostream& operator<<(std::ostream& out, const Edge<U>& e);
};

template<typename T>
std::ostream& operator<<(std::ostream& out, const Edge<T>& e) {
  out << e.from << " -- " << e.to << " (" << e.dist << ")";
  return out;
}

template<typename T>
struct node {
     vector<pair<T, int> > edges;    
     T *value;
 
    bool operator==(const node& a) {
         if(a.value == value)
            return true;
         else 
             return false;
         
     }
};

template<typename T>
class Graph {
   
	public:
        list<struct node<T>> vertices; //may change this
        bool isDirected;
        list<Edge<T>> listOfEdges;
		Graph(bool isDirectedGraph = true) {
			if(isDirectedGraph == true) {
				isDirected = true;
			}
			else {
				isDirected = false;
			}
		}
    //~Graph();
    bool contains(const T& info) {
        for(auto it = vertices.begin(); it != vertices.end(); ++it) {
           if(*(it->value) == info)
               return true;
            
        }
        return false;
	}
    bool addVtx(const T& info) {
		if(contains(info) == true)
			return false;
		node<T> newNode;
        newNode.value = new T;
        *(newNode.value) = info;
		vertices.push_back(newNode);
	
		return true;
    }
    bool rmvVtx(const T& info) {
        for(auto it = vertices.begin(); it != vertices.end(); ++it) {
            if(*(it->value) == info) {
                vertices.erase(it);
                // need to delete all edges connected as well
                for(auto it = vertices.begin(); it != vertices.end(); ++it) { 
                    rmvEdg(*(it->value), info);
                }
                return true;
            }
        }
		return false;
	}

    bool compareVert(const pair<T, int> &a, const pair<T, int> &b) const {
        int index1 = 0, index2 = 0;
        int counter = 0;
        for(auto it = vertices.begin(); it != vertices.end(); ++it) {
        if(*(it->value) == a.first)
            index1 = counter;
        if(*(it->value) == b.first)
            index2 = counter;
        counter++;
        }
        return index1 < index2;
    }

    bool addEdg(const T& from, const T& to, int distance) { // need to check if the edge  already exists
        if(isDirected == true) { // psaxno ton kombo from sto vertices kai sthn lista toy bazw to kai distance

            for(auto it = vertices.begin(); it != vertices.end(); ++it) {
                if(*(it->value) == from) {
                    pair<T, int> pair1;
                    pair1 = make_pair(to, distance);
                    //check if the pair is already in the list
                    for(auto it2 = it->edges.begin(); it2 != it->edges.end(); ++it2) {
                        if(it2->first == pair1.first)
                            return false;
                    }
                    pair<T, int> p1, p2;
                    p1.first = from;
                    p2.first = to;
                    bool boolVar = compareVert(p1, p2);
                    if(boolVar == true) {
                        Edge<T> newEdge(from, to, distance);
                        listOfEdges.push_back(newEdge);
                        (it->edges).push_back(pair1);
                    
                    }
                    else{
                        Edge<T> newEdge(to, from, distance);
                        listOfEdges.push_back(newEdge);
                        (it->edges).push_back(pair1);
                    }
                }
            }
            return true;
        }
        else { // bazw kai stous duo thn syndesh
            for(auto it = vertices.begin(); it != vertices.end(); ++it) {
                if(*(it->value) == from) {
                    pair<T, int> pair1;
                    pair1 = make_pair(to, distance);
                    //check if the pair is already in the list
                    for(auto it2 = it->edges.begin(); it2 != it->edges.end(); ++it2) {
                        if(it2->first == pair1.first)
                            return false;
                    }
                    pair<T, int> p1, p2;
                    p1.first = from;
                    p2.first = to;
                    bool boolVar = compareVert(p1, p2);
                    if(boolVar == true) {
                        Edge<T> newEdge(from, to, distance);
                        listOfEdges.push_back(newEdge);
                        (it->edges).push_back(pair1);
                    
                    }
                    else{
                        Edge<T> newEdge(to, from, distance);
                        listOfEdges.push_back(newEdge);
                        (it->edges).push_back(pair1);
                    }
                }
            }

            for(auto it = vertices.begin(); it != vertices.end(); ++it) {
                if(*(it->value) == to) {
                    pair<T, int> pair1;
                    pair1 = make_pair(from, distance);
                    //check if the pair is already in the list
                    
                    (it->edges).push_back(pair1);
                }
            }
            return true;
        }
        return false;
    }
    bool rmvEdg(const T& from, const T& to) {
        if(isDirected == true) { // psaxno ton kombo from sto vertices kai sthn lista toy bazw to kai distance

            for(auto it = vertices.begin(); it != vertices.end(); ++it) {
                if(*(it->value) == from) {
                    pair<T, int> pair1;
                    int distance = 0;
                    pair1 = make_pair(to, distance);
                    //check if the pair is already in the list
                    for(auto it2 = it->edges.begin(); it2 != it->edges.end(); ++it2) {
                        if(it2->first == pair1.first) {
                            (it->edges).erase(it2);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        else { // bazw kai stous duo thn syndesh
            for(auto it = vertices.begin(); it != vertices.end(); ++it) {
                if(*(it->value) == from) {
                    pair<T, int> pair1;
                    int distance = 0;
                    pair1 = make_pair(to, distance);
                    //check if the pair is already in the list
                    for(auto it2 = it->edges.begin(); it2 != it->edges.end(); ++it2) {
                        if(it2->first == pair1.first) {
                            (it->edges).erase(it2);
                        }
                    }
                }
            }
            for(auto it = vertices.begin(); it != vertices.end(); ++it) {
                if(*(it->value) == to) {
                    pair<T, int> pair1;
                    int distance = 0;
                    pair1 = make_pair(from, distance);
                    //check if the pair is already in the list
                    for(auto it2 = it->edges.begin(); it2 != it->edges.end(); ++it2) {
                        if(it2->first == pair1.first) {
                           (it->edges).erase(it2);
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    list<T> dfs(const T& info) const { // check if a need to put the element in reverse order
        list<T> retList; // the list to return 
        stack<T> stack;
        T element = info;
        map <T, bool> visited;
        vector <pair<T, int>> holder;
        
        for(auto iter = vertices.begin(); iter != vertices.end(); ++iter) {
            visited.insert(pair<T, bool>(*(iter->value), false));
        }

        stack.push(element);
        
        while(!stack.empty()) {
            element = stack.top();
            
            stack.pop();

            if(!visited[element]) {
                retList.push_back(element);
                visited[element] = true;
            }

            for(auto iter = vertices.begin(); iter != vertices.end(); ++iter) {
                if(*(iter->value) == element)
                    holder = iter->edges;
            }
            
            sort(holder.begin(), holder.end(), [this](pair<T, int> a, pair<T, int> b) { return compareVert(a, b); });
            for(auto it = holder.rbegin(); it != holder.rend(); it++) {
                if(!visited[it->first]) {
                    stack.push(it->first);
                }
            }
        }
        return retList;
    }
    list<T> bfs(const T& info) const {
        list<T> retList; // the list to return 
        queue<T> queue;
        T element = info;
        map <T, bool> visited;
        vector <pair<T, int>> holder;
        
        for(auto iter = vertices.begin(); iter != vertices.end(); ++iter) {
            visited.insert(pair<T, bool>(*(iter->value), false));
        }

        visited[element] = true;
        queue.push(element);
        
        while(!queue.empty()) {
            element = queue.front();
            retList.push_back(element);
            queue.pop();

            for(auto iter = vertices.begin(); iter != vertices.end(); ++iter) {
                if(*(iter->value) == element)
                    holder = iter->edges;
            }
            
            sort(holder.begin(), holder.end(), [this](pair<T, int> a, pair<T, int> b) { return compareVert(a, b); });
            for(auto it : holder) {
                if(!visited[it.first]) {
                    visited[it.first] = true;
                    queue.push(it.first);
                }
            }
        }
        return retList;
    }
    list<Edge<T>> mst() {
        list<Edge<T>> tree;// ret value
        if(isDirected == true)
            return tree;
    
        listOfEdges.sort();
        //make a list of T with the vertices
        list<T> listOfVertices;

        for(auto it = vertices.begin(); it != vertices.end(); it++) {
            listOfVertices.push_back(*(it->value));
        }
        UnionFind<T> unionFind(listOfVertices);
        //just need to change the vertex in insertion order;
        for(auto it :listOfEdges) {
            if(unionFind.find(it.from) != unionFind.find(it.to)) {
                unionFind.unite(it.from, it.to);
                tree.push_back(it);
            }
        }
        return tree;
    }
    
    void print2DotFile(const char *filename) const;
    T minimunDistance(map<T, int> dist, map<T, bool> boolSet) {
        int min = INT_MAX;
        T min_index;

        for(auto i : dist) {
            if(boolSet[i.first] == false && dist[i.first] <= min)
                min = dist[i.first], min_index = i.first;
        }
        return min_index;
    }

    list<T> returnSolution(map<T, T> parent, T j, T from) {
        list<T> retList;
        T initValue {};

        while(parent[j] != initValue) {
            retList.push_back(j);
            j = parent[j];
        }

        retList.push_back(j);

        if(j != from) // when there is no path between the two nodes
            return {};
        return retList;
    }

    list<T> dijkstra(const T& from, const T& to) {
        list<T> retList;
        int V = vertices.size();
        map<T, int> dist;
        map<T, bool> boolSet;
        map<T, T> parent;
        vector <pair<T, int>> holder;
        
        //init the sets
        for(auto iter = vertices.begin(); iter != vertices.end(); ++iter) {
            dist.insert(pair<T, int>(*(iter->value), INT_MAX));
            boolSet.insert(pair<T, bool>(*(iter->value), false));
            parent[*(iter->value)]; //init this set
        }

        dist[from] = 0;

        for(int count = 0; count < V - 1; count++) { //iterate to all vertices and find shortest path
            T u = minimunDistance(dist, boolSet);
            boolSet[u] = true;

            for(auto iter = vertices.begin(); iter != vertices.end(); ++iter) {
                if(*(iter->value) == u)
                    holder = iter->edges;
            }
            for(auto it = holder.begin(); it != holder.end(); it++) {
                
                if(!boolSet[it->first] && it->second != 0 && dist[u] + it->second < dist[it->first]) {
                    parent[it->first] = u;
                    dist[it->first] =  dist[u] + it->second;
                
                }
            }
        }
        return returnSolution(parent, to, from);
        
    }
  };


#endif
