package de.heidelberg.pvs.diego.collectionswitch.custom.sets;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


@SuppressWarnings("rawtypes")
public class ArraySet_Naive extends AbstractSet {
    private static final Object[] EMPTY_ARRAY = new Object[0];
    
    private class ArrayIterator implements Iterator {
        int itVersion = version;
        int pos = 0; // position of next item to return
        boolean hasNext = values.length > 0;
        boolean removeOk = false;
        
        public boolean hasNext() {
            return hasNext;
        }
        
        public Object next() {
            if(itVersion != version) {
                throw new ConcurrentModificationException();
            } else if(!hasNext) {
                throw new NoSuchElementException();
            } else {
                Object ret = values[pos];
                ++pos;
                hasNext = pos < values.length;
                removeOk = true;
                return ret;
            }
        }
        
        public void remove() {
            if(itVersion != version) {
                throw new ConcurrentModificationException();
            } else if(!removeOk) {
                throw new IllegalStateException();
            } else if(values.length == 1) {
                values = EMPTY_ARRAY;
                ++version;
                itVersion = version;
                removeOk = false;
            } else {
                Object[] newValues = new Object[values.length - 1];
                if(pos > 1) {
                    System.arraycopy(values, 0, newValues, 0, pos - 1);
                }
                if(pos < values.length) {
                    System.arraycopy(values, pos, newValues, pos - 1, values.length - pos);
                }
                values = newValues;
                --pos;
                ++version;
                itVersion = version;
                removeOk = false;
            }
        }
    }
    
    private int version = 0;
    private Object[] values = EMPTY_ARRAY;
    
    public ArraySet_Naive() { 
    }
    
    public ArraySet_Naive(int initialCapacity) {
    	// IGNORED
    	//values = new Object[initialCapacity];
    }
    
    public ArraySet_Naive(Collection<?> set) {
    	this.addAll(set);
    	
    }
    
    public Object[] toArray() {
        return values;
    }
    
    public Object clone() {
        ArraySet_Naive ret = new ArraySet_Naive();
        if(this.values == EMPTY_ARRAY) {
            ret.values = EMPTY_ARRAY;
        } else {
            ret.values = (Object[]) this.values.clone();
        }
        return ret;
    }
    
    public void clear() {
        values = EMPTY_ARRAY;
        ++version;
    }
    
    public boolean isEmpty() {
        return values.length == 0;
    }

    public int size() {
        return values.length;
    }
    
    public boolean add(Object value) {
        int n = values.length;
        for(int i = 0; i < n; i++) {
            if(values[i].equals(value)) return false;
        }
        
        Object[] newValues = new Object[n + 1];
        System.arraycopy(values, 0, newValues, 0, n);
        newValues[n] = value;
        values = newValues;
        ++version;
        return true;
    }
    
    public boolean contains(Object value) {
        for(int i = 0, n = values.length; i < n; i++) {
            if(values[i].equals(value)) return true;
        }
        return false;
    }

    public Iterator iterator() {
        return new ArrayIterator();
    }
    
}
