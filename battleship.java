package BattleShip;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;
import javax.swing.border.*;

public class BattleshipGUI extends JFrame implements ActionListener {

	static final int WIDTH = 11;
	static final int HEIGHT = 11;
	static final String[] Y_AXIS_LABEL = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
	static final String[] X_AXIS_LABEL = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
	static String Coordinate;
	static String[] CoordinateSplit;
	int[][] hits = new int[11][11];
	String[] ships = { "Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer" };
	int[][] probability = new int[10][10];

	JPanel levelPan = new JPanel();
	JPanel coinPan = new JPanel();
	JPanel choosePan = new JPanel();
	JPanel orderPan = new JPanel();
	JPanel gamePan = new JPanel();
	JPanel gameWindow = new JPanel();
	JPanel pan2 = new JPanel();
	JPanel userPanel = new JPanel();
	JPanel computerPanel = new JPanel();
	JPanel userOutput = new JPanel();
	JPanel userPlay = new JPanel();
	JPanel computerPlay = new JPanel();
	JPanel computerScore = new JPanel();

	JButton toss = new JButton("Toss");
	JButton nextMove = new JButton("Calculate Move");
	JButton start = new JButton("Start");
	JButton[][] button = new JButton[HEIGHT][WIDTH];
	JButton player = new JButton("Player");
	JButton comp = new JButton("Computer");
	JButton hit = new JButton("Hit");
	JButton miss = new JButton("Miss");
	JButton carrier = new JButton("Carrier");
	JButton battleship = new JButton("Battleship");
	JButton cruiser = new JButton("Cruiser");
	JButton submarine = new JButton("Submarine");
	JButton destroyer = new JButton("Destroyer");
	JButton easy = new JButton("Easy");
	JButton advanced = new JButton("Advanced");

	JLabel level = new JLabel("Select the difficulty level:");
	JLabel result = new JLabel("Click to have a coin toss:");
	JLabel order = new JLabel(" ");
	JLabel choose = new JLabel("Choose who goes first:");
	JLabel outcome = new JLabel("The results of your choice:");
	JLabel compOutcome = new JLabel("Select the outcome of the computer's choice:");
	JLabel compOutcomeShip = new JLabel("If a ship was hit, please select which one was hit:");
	JLabel space = new JLabel(" ");
	JLabel space1 = new JLabel(" ");
	JLabel space2 = new JLabel(" ");
	JLabel lEmpty = new JLabel(" ");
	JLabel uTurnNum = new JLabel("# Turns:");
	JLabel uHitNum = new JLabel("# Hits:");
	JLabel uMissNum = new JLabel("# Misses:");
	JLabel uRemShip = new JLabel("# Remaining Ships: 5");
	JLabel cTurnNum = new JLabel("# Turns:");
	JLabel cHitNum = new JLabel("# Hits:");
	JLabel cMissNum = new JLabel("# Misses:");
	JLabel cRemShip = new JLabel("# Remaining Ships: 5");
	JLabel uChooseLabel = new JLabel("Click a spot to fire");
	JLabel cChooseLabel = new JLabel("The computer's coordinate choice:");
	JLabel compComment = new JLabel(" ");

	JTextField enterCoordinate = new JTextField("Enter Coordinate", 10);

	int shipPlacement[][] = new int[11][11];
	int simpleCoordinate[][] = new int[11][11];
	int shipPlacementAI[][] = new int[11][11];
	int shipLength[] = { 2, 3, 3, 4, 5 };

	int uTurnCount = 0;
	int uHitCount = 0;
	int uMissCount = 0;
	int uRemShipCount = 0;
	int cTurnCount = 0;
	int cHitCount = 0;
	int cMissCount = 0;
	int cRemShipCount = 0;
	int tossCoin;

