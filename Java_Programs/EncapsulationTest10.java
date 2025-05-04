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
class Customer{
    private int custid;
    private String custname;
    private String custemail;
    private long mobilno;

    public int getCustid() {
        return custid;
    }

    public void setCustid(int custid) {
        this.custid = custid;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustemail() {
        return custemail;
    }

    public void setCustemail(String custemail) {
        this.custemail = custemail;
    }

    public long getMobilno() {
        return mobilno;
    }

    public void setMobilno(long mobilno) {
        this.mobilno = mobilno;
    }
}
public class EncapsulationTest10 {
    public static void main(String[] args) {
        Customer c=new Customer();
        c.setCustid(111);
        c.setCustname("sagar");
        c.setCustemail("abc@123");
        c.setMobilno(1234567890);
        System.out.println("ID ="+c.getCustid());
        System.out.println("NAME ="+c.getCustname());
        System.out.println("Email ="+c.getCustemail());
        System.out.println("MOBILE NO ="+c.getMobilno());
    }
}
