# 2nd-Year-Topic-Modeler-Assignment
Repository for a top modeler assignment given during second year term 2 of Computer Science: Infrastructure in TU Dublin 

Topic modeller, created a program to display a GUI which prompts the user to select the location of two .txt files after which the program compares the top ten most common words of each file, excluding non-content words such as “the, in, at, the”. Once the two sets of words are found the percentage overlap is shown to the user. The program begins with an initial set of non-content words to ignore but there is functionality for the user to add and remove buffer words to/from the list. These changes are saved statically between launches of the program.

o List of classes, with a description. 
  FileManager.java, this class is used when interacting with files, used to permform a variety of functions 
  
    public static List<Map.Entry<String, Integer>> countWords(String file)
      this takes in a txt file location in the form of a string and returns a list of the top 10 most occuring words, excluding stop words.
    
    public static void banWords(String wordsToBan)
      this takes in a String of words to ban, in the form "word1\nword2\nword3"
      this is retrieved from the user within the gui and words are banned upon the running of the program from the GUI
      
    public static void defaultBans()
      this provides the program with some preset stop words to not count
      
    public static void saveOutput(List<Map.Entry<String, Integer> > sortedList, List<Map.Entry<String, Integer> > sortedList2, Integer overlap, String file1, String file2)
      this takes in the List of sorted words from the two file locations, the %overlap in the form of an integer and the file locations that were used for the wordCount() function
   
   
   FileSelectorGUI.java, this class is used to display the GUI and calls to the FileManager.java class to interact with files
   
   public FileSelectorGUI()
     This sets up the GUI with various textAreas, textFields, Buttons, and a file selector
     
     public void actionPerformed(ActionEvent e) 
       this decides what to do based on how the user is interacting with the GUI, opening a file selector window or running the program to count words
    
    App.java, this class simply calls the FileSelectorGUI.java to start the program
    
o Description of the core functionality you included;
This tool will allow a user to detect whether a set of documents are “about” the same topic or not. 
The tool will analyse the words in each document – and decide what the most common words are in each document. 
“Stop” words are excluded from the analysis (this is the list of words that don’t really add meaning which are not included in the similarity measure – such as “the”, “a”, “of”, etc...), these can be added within the banned words text area in the GUI
Basic on the overlap in the top X words (e.g. 10), a grade of likelihood of it being about the same topic can be produced (e.g. 70% overlap in the top ten words = 70% likely to be about the same topic). Likewise, documents with small overlap on most comment words (e.g. < 40%) are definitely different topics.

o Description/list of the optional functionality you included. 
Worked on the GUI, making it look better than a very basic GUI, added colour along with various panels, textfields, textAreas, and buttons
Implemented a file chooser GUI rather than just console input for file location
Allowed stop words to be added or removed easily using a text area
Results are displayed to a central text area, including the word and how often it occurs
The results, files selected, overlap% are saved persistently to a file called SavedOutput.txt, each run of the program adds its information to the end of this file

o Explain what you would add if you had more time. 
  check for whitespace after/before a banned word from the text area
