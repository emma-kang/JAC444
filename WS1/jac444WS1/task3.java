
package jac444WS1;
import java.util.Scanner;
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

}

public class task3 {
    public static void main(String[] args) {
        Account[] account = new Account[10];

        // set id and initial balance
        for (int i = 0; i < account.length; i++) {
            // initialize default value for data field
            account[i] = new Account();
            account[i].setId(i);
            account[i].setBalance(100);
        };

        // for user choice
        String id;
        String choice;
        int changeId;
        int changeChoice = 0;
        int amountWithdraw;
        int amountDeposit;

        // Create Scanner object
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print("Enter an id : ");
            id = input.next(); // Input String (next())

            if(isNumber(id) == false) {
                System.out.println("Not vaild input\n");
            }

            else if(isNumber(id) == true) {
                changeId = Integer.parseInt(id); // transfer String type as Int type

                for(int i=0; i<account.length; i++) {
                    if(account[i].getId() == changeId) {
                        do {
                            System.out.println("Main Menu");
                            System.out.println("1: Check Balance");
                            System.out.println("2: Withdraw");
                            System.out.println("3: Deposit");
                            System.out.println("4: Exit");
                            System.out.print("Enter a choice: ");
                            choice = input.next();

                            if(isNumber(choice) == true) {
                                changeChoice = Integer.parseInt(choice);
                                switch(changeChoice) {
                                    case 1 :
                                        System.out.println("The balance is " + account[i].getBalance());
                                        System.out.println();
                                        break;
                                    case 2 :
                                        System.out.print("Enter an amount to withdraw: ");
                                        amountWithdraw = input.nextInt();
                                        account[i].withdraw(amountWithdraw);
                                        System.out.println();
                                        break;
                                    case 3 :
                                        System.out.print("Enter an amount to deposit: ");
                                        amountDeposit = input.nextInt();
                                        account[i].deposit(amountDeposit);
                                        System.out.println();
                                        break;
                                    case 4 :
                                        System.out.println();
                                        break;

                                    default :
                                        System.out.println("Enter Existing Menu (1 ~ 4)");
                                        System.out.println();
                                        break;
                                }
                            }
                            else if(isNumber(choice) == false) {
                                System.out.println("Not valid input\n");
                                continue;
                            }
                        }while(changeChoice != 4);
                    }
                }
            }
        }
    }

    private static boolean isNumber(String idValue) {
        try {
            Integer.parseInt(idValue); // transfer int type possibly
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}

