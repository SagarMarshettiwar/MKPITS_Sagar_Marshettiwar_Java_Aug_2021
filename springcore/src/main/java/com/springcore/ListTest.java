package com.springcore;

import java.util.ArrayList;

public class ListTest {
	public static void main(String args[]) {
		ArrayList<Model> modelarraylist = new ArrayList<Model>();
		Model m=new Model();
		m.setName("sagar");
		m.setStreet("katol");
		m.setAge("25");
		m.setAddress("it park");
		/*
		 * m.setName("amit"); m.setStreet("sadar"); m.setAge("24");
		 * m.setAddress("Mate");
		 */
		modelarraylist.add(m);
		
		Model m1=new Model(); 
		m1.setName("amit");
		m1.setStreet("sadar");
		m1.setAge("24"); 
		m1.setAddress("Mate");
		  
		modelarraylist.add(m1); 
		/*
		 * System.out.println(modelarraylist.get(0).getName());
		 * System.out.println(modelarraylist.get(0).getStreet());
		 * System.out.println(modelarraylist.get(0).getAge());
		 * System.out.println(modelarraylist.get(0).getAddress());
		 */
		 for(int i=0;i<modelarraylist.size();i++) {
			 System.out.println(modelarraylist.get(i).getName());
			 System.out.println(modelarraylist.get(i).getStreet());
			 System.out.println(modelarraylist.get(i).getAge());
			 System.out.println(modelarraylist.get(i).getAddress()); }
		 
	}
}