	public BattleshipGUI() {
		setSize(600, 300);
		setTitle("Level");

		BoxLayout levelPanLayout = new BoxLayout(levelPan, BoxLayout.Y_AXIS);
		levelPan.setLayout(levelPanLayout);

		level.setAlignmentX(Component.CENTER_ALIGNMENT);
		levelPan.add(level);
		easy.setAlignmentX(Component.CENTER_ALIGNMENT);
		levelPan.add(easy);
		advanced.setAlignmentX(Component.CENTER_ALIGNMENT);
		levelPan.add(advanced);
		easy.addActionListener(this);
		advanced.addActionListener(this);

		add(levelPan);
		setVisible(true);
	}

	public void order() {
		remove(levelPan);
		add(orderPan);
		repaint();
		pack();

		setSize(600, 300);
		setTitle("Order of Players");

		GridLayout orderPanLayout = new GridLayout(1, 2);
		orderPan.setLayout(orderPanLayout);

		BoxLayout coinPanLayout = new BoxLayout(coinPan, BoxLayout.Y_AXIS);
		coinPan.setLayout(coinPanLayout);

		BoxLayout choosePanLayout = new BoxLayout(choosePan, BoxLayout.Y_AXIS);
		choosePan.setLayout(choosePanLayout);

		choose.setAlignmentX(Component.CENTER_ALIGNMENT);
		choosePan.add(choose);
		choosePan.add(player);
		player.setAlignmentX(Component.CENTER_ALIGNMENT);
		player.addActionListener(this);
		choosePan.add(comp);
		comp.setAlignmentX(Component.CENTER_ALIGNMENT);
		comp.addActionListener(this);

		result.setAlignmentX(Component.CENTER_ALIGNMENT);
		coinPan.add(result);
		toss.setAlignmentX(Component.CENTER_ALIGNMENT);
		toss.addActionListener(this);
		coinPan.add(toss);
		order.setAlignmentX(Component.CENTER_ALIGNMENT);
		coinPan.add(order);
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		coinPan.add(start);
		start.addActionListener(this);

		orderPan.add(choosePan);
		orderPan.add(coinPan);
		add(orderPan);
		setVisible(true);
	}

