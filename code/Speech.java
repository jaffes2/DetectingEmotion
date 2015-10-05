/* Sarabeth Jaffe
 * Using Moodzle Emotion API
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.Serializable;
 
import javax.net.ssl.HttpsURLConnection;

public class Speech {
   
    public String fileName;
    
    //Moodzle's servers are currently offline so output is a prototype
    public static void Moodzle(){
      
          try {

            String s = null;
            String information = null;
            String curl_command = "curl --include --request POST 'https://moodzle.p.mashape.com/voice_emotion.php' \\ --header \"X-Mashape-Authorization: nergWhO3q9xiZnJKr7TFYzoABe3hQPyw\" --filename \"angry.wav\"";

            //try to run curl command to get Speech processing information
            Process child = Runtime.getRuntime().exec(curl_command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(child.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(child.getErrorStream()));

            // read the output from the command
            // System.out.println("Here is the standard output of the command:\n");
            System.out.println();
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                information += s;
            }

             //read any errors from the attempted command
            //System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            //System.out.println("Curl command is: " + curl_command);

        } catch (Exception e) {

            System.out.println("Exception Thrown while trying to retrieve sentiment score.");

        }
    }
    
    //Prototype since Moodzle servers are down
    public static void sample_output(String audio){
        
        System.out.println("Analyzing Speech Audio file " + audio + "...");
        
        if(audio.equals("angry.wav")){
            
            System.out.println("Loudness: High");
            System.out.println("Word Tempo: Fast");
            System.out.println("Periods of Silence: Medium");
            System.out.println("Overall Pitch: High");
            System.out.println("Words/second: 27/13 = 2.076923076923077");
            
            System.out.println("Emotion Prediction: Anger");
        }
        
        else if(audio.equals("sad.wav")){
            
            System.out.println("Loudness: Low");
            System.out.println("Word Tempo: Slow");
            System.out.println("Periods of Silence: Long");
            System.out.println("Overall Pitch: Medium");
            System.out.println("Words/second: 13/11 = 1.181818181818182");
            
            System.out.println("Emotion Prediction: Sad");
        }
        
        else if(audio.equals("ecstatic.wav")){
            
            System.out.println("Loudness: High");
            System.out.println("Word Tempo: Fast");
            System.out.println("Periods of Silence: Short");
            System.out.println("Overall Pitch: High");
            System.out.println("Words/second: 9/7 = 1.285714285714286");
            
            System.out.println("Emotion Prediction: Happy");
        }
        
        else{
            System.out.println("Unknown audio file. Cannot Analyze.");
        }
    }
    
    //temporary prototype
    public String emotion(){
        
        return fileName.substring(0, fileName.length()-3);
    }
    
    public Speech(String file){
        
        fileName = file;
        sample_output(file);      
    }
    
}
