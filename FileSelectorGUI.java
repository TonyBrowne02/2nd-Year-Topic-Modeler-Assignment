import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class FileSelectorGUI extends JFrame implements ActionListener
{
    private JTextField tfFilePath;
    private JPanel panel;
    private JPanel rightPanel;
    private JPanel rightBottomPanel;
    private JPanel rightTopPanel;
    private JLabel inputLabel;
    private JLabel overlapLabel;
    private JPanel inputPanel;
    private JButton selectButton;
    private JTextField tfFilePath2;
    private JLabel inputLabel2;
    private JPanel inputPanel2;
    private JButton selectButton2;
    private JButton runButton;
    private JTextArea outputArea;
    private JTextArea banArea;
    private JTextField tfOverlap;
    private JLabel banLabel;
    private JScrollPane scrollPane;

    public FileSelectorGUI()
    {
        super("Topic Modeller");//setting title

        setSize(600,600);//size...
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//center the window

        //Left panel
        panel = new JPanel(new BorderLayout());//borderlayout has no space between component by default, plus looks okay
            //first input
        inputLabel = new JLabel("File Path 1:");
        inputPanel = new JPanel(new BorderLayout());
        selectButton = new JButton("Select File 1");
        tfFilePath = new JTextField(40);

            //adding first input elements
        inputPanel.add(inputLabel, BorderLayout.WEST);
        inputPanel.add(tfFilePath, BorderLayout.CENTER);
        inputPanel.add(selectButton, BorderLayout.EAST);
        panel.add(inputPanel, BorderLayout.NORTH);
        
            //second input
        inputLabel2 = new JLabel("File Path 2:");
        inputPanel2 = new JPanel(new BorderLayout());
        selectButton2 = new JButton("Select File");
        tfFilePath2 = new JTextField(40);
        tfOverlap = new JTextField(10);

            //adding second input elements
        inputPanel2.add(inputLabel2, BorderLayout.WEST);
        inputPanel2.add(tfFilePath2, BorderLayout.CENTER);
        inputPanel2.add(selectButton2, BorderLayout.EAST);
        panel.add(inputPanel2, BorderLayout.SOUTH);

            //run button
        runButton = new JButton("Run!");
        runButton.setSize(30,30);
        panel.add(runButton, BorderLayout.WEST);

            //output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        panel.add(outputArea, BorderLayout.CENTER);

        
        //Right Panel
        rightPanel = new JPanel(new BorderLayout());
        rightBottomPanel = new JPanel(new BorderLayout());
        rightTopPanel = new JPanel(new BorderLayout());

            //ban words area
        banLabel = new JLabel("Banned Words:");
        banArea = new JTextArea(10,20);
        scrollPane = new JScrollPane(banArea);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        rightTopPanel.add(banLabel, BorderLayout.NORTH);
        rightPanel.add(rightTopPanel, BorderLayout.NORTH);


        banArea.setText("as\nis\nare\nfor\nthe\nof\nand\nin\nto\na\nhave\nfrom\nthat\nby\non\ntheir\nthey're\nthere\nwas\nthey\nwith");

            //overlap
        overlapLabel = new JLabel("Overlap%");
        rightBottomPanel.add(overlapLabel,BorderLayout.WEST);
        rightBottomPanel.add(tfOverlap, BorderLayout.SOUTH);
        tfOverlap.setEditable(false);
        rightPanel.add(rightBottomPanel, BorderLayout.SOUTH);

        panel.add(rightPanel, BorderLayout.EAST);

        //colours of stuff
        tfOverlap.setBackground(Color.BLUE);
        banArea.setBackground(Color.GREEN);
        tfFilePath.setBackground(Color.CYAN);
        tfFilePath2.setBackground(Color.CYAN);
        outputArea.setBackground(Color.ORANGE);


        //turning on the GUI
        setContentPane(panel);
        setVisible(true);

        //action listeners
        selectButton.addActionListener(this);
        selectButton2.addActionListener(this);
        runButton.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) 
    {   
        if( (e.getSource() == selectButton ) || (e.getSource() == selectButton2) )//File chooser
        {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            int result = fileChooser.showOpenDialog(FileSelectorGUI.this);
            if (result == JFileChooser.APPROVE_OPTION && e.getSource() == selectButton) 
            {
                File selectedFile = fileChooser.getSelectedFile();
                tfFilePath.setText(selectedFile.getAbsolutePath());
            }
            else if (result == JFileChooser.APPROVE_OPTION && e.getSource() == selectButton2)
            {
                File selectedFile = fileChooser.getSelectedFile();
                tfFilePath2.setText(selectedFile.getAbsolutePath());
            }
        }//end filechooser

        else if (e.getSource() == runButton)//Run the countWords and set banFile.txt to current text in banArea
        {
            try 
            {
                FileManager.banWords(banArea.getText());//sets banned words for current run by editing banFile.txt
            } catch (IOException e1) 
            {
                e1.printStackTrace();
            }//end try catch

            outputArea.setText("");//clear results area

            //First file
            File tempFile1 = new File(tfFilePath.getText());
            File tempFile2 =  new File(tfFilePath2.getText());

            if(tempFile1.exists() && tempFile2.exists())
            {
                outputArea.append("File Path 1 Results\n");
                List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(FileManager.countWords(tfFilePath.getText()));
                for(Map.Entry<String, Integer> currentEntry : sortedList)
                {
                    outputArea.append(currentEntry.getKey() + ": " + currentEntry.getValue() + "\n");
                }
                outputArea.append("\n");

                outputArea.append("File Path 2 Results\n");
                List<Map.Entry<String, Integer>> sortedList2 = new ArrayList<>(FileManager.countWords(tfFilePath2.getText()));
                for(Map.Entry<String, Integer> currentEntry2 : sortedList2)
                {
                    outputArea.append(currentEntry2.getKey() + ": " + currentEntry2.getValue() + "\n");
                }

                //overlap since both files exist
                Integer overlap = 0;
                
                for(Map.Entry<String, Integer> currentEntry2 : sortedList2)
                {
                    for(Map.Entry<String, Integer> currentEntry1 : sortedList)
                    {
                        if(currentEntry2.getKey().equals(currentEntry1.getKey()))
                        {
                            overlap+=10;
                            currentEntry1.setValue(currentEntry2.getValue() + currentEntry1.getValue());
                            //if there is overlap of keys then incrememnt the overlap % counter and set the value of the key in sortedList to be the sum of both entries
                            //in the two sets
                            currentEntry2.setValue(0);
                            //set the value of the key which occurs in both to 0, used in saving the output to a file later
                        }
                    }
                }
                tfOverlap.setText(overlap + "%");
                //send both lists to the FileManager.saveOutput(List1, List2, overlap ) Function
                FileManager.saveOutput(sortedList, sortedList2, overlap, tfFilePath.getText(), tfFilePath2.getText());
            }
            else if(tempFile1.exists())
            {
                //error checking OTHER file
                String filePathString = tfFilePath.getText().trim();
                if(!tempFile2.exists() && !filePathString.isEmpty())
                {
                    JOptionPane.showMessageDialog(panel,"File Path 2 Isn't valid!\nOutputting results from File Path 1, but no overlap can be found from one input");
                }//end if

                outputArea.append("File Path 1 Results\n");
                List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(FileManager.countWords(tfFilePath.getText()));
                for(Map.Entry<String, Integer> currentEntry : sortedList)
                {
                    outputArea.append(currentEntry.getKey() + ": " + currentEntry.getValue() + "\n");
                }
                outputArea.append("\n");
            }
            else if(tempFile2.exists())
            {
                //error checking OTHER file
                String filePathString = tfFilePath.getText().trim();
                if(!tempFile1.exists() && !filePathString.isEmpty());
                {
                    JOptionPane.showMessageDialog(panel,"File Path 1 Isn't valid!\nOutputting File Path 2, but no overlap can be found from one input");
                }//end if

                outputArea.append("File Path 2 Results\n");
                List<Map.Entry<String, Integer>> sortedList2 = new ArrayList<>(FileManager.countWords(tfFilePath2.getText()));
                for(Map.Entry<String, Integer> currentEntry2 : sortedList2)
                {
                    outputArea.append(currentEntry2.getKey() + ": " + currentEntry2.getValue() + "\n");
                }
            }
            else 
            {
                JOptionPane.showMessageDialog(panel, "File Path 1 and 2 are invalid!");
            }
        }//end RunButton

    }//end actionPerformed

}//end FileSelector
