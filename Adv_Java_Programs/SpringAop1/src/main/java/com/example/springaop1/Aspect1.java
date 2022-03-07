package com.example.springaop1;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

//@Aspect
//public class Aspect1 {
//    @Before("execution(public int getId())")
//    public void beforeAdvice(){
//        System.out.println("before");
//    }
//    @After("execution(public int getId())")
//    public void afterAdvice(){
//        System.out.println("after");
//    }
//}
//@Aspect     //to call Specific Advice by using package
//public class Aspect1 {
//    @Before("execution(public int com.example.springaop1.Stud.getId())")
//    public void beforeAdvice(){
//        System.out.println("before");
//    }
////    @After("execution(public int getId())")
////    public void afterAdvice(){
////        System.out.println("after");
////    }

    @Aspect     //to call all common call
    public class Aspect1 {
        @Before("execution(public * get*(..))")
        public void beforeAdvice(){
            System.out.println("before");
        }
//    @After("execution(public int getId())")
//    public void afterAdvice(){
//        System.out.println("after");
//    }
}