package com.springcore.spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Student {
	@Value("#{11+22}")
	private int x;
	@Value("#{8>6?88:55}")
	private int y;
	@Value("#{T(java.lang.Math).sqrt(25)}")
	private double z;
	@Value("#{T(java.lang.Math).E}")  
	private double e;
	@Value("#{T(java.lang.Math).PI}")
	private double pi;
	@Value("#{new java.lang.String('Sagar Marshettiwar')}")
	private String name;
	@Value("#{8>2}")
	private boolean Active;
	
	
	public boolean isActive() {
		return Active;
	}
	public void setActive(boolean active) {
		Active = active;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getE() {
		return e;
	}
	public void setE(double e) {
		this.e = e;
	}
	public double getPi() {
		return pi;
	}
	public void setPi(double pi) {
		this.pi = pi;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "Student [x=" + x + ", y=" + y + ", z=" + z + ", e=" + e + ", pi=" + pi + ", name=" + name + ", Active="
				+ Active + "]";
	}


}
