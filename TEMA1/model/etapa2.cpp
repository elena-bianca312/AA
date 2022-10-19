#include <iostream>
#include <cstdlib>
#include <fstream>
#include <ctime>
#include "treap.h"
#include "heap.h"
#include <bits/stdc++.h>
#include <vector>
using namespace std;

void readNumbersFromFile(vector<int> &V, ifstream& ins) {
	int number;
	while (ins >> number) {
		V.push_back(number);
	}
}

int main(int argc, char *argv[])
{
	ifstream in;
	vector<int> keys;

	in.open(argv[1]);
	//cout << argv[1];

	//  keys
	readNumbersFromFile(keys, in);
	string name = argv[1];
	string st = name.replace(0, 2, "out");
	st.erase(name.size() - 2);
	st.append("out", 3);

	cout << st;
	ofstream out;
	out.open(st);

	int n = 1;
	int i, k;
	if (argc == 3) {
		n = atoi(argv[2]);
	}

	k = keys.size();
	if (n > k) {
		n = k;
	}

	// Construct a Treap and a MinHeap
	TreapNode* root = nullptr;
	MinHeap heap(k + 1);
	srand(time(nullptr));
	for (int key: keys) {
	    cout << key;
		insertNode(root, key);
		heap.insertKey(key);

	}

	out << "---------------------------------\n";
	out << "--------------TREAP--------------\n";
	out << "---------------------------------\n";
	out << "Constructed Treap:\n\n";
	printTreap(root, out);

	for (i = 0; i < n; i++) {
		out << "\nDeleting min (" << getMin(root)->data << ") node :\n\n";
		deleteNode(root, getMin(root)->data);
		printTreap(root, out);
	}


	out << "\n\n--------------------------------\n";
	out << "--------------HEAP--------------\n";
	out << "--------------------------------\n";

	// Construct a MinHeap
	out << "Constructed Heap:\n\n";
	heap.printHeap(out);

	for (i = 0; i < n; i++) {
		out << "\nDeleting min (" << heap.getMin() << ") node :\n\n";
		heap.extractMin();
		heap.printHeap(out);
	}

	in.close();
    out.close();

	return 0;
}