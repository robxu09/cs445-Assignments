package cs445.a1;

import java.util.Arrays;

public class Set<E> implements SetInterface<E>{

	/**
	Whenever the capacity is reached, the array must resize, 
	using the techniquesdiscussed in lecture 
	(i.e., you shouldneverthrowSetFullException).
	*/



	//stuff I need
	private E[] set;
	private int numOfEntries;
	private static final int DEFAULT_CAP = 20;
	private static final int MAX_CAP = 10000;
	private boolean initialized = false;
	//Constructors. three of them

	public Set(int capacity){
		checkCapacity(capacity);
		@SuppressWarnings("unchecked")
		E[] tempSet = (E[])new Object[capacity];
		set = tempSet;
		numOfEntries = 0;
		initialized = true;
	}

	//use set capacity but with default
	public Set(){
		this(DEFAULT_CAP);
		initialized = true;
	}

	/**
	For Set(E[] entries)
	Note  that  this  constructor  must  still  create
	its own backing array, andnotadopt the argument as a data member;  
	it must also skipall  duplicates  andnullvalues  in  the  provided  array,  
	to  satisfy  the  data  structure’s  usualassumptions.
	*/
	public Set(E[] entries){
		@SuppressWarnings("unchecked")
		E[] tempSet = (E[])new Object[entries.length];
		set = tempSet;
		initialized = true;
		for(int i=0;i<entries.length;i++){
            add(entries[i]);
			
		}
	}

	//Implement these
    public int getSize(){
    	checkInitialized();
    	return numOfEntries;
    }

    /**
     * Determines whether this set is empty.
     *
     * @return  true if this set is empty; false if not
     */
    public boolean isEmpty(){
    	if(getSize()==0){
    		return true;
    	}
    	return false;
    }

    /**
     * Adds a new entry to this set, avoiding duplicates.
     *
     * <p> If newEntry is not null, this set does not contain newEntry, and this
     * set has available capacity (if applicable), then add modifies the set so
     * that it contains newEntry. All other entries remain unmodified.
     * Duplicates are determined using the .equals() method.
     *
     * <p> If newEntry is null, then add throws NullPointerException without
     * modifying the set. If this set already contains newEntry, then add
     * returns false without modifying the set. If this set has a capacity
     * limit, and does not have available capacity, then add throws
     * SetFullException without modifying the set. If this set does not have a
     * capacity limit (i.e., if it resizes as needed), then it will never throw
     * SetFullException.
     *
     * @param newEntry  The object to be added as a new entry
     * @return  true if the addition is successful; false if the item already is
     * in this set
     * //@throws SetFullException  If this set has a fixed capacity and does not
     * have the capacity to store an additional entry
     * @throws NullPointerException  If newEntry is null
     */
    public boolean add(E newEntry) throws NullPointerException{
    	//contains will throw the null pointer exception
    	//System.out.println(newEntry);

    	checkInitialized();
    	if(contains(newEntry)){
    		return false;
    	}else{
    		numOfEntries++;
    		if(numOfEntries>set.length){
    			//double size.
    			System.out.println("Size before doubling is " + set.length);
    			System.out.println("Number of Entries will be " + numOfEntries);
       			doubleCapacity();
       			System.out.println("Just doubled.");
    		}
            
    		set[numOfEntries-1] = newEntry;
    	}
    	return true;

    }

    /**
     * Removes a specific entry from this set, if possible.
     *
     * <p> If this set contains the entry, remove modifies the set so that it no
     * longer contains entry. All other entries remain unmodified. Identifying
     * this entry is accomplished using the .equals() method. The removed entry
     * will be returned.
     *
     * <p> If this set does not contain entry, remove will return null without
     * modifying the set. Because null cannot be added, a return value of null
     * will never indicate a successful removal.
     *
     * <p> If the specified entry is null, then remove throws
     * NullPointerException without modifying the set.
     *
     * @param entry  The entry to be removed
     * @return  The removed entry if removal was successful; null otherwise
     * @throws NullPointerException  If entry is null
     */
    public E remove(E entry) throws NullPointerException {
    	checkInitialized();
    	if(entry == null){
    		throw new NullPointerException();
    	}
        int index = getIndexOf(entry);
        E result = removeAt(index);
        return result;
    }


