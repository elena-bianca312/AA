#include <fstream>
using namespace std;
#ifndef HEAP_H
#define HEAP_H
// Prototype of a utility function to swap two integers 
void swap(int *x, int *y); 
  
// A class for Min Heap 
class MinHeap 
{ 
    int *harr; // pointer to array of elements in heap 
    int capacity; // maximum possible size of min heap 
    int size; // Current number of elements in min heap 
public: 
    // Constructor 
    MinHeap(int capacity);

    // to heapify a subtree with the root at given index 
    void MinHeapify(int i); 
  
    int parent(int i) { return (i-1)/2; } 
  
    // to get index of left child of node at index i 
    int left(int i) { return (2*i + 1); } 
  
    // to get index of right child of node at index i 
    int right(int i) { return (2*i + 2); } 
  
    // to extract the root which is the minimum element 
    int extractMin();
  
    // Returns the minimum key (key at root) from min heap 
    int getMin() { return harr[0]; } 
  
    // Inserts a new key 'k' 
    void insertKey(int k);

    // Prints the heap
    void printHeap(ofstream& out);
}; 
  
#endif