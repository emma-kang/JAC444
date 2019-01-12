
package jac444WS1;
//import java.util.Scanner;
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
        double monthlyInterestRate = getMonthlyInterestRate();
        return this.balance * monthlyInterestRate;
    }

    // withdraw method
    public void withdraw(double amount){
        if(this.balance-amount < 0){
            this.balance = 0;
        }
        else
            this.balance -= amount;
    }

    // deposit method
    public void deposit(double amount){
        this.balance += amount;
    }

    public String toString(){
        return "ID : " + this.getId() + " , " + "Balance : " + this.getBalance() + " , " +
                "Annual Interest Rate : " + this.getAnnualInterestRate() + " , " +
                "Monthly Interest Rate : " + this.getMonthlyInterestRate() + " , " +
                "Monthly Interest : " + this.getMonthlyInterest();
    }

}

class CheckingAccount extends Account{
    private double overlimit;

    public CheckingAccount(){
        // call parent's constructor
        super();
        overlimit = 0;
    }

    public CheckingAccount(int id, double balance, double limit){
        // call parent's constructor
        super(id, balance);
        this.overlimit = limit;
    }

    // Accessor method for overlimit of CheckingAccount
    public double getOverlimit(){
        return this.overlimit;
    }

    // Mutator method for overlimit of CheckingAccount
    public void setOverlimit(double limit){
        this.overlimit = limit;
    }

    // override withdraw method
    public void withdraw(double amount){
        // If getBalance() substract amount is not over overlimit
        // set new Balance
        // else case, print error message
        if(getBalance() - amount > overlimit)
            setBalance(getBalance() - amount);
        else
            System.out.println("The amount cannot exceed the overdraft limit");
    }

    // Return a String decription of CheckingAccount class
    public String toString(){
        return super.toString() + "\nOverdraft limit: $" + String.format("%.2f", overlimit);
    }
}

class SavingAccount extends Account{
    // Call parent's default constructor
    public SavingAccount(){
        super();
    }

    // SavingAccount Constructor
    public SavingAccount(int id, double balance){
        super(id, balance);
    }

    // Overrides withdraw method
    public void withdraw(double amount){
        if(amount > getBalance())
            setBalance(getBalance()-amount);
        else
            System.out.println("Cannot be overdrawn");
    }

}

public class task4 {
    public static void main(String[] args){
// Create Account, SavingsAccount and Checking Account objects
        Account account = new Account(1122, 20000);
        SavingAccount savings = new SavingAccount(1001, 20000);
        CheckingAccount checking = new CheckingAccount(1004, 20000, -20);
        // Set annual interest rate of 4.5%
        account.setAnnualInterestRate(0.045);
        savings.setAnnualInterestRate(0.045);
        checking.setAnnualInterestRate(0.045);

        account.withdraw(2500);
        savings.withdraw(2500);
        checking.withdraw(2500);

        account.deposit(3000);
        savings.deposit(3000);
        checking.deposit(3000);

        checking.withdraw(24000);

        // Invoke toString methods
        System.out.println(account);
        System.out.println(savings);
        System.out.println(checking);

    }
}
