import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner; // Import the Scanner class

//H5
//Cameron Falls

public class my_HW5 {

	public static void printQueue(Queue<Integer> tempQ) {
		for (int i = 0; i < tempQ.size(); i++) {
			int tempval = tempQ.remove();
			System.out.print(tempval + ",");
			tempQ.add(tempval);
		}
	}

	public static Integer calcManDist(myNode tempNode, int goalRow, int goalColumn) {
		// calculate Manhattan Distance
		int manDist = Math.abs(tempNode.getNodeY() - goalRow) + Math.abs(tempNode.getNodeX() - goalColumn);
		return manDist;
	}

	public static void printMap(String[][] my_map, int robotRow, int robotColumn, int goalRow, int goalColumn,
			int manDist) {
		// printing out my map
		System.out.print("  ");
		for (int i = 0; i < 15; i++) {
			if (i > 9) {
				System.out.print(" " + i);
			} else {
				System.out.print("  " + i);
			}

		}
		System.out.print("\n");
		for (int i = 0; i < 15; i++) {
			System.out.print(i);
			if (i < 10) {
				System.out.print("  ");
			} else {
				System.out.print(" ");
			}

			for (int j = 0; j < 15; j++) {
				System.out.print("{" + my_map[i][j] + "}");
			}
			System.out.print("\n");
		}

		System.out.print("The robot is at row " + robotRow + " column " + robotColumn + "\n");
		System.out.print("The goal is at row " + goalRow + " column " + goalColumn + "\n");
		// System.out.print("The robot's current Manhattan Distance = " + manDist +
		// "\n");
	}

	public static void addToFrontier(String[][] my_map, myNode tempNode, Queue<Integer> myFrontier,
			Queue<Integer> myVisited, int goalRow, int goalColumn, myNode[] myNodeMap, int tempX, int tempY) {
		if (my_map[tempY][tempX].equalsIgnoreCase("E") || my_map[tempY][tempX].equalsIgnoreCase("G")) {
			int frontNodePos = (tempY * 15) + tempX;

			if ((!myVisited.contains(frontNodePos)) && (!myFrontier.contains(frontNodePos))) {
				myFrontier.add(frontNodePos);
				// System.out.println("Adding above r " + (tempNode.getNodeY() - 1) + " c " +
				// tempNode.getNodeX() + " val = "+ tempNodePos);
				myNode frontNode = new myNode();
				frontNode.setNodeX(tempX);
				frontNode.setNodeY(tempY);
				frontNode.setDistTrav(tempNode.getDistTrav() + 1);
				int tempManDist = calcManDist(frontNode, goalRow, goalColumn);
				frontNode.setManDist(tempManDist);

				if ((myNodeMap[frontNodePos].getManDist()
						+ myNodeMap[frontNodePos].getDistTrav()) < (frontNode.getManDist() + frontNode.getDistTrav())) {
					myNodeMap[frontNodePos] = frontNode;
				}

				ArrayList<Integer> myPathTemp = new ArrayList<Integer>();

				// System.out.println("");

//				myPathTemp = tempNode.getMyPath();
//				System.out.println(myPathTemp);
//				myPathTemp.add(frontNodePos);
//				myNodeMap[frontNodePos].setMyPath(tempNode.getMyPath());

				// System.out.println("the old node");
				// System.out.println(tempNode.getMyPath());
				for (int i = 0; i < tempNode.getMyPath().size(); i++) {
					myPathTemp.add(tempNode.getMyPath().get(i));
				}
				// System.out.println("the copy of the old node");
				// System.out.println(tempNode.getMyPath());
				myPathTemp.add(frontNodePos);
				// System.out.println("the copy of the old node + the new node");
				// System.out.println(tempNode.getMyPath());
				myNodeMap[frontNodePos].setMyPath(myPathTemp);
				// System.out.println("the new node");
				// System.out.println(myNodeMap[frontNodePos].getMyPath());
				// System.out.println("the old node again");
				// System.out.println(tempNode.getMyPath());

			}

		}
	}

