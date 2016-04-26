package com.hawk;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	
	
	public static class MyThread extends Thread{
		private static final AtomicInteger index = new AtomicInteger(0);
		public MyThread(){
			this.setName(this.getClass().getSimpleName() + index.incrementAndGet());
		}
		
		public MyThread(int i){
			
		}
	}
	
	public static class WriterThread extends MyThread{
		public WriterThread(int i){
			
		}
	}

	
	
	public static void main(String[] args) {
		WriterThread thread = new WriterThread(1);
		System.out.println(thread.getName());

	}

}
