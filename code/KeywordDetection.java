/* Detects emotionally revealing words within the given text
 * Sarabeth Jaffe
 */

import java.util.*;
import java.io.*;

public class KeywordDetection {

    public double joy_score;
    public double anger_score;
    public double sad_score;
    private Vector<String> joy_words = new Vector();
    private Vector<String> anger_words = new Vector();
    private Vector<String> sad_words = new Vector();
    private Vector<String> negation_words = new Vector();
    private String text;

    private Vector<String> joy_found = new Vector();
    private Vector<String> anger_found = new Vector();
    private Vector<String> sad_found = new Vector();
    private Vector<String> negation_found = new Vector();

    KeywordDetection(String t) {

        System.out.println("\nKeyword Detection...");
        text = t;

        joy_score = 0;
        anger_score = 0;
        sad_score = 0;

        loadWords(joy_words, "C:\\Users\\Sarabeth\\Documents\\NetBeansProjects\\NLPFinal\\src\\positive-words.txt");
        loadWords(anger_words, "C:\\Users\\Sarabeth\\Documents\\NetBeansProjects\\NLPFinal\\src\\negative-words.txt");
        loadWords(sad_words, "C:\\Users\\Sarabeth\\Documents\\NetBeansProjects\\NLPFinal\\src\\negative-words.txt");
        loadWords(negation_words, "C:\\Users\\Sarabeth\\Documents\\NetBeansProjects\\NLPFinal\\src\\negationwords.txt");
        
        wordsDetected("Detecting Joyful Words...", joy_words, joy_found);
        wordsDetected("Detecting Anger Words...", anger_words, anger_found);
        wordsDetected("Detecting Sad Words...", sad_words, sad_found);
        wordsDetected("Detecting Negation Words...", negation_words, negation_found);
    }

    //Remove Punctuation. This is needed for word detection.
    public void remove_punctuation(){
        
         char [] punc = {'.', ',', ';', '?', '!', ':'};
         
         for(int i = 0; i < punc.length; i++)
            text = text.replace("" + punc[i], "");
    }
    
    //Load in emotive words
    public void loadWords(Vector<String> words, String fileName) {

        //load in positive words/negative words
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
   
            String line = br.readLine();

            //System.out.println(line);

            while (line != null) {
                
               if(line != null) 
                   words.add(line);
                
                line = br.readLine();    
            }
            
            br.close();
            
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Exception thrown in loadWords");
        }
    }

    //Determine which emotive words were detected
    public Vector<String> wordsDetected(String detecting, Vector<String> words_given, Vector<String> words_detected){
        
        remove_punctuation(); // remove punctuation from text
        System.out.println(detecting);
        System.out.print("{");

        String [] s = text.split(" ");
        for(int i = 0; i < words_given.size(); i++){
            
            for(int j = 0; j < s.length; j++){
                
               // System.out.println("S[j] is: " + s[j]);
                if(s[j].equals(words_given.get(i))){
                    System.out.print(words_given.get(i) + ", ");
                    words_detected.add(words_given.get(i));
                }
            }    
        }
        
        System.out.print("}\n");
        
        return words_detected;
    }
    
}