	public void gameBoard() {
		remove(orderPan);
		add(gameWindow);
		repaint();
		pack();

		setSize(1200, 700);
		setTitle("Battleship");

		GridLayout display = new GridLayout(1, 2);
		gameWindow.setLayout(display);

		GridLayout board = new GridLayout(11, 11);
		gamePan.setLayout(board);

		GridLayout grid = new GridLayout(2, 1);
		pan2.setLayout(grid);

		GridLayout grid1 = new GridLayout(1, 2);
		userPanel.setLayout(grid1);

		BoxLayout boxLayout1 = new BoxLayout(userOutput, BoxLayout.Y_AXIS);
		userOutput.setLayout(boxLayout1);

		BoxLayout boxLayout3 = new BoxLayout(userPlay, BoxLayout.Y_AXIS);
		userPlay.setLayout(boxLayout3);

		GridLayout grid2 = new GridLayout(1, 2);
		computerPanel.setLayout(grid2);

		BoxLayout boxLayout4 = new BoxLayout(computerScore, BoxLayout.Y_AXIS);
		computerScore.setLayout(boxLayout4);

		BoxLayout boxLayout2 = new BoxLayout(computerScore, BoxLayout.Y_AXIS);
		computerScore.setLayout(boxLayout2);

		TitledBorder gameboard;
		Border blackline;
		blackline = BorderFactory.createLineBorder(Color.black);
		gameboard = BorderFactory.createTitledBorder(blackline, "Gameboard");
		gameboard.setTitleJustification(TitledBorder.CENTER);
		gamePan.setBorder(gameboard);
		gamePan.setLayout(board);
		button[0][0] = new JButton("");
		button[0][0].setBackground(Color.decode("#EEEEEE"));
		button[0][0].setEnabled(false);
		button[0][0].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		gamePan.add(button[0][0]);

		for (int i = 0; i < WIDTH - 1; i++) {

			button[0][i + 1] = new JButton(X_AXIS_LABEL[i]);
			button[0][i + 1].setBackground(Color.decode("#EEEEEE"));
			button[0][i + 1].setEnabled(false);
			button[0][i + 1].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			button[0][i + 1].addActionListener(this);
			gamePan.add(button[0][i + 1]);
		}

		for (int i = 1; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				if (j == 0) {
					button[i][j] = new JButton(Y_AXIS_LABEL[i - 1]);
					button[i][j].setBackground(Color.decode("#EEEEEE"));
					button[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
					button[i][j].setEnabled(false);
					gamePan.add(button[i][j]);
				} else {
					button[i][j] = new JButton(" ");
					button[i][j].setBackground(Color.LIGHT_GRAY);
					button[i][j].addActionListener(this);

					gamePan.add(button[i][j]);
				}
			}
		}

		TitledBorder userPlayPanel;
		userPlayPanel = BorderFactory.createTitledBorder(blackline, "User Play");
		userPlayPanel.setTitleJustification(TitledBorder.CENTER);
		userPanel.setBorder(userPlayPanel);
		userOutput.add(uChooseLabel);
		userOutput.add(space);
		userOutput.add(outcome);
		userOutput.add(compComment);

		TitledBorder userScorePanel;
		userScorePanel = BorderFactory.createTitledBorder(blackline, "User Score");
		userScorePanel.setTitleJustification(TitledBorder.CENTER);
		userPlay.setBorder(userScorePanel);
		userPlay.add(uTurnNum);
		userPlay.add(uMissNum);
		userPlay.add(uHitNum);
		userPlay.add(uRemShip);

		TitledBorder computerPlayPanel;
		computerPlayPanel = BorderFactory.createTitledBorder(blackline, "Computer Play");
		computerPlayPanel.setTitleJustification(TitledBorder.CENTER);
		computerPanel.setBorder(computerPlayPanel);
		computerPlay.add(cChooseLabel);
		computerPlay.add(nextMove);
		computerPlay.add(space1);
		computerPlay.add(compOutcome);
		computerPlay.add(enterCoordinate);
		computerPlay.add(hit);
		computerPlay.add(miss);
		hit.setBackground(Color.red);
		miss.setBackground(Color.green);
		computerPlay.add(space2);
		computerPlay.add(compOutcomeShip);
		computerPlay.add(cruiser);
		computerPlay.add(battleship);
		computerPlay.add(carrier);
		computerPlay.add(submarine);
		computerPlay.add(destroyer);

		TitledBorder computerScorePanel;
		computerScorePanel = BorderFactory.createTitledBorder(blackline, "Computer Score");
		computerScorePanel.setTitleJustification(TitledBorder.CENTER);
		computerScore.setBorder(computerScorePanel);
		computerScore.add(cTurnNum);
		computerScore.add(cMissNum);
		computerScore.add(cHitNum);
		computerScore.add(cRemShip);

		nextMove.addActionListener(this);
		hit.addActionListener(this);
		miss.addActionListener(this);
		cruiser.addActionListener(this);
		battleship.addActionListener(this);
		carrier.addActionListener(this);
		submarine.addActionListener(this);
		destroyer.addActionListener(this);

		userPanel.add(userOutput);
		userPanel.add(userPlay);
		computerPanel.add(computerPlay);
		computerPanel.add(computerScore);
		pan2.add(userPanel);
		pan2.add(computerPanel);
		gameWindow.add(pan2);
		gameWindow.add(gamePan);
		add(gameWindow);
	}

	public void tossCoin() {
		tossCoin = (int) (Math.random() * 2);
		if (tossCoin == 0) {
			order.setText("User goes first");
		} else {// toss ==1
			order.setText("Computer goes first");
		}
	}

