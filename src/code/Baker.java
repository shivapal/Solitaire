package code;

import java.util.ArrayList;
//class for Baker's Dozen which is a subclass of Solitaire
public class Baker extends Solitaire {
	
	//constructor
	public Baker() {
		super();//call the Solitaire constructor
		tableau = new Card[13][13];//assigns the tableau array to have 13 piles of 13 cards each
		dealDeck(); //assign each element in the tableau array to a card in gameDeck
		checkKing();
	}
	
	//assigns cards in tableau to cards in gameDeck
	public void dealDeck() {
		int counter=0; //counts how many cards in the deck have been dealt
		tableauTopIndex= new int [13]; //instantiate tableauTopIndex
		//for loop iterates through each tableau pile
		for (int i=0;i<13;i++) {
			tableauTopIndex[i]=3; //assign tableauTopIndex to 3 for all indices
			//for loop assigns a card to each index in a pile
			for (int j=0; j<4;j++) {
				tableau[i][j]=gameDeck.getDeckList().get(counter);//assign a card from gameDeck to an index in tableau
				counter++; //increase the cards dealt in the deck by 1
			}
		}
	}
	
	//checks if a king is in a pile in the tableau array, and if it is moves it to the bottom of the pile, and moves the other elements up
	public void checkKing() {
		//this for loop iterates through all the piles in tableau
		for(int i=0;i<13;i++) {
			ArrayList<Card> jStore= new ArrayList(); //holds king cards temporarily
			ArrayList<Card> copy = new ArrayList(); //holds non-king cards temporarily
			//this for loop iterates through all cards in a pile
			for(int j=0; j<4; j++) {
				if(tableau[i][j].getRank()==13) { //checks if a king is assigned to an index in tableau
					jStore.add(tableau[i][j]); //adds the king to jStore
				} else { //otherwise add the card to copy
					copy.add(tableau[i][j]);
				}
			} 
			if (jStore.size()>0) { //run this code if there is a king in the pile 
				for(int k=0; k<jStore.size();k++) {//run this code for each king in the pile
					tableau[i][k]=jStore.get(k); //assign the bottom positions of a pile to kings
				}
				for(int n=0; n<copy.size();n++) { //run this code for each card that isn't a king in a pile
					tableau[i][n+jStore.size()]=copy.get(n); //move non-king cards up in a pile
				}
			}
		}
	}
	
	/*responsible for behavior that moves top card from a tableau pile to another tableau pile, only if the card's rank is 1 lower than the card on top of tableau
	  * @params
	  * inputPile corresponds to which tableau pile the card on top will be taken from
	  * outputPile corresponds to which tableau pile the card on top will put on top of
	  */
	public void tableauToTableau(int inputPile, int outputPile) {
		 if(checkRemoveFromTableauToTableau(inputPile, outputPile)) {//checks that the input card is of rank 1 lower than the card on top of the output pile
			 tableau[outputPile][tableauTopIndex[outputPile]+1]=tableau[inputPile][tableauTopIndex[inputPile]];//assigns the new top index of the output pile to be the input card
			 tableau[inputPile][tableauTopIndex[inputPile]]=null; //set the input card's original location to be null
			 tableauTopIndex[inputPile]--; //reduce the size of the input pile by 1
			 tableauTopIndex[outputPile]++; //increase the size of the output pile by 1
		 }
		 
	 }
	
	public Boolean checkRemoveFromTableauToTableau(int inputPile, int outputPile) {
		if(tableau[inputPile][tableauTopIndex[inputPile]]!=null
			&&tableau[inputPile][tableauTopIndex[inputPile]].getRank()==tableau[outputPile][tableauTopIndex[outputPile]].getRank()-1) {
			return true;
		}
		return false;
	}
	//sets victory to false
	public void resetGame() {
		victory=false;
	}
}
