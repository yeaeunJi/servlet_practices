package helloweb;

import java.util.Enumeration;
import java.util.Vector;

public class EnumerationTest{
    
    public static void main(String[] args) {
        
        Vector<String> v1 = new Vector<String>(2);
    
        v1.addElement("삼성");
        v1.addElement("LG");
        v1.addElement("SK");
        v1.addElement("구글");

        System.out.println("Vector 요소들은 다음과 같다.");
        for(int i=0; i<v1.size(); i++){
            System.out.println("v1의 "+i+"번째 요소 : "+v1.elementAt(i));
        }
        
        Enumeration<String> e = v1.elements();
        
      System.out.println();
      System.out.println("Vector v1으로부터 생성한 Enumeration의 요소들은 다음과 같다.");
        
        while(e.hasMoreElements()){
            System.out.println("e의 요소 : "+e.nextElement());
        }
    }
}