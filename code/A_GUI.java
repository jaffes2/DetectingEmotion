/* A_GUI for Emotion Detection NLP w/ Watson
 * Sarabeth Jaffe
 */

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.io.*;

public class A_GUI {

    private JFileChooser fc;
    private JTextArea text;
    private JTextField textBox_for_Topics;
    private JTextArea output;
    private JFileChooser chooser;

    public static void main(String[] args) {

        A_GUI g = new A_GUI();
        g.redirectSystemStreams(); // redirect output away from the console
    }

    public A_GUI() {

        JFrame guiFrame = new JFrame();

        Container contentPane = guiFrame.getContentPane();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        //make the program exit when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Sarabeth's NLP GUI");
        guiFrame.setSize(600, 600);

        JPanel displayPanel = new JPanel();
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(6, 2));
        displayPanel.setLayout(new FlowLayout());

        JLabel audioFile = new JLabel("Audio File:");
        JLabel textBox = new JLabel("Transcribed Text:");

        JLabel topics = new JLabel("Topics: ");
        textBox_for_Topics = new JTextField(30);
        text = new JTextArea(10, 30);

        JButton selectAudio = new JButton("Select Audio File");
        //Add action listener to button
        selectAudio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
                System.out.println("You clicked the Select Audio Button");

                chooser = new JFileChooser();

                int returnVal = chooser.showOpenDialog(new JPanel());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this file: "
                            + chooser.getSelectedFile().getName());
                }
            }
        });

        JButton submitText = new JButton("Analyze Text");
        //Add action listener to button
        submitText.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
                System.out.println("Analyzing Text...");
                //System.out.println("Text contains: " + text.getText());
                if (text.getText() != null && !text.getText().equals("")) {
                    EmotionTranscription text_trans = new EmotionTranscription(text.getText());
                    text_trans.sentimentScoring();
                    clearTextArea();

                    //if they specify topics, we can analyze their emotions related to a certain topic
                    if (!textBox_for_Topics.getText().equals("")) {

                        //give it the topics
                        text_trans.topicalEmotions(textBox_for_Topics.getText());
                    }
                } else {
                    System.out.print("Text Field is blank!");
                }
            }
        });

        JButton analyze = new JButton("Analyze for Emotion");
        //Add action listener to button
        analyze.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
                System.out.println("Analyzing Text and Audio...");

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                
                //System.out.println("Text contains: " + text.getText());
                if (text.getText() != null && !text.getText().equals("") && !chooser.getSelectedFile().getName().equals("")) {

                    System.out.println("Analyzing Text...");
                    System.out.println("------------------------");
                    EmotionTranscription text_trans = new EmotionTranscription(text.getText());
                    text_trans.sentimentScoring();
                    clearTextArea();

                    //if they specify topics, we can analyze their emotions related to a certain topic
                    if (!textBox_for_Topics.getText().equals("")) {

                        //give it the topics
                        text_trans.topicalEmotions(textBox_for_Topics.getText());
                    }

                    System.out.println();
                    System.out.println("Analyzing Speech...");
                    System.out.println("------------------------");
                    Speech s = new Speech(chooser.getSelectedFile().getName());

                    //do analyses/comparisons of text and speech findings...then output overall result
                    System.out.println();
                    System.out.println("Overall Emotion is: " + s.emotion());

                } else {

                    System.out.print("Text Field or Audio field is blank! Cannot Analyze");
                }
            }
        });


        JLabel output_label = new JLabel("Output: ");

        output = new JTextArea(16, 40); //output area
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        layout.putConstraint(SpringLayout.EAST, selectAudio, 200, SpringLayout.EAST, audioFile);
        contentPane.add(audioFile);
        contentPane.add(selectAudio);

        JScrollPane scrolling = new JScrollPane(text);
        text.setEditable(true);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);

        scrolling.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        layout.putConstraint(SpringLayout.SOUTH, scrolling, 250, SpringLayout.SOUTH, selectAudio);

        layout.putConstraint(SpringLayout.EAST, scrolling, 390, SpringLayout.EAST, textBox);

        //layout.putConstraint(SpringLayout.WEST, text, 200, SpringLayout.WEST, textBox);
        //layout.putConstraint(SpringLayout.SOUTH, text, 60, SpringLayout.SOUTH, selectAudio);
        layout.putConstraint(SpringLayout.SOUTH, textBox, 100, SpringLayout.SOUTH, selectAudio);

        contentPane.add(textBox);
        // contentPane.add(text);
        contentPane.add(scrolling);


        layout.putConstraint(SpringLayout.SOUTH, topics, 60, SpringLayout.SOUTH, scrolling);
        layout.putConstraint(SpringLayout.SOUTH, textBox_for_Topics, 60, SpringLayout.SOUTH, scrolling);
        layout.putConstraint(SpringLayout.WEST, textBox_for_Topics, 200, SpringLayout.WEST, topics);

        contentPane.add(topics);
        contentPane.add(textBox_for_Topics);

        layout.putConstraint(SpringLayout.SOUTH, submitText, 60, SpringLayout.SOUTH, topics);
        layout.putConstraint(SpringLayout.SOUTH, analyze, 60, SpringLayout.SOUTH, topics);
        layout.putConstraint(SpringLayout.WEST, analyze, 200, SpringLayout.WEST, submitText);

        contentPane.add(submitText);
        contentPane.add(analyze);

        layout.putConstraint(SpringLayout.SOUTH, output_label, 60, SpringLayout.SOUTH, submitText);
        //layout.putConstraint(SpringLayout.SOUTH, output, 400, SpringLayout.SOUTH, submitText);
        //layout.putConstraint(SpringLayout.WEST, output, 50, SpringLayout.WEST, contentPane);

        contentPane.add(output_label);

        JScrollPane scroll = new JScrollPane(output);
        output.setEditable(false);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        layout.putConstraint(SpringLayout.SOUTH, scroll, 325, SpringLayout.SOUTH, submitText);

        contentPane.add(scroll);

        //listPanel.setVisible(true);
        //j.setVisible(true);

        // guiFrame.add(listPanel, BorderLayout.CENTER);
        // guiFrame.add(displayPanel, BorderLayout.PAGE_END);
        // guiFrame.add(j);

        //guiFrame.pack();

        guiFrame.setVisible(true);
    }

    private void clearTextArea() {

        output.setText("");
    }

    //redirecting output to the textbox area
    private void updateTextArea(final String text) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                output.append(text);
            }
        });
    }

    //redirecting output stream
    private void redirectSystemStreams() {

        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                updateTextArea(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                updateTextArea(new String(b, off, len));
            }

            @Override
            public void write(byte[] b) throws IOException {
                write(b, 0, b.length);
            }
        };

        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
}