#include <iostream>
#include <cstdlib>
#include <fstream>
#include <ctime>
#include <chrono>
#include <ratio>
#include "algo1.h"
#include "algo2.h"
#include "algo3.h"
#include <vector>

using namespace std;
using namespace std::chrono;

void readNumbersFromFile(vector<int> &V, ifstream &ins) {
    int number;
    while (ins >> number) {
        V.push_back(number);
    }
}

int main(int argc, char *argv[]) {

    ifstream in;
    vector<int> index;
    (void)argc;

    //open input file
    in.open(argv[1]);

    //create output file
    readNumbersFromFile(index, in);
    string filename = argv[1];
    string test_type = argv[2];
    string st;
    if (test_type == "Dijkstra") {
        st = filename.replace(15, 2, "out");
    }
    if (test_type == "BellmanFord") {
        st = filename.replace(18, 2, "out");
    }
    if (test_type == "Dijkstra2") {
        st = filename.replace(16, 2, "out");
    }

    st.erase(filename.size() - 2);
    st.append("out", 3);

    ofstream out;
    out.open(st);

    auto start = high_resolution_clock::now();

    int N = index[0];
    int M = index[1];
    int src = index[2];
    int max = 0;
    int **graphDijkstra;
    graphDijkstra = (int **) malloc(N * sizeof(int *));
    for (int i = 0; i < N; i++) {
        graphDijkstra[i] = (int *) calloc(N, sizeof(int));
    }

    for (unsigned int k = 3; k < index.size(); k = k + 3) {
        graphDijkstra[index[k] - 1][index[k + 1] - 1] = index[k + 2];
        graphDijkstra[index[k + 1] - 1][index[k] - 1] = index[k + 2];
    }

    int **graph;
    graph = (int **) malloc(M * sizeof(int *));
    for (int i = 0; i < M; i++) {
        graph[i] = (int *) calloc(3, sizeof(int));
    }

    int l = 0;
    for (unsigned int k = 3; k < index.size(); k = k + 3) {
        graph[l][0] = index[k] - 1;
        graph[l][1] = index[k + 1] - 1;
        graph[l][2] = index[k + 2];
        if (index[k + 2] > max)
            max = index[k + 2];
        l++;
    }

    if (test_type == "Dijkstra") {
        algo1 x1;
        x1.dijkstra(N, graphDijkstra, src - 1, out);
    }
    if (test_type == "BellmanFord") {
        algo2 x2;
        x2.bellmanFord(graph, N, M, src - 1, out);
    }
    if (test_type == "Dijkstra2") {
        algo3 x3 = algo3(N);
        for (int i = 0; i < M; i++) {
            x3.addEdge(graph[i][0], graph[i][1], graph[i][2]);
        }
        x3.dijkstra2(N, src - 1, out, max);
    }

    auto stop = high_resolution_clock::now();
    auto duration = (float) duration_cast<microseconds>(stop - start).count();
    cout<<duration<<" microseconds";

    in.close();
    out.close();

    return 0;
}
