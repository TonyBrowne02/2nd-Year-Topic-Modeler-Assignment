//TODO add a banned words area to add more words 


import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class FileSelectorGUI extends JFrame implements ActionListener{
    private JTextField tfFilePath;
    private JPanel panel;
    private JLabel inputLabel;
    private JPanel inputPanel;
    private JButton confirmButton;
    private JTextField tfFilePath2;
    private JLabel inputLabel2;
    private JPanel inputPanel2;
    private JButton confirmButton2;
    private JButton runButton;
    private JTextArea outputArea;

    public FileSelectorGUI()
    {
        super("Text File Selector");//setting title

        setSize(600,600);//size...
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//center the window

        panel = new JPanel(new BorderLayout());//borderlayout has no space between component by default, plus looks okay

        //first input
        inputLabel = new JLabel("File Path 1:");
        inputPanel = new JPanel(new BorderLayout());
        confirmButton = new JButton("Select File 1");
        tfFilePath = new JTextField(40);

            //adding first input elements
        inputPanel.add(inputLabel, BorderLayout.WEST);
        inputPanel.add(confirmButton, BorderLayout.EAST);
        inputPanel.add(tfFilePath, BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.NORTH);
        
        //second input
        inputLabel2 = new JLabel("File Path 2:");
        inputPanel2 = new JPanel(new BorderLayout());
        confirmButton2 = new JButton("Select File");
        tfFilePath2 = new JTextField(40);
            //adding second input elements
        inputPanel2.add(inputLabel2, BorderLayout.WEST);
        inputPanel2.add(confirmButton2, BorderLayout.EAST);
        inputPanel2.add(tfFilePath2, BorderLayout.CENTER);
        panel.add(inputPanel2, BorderLayout.SOUTH);

        //run button
        runButton = new JButton("Run!");
        runButton.setSize(30,30);
        panel.add(runButton, BorderLayout.WEST);

        //output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        panel.add(outputArea, BorderLayout.CENTER);

        //turning on the GUI
        setContentPane(panel);
        setVisible(true);


        //action listeners
        confirmButton.addActionListener(this);
        confirmButton2.addActionListener(this);
        runButton.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) 
    {   
        //File chooser
        if( (e.getSource() == confirmButton ) || (e.getSource() == confirmButton2) )
        {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            int result = fileChooser.showOpenDialog(FileSelectorGUI.this);
            if (result == JFileChooser.APPROVE_OPTION && e.getSource() == confirmButton) 
            {
                File selectedFile = fileChooser.getSelectedFile();
                tfFilePath.setText(selectedFile.getAbsolutePath());
            }
            else if (result == JFileChooser.APPROVE_OPTION && e.getSource() == confirmButton2)
            {
                File selectedFile = fileChooser.getSelectedFile();
                tfFilePath2.setText(selectedFile.getAbsolutePath());
            }
        }
        //Run the countWords
       else if (e.getSource() == runButton)
       {
        outputArea.setText("");

        File tempFile1 = new File(tfFilePath.getText());
        if(tempFile1.exists())
        {
            outputArea.append("File Path 1 Results\n");
            List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(FileManager.countWords(tfFilePath.getText()));
            for(Map.Entry<String, Integer> currentEntry : sortedList)
            {
                outputArea.append(currentEntry.getKey() + ":" + currentEntry.getValue() + "\n");
            }
            outputArea.append("\n");
        }
        else
        {
            //TODO popout letting use their file doesn't exist
        }

        File tempFile2 =  new File(tfFilePath2.getText());
        if(tempFile2.exists())
        {
            outputArea.append("File Path 2 Results\n");
            List<Map.Entry<String, Integer>> sortedList2 = new ArrayList<>(FileManager.countWords(tfFilePath2.getText()));
            for(Map.Entry<String, Integer> currentEntry2 : sortedList2)
            {
                outputArea.append(currentEntry2.getKey() + ":" + currentEntry2.getValue() + "\n");
            }
        }
        else
        {

        }
       }//TODO else let the user know the second file is not valid
    }
}
