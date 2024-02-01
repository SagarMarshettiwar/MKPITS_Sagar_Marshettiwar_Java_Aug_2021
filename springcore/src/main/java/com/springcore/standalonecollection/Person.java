package com.springcore.standalonecollection;

import java.util.List;
import java.util.Map;
import java.util.Properties;


public class Person {
	private List<String> friends;
	private Map<String,String> feestructor;
	private Properties property;
	
	
	public Properties getProperty() {
		return property;
	}

	public void setProperty(Properties property) {
		this.property = property;
	}

	public Map<String, String> getFeestructor() {
		return feestructor;
	}

	public void setFeestructor(Map<String, String> feestructor) {
		this.feestructor = feestructor;
	}

	public List<String> getFriends() {
		return friends;
	}

	public void setFriends(List<String> friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return "Person [friends=" + friends + ", feestructor=" + feestructor + ", property=" + property + "]";
	}
	

}
