#include <fstream>
using namespace std;
#ifndef TREAP_H
#define TREAP_H

// A Treap Node
struct TreapNode
{
	int data;
	int priority;
	TreapNode *left, *right;

	// Constructor
	TreapNode(int data)
	{
		this->data = data;
		this->priority = rand() % 100;
		this->left = this->right = nullptr;
	}
};


void rotateLeft(TreapNode* &root);

void rotateRight(TreapNode* &root);

void insertNode(TreapNode* &root, int data);

TreapNode* getMin(TreapNode* root);

void deleteNode(TreapNode* &root, int key);

void printTreap(TreapNode* root, ofstream& out);
#endif