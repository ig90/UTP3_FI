package zad3;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlokadaJawna implements Runnable{

	private KontenerDanych kont;
	private Lock blokada;
	private int counter = 0;
	
	public BlokadaJawna(KontenerDanych kont){
		this.kont = kont;
		blokada = new ReentrantLock();
	}
	
	@Override
	public void run() {
		blokada.lock();
		
		try {
			while((counter = kont.incr())< 10 ){
				System.out.println("WÄ…tek3: " + counter);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
		blokada.unlock();
		}
	}

}
