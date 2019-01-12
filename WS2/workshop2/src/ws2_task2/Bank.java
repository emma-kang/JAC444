/**
 * JAC444 Workshop 2
 * Instructor: Mahboob Ali
 * Name: Yuseon Kang (Student ID: 146831169)
 * Bank class
 * Date: 2018-10-15
 */
package ws2_task2;
import java.util.Scanner;
import static ws2_task2.Task2.check;
import static ws2_task2.Task2.checkDouble;


public class Bank {
    //private int id;
    private double balance; // current balance
    private int loanedBank[]; // parallel array for loaned Bank id
    private double loanedValue[]; // parallel array for loaned value


    public Bank(int id, double b, int nLoaned){
        Scanner input = new Scanner(System.in);

        // store parameter to the data field
        //this.id = id;
        this.balance = b;
        ///this.limit = limit;
        loanedBank = new int[nLoaned];
        loanedValue = new double[nLoaned];


        for(int i=0; i < nLoaned; i++){
            System.out.print("Bank ID: ");
            loanedBank[i] = input.nextInt();
            System.out.print("Amount: ");
            loanedValue[i] = input.nextDouble();
            loanedValue[i] = checkDouble(loanedValue[i]);
        }
    }

    public double getTotalAsset(){

        double sum = this.balance;

        for(int i = 0; i < this.loanedBank.length; i++){
            sum += this.loanedValue[i];
        }

        return sum;
    }


    public double findLoan(int id){
        // to find loan by using id
        for(int i = 0; i < loanedBank.length; i++){
            if(loanedBank[i] == id){
                return loanedValue[i];
            }
        }

        return 0;
    }

    public void clearLoan(int id){
        for(int i = 0; i < loanedBank.length; i++){
            if(loanedBank[i] == id)
                loanedValue[i] = 0;
        }
    }

}



