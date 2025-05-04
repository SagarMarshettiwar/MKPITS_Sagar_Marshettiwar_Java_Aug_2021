//create a parent class account having fields accountno and balance. and constructor with 2 parameters
//create a subclass saving inherited from account class having fields interest
//and constructor with 3 parameters and from this call parent class constructor.
//and a display method, deposit method and withdrawl method.
class Account{
    int accno;
    int balance;

    public Account(int accno, int balance) {
        this.accno = accno;
        this.balance = balance;
    }
    void display(){
        System.out.println("Account no ="+accno);
        System.out.println("Balance ="+balance);
    }
    void deposit(int add){
        System.out.println("Amount Deposited ="+(balance+add));
    }
//    void Withdraw(int Sub){
//        if(balance>Sub){
//            System.out.println("Amount Withdraw ="+(balance-Sub));
//        }else{
//            System.out.println("insufficient Balance");
//        }
//    }
}    
class Saving extends Account{
    int interest;
    int result;
    public Saving(int interest, int accno, int balance) {
        super(accno, balance);
        this.interest = interest;
        super.display();
    }
    void deposit(int add){
        super.deposit(add);
        result=balance+add+interest;
        System.out.println("Deposited with Interest"+result);
    }
    void Withdraw(int sub){
        if(result>sub){
            System.out.println("Amount Withdraw ="+(result-sub));
        }else{
            System.out.println("insufficient Balance");
        }
    }
}
public class SuperEx11 {
    public static void main(String[] args) {
        Saving s=new Saving(5,111,1000);
        System.out.println("Deposited =500");
        s.deposit(500);
        System.out.println("Withdrawl =500");
        s.Withdraw(500);
    }
}