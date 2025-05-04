/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hello;

/**
 *
 * @author SAGAR
 */
public class Order {
    int orderno;
    String orderdate;
    String custname;
    String prodname;
    int price;
    int quantity;
public void insert(int on,String od,String cn,String pn){
    orderno=on;
    orderdate=od;
    custname=cn;
    prodname=pn;
}
public int calculateBill(int cost,int Add){
    quantity=Add;
    return price=cost*quantity;
}
public String Display(){
    return "order no "+orderno+"\norder date "+orderdate+"\nCustmer name "+custname+"\nProduct name"
            +prodname+"\nprice"+price+"\nQuantity"+quantity;
    }
}