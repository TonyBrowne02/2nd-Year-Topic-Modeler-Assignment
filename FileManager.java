//retrieving banned words from initilaisiation doesn't work, 

/*
 * Author:Tony Browne
 * Date:28.03.2023 
 * Description:Class to deal with the logic of counting the 10 most common words in two files passed to the CountWords function, 
 */

import java.io.*;
import java.util.*;

public class FileManager 
{
    public static List<Map.Entry<String, Integer>> countWords(String file)
    {
        String inputLine;
        int i,j = 0;
        Set<String> bannedWords = new HashSet<>();

        //Setting banned words from file and intialising banned words with defaults even if nothing has been added
        try 
        {
            File banFile= new File("banFile.txt");
            
            if(banFile.createNewFile())
            {
                System.out.println("banFile.txt is created, this will store the banned output words for the topic modeller");
                defaultBans();
            }
            else
            {
                BufferedReader banReader = new BufferedReader(new FileReader("banFile.txt"));
                while((inputLine = banReader.readLine()) != null)
                {
                    bannedWords.add(inputLine);
                }
                banReader.close();
            }//end if else

        }//end try 
        catch (IOException e) 
        {
            System.out.println("Something wrong has happened with the banFile during wordCount() execution");
        
        }//end catch
        

        //make a hashmap mapping the key of a word to an integer value
        Map<String, Integer> wordCount = new HashMap<>();
        try 
        {
            BufferedReader wordReader = new BufferedReader(new FileReader(file));//create a new wordReader for first file
            
            while ((inputLine = wordReader.readLine()) != null) //while the next line isnt't null, i.e the end of the file hasn't been reached
            {
                String[] words = inputLine.split("[^a-zA-Z]+");//split the String by non alphabet chars 
                for (String curword : words) //for each String called curword within the array of words within words
                {
                    if (curword.length() > 1) 
                    {
                        curword = curword.toLowerCase(); // convert to a lower case
                        if(!bannedWords.contains(curword))
                        {
                            if (wordCount.containsKey(curword)) //if the current word is already mapped as a key in the hashmap
                            {
                                wordCount.put(curword, wordCount.get(curword) + 1); // incremeent the integer mapped to that key
                            } 
                            else 
                            {
                                wordCount.put(curword, 1); //add the current word as a key and set the value to 1
                            }//end if-else
                        }
                    }//end if
                }//end for each
            }//end while
            wordReader.close();
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
        return sortedList.subList(0, 10);
    }//end countWords(String)

    public static void banWords(String wordsToBan) throws IOException
    {
        File banFile= new File("banFile.txt");
        try (BufferedWriter banWriter = new BufferedWriter(new FileWriter("banFile.txt"))) 
        {
            if(banFile.createNewFile())
            {
                System.out.println("banFile.txt is created, this will store the banned output words for the topic modeller");
                defaultBans();//add default bans
            }
            System.out.println(wordsToBan);
            banWriter.write(wordsToBan);
            banWriter.close();
        }//end try

    }//end banWords(String)

    public static void defaultBans()
    {
        String[] defaultBans = {"as", "is", "are", "for", "the", "of", "and", "in", "to", "a", "have", "from", "that", "by", "on", "their", "they're", "there", "was", "they", "with"};
        int i;

        try (BufferedWriter banWriter = new BufferedWriter(new FileWriter("banFile.txt"))) 
        {
            for(i=0; i<defaultBans.length; i++)
            {
                banWriter.append(defaultBans[i] + "\n");
            }
            banWriter.close();
        } catch (IOException e) 
        {
            System.out.println("Error executing defaultBans()");
            e.printStackTrace();
        }//end try catch

    }//end defaultBans

}//end file manager