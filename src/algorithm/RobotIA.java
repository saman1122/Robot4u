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
import tools.Position;
import specifications.RequireSimulatorService;

import java.util.ArrayList;
import java.util.Random;
import data.Obstacle;

public class RobotIA implements AlgorithmService, RequireSimulatorService{
	private SimulatorService simulator;
	private Random gen;

	private int mapping[][];
	private ArrayList<Position> listPositionAlle;
	private ArrayList<Obstacle> listObstacle;
	
	private Direction direction;
	private Position currentPosition;
	public final static String UP = "U", DOWN = "D", RIGHT = "R", LEFT = "L";

	private boolean mappingFinish;

	public RobotIA() {
		gen = new Random();
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
		

		direction = Direction.NORD;
		//simulator.moveRight();
	}

	public boolean mapping() {
		boolean retour = false;
		if (!mappingFinish) {
			boolean stepRealised = false;

			while(!stepRealised) {

				if(moveLeft()) {
					stepRealised = true;
				}else
				{
					System.out.println("moveL impossible");
					if(moveUp()) {
						stepRealised = true;
					}else
					{
						System.out.println("moveU impossible");
						if(moveRight()) {
							stepRealised = true;
						}else
						{
							System.out.println("moveR impossible");
							if(moveDown()) {
								stepRealised = true;
							}else
							{
								System.out.println("moveD impossible");
								System.out.println("NO MOVE AVAILABLE");
							}
						}
					}
				}
			}

			System.out.println("Current position" + currentPosition);
		}

		return retour;
	}

	@Override
	public void stepAction(){
		if (mapping()) {

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
		
		if (checkMove == 0) listObstacle.add(new Obstacle(new Position(0, 0)));

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

		if(simulator.moveRightCheck(direction) >= 1) {
			simulator.moveR(direction);
			updateCurrentPosition(RIGHT);
			changeDirection(RIGHT);
			retour = true;
		}

		return retour;
	}

	public boolean moveUp() {
		boolean retour = false;

		if(simulator.moveUpCheck(direction) >= 1) {
			simulator.moveU(direction);
			updateCurrentPosition(UP);
			changeDirection(UP);
			retour = true;
		}

		return retour;
	}
	public boolean moveDown() {
		boolean retour = false;

		if(simulator.moveDownCheck(direction) >= 1) {
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


}
