package com.testcase;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableTest {

	   public static void main(String... args) throws ExecutionException, InterruptedException {

	        CompletableFuture<String> future = CompletableFuture.supplyAsync( () -> doSomethingAndReturnA() );
	        
	        future.thenApply(a -> convertToB(a));

	        String i = future.get();
	        System.out.println("out : " + i);

		   
		   
		   
		   
	    }

	    private static int convertToB(final String a) {
	        System.out.println("convertToB: " + Thread.currentThread().getName());
	        return Integer.parseInt(a);
	    }

	    private static String doSomethingAndReturnA() {
	        System.out.println("doSomethingAndReturnA: " + Thread.currentThread().getName());
	        try {
	            Thread.sleep(1);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        return "5";
	    }	
	
}
