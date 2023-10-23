/**
 * Author: Sherali Ozodov
 * File name: PatientQueue.java
 * Course: CSC 210
 * Purpose:This program implements a priority queue using 
 * a binary minimum heap. The priority queue us specific to 
 * Patient objects with keep track of patients with specific
 * priority.
 * 
 */

import java.util.NoSuchElementException;

class Heap {
	/**
	 * In the Heap class, the queue's internal 
	 * heap array is created with the capacity of 10.But, 
	 * it doesn't contains any patients initially.The frontmost
	 * element is stored at index 1 in your array. 
	 */
	
	private Patient[] heap;
	private int size;
	
	/*
	 * The constructor initializes the array of Patients object 
	 * with the capacity of 10, and set size to 0.
	 */
	public Heap() {
		heap = new Patient[10];
		size = 0;
	}
	
	/**
	 * This method returns the parent node of the minimum heap.
	 */
	private int parent(int i) {return i/2;}
	/**
	 * This method returns the left child of the minimum heap.
	 */
	private int left(int i) {return 2*i;}
	/**
	 * This method returns the right child of the minimum heap.
	 */
	private int right(int i) {return 2*i + 1;}
	
	/**
	 * This method is used to compare one patient has 
	 * higher priority than the other one. First, it checks their priorities.
	 * If the priorities are equal, it checks their names.
	 */
	private boolean compareTo(Patient patient1, Patient parent2) {
		if (patient1.priority < parent2.priority) 
			return true;
		else if (patient1.priority == parent2.priority) {
			if (patient1.name.compareTo(parent2.name) < 0)
				return true;
		}
		return false;
	}

    /**
     * This method is used if there is no room in the queue’s internal array. 
     * Then, it resizes it to a larger array with twice the capacity.
     */
	private void double_size() {
        Patient[] new_heap = new Patient[heap.length * 2];
        for(int i = 1; i < heap.length; i++) {
        	new_heap[i] = heap[i];
        }
        heap = new_heap;
    }

	/**
	 * This method is used to change the priority of the priorityqueue. It is
	 * basically used to ”bubbles” the patient forward or backward in the queue.
	 */
	public void set_priority(Patient patient, int i){
	    heap[i] = patient;
	    bubbleUp(i);
	    bubbleDown(i);
	}

	/*
	 * This function is used to bubble up the heap, meaning that
	 * if the parent's priority is higher than its children, then it 
	 * swaps with the children. Then, it recurses. 
	 */
	private void bubbleUp(int i){
	    if(i > 1){
	        if (compareTo(heap[i], heap[parent(i)])){
	            Patient temp = heap[parent(i)];
	            heap[parent(i)] = heap[i];            
	            heap[i] = temp;
	            bubbleUp(parent(i));   
	        }
	    }
	}
	
	/**
	 *  The function is used to insert the specified patient into 
	 *  the priority queue. If their is no room in the queue, it calls
	 *  the double_size method to resizes it to a larger array 
	 *  with twice the capacity. Otherwise, it inserts the patient, 
	 *  bubbles up and increase the size of it. 
	 */
	public void add(Patient patient) {
	    if (size >= heap.length){
	        double_size();   
	    }   
	    heap[size+1] = patient;
	    bubbleUp(size+1);
	    size++;
	}
	
	/**
	 * This method removes the frontmost patient in the 
	 * queue. If it is empty, it throws and exception.
	 */
	public void remove() {
		   if (isEmpty())
			   throw new NoSuchElementException("Heap is empty, No element to delete");
		   heap[1] = heap[size];
		   size--;
		   bubbleDown(1);
		}
	
	/*
	 * This function is used to bubble down the heap, meaning that
	 * it chooses the child with the smaller key and swap with the root if needed.
	 * Then, the original last node becomes the root. 
	 */
	private void bubbleDown(int i) {
		if (left(i) <= size) {
			   // find higher priority child
			   int higherpriorityChild = left(i);
			   if (right(i) <= size && compareTo(heap[right(i)], heap[left(i)]))
			      higherpriorityChild = right(i);
			   // checks if we need to swap it with the root.
			   if (compareTo(heap[higherpriorityChild], heap[i])) {
		            Patient temp = heap[higherpriorityChild];
		            heap[higherpriorityChild] = heap[i];            
		            heap[i] = temp;
		            bubbleDown(higherpriorityChild);      
			   }
			}
		}
	
