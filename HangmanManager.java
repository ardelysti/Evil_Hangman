import java.util.*;

/**
 * Class HangmanManager is the cause of corruption of the original
 * hangman game. It alters the words chosen by the game everytime a
 * guess is placed so that the player will always lose.
 *
 * @author Pande N Ardelysti Kardi
 * @version 10/20/2018
 */
public class HangmanManager
{
   private Set<String> wordsSet;
   private int length;
   private int max;
   private SortedSet<Character> charsSet;
   private String displayPattern;
   
   /**
    * This is the constructor for class HangmanManager that sets the
    * values to their respective class fields required to begin the game.
    * 
    * @param dictionary the list containing all the words from given 
    * dictionary.
    * @param length the length of word to narrow the word list.
    * @param max the max number of attemps.
    */
   public HangmanManager(List<String> dictionary, int length, int max)
   {
      wordsSet = new TreeSet<>();
      charsSet = new TreeSet<>();
      
      displayPattern = "";
      
      if(length<1 || max <0)
         throw new IllegalArgumentException();
      else
      {
         this.length = length;
         this.max = max;
         
      }
      
      for(int i = 0;i < length;i++)
         displayPattern += "- ";
      
      for(int i = 0; i < dictionary.size(); i++)
      {
         if(dictionary.get(i).length() == length)
            wordsSet.add(dictionary.get(i));
      }
   }
   
   /**
    * This is the method that returns the current set of words
    * the game is using.
    *
    * @return the set of current words.
    */
   public Set<String> words()
   {
      return wordsSet; 
   }
   
   /**
    * This is the method that returns the number of guesses the
    * player has left.
    *
    * @return the number of guesses left allowed.
    */
   public int guessesLeft()
   {
      return max - charsSet.size();
   }
   
   /**
    * This is the method that returns the set of characters guessed.
    *
    * @return the character set.
    */
   public SortedSet<Character> guesses()
   {
      return charsSet;
   }
   
   /**
    * This is the method that returns the display of patterns of the
    * current mystery word.
    *
    * @return the pattern.
    */
   public String pattern()
   {
      if(!charsSet.isEmpty())
         return makePattern2();
      
      return displayPattern;
   }
   
   /**
    * This is the method that does most of the work. It records the
    * guessed letter passed by main that was entered by the player 
    * and then chooses the set of words that has the most possibilities.
    * 
    * @param guess the guessed letter.
    * @return the number of occurrences of the guessed letter.
    */
   public int record(char guess)
   {
      charsSet.add(guess);
      int occ = 0;
      Map<String,Set<String>> theMap = new TreeMap<>(makeMap(guess));
      
      int maxWordCount= -1;
      String maxWordPatt = "";
      
      for(String theKey : theMap.keySet())
      {
         if(theMap.get(theKey).size() > maxWordCount)
         {
            maxWordCount = theMap.get(theKey).size();
            maxWordPatt = theKey;
         } 
      }
      
      if(!maxWordPatt.equals(""))
         wordsSet.retainAll(theMap.get(maxWordPatt)); 
      
      for(int i = 0; i < maxWordPatt.length();i++)
      {
         if(maxWordPatt.charAt(i) == guess) {occ++;}
      }
      
      return occ;
   }
   
   /**
    * This is the method that creates the mapping from a pattern
    * containing the letter passed to sets of words having an 
    * equal pattern.
    *
    * @param guess the guessed letter.
    * @return the mapping made.
    */
   public Map<String, Set<String>> makeMap(char guess)
   {
      Map<String,Set<String>> theMap = new TreeMap<>();
      
      for(String word : wordsSet)
      {
         String patt = makePattern(word,guess);
         if(theMap.containsKey(patt))
         {
            theMap.get(patt).add(word);
         }
         else
         {
            Set<String> temp = new TreeSet<>();
            temp.add(word);
            theMap.put(patt,temp);
         }
      }
      
      return theMap;
   }
   
   /**
    * This method creates the pattern for the passed word containing
    * the passed letter.
    * 
    * @param theWord the word passed through parameters.
    * @param theChar the letter passed through parameters.
    * @return the pattern created.
    */
   public String makePattern(String theWord, char theChar)
   {
      String patt = "";
      for(int i = 0;i < theWord.length();i++)
      {
         if(theWord.charAt(i) == theChar)
         {
            patt += theChar + " ";
         }
         else
         {
            patt += "- ";
         }
      }
      
      return patt;
   }
   
   /**
    * This method creates a pattern similar to the method preceding this
    * one. However, this pattern may contain all the letters in the 
    * set of letters guessed to make a final display pattern for HangmanMain.
    *
    * @return the final pattern crea ted.
    */
   public String makePattern2()
   {
      String patt = "";
   
      for(String theWord : wordsSet)
      {
         patt = "";
         for(int i = 0;i < theWord.length();i++)
         {
            int counter = 0;
            for(char theChar : charsSet)
            {
               counter++;
               if(theWord.charAt(i) == theChar)
               {
                  patt += theChar + " ";
                  break;
               }
               else if(theWord.charAt(i) != theChar && counter == charsSet.size())
               {
                  patt += "- ";
               }
            }
         }
      }
      
      return patt;
   }
}