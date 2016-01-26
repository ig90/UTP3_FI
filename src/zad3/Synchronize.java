package zad3;

public class Synchronize implements Runnable{

	private KontenerDanych kont;
	
	public Synchronize(KontenerDanych kont){
		this.kont = kont;
	}
	
	@Override
	public void run() {
		
		synchronized(this){
		int counter = 0;
		while((counter = kont.incr())< 10 ){
			System.out.println("WÄ…tek1: " + counter);
		}
	}
	}

}
