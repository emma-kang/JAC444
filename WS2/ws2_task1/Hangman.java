
package ws2_task1;
import java.util.Scanner;

public class Hangman {
    String[] words;
    String picked;
    String hidden;
    int count; // to count the miss time

    public Hangman(){ }
    public Hangman(String[] words){
        this.words = words;
    }
    public Hangman(String text){
        this.words = text.split("\\s");
    }

    // Randomly pick word from words list
    public void setPickedWord(String[] w){
        // generate random index using Math.random method
        int index = (int)(Math.random()*w.length);
        picked = w[index];
    }

    public void setHiddenWord(){
        hidden = ""; // initialize the hidden word as empty string
        if(picked != null){
            for(int i=0; i<picked.length(); i++){
                this.hidden += "*"; // add asterisk to the hidden as much as picked word's length
            }
        }
        else {
            System.out.println("There is no selected word.");
        }
    }

    // compare user input with pick words
    public void guessWord(String p, String h, char c){

        StringBuilder hiddenWord = new StringBuilder(h);

        if(!existWord(h, c)){
            for(int i = 0; i < p.length(); i++) {
                if (c == p.charAt(i) && h.charAt(i) == '*') {
                    hiddenWord = hiddenWord.deleteCharAt(i);
                    hiddenWord = hiddenWord.insert(i, c);
                    hidden = hiddenWord.toString();
                }
            }
        }

        if(!existWord(p, c)){
            System.out.println(c + " is not in the word");
            count++;
        }
        else if(existWord(h, c)){
            System.out.println(c + " is already in the word");
        }

    }

    public boolean existWord(String str, char c){
        for(int i = 0; i < str.length(); i++){
            if(c == str.charAt((i)))
                return true;
        }
        return false;
    }

    // play hangman game
    public void playGame(){
        setPickedWord(words);
        setHiddenWord();

        // Create Scanner object
        Scanner input = new Scanner(System.in);
        char ch;

        do{
            //System.out.println(picked);
            //System.out.println(hidden);
            System.out.print("(Guess) Enter a letter in word " + hidden + " > ");
            ch = input.next().charAt(0);

            if(Character.isLetter(ch) == true){
                ch = Character.toLowerCase(ch);
                guessWord(picked, hidden, ch);
            }
            else if(Character.isLetter(ch) == false){
                System.out.println("Invalid Input, Please type a letter");
            }
        }while(!picked.equals(hidden));

        System.out.print("The word is " + picked);
        System.out.print(" You missed " + count + " time\n");

    }

}
