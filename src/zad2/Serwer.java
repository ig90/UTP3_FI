package zad2;

import java.net.*;
import java.util.concurrent.*;
import java.io.*;


public class Serwer { 
  
	    ServerSocket ss;
	    boolean up;

	    public Serwer(int port){
	      try{
	        ss = new ServerSocket(port);
	      }
	      catch (Exception e){
	        e.printStackTrace();
	      }
	      up = true;
	    }

	    public void runServer(){
	      try{
	        while (up){
	          Socket s = ss.accept();
	          System.out.println("client connected");
	          service(s);
	        }
	        ss.close();
	      }
	      catch (Exception e){
	        e.printStackTrace();
	      }
	    }

	    void service(Socket s){
	      Thread t = new Thread(new ClientHandler(s));
	      t.start();
	    }

	}

	  class ClientHandler implements Runnable{
	    Socket sckt;

	    public ClientHandler(Socket s){
	      sckt = s;
	    }

	    public void run(){
	   
	      try{
	        BufferedReader br
	         = new BufferedReader(new InputStreamReader(sckt.getInputStream()));
	        String msg = br.readLine();
	        FutureTaskCallback<String> ftc 
	         = new FutureTaskCallback<String>(new MyCallable(msg));
	        new Thread(ftc).start();
	        PrintWriter pw = new PrintWriter(sckt.getOutputStream(), true);
	        pw.println(ftc.get());
	        sckt.close();
	      }
	      catch (Exception e){
	        e.printStackTrace();
	      }
	    }
	  }

	  class FutureTaskCallback<V> extends FutureTask<V> {

	    public FutureTaskCallback(Callable<V> callable) {
	      super(callable);
	    }

	    public void done() {
	      String result = "Wynik: ";
	      if (isCancelled()){
	       result += "Anulowano";
	      }
	      else{
	        try {
	          result += get();
	        }
	        catch(Exception exc) {
	          result += exc.toString();
	        }
	      }
	      System.out.println(result);
	    }
	  }
	  
	  class MyCallable implements Callable<String>{
		  
		  int counter=0;
		  String s="hello";
		  public MyCallable(String s){
		      this.s=s;
		    }
		  
		  public String call() throws java.io.IOException, InterruptedException {

			  StringBuffer out = new StringBuffer();

			  while(counter < 100 ){
				  	counter++;
				  	Thread.sleep(500);
					System.out.println(s + counter);
					if (counter == 99){
						
					}
				}
	
			  return out.toString();
		  
	  }

	  }
	  
