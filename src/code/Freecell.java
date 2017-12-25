package code;
//class for the freecell game that is a subclass of Solitaire
public class Freecell extends Solitaire {
	//constructor
	public Freecell() {
		super();//call the superclass constructor
		tableau=new Card[8][20];//instantiate the tableau array to be have 8 piles of 20 cards long
		freecell = new Card[4];//instantiate the freecell array to have 4 piles
		//assign null to all indices in the freecell array
		for (int i=0;i<4;i++) { 
			freecell[i]=null;
		}
		dealDeck(); //assign cards to the tableau array
	}
	
	//assigns cards to the tableau array
	public void dealDeck() {
		int counter=0;//holds how many of the deck has been dealt out
		tableauTopIndex= new int [8]; //instantiate the tableauTopIndex array
		//for loop iterating over each pile in the tableau array
		for (int i=0;i<8;i++) {
			//for loop iterating over each of the bottom 6 cards of each pile
			for (int j=0; j<6;j++) {
				tableau[i][j]=gameDeck.getDeckList().get(counter); //assign position of tableau array to a card
				counter++; //increase counter to show that another card is dealt
			}
		}
		int counter2=0; //counter2 holds how many values have been assigned a top index of 6
		//while loop assigns 6 to first 4 tableauTopIndex piles, and also assigns a card to each of those top indices 
		while(counter2<4) {
			tableau[counter2][6]=gameDeck.getDeckList().get(counter);//assign card to the top index of a pile
			tableauTopIndex[counter2]=6;//assign tableauTopIndex to a pile
			counter++; //increase the amount of cards dealt by 1
			counter2++; //increase the amount of tableau piles dealt 7 cards by 1
		}
		//for loop setting tableauTopIndex equal to 5 for the last 4 indices
		for(int i=0;i<4;i++) {
			tableauTopIndex[i+4]=5;//set last 4 indices of tableauTopIndex to 5
		}
	}
	
	/*responsible for behavior that moves top card from a tableau pile to another tableau pile, only if the card's rank is 1 lower and of opposite color than the card on top of tableau
	  * @params
	  * inputPile corresponds to which tableau pile the card on top will be taken from
	  * outputPile corresponds to which tableau pile the card on top will put on top of
	  */
	public void tableauToTableau(int inputPile, int outputPile) {
		 if(checkRemoveFromTableauToTableau(inputPile,outputPile)){//checks that the input card color is opposite that of the card on top of the output pile
			 tableau[outputPile][tableauTopIndex[outputPile]+1]=tableau[inputPile][tableauTopIndex[inputPile]];//assigns the new top index of the output pile to be the input card
			 tableau[inputPile][tableauTopIndex[inputPile]]=null; //set the input card's original location to be null
			 tableauTopIndex[inputPile]--; //reduce the size of the input pile by 1
			 tableauTopIndex[outputPile]++; //increase the size of the output pile by 1
		 }
	 }
	
	/*responsible for behavior that moves top card from a tableau pile to a freecell pile, only if the freecell pile is empty
	  * @params
	  * inputPile corresponds to which tableau pile the card on top will be taken from
	  * outputPile corresponds to which freecell pile the card on top will put on top of
	  */
	public void tableauToFreecell(int inputPile, int outputPile) {
		 if(addToFreecell(outputPile)) { //checks if the freecell out pile is empty
			 freecell[outputPile]=tableau[inputPile][tableauTopIndex[inputPile]];//assign the value of the freecell pile to be equal to the input card
			 tableau[inputPile][tableauTopIndex[inputPile]]=null; //set the original location of the input card to null
			 tableauTopIndex[inputPile]--; //decrease the tableauTopIndex of the input pile by 1
		 }
	 }
	
	/*responsible for behavior that moves a card from a freecell pile to the top of a tableau pile, only if the card's rank is 1 lower and of opposite color than the card on top of tableau
	  * @params
	  * inputPile corresponds to which freecell pile the card will be taken from
	  * outputPile corresponds to which tableau pile the card on top will put on top of
	  */
	public void freecellToTableau(int inputPile, int outputPile) {
		 if(checkRemoveFromFreecellToTableau(inputPile,outputPile)) { //checks that the input card color is opposite that of the card on top of the output pile
			 tableau[outputPile][tableauTopIndex[outputPile]+1]=freecell[inputPile];//assigns the new top index of the output pile to be the input card
			 freecell[inputPile]=null; //assign the freecell input pile to null
			 tableauTopIndex[outputPile]++; //increase the size of the output pile top index by 1
		 }
	 }
	
	public void freecellToHomecell(int inputPile, int outputPile) {
		Card copy=null;
		 if(checkRemoveFromFreecellToHomecell(inputPile, outputPile)) { //or checks that the input card is an ace and the output pile is empty
			 copy=freecell[inputPile];
			 if(copy.getRank()==1&&homecellTopIndex[outputPile]==0) {
				 homecell[outputPile][homecellTopIndex[outputPile]]=freecell[inputPile]; //assign the new card on the top of the homecell pile to be equal to the input card
			 }else {
				 homecell[outputPile][homecellTopIndex[outputPile]+1]=freecell[inputPile]; //assign the new card on the top of the homecell pile to be equal to the input card
				 homecellTopIndex[outputPile]++; //increase the homecellTopIndex of the output pile by 1
			 }
			 freecell[inputPile]=null; //set the old position of the input card to hold a null value
			 checkWin(); //check if the game is won
		 }
	 }
	

	
	//sets victory to false
	public void resetGame() {
		victory=false;
	}
	
	public Boolean addToFreecell(int outputPile) {
		if(freecell[outputPile]==null) {
			return true;
		}
		return false;
	}
	
	public Boolean checkRemoveFromFreecellToTableau(int inputPile,int outputPile) {
		 if(freecell[inputPile]!=null
				 && (freecell[inputPile].getRank()==tableau[outputPile][tableauTopIndex[outputPile]].getRank()-1 //checks that the input card is of rank 1 lower than the card on top of the output pile
				 && freecell[inputPile].getColor()!=tableau[outputPile][tableauTopIndex[outputPile]].getColor())){
		
			return true;
		}
		return false;
	}
	
	public Boolean checkRemoveFromFreecellToHomecell(int inputPile, int outputPile) {
		if((freecell[inputPile]!=null&&homecell[outputPile][homecellTopIndex[outputPile]]!=null)
				&&((freecell[inputPile].getRank()==1+homecell[outputPile][homecellTopIndex[outputPile]].getRank() //checks that input card is 1 rank higher than the card on top of the homecell pile
				&&freecell[inputPile].getSuit()==homecell[outputPile][homecellTopIndex[outputPile]].getSuit())//checks that the input card is of the same rank as the card on top of the homecell pile
				|| (freecell[inputPile].getRank()==1&&homecellTopIndex[outputPile]==0))) {
			
			return true;
		}
		return false;
	}
	
	public Boolean checkRemoveFromTableauToTableau(int inputPile, int outputPile) {
		if(tableau[inputPile][tableauTopIndex[inputPile]]!=null
				&&(tableau[inputPile][tableauTopIndex[inputPile]].getRank()==tableau[outputPile][tableauTopIndex[outputPile]].getRank()-1 //checks that the input card is of rank 1 lower than the card on top of the output pile
				&&tableau[inputPile][tableauTopIndex[inputPile]].getColor()!=tableau[outputPile][tableauTopIndex[outputPile]].getColor())) {
			return true;
		}
		return false;
	}

}
