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
#include <algorithm>
using namespace std;

template<typename T>
struct Edge {
  T from;
  T to;
  int dist;
  Edge(T f, T t, int d): from(f), to(t), dist(d) {
  }
  bool operator<(const Edge<T>& e) const;
  bool operator>(const Edge<T>& e) const;
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
	list<T> conncetions;
	int dist;
	T value;
};


template <typename T>
class Graph {
    list<struct node<T>> vertices; //may change this
    bool isDirected;
	public:
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
		return std::find(vertices.value.begin(), vertices.value.end(), info) != vertices.value.end();
	}
    bool addVtx(const T& info) {
		if(contains(info) == true)
			return false;
			
		vertices.value.push_back(info);
	
		return true;
    }
    bool rmvVtx(const T& info) {
		if(contains(info) == true) {
			vertices.value.remove(info);
			return true;
		}
		else 
			return false;
	}
    bool addEdg(const T& from, const T& to, int distance) {
      
      return true;
    }
    bool rmvEdg(const T& from, const T& to);
    list<T> dfs(const T& info) const;
    list<T> bfs(const T& info) const;
    list<Edge<T>> mst();
    
    void print2DotFile(const char *filename) const;
    list<T> dijkstra(const T& from, const T& to);
  };


#endif
