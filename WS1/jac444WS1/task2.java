

package jac444WS1;
import java.util.Date;

class Account {
    // private data field
    private int id;
    private double balance;
    private double annualInterestRate;
    private Date dateCreated;

    // Default Constructor
    public Account(){
        // initialize the value as default
        id = 0;
        balance = 0;
        annualInterestRate = 0;
    }
    // Constructor
    public Account(int accId, double initBalance){
        id = accId;
        balance = initBalance;
    }
    // Accessor method for id
    public int getId(){
        return this.id;
    }

    // Accessor method for balance
    public double getBalance(){
        return this.balance;
    }

    // Accessor method for annualInterestRate
    public double getAnnualInterestRate(){
        return this.annualInterestRate;
    }

    // Accessor method for dateCreated
    public Date getDate(){
        this.dateCreated = new Date();
        return this.dateCreated;
    }

    // Mutator method for id
    public void setId(int i){
        this.id = i;
    }

    // Mutator method for balance
    public void setBalance(double b){
        this.balance = b;
    }

    // Mutator method for annualInterstRate
    public void setAnnualInterestRate(double rate){
        this.annualInterestRate = rate;
    }

    // getMonthlyInterestRate method
    public double getMonthlyInterestRate(){
        return this.annualInterestRate/12;
    }

    // getMonthlyInterest method
    public double getMonthlyInterest(){
        return this.balance * getMonthlyInterestRate();
    }

    // withdraw method
    public void withdraw(double amount){
        this.balance -= amount;
    }

    // deposit method
    public void deposit(double amount){
        this.balance += amount;
    }

}

public class task2 {
    public static void main(String[] args){
        Account a = new Account(1122, 20000);
        a.setAnnualInterestRate(0.045);
        a.withdraw(2500);
        a.deposit(3000);

        System.out.println(a.getBalance() + " " + a.getMonthlyInterest());
        System.out.print(a.getDate());

    }
}

