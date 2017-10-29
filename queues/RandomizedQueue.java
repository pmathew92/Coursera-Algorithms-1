import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] s;
	private int size;

	public RandomizedQueue() {
		this.s= (Item[]) new Object[1];
	}

	private class RandomizedQueueIterator implements Iterator<Item> {
		int count = size;
		int[] order =  new int[size];

		public RandomizedQueueIterator() {
			for (int i = 0; i < count; i++) {
				order[i] = i;
            }
            StdRandom.shuffle(order);
		}

		public boolean hasNext() {
			return count > 0;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if( !hasNext() ){
				throw new java.util.NoSuchElementException();
			}
			return s[order[--count]];		
		}

	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void enqueue( Item item ) {
		if( item == null) {
			throw new NullPointerException();
		}

		if ( size == s.length ) {
			resizeArray(2*s.length);
		}

		s[size++] = item;
	}

	public Item dequeue () {
		if( isEmpty() ) {
			throw new NoSuchElementException();
		}

		int random = StdRandom.uniform(size);
		Item item = s[random];
		s[random] = s[--size];
		s[size] = null;

		if ( size > 0 && size == s.length/4 ) {
			resizeArray( s.length/2 );
		}
		return item;
	}

	public Item sample() {
		if( isEmpty() ) {
			throw new NoSuchElementException();
		}

		int random = StdRandom.uniform(size);
		return s[random];
	}

	public Iterator<Item> iterator() {
		return new RandomizedQueueIterator();
	}

	private void resizeArray( int capacity ) {
		Item[] copy = (Item[]) new Object[capacity];

		for (int i=0;i<size;i++) {
			copy[i]=s[i];
		}
		s=copy;
	}

}