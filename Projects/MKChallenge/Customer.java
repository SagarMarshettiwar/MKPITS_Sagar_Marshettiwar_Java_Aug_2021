/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MKChallenge;

/**
 *
 * @author SAGAR
 */
public class Customer {
    public int id;
    public String name;
    public String email;
    public String phoneno;
    public String acctyp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getAcctyp() {
        return acctyp;
    }

    public void setAcctyp(String acctyp) {
        this.acctyp = acctyp;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", email=" + email + ", phoneno=" + phoneno + ", acctyp=" + acctyp +'}';
    }
        
}
