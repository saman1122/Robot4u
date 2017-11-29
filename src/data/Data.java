/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-09 buixuan.
 * ******************************************************/
package data;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import specifications.DataService;
import tools.Direction;
import tools.Position;

public class Data implements DataService{
  //private Heroes hercules;
  Position robotPosition;
  Direction robotDirection;
  ConcurrentHashMap<Position,Integer> knownPositions;
  ArrayList<Position> checkedPositions;
  
  
  int stepNumber;
  
  double mapMinX;
  double mapMaxX;
  double mapMinY;
  double mapMaxY;

  public Data(){
	  
    mapMinX = 0;
	mapMaxX = 4;
	mapMinY = 0;
	mapMaxY = 3;
	
	stepNumber = 0;
	
    //hercules = new Heroes;
    //heroesPosition = new Position((int) (Math.random() * mapMaxX-mapMinX )+mapMinX,(int) (Math.random() * mapMaxY-mapMinY )+mapMinY); 
    //cerisePosition = new Position((int) (Math.random() * mapMaxX-mapMinX )+mapMinX,(int) (Math.random() * mapMaxY-mapMinY )+mapMinY);
    //monsterPosition = new Position((int) (Math.random() * mapMaxX-mapMinX )+mapMinX,(int) (Math.random() * mapMaxY-mapMinY )+mapMinY);
    knownPositions = new ConcurrentHashMap<Position,Integer>();
    checkedPositions = new ArrayList<Position>();
    robotPosition = new Position(2,2); 
    
    
    //ThreadLocalRandom.current().nextInt(0, 700);
    
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
public ConcurrentHashMap<Position,Integer> getKnownPositions() {
	return knownPositions;
}

@Override //si meme position overwrite pour garder dernier state
public void addKnownPositions(double x,double y,int state) {
	
	/*
	Position position = new Position(x, y);
	Set<Position> keys = getKnownPositions().keySet();

    for(Position key: keys){
        System.out.println("coord : "+key.x+","+key.y+" état : "+ getKnownPositions().get(key));
        if(position.equals(getKnownPositions().get(key)))
        {
        	knownPositions.remove(new Position(x,y));
        }
    }*/
	//knownPositions.remove(new Position(x,y));
    knownPositions.put(new Position(x,y) , state);
	/*
	Set<Entry<Position,Integer>> set = getKnownPositions().entrySet();
    Iterator<Map.Entry<Position, Integer>> it = set.iterator();
    
    //int a=0;
    
    //ArrayList<Position> listASuppr = new ArrayList<Position>();
    while(it.hasNext()){
       Map.Entry<Position, Integer> entry = it.next();
       if(position.equals(entry.getKey()))
       {
    	   //listASuppr.add(entry.getKey());
    	   //System.out.print(entry.getKey());
    	   knownPositions.remove(new Position(x,y));
       //a=1;
       }
    }
    */

	/*
    if(a==0)
    {
    */
		
	//}
	
		/*
	Set<Position> keys = getKnownPositions().keySet();

    for(Position key: keys){
        System.out.println("coord : "+key.x+","+key.y+" état : "+ getKnownPositions().get(key));
    }
    */
			

}


@Override
public ArrayList<Position> getCheckedPositions() {//Extraction depuis knownPosition
	/* 
	Set<Integer> keys = getKnownPositions().keySet();
	for(int key: keys){
        if(key==1)
        	checkedPositions.add(getKnownPositions().get(key));       	
    }
	*/
	return checkedPositions;
}

@Override
public void addCheckedPositions(double x,double y) {//garde doublon pour mesurer l'optimisation
	checkedPositions.add(new Position(x,y));
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
}
