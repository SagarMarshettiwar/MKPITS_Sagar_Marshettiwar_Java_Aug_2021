package com.restAPI.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restAPI.Model.Books;

@Service
public class Services {
	public static List<Books> list = new ArrayList<>();
	
	static {
		list.add(new Books(1,"java core","abc"));
		list.add(new Books(2,"java core 2","abc 1"));
		list.add(new Books(3,"java core 3","abc 2"));
	}
	
	public List<Books> getlist(){
		return list;
	}
	
	public List<Books> getListById(int id) {
	    List<Books> resultList = new ArrayList<>();
	    for (Books book : list) {
	        if (book.getId() == id) {
	            resultList.add(book);
	        }
	    }
	    return resultList;
	}
	
	public Books addBooks(Books b) {
		list.add(b);
		return b;
	}
	
	public List<Books> deleteBooksBYID(int id) {
		for (int i = 0; i < list.size(); i++) {
	        Books book = list.get(i);
	        if (book.getId() == id) {
	            list.remove(i);
	            return list;
	        }
	    }
	    return null;
    }
	
	public List<Books> updateBooksById(Books b,int id){
		for (int i = 0; i < list.size(); i++) {
	        Books book = list.get(i);
	        if (book.getId() == id) {  
	            book.setName(b.getName());
	            book.setAuthor(b.getAuthor());
	            return list;
	        }
	    }
	    return null;
	}
	
}