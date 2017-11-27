/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-09 buixuan.
 * ******************************************************/
package data;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import specifications.DataService;
import tools.HardCodedParameters;

public class Data implements DataService{
  //private Heroes hercules;
  Position heroesPosition;
  Position cerisePosition;
  Position monsterPosition;
  TreeMap<Integer,Position> knownPositions;
  ArrayList<Position> checkedPositions;
  
  
  int stepNumber;
  int healthHero;
  
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
    knownPositions = new TreeMap<Integer,Position>();
    checkedPositions = new ArrayList<Position>();
    heroesPosition = new Position(2,3); 
    cerisePosition = new Position(ThreadLocalRandom.current().nextDouble(mapMinX, mapMaxX),ThreadLocalRandom.current().nextDouble(mapMinY, mapMaxY));
    monsterPosition = new Position(ThreadLocalRandom.current().nextDouble(mapMinX, mapMaxX),ThreadLocalRandom.current().nextDouble(mapMinY, mapMaxY));
    
    //ThreadLocalRandom.current().nextInt(0, 700);
    
    healthHero = 3;
    
    
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
public int getHealthHero() {
	return healthHero;
}

@Override
public void setHealthHero(int healthHero) {
	this.healthHero = healthHero;
}

@Override
public void addHealthHero() { healthHero = healthHero+1; }

@Override
public Position getCerisePosition() {
	return cerisePosition;
}
@Override
public void setCerisePosition(Position cerisePosition) {
	this.cerisePosition = cerisePosition;
}

@Override
public Position getMonsterPosition() {
	return monsterPosition;
}
@Override
public void setMonsterPosition(Position monsterPosition) {
	this.monsterPosition = monsterPosition;
}

@Override
public TreeMap<Integer, Position> getKnownPositions() {
	return knownPositions;
}

@Override //si meme position overwrite pour garder dernier state
public void addKnownPositions(double x1,double y1,int state1,double x2,double y2,int state2,double x3,double y3,int state3,double x4,double y4,int state4,double x5,double y5,int state5) { //ajouter condition anti doublons
	
		
			knownPositions.put(new Integer(state1), new Position(x1,y1));
		
			knownPositions.put(new Integer(state2),new Position(x2,y2));
		
			knownPositions.put(new Integer(state3),new Position(x3,y3));
		
			knownPositions.put(new Integer(state4),new Position(x4,y4));
		
			knownPositions.put(new Integer(state5),new Position(x5,y5));

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
  public Position getHeroesPosition(){ return heroesPosition; }

  @Override
  public int getStepNumber(){ return stepNumber; }

  @Override
  public void setHeroesPosition(Position p) { heroesPosition = p; }
  
  @Override
  public void setStepNumber(int n){ stepNumber=n; }
}
