#ifndef PROJECT_ALGO1_H
#define PROJECT_ALGO1_H

#include <fstream>
#include <iostream>

#define INT_MAX 9999999

using namespace std;

class algo1 {

    int minDistance(int V, int dist[], bool sptSet[]) {
        // Initialize min value
        int min = INT_MAX, min_index = 0;

        for (int v = 0; v < V; v++)
            if (!sptSet[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    void printSolution(int V, int dist[], ofstream &out, int src) {
        for (int i = 0; i < V; i++) {
            if (dist[i] == INT_MAX)
                dist[i] = 0;
            if (i != src) {
                out << dist[i] << " ";
            }
        }
    }

public:
    void dijkstra(int V, int **graph, int src, ofstream &out) {

        int dist[V];
        bool sptSet[V];
        for (int i = 0; i < V; i++) {
            dist[i] = INT_MAX;
            sptSet[i] = false;
        }

        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(V, dist, sptSet);
            sptSet[u] = true;
            for (int v = 0; v < V; v++)
                if (!sptSet[v] && graph[u][v] && dist[u] != INT_MAX
                    && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];
        }

        printSolution(V, dist, out, src);
    }
};

#endif
