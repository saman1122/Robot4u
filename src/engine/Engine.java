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
import specifications.AlgorithmService;
import specifications.RequireAlgorithmService;

import data.Position;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Enumeration;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

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
  }

  @Override
  public void start(){
    algorithm.activation();
    engineClock.schedule(new TimerTask(){
      public void run() {
    	  
    	  start++;
    	  if(start>2)
    	  {
    	  System.out.println("\n --------Heroes position: "+data.getHeroesPosition().x+", "+data.getHeroesPosition().y);
    	data.addCheckedPositions(data.getHeroesPosition().x,data.getHeroesPosition().y);
    	
    	
    	System.out.print(moveLeftCheck());
    	System.out.print(moveUpCheck());
    	System.out.print(moveDownCheck());
    	System.out.print(moveRightCheck());
    	data.addKnownPositions(
    			data.getHeroesPosition().x,data.getHeroesPosition().y,1,//position
    			data.getHeroesPosition().x-1,data.getHeroesPosition().y,moveLeftCheck(),//left
    			data.getHeroesPosition().x,data.getHeroesPosition().y-1,moveUpCheck(),//up
    			data.getHeroesPosition().x+1,data.getHeroesPosition().y,moveRightCheck(),//right
    			data.getHeroesPosition().x,data.getHeroesPosition().y+1,moveDownCheck()//down
    			);
    	
    	
        algorithm.stepActionEmptyRoom();
        
        data.setStepNumber(data.getStepNumber()+1);
        
        System.out.print(" \n positions known \n");
        
        Set<Integer> keys = data.getKnownPositions().keySet();

        for(int key: keys){
            System.out.println("coord : "+data.getKnownPositions().get(key).x+","+data.getKnownPositions().get(key).y+" état : "+ key);
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
    data.setHeroesPosition(new Position(data.getHeroesPosition().x-1,data.getHeroesPosition().y));
  }
  
  @Override
  public void moveR(){
    data.setHeroesPosition(new Position(data.getHeroesPosition().x+1,data.getHeroesPosition().y));
  }
  
  @Override
  public void moveU(){
    data.setHeroesPosition(new Position(data.getHeroesPosition().x,data.getHeroesPosition().y-1));
  }
  
  @Override
  public void moveD(){
    data.setHeroesPosition(new Position(data.getHeroesPosition().x,data.getHeroesPosition().y+1));
  }
  
  @Override
  public int moveLeftCheck(){
	  int r;
	  if(data.getHeroesPosition().x > data.getMapMinX())
	  {
		  r=0;
		  for(Position p : data.getCheckedPositions()){
			 if(p.x==data.getHeroesPosition().x-1 && p.y==data.getHeroesPosition().y)
				 r=1;
		  }
	  }
	  else
		  r= 2;
	  return r;
  }
  
  @Override
  public int moveRightCheck(){
	  int r;
	  if(data.getHeroesPosition().x < data.getMapMaxX())
	  {
		  r=0;
		  for(Position p : data.getCheckedPositions()){
			 if(p.x==data.getHeroesPosition().x+1 && p.y==data.getHeroesPosition().y)
				 r=1;
		  }
	  }
	  else
		  r= 2;
	  return r;
  }
  
  @Override
  public int moveUpCheck(){
	  int r;
	  if(data.getHeroesPosition().y > data.getMapMinY())
	  {
		  r=0;
		  for(Position p : data.getCheckedPositions()){
			 if(p.x==data.getHeroesPosition().x && p.y==data.getHeroesPosition().y-1)
				 r=1;
		  }
	  }
	  else
		  r= 2;
	  return r;
  }
  
  @Override
  public int moveDownCheck(){
	  int r;
	  if(data.getHeroesPosition().y < data.getMapMaxY())
	  {
		  r=0;
		  for(Position p : data.getCheckedPositions()){
			 if(p.x==data.getHeroesPosition().x && p.y==data.getHeroesPosition().y+1)
				 r=1;
		  }
	  }
	  else
		  r= 2;
	  return r;
  }
  
  @Override
  public boolean moveLeft(){
	  if(data.getHeroesPosition().x > data.getMapMinX())
	  {
    data.setHeroesPosition(new Position(data.getHeroesPosition().x-1,data.getHeroesPosition().y));
    return true;
	  }
	  else
		  return false;
  }
  
  @Override
  public boolean moveRight(){
	  if(data.getHeroesPosition().x < data.getMapMaxX())
	  {
    data.setHeroesPosition(new Position(data.getHeroesPosition().x+1,data.getHeroesPosition().y));
    return true;
	  }
	  else
		  return false;
  }
  
  @Override
  public boolean moveUp(){
	  if(data.getHeroesPosition().y > data.getMapMinY())
	  {
    data.setHeroesPosition(new Position(data.getHeroesPosition().x,data.getHeroesPosition().y+1));
    return true;
	  }
	  else
		  return false;
  }
  
  @Override
  public boolean moveDown(){
	  if(data.getHeroesPosition().y < data.getMapMaxY())
	  {
    data.setHeroesPosition(new Position(data.getHeroesPosition().x,data.getHeroesPosition().y-1));
    return true;
	  }
	  else
		  return false;
  }
  
  @Override
  public void moveLeftMonster(){
	  if(data.getMonsterPosition().x > data.getMapMinX())
    data.setMonsterPosition(new Position(data.getMonsterPosition().x-1,data.getMonsterPosition().y));
  }
  
  @Override
  public void moveRightMonster(){
	  if(data.getMonsterPosition().x < data.getMapMaxX())
    data.setMonsterPosition(new Position(data.getMonsterPosition().x+1,data.getMonsterPosition().y));
  }
  
  @Override
  public void moveUpMonster(){
	  if(data.getMonsterPosition().y > data.getMapMinY())
    data.setMonsterPosition(new Position(data.getMonsterPosition().x,data.getMonsterPosition().y-1));
  }
  
  @Override
  public void moveDownMonster(){
	  if(data.getMonsterPosition().y < data.getMapMaxY())
    data.setMonsterPosition(new Position(data.getMonsterPosition().x,data.getMonsterPosition().y+1));
  }
  
  @Override
  public void collisionCerise(){
	  data.addHealthHero();
	  data.setCerisePosition(new Position(ThreadLocalRandom.current().nextDouble(data.getMapMinX(), data.getMapMaxX()),ThreadLocalRandom.current().nextDouble(data.getMapMinX(), data.getMapMaxX())));
	  
  }
  
  @Override
  public void collisionMonster(){
	  data.setMonsterPosition(new Position(ThreadLocalRandom.current().nextDouble(data.getMapMinX(), data.getMapMaxX()),ThreadLocalRandom.current().nextDouble(data.getMapMinX(), data.getMapMaxX())));
  }
}
