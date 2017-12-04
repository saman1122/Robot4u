/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithms/RandomWalker.java 2015-03-09 buixuan.
 * ******************************************************/
package algorithm;

import specifications.AlgorithmService;
import specifications.SimulatorService;
import tools.Direction;
import tools.Obstacle;
import tools.Position;
import tools.SensorSimulator;
import specifications.RequireSimulatorService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class RobotIA implements AlgorithmService, RequireSimulatorService{
	private SimulatorService simulator;

	private int mapping[][];
	private ArrayList<Position> listPositionAlle;
	private ArrayList<Obstacle> listObstacle;

	private Direction direction;
	private Position currentPosition;
	public final static String UP = "U", DOWN = "D", RIGHT = "R", LEFT = "L";

	private boolean mappingFinish;

	public RobotIA() {
		direction = Direction.NORD;
		currentPosition = new Position(0, 0);

		listObstacle = new ArrayList<Obstacle>();
		listPositionAlle = new ArrayList<Position>();
		listPositionAlle.add(currentPosition);

		mappingFinish = false;
	}

	@Override
	public void bindSimulatorService(SimulatorService service){
		simulator = service;
	}

	@Override
	public void activation(){
		if (moveLeft()) while (moveUp());

		//		if (moveRight()) {
		//			while (!moveLeft()) moveUp();
		//
		//			while (moveUp());
		//		}

		direction = Direction.NORD;
		//simulator.moveRight();
	}

	public boolean mapping() {
		boolean retour = false;
		if (!mappingFinish) {

			useSensor();

			if(moveLeft()) {
			}else {
				System.out.println("moveL impossible");
				if(moveUp()) {
				}else {
					System.out.println("moveU impossible");
					if(moveRight()) {
					}else {
						System.out.println("moveR impossible");
						if(moveDown()) {
						}else {
							System.out.println("moveD impossible");
							System.out.println("NO MOVE AVAILABLE");
						}
					}
				}
			}

			if (countNbrTour() == 5) {
				createMapping();

				mappingFinish = true;
			}



			System.out.println("Current position" + currentPosition);
		}

		return retour;
	}

	private void createMapping() {
		int xMin = Integer.MAX_VALUE,xMax = Integer.MIN_VALUE, yMin = Integer.MAX_VALUE, yMax = Integer.MIN_VALUE;
		for (Obstacle obs: listObstacle) {
			if (obs.p.x < xMin) xMin = (int) obs.p.x;
			if (obs.p.x > xMax) xMax = (int) obs.p.x;
			if (obs.p.y < yMin) yMin = (int) obs.p.y;
			if (obs.p.y > yMax) yMax = (int) obs.p.y;
		}
		mapping = new int[yMax - yMin + 1][];

		for (int i = 0; i < mapping.length; i++) {
			mapping[i] = new int [xMax - xMin + 1];
		}


		for (int i = 0; i<mapping.length;i++) {
			for (int j = 0; j < mapping[i].length;j++) {
				mapping[i][j]=0;
			}
		}

		for (Obstacle obs: listObstacle) {
			mapping[(int)obs.p.y - yMin][(int)obs.p.x - xMin] = 1;
		}

		for (Position p: listPositionAlle) {
			mapping[(int)p.y - yMin][(int)p.x - xMin] = 2;
		}

		for (int i = 0; i<mapping.length;i++) {
			System.out.println();
			for (int j = 0; j < mapping[i].length;j++) {
				System.out.print( mapping[i][j] + "|");
			}
		}
		System.out.println();
		System.out.println();

	}

	@Override
	public void stepAction(){
		if (mapping()) {
			//Mapping finish
		}
	}

	public void changeDirection(String newMove) {
		switch (newMove) {
		case LEFT:
			if (direction == Direction.NORD) {
				direction = Direction.OUEST;
			}else if (direction == Direction.EST) {
				direction = Direction.NORD;
			}else if (direction == Direction.SUD) { 
				direction = Direction.EST;
			}else if (direction == Direction.OUEST) { 
				direction = Direction.SUD;
			}
			break;
		case RIGHT:
			if (direction == Direction.NORD) {
				direction = Direction.EST;
			}else if (direction == Direction.EST) {
				direction = Direction.SUD;
			}else if (direction == Direction.SUD) {
				direction = Direction.OUEST;
			}else if (direction == Direction.OUEST) {
				direction = Direction.NORD;
			}
			break;
		case UP:
			if (direction == Direction.NORD) {
				direction = Direction.NORD;
			}else if (direction == Direction.EST) {
				direction = Direction.EST;
			}else if (direction == Direction.SUD) {
				direction = Direction.SUD;
			}else if (direction == Direction.OUEST) {
				direction = Direction.OUEST;
			}
			break;
		case DOWN:
			if (direction == Direction.NORD) {
				direction = Direction.SUD;
			}else if (direction == Direction.EST) {
				direction = Direction.OUEST;
			}else if (direction == Direction.SUD) {
				direction = Direction.NORD;
			}else if (direction == Direction.OUEST) {
				direction = Direction.EST;
			}
			break;
		}
	}

	private void updateCurrentPosition(String move) {

		currentPosition = getPositionAt(move);

		listPositionAlle.add(currentPosition);

	}

	public boolean moveLeft() {
		boolean retour = false;
		int checkMove = simulator.moveLeftCheck(direction);

		if (checkMove == 0) listObstacle.add(new Obstacle(getPositionAt(LEFT)));

		if(checkMove >= 1) {
			simulator.moveL(direction);
			updateCurrentPosition(LEFT);
			changeDirection(LEFT);
			retour = true;
		}

		return retour;
	}

	public boolean moveRight() {
		boolean retour = false;
		int checkMove = simulator.moveRightCheck(direction);

		if (checkMove == 0) listObstacle.add(new Obstacle(getPositionAt(RIGHT)));

		if(checkMove >= 1) {
			simulator.moveR(direction);
			updateCurrentPosition(RIGHT);
			changeDirection(RIGHT);
			retour = true;
		}

		return retour;
	}

	public boolean moveUp() {
		boolean retour = false;
		int checkMove = simulator.moveUpCheck(direction);

		if (checkMove == 0) listObstacle.add(new Obstacle(getPositionAt(UP)));

		if(checkMove >= 1) {
			simulator.moveU(direction);
			updateCurrentPosition(UP);
			changeDirection(UP);
			retour = true;
		}

		return retour;
	}
	public boolean moveDown() {
		boolean retour = false;
		int checkMove = simulator.moveDownCheck(direction);

		if (checkMove == 0) listObstacle.add(new Obstacle(getPositionAt(DOWN)));

		if(checkMove >= 1) {
			simulator.moveD(direction);
			updateCurrentPosition(DOWN);
			changeDirection(DOWN);
			retour = true;
		}

		return retour;
	}

	private Position getPositionAt(String position) {
		double x = currentPosition.x, y = currentPosition.y;

		if (direction == Direction.NORD) {
			switch (position) {
			case LEFT:
				x -= 1;
				break;
			case RIGHT:
				x += 1;
				break;
			case UP:
				y -= 1;
				break;
			case DOWN:
				y += 1;
				break;
			}
		}else if (direction == Direction.SUD) {
			switch (position) {
			case LEFT:
				x += 1;
				break;
			case RIGHT:
				x -= 1;
				break;
			case UP:
				y += 1;
				break;
			case DOWN:
				y -= 1;
				break;
			}
		}else if (direction == Direction.EST) {
			switch (position) {
			case UP:
				x += 1;
				break;
			case DOWN:
				x -= 1;
				break;
			case LEFT:
				y -= 1;
				break;
			case RIGHT:
				y += 1;
				break;
			}
		}else if (direction == Direction.OUEST) {
			switch (position) {
			case UP:
				x -= 1;
				break;
			case DOWN:
				x += 1;
				break;
			case LEFT:
				y += 1;
				break;
			case RIGHT:
				y -= 1;
				break;
			}
		}

		return new Position(x, y);
	}

	private void useSensor() {
		SensorSimulator sensor = simulator.getSensorResult(direction);

		Position obsL = sensor.getObstacleL();
		Position obsR = sensor.getObstacleR();
		Position obsD = sensor.getObstacleD();
		Position obsU = sensor.getObstacleU();

		obsL.x += currentPosition.x;
		obsL.y += currentPosition.y;
		obsR.x += currentPosition.x;
		obsR.y += currentPosition.y;
		obsU.x += currentPosition.x;
		obsU.y += currentPosition.y;
		obsD.x += currentPosition.x;
		obsD.y += currentPosition.y;

		listObstacle.add(new Obstacle(obsL));
		listObstacle.add(new Obstacle(obsR));
		listObstacle.add(new Obstacle(obsU));
		listObstacle.add(new Obstacle(obsD));
	}

	private int countNbrTour() {
		int retour = 0;

		Set<Position> unique = new HashSet<Position>(listPositionAlle);
		for (Position key : unique) {
			int nbr = Collections.frequency(listPositionAlle, key);
			if (nbr > retour) retour = nbr;
		}

		return retour;
	}

	@Override
	public int[][] getMapping() {return mapping;}

	@Override
	public ArrayList<Position> getListPositionAlle() {return listPositionAlle;}

	@Override
	public ArrayList<Obstacle> getListObstacle() {return listObstacle;}
}
