package ws2_task1;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        // store file name into String
        String fn = "hangman.txt";
        Hangman a = new Hangman();
        String option;
        boolean valid;
        boolean finish = false;
        boolean flag = false;
        int choice = 0;
        String[] words = {"string", "hello", "nice", "day"};
        Scanner input = new Scanner(System.in);

        do{
            System.out.println("Hangman Game");
            System.out.println("Press 1 to run game with randomly generated word.");
            System.out.println("Press 2 to run game by reading the word from the text file\n");
            do{
                try{
                    System.out.print("Enter your choice:");
                    choice = input.nextInt();
                    choice = check(choice); // call function to check the number
                    flag = false;
                } catch (Exception e){
                    input.nextLine();
                    System.out.println("Invalid input, please enter the number");
                    flag = true;
                }
            } while(flag);

            // option 2 for reading text file
            if(choice == 2){
                try (BufferedReader br = new BufferedReader(new FileReader(fn))){
                    String text;
                    while ((text = br.readLine()) != null){
                        // read all line of text file
                        a = new Hangman(text);
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            else if(choice == 1){
                a = new Hangman(words); // option 1 is using declared Array
            }

            valid = false;
            a.playGame();
            while(!valid) {
                System.out.print("Do you want to guess another word? Enter y or n>");
                option = input.next();
                // IgnoreCare means input value's case will not be matter
                if(option.equalsIgnoreCase("y")) {
                    valid = true;
                }
                else if(option.equalsIgnoreCase("n")){
                    finish = true;
                    valid = true;
                }
                else{
                    System.out.println("Invalid input, please enter y or n");
                    valid = false;
                }
            }

        } while(!finish);

        System.out.println("Have a nice day! :)");

    }

    public static int check(int num) throws IllegalArgumentException{
        if(num < 0 || num > 2) {
            throw new IllegalArgumentException("Invalid input, please enter 1, 2 or 3");
        }
        return num;
    }
}