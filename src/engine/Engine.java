/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-09 buixuan.
 * ******************************************************/
package engine;

import specifications.EngineService;
import specifications.DataService;
import specifications.RequireDataService;
import tools.Direction;
import tools.Position;
import specifications.AlgorithmService;
import specifications.RequireAlgorithmService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import data.Obstacle;

public class Engine implements EngineService, RequireDataService, RequireAlgorithmService{
	private Timer engineClock;
	private DataService data;
	private AlgorithmService algorithm;
	private int start = 0;

	public Engine(){}

	@Override
	public void bindDataService(DataService service){
		data=service;
	}

	@Override
	public void bindAlgorithmService(AlgorithmService service){
		algorithm=service;
	}

	@Override
	public void init(){
		engineClock = new Timer();


		//Creation des obstacles
		data.addObstaclePositions(1,3);
		data.addObstaclePositions(2,3);
		data.addObstaclePositions(3,3);

		data.addObstaclePositions(0,1);
		data.addObstaclePositions(2,1);
		data.addObstaclePositions(3,1);
		//obstaclePositions.add(new Obstacle(new Position(4,1)));

		//initialisation position robot

		boolean onObstacle = true;
		Position initialPosRobot = null;

		while (onObstacle) {
			onObstacle = false;
			initialPosRobot = new Position(ThreadLocalRandom.current().nextInt((int)data.getMapMinX(), (int)data.getMapMaxX() + 1),
					ThreadLocalRandom.current().nextInt((int)data.getMapMinY(), (int)data.getMapMaxY() + 1));

			ArrayList<Obstacle> obstacles = data.getObstaclePositions();

			for (Obstacle obs:obstacles) {
				if(obs.p.equals(initialPosRobot)) onObstacle = true;
			}

		}

		data.setRobotPosition(initialPosRobot);
		data.setRobotInitPosition(initialPosRobot);

	}

	@Override
	public void start(){
		algorithm.activation();
		engineClock.schedule(new TimerTask(){
			@Override
			public void run() {

				start++;
				if(start>2)
				{
					System.out.println("\n --------Heroes position: "+data.getRobotPosition().x+", "+data.getRobotPosition().y);
					data.addCheckedPositions(data.getRobotPosition().x,data.getRobotPosition().y);


					//ajoute coordonnées à la map (x,y,state) | state : 0 = obstacle | state : 1 = non connu | state : 2 = connu
					data.addKnownPositions(data.getRobotPosition().x,data.getRobotPosition().y,2);//current position
					data.addKnownPositions(data.getRobotPosition().x-1,data.getRobotPosition().y,moveLeftCheck(Direction.NORD));//left
					data.addKnownPositions(data.getRobotPosition().x,data.getRobotPosition().y-1,moveUpCheck(Direction.NORD));//up
					data.addKnownPositions(data.getRobotPosition().x+1,data.getRobotPosition().y,moveRightCheck(Direction.NORD));//right
					data.addKnownPositions(data.getRobotPosition().x,data.getRobotPosition().y+1,moveDownCheck(Direction.NORD));//down


					algorithm.stepAction();

					data.setStepNumber(data.getStepNumber()+1);

					System.out.print(" \n positions known \n");

					Set<Entry<Position,Integer>> set = data.getKnownPositions().entrySet();
					Iterator<Map.Entry<Position, Integer>> it = set.iterator();

					while(it.hasNext()){
						Map.Entry<Position, Integer> entry = it.next();
						System.out.println("coord : "+entry.getKey().x+","+entry.getKey().y+"  etat : "+entry.getValue());
					}

					System.out.println(" \n pos checked");
					for (Position p:data.getCheckedPositions()){
						System.out.print("liste de positionX "+p.x);
						System.out.print("liste de positionY "+p.y);
					}

				}
			}
		},0,700);
	}


	@Override
	public void moveL(Direction direction){
		String nom = direction.getNom();
		Position position = new Position(data.getRobotPosition().x, data.getRobotPosition().y);

		if (nom.equals(Direction.NORD.getNom())) position.x -= 1;
		if (nom.equals(Direction.SUD.getNom())) position.x += 1;
		if (nom.equals(Direction.EST.getNom())) position.y -= 1;
		if (nom.equals(Direction.OUEST.getNom())) position.y += 1;

		data.setRobotPosition(position);
	}

