package com.farouk.thread.application;

import java.util.concurrent.FutureTask;

import com.farouk.thread.callable.CallableDemo;

public class CallableApplication {
	
	 // the idea is to use callable without ExecutorService
	
	public static void main(String args[]) {
		   CallableDemo C1 = new CallableDemo( "Thread-1", 34976485, 976485);
		        // FuturTask will encapsulate the value received from the thread
		   FutureTask<Integer> futureC1 = new FutureTask<Integer>(C1);
		   Thread tC1 = new Thread(futureC1);
		   System.out.println("Starting " +  "Thread-1" + " on value [" + 34976485 + "," + 976485 + "]");
		   tC1.start();
		   
		   CallableDemo C2 = new CallableDemo( "Thread-2", 976484, 485);
		   FutureTask<Integer> futureC2 = new FutureTask<Integer>(C2);
		   Thread tC2 = new Thread(futureC2);
		   System.out.println("Starting " +  "Thread-2" + " on value [" + 976484 + "," + 485 + "]");
		   tC2.start();
		   
		   CallableDemo C3 = new CallableDemo( "Thread-3", 484, 1);
		   FutureTask<Integer> futureC3 = new FutureTask<Integer>(C3);
		   Thread tC3 = new Thread(futureC3);
		   System.out.println("Starting " +  "Thread-3" + " on value [" + 484 + "," + 1 + "]");
		   tC3.start();
		             // isDone is a boolean value to indicate whether the thread complete its task
		   while(!futureC1.isDone() && !futureC2.isDone() && !futureC3.isDone()){
			           System.out.println("Waiting");
		             }
		   int result = 0;
		   try {
			             result = futureC1.get().intValue() + futureC2.get().intValue() + futureC3.get().intValue();;
		             } catch (Exception e) {
			            e.printStackTrace();
		                   }
		   System.out.println("Returning result is " + result);
	   }   
	   
}