	public void placeShips(int randPos, int randX, int randY, int[][] shipPlacement, int shipCount, int shipLength) {
		if (randPos == 0) { // up
			if (randX - shipLength >= -1) {
				for (int i = 0; i < shipLength; i++) {
					shipPlacement[randX - i][randY] = shipCount;
				}
			} else if (randX - shipLength < -1) {
				randPos = (int) (Math.random() * 3 + 1);
			}
		}
		if (randPos == 1) { // down
			if (randX + shipLength <= 10) {
				for (int i = 0; i < shipLength; i++) {
					shipPlacement[randX + i][randY] = shipCount;
				}
			} else if (randX + shipLength > 10) {
				do {
					randPos = (int) (Math.random() * 4);
				} while (randPos == 1);
			}
		}
		if (randPos == 2) { // left
			if (randY - shipLength >= -1) {
				for (int i = 0; i < shipLength; i++) {
					shipPlacement[randX][randY - i] = shipCount;
				}
			} else if (randY - shipLength < -1) {
				do {
					randPos = (int) (Math.random() * 4);
				} while (randPos == 2);
			}
		}
		if (randPos == 3) { // RIGHT
			if (randY + shipLength <= 10) {
				for (int i = 0; i < shipLength; i++) {
					shipPlacement[randX][randY + i] = shipCount;
				}
			} else if (randY + shipLength > 10) {
				do {
					randPos = (int) (Math.random() * 4);
				} while (randPos == 3);
			}
		}
	}

	public void simpleShipAI() {
		int shipCount = 1;
		int randPos;
		int randX;
		int randY;
		int shipLength = 0;
		do {
			randX = (int) (Math.random() * 10);
			randY = (int) (Math.random() * 4);
			randPos = (int) (Math.random() * 4);

			if (shipCount == 1) { // place destroyer ship
				shipLength = 5;
				placeShips(randPos, randX, randY, shipPlacement, shipCount, shipLength);
				shipCount++;
			} else if (shipCount == 2) { // place destroyer ship
				shipLength = 4;
				placeShips(randPos, randX, randY, shipPlacement, shipCount, shipLength);
				shipCount++;
			} else if (shipCount == 3) { // place destroyer ship
				shipLength = 3;
				placeShips(randPos, randX, randY, shipPlacement, shipCount, shipLength);
				shipCount++;
			} else if (shipCount == 4) { // place destroyer ship
				shipLength = 3;
				placeShips(randPos, randX, randY, shipPlacement, shipCount, shipLength);
				shipCount++;
			} else if (shipCount == 5) { // place destroyer ship
				shipLength = 2;
				placeShips(randPos, randX, randY, shipPlacement, shipCount, shipLength);
				shipCount++;
			}

		} while (shipCount < 6);

	}

	public void simpleCoordinateAI() {
		int randX = (int) (Math.random() * 10);
		int randY = (int) (Math.random() * 10);
		do {
			randX = (int) (Math.random() * 10);
			randY = (int) (Math.random() * 10);
		} while (simpleCoordinate[randY][randX] == 1);

		simpleCoordinate[randY][randX] = 1;

		String letter;
		if (randX == 0) {
			letter = "A";
		} else if (randX == 1) {
			letter = "B";
		} else if (randX == 2) {
			letter = "C";
		} else if (randX == 3) {
			letter = "D";
		} else if (randX == 4) {
			letter = "E";
		} else if (randX == 5) {
			letter = "F";
		} else if (randX == 6) {
			letter = "G";
		} else if (randX == 7) {
			letter = "H";
		} else if (randX == 8) {
			letter = "I";
		} else {
			letter = "J";
		}

		cChooseLabel.setText("The computer's coordinate choice: " + letter + ", " + (randY + 1));
		cTurnCount++;
		cTurnNum.setText("# Turns: " + cTurnCount);
	}

