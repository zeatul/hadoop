package com.hawk.jvm;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

public class ExecuteAncestorMethodTest {
	
	public  class GrandFather{
		void thinking() throws Throwable{
			System.out.println("I am GrandFather");
		}
	}
	
	public  class Father extends GrandFather{
		void thinking() throws Throwable{
			System.out.println("I am father2");
		}
	}
	
	public  class Son extends Father{
		void thinking() throws Throwable{
//			System.out.println("I am son");
			MethodType mt = MethodType.methodType(void.class);
			MethodHandle mh = lookup().findSpecial(GrandFather.class, "thinking", mt, getClass());
			mh.invoke(this);
		}
	}
	
	public static void main(String[] args) throws Throwable{
		
		new ExecuteAncestorMethodTest().new Son().thinking();
		
//		Son son = new Son();
//		MethodType mt = MethodType.methodType(void.class);
//		MethodHandle mh = lookup().findSpecial(Father.class, "thinking", mt, Son.class).bindTo(son);
//		mh.invoke(null);
	}

}
