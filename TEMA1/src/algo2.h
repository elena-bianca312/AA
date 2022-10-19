#include <iostream>
#include <fstream>

#ifndef PROJECT_ALGO2_H
#define PROJECT_ALGO2_H
#define INT_MAX 9999999

using namespace std;

class algo2 {

public:
    void bellmanFord(int **graph, int V, int E, int src, ofstream &out) {
        int dist[V];
        for (int i = 0; i < V; i++)
            dist[i] = INT_MAX;

        dist[src] = 0;
        for (int i = 0; i < V - 1; i++) {

            for (int j = 0; j < E; j++) {
                if (dist[graph[j][0]] + graph[j][2] <
                    dist[graph[j][1]])
                    dist[graph[j][1]] =
                            dist[graph[j][0]] + graph[j][2];
            }
        }

        int ok = 1;
        for (int i = 0; i < E; i++) {
            int x = graph[i][0];
            int y = graph[i][1];
            int weight = graph[i][2];
            if (dist[x] != INT_MAX &&
                dist[x] + weight < dist[y]) {
                out << "Ciclu negativ!" << endl;
                ok = 0;
                break;
            }

        }

        if (ok == 1)
            for (int i = 0; i < V; i++) {
                if (dist[i] == INT_MAX)
                    dist[i] = 0;
                if (i != src) {
                    out << dist[i] << " ";
                }
            }
    }
};

#endif
