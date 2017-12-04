/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-09 buixuan.
 * ******************************************************/
package data;

import java.util.ArrayList;
import specifications.DataService;
import tools.Canape;
import tools.Direction;
import tools.Lit;
import tools.ObjectObstacle;
import tools.Obstacle;
import tools.Position;

public class Data implements DataService{

	private Position robotPosition;
	private Position positionOrigine;
	private Direction robotDirection;
	private ArrayList<Obstacle> obstaclePositions;
	private ArrayList<Object> obstacleObject;


	int stepNumber;

	double mapMinX;
	double mapMaxX;
	double mapMinY;
	double mapMaxY;

	double miniMapMinX;
	double miniMapMaxX;
	double miniMapMinY;
	double miniMapMaxY;

	public Data(){

		mapMinX = 0;
		mapMaxX = 12;
		mapMinY = 0;
		mapMaxY = 6;

		miniMapMinX = mapMaxX+1;
		miniMapMinY = mapMinY;

		stepNumber = 0;

		obstaclePositions = new ArrayList<Obstacle>();
		obstacleObject = new ArrayList<Object>();

	}

	@Override
	public double getMapMinX() {
		return mapMinX;
	}

	@Override
	public double getMapMaxX() {
		return mapMaxX;
	}

	@Override
	public double getMapMinY() {
		return mapMinY;
	}

	@Override
	public double getMapMaxY() {
		return mapMaxY;
	}

	@Override
	public double getMiniMapMinX() {
		return miniMapMinX;
	}

	@Override
	public double getMiniMapMinY() {
		return miniMapMinY;
	}

	@Override
	public ArrayList<Obstacle> getObstaclePositions() {return obstaclePositions;}

	@Override
	public void addObstaclePositions(double x,double y) {
		obstaclePositions.add(new Obstacle(new Position(x,y)));
	}

	@Override
	public Position getRobotPosition(){ return robotPosition; }

	@Override
	public int getStepNumber(){ return stepNumber; }

	@Override
	public void setRobotPosition(Position p) { robotPosition = p; }

	@Override
	public void setStepNumber(int n){ stepNumber=n; }

	@Override
	public Direction getRobotDirection() { return robotDirection; }

	@Override
	public void setRobotDirection(Direction direction) {this.robotDirection = direction;}

	@Override
	public Position getRobotInitPosition() {return this.positionOrigine;}

	@Override
	public void setRobotInitPosition(Position p) {this.positionOrigine = p;}

	@Override
	public void addObstacleObject(Object obj) {
		Class objClass = obj.getClass();
		
		obstacleObject.add(obj);
		
		ObjectObstacle objet = (ObjectObstacle) obj;
		
		addObstacleList(objet.getListPoints());
		
//		if (objClass == Canape.class) {
//			Canape canape = (Canape) obj;
//			addObstacleList(canape.getListPoints());
//		}else if(objClass == Lit.class) {
//			Lit lit = (Lit) obj;
//			addObstacleList(lit.getListPoints());
//		}
		
	}

	@Override
	public void addObstacleList(ArrayList<Obstacle> list) {
		for (Obstacle obs: list) obstaclePositions.add(obs);
	}

	@Override
	public ArrayList<Object> getObstacleObject() {return this.obstacleObject;}
}
