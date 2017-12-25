package code;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

public class Deck {
	
	private ArrayList<Card> deckList=new ArrayList<Card>();
	
	public Deck() {
		createDeck();
	}
	
	public void createDeck(){
		for(int i=1; i<5; i++){
			for (int j=1; j<14; j++){
				// first trying to load the image
				URL imgURL = this.getClass().getResource("cards/"+j+getSuitChar(i)+".gif");
				deckList.add(new Card(j,i, getCardImage(imgURL)));
			}
		}
		
	}
	
	private BufferedImage getCardImage(URL path) {
		
		BufferedImage img ;
		try {
		    img = ImageIO.read((path));
		} catch (IOException e) {
			System.err.println("Couldn't find file "+ path);
			img = null;
		}
		
		
		return img;
	}
	
	private String getSuitChar(int i){
		if(i==1){
			return "s";
		} else if (i == 2){
			return "c";
		} else if(i ==3){
			return "h";
		}else{
			return "d";
		}
	}
	
	public ArrayList<Card> getDeckList(){
		return deckList;
	}
	
	public void shuffleDeck(){
		Collections.shuffle(deckList); //found randomize method here http://java2novice.com/java-collections-and-util/arraylist/shuffle/
	}
	
	public static void main(String[]a){
		Deck deck = new Deck();
		System.out.println(deck.getDeckList());
	}

}
