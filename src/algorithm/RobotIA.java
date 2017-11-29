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
import specifications.RequireSimulatorService;
import java.util.Random;

public class RobotIA implements AlgorithmService, RequireSimulatorService{
	private SimulatorService simulator;
	private Random gen;
	private Direction direction;

	public RobotIA() {
		gen = new Random();
		direction = Direction.NORD;
	}

	@Override
	public void bindSimulatorService(SimulatorService service){
		simulator = service;
	}

	@Override
	public void activation(){
		//simulator.moveRight();
	}


	@Override
	public void stepActionEmptyRoom(){

		if(simulator.moveLeftCheck(direction) == 1) {
			simulator.moveL(direction);
			changeDirection("L");
		}else
		{
			System.out.println("moveL impossible");
			if(simulator.moveUpCheck(direction) == 1) {
				simulator.moveU(direction);
				changeDirection("U");
			}else
			{
				System.out.println("moveU impossible");
				if(simulator.moveRightCheck(direction) == 1) {
					simulator.moveR(direction);
					changeDirection("R");
				}else
				{
					System.out.println("moveR impossible");
					if(simulator.moveDownCheck(direction) == 1) {
						simulator.moveD(direction);
						changeDirection("D");
					}else
					{
						System.out.println("moveD impossible");
						System.out.println("NO MOVE AVAILABLE");
						
						if(simulator.moveLeftCheck(direction) >= 1) {
							simulator.moveL(direction);
							changeDirection("L");
						}else {
							System.out.println("moveL impossible");
							if(simulator.moveUpCheck(direction) >= 1) {
								simulator.moveU(direction);
								changeDirection("U");
							} else {
								System.out.println("moveU impossible");
								if(simulator.moveRightCheck(direction) >= 1) { 
									simulator.moveR(direction);
									changeDirection("U");
								} else {
									System.out.println("moveR impossible");
									if(simulator.moveDownCheck(direction) >= 1) {
										simulator.moveD(direction);
										changeDirection("R");
									}
									else
									{
										System.out.println("moveD impossible");
										System.out.println("NO MOVE AVAILABLE");
									}
								}
							}
						}
					}
				}
			}
		}

	}

	@Override
	public void stepActionEmptyRoomV2(){
		//
		//		if(simulator.moveLeftCheck() > 0)
		//			simulator.moveL();
		//		else
		//		{
		//			System.out.println("moveL impossible");
		//			if(simulator.moveUpCheck() > 0)
		//				simulator.moveU();
		//			else
		//			{
		//				System.out.println("moveU impossible");
		//				if(simulator.moveRightCheck() > 0)
		//					simulator.moveR();
		//				else
		//				{
		//					System.out.println("moveR impossible");
		//					if(simulator.moveDownCheck() > 0)
		//						simulator.moveD();
		//					else
		//					{
		//						System.out.println("moveD impossible");
		//						System.out.println("NO NEW MOVE AVAILABLE");
		//
		//						if(simulator.moveLeftCheck() >= 1)
		//							simulator.moveL();
		//						else
		//						{
		//							System.out.println("moveL impossible");
		//							if(simulator.moveUpCheck() >= 1)
		//								simulator.moveU();
		//							else
		//							{
		//								System.out.println("moveU impossible");
		//								if(simulator.moveRightCheck() >= 1)
		//									simulator.moveR();
		//								else
		//								{
		//									System.out.println("moveR impossible");
		//									if(simulator.moveDownCheck() >= 1)
		//										simulator.moveD();
		//									else
		//									{
		//										System.out.println("moveD impossible");
		//										System.out.println("NO MOVE AVAILABLE");
		//									}
		//								}
		//							}
		//						}
		//					}
		//				}
		//			}
		//		}
		//
	}

	@Override
	public void stepActionTest(){

	}

	@Override
	public void stepAction(){
		//		switch (gen.nextInt(4)){
		//		case 0:
		//			simulator.moveLeft();
		//			break;
		//		case 1:
		//			simulator.moveRight();
		//			break;
		//		case 2:
		//			simulator.moveUp();
		//			break;
		//		default:
		//			simulator.moveDown();
		//			break;
		//		}
	}

	public void changeDirection(String newMove) {
		switch (newMove) {
		case "L":
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
		case "R":
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
		case "U":
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
		case "D":
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


}
