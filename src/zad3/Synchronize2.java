package zad3;

public class Synchronize2 implements Runnable{

	private KontenerDanych kont;
	
	public Synchronize2(KontenerDanych kont){
		this.kont = kont;
	}
	
	@Override
	public void run() {
		int counter = 0;
		while((counter = kont.incr())< 10 ){
			System.out.println("WÄ…tek2: " + counter);
		}
	}

}
