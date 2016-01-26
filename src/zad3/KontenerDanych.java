package zad3;

public class KontenerDanych {
	
	private volatile int counter;
	
	public int incr(){
		
		return ++counter;
	}

}