	public void complexShipAI(int shipPlacementAI[][]) {
		int randArrangement = 0; // (int)(Math.random()* 2);
		int orient = (int) (Math.random() * 1);
		int topOrBottom = (int) (Math.random() * 1);
		int leftOrRight = (int) (Math.random() * 1);
		int x = 1;

		if (randArrangement == 0) // edge arrangement
		{
			for (int i = 0; i < shipLength.length; i++) {
				if (orient == 0) // horizontal
				{
					if (topOrBottom == 0) // top
					{
						x = (int) (Math.random() * (11 - shipLength[i])) + 1;
						System.out.println(x);
						for (int a = 0; a < shipLength[i]; a++) {
							System.out.println(shipPlacement[1][x + a]);
							shipPlacementAI[1][x + a] = shipLength[i];
						}
					} else if (topOrBottom == 1) // bottom
					{
						x = (int) (Math.random() * (11 - shipLength[i])) + 1;
						for (int a = 0; a < shipLength[i]; a++) {
							System.out.println(shipPlacement[1][x + a]);
							shipPlacementAI[11][x + a] = shipLength[i];
						}
					}
				} else if (orient == 1) // vertical
				{
					if (leftOrRight == 0) // left
					{
						x = (int) (Math.random() * (11 - shipLength[i])) + 1;
						for (int a = 0; a < shipLength[i]; a++) {
							System.out.println(shipPlacement[x + a][1]);
							shipPlacementAI[x + a][1] = shipLength[i];
						}
					} else if (leftOrRight == 1) // right
					{
						x = (int) (Math.random() * (11 - shipLength[i])) + 1;
						for (int a = 0; a < shipLength[i]; a++) {
							System.out.println(shipPlacement[x + a][11]);
							shipPlacementAI[x + a][11] = shipLength[i];
						}
					}
				}
			}
		} else if (randArrangement == 1) // middle arrangement
		{
		} else if (randArrangement == 2) // middle and edge arrangement
		{
		}

	}

