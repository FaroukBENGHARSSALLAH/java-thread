package com.farouk.thread.application;

import com.farouk.thread.runnable.RunnableDemo;

public class RunnableApplication {

	    public static void main(String[] args) {
			
	    	   RunnableDemo thread1  = new RunnableDemo("Thread-1");
	    	   RunnableDemo thread2  = new RunnableDemo("Thread-2");
	    	   
	    	   thread1.start();
	    	   thread2.start();
		}
}
