#include <iostream>

#ifndef _GRAPH_UI_
#define _GRAPH_UI_

template <typename T>
int graphUI() {
  
  string option, line;
  int distance;
  bool digraph = false;
  
  cin >> option;
  if(!option.compare("digraph"))
    digraph = true;
  Graph<T> g(digraph);
  
  while(true) {
    
    std::stringstream stream;
    cin >> option;
    
    if(!option.compare("av")) {
        getline(std::cin, line);
        stream << line;
        T vtx(stream);
        if(g.addVtx(vtx))
            cout << "av " << vtx << " OK\n";
        else
            cout << "av " << vtx << " NOK\n";
    }
    else if(!option.compare("rv")) {
        getline(std::cin, line);
        stream << line;
        T vtx(stream);
        if(g.rmvVtx(vtx))
            cout << "rv " << vtx << " OK\n";
        else
            cout << "rv " << vtx << " NOK\n";
    }
    else if(!option.compare("ae")) {
        getline(std::cin, line);
        stream << line;
        T vtx1(stream), vtx2(stream);
        stream >> distance;

        if(g.addEdg(vtx1, vtx2, distance))
            cout << "ae " << vtx1 << " " << vtx2 <<" OK\n";
        else
            cout << "ae " << vtx1 << " " << vtx2 <<" NOK\n";
    }
    else if(!option.compare("re")) {
        getline(std::cin, line);
        stream << line;
        T vtx1(stream), vtx2(stream);

        if(g.rmvEdg(vtx1, vtx2))
            cout << "re " << vtx1 << " " << vtx2 <<" OK\n";
        else
            cout << "re " << vtx1 << " " << vtx2 <<" NOK\n";
    }
    else if(!option.compare("dot")) {
    }
    else if(!option.compare("bfs")) {
        list<T> visited;
        getline(std::cin, line);
        stream << line;
        T vtx(stream);
        cout << "\n----- BFS Traversal -----\n";
        visited = g.bfs(vtx);
        for (auto iter : visited){
            cout<<iter;
            if(iter != visited.back())
                cout<< " -> ";
        }
        cout << "\n-------------------------\n";
    }
    else if(!option.compare("dfs")) {
        list<T> visited;
        getline(std::cin, line);
        stream << line;
        T vtx(stream);
        cout << "\n----- DFS Traversal -----\n";
        visited = g.dfs(vtx);
        for (auto iter : visited){
            cout<<iter;
            if(iter != visited.back())
                cout<< " -> ";
        }
        cout << "\n-------------------------\n";
    }
    else if(!option.compare("dijkstra")) {
        getline(std::cin, line);
        stream << line;
        T from(stream);
        T to(stream);

        list<T> list = g.dijkstra(from, to);
        
        cout << "Dijkstra (" << from << " - " << to <<"): ";

        for (auto iter = list.rbegin(); iter != list.rend(); ++iter){
            cout<<*iter;
            if(*iter != to)
                cout<< ", ";
        }
        cout<<"\n";
      
    }
    else if(!option.compare("mst")) {
        list<Edge<T>> mst;
        int sum = 0;
        mst = g.mst();
        cout << "\n--- Min Spanning Tree ---\n";
        for (auto iter = mst.begin(); iter != mst.end(); ++iter){
            cout<<*iter<<endl;
            sum += (iter->dist);
        }
        cout << "MST Cost: " << sum << endl;
    }
    else if(!option.compare("q")) {
      cerr << "bye bye...\n";
      return 0;
    }
    else if(!option.compare("#")) {
      string line;
      getline(cin,line);
      cerr << "Skipping line: " << line << endl;
    }
    else {
      cout << "INPUT ERROR\n";
      return -1;
    }
  }
  return -1;  
}

#endif
