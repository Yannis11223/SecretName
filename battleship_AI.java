package BattleShip;

public class battleship_AI {
	private final String[] Y_AXIS_LABEL = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
	private final String[] X_AXIS_LABEL = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

	private final int WIDTH = 10;
	private final int HEIGHT = 10;
	private final int numberOfShips = 5;

	private int[][] probability = new int[HEIGHT][WIDTH];
	private boolean[] activeShips = new boolean[numberOfShips];

	public battleship_AI() {

		for (int i = 0; i < activeShips.length; i++) {
			activeShips[i] = true;
		}

		for (int i = 0; i < probability.length; i++) {
			for (int j = 0; j < (probability[i].length); j++) {
				probability[i][j] = 0;
			}
		}

	}

	public String getMove() {
		String coordinate = Y_AXIS_LABEL[bestMove(activeShips, probability)[0]];
		coordinate += X_AXIS_LABEL[bestMove(activeShips, probability)[1]];
		return coordinate;
	}

	public void setHit(String hitCoordinate) {
		int[] hitIndex = coordinateToIndex(hitCoordinate);
		probability[hitIndex[0]][hitIndex[1]] = -1;
	}

	public void setMiss(String hitCoordinate) {
		int[] hitIndex = coordinateToIndex(hitCoordinate);
		probability[hitIndex[0]][hitIndex[1]] = -2;
	}

	public void setSunk(String startCoordinate, String endCoordinate) {

		int[] startIndex = coordinateToIndex(startCoordinate);
		int[] endIndex = coordinateToIndex(endCoordinate);

		if (startIndex[0] == endIndex[0]) {
			// if the ship is horizontal
			for (int i = 0; i < (endIndex[1] - startIndex[1] + 1); i++) {
				probability[startIndex[0]][startIndex[1] + i] = -2;
			}

		} else {
			// if the ship is vertical
			for (int i = 0; i < (endIndex[0] - startIndex[0] + 1); i++) {
				probability[startIndex[0] + i][startIndex[1]] = -2;
			}
		}
	}

	private int[] coordinateToIndex(String coordinate) {

		int[] indexes = new int[2];

		String[] CoordinateSplit = coordinate.split("");

		for (int i = 0; i < Y_AXIS_LABEL.length; i++) {
			if (CoordinateSplit[0].equals(Y_AXIS_LABEL[i])) {
				indexes[0] = i;
			}
		}
		for (int i = 0; i < X_AXIS_LABEL.length; i++) {
			if (CoordinateSplit[1].equals(X_AXIS_LABEL[i])) {
				indexes[1] = i;
			}
		}
		return indexes;
	}

	private int[] bestMove(boolean[] ships, int[][] probability) {

		int[] moveCoordinates = new int[2];
		// best Coordinates in form y x
		int hitCount = 0;
		int maxProbability = 0;

		calculateDensity(ships, probability);

		for (int i = 0; i < probability.length; i++) {
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
			return moveCoordinates;

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

						return moveCoordinates;
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
						return moveCoordinates;

					}
				}
			}
		}

		return moveCoordinates;
	}

	private void calculateDensity(boolean[] remainingShips, int[][] probability) {

		if (remainingShips[0] == true) {
			probability = calculateShipDensity(5, probability);

		} else if (remainingShips[1] == true) {
			probability = calculateShipDensity(4, probability);

		} else if (remainingShips[2] == true) {
			probability = calculateShipDensity(3, probability);

		} else if (remainingShips[3] == true) {
			probability = calculateShipDensity(3, probability);

		} else if (remainingShips[4] == true) {
			probability = calculateShipDensity(2, probability);

		}
	}

	private int[][] calculateShipDensity(int shipLength, int[][] probability) {

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
