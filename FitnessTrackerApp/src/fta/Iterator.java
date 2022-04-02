package fta;

public interface Iterator {

	boolean hasNext();
	boolean hasLast();
	
	Object next();
	Object last();
}
