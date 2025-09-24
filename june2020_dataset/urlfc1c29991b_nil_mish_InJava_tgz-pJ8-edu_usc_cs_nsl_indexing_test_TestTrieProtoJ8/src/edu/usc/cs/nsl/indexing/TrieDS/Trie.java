package edu.usc.cs.nsl.indexing.TrieDS;


import java.util.*;
import java.io.*;

import edu.usc.cs.nsl.indexing.TrieDS.TrieProto.TrieNodePB;
import edu.usc.cs.nsl.indexing.TrieDS.TrieProto.TriePB;

public class Trie
{
   private TrieNode root;
   
   /**
    * Constructor
    */
   public Trie()
   {
      root = new TrieNode();
   }
   
   public Trie(TriePB tp){
	   root = new TrieNode(tp.getRoot(),null);
   }
   
   /**
    * Adds a word to the Trie (Basic Version)
    * @param word - The word to add
    * @param value - The value associated with the word
    */
   public TrieNode addWord(String word,int value)
   {
      return root.addWord(word,value,null,null,true,true);
   }

   /**
    * Adds a word to the Trie (Reference version)
    * @param word
    * @param value
    * @param reference - the Trie node it can refer to.
    * @param ref_string - The String at the reference this trie node may refer to. 
    */
   public TrieNode addReference(String word, TrieNode reference, String ref_string)
   {
      return root.addWord(word,0, reference, ref_string,true,false);
   }
   
   /**
    * Remove the word from the Trie.
    * @param The word to remove from the Trie
    * @return true if the word was present in the Trie (even if as a prefix) , false if the word was never added. 
    */
   public boolean removeWord(String word){
	   return root.removeWord(word);
   }
   
   /**
    * Checks if the word is present in the Trie or not.
    * @param word - The word to be checked.
    * @return true if the word is present as a word. false if the word was never added or if it is only a prefix for other word.
    */
   public boolean checkWordInTrie(String word){
	   return root.checkWord(word);
   }

   
   /**
    * Get the words in the Trie with the given
    * prefix
    * @param prefix
    * @return a List containing String objects containing the words in
    *         the Trie with the given prefix.
    */
   public List<WordValueStore> getWordsWithPrefix(String prefix)
   {
	   if(prefix == null) return null;
      //Find the node which represents the last letter of the prefix
      TrieNode lastNode = root;
      for (int i=0; i<prefix.length(); i++)
      {
      lastNode = lastNode.getNode(prefix.charAt(i));
      
      //If no node matches, then no words exist, return empty list
      if (lastNode == null) return new ArrayList<WordValueStore>();      
      }
      
      //Return the words which share the prefix from the last node
      List<WordValueStore> the_list = new ArrayList<WordValueStore>();
      lastNode.getWords(the_list);
      return the_list;
   }
   
   /**
    * Obtain the byte array representation for serialization
    * @return byte array with Trie as protocol buffer
    */
   
   public byte[] toProtocolBuffer(){
	   TrieNodePB rootPB = root.toTriePB();
	   TriePB.Builder tpb = TriePB.newBuilder();
	   tpb.setRoot(rootPB);
	   return tpb.build().toByteArray();
   }
   
   /**
    * Write the serialized version of the Trie to the file.
 * @throws IOException 
    */
   
   public void writeProtocolBuffer(FileOutputStream out) throws IOException{
	   TrieNodePB rootPB = root.toTriePB();
	   TriePB.Builder tpb = TriePB.newBuilder();
	   tpb.setRoot(rootPB);
	   tpb.build().writeTo(out);
   }
   
}