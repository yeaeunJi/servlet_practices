package helloweb;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

public class EnumerationIterator implements Iterator{
	Enumeration enum1;
	
	public EnumerationIterator(Enumeration enum1) {
		this.enum1 = enum1;
	}
	
	public boolean hasNext() {
	    return enum1.hasMoreElements();
	  }

	  public Object next() {
	    return enum1.nextElement();
	  }

	  public void remove() {
	    throw new UnsupportedOperationException();
	  }
	  
//	  public static void main(String[] args) {
//		  
//	        Vector<String> v1 = new Vector<String>(2);
//	    
//	        v1.addElement("삼성");
//	        v1.addElement("LG");
//	        v1.addElement("SK");
//	        v1.addElement("구글");
//	        
//	      Enumeration<String> enum1 = v1.elements();
//	        
//		  Iterator<String> eiIter = new EnumerationIterator(enum1);
//		  while(eiIter.hasNext()) {
//			  System.out.println(" - "+eiIter.next()); 
//		  }
//	  }
}