	@Override
	public void moveR(Direction direction){
		String nom = direction.getNom();
		Position position = new Position(data.getRobotPosition().x, data.getRobotPosition().y);

		if (nom.equals(Direction.NORD.getNom())) position.x += 1;
		if (nom.equals(Direction.SUD.getNom())) position.x -= 1;
		if (nom.equals(Direction.EST.getNom())) position.y += 1;
		if (nom.equals(Direction.OUEST.getNom())) position.y -= 1;

		data.setRobotPosition(position);
	}

	@Override
	public void moveU(Direction direction){
		String nom = direction.getNom();
		Position position = new Position(data.getRobotPosition().x, data.getRobotPosition().y);

		if (nom.equals(Direction.NORD.getNom())) position.y -= 1;
		if (nom.equals(Direction.SUD.getNom())) position.y += 1;
		if (nom.equals(Direction.EST.getNom())) position.x += 1;
		if (nom.equals(Direction.OUEST.getNom())) position.x -= 1;

		data.setRobotPosition(position);
	}

	@Override
	public void moveD(Direction direction){
		String nom = direction.getNom();
		Position position = new Position(data.getRobotPosition().x, data.getRobotPosition().y);

		if (nom.equals(Direction.NORD.getNom())) position.y += 1;
		if (nom.equals(Direction.SUD.getNom())) position.y -= 1;
		if (nom.equals(Direction.EST.getNom())) position.x -= 1;
		if (nom.equals(Direction.OUEST.getNom())) position.x += 1;

		data.setRobotPosition(position);
	}

	@Override
	public int moveLeftCheck(Direction direction){
		int r;
		boolean resultatIf = false;
		String nom = direction.getNom();
		Position position = null;

		if (nom.equals(Direction.NORD.getNom())) {
			position = new Position(data.getRobotPosition().x - 1, data.getRobotPosition().y);
			resultatIf = (data.getRobotPosition().x - 1 >= data.getMapMinX());
		}
		if (nom.equals(Direction.SUD.getNom())) {
			position = new Position(data.getRobotPosition().x + 1, data.getRobotPosition().y);
			resultatIf = (data.getRobotPosition().x + 1 <= data.getMapMaxX());
		}
		if (nom.equals(Direction.EST.getNom())) {
			position = new Position(data.getRobotPosition().x, data.getRobotPosition().y - 1);
			resultatIf = (data.getRobotPosition().y - 1 >= data.getMapMinY());
		}
		if (nom.equals(Direction.OUEST.getNom())) {
			position = new Position(data.getRobotPosition().x, data.getRobotPosition().y + 1);
			resultatIf = (data.getRobotPosition().y + 1 <= data.getMapMaxY());
		}

		if(resultatIf)
		{
			r=1;
			for(Position p : data.getCheckedPositions()){
				if(p.x==position.x && p.y==position.y)
					r=2;
			}
		} else {
			r= 0;
		}

		for(Obstacle o : data.getObstaclePositions())
		{
			if(o.p.x == position.x && o.p.y == position.y)
				r = 0;
		}
		return r;
	}

	@Override
	public int moveRightCheck(Direction direction){
		int r;
		boolean resultatIf = false;
		String nom = direction.getNom();
		Position position = null;

		if (nom.equals(Direction.SUD.getNom())) {
			position = new Position(data.getRobotPosition().x - 1, data.getRobotPosition().y);
			resultatIf = (data.getRobotPosition().x - 1 >= data.getMapMinX());
		}
		if (nom.equals(Direction.NORD.getNom())) {
			position = new Position(data.getRobotPosition().x + 1, data.getRobotPosition().y);
			resultatIf = (data.getRobotPosition().x + 1 <= data.getMapMaxX());
		}
		if (nom.equals(Direction.OUEST.getNom())) {
			position = new Position(data.getRobotPosition().x, data.getRobotPosition().y - 1);
			resultatIf = (data.getRobotPosition().y - 1 >= data.getMapMinY());
		}
		if (nom.equals(Direction.EST.getNom())) {
			position = new Position(data.getRobotPosition().x, data.getRobotPosition().y + 1);
			resultatIf = (data.getRobotPosition().y + 1 <= data.getMapMaxY());
		}

		if(resultatIf)
		{
			r=1;
			for(Position p : data.getCheckedPositions()){
				if(p.x==position.x && p.y==position.y)
					r=2;
			}
		} else {
			r= 0;
		}

		for(Obstacle o : data.getObstaclePositions())
		{
			if(o.p.x == position.x && o.p.y == position.y)
				r = 0;
		}
		return r;
	}

