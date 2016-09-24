package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		// COMPLETE THIS METHOD
		CardNode ptr = deckRear.next;
		CardNode prev = deckRear;
		while(ptr.cardValue!=27){
			prev = ptr;
			ptr=ptr.next;
		}
		if(ptr.next == deckRear){
			prev.next = ptr.next;
			ptr.next = ptr.next.next;
			prev.next.next = ptr;
			deckRear = ptr;
		}
		else if(ptr == deckRear){
			prev.next = ptr.next;
			ptr.next = ptr.next.next;
			prev.next.next = ptr;
			deckRear = prev.next;
		}
		else{
			prev.next = ptr.next;
			ptr.next = ptr.next.next;
			prev.next.next = ptr;
		}
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
	    // COMPLETE THIS METHOD
		CardNode ptr = deckRear.next;
		CardNode prev = deckRear;
		while(ptr.cardValue!=28){
			prev = ptr;
			ptr = ptr.next;
		}
		if(ptr.next.next == deckRear){
			prev.next = ptr.next;
			ptr.next = prev.next.next.next;
			prev.next.next.next = ptr;
			deckRear = ptr;
		}
		else if(ptr.next == deckRear){
			prev.next = ptr.next;
			ptr.next = prev.next.next.next;
			prev.next.next.next = ptr;
			deckRear = prev.next.next;
		}
		else if(ptr == deckRear){
			prev.next = ptr.next;
			ptr.next = prev.next.next.next;
			prev.next.next.next = ptr;
			deckRear = prev.next;
		}
		else{
			prev.next = ptr.next;
			ptr.next = prev.next.next.next;
			prev.next.next.next = ptr;
		}
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		// COMPLETE THIS METHOD
		CardNode joker27 = deckRear.next;
		CardNode beforeJoker27 = deckRear;
		CardNode afterJoker27 = joker27.next;
		int numOfCardsBefore27 = 0;
		CardNode joker28 = deckRear.next;
		CardNode beforeJoker28 = deckRear;
		CardNode afterJoker28 = joker28.next;
		int numOfCardsBefore28 = 0;
		while(joker27.cardValue != 27){
			beforeJoker27 = joker27;
			joker27 = afterJoker27;
			afterJoker27 = joker27.next;
			numOfCardsBefore27++;
		}
		while(joker28.cardValue != 28){
			beforeJoker28 = joker28;
			joker28 = afterJoker28;
			afterJoker28 = joker28.next;
			numOfCardsBefore28++;
		}
		if(numOfCardsBefore27 == 0){
			
			deckRear = joker28;
			
		}
		else if(numOfCardsBefore28 == 0){
			deckRear = joker27;
		}
		else if(joker27 == deckRear){
			deckRear = beforeJoker28;
		}
		else if(joker28 == deckRear){
			
			deckRear = beforeJoker27;
		}
		else{
			//if 27 comes after 28
			if(numOfCardsBefore28 < numOfCardsBefore27){
				CardNode oldFrontNewAfter27 = deckRear.next;
				CardNode oldRearNewBefore28 = deckRear;
				deckRear = beforeJoker28;
				deckRear.next = afterJoker27;
				joker27.next = oldFrontNewAfter27;
				oldRearNewBefore28.next = joker28;
			}
			//if 28 comes after 27
			else{
				CardNode oldFrontNewAfter28 = deckRear.next;
				CardNode oldRearNewBefore27 = deckRear;
				deckRear = beforeJoker27;
				deckRear.next = afterJoker28;
				joker28.next = oldFrontNewAfter28;
				oldRearNewBefore27.next = joker27;
			}
			
		}
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {		
		// COMPLETE THIS METHOD
		int lastCardValue = deckRear.cardValue;
		if(lastCardValue >= 27){
			return;
		}
		CardNode originalBeforeRear = deckRear.next;
		while(originalBeforeRear.next!=deckRear){
			originalBeforeRear = originalBeforeRear.next;
		}
		CardNode firstCardToBeMoved = deckRear.next;
		CardNode lastCardToBeMoved = deckRear.next;
		for(int i = 1; i < lastCardValue; i++){
			lastCardToBeMoved = lastCardToBeMoved.next;
		}
		CardNode afterLastCardToBeMovedButNewFront = lastCardToBeMoved.next;
		if(firstCardToBeMoved == lastCardToBeMoved){
			originalBeforeRear.next = firstCardToBeMoved;
			firstCardToBeMoved.next = deckRear;
			deckRear.next = afterLastCardToBeMovedButNewFront;
		}
		else{
			originalBeforeRear.next = firstCardToBeMoved;
			lastCardToBeMoved.next = deckRear;
			deckRear.next = afterLastCardToBeMovedButNewFront;
		}
		
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		
		/*CardNode ptr = deckRear.next;
		int firstCardValue = deckRear.next.cardValue;
		if(firstCardValue > 27){
			firstCardValue = 27;
		}
		
		for(int i = 1; i < firstCardValue; i++){
			ptr = ptr.next;
		}*/
		int key = 0;
		do{
			jokerA();
			jokerB();
			tripleCut();
			countCut();
			CardNode ptr = deckRear.next;
			int firstCardValue = deckRear.next.cardValue;
			if(firstCardValue > 27){
				firstCardValue = 27;
			}
			for(int i = 1; i < firstCardValue; i++){
				ptr = ptr.next;
			}
			key = ptr.next.cardValue;
			
			
		}while(key >= 27);
		return key;
		//return ptr.next.cardValue;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		String encrypted = "";
		for(int i = 0; i < message.length();i++){
			if(Character.isLetter(message.charAt(i))){
				int key = getKey();
				/*int key = 0;
				do{
					jokerA();
					jokerB();
					tripleCut();
					countCut();
					key = getKey();
					
				}while(key >= 27);*/
				
				int keyPlusAlphabetPos = key + Character.toUpperCase(message.charAt(i)) - 'A' + 1;
				
				if(keyPlusAlphabetPos > 26){
					keyPlusAlphabetPos -= 26;
				}
				
				char ch = (char) ('A' + keyPlusAlphabetPos - 1);
				encrypted = encrypted+ch;
			}
		}
	    return encrypted;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		String decrypted = "";
		for(int i = 0; i < message.length();i++){
			if(Character.isLetter(message.charAt(i))){
				int key = getKey();
				/*int key = 0;
				do{
					jokerA();
					jokerB();
					tripleCut();
					countCut();
					key = getKey();
					
				}while(key >= 27);*/
				int alphabetPosMinusKey = 0;
				if(Character.toUpperCase(message.charAt(i)) - 'A' + 1 <= key){
					alphabetPosMinusKey = Character.toUpperCase(message.charAt(i)) - 'A' + 1 - key + 26;
				}
				else{
					alphabetPosMinusKey = Character.toUpperCase(message.charAt(i)) - 'A' + 1 - key;
				}
				char ch = (char) ('A' + alphabetPosMinusKey - 1);
				decrypted = decrypted + ch;
			}
		}
	    return decrypted;
	    
	}
}