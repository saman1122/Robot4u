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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import data.Obstacle;

public class Engine implements EngineService, RequireDataService, RequireAlgorithmService{
<<<<<<< HEAD
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
		String axe = direction.getAxe();
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
		String axe = direction.getAxe();
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
		String axe = direction.getAxe();
		Position position = new Position(data.getRobotPosition().x, data.getRobotPosition().y);

		if (nom.equals(Direction.NORD.getNom())) position.y -= 1;
		if (nom.equals(Direction.SUD.getNom())) position.y += 1;
		if (nom.equals(Direction.EST.getNom())) position.x += 1;
		if (nom.equals(Direction.OUEST.getNom())) position.x -= 1;

		//		if (direction.getSigne() == "-") {
		//			if (axe == "y") position.y -= 1;
		//			if (axe == "x") position.x -= 1;
		//		}else {
		//			if (axe == "y") position.y += 1;
		//			if (axe == "x") position.x += 1;
		//		}

		data.setRobotPosition(position);
	}

	@Override
	public void moveD(Direction direction){
		String nom = direction.getNom();
		String axe = direction.getAxe();
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
		String axe = direction.getAxe();
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
		String axe = direction.getAxe();
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
		String axe = direction.getAxe();
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
		String axe = direction.getAxe();
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

=======
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
    	data.addKnownPositions(data.getRobotPosition().x-1,data.getRobotPosition().y,moveLeftCheck());//left
		data.addKnownPositions(data.getRobotPosition().x,data.getRobotPosition().y-1,moveUpCheck());//up
		data.addKnownPositions(data.getRobotPosition().x+1,data.getRobotPosition().y,moveRightCheck());//right
		data.addKnownPositions(data.getRobotPosition().x,data.getRobotPosition().y+1,moveDownCheck());//down
    	
    	
        algorithm.stepActionEmptyRoom();
        
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
  public void moveL(){
    data.setRobotPosition(new Position(data.getRobotPosition().x-1,data.getRobotPosition().y));
  }
  
  @Override
  public void moveR(){
    data.setRobotPosition(new Position(data.getRobotPosition().x+1,data.getRobotPosition().y));
  }
  
  @Override
  public void moveU(){
    data.setRobotPosition(new Position(data.getRobotPosition().x,data.getRobotPosition().y-1));
  }
  
  @Override
  public void moveD(){
    data.setRobotPosition(new Position(data.getRobotPosition().x,data.getRobotPosition().y+1));
  }
  
  @Override
  public int moveLeftCheck(){
	  int r;
	  if(data.getRobotPosition().x > data.getMapMinX())
	  {
		  r=1;
		  for(Position p : data.getCheckedPositions()){
			 if(p.x==data.getRobotPosition().x-1 && p.y==data.getRobotPosition().y)
				 r=2;
		  }
	  }
	  else
		  r= 0;
	  return r;
  }
  
  @Override
  public int moveRightCheck(){
	  int r;
	  if(data.getRobotPosition().x < data.getMapMaxX())
	  {
		  r=1;
		  for(Position p : data.getCheckedPositions()){
			 if(p.x==data.getRobotPosition().x+1 && p.y==data.getRobotPosition().y)
				 r=2;
		  }
	  }
	  else
		  r= 0;
	  return r;
  }
  
  @Override
  public int moveUpCheck(){
	  int r;
	  if(data.getRobotPosition().y > data.getMapMinY())
	  {
		  r=1;
		  for(Position p : data.getCheckedPositions()){
			 if(p.x==data.getRobotPosition().x && p.y==data.getRobotPosition().y-1)
				 r=2;
		  }
	  }
	  else
		  r= 0;
	  return r;
  }
  
  @Override
  public int moveDownCheck(){
	  int r;
	  if(data.getRobotPosition().y < data.getMapMaxY())
	  {
		  r=1;
		  for(Position p : data.getCheckedPositions()){
			 if(p.x==data.getRobotPosition().x && p.y==data.getRobotPosition().y+1)
				 r=2;
		  }
	  }
	  else
		  r= 0;
	  return r;
  }
  
  @Override
  public boolean moveLeft(){
	  if(data.getRobotPosition().x > data.getMapMinX())
	  {
    data.setRobotPosition(new Position(data.getRobotPosition().x-1,data.getRobotPosition().y));
    return true;
	  }
	  else
		  return false;
  }
  
  @Override
  public boolean moveRight(){
	  if(data.getRobotPosition().x < data.getMapMaxX())
	  {
    data.setRobotPosition(new Position(data.getRobotPosition().x+1,data.getRobotPosition().y));
    return true;
	  }
	  else
		  return false;
  }
  
  @Override
  public boolean moveUp(){
	  if(data.getRobotPosition().y > data.getMapMinY())
	  {
    data.setRobotPosition(new Position(data.getRobotPosition().x,data.getRobotPosition().y+1));
    return true;
	  }
	  else
		  return false;
  }
  
  @Override
  public boolean moveDown(){
	  if(data.getRobotPosition().y < data.getMapMaxY())
	  {
    data.setRobotPosition(new Position(data.getRobotPosition().x,data.getRobotPosition().y-1));
    return true;
	  }
	  else
		  return false;
  }
  
  
>>>>>>> 16dcb4783878f028d18181c482f696a84cb8a703
}
