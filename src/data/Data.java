/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-09 buixuan.
 * ******************************************************/
package data;

import java.util.ArrayList;
import specifications.DataService;
import tools.Direction;
import tools.Position;

public class Data implements DataService{

	private Position robotPosition;
	private Position positionOrigine;
	private Direction robotDirection;
	private ArrayList<Obstacle> obstaclePositions;
	private ArrayList<Object> obstacleObject;
	private ArrayList<Vetement> listVetement;


	int stepNumber;

	private double mapMinX;
	private double mapMaxX;
	private double mapMinY;
	private double mapMaxY;

	private double miniMapMinX;
	private double miniMapMaxX;
	private double miniMapMinY;
	private double miniMapMaxY;

	public Data(){
		
		stepNumber = 0;

		listVetement = new ArrayList<Vetement>();
		obstaclePositions = new ArrayList<Obstacle>();
		obstacleObject = new ArrayList<Object>();

	}
	
	@Override
	public void setMapMinX(double mapMinX) {
		this.mapMinX = mapMinX;
	}
	
	@Override
	public void setMapMaxX(double mapMaxX) {
		this.mapMaxX = mapMaxX;
		this.miniMapMinX = this.mapMaxX+1;
	}
	
	@Override
	public void setMapMinY(double mapMinY) {
		this.mapMinY = mapMinY;
	}
	
	@Override
	public void setMapMaxY(double mapMaxY) {
		this.mapMaxY = mapMaxY;
		this.miniMapMinY = this.mapMinY;
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
		
	}

	@Override
	public void addObstacleList(ArrayList<Obstacle> list) {
		for (Obstacle obs: list) obstaclePositions.add(obs);
	}

	@Override
	public ArrayList<Object> getObstacleObject() {return this.obstacleObject;}
	
	@Override
	public ArrayList<Vetement> getVetements() {return this.listVetement;}
	
	@Override 
	public void addVetement(Vetement vetement) {
		this.listVetement.add(vetement);
	}
	
	@Override 
	public void setVetements(ArrayList<Vetement> listVetement) {
		this.listVetement = listVetement;
	}
}
