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
public class Account
{
    public int bal=1000;
    public int deposit(int amt)
    {
        bal= bal + amt;
        return bal;
    }
}
