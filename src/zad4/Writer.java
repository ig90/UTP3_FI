/**
 *
 *  @author Filipiuk Igor S7334
 *
 */

package zad4;

import java.util.concurrent.BlockingQueue;


public class Writer implements Runnable {
	
	protected BlockingQueue queue = null;


	public Writer(Author autor) {
		
	}

	@Override
	public void run() {


		try {
			System.out.println(" " + queue.take());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
