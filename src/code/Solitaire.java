package code;
//contains sharable fields, constructor, and methods
public class Solitaire { //class declaration
	 public Card tableau[][]; //2d array, with the first dimension corresponding to a certain tableau pile, the second dimension corresponding to a card's position in that pile. with [x][0] being at the bottom
	 public Card homecell[][]; //2d array, with the first dimension corresponding to a certain homecell pile, the second dimension corresponding to a card's position in that pile. with [x][0] being at the bottom
	 public Card freecell[]; //array with each index corresponding to a freecell pile
	 public int tableauTopIndex[]; //holds the top index of each tableau pile
	 public int homecellTopIndex[]; //holds the top index of each homecell pile
	 boolean victory; //holds the value of whether the game is won yet, false if the game is not won yet, true if game is won
	 public Deck gameDeck; //holds the Deck class instance to be used
	
	 public Solitaire() { //constructor
	 gameDeck = new Deck(); //instantiate gameDeck instance
	 gameDeck.shuffleDeck(); //shuffle gameDeck instance
	 homecell = new Card[4][13]; //instantiate homecell array
	 victory=false;
	 homecellTopIndex=new int[4];
	 //assigns homecellTopIndex to be 0 for all indices
	 	for(int i=0;i<4;i++) {
	 		homecellTopIndex[i]=0;
	 	}
	 }
	 
	 
	 /*responsible for behavior that moves top card from a tableau pile to homecell pile, only if the card's rank is 1 higher and the same suit as the card on top of homecell or if the homecell is 
	  * empty and the input card is an ace
	  * @params
	  * inputPile corresponds to which tableau pile the card on top will be taken from
	  * outputPile corresponds to which homecell pile the card on top will put on top of
	  */
	 public void tableauToHomecell(int inputPile, int outputPile) {
		 Card copy=null;
		 if(checkRemoveFromTableauToHomecell(inputPile,outputPile)) { //or checks that the input card is an ace and the output pile is empty
			 copy=tableau[inputPile][tableauTopIndex[inputPile]];
			 if(copy.getRank()==1&&homecellTopIndex[outputPile]==0) {
				 homecell[outputPile][homecellTopIndex[outputPile]]=tableau[inputPile][tableauTopIndex[inputPile]]; //assign the new card on the top of the homecell pile to be equal to the input card
			 }else {
				 homecell[outputPile][homecellTopIndex[outputPile]+1]=tableau[inputPile][tableauTopIndex[inputPile]]; //assign the new card on the top of the homecell pile to be equal to the input card
				 homecellTopIndex[outputPile]++; //increase the homecellTopIndex of the output pile by 1
			 }
			 tableau[inputPile][tableauTopIndex[inputPile]]=null; //set the old position of the input card to hold a null value
			 tableauTopIndex[inputPile]--; //reduce the tableauTopIndex of the input pile by 1
			 checkWin(); //check if the game is won
		 }
	 }
	 
	 public Boolean checkRemoveFromTableauToHomecell(int inputPile,int outputPile) {
		 if(tableau[inputPile][tableauTopIndex[inputPile]].getRank()==1&&homecellTopIndex[outputPile]==0) {
			 return true;
		 }
		 if((tableau[inputPile][tableauTopIndex[inputPile]]!=null && homecell[outputPile][homecellTopIndex[outputPile]]!=null)
			&&(tableau[inputPile][tableauTopIndex[inputPile]].getRank()==1+homecell[outputPile][homecellTopIndex[outputPile]].getRank() //checks that input card is 1 rank higher than the card on top of the homecell pile
			&&tableau[inputPile][tableauTopIndex[inputPile]].getSuit()==homecell[outputPile][homecellTopIndex[outputPile]].getSuit()//checks that the input card is of the same rank as the card on top of the homecell pile
					)){
			return true;
		}
		return false;
	}
	 
	 //checks if the game is won
	 public void checkWin() {
		 int counter=0;
		 for(int i=0; i<homecellTopIndex.length;i++){
			 if (homecellTopIndex[i]==12){
				 counter++;
			 }
		 }
			if (counter==4) { //if the tableau array is full, the game is won
				victory=true; //sets victory to true
			}
	 }
}

	
