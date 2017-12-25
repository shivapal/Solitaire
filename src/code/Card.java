package code;

import java.awt.image.BufferedImage;

public class Card {

	private int rank; // 1 corresponds to ace, 2-10 correspond to number ranks, 11, 12, 13 respectively to jack, queen, king
	private int suit; // 1 corresponds to spades, 2 to clubs, 3 hearts, 4 to diamonds
	BufferedImage image; // imgae crrosponding to suit and rank
	
	public Card(int newRank,int newSuit, BufferedImage img){
		rank=newRank;
		suit=newSuit;
		image = img;
	}
	

	public int getRank(){
		return rank;
	}
	
	public int getSuit(){
		return suit;
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public int getColor(){
		if (suit < 3){
			return 1; //1 corresponds to black
		} else {
			return 2; //2 corresponds to red
		}
		
	}

	public String toString(){
		return ""+getRank()+" "+getSuit()+"  "+getImage();
	}
	
	}
