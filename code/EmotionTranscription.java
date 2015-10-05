/* Detects/Scores Emotion of the Transcripted text.
 * Sarabeth Jaffe
 */

import java.io.*;
import java.lang.*;

public class EmotionTranscription {

    private static String curl_command;
    private String text;
    private double negative_score = 0;
    private double positive_score = 0;
    private double neutral_score = 0;
    private String label = "";
    private int num_sentences;

    public EmotionTranscription(String t) {

        //System.out.println("Inside of Emotion Transliteration.");
        text = t;

        curl_command = "curl -d \"text=";
        curl_command += text;
        curl_command += "\" http://text-processing.com/api/sentiment/";
        
        adjective_detection();
        punctuationCount();
        
        char [] punc = {'.', '?', '!'};
        int found = 0;
          
        for(int j = 0; j< text.length(); j++){
             for(int i = 0; i < 3; i++){
                if(text.charAt(j) == punc[i]){
                    //System.out.print(punc[i]) ;
                    found++;

                }
             }
        }
        
        num_sentences = found;
        
        sentence_count();
        
        KeywordDetection k = new KeywordDetection(text);
    }

    //returns emotionally evocative words -> words that may be of importance
   /* public String[] emotionalWords(){
        
     String[] emotionalWords;
 
     return emotionalWords;
     }*/
    
    /*public Vector<String> emotiveExpressionsFound(){
        
        
    }*/
    
    public void topicalEmotions(String topics) {

        System.out.println("\nTopical Emotions Analysis...");
        String sentiment_info = "";

        String topic_command = "curl -d \"topics=";
        topic_command += topics;
        topic_command += "&text=" + text + "\" ";
        topic_command += "https://api.repustate.com/v2/ed0587d10d42cc05b6b6f69b32287f4d9131657f/topic.json -k";

        try {

            String s = null;

            //try to run curl commands to get sentiment scoring from Text-Processing
            Process child = Runtime.getRuntime().exec(topic_command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(child.getInputStream()));

            // BufferedReader stdError = new BufferedReader(new InputStreamReader(child.getErrorStream()));

            // read the output from the command
            // System.out.println("Here is the standard output of the command:\n");
            //System.out.println();
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                sentiment_info += s;
            }

            // read any errors from the attempted command
            //System.out.println("Here is the standard error of the command (if any):\n");
            // while ((s = stdError.readLine()) != null) {
            //System.out.println(s);
            //}

            System.out.println("\nCurl command is: " + topic_command);

        } catch (Exception e) {

            System.out.println("Exception Thrown while trying to retrieve topical scoring.");

        }
    }

    //Sentiment Scoring...
    public void sentimentScoring() {

        System.out.print("\nSentiment Scoring...");
        String sentiment_info = "";

        try {

            String s = null;

            //try to run curl commands to get sentiment scoring from Text-Processing
            Process child = Runtime.getRuntime().exec(curl_command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(child.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(child.getErrorStream()));

            // read the output from the command
            // System.out.println("Here is the standard output of the command:\n");
            System.out.println();
            while ((s = stdInput.readLine()) != null) {
                //System.out.println(s);
                sentiment_info += s;
            }

            // read any errors from the attempted command
            //System.out.println("Here is the standard error of the command (if any):\n");
            // while ((s = stdError.readLine()) != null) {
            //System.out.println(s);
            //}

            //System.out.println("Curl command is: " + curl_command);

        } catch (Exception e) {

            System.out.println("Exception Thrown while trying to retrieve sentiment score.");

        }


        try {
            String[] byte_array = sentiment_info.split(" ");

            if (byte_array.length > 3) {

                String n = byte_array[2].substring(0, byte_array[2].length() - 1);
                negative_score = Double.parseDouble(n);
                System.out.println("negative score is: " + negative_score);

                String neutral = byte_array[4].substring(0, byte_array[4].length() - 1);
                neutral_score = Double.parseDouble(neutral);
                System.out.println("neutral score is: " + neutral_score);

                String positive = byte_array[6].substring(0, byte_array[6].length() - 2);
                positive_score = Double.parseDouble(positive);
                System.out.println("positive score is: " + positive_score);

                label = byte_array[8].substring(1, byte_array[8].length() - 2);
                System.out.println("Sentiment is: " + label);
            }

        } catch (Exception e) {

            System.out.println("Exception thrown while converting sentiment info into byte array.");
        }
    }
    
    public void sentence_count(){
        
        String [] s = text.split(" ");
        
        System.out.println("\nWords per sentence ratio: " + s.length + "/" + num_sentences);
    }

    //calculating punctuation/number of words ratio
    public void punctuationCount() {
        
        System.out.println("\nPunctuation Counter...");
        System.out.print("{");
        
        char [] punc = {'.', ',', ';', '?', '!', ':'};
        
        for(int i = 0; i < 6; i++){
            
            if(text.contains(""+ punc[i]))
                System.out.print(punc[i]) ;
        }
        
        System.out.print("}\n");        
    }

    //adjective detection
    public void adjective_detection() {

        System.out.println("\nAdjective Detection...");
        String adjectives = "";

        String adjective_command = "curl -d \"";
        adjective_command += "&text=" + text + "\" ";
        adjective_command += "https://api.repustate.com/v2/ed0587d10d42cc05b6b6f69b32287f4d9131657f/adj.json -k";

        try {

            String s = null;

            //try to run curl commands to get sentiment scoring from Text-Processing
            Process child = Runtime.getRuntime().exec(adjective_command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(child.getInputStream()));

            //System.out.println();
            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                adjectives += s;
            }

            System.out.println("Curl command is: " + adjective_command);

        } catch (Exception e) {

            System.out.println("Exception Thrown while trying to retrieve topical scoring.");

        }
    }

    public static void main(String[] args) {

        A_GUI g = new A_GUI();
    }
}