	public static void checkFrontier(String[][] my_map, myNode tempNode, Queue<Integer> myFrontier,
			Queue<Integer> myVisited, int goalRow, int goalColumn, myNode[] myNodeMap) {

		// above
		int tempY = (tempNode.getNodeY() - 1);
		int tempX = tempNode.getNodeX();
		addToFrontier(my_map, tempNode, myFrontier, myVisited, goalRow, goalColumn, myNodeMap, tempX, tempY);
		// right
		tempY = tempNode.getNodeY();
		tempX = (tempNode.getNodeX() + 1);
		addToFrontier(my_map, tempNode, myFrontier, myVisited, goalRow, goalColumn, myNodeMap, tempX, tempY);
		// below
		tempY = (tempNode.getNodeY() + 1);
		tempX = tempNode.getNodeX();
		addToFrontier(my_map, tempNode, myFrontier, myVisited, goalRow, goalColumn, myNodeMap, tempX, tempY);
		// left
		tempY = tempNode.getNodeY();
		tempX = (tempNode.getNodeX() - 1);
		addToFrontier(my_map, tempNode, myFrontier, myVisited, goalRow, goalColumn, myNodeMap, tempX, tempY);

	}

	public static void main(String[] args) {

		int gameOn = 1;
		System.out.println("Welcome to the A* Machine!");
		while (gameOn == 1) {

			// creating my array map
			String[][] my_map = new String[15][15];
			// filling my map with strings that say empty
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					my_map[i][j] = "E";
				}
			}

			// creating a border around the map
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (j == 0) {
						my_map[i][j] = "O";
					}
					if (j == 14) {
						my_map[i][j] = "O";
					}
					if (i == 0) {
						my_map[i][j] = "O";
					}
					if (i == 14) {
						my_map[i][j] = "O";
					}
				}
			}

			Random rand = new Random();

			// placing the robot

			Scanner robotS = new Scanner(System.in); // Create a Scanner object
			System.out.println("\nRobot Row (between 1 and 13) = ");

			String robotRowString = robotS.nextLine(); // Read user input

			System.out.println("\nRobot column (between 1 and 13) = ");

			String robotColumnString = robotS.nextLine(); // Read user input

			int robotRow = Integer.parseInt(robotRowString);
			int robotColumn = Integer.parseInt(robotColumnString);
			my_map[robotRow][robotColumn] = "R";

			// placing the goal
			System.out.println("\nGoal Row (between 1 and 13) = ");

			String goalRowString = robotS.nextLine(); // Read user input

			System.out.println("\nGoal column (between 1 and 13) = ");

			String goalColumnString = robotS.nextLine(); // Read user input

			int goalRow = Integer.parseInt(goalRowString);
			int goalColumn = Integer.parseInt(goalColumnString);
			my_map[goalRow][goalColumn] = "G";

			// filling my map with obstacles

			int obs_count = 0;
			while (obs_count < 22) {
				// System.out.print("obs = " + obs_count + "\n");
				int n1 = rand.nextInt(15);
				int n2 = rand.nextInt(15);
				if (my_map[n1][n2].equalsIgnoreCase("E")) {
					my_map[n1][n2] = "O";
					obs_count = obs_count + 1;
				}
				n1 = rand.nextInt(15);
				n2 = rand.nextInt(15);
			}

			// my_map[y][x]

			// creating my Queue
			// PriorityQueue<Integer> myVisited = new PriorityQueue<>();
			Queue<Integer> myVisited = new LinkedList<>();
			// PriorityQueue<Integer> myFrontier = new PriorityQueue<>();
			Queue<Integer> myFrontier = new LinkedList<>();
			int robotPos = (robotRow * 15) + robotColumn;
			int goalPos = (goalRow * 15) + goalColumn;
			myNode robotNode = new myNode();
			robotNode.setNodeX(robotColumn);
			robotNode.setNodeY(robotRow);
			robotNode.setDistTrav(0);

			// calculate Manhattan Distance
			int manDist = calcManDist(robotNode, goalRow, goalColumn);
			robotNode.setManDist(manDist);
			printMap(my_map, robotRow, robotColumn, goalRow, goalColumn, manDist);

			// creating a node map to keep track of the nodes we look at
			myNode[] myNodeMap = new myNode[225];

			for (int i = 0; i < 225; i++) {
				myNode blankNode = new myNode();
				blankNode.setNodeX(-1);
				blankNode.setNodeY(-1);
				blankNode.setDistTrav(-1);
				blankNode.setManDist(-1);
				myNodeMap[i] = blankNode;
			}

			myNodeMap[robotPos] = robotNode;

			myFrontier.add(robotPos);
			checkFrontier(my_map, robotNode, myFrontier, myVisited, goalRow, goalColumn, myNodeMap);

			// checkFrontier(my_map, robotNode, myFrontier, myVisited, goalRow, goalColumn,
			// myNodeMap);

