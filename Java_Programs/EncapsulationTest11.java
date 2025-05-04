/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EncapsulationExample;

/**
 *
 * @author SAGAR
 */
class Product{
    private int pId;
    private String pName;
    private double pPrice;

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public double getpPrice() {
        return pPrice;
    }

    public void setpPrice(double pPrice) {
        this.pPrice = pPrice;
    }
    
}
public class EncapsulationTest11 {
    public static void main(String[] args) {
        Product p=new Product();
        p.setpId(111);
        p.setpName("sagar");
        p.setpPrice(1000.00);
        System.out.println("ID ="+p.getpId());
        System.out.println("Name ="+p.getpName());
        System.out.println("Price ="+p.getpPrice());
    }
}
