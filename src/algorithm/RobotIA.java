/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithms/RandomWalker.java 2015-03-09 buixuan.
 * ******************************************************/
package algorithm;

import specifications.AlgorithmService;
import specifications.SimulatorService;
import specifications.RequireSimulatorService;

import java.util.Random;

public class RobotIA implements AlgorithmService, RequireSimulatorService{
	private SimulatorService simulator;
	private Random gen;

	public RobotIA() {
		gen = new Random();
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

		if(simulator.moveLeftCheck() == 1)
			simulator.moveL();
		else
		{
			System.out.println("moveL impossible");
			if(simulator.moveUpCheck() == 1)
				simulator.moveU();
			else
			{
				System.out.println("moveU impossible");
				if(simulator.moveRightCheck() == 1)
					simulator.moveR();
				else
				{
					System.out.println("moveR impossible");
					if(simulator.moveDownCheck() == 1)
						simulator.moveD();
					else
					{
						System.out.println("moveD impossible");
						System.out.println("NO MOVE AVAILABLE");
					}
				}
			}
		}

	}

	@Override
	public void stepActionEmptyRoomV2(){

		if(simulator.moveLeftCheck() == 1)
			simulator.moveL();
		else
		{
			System.out.println("moveL impossible");
			if(simulator.moveUpCheck() == 1)
				simulator.moveU();
			else
			{
				System.out.println("moveU impossible");
				if(simulator.moveRightCheck() == 1)
					simulator.moveR();
				else
				{
					System.out.println("moveR impossible");
					if(simulator.moveDownCheck() == 1)
						simulator.moveD();
					else
					{
						System.out.println("moveD impossible");
						System.out.println("NO NEW MOVE AVAILABLE");

						if(simulator.moveLeftCheck() >= 1)
							simulator.moveL();
						else
						{
							System.out.println("moveL impossible");
							if(simulator.moveUpCheck() >= 1)
								simulator.moveU();
							else
							{
								System.out.println("moveU impossible");
								if(simulator.moveRightCheck() >= 1)
									simulator.moveR();
								else
								{
									System.out.println("moveR impossible");
									if(simulator.moveDownCheck() >= 1)
										simulator.moveD();
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
	public void stepActionTest(){

		simulator.moveU();

	}

	@Override
	public void stepAction(){
		switch (gen.nextInt(4)){
		case 0:
			simulator.moveLeft();
			break;
		case 1:
			simulator.moveRight();
			break;
		case 2:
			simulator.moveUp();
			break;
		default:
			simulator.moveDown();
			break;
		}
	}


}