	@Override
	public int moveUpCheck(Direction direction){
		int r;
		boolean resultatIf = false;
		String nom = direction.getNom();
		Position position = null;

		if (nom.equals(Direction.OUEST.getNom())) {
			position = new Position(data.getRobotPosition().x - 1, data.getRobotPosition().y);
			resultatIf = (data.getRobotPosition().x - 1 >= data.getMapMinX());
		}
		if (nom.equals(Direction.EST.getNom())) {
			position = new Position(data.getRobotPosition().x + 1, data.getRobotPosition().y);
			resultatIf = (data.getRobotPosition().x + 1 <= data.getMapMaxX());
		}
		if (nom.equals(Direction.NORD.getNom())) {
			position = new Position(data.getRobotPosition().x, data.getRobotPosition().y - 1);
			resultatIf = (data.getRobotPosition().y - 1 >= data.getMapMinY());
		}
		if (nom.equals(Direction.SUD.getNom())) {
			position = new Position(data.getRobotPosition().x, data.getRobotPosition().y + 1);
			resultatIf = (data.getRobotPosition().y + 1 <= data.getMapMaxY());
		}

		if(resultatIf)
		{
			r=1;
			for(Position p : data.getCheckedPositions()){
				if(p.x==position.x && p.y==position.y)
					r=2;
			}
		} else {
			r= 0;
		}

		for(Obstacle o : data.getObstaclePositions())
		{
			if(o.p.x == position.x && o.p.y == position.y)
				r = 0;
		}
		return r;
	}

	@Override
	public int moveDownCheck(Direction direction){
		int r;
		boolean resultatIf = false;
		String nom = direction.getNom();
		Position position = null;

		if (nom.equals(Direction.EST.getNom())) {
			position = new Position(data.getRobotPosition().x - 1, data.getRobotPosition().y);
			resultatIf = (data.getRobotPosition().x - 1 >= data.getMapMinX());
		}
		if (nom.equals(Direction.OUEST.getNom())) {
			position = new Position(data.getRobotPosition().x + 1, data.getRobotPosition().y);
			resultatIf = (data.getRobotPosition().x + 1 <= data.getMapMaxX());
		}
		if (nom.equals(Direction.SUD.getNom())) {
			position = new Position(data.getRobotPosition().x, data.getRobotPosition().y - 1);
			resultatIf = (data.getRobotPosition().y - 1 >= data.getMapMinY());
		}
		if (nom.equals(Direction.NORD.getNom())) {
			position = new Position(data.getRobotPosition().x, data.getRobotPosition().y + 1);
			resultatIf = (data.getRobotPosition().y + 1 <= data.getMapMaxY());
		}

		if(resultatIf)
		{
			r=1;
			for(Position p : data.getCheckedPositions()){
				if(p.x==position.x && p.y==position.y)
					r=2;
			}
		} else {
			r= 0;
		}

		for(Obstacle o : data.getObstaclePositions())
		{
			if(o.p.x == position.x && o.p.y == position.y)
				r = 0;
		}
		return r;
	}

	@Override
	public ArrayList<Obstacle> getListObstacles() {
		ArrayList<Obstacle> returnList = new ArrayList<Obstacle>();
		ArrayList<Obstacle> list = algorithm.getListObstacle();
		Position initialRobot = data.getRobotInitPosition();

		for (Obstacle obs: list) {
			double x = initialRobot.x + obs.p.x;
			double y = initialRobot.y + obs.p.y;

			returnList.add(new Obstacle(new Position(x, y)));
		}

		return returnList;
	}

	@Override
	public ArrayList<Position> getListPositionAlle() {
		ArrayList<Position> returnList = new ArrayList<Position>();
		ArrayList<Position> list = algorithm.getListPositionAlle();
		Position initialRobot = data.getRobotInitPosition();

		for (Position p: list) {
			double x = initialRobot.x + p.x;
			double y = initialRobot.y + p.y;

			returnList.add(new Position(x, y));
		}

		return returnList;
	}

	@Override
	public int[][] getMapping() {
		int retour[][] = getMapping();

		for (int i=0; i < retour.length; i++) {
			for (int j=0; j < retour.length;j++);
		}

		return retour;
	}
}
