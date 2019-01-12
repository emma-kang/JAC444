
package jac444WS1;
import java.util.Scanner;

class Location{
    // public data field
    public double maxValue;
    public int row;
    public int col;

    // Method
    public static Location locateLargest(double[][] a){
        Location temp = new Location();

        // initialize the maxValue as the first value
        temp.maxValue = a[0][0];

        // Create a Scanner object to store array's values
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the array: ");

        // Store value into the array
        for(int i=0; i<a.length; i++){
            for(int j=0; j<a[i].length; j++){
                a[i][j] = input.nextDouble();
            }
        }

        // find maxValue and Location by comparing each value of array
        for(int i=0; i < a.length; i++){
            for(int j=0; j < a[i].length; j++){
                if(a[i][j] > temp.maxValue){
                    temp.maxValue = a[i][j];
                    temp.row = i;
                    temp.col = j;
                }
            }
        }

        return temp;
    }
}

public class task1 {
    public static void main(String[] args){
        // Create a Scanner object to get input value by user
        Scanner input = new Scanner(System.in);

        int rowNum = 0;
        int colNum = 0;
        boolean flag = false;

        do {
            try {
                // store the number of rows and cols from user input
                System.out.print("Enter the number of rows and columns in the array: ");
                rowNum = input.nextInt();
                colNum = input.nextInt();
                flag = false;
            } catch (Exception e){
                input.nextLine();
                System.out.println("Not valid input");
                flag = true;
            }
        } while(flag);

        // declare two dimensional array
        double a[][] = new double[rowNum][colNum];

        // Create Location class object and call methods of location object
        Location location = Location.locateLargest(a);

        // Result Output
        System.out.print("The location of the largest element is "
                + (int)location.maxValue + " at (" + location.row + ", " + location.col + ")");
    }
}