	/**
	 * It returns the size of heap array.
	 */
	int size() {
		return size;
	}
	
	/**
	 * It checks if the heap array is empty.
	 */
	boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * It checks if the heap array is full.
	 */
	boolean isFull() {
		return size == heap.length;
	}
	 
	/**
	 * It returns the patient at the specific index of heap.
	 */
	public Patient get(int i) {
		return heap[i];
	}  
}


public class PatientQueue {
	/*
	 * PatientQueue class implements the priority queue using heap.
	 */
	private Heap hp;
	private int size;
	
	/**
	 * The constructor initializes a new empty queue that 
	 * does not contain any patients. Initially the queue’s internal 
	 * heap array should have a capacity of 10 elements. The frontmost 
	 * element is stored at index 1.
	 */
	public PatientQueue() {
		hp = new Heap();
		size = 0;
	}
	
	/**
	 * This method adds the given person into the patient queue
	 * by taking Patient object directly. It calls the heap's add method
	 * created above. Then it increases the size by 1.
	 */
	public void enqueue(Patient patient) {
		hp.add(patient);
		size++;
	}
	
	/**
	 * This method does the same function but it takes the name and his/her priority
	 * as parameters, not Patient object directly. It calls the heap's add method
	 * created above. Then it increases the size by 1.
	 */
	public void enqueue(String name, int priority) {
		Patient patient = new Patient(name, priority);
		hp.add(patient);
		size++;
	}
	
	/**
	 * This method removes the frontmost patient in the 
	 * queue, and return their name as a string. It throws an 
	 * exception if the queue is empty.
	 */
	public String dequeue() {
		String return_string = hp.get(1).name;
		hp.remove();
		size--;
		return return_string;
		
	}
	
	/**
	 * This function returns the name of the frontmost 
	 * patient in the queue, without removing or altering the state 
	 * of the queue. It throws an exception if the queue is empty.
	 */
	public String peek() {
        if (isEmpty())
            throw new NoSuchElementException("The queue is empty. No element to peek.");
        return hp.get(1).name;
    }
	
	/**
	 * This function should return the integer priority of the 
	 * frontmost patient in the queue without removing it or altering 
	 * the state of the queue. It throws an exception if the 
	 * queue is empty.
	 */
	public int peekPriority() {
        if (isEmpty())
            throw new NoSuchElementException("The queue is empty. No element to peek.");
        return hp.get(1).priority;
    }
	
	/**
	 * This function is used to modify the priority of a given 
	 * existing patient in the queue. It loops through the queue and finds
	 * the patient whose priority needs to be changed if exists in the queue.
	 * It maintain the proper heap ordering after the priority change. It
	 * does ”bubble” the patient forward or backward in the queue. 
	 */
	public void changePriority(String name, int newPriority){
	    Patient patient = new Patient(name, newPriority);
	    for (int i = 1; i <= size(); i++){
	        if (hp.get(i).name.equals(name)){
	            hp.set_priority(patient, i);
	            break;
	        }
	    }
	}
	
	/**
	 * This method is used to get the patient at the 
	 * specific index of queue.
	 */
	Patient get(int i) {
		return hp.get(i);
	}
	
	/**
	 * This function is used to create a new queue, meaning that it does not have
	 * any patient any more. 
	 */
	void clear() {
		hp = new Heap();
		size = 0;
	}
	
	/**
	 * This functions prints out the queue in a 
	 * format name (priority).
	 */
	public String toString() {
		   String name_priority = "{";
		   for (int i = 1; i <= size; i++)
			   name_priority += (i == 1 ? "" : ", ") + hp.get(i);
		   return name_priority + "}";
	}
	
	/**
	 * It returns the size of the queue.
	 */
	int size() {
		return size;
	}
	
	/**
	 * It checks if the queue is empty.
	 */
	boolean isEmpty() {
		return size == 0;
	}
}
