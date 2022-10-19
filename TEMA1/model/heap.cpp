#include <iostream>
#include <fstream>
#include <cstdlib>
#include <ctime>
#include <bits/stdc++.h>
#include<climits> 
using namespace std;
#include "heap.h"
// Constructor: Builds a heap from a given array a[] of given size 
MinHeap::MinHeap(int cap) 
{ 
    size = 0; 
    capacity = cap; 
    harr = new int[cap]; 
} 
  
// Inserts a new key 'k' 
void MinHeap::insertKey(int k) 
{ 
    if (size == capacity) 
    { 
        cout << "\nOverflow: Could not insertKey\n"; 
        return; 
    } 
  
    // First insert the new key at the end 
    size++; 
    int i = size - 1; 
    harr[i] = k; 
  
    // Fix the min heap property if it is violated 
    while (i != 0 && harr[parent(i)] > harr[i]) 
    { 
       swap(&harr[i], &harr[parent(i)]); 
       i = parent(i); 
    } 
} 
  
// Method to remove minimum element (or root) from min heap 
int MinHeap::extractMin() 
{ 
    if (size <= 0) 
        return INT_MAX; 
    if (size == 1) 
    { 
        size--; 
        return harr[0]; 
    } 
  
    // Store the minimum value, and remove it from heap 
    int root = harr[0]; 
    harr[0] = harr[size-1]; 
    size--; 
    MinHeapify(0); 
  
    return root; 
} 
  

// A recursive method to heapify a subtree with the root at given index 
// This method assumes that the subtrees are already heapified 
void MinHeap::MinHeapify(int i) 
{ 
    int l = left(i); 
    int r = right(i); 
    int smal = i; 
    if (l < size && harr[l] < harr[i]) 
        smal = l; 
    if (r < size && harr[r] < harr[smal]) 
        smal = r; 
    if (smal != i) 
    { 
        swap(&harr[i], &harr[smal]); 
        MinHeapify(smal); 
    } 
} 
  
void MinHeap::printHeap(ofstream& out) {
    for (int i = 0; i < size - 1; i++) {
        out << harr[i] << ", ";
    }
    out << harr[size - 1] << "\n\n";
}

// A utility function to swap two elements 
void swap(int *x, int *y) 
{ 
    int temp = *x; 
    *x = *y; 
    *y = temp; 
}