/**
 * JAC444 Workshop 2
 * Instructor: Mahboob Ali
 * Name: Yuseon Kang (Student ID: 146831169)
 * Task2.java
 * Date: 2018-10-15
 */

package ws2_task2;
import java.util.Scanner;
import java.util.ArrayList;


public class Task2 {
    public static void main(String[] args){
        // limit the max input of the number of banks
        final int MAX = 5;
        // create Scanner object
        Scanner input = new Scanner(System.in);
        // to get user input
        int nBank = 0;
        double limit = 0;
        // to find invalid input
        boolean flag = false;
        // integer arraylist : this is more flexible for allocating memory(in other words the size of array)
        ArrayList<Integer> unsafe = new ArrayList<>();

        System.out.print("**********************************************\n");
        System.out.print("*** Welcome to Bank Stabilty Check Program ***\n");
        System.out.print("**********************************************\n\n");

        System.out.println("Maximum the number of bank to check is Five");
        System.out.println("Asset limt should be from 100 to 10000\n");

        do{
            try{
                System.out.println("Number of banks: ");
                nBank = input.nextInt();
                nBank = check(nBank, MAX);
                System.out.println("Minimun asset limit: ");
                limit = input.nextDouble();
                limit = checkDouble(limit);
                flag = false;
            } catch (Exception e){
                input.nextLine();
                if(e.getMessage() != null)
                    System.out.println(e.getMessage());
                else
                    System.out.println("Invalid input, please try again");
                flag = true;
            }
        } while(flag);


        Bank[] banks = new Bank[nBank];

        for(int i=0; i < nBank; i++){
            do{
                try{
                    double curBalance = 0;
                    int numLoaned = 0;
                    System.out.println("Bank # " + i);
                    System.out.println("Balance: ");
                    curBalance = input.nextDouble();
                    curBalance = checkDouble(curBalance);
                    System.out.println("Number of banks Loaned: ");
                    numLoaned = input.nextInt();
                    numLoaned = check(numLoaned, nBank);
                    banks[i] = new Bank(i, curBalance, numLoaned);
                    flag = false;
                } catch (Exception e){
                    input.nextLine();
                    System.out.println("Invalid input, please try again");
                    flag = true;
                }
            } while (flag);
        }

        // check the banks whether bank is unsafe or not
        for(int i = 0; i < banks.length; i++){
            // banks' index is same with the bank id
            if(unsafeBank(banks[i], limit))
                unsafe.add(i);
        }

        // compare between bank's id and unsafe bank's id
        for(int i = 0; i < banks.length; i++){
            for(int j = 0; j < unsafe.size(); j++){
                // if there's loaned bank list which is same with the unsafe bank id
                if(banks[i].findLoan(unsafe.get(j)) > 0){
                    banks[i].clearLoan(unsafe.get(j)); // clear the loan to re-calculate the total asset
                    // re-check safe or unsafe
                    if(unsafeBank(banks[i], limit)){
                        unsafe.add(i);
                    }
                }
            }
        }

        // if there are unsafe banks print out the bank
        if(unsafe.size() > 0){
            System.out.print("Unsafe banks are ");
            for(int i = 0; i < unsafe.size(); i++){
                System.out.print("Bank " + unsafe.get(i));
                if(unsafe.size() > 1 && i != unsafe.size()-1)
                    System.out.print(" and ");
            }
        }
        else {
            System.out.println("There are no unsafe banks.");
            System.out.print("Safe banks are ");
            for (int i = 0; i < banks.length; i++){
                System.out.print("Bank " + i);
                if(banks.length > 1 && i != (banks.length-1))
                    System.out.print(" and ");
            }
        }

    }


    public static boolean unsafeBank(Bank bank, double limit){
        if(bank.getTotalAsset() < limit){
            return true;
        }

        return false;
    }

    public static int check(int num, int max) throws IllegalArgumentException{
        if(num < 0){
            throw new IllegalArgumentException("Invalid input, it must be positive");
        }
        else if(num > max){
            throw new IllegalArgumentException("Invalid input, the input cannot exceed the max value");
        }
        return num;
    }

    public static double checkDouble(double num) throws IllegalArgumentException{
        // limit the minimum input and maximum input
        final int MAX = 10000;
        final int MIN = 100;
        if(num < 0){
            throw new IllegalArgumentException("Invalid input, it must be positive");
        }
        else if (num < MIN || num > MAX){
            throw new IllegalArgumentException("Invalid input, limit asset should set from 100 to 10000");
        }
        else if(num == 0){
            throw new IllegalArgumentException("This bank is already unsafe because of no balance");
        }

        return num;
    }

}
