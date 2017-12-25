package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import code.Ace;
import code.Card;
import code.Deck;
import code.Solitaire;


public class AceTest {
	
	@Test
	public void tabInitialCards(){
		Ace ace = new Ace();
		int counter = 0;
		int counter2 = 0;
		for(int i = 0; i < ace.tableauTopIndex.length; i++) {
			if(ace.tableauTopIndex[i] == 0) {
				counter++;
				
			}
			if(ace.tableau[i][0] != null) {
				counter2++;
			}
		}
		assertEquals(4, counter);
		assertEquals(4, counter2);
	}
	
	@Test
	public void tabDetermineAddLegal(){
		Ace ace = new Ace();
		assertTrue(ace.add(0,0,0));
		while(ace.gameDeck.getDeckList().size()>1){
			ace.gameDeck.getDeckList().remove(0);
		}
		assertFalse(ace.add(0, 0, 0));
		assertFalse(ace.add(1, 0, 1));
		ace.tableau[1][0]=null;
		assertTrue(ace.add(1, 2, 1));
	}
	
	@Test
	public void tabDetermineRemoveLegal(){
		Ace ace = new Ace();
		Card a2 = new Card(2,1,null);
		Card b2 = new Card(2,2,null);
		Card c2 = new Card(2,3,null);
		Card d2 = new Card(2,4,null);
		Card d13 = new Card(13,4,null);
		ace.tableau[0][0]=a2;
		ace.tableau[1][0]=b2;
		ace.tableau[2][0]=c2;
		ace.tableau[3][0]=d2;
		assertFalse(ace.remove(0,0,0));
		ace.tableau[0][0]=d13;
		assertTrue(ace.remove(0, 3, 0));
		assertFalse(ace.remove(1, 0, 1));
		ace.tableau[0][0]=null;
		assertTrue(ace.remove(1, 3, 0));
	}
	
	@Test
	public void addTabIncreaseCards(){
		Ace ace = new Ace();
		assertEquals(0,ace.tableauTopIndex[0]);
		assertNull(ace.tableau[0][1]);
		Card store = ace.gameDeck.getDeckList().get(ace.gameDeck.getDeckList().size()-1);
		ace.dealDeck();
		assertEquals(store,ace.tableau[0][1]);
		assertEquals(1,ace.tableauTopIndex[0]);
		assertNotNull(ace.tableau[0][1]);
		ace.tableau[1][0]=null;
		ace.tableau[1][1]=null;
		ace.tableauTopIndex[1]=0;
		ace.tableauToTableau(0, 1);
		assertEquals(store,ace.tableau[1][0]);
	}
	
	@Test
	public void removeTabDecreaseCards(){
		Ace ace = new Ace();
		Card a2 = new Card(2,1,null);
		Card b2 = new Card(2,2,null);
		Card c2 = new Card(2,3,null);
		Card d2 = new Card(2,4,null);
		Card d13 = new Card(13,4,null);
		ace.tableau[0][0]=a2;
		ace.tableau[1][0]=b2;
		ace.tableau[2][0]=c2;
		ace.tableau[2][1]=d2;
		ace.tableau[3][0]=d13;
		ace.tableauTopIndex[2]=1;
		ace.tabToHomecell(2);
		assertEquals(0,ace.tableauTopIndex[2]);
		assertEquals(c2,ace.tableau[2][ace.tableauTopIndex[2]]);
	}
	
	@Test
	public void emptyHome(){
		Ace ace = new Ace();
		assertEquals(0, ace.home.size());
	}
	
	@Test
	public void addToHomecell(){
		Ace ace = new Ace();
		assertEquals(true,ace.anywhereToHomecell());
	}
	
	@Test
	public void removeFromHomecell(){
		Ace ace = new Ace();
		assertEquals(false,ace.homecellToNowhere());
	}
	
	@Test
	public void addHomecellIncreaseCards(){
		Ace ace = new Ace();
		Card a2 = new Card(2,1,null);
		Card b2 = new Card(2,2,null);
		Card c2 = new Card(2,3,null);
		Card d2 = new Card(2,4,null);
		Card d13 = new Card(13,4,null);
		ace.tableau[0][0]=a2;
		ace.tableau[1][0]=b2;
		ace.tableau[2][0]=c2;
		ace.tableau[2][1]=d2;
		ace.tableau[3][0]=d13;
		ace.tableauTopIndex[2]=1;
		ace.tabToHomecell(2);
		assertEquals(1,ace.home.size());
		assertEquals(d2,ace.home.get(ace.home.size()-1));
	}
	
	@Test
	public void stockInitialCards(){
		Ace ace = new Ace();
		assertEquals(48,ace.gameDeck.getDeckList().size());
	}
	
	@Test
	public void stockDetermineAddLegal(){
		Ace ace = new Ace();
		assertEquals(false,ace.anywhereToStock());
	}
	
	@Test
	public void stockDetermineRemoveLegal(){
		Ace ace = new Ace();
		assertEquals(true,ace.add(0,0,0));
		while(ace.gameDeck.getDeckList().size()>1){
			ace.gameDeck.getDeckList().remove(0);
		}
		assertEquals(false,ace.add(0,0,0));
	}
	
	@Test
	public void testDealDeck() {
		Ace ace = new Ace();
		int counter = 0;
		int counter2 = 0;
		int counter3 = 0;
		int counter4 = 0;
			assertNotNull(ace.tableauTopIndex);
			assertNotNull(ace.tableau);
			assertEquals(48, ace.gameDeck.getDeckList().size());
			for(int i = 0; i < ace.tableauTopIndex.length; i++) {
				if(ace.tableauTopIndex[i] == 0) {
					counter++;
					
				}
				if(ace.tableau[i][0] != null) {
					counter2++;
				}
			}
			assertEquals(4, counter);
			assertEquals(4, counter2);
			ace.dealDeck();
		for(int i = 0; i < ace.tableauTopIndex.length; i++) {
			if(ace.tableauTopIndex[i] == 1) {
				counter3++;
			}
			if(ace.tableau[i][1] != null) {
				counter4++;
			}
		}
			assertEquals(4, counter3);
			assertEquals(4, counter4);
			assertEquals(44, ace.gameDeck.getDeckList().size());
		
	}
}