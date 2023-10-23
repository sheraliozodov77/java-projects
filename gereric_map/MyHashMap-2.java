/**
 * Author:  Sherali Ozodov
 * File name: MyHashMap.java
 * Course:  CSC 210
 * Purpose: This program is a generic implementation of a map 
 * using a hash table.
 * 
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * This class is aimed to create a node and 
 * different method to access node
 */
class Node < K,V > {
	
    public K key;
    public V value;
    public Node < K,V > next;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }
    
    public V getValue() {
        return value;
    }

    public V setValue(V newValue) {
        V old = value;
        value = newValue;
        return old;
    } 
}


/**
 * This class creates a hashtable with table size 8 and has one method which 
 * is used to access to linked lists
 * in arraylist.
 */
class HashTable<K, V>{
    final int TABLE_SIZE = 8;
    ArrayList<LinkedList<Node<K, V>>> arraylist = new ArrayList<>(TABLE_SIZE);

    /**
     * It returns the linked list at intended index.
     */
	public LinkedList<Node<K, V>> get(int index) {
		return arraylist.get(index);
	}    
}


public class MyHashMap < K, V > {
		private int size;
		HashTable<K, V> hashTable = new HashTable<>();
		ArrayList<LinkedList<Node<K, V>>> arraylist = hashTable.arraylist;
		
		
		/**
		 * It is a MyHashMap constructor.
		 */
	    public MyHashMap() {
	        for (int i = 0; i < hashTable.TABLE_SIZE; i++)
	        	arraylist.add(new LinkedList < Node < K, V >> ());
	    }

	    /**
	     * This is the method to hash.
	     */
	    private int hash(K key) {
	        int hashCode = key.hashCode();
	        int index = hashCode % hashTable.TABLE_SIZE;
	        return Math.abs(index);
	    }
	    /**
	     * Associates the specified value with the specified key in this map.
	     * @param key
	     * @param value
	     */
	    public V put(K key, V value) {
	        int index = hash(key);
		    LinkedList<Node<K, V>> list = hashTable.get(index);
            V old = null;
            // If the same key is passed in with a
            // different value, do the value update ‘in place’.
            if (containsKey​(key)) {
                for (Node<K, V> pair: list) {
                    if (pair.getKey().equals(key)) {
                        old = (V) pair.getValue();
                        pair.setValue(value);
                    }
                }
            } 
            // Otherwise, it puts the (key,value) pairs at the head of the linked list.
            	else {
                list.addFirst(new Node<>(key, value));
                size++;
            }
            return old;
	    }
	
	    /**
	     * Returns true if this map contains a mapping for the specified key.
	     * @param key - The key whose presence in this map is to be tested
	     */
	    
	    public boolean containsKey​(K key) {
			int index = hash(key);
		    LinkedList<Node<K, V>> list = hashTable.get(index);
	        
	        for (int i = 0; i < list.size(); i++) {
				Node<K, V> pair = list.get(i);
				if (pair.getKey().equals(key))
	                return true;
			}
	        return false;
		}
	
	    /**
	     * Returns true if this map maps one or more keys to the specified value.
	     * @param val value whose presence in this map is to be tested
	     */
	    public boolean containsValue​(V val) {
	        for (LinkedList<Node<K, V>> list: arraylist)
	        	for (Node<K, V> pair: list ) {
					if (pair.getValue().equals(val))
	                    return true;
				}
	        return false;
		}
	    
	    /**
	     * Returns the value to which the specified key is mapped, or null if 
	     * this map contains no mapping for the key.
	     * @param the key whose associated value is to be returned
	     */
	
	    public V get(K key){
	    	int index = hash(key);
	        LinkedList<Node<K,V>> list = hashTable.get(index);
            for(int j = 0; j < list.size(); j++){
                Node<K, V> node = list.get(j);
                if (node.getKey().equals(key)){
                    return node.getValue();
                }
            }
	        return null;
	    }
	    
	    /**
	     * Returns the number of key-value mappings in this map.
	     */
	    public int size() {
	    	return size;
	    }
	
	    /**
	     * Returns true if this map contains no key-value mappings.
	     */
	    public boolean isEmpty() {
	        return size() == 0;
	    }
	
	    /*
	     * Removes all of the mappings from this map.
	     */
	    public void clear() {
			for (LinkedList<Node<K, V>> list: arraylist)
	            list.clear();
	    }
	  
	    /**
	     * Returns a Set view of the keys contained in this map.
	     */
	    public Set<K> keySet() {
	        Set<K> set = new HashSet<>();
	        for (LinkedList<Node<K, V>> list: arraylist) {
	            for (Node<K, V> pair: list) {
	                set.add((K) pair.getKey());
	            }
	        }
	        return set;
	    }   
	    
	    /**
	     * Removes the mapping for the specified key from this map if present.
	     * @param key which needs to be removed
	     */
	    public V remove(K key){
	    	
	        int index = hash(key);
	        LinkedList<Node<K, V>> list = arraylist.get(index);
	        for (int i = 0; i < list.size(); i++) {
	            if (list.get(i).getKey() == key) {
	            	list.remove(list.get(i));
	            	size--;
	            }
	        }
			return null;
	    }
	    
	    
	    /**
	     * This method should output how many conflicts occur at each 
	     * array slot (or bucket) by printing the index number and list the 
	     * keys at that slot.
	     */
	    public void printTable() {
	        int conflicts = 0;
	        for (int i = 0; i < hashTable.TABLE_SIZE; i++) {
	        	// it loops through each linked list and if 
	        	// there is two or more key value mappings, it counts the collision.
	            var list = arraylist.get(i);
	            if (list.size() >= 1) {
		            System.out.print("Index " + i + ": (" + (list.size()-1) + " conflicts), [");
		            conflicts += (list.size()-1);
	            } else  { 
	            	System.out.print("Index " + i + ": (" + (list.size()) + " conflicts), [");
	            }
	            for (Node < K, V > node: list) {
	                    System.out.print(node.key + ", ");
	            }
	            System.out.print("]");
	            System.out.println();
	        }	        
	        // at the end, it should also print out the number of conflicts occured.
	        System.out.println("Total # of conflicts: " + Integer.toString(conflicts));
	    }   
}

