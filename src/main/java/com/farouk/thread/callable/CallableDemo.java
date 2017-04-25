package com.farouk.thread.callable;

import java.util.concurrent.Callable;

       // callable API enable receive value from threads
public class CallableDemo implements Callable<Integer> {
	
	   private String threadName;
	   private int max;
	   private int min;
	   
	  public CallableDemo( String name, int max, int min) {
	      threadName = name;
	      this.max = max;this.min = min;
	      System.out.println("Creating Callable  " +  threadName  + " on value [" + min + "," + max + "]");
	   }

	   
	              // call method, it's like the thread's run method
	   
		public Integer call() throws Exception {
			 System.out.println("Running  Callable " +  threadName  + " on value [" + min + "," + max + "]");
			 int sum = 0;
		      try {
		         for(int i = max; i >=min; i--) {
		                sum += i;
		         }
		      }catch (Exception e) {
		         System.out.println("Thread " +  threadName + " on value [" + min + "," + max + "]" + " interrupted.");
		      }
		      System.out.println("Thread " +  threadName + " on value [" + min + "," + max + "]" + " exiting.");
			return new Integer(sum);
		}
		
		
	
	}