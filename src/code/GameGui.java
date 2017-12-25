package code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagLayoutInfo;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class GameGui extends JFrame implements ActionListener, MouseListener, MouseMotionListener {

	public int counterTtoH = 0; // holds number of clicks
	// used to compare clicks
	public JLabel prevTile;
	public JLabel currnTile;
	// first screen buttons
	public JButton bakerButton;
	public JButton exitButton;
	public JButton freecellButton;
	public JButton aceupButton;
	public JButton menuButton;

	public JPanel firstScreen;
	public JLabel homeCell0;
	public JLabel homeCell1;
	public JLabel homeCell2;
	public JLabel homeCell3;
	public JLabel stockPile;
	public JLabel homePile;

	public int inputPile = 100;
	public Card prevCard;

	// status of status panel
	public String currentText = "Welcome";
	// status panel, showing errors
	public JPanel statusPanel;

	// card tiles, we are not storing blank tiles
	public JLabel[][] allTiles = new JLabel[13][13];
	public JLabel[] freeTiles = new JLabel[4];

	// types of game
	public Baker bakerGame;
	public Freecell freecellGame;
	public Ace aceGame;

	public JPanel gameBoard;
	public int gameType; // 0 for baker and 1 for freecell 2 for ace up
	Boolean firstClickFree = false; // holds if first click in a sequence is on
									// a freecell

	public GameGui() {
		startGui();
	}

	// just frame
	public void startGui() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(startGamePanel());
		setSize(1100, 900);
		setVisible(true);

	}

	// first screen choose type of game here and returns whole panel
	public JPanel startGamePanel() {

		// main panel for this method
		firstScreen = new JPanel(new BorderLayout());

		// northPart or topPart of panel
		JPanel topPanel = new JPanel(new FlowLayout());
		JLabel topLabel = new JLabel();
		topLabel.setIcon(getImage("solitaire1.jpg"));
		topLabel.setPreferredSize(new Dimension(700, 600));
		topPanel.add(topLabel);
		topPanel.setAlignmentX(CENTER_ALIGNMENT);
		topPanel.setBackground(Color.BLACK);
		// buttomPart of the panel
		JPanel buttomPanel = new JPanel(new FlowLayout());
		bakerButton = new JButton("Baker Game");
		bakerButton.setPreferredSize(new Dimension(200, 100));
		freecellButton = new JButton("Freecell Game");
		freecellButton.setPreferredSize(new Dimension(200, 100));
		aceupButton = new JButton("Ace's Up Game");
		aceupButton.setPreferredSize(new Dimension(200, 100));

		exitButton = new JButton("Exit");
		exitButton.setPreferredSize(new Dimension(200, 100));

		buttomPanel.setBackground(Color.BLACK);
		buttomPanel.add(bakerButton);
		buttomPanel.add(freecellButton);
		buttomPanel.add(aceupButton);
		buttomPanel.add(exitButton);

		firstScreen.setPreferredSize(getSize());
		firstScreen.add(topPanel, BorderLayout.CENTER);
		firstScreen.add(buttomPanel, BorderLayout.SOUTH);

		// adding actionlistener to buttons
		bakerButton.addActionListener(this);
		freecellButton.addActionListener(this);
		aceupButton.addActionListener(this);
		exitButton.addActionListener(this);
		return firstScreen;

	}

	// sets up status panel
	public void setStatusPanel() {
		statusPanel = new JPanel();
		statusPanel.setPreferredSize(new Dimension(getWidth(), 50));
		statusPanel.setBackground(Color.PINK);
		// statusPanel.setLayout(new GroupLayout(statusPanel));
		statusPanel.addMouseMotionListener(this);
		// return retPanel;
	}

	int r = 0;
	int b = 0;
	int g = 128;

	// returns homeCell panel with 4 empty cells
	public JPanel getHomeCellPanel() {
		JPanel retPanel = new JPanel();
		homeCell1 = new JLabel();
		homeCell1.setIcon(getImage("cards/gold.gif"));
		homeCell0 = new JLabel();
		homeCell0.setIcon(getImage("cards/gold.gif"));
		homeCell2 = new JLabel();
		homeCell2.setIcon(getImage("cards/gold.gif"));
		homeCell3 = new JLabel();
		homeCell3.setIcon(getImage("cards/gold.gif"));
		retPanel.setLayout(new BoxLayout(retPanel, BoxLayout.Y_AXIS));
		retPanel.setPreferredSize(new Dimension(125, getHeight()));
		retPanel.setBackground(Color.GREEN);

		homeCell0.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		homeCell1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		homeCell2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		homeCell3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		retPanel.add(homeCell0);
		retPanel.add(homeCell1);
		retPanel.add(homeCell2);
		retPanel.add(homeCell3);
		retPanel.add(getMenuButton());
		setStatusPanel();
		retPanel.add(this.statusPanel);
		return retPanel;
	}

	// returns panel containing all component of Baker's game
	public JPanel bakerGamePanel() {

		JPanel retPanel = new JPanel(new BorderLayout());

		JPanel homeCellPanel = getHomeCellPanel();
		retPanel.add(homeCellPanel, BorderLayout.EAST);

		JPanel gameBoardPanel = getBakerBoard();
		retPanel.add(gameBoardPanel, BorderLayout.CENTER);

		return retPanel;
	}

	// returns panel containing all component of freecell game
	public JPanel freeCellGamePanel() {
		JPanel retPanel = new JPanel(new BorderLayout());

		JPanel homeCellPanel = getHomeCellPanel();
		retPanel.add(homeCellPanel, BorderLayout.EAST);

		JPanel gameBoardPanel = getFreeCellBoard();
		retPanel.add(gameBoardPanel);
		return retPanel;
	}

	public JPanel aceUpGamePanel() {
		JPanel retPanel = new JPanel(new BorderLayout());

		retPanel.add(stockPilePanel(), BorderLayout.WEST);
		retPanel.add(aceBoardPanel(), BorderLayout.CENTER);
		retPanel.add(aceHomePanel(), BorderLayout.EAST);
		setStatusPanel();
		retPanel.add(statusPanel, BorderLayout.SOUTH);
		return retPanel;
	}

	// aceUp game stock pile panel
	public JPanel stockPilePanel() {
		JPanel retPanel = new JPanel();
		stockPile = new JLabel();
		stockPile.setIcon(getImage("cards/gold.gif"));
		stockPile.addMouseListener(this);
		retPanel.add(stockPile);
		retPanel.setBackground(Color.BLACK);
		return retPanel;
	}

	// ace up game board
	public JPanel aceBoardPanel() {
		JPanel retPanel = new JPanel(new GridBagLayout());
		retPanel.setBackground(Color.RED);
		retPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.BOTH;
		aceGame = new Ace();
		retPanel = getAndSet(retPanel, aceGame, cons, 2);
		return retPanel;
	}

	// ace up game home pile panel
	public JPanel aceHomePanel() {
		JPanel retPanel = new JPanel();
		homePile = new JLabel();
		homePile.setIcon(getImage("cards/gold.gif"));
		retPanel.add(homePile);
		retPanel.setBackground(Color.BLUE);
		return retPanel;
	}

	// returns baker's game game board
	public JPanel getBakerBoard() {
		gameBoard = new JPanel(new GridBagLayout());
		gameBoard.setBackground(Color.BLACK);
		gameBoard.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.BOTH;
		bakerGame = new Baker();
		gameBoard = getAndSet(gameBoard, bakerGame, cons, 0);
		return gameBoard;
	}

	// returns freecell's gameBoard
	public JPanel getFreeCellBoard() {
		JPanel retPanel = new JPanel(new GridBagLayout());
		retPanel.setBackground(Color.BLACK);
		retPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		GridBagConstraints cons = new GridBagConstraints();
		cons.fill = GridBagConstraints.BOTH;
		freecellGame = new Freecell();
		retPanel = getAndSet(retPanel, freecellGame, cons, 1);
		return retPanel;
	}

	// adds tiles type 0 for baker and 1 for freecell and 2 for ace up
	private JPanel getAndSet(JPanel retPanel, Solitaire game, GridBagConstraints cons, int type) {
		// setting up for freecell else setup for baker's game
		if (type == 1) {
			int paddingY = 0;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 12; j++) {
					JPanel cardTile = new JPanel();
					if ((j < 8 && i < 7 && type == 1)) {
						allTiles[j][i] = new JLabel();
						try {
							allTiles[j][i].setIcon(toImgIcon(game.tableau[j][i].getImage()));
							allTiles[j][i].setName(game.tableau.toString());
						} catch (NullPointerException e) {

						}
						paddingY = 0;

					} else if (i == 0 && j >= 7 && j < 12 && type == 1) {
						freeTiles[j - 8] = new JLabel();
						freeTiles[j - 8].setIcon(getImage("cards/green.gif"));
						freeTiles[j - 8].addMouseListener(this);
						cardTile.add(freeTiles[j - 8]);
					} else {
						allTiles[j][i] = new JLabel();
						paddingY = -10;
					}
					if (j < 8) {
						allTiles[j][i].addMouseListener(this);
						cardTile.add(allTiles[j][i]);
					}

					cardTile.setPreferredSize(new Dimension(40, 60));

					cardTile.addMouseListener(this);
					cons.gridx = j;
					cons.gridy = i;
					cons.weightx = 0.5;
					cons.weighty = 0.5;
					cons.ipady = paddingY;
					cons.anchor = GridBagConstraints.FIRST_LINE_START;
					retPanel.add(cardTile, cons);

				}
			}

			return retPanel;
		} else if (type == 0)

		{
			int paddingY = 0;
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 13; j++) {
					JPanel cardTile = new JPanel();
					if ((i < 4 && j < 13 && type == 0)) {
						allTiles[j][i] = new JLabel();
						allTiles[j][i].setIcon(toImgIcon(game.tableau[j][i].getImage()));
						paddingY = 0;
					} else {
						allTiles[j][i] = new JLabel();
						paddingY = -10;
					}
					allTiles[j][i].addMouseListener(this);
					cardTile.setPreferredSize(new Dimension(40, 60));
					cardTile.add(allTiles[j][i]);
					cardTile.addMouseListener(this);
					cons.gridx = j;
					cons.gridy = i;
					cons.weightx = 0.5;
					cons.weighty = 0.5;
					cons.ipady = paddingY;
					cons.anchor = GridBagConstraints.FIRST_LINE_START;
					retPanel.add(cardTile, cons);

				}
			}
			return retPanel;
		} else {
			int paddingY = 0;
			for (int i = 0; i < 13; i++) {
				for (int j = 0; j < 4; j++) {
					JPanel cardTile = new JPanel();
					if ((i < 1 && j < 4 && type == 2)) {
						allTiles[j][i] = new JLabel();
						allTiles[j][i].setIcon(toImgIcon(game.tableau[j][i].getImage()));
						paddingY = 0;
					} else {
						allTiles[j][i] = new JLabel();
						paddingY = -10;
					}
					// allTiles[j][i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
					allTiles[j][i].addMouseListener(this);
					cardTile.setPreferredSize(new Dimension(40, 60));
					cardTile.add(allTiles[j][i]);
					cardTile.addMouseListener(this);
					cons.gridx = j;
					cons.gridy = i;
					cons.weightx = 0.5;
					cons.weighty = 0.5;
					cons.ipady = paddingY;
					cons.anchor = GridBagConstraints.FIRST_LINE_START;
					retPanel.add(cardTile, cons);

				}
			}

			return retPanel;
		}
	}

	private ImageIcon toImgIcon(BufferedImage image) {
		return new ImageIcon(image);
	}

	public static void main(String[] args) {
		new GameGui();

	}

	public ImageIcon getImage(String path) {

		URL imgURL = this.getClass().getResource(path);
		ImageIcon img;
		img = new ImageIcon(imgURL);
		return img;

	}

	// handles button presses
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == bakerButton) {

			gameType = 0;
			turnButtonOff();
			getContentPane().add(bakerGamePanel());

		} else if (e.getSource() == freecellButton) {

			gameType = 1;
			turnButtonOff();
			getContentPane().add(freeCellGamePanel());
		} else if (e.getSource() == exitButton) {

			System.exit(0);
		} else if (e.getSource() == aceupButton) {
			// add new game panel to contentPane

			gameType = 2;
			turnButtonOff();
			getContentPane().add(aceUpGamePanel());
			System.out.println("ace game pressed");
		} else if (e.getSource() == menuButton) {
			// take to firstscreen

			turnButtonOn();
			getContentPane().add(firstScreen);
			System.out.println("main menu button pressed");
			// menuButton.setVisible(false);
		}

	}

	private void turnButtonOn() {
		bakerButton.setVisible(true);
		bakerButton.revalidate();
		bakerButton.repaint();

		freecellButton.setVisible(true);
		aceupButton.setVisible(true);
		firstScreen.setVisible(true);
		firstScreen.repaint();
		firstScreen.revalidate();
	}

	private void turnButtonOff() {
		bakerButton.setVisible(false);
		freecellButton.setVisible(false);
		aceupButton.setVisible(false);
		firstScreen.setVisible(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressedAceUp(MouseEvent e) {

		if (e.getSource() == stockPile) {
			// System.out.println("stock pile pressed");
			if(aceGame.gameDeck.getDeckList().size()>3){
				aceGame.dealDeck();
				currentText = "Stock Pile pressed";
			}else{
				currentText = "Stock Pile empty, invalid move";
			}
			
			for (int i = 0; i < 4; i++) {
				Card holder = aceGame.tableau[i][aceGame.tableauTopIndex[i]];
				System.out.println("i,tableau top is  " + i + "  " + aceGame.tableauTopIndex[i] + "  ");
				// System.out.println(allTiles[i][aceGame.tableauTopIndex[i]].toString());
				allTiles[i][aceGame.tableauTopIndex[i]].setIcon(toImgIcon(holder.getImage()));

			}
			
			updateStatus();
			checkEasterEgg2();
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 13; j++) {
				if (e.getSource() == allTiles[i][j] && aceGame.tableauTopIndex[i] == j) {
					Card holder = aceGame.tableau[i][j];
					// allTiles[i][j].setBorder(BorderFactory.createLineBorder(Color.BLUE));
					currnTile = allTiles[i][j];
					currnTile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					if (prevTile != null) {
						prevTile.setBorder(BorderFactory.createEmptyBorder());
					}
					counterTtoH++;

					// tableau to home cell
					if (counterTtoH == 2 && prevTile == currnTile) {
						counterTtoH = 0;

						if (aceGame.checkTableauToHomecell(i)) {
							aceGame.tabToHomecell(i);
							currentText = "Success";
							updateStatus();
							if (aceGame.tableauTopIndex[i] != 0) {
								currnTile.setIcon(null);
							} else {
								currnTile.setIcon(getImage("cards/green.gif"));
							}
							homePile.setIcon(toImgIcon(holder.getImage()));
							checkEasterEgg2();
						} else {
							currentText = "Invalid Move";
							updateStatus();
						}

						// tableau to tableau
					} else if (counterTtoH == 2 && aceGame.tableauTopIndex[i] == 0) {
						counterTtoH = 0;
						System.out.println("inside t2t");
						JLabel travTile = allTiles[i][j];
						if (aceGame.checkTableauToTableau(inputPile, i)) {
							aceGame.tableauToTableau(inputPile, i);
							if (aceGame.tableauTopIndex[i] != 0) {
								prevTile.setIcon(null);
							} else {
								prevTile.setIcon(getImage("cards/green.gif"));
							}
							travTile.setIcon(toImgIcon(prevCard.getImage()));
							checkEasterEgg2();
						} else {
							currentText = "Invalid move";
							updateStatus();
						}
						
//						if(allTiles[i][0]== null){
//							System.out.println("coming here");
//							allTiles[i][0].setIcon(getImage("cards/green.gif"));
//						}

					} else {
						prevTile = currnTile;
						prevCard = holder;
						inputPile = i;
						currentText = "Click one more card";
						updateStatus();
					}
					// System.out.println(" card pressed is "+ holder.getSuit()
					// + " " + holder.getRank());
				}
			}
		}
		revalidate();
		repaint();
	}
	
	
	// handles clicking on cards for baker's dozen
	public void mousePressedBaker(MouseEvent e) {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				allTiles[j][i].setBackground(new Color(r, g, b));
				if (e.getSource() == allTiles[j][i]) {
					if (i == bakerGame.tableauTopIndex[j]) {
						counterTtoH++;
						currnTile = allTiles[j][i];
					}
					// double click on a tableau to move to homecell
					if (counterTtoH == 2 && prevTile == currnTile) {
						counterTtoH = 0;
						int outputPile = 100;
						Card holder = bakerGame.tableau[j][i];

						if (holder.getSuit() == 1) {
							outputPile = 0;
						} else if (holder.getSuit() == 2) {
							outputPile = 1;
						} else if (holder.getSuit() == 3) {
							outputPile = 2;
						} else if (holder.getSuit() == 4) {
							outputPile = 3;
						}
						if (bakerGame.checkRemoveFromTableauToHomecell(j, outputPile) == true) {
							currentText = "Success";
							updateStatus();
							prevTile.setIcon(null);
							bakerGame.tableauToHomecell(j, outputPile);
							if (outputPile == 0) {
								homeCell0.setIcon(toImgIcon(holder.getImage()));
							} else if (outputPile == 1) {
								homeCell1.setIcon(toImgIcon(holder.getImage()));
							} else if (outputPile == 2) {
								homeCell2.setIcon(toImgIcon(holder.getImage()));
							} else if (outputPile == 3) {
								homeCell3.setIcon(toImgIcon(holder.getImage()));
							}
							if (bakerGame.victory == true) {
								currentText = "Victory";
								updateStatus();
							}
						} else {
							currentText = "Invalid move";
							updateStatus();
							currnTile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
						}

						// check if card can go from homecell to homecell pile
					} else if (counterTtoH == 2 && prevTile != currnTile) {
						counterTtoH = 0;
						JLabel travTile;
						travTile = allTiles[j][i + 1];
						if (inputPile != 100) {
							if (bakerGame.checkRemoveFromTableauToTableau(inputPile, j) == true) {
								prevTile.setIcon(null);
								travTile.setIcon(toImgIcon(prevCard.getImage()));
								bakerGame.tableauToTableau(inputPile, j);
								currentText = "Success";
								updateStatus();
							} else {
								currentText = "Invalid move";
								updateStatus();
								prevTile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
							}
						} else {
							currentText = "Invalid Move";
							updateStatus();
						}

					} else {

						if (i == bakerGame.tableauTopIndex[j]) {
							inputPile = j;
							currentText = "Click one more card";
							updateStatus();
							prevCard = bakerGame.tableau[j][i];
							prevTile = currnTile;
							currnTile.setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.blue));
						}
					}

					revalidate();
					repaint();

				}
			}
		}

	}

	// handles mouse actions with freecell
	public void mousePressedFreecell(MouseEvent e) {

		for (int i = 0; i < 8; i++) {
			if (i < 4) {
				if (e.getSource() == freeTiles[i]) {
					counterTtoH++;
					currnTile = freeTiles[i];
					firstClickFree = true;
					// moves cards to homecell if double clicked
					if (counterTtoH == 2 && prevTile == currnTile) {
						counterTtoH = 0;
						firstClickFree = false;
						int outputPile = 100;
						Card holder = freecellGame.freecell[i];

						if (holder.getSuit() == 1) {
							outputPile = 0;
						} else if (holder.getSuit() == 2) {
							outputPile = 1;
						} else if (holder.getSuit() == 3) {
							outputPile = 2;
						} else if (holder.getSuit() == 4) {
							outputPile = 3;
						}
						if (freecellGame.checkRemoveFromFreecellToHomecell(i, outputPile) == true) {
							prevTile.setIcon(null);
							freecellGame.tableauToHomecell(i, outputPile);
							if (outputPile == 0) {
								homeCell0.setIcon(toImgIcon(holder.getImage()));
							} else if (outputPile == 1) {
								homeCell1.setIcon(toImgIcon(holder.getImage()));
							} else if (outputPile == 2) {
								homeCell2.setIcon(toImgIcon(holder.getImage()));
							} else if (outputPile == 3) {
								homeCell3.setIcon(toImgIcon(holder.getImage()));
							}
							if (bakerGame.victory == true) {
								currentText = "Victory";
								updateStatus();
							}
						} else {
							currentText = "Invalid Move";
							updateStatus();
							currnTile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
						}
					} else if (firstClickFree && counterTtoH == 2) {
						// check if card can move to tabaleu pile
						counterTtoH = 0;
						firstClickFree = false;
						JLabel travTile;
						travTile = freeTiles[i];
						if (inputPile != 100) {
							if (freecellGame.freecell[i] == null) {
								prevTile.setIcon(null);
								travTile.setIcon(toImgIcon(prevCard.getImage()));
								freecellGame.tableauToFreecell(inputPile, i);
							} else {
								currentText = "Invalid Move";
								updateStatus();
								prevTile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
							}
						} else {
							currentText = "Invalid Move";
							updateStatus();
							System.out.println("fail outer");
						}
					} else {
						inputPile = i;
						currentText = "Click one more card";
						updateStatus();
						prevCard = freecellGame.freecell[i];
						prevTile = currnTile;
						currnTile.setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.blue));
					}
				}
			}
			for (int j = 0; j < 12; j++) {
				if (e.getSource() == allTiles[j][i]) {
					if (i == freecellGame.tableauTopIndex[j]) {
						counterTtoH++;
						currnTile = allTiles[j][i];
					}
					// double click a card to move to homecell
					if (counterTtoH == 2 && prevTile == currnTile) {
						counterTtoH = 0;
						int outputPile = 100;
						Card holder = freecellGame.tableau[j][i];
						System.out.println("you pressed same tile");

						if (holder.getSuit() == 1) {
							outputPile = 0;
						} else if (holder.getSuit() == 2) {
							outputPile = 1;
						} else if (holder.getSuit() == 3) {
							outputPile = 2;
						} else if (holder.getSuit() == 4) {
							outputPile = 3;
						}

						System.out.println(j + "," + outputPile + "," + holder.getSuit());
						if (freecellGame.checkRemoveFromTableauToHomecell(j, outputPile) == true) {
							prevTile.setIcon(null);
							freecellGame.tableauToHomecell(j, outputPile);
							if (outputPile == 0) {
								homeCell0.setIcon(toImgIcon(holder.getImage()));
							} else if (outputPile == 1) {
								homeCell1.setIcon(toImgIcon(holder.getImage()));
							} else if (outputPile == 2) {
								homeCell2.setIcon(toImgIcon(holder.getImage()));
							} else if (outputPile == 3) {
								homeCell3.setIcon(toImgIcon(holder.getImage()));
							}
						} else {
							updateStatus();
							System.out.println("fail outer");
							System.out.println("fail");
							currnTile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
						}

						// check if card can go to homecell pile
					} else if (counterTtoH == 2 && prevTile != currnTile) {
						System.out.println("you pressed different tile");
						counterTtoH = 0;
						JLabel travTile;
						if (firstClickFree) {
							System.out.println("right for loop" + inputPile + "," + j + "," + inputPile);
							travTile = allTiles[j][i + 1];
							System.out.println("potato" + freeTiles[inputPile] == null);
							if (inputPile != 100) {
								if (freecellGame.checkRemoveFromFreecellToTableau(inputPile, j)) {
									travTile.setIcon(toImgIcon(prevCard.getImage()));
									travTile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
									prevTile.setIcon(getImage("cards/green.gif"));

									freecellGame.freecellToTableau(inputPile, j);
									revalidate();
									repaint();
								} else {
									updateStatus();
									System.out.println("fail outer");
									System.out.println("fail inner right call");
									prevTile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
								}
							} else {
								updateStatus();
								System.out.println("fail outer");
								System.out.println("fail outer");
							}
						} else {

							// check if card can move to tabaleu pile
							travTile = allTiles[j][i + 1];
							if (inputPile != 100) {
								if (freecellGame.checkRemoveFromTableauToTableau(inputPile, j) == true) {
									prevTile.setIcon(null);
									travTile.setIcon(toImgIcon(prevCard.getImage()));
									freecellGame.tableauToTableau(inputPile, j);
								} else {
									currentText = "Invalid Move";
									updateStatus();
									System.out.println("fail inner wrong call" + firstClickFree);
									prevTile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
								}
							} else {
								currentText = "Invalid Move";
								updateStatus();

								System.out.println("fail outer");
							}
						}

					} else {
						if (i == freecellGame.tableauTopIndex[j]) {
							inputPile = j;
							// currentText = "Click one more card";
							// updateStatus();
							prevCard = freecellGame.tableau[j][i];
							prevTile = currnTile;
							currnTile.setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.blue));
						}
					}

					revalidate();
					repaint();

				}
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// System.out.println("mouse pressed");
		if (gameType == 0) {
			mousePressedBaker(e);
		} else if (gameType == 1) {
			mousePressedFreecell(e);
		} else if (gameType == 2) {
			mousePressedAceUp(e);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	// summon devil
	public void checkEasterEgg() {
		int counter = 0;
		for (int i = 0; i < 4; i++) {
			if (gameType == 0) {
				if (bakerGame.homecellTopIndex[i] == 5) {
					counter++;
				}
			} else {
				if (gameType == 1) {
					if (freecellGame.homecellTopIndex[i] == 5) {
						counter++;
					}
				}
			}
		}
		if (counter == 3) {
			// summon devil
			JLabel devil = new JLabel();
			devil.setIcon(getImage("CanadianDevil.png"));
			updateStatus();
			statusPanel.add(devil);
		}
	}

	public void updateStatus() {
		statusPanel.removeAll();
		statusPanel.add(new JLabel(currentText));
		statusPanel.revalidate();
		statusPanel.repaint();
	}

	public JButton getMenuButton() {
		menuButton = new JButton("Main Menu");
		menuButton.addActionListener(this);
		return menuButton;
	}

	public void checkEasterEgg2(){
		int counter = 0;
		for (int i = 0; i < 4; i++) {
			if(aceGame.tableau[i][aceGame.tableauTopIndex[i]]!=null){
				if(aceGame.tableau[i][aceGame.tableauTopIndex[i]].getRank()==6){
					counter++;
				}
			}
			
		}
		if (counter == 3) {
			// summon devil
			JLabel devil = new JLabel();
			devil.setIcon(getImage("AmericanDevil.png"));
			statusPanel.removeAll();
			statusPanel.add(devil);
			statusPanel.revalidate();
			statusPanel.repaint();
		}
	}
	
}
