/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EncapsulationExample;

import java.util.Scanner;

/**
 *
 * @author SAGAR
 */
class Order{
    private int orderId;
    private String orderDate;
    private String productName;
    private int price;
    private int quantity;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String calculateBill(int p,int q){
        int rate=p*q;
        return "Total Bill ="+rate;
    }
    
}
public class Encapsulationtest12 {
    public static void main(String[] args) {
        Scanner s=new Scanner(System.in);
        Order o=new Order();
        System.out.println("id =");
        int ID=s.nextInt();
        System.out.println("name =");
        String NAME=s.next();
        System.out.println("Date =");
        String DATE=s.next();
        System.out.println("Price =");
        int PRICE=s.nextInt();
        System.out.println("Quantity =");
        int QUANTITY=s.nextInt();
        o.setOrderId(ID);
        o.setProductName(NAME);
        o.setOrderDate(DATE);
        o.setPrice(PRICE);
        o.setQuantity(QUANTITY);
        System.out.println("-------------------------------------------------");
        System.out.println("id ="+o.getOrderId());
        System.out.println("name ="+o.getProductName());
        System.out.println("date ="+o.getOrderDate());
        System.out.println("price ="+o.getPrice());
        System.out.println("quantity ="+o.getQuantity());
        System.out.println("-------------------------------------------------");
        System.out.println(o.calculateBill(o.getPrice(),o.getQuantity()));
    }
}
