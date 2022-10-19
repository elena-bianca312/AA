#include <iostream>
#include <fstream>
#include <cstdlib>
#include <ctime>
#include <bits/stdc++.h>
using namespace std;
#include "treap.h"


/* Function to left rotate given Treap

      r                       R
     / \     Left Rotate     / \
    L   R     -------->     r   Y
       / \                 / \
      X   Y               L   X
*/

void rotateLeft(TreapNode* &root)
{
	TreapNode* R = root->right;
	TreapNode* X = root->right->left;

	// rotate
	R->left = root;
	root->right = X;

	// set new root
	root = R;
}

/* Function to right rotate given Treap

         r                        L
        / \     Right Rotate     / \
      L    R     --------->     X   r
     / \                           / \
    X    Y                        Y   R
*/

void rotateRight(TreapNode* &root)
{
	TreapNode* L = root->left;
	TreapNode* Y = root->left->right;

	// rotate
	L->right = root;
	root->left = Y;

	// set new root
	root = L;
}

// Recursive function to insert a given key with a priority into Treap
// using a reference parameter
void insertNode(TreapNode* &root, int data)
{
	// base case
	if (root == nullptr)
	{
		root = new TreapNode(data);
		return;
	}

	// if given data is less than the root node, insert in the left subtree
	// else insert in the right subtree
	if (data < root->data)
	{
		insertNode(root->left, data);

		// rotate right if heap property is violated
		if (root->left != nullptr && root->left->priority > root->priority)
			rotateRight(root);
	}
	else
	{
		insertNode(root->right, data);

		// rotate left if heap property is violated
		if (root->right != nullptr && root->right->priority > root->priority)
			rotateLeft(root);
	}
}

// Recursive Function that returns the max value of the given Treap
TreapNode* getMin(TreapNode* root)
{
	// if key is not present in the subtree
	if (root == nullptr)
		return 0;

	// if max is found
	if (root->left == nullptr)
		return root;
	return getMin(root->left);
}

// Recursive function to delete a key from the given Treap
void deleteNode(TreapNode* &root, int key)
{
	// base case: key not found in tree
	if (root == nullptr)
		return;

	// if given key is less than the root node, recur for left subtree
	if (key < root->data)
		deleteNode(root->left, key);

	// if given key is more than the root node, recur for right subtree
	else if (key > root->data)
		deleteNode(root->right, key);

	// if key found
	else
	{
		// Case 1: node to be deleted has no children (it is a leaf node)
		if (root->left == nullptr && root->right == nullptr)
		{
			// deallocate the memory and update root to null
			delete root;
			root = nullptr;
		}

		// Case 2: node to be deleted has two children
		else if (root->left && root->right)
		{
			// if left child has less priority than right child
			if (root->left->priority < root->right->priority)
			{
				// call rotateLeft on root
				rotateLeft(root);

				// recursively delete the left child
				deleteNode(root->left, key);
			}
			else
			{
				// call rotateRight on root
				rotateRight(root);

				// recursively delete the right child
				deleteNode(root->right, key);
			}
		}

		// Case 3: node to be deleted has only one child
		else
		{
			// find child node
			TreapNode* child = (root->left)? root->left: root->right;
			TreapNode* curr = root;

			root = child;

			// deallocate the memory
			delete curr;
		}
	}
}

// A utility function to print tree 
void printTreap(TreapNode* root, ofstream& out) 
{ 
    if (root) 
    { 
        printTreap(root->left, out); 
        out << "data: "<< root->data << " | priority: "
            << root->priority; 
        if (root->left) 
            out << " | left child: " << root->left->data; 
        if (root->right) 
            out << " | right child: " << root->right->data; 
        out << endl; 
        printTreap(root->right, out); 
    } 
}

