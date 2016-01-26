/**
 *
 *  @author Filipiuk Igor S7334
 *
 */

package zad3;


public class Main {

  public static void main(String[] args) {
	  
	  KontenerDanych kont = new KontenerDanych();
	  
	  Runnable runn1 = new Synchronize(kont); 
	  Thread watek1 = new Thread(runn1);
	  
	  Runnable runn2 = new Synchronize2(kont); 
	  Thread watek2 = new Thread(runn2);
	  
	  Runnable runn3 = new BlokadaJawna(kont);
	  Thread watek3 = new Thread(runn3);
	  
	  watek1.start();
	  watek2.start();
	  watek3.start();
  }
  
}
