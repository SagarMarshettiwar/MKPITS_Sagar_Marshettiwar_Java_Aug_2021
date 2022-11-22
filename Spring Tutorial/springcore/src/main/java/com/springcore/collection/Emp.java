package com.springcore.collection;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Emp {
	private String name;
	private List<String> phones;
	private Set<String> Addresses;
	private Map<String, String> Courss;
	private Properties prop ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPhones() {
		return phones;
	}
	public void setPhones(List<String> phones) {
		this.phones = phones;
	}
	public Set<String> getAddresses() {
		return Addresses;
	}
	public void setAddresses(Set<String> addresses) {
		Addresses = addresses;
	}
	public Map<String, String> getCourss() {
		return Courss;
	}
	public void setCourss(Map<String, String> courss) {
		Courss = courss;
	}
	public Properties getProp() {
		return prop;
	}
	public void setProp(Properties prop) {
		this.prop = prop;
	}
	public Emp(String name, List<String> phones, Set<String> addresses, Map<String, String> courss, Properties prop) {
		super();
		this.name = name;
		this.phones = phones;
		Addresses = addresses;
		Courss = courss;
		this.prop = prop;
	}
	public Emp() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Emp [name=" + name + ", phones=" + phones + ", Addresses=" + Addresses + ", Courss=" + Courss
				+ ", prop=" + prop + "]";
	}
	
	
	
}