    /**
     * Removes an arbitrary entry from this set, if possible.
     *
     * <p> If this set contains at least one entry, remove will modify the set
     * so that it no longer contains one of its entries. All other entries
     * remain unmodified. The removed entry will be returned.
     *
     * <p> If this set is empty, remove will return null without modifying the
     * set. Because null cannot be added, a return value of null will never
     * indicate a successful removal.
     *
     * @return  The removed entry if the removal was successful; null otherwise
     */
    public E remove() {
    	checkInitialized();
        E result = removeAt(numOfEntries - 1);
        return result;
    }

	private E removeAt(int index) {
        E result = null;

        if (index >= 0) {
            // Entry to remove
            result = set[index];
            int last = numOfEntries - 1;
            // Replace entry to remove with last entry
            set[index] = set[last];
            // Remove reference to last entry
            set[last] = null;
            numOfEntries--;
        }

        return result;
    }
    /**
     * Removes all entries from this set.
     *
     * <p> If this set is already empty, clear will not modify the set.
     * Otherwise, the set will be modified so that it contains no entries.
     */
    public void clear(){
    	for(int i=0;i<numOfEntries;i++){
    		set[i]=null;
    	}
    	numOfEntries=0;
    }

    /**
     * Tests whether this set contains a given entry. Equality is determined
     * using the .equals() method.
     *
     * <p> If this set contains entry, then contains returns true. Otherwise
     * (including if this set is empty), contains returns false. If entry is
     * null, then contains throws NullPointerException. The method never modifies
     * this set.
     *
     * @param entry  The entry to locate
     * @return  true if this set contains entry; false if nots
     * @throws NullPointerException  If entry is null
     */
    public boolean contains(E entry) throws NullPointerException{
    	//can't check initialized
    	checkInitialized();
   		if(entry == null){
    		throw new NullPointerException();
    	}
    	//change to see if getindexof is -1 to return false
    	/*
    	for(int i=0;i<set.length;i++){
    		if(entry.equals(set[i])){
    			return true;
    		}
    	}
		*/
    	if(getIndexOf(entry)==-1){
    		return false;
    	}
    	return true;

    }

    /**
     * Retrieves all entries that are in this set.
     *
     * <p> An array is returned that contains a reference to each of the entries
     * in this set. The returned array's length will be equal to the number of
     * elements in this set, and thus the array will contain no null values.
     *
     * <p> If the implementation of set is array-backed, toArray will not return
     * the private backing array. Instead, a new array will be allocated with
     * exactly the appropriate capacity (including an array of size 0, if the
     * set is empty).
     *
     * @return  A newly-allocated array of all the entries in this set
     */
    public Object[] toArray(){
    	E[] result = (E[])new Object[numOfEntries];
    	for(int i=0;i<numOfEntries;i++){
    		result[i]=set[i];
    	}
    	return result;
    }

    private void checkInitialized(){
    	if(!initialized){
    		throw new SecurityException("Set is not initialized properly");
    	}
    }

    private int getIndexOf(E entry) {
        int location = -1;
        int index = 0;

        while (index < numOfEntries&&location == -1) {
            if (entry.equals(set[index])) {
                location = index;
            }
            index++;
        }

        return location;
    }

    private void doubleCapacity(){
    	int newLength = 2 * set.length;
    	checkCapacity(newLength);
    	set = Arrays.copyOf(set, newLength);
    }

    private void checkCapacity(int capacity){
    	if(capacity > MAX_CAP||capacity<=0){
    		throw new IllegalArgumentException("The set cannot be expanded past a maximum of " + MAX_CAP 
    			+ "\nAlso, don't make the size less than or equal to zero.");
    	}
    }
}