	public void actionPerformed(ActionEvent event) {
		if (easy == event.getSource()) {
			order();
		}

		if (toss == event.getSource()) // if toss button is clicked
		{
			tossCoin();
		}
		if (start == event.getSource()) {
			gameBoard();
			simpleShipAI();
		}

		if (player == event.getSource()) // if player button is clicked
		{
			gameBoard();
			simpleShipAI();
		} else if (comp == event.getSource()) {
			gameBoard();
			simpleCoordinateAI();
			simpleShipAI();
		}

		if (hit == event.getSource()) {
			Coordinate = enterCoordinate.getText();
			enterCoordinate.setText("");
			CoordinateSplit = Coordinate.split("");
			if (checkInput(CoordinateSplit)) {
				hits[StringtoInt(CoordinateSplit[0])][Integer.parseInt(CoordinateSplit[1])] = -1;
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Coordinate", "Warning", JOptionPane.WARNING_MESSAGE);
			}

		} else if (miss == event.getSource()) {
			Coordinate = enterCoordinate.getText();
			enterCoordinate.setText("");
			CoordinateSplit = Coordinate.split("");
			if (checkInput(CoordinateSplit)) {
				hits[StringtoInt(CoordinateSplit[0])][Integer.parseInt(CoordinateSplit[1])] = -2;
			} else {
				JOptionPane.showMessageDialog(null, "Invalid Coordinate", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}

		for (int i = 1; i < HEIGHT; i++) {
			for (int j = 1; j < WIDTH; j++) {
				if (shipPlacement[i][j] != 0 && button[i][j] == event.getSource()) {
					button[i][j].setBackground(Color.red);
					uTurnCount++;
					uTurnNum.setText("# Turns: " + uTurnCount);
					uHitCount++;
					uHitNum.setText("# Hits: " + uHitCount);
					compComment.setText("HIT");
				} else if (button[i][j] == event.getSource()) {
					button[i][j].setBackground(Color.cyan);
					uTurnCount++;
					uTurnNum.setText("# Turns: " + uTurnCount);
					uMissCount++;
					uMissNum.setText("# Misses: " + uMissCount);
					compComment.setText("MISS");
				}
			}
		}

		if (nextMove == event.getSource()) {
			cChooseLabel.setText("The computer's coordinate choice: " + bestMove(ships, probability, hits)[0]
					+ bestMove(ships, probability, hits)[1]);
			compComment.setText("");
		}

		if (advanced == event.getSource()) {
			{
				order();
			}

			if (toss == event.getSource()) // if toss button is clicked
			{
				tossCoin();
			}
			if (start == event.getSource()) {
				complexShipAI(shipPlacementAI);
				System.out.println("H");
				gameBoard();
			}

			if (player == event.getSource()) // if player button is clicked
			{
				complexShipAI(shipPlacementAI);
				gameBoard();
			} else if (comp == event.getSource()) // if comp button is clicked
			{
				complexShipAI(shipPlacementAI);
				gameBoard();
				cChooseLabel.setText("The computer's coordinate choice: " + bestMove(ships, probability, hits)[0]
						+ bestMove(ships, probability, hits)[1]);
			}

			if (hit == event.getSource()) {
				Coordinate = enterCoordinate.getText();
				enterCoordinate.setText("");
				CoordinateSplit = Coordinate.split("");
				if (checkInput(CoordinateSplit)) {
					hits[StringtoInt(CoordinateSplit[0])][Integer.parseInt(CoordinateSplit[1])] = -1;
				} else {
					JOptionPane.showMessageDialog(null, "Invalid Coordinate", "Warning", JOptionPane.WARNING_MESSAGE);
				}

			} else if (miss == event.getSource()) {
				Coordinate = enterCoordinate.getText();
				enterCoordinate.setText("");
				CoordinateSplit = Coordinate.split("");
				if (checkInput(CoordinateSplit)) {
					hits[StringtoInt(CoordinateSplit[0])][Integer.parseInt(CoordinateSplit[1])] = -2;
				} else {
					JOptionPane.showMessageDialog(null, "Invalid Coordinate", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}

			else if (nextMove == event.getSource()) {
				cChooseLabel.setText("The computer's coordinate choice: " + bestMove(ships, probability, hits)[0]
						+ bestMove(ships, probability, hits)[1]);
			}
			for (int i = 1; i < HEIGHT; i++) {
				for (int j = 1; j < WIDTH; j++) {
					if (shipPlacementAI[i][j] != 0 && button[i][j] == event.getSource()) {
						button[i][j].setBackground(Color.red);
						uTurnCount++;
						uTurnNum.setText("# Turns: " + uTurnCount);
						uHitCount++;
						uHitNum.setText("# Hits: " + uHitCount);
						compComment.setText("HIT");
					} else if (button[i][j] == event.getSource()) {
						button[i][j].setBackground(Color.cyan);
						uTurnCount++;
						uTurnNum.setText("# Turns: " + uTurnCount);
						uMissCount++;
						uMissNum.setText("# Misses: " + uMissCount);
						compComment.setText("MISS");
					}
				}
			}

		}
	}

	public static void main(String[] args) {
		BattleshipGUI Frame = new BattleshipGUI(); // display frame
	}

	public static int StringtoInt(String coordinateSplit2) {
		for (int i = 0; i < Y_AXIS_LABEL.length; i++) {
			if (coordinateSplit2.equals(Y_AXIS_LABEL[i])) {
				return (i + 1);
			}
		}
		return 0;
	}

	public static boolean checkInput(String[] input) {

		for (int i = 0; i < Y_AXIS_LABEL.length; i++) {
			if (input[0].equals(Y_AXIS_LABEL[i])) {
				for (int j = 0; j < X_AXIS_LABEL.length; j++) {
					if (input[1].equals(X_AXIS_LABEL[j])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	////////////////////////////////////////////////////////////// AI CODE DON'T
	////////////////////////////////////////////////////////////// TOUCH
	public static String[] bestMove(String[] ships, int[][] probability, int[][] hits) {

		String[] coordinate = new String[2];
		int[] moveCoordinates = new int[2];
		// best Coordinates in form y x
		int hitCount = 0;
		int maxProbability = 0;

		calculateDensity(ships, probability, hits);

		for (int i = 0; i < (probability.length); i++) {
			for (int j = 0; j < probability[i].length; j++) {
				if (probability[i][j] == -1) {
					hitCount++;
				}
			}
		}

		if (hitCount == 0) {
			// No hits probability guess mode
			for (int i = 0; i < (probability.length); i++) {
				for (int j = 0; j < probability[i].length; j++) {
					if (probability[i][j] > maxProbability) {
						maxProbability = probability[i][j];
						moveCoordinates[0] = i;
						moveCoordinates[1] = j;
					}
				}
			}
			coordinate[0] = Y_AXIS_LABEL[moveCoordinates[0]];
			coordinate[1] = X_AXIS_LABEL[moveCoordinates[1]];
			return coordinate;

		} else if (hitCount == 1) {
			// One hit hunt mode
			for (int i = 0; i < (probability.length); i++) {
				for (int j = 0; j < probability[i].length; j++) {
					if (probability[i][j] == -1) {
						if (i < 9) {
							maxProbability = probability[i + 1][j];
							moveCoordinates[0] = i + 1;
							moveCoordinates[1] = j;
						}
						if (i > 0 && probability[i - 1][j] > maxProbability) {
							maxProbability = probability[i - 1][j];
							moveCoordinates[0] = i - 1;
							moveCoordinates[1] = j;
						}
						if (j < 9 && probability[i][j + 1] > maxProbability) {
							maxProbability = probability[i][j + 1];
							moveCoordinates[0] = i;
							moveCoordinates[1] = j + 1;
						}
						if (j > 0 && probability[i][j - 1] > maxProbability) {
							maxProbability = probability[i][j - 1];
							moveCoordinates[0] = i;
							moveCoordinates[1] = j - 1;
						}
						coordinate[0] = Y_AXIS_LABEL[moveCoordinates[0]];
						coordinate[1] = X_AXIS_LABEL[moveCoordinates[1]];
						return coordinate;
					}
				}
			}

		} else {
			// Greater than one hit, sink mode
			for (int i = 0; i < (probability.length); i++) {
				for (int j = 0; j < probability[i].length; j++) {
					if (probability[i][j] == -1) {
						// if hits are not on the edges
						if (i < 9 && probability[i + 1][j] == -1) {
							maxProbability = probability[i - 1][j];
							int count = 0;
							while (i + 1 + count < 9 && probability[i + 1 + count][j] == -1
									&& probability[i + 1 + count][j] != -2) {
								count++;
							}
							if (probability[i + 1 + count][j] > maxProbability) {
								moveCoordinates[0] = i + 1 + count;
								moveCoordinates[1] = j;
							} else {
								moveCoordinates[0] = i - 1;
								moveCoordinates[1] = j;
							}

						} else if (i > 0 && probability[i - 1][j] == -1) {
							maxProbability = probability[i + 1][j];
							int count = 0;
							while (i - 1 - count > 0 && probability[i - 1 - count][j] == -1
									&& probability[i - 1 - count][j] != -2) {
								count++;
							}
							if (probability[i - 1 - count][j] > maxProbability) {
								moveCoordinates[0] = i - 1 - count;
								moveCoordinates[1] = j;
							} else {
								moveCoordinates[0] = i + 1;
								moveCoordinates[1] = j;
							}
						} else if (j < 9 && probability[i][j + 1] == -1) {
							maxProbability = probability[i][j - 1];
							int count = 0;
							while (j + 1 + count < 9 && probability[i][j + 1 + count] == -1
									&& probability[i][j + 1 + count] != -2) {
								count++;
							}
							if (probability[i][j + 1 + count] > maxProbability) {
								moveCoordinates[0] = i;
								moveCoordinates[1] = j + 1 + count;
							} else {
								moveCoordinates[0] = i;
								moveCoordinates[1] = j - 1;
							}
						} else if (j > 0 && probability[i][j - 1] == -1) {
							maxProbability = probability[i][j + 1];
							int count = 0;
							while (j - 1 - count > 0 && probability[i][j - 1 - count] == -1
									&& probability[i][j + 1 + count] != -2) {
								count++;
							}
							if (probability[i][j + 1 + count] > maxProbability) {
								moveCoordinates[0] = i - 1 - count;
								moveCoordinates[1] = j;
							} else {
								moveCoordinates[0] = i + 1;
								moveCoordinates[1] = j;
							}
						}
						coordinate[0] = Y_AXIS_LABEL[moveCoordinates[0]];
						coordinate[1] = X_AXIS_LABEL[moveCoordinates[1]];
						return coordinate;

					}
				}
			}
		}
		coordinate[0] = Y_AXIS_LABEL[moveCoordinates[0]];
		coordinate[1] = X_AXIS_LABEL[moveCoordinates[1]];
		return coordinate;
	}

	public static void calculateDensity(String[] ships, int[][] probability, int[][] hits) {

		probability = initializeAI(hits, probability);

		for (int i = 0; i < ships.length; i++) {
			if (ships[i].equals("Carrier")) {
				probability = calculateShipDensity(5, probability);

			} else if (ships[i].equals("Battleship")) {
				probability = calculateShipDensity(4, probability);

			} else if (ships[i].equals("Cruiser")) {
				probability = calculateShipDensity(3, probability);

			} else if (ships[i].equals("Submarine")) {
				probability = calculateShipDensity(3, probability);

			} else if (ships[i].equals("Destroyer")) {
				probability = calculateShipDensity(2, probability);

			}
		}

	}

	public static int[][] initializeAI(int[][] hitGrid, int[][] probability) {
		for (int i = 1; i < hitGrid.length; i++) {
			for (int j = 1; j < (hitGrid[i].length); j++) {
				if (hitGrid[i][j] == -1) {
					// -1 is placeholder for hit marker
					probability[i - 1][j - 1] = -1;
					// -1 is placeholder for hit marker

				} else if (hitGrid[i][j] == -2) {
					// -2 is placeholder for missed or sunk ships

					probability[i - 1][j - 1] = -2;
					// -2 is placeholder for missed or sunk ships

				} else {
					probability[i - 1][j - 1] = 0;
				}
			}
		}
		return probability;
	}

	public static int[][] calculateShipDensity(int shipLength, int[][] probability) {

		for (int i = 0; i <= (probability.length - shipLength); i++) {
			for (int j = 0; j < probability[i].length; j++) {
				for (int k = 0; k < shipLength; k++) {
					if (probability[i + k][j] == -1 || probability[i + k][j] == -2) {
						// -1 is placeholder for hit marker
						// -2 is a placeholder for missed or sunk ships
						break;
					} else if (k == shipLength - 1) {
						for (int l = 0; l < shipLength; l++) {
							probability[i + l][j]++;
						}
					}
				}

			}
		}

		for (int i = 0; i < probability.length; i++) {
			for (int j = 0; j <= (probability[i].length - shipLength); j++) {
				for (int k = 0; k < shipLength; k++) {
					if (probability[i][j + k] == -1 || probability[i][j + k] == -2) {
						// -1 is placeholder for hit marker
						// -2 is a placeholder for missed or sunk ships
						break;
					} else if (k == shipLength - 1) {
						for (int l = 0; l < shipLength; l++) {
							probability[i][j + l]++;
						}
					}
				}

			}
		}
		return probability;
	}
}
