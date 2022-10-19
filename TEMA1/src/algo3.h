#include <iostream>
#include <fstream>
#include <list>
#include <vector>

#ifndef PROJECT_ALGO3_H
#define PROJECT_ALGO3_H
#define INT_MAX 9999999

using namespace std;

class algo3 {

    int V;
    list<pair<int, int> > *adj;

public:
    explicit algo3(int V) {
        this->V = V;
        adj = new list<pair<int, int> >[V];
    }

    void addEdge(int u, int v, int w) {
        adj[u].emplace_back(v, w);
        adj[v].emplace_back(u, w);
    }

    void dijkstra2(int M, int src, ofstream &out, int W) {
        vector<pair<int, list<int>::iterator> > dist(M);
        for (int i = 0; i < M; i++)
            dist[i].first = INT_MAX;

        list<int> B[W * M + 1];

        B[0].push_back(src);
        dist[src].first = 0;
        int idx = 0;

        while (true) {
            while (B[idx].empty() && idx < W * M)
                idx++;

            if (idx == W * M)
                break;

            int u = B[idx].front();
            B[idx].pop_front();

            for (auto i = adj[u].begin(); i != adj[u].end(); ++i) {
                int v = (*i).first;
                int weight = (*i).second;

                int du = dist[u].first;
                int dv = dist[v].first;


                if (dv > du + weight) {
                    if (dv != INT_MAX)
                        B[dv].erase(dist[v].second);

                    dist[v].first = du + weight;
                    dv = dist[v].first;
                    B[dv].push_front(v);
                    dist[v].second = B[dv].begin();
                }
            }
        }
        for (int i = 0; i < M; i++) {
            if (dist[i].first == INT_MAX) {
                dist[i].first = 0;
            }
            if (i != src) {
                out << dist[i].first << " ";
            }
        }
    }
};

#endif