//			System.out.print("Frontier = ");
//			printQueue(myFrontier);
//			System.out.print("\nVisited = ");
//			printQueue(myVisited);

			int goalFound = 0;
			int mycount = 1;
			ArrayList<Integer> bestPath = new ArrayList<Integer>();
			while ((mycount < 225) && goalFound == 0) {

				int smallestValue = 10000;
				int frontSize = myFrontier.size();
				int bestNode = -1;

				for (int i = 0; i < frontSize; i++) {
					int nextNodePos = myFrontier.remove();
					myFrontier.add(nextNodePos);
					int nextNodeAValue = myNodeMap[nextNodePos].getDistTrav() + myNodeMap[nextNodePos].getManDist();
					if (nextNodeAValue < smallestValue) {
						smallestValue = nextNodeAValue;
						bestNode = nextNodePos;
					}

				}
				// System.out.println("");
				// System.out.print("\nAdding a Node row " + myNodeMap[bestNode].getNodeY() + "
				// column " + myNodeMap[bestNode].getNodeX());

				myFrontier.remove(bestNode);

				myVisited.add(bestNode);

				if (bestNode == goalPos) {
					// System.out.print("\nLADDIES AND GENTLEMAN, WE GOT HIM");
					goalFound = 1;
					bestPath = myNodeMap[bestNode].getMyPath();
				}
				
				if (!myFrontier.isEmpty()) {
					checkFrontier(my_map, myNodeMap[bestNode], myFrontier, myVisited, goalRow, goalColumn, myNodeMap);
				}
				
				// System.out.print("\niteration " + mycount);
				// System.out.print("\nFrontier = ");
				// printQueue(myFrontier);
				// System.out.print("\nVisited = ");
				// printQueue(myVisited);

				mycount = mycount + 1;
//			if (goalFound == 0) {
//				Scanner myObj = new Scanner(System.in); // Create a Scanner object
//				System.out.println("\nPress y to keep going");
//
//				String inputUser = myObj.nextLine(); // Read user input
//				if (!inputUser.equals("y")) {
//					mycount = 1000;
//				}
//			}

			}
			if (goalFound == 1) {
				int bestPathSize = bestPath.size();
				System.out.print("\nPath\n");
				System.out.print("row " + robotRow + " column " + robotColumn + "\n");
				for (int i = 0; i < bestPathSize; i++) {
					int tempPathVal = bestPath.get(i);
					int tempPathX = tempPathVal % 15;
					int tempPathY = tempPathVal / 15;

					System.out.print("row " + tempPathY + " column " + tempPathX + "\n");
					my_map[tempPathY][tempPathX] = "P";

				}

				System.out.println("");
				my_map[goalRow][goalColumn] = "G";
				printMap(my_map, robotRow, robotColumn, goalRow, goalColumn, manDist);
			}else {
				System.out.print("\nNo Path found\n");
			}

			System.out.println("\nType 1 to play again");

			String gameInputString = robotS.nextLine(); // Read user input

			gameOn = Integer.parseInt(gameInputString);

		}
		System.out.println("GoodBye!");
	}

}
