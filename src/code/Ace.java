package code;

import java.util.ArrayList;
import java.util.List;

//handles ace's high game
public class Ace extends Solitaire {
	public List<Card> home=new ArrayList<Card>(); //holds the homecell pile
	
	public Ace(){
		super();
		tableau=new Card[4][13];
		tableauTopIndex = new int [4]; //s
		for (int i=0; i<4;i++){
			tableau[i][0]=gameDeck.getDeckList().remove(gameDeck.getDeckList().size()-1);
			//s
			tableauTopIndex[i]=0;
		}
	}
	
	//checks if adding from stock or tableau is legal
	public boolean add(int pileType, int inputPile, int outputPile){
		if(pileType==0){ //tests adding from stock to tableau
			if(gameDeck.getDeckList().size()>=4){
				return true;
			}
		} else { //check tableau to tableau
			if(checkTableauToTableau(inputPile,outputPile)){
				return true;
			}
		}
		return false;
	}
	
	//checks if removing from tableau to tableau or tableau to homecell is legal
	public boolean remove(int pileType, int inputPile, int outputPile){
		if(pileType==0){ //check tableau to homecell
			if(checkTableauToHomecell(inputPile)){
				return true;
			}
		} else { //check tableau to tableau
			if(checkTableauToTableau(inputPile,outputPile)){
				return true;
			}
		}
		return false;
	}
	//deals out the top 4 cards of the stock pile
	public void dealDeck(){
		if (add(0,0,0)){ //s
			for (int i=0; i<4;i++){
				if(tableau[i][tableauTopIndex[i]]!=null){
					tableauTopIndex[i]=tableauTopIndex[i]+1;
				}
				tableau[i][tableauTopIndex[i]]=gameDeck.getDeckList().remove(gameDeck.getDeckList().size()-1);
			}
		}
		
	}
	
	//moves a card from a tableau pile to a tableau pile
	public void tableauToTableau(int inputPile,int outputPile){	
		if(add(1,inputPile,outputPile)&&remove(1,inputPile,outputPile)){
			System.out.println("here");
			tableau[outputPile][tableauTopIndex[outputPile]]=tableau[inputPile][tableauTopIndex[inputPile]];
			tableau[inputPile][tableauTopIndex[inputPile]]=null;
			if(tableauTopIndex[inputPile]!=0){
				tableauTopIndex[inputPile]--;
			}
		}
	}
	
	//returns if it is legal to move from tableau to homecell
	public boolean anywhereToHomecell(){
		return true;
	}
	
	//returns if it is legal to move from homecell to anywhere
	public boolean homecellToNowhere(){
		return false;
	}
	
	//returns if it is legal to move a card to stock
	public boolean anywhereToStock(){
		return false;
	}
	
	//checks if a card can be moved from tableau to tableau
	public boolean checkTableauToTableau(int inputPile, int outputPile){
		if(tableau[inputPile][tableauTopIndex[inputPile]]!=null&& tableau[outputPile][tableauTopIndex[outputPile]]==null){
			return true;
		}
		return false;
	}
	
	//moves from tableau to homecell
	public void tabToHomecell(int inputPile){
		if(add(0,inputPile,0)&&remove(0,inputPile,0)){
			home.add(tableau[inputPile][tableauTopIndex[inputPile]]);
			tableau[inputPile][tableauTopIndex[inputPile]]=null;
			//System.out.println("here11");
			if(tableauTopIndex[inputPile]!=0){
				//System.out.println("here");
				tableauTopIndex[inputPile]--;
			}
			
		}
	}
	
	//checks if a card can be moved from tableau to homecell
	public boolean checkTableauToHomecell(int inputPile) {
		if(tableau[inputPile][tableauTopIndex[inputPile]]!=null){
			for(int i=0;i<4;i++){
				//System.out.println("tableau top index "+  tableauTopIndex[i]);
				if(tableau[i][tableauTopIndex[i]]!=null){
				if(tableau[i][tableauTopIndex[i]].getSuit()==tableau[inputPile][tableauTopIndex[inputPile]].getSuit()&&  
				(tableau[i][tableauTopIndex[i]].getRank()==1
						||tableau[i][tableauTopIndex[i]].getRank()>tableau[inputPile][tableauTopIndex[inputPile]].getRank())) {
					if(tableau[inputPile][tableauTopIndex[inputPile]].getRank()==1){
						return false;
					}
					return true;
				}
				}
			}
				
		} else {
			//System.out.println("it is null");
		}
		return false;
	}
}
