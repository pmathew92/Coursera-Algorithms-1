import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque <Item> implements Iterable <Item> {
	private int size; 
	private Node first;
	private Node last;

	public Deque() {
		this.size = 0;
		this.first = null;
		this.last = null;
	}

	private class Node { 
		Item item;
		Node next,prev;
	}


	private class DequeIterator implements Iterator <Item> {
		Node current=first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if( !hasNext() ){
				throw new java.util.NoSuchElementException();
			}

			Item item = current.item;
			current = current.next;
			return item;
		}

	}

	public boolean isEmpty() {
		return size == 0;
	}


	public int size() {
	 	return size;
	 }

	public void addFirst(Item item) {
		if (item == null ) {
			throw new NullPointerException();	
		}

		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
		first.prev = null;
		if ( isEmpty() ) {
			last = first;	
		} else {
			oldFirst.prev = first;
		}

		size++;
	} 

	public void addLast(Item item) {
		if (item == null ) {
			throw new NullPointerException();	
		}

		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.next = null;
		last.prev = oldLast;

		if( isEmpty() ) {
			first = last;
		} else {
			oldLast.next = last;
		}

		size++;
	}

	public Item removeFirst() {
		if ( isEmpty() ) {
			throw new NoSuchElementException();
		}
		Item item = first.item;
		first = first.next;
		size--;

		if ( isEmpty() ) {
			last = null;
		} else {
			first.prev = null;
		}

		return item;
	}

	public Item removeLast() {
		if ( isEmpty() ) {
			throw new NoSuchElementException();
		}

		Item item = last.item;
		last=last.prev;
		size--;

		if ( isEmpty() ) {
			first = null;
		} else {
			last.next = null;
		}

		return item;
	}

	public Iterator <Item> iterator() {
		return new DequeIterator();
	}
}