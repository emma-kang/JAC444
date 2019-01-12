

package jac444_ws4_task1;
import java.io.*;
import java.util.*;


public class BabyNameRank {

    /** Main Method */
    public static void main(String[] args) throws Exception {

        String year;
        String gender;
        String name;

        // To check end game and valid input data
        String flag;
        boolean invalid = false;

        // Scanner object
        Scanner sc = new Scanner(System.in);

        do{
            // Year user input
            System.out.println("Enter the year: ");
            year = sc.next();
            // check the year is number or not
            if(!isNumber(year)){
                do{
                    System.out.println("Invalid input: year must be between 2001 and 2010");
                    year = sc.next();
                } while(!isNumber(year));
            }
            // check the year is in valid range
            int tempYear = Integer.parseInt(year);
            if(tempYear < 2001 || tempYear > 2010){
                do{
                    System.out.println("Invalid input: year must be between 2001 and 2010");
                    try{
                        tempYear = sc.nextInt();
                        invalid = false;
                    } catch(Exception ex){
                        sc.nextLine();
                        invalid = true;
                    }
                } while(invalid);
            }
            year = Integer.toString(tempYear);

            invalid = true; // initialize invalid flag

            // Gender user input
            System.out.println(("Enter the gender: "));
            gender = sc.next();
            if(!isString(gender)){
                do{
                    System.out.println("Invalid input: gender must be M(m) or F(f)");
                    gender = sc.next();
                } while(isString(gender));
            }

            while(invalid){
                if(gender.charAt(0) == 'm' || gender.charAt(0) == 'M'
                        || gender.charAt(0) == 'f' || gender.charAt(0) == 'F') invalid = false;
                else{
                    System.out.println("Invalid input: gender must be M(m) or F(f)");
                    gender = sc.next();
                    invalid = true;
                }
            }

            // Name user input
            System.out.println("Enter the name: ");
            name = sc.next();

            if(!isString(name)){
                do{
                    System.out.println("Invalid input: name must be a string");
                    name = sc.next();
                } while(!isString(name));
            }

            findRank(year, gender, name);

            System.out.println("Enter another inquiry (Y/N)");
            flag = sc.next();
            if(!isString(flag)){
                do{
                    System.out.println("Invalid input: user should choose Y or N");
                    flag = sc.next();
                } while(!isString(flag));
            }

        } while(flag.equalsIgnoreCase("Y"));

        System.out.println("Good Bye!");

    }

    /** FindRank method from a file */
    public static void findRank(String year, String gender, String name){
        File file = new File("babynamesranking" + year + ".txt");
        String rank = "";

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));

            while(true){ 
                // Read line from the file
                String line = br.readLine();
                // if there no line to read, loop will end
                if(line==null) break;
                else if(line.toLowerCase().contains(name.toLowerCase())){
                    // if the line has the name, store the line into another string array (line.contains())
                    // extract the rank (stored in index 0) and end the loop
                    String[] result = line.split("\\s+");
                    if(gender.equalsIgnoreCase("M") && result[1] == name)
                        rank = result[0];
                    else if(gender.equalsIgnoreCase("F") && result[3] == name)
                        rank = result[0];
                    else
                        rank = "";
                    break;
                }
            }
            // call method to display output
            displayOutput(year, gender, name, rank);

        } catch (FileNotFoundException ex){
            System.out.println("Cannot found the file" + ex);

        } catch (IOException ex){
            System.out.println("IOException generated " + ex);
        }

    }

    public static void displayOutput(String year, String gender, String name, String rank){

        // display output
        // if there's no rank for the user input
        // display message
        if(rank.equals(""))
            System.out.println("The name doesn't exist in the ranking list");
        else{
            if(gender.equals("M")){
                System.out.print("Boy name ");
            }
            else if(gender.equals("F")){
                System.out.print("Girl name ");
            }
            System.out.println(name + " is ranked #" + rank + " in year " + year);
        }

    }

    /** check invalid input */
    public static boolean isNumber(String str){
        try{
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException ex){
            return false;
        }
    }

    public static boolean isString(String str){
        try{
            Integer.parseInt(str);
            return false;
        } catch(NumberFormatException ex){
            return true;
        }
    }


}
