/*
 * Author:Tony Browne
 * Date:28.03.2023 
 * Description:Class to deal with the logic of counting the 10 most common words in two files passed to the CountWords function, 
 */

import java.io.*;
import java.util.*;

public class FileManager 
{
    public List<Map.Entry<String, Integer>> CountWords(String file)
    {
        String line = "";
        int i,j = 0;
        
        //make a hashmap mapping the key of a word to an integer value
        Map<String, Integer> wordCount = new HashMap<>();
        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader(file));//create a new reader for first file
            
            while (reader.readLine() != null) //while the next line isnt't null, i.e the end of the file hasn't been reached
            {
                String[] words = line.split(" [^ a-z A-Z ]+ ");//split the String by non alphabet chars //TODO THIS DOESNT WORK
                for (String curword : words) //for each String called curword within the array of words within words
                {
                    if (curword.length() >= 1 ) 
                    {
                        curword = curword.toLowerCase(); // convert to a lower case
                        if (wordCount.containsKey(curword)) //if the current word is already mapped as a key in the hashmap
                        {
                            wordCount.put(curword, wordCount.get(curword) + 1); // incremeent the integer mapped to that key
                        } 
                        else 
                        {
                            wordCount.put(curword, 1); //add the current word as a key and set the value to 1
                        }//end if-else
                    }//end if
                }//end for each
            }//end while
            reader.close();
        }//end try
        catch (Exception e) 
        {
            e.printStackTrace();
        }//end catch


        List<Map.Entry<String, Integer> > sortedList = new ArrayList<>(wordCount.entrySet());
        //Creates a new list of map entries from the hashmap created above
        for(i=0; i<10; i++)
        {
            for(j=i+1; j<wordCount.size(); j++)
            {
                if(sortedList.get(i).getValue() < sortedList.get(j).getValue())
                {
                    Collections.swap(sortedList, i,j);
                }//end if
            }//end for
        }//end outer for
        //sorts the largest 10 elements to the start of the list, index's 0->9
        

        return sortedList;
    }
}

