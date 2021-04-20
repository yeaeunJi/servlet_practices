package helloweb;


import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author Sang Hyup Lee
 * @version 1.0
 *
 */
public class IteratorAdapterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Vector v = new Vector();

		v.addElement(new String("Java"));
		v.addElement(new String("Eclipse"));
		v.addElement(new String("Spring"));

		Enumeration enu = v.elements();

		/*
	        Iterator iter = v.iterator();
	        while ( iter.hasNext() ) {
	            String temp = ""+iter.next();
	            System.out.println(temp);
	        }
		 */

		EnumerationIterator iteratorAdapter = new EnumerationIterator(enu);

		while ( iteratorAdapter.hasNext() ) {
			String temp = ""+iteratorAdapter.next();
			System.out.println(temp);
		}
	}

}