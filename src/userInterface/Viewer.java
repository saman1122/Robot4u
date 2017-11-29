/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-09 buixuan.
 * ******************************************************/
package userInterface;

import tools.HardCodedParameters;

import specifications.ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.StartEngineService;
import specifications.RequireStartEngineService;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import data.Obstacle;
import data.Position;
import javafx.scene.Group;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Viewer implements ViewerService, RequireReadService, RequireStartEngineService{
  private ReadService data;
  private StartEngineService startEngine;

  public Viewer(){}

  @Override
  public void bindReadService(ReadService service){
    data=service;
  }
  
  @Override
  public void bindStartEngineService(StartEngineService service){
    startEngine=service;
  }

  @Override
  public void init(){
  }

  @Override
  public void startViewer(){
    startEngine.start();
  }

  @Override
  public Group getPanel(){
    
	  
	  final int zoom=HardCodedParameters.zoom;
	    
	    
	  Group panel = new Group();
	  Rectangle heroesAvatar = new Rectangle(data.getRobotPosition().x*zoom,data.getRobotPosition().y*zoom,zoom,zoom);
    heroesAvatar.setFill(Color.rgb(10,10,10));
    heroesAvatar.setEffect(new Lighting());
    //heroesAvatar.setTranslateX(data.getHeroesPosition().x);
    //heroesAvatar.setTranslateY(data.getHeroesPosition().y);
    
    //Limite map
    //Image image = new Image("fond.jpg");
    Rectangle limitMap = new Rectangle(data.getMapMinX()*zoom,data.getMapMinY()*zoom,(data.getMapMaxX()+1)*zoom,(data.getMapMaxY()+1)*zoom);
    limitMap.setFill(Color.rgb(156,216,255,0.2));
    
    //Grille
    double epaisseurLine = 0.5;
    int tailleCase = zoom;
    
    for(int i=0; i<=data.getMapMaxX()+1; i++)
    {
      //lignes verticales
      Line l = new Line(tailleCase * i, data.getMapMinY(), tailleCase * i, (data.getMapMaxY()+1)*tailleCase ); 
      l.setStrokeWidth(epaisseurLine);
      l.setStroke(Color.BLACK);
      panel.getChildren().add(l);
      if(i<=data.getMapMaxY()+1)
      {     //lignes horizontales
        l = new Line(data.getMapMinX(), tailleCase * i, (data.getMapMaxX()+1)*tailleCase, tailleCase * i ); 
        l.setStrokeWidth(epaisseurLine);
        l.setStroke(Color.BLACK);
        panel.getChildren().add(l);
      }
    }
    
    //Marquer passage checké
    for(Position p:data.getCheckedPositions())
    {
    Rectangle checked  = new Rectangle(p.x*zoom,p.y*zoom,zoom,zoom);
    checked.setFill(Color.rgb(50,200,50,0.2));
    panel.getChildren().add(checked);
    }
    
    /*
    //Génération d'obstacles
    for(Obstacle o:data.getObstaclePositions())
    {
    Rectangle checked  = new Rectangle(o.p.x*zoom,o.p.y*zoom,zoom,zoom);
    checked.setFill(Color.rgb(255,0,0,1));
    panel.getChildren().add(checked);
    }
    */

    //Génération miniMap
    //Rectangle miniMap = new Rectangle(data.getMiniMapMinX()*zoom+zoom,data.getMiniMapMinY()*zoom);
    
    /*
    Set<Entry<Position,Integer>> set = data.getKnownPositions().entrySet();
    Iterator<Map.Entry<Position, Integer>> it = set.iterator();

    while(it.hasNext()){
       Map.Entry<Position, Integer> entry = it.next();
       Rectangle miniMap = new Rectangle(data.getMiniMapMinX()*zoom+zoom+entry.getKey().x*zoom,data.getMiniMapMinY()*zoom+entry.getKey().y*zoom,50,50);
       System.out.println("coordonnees : "+entry.getKey().x+","+entry.getKey().y+"  etat : "+entry.getValue());
       miniMap.setFill(Color.rgb(10,10,10,0.5));
       panel.getChildren().add(miniMap);
    }
    */
    
    
    Set<Position> keys = data.getKnownPositions().keySet();
	for(Position key: keys){
		   Rectangle miniMap = new Rectangle(data.getMiniMapMinX()*zoom+zoom+key.x*zoom/2,data.getMiniMapMinY()*zoom+zoom+key.y*zoom/2,zoom/2,zoom/2);
	       System.out.println("coordonnees : "+key.x+","+key.y+" state : "+data.getKnownPositions().get(key));
	       
	       if(data.getKnownPositions().get(key) == 0)//obstacle
	       miniMap.setFill(Color.rgb(255,0,0,1));
	       
	       if(data.getKnownPositions().get(key) == 1)//non connu
		       miniMap.setFill(Color.rgb(0,0,0,0.5));
	       
	       if(data.getKnownPositions().get(key) >= 2)//connu
		       miniMap.setFill(Color.rgb(50,200,50,0.2));
	       
	       
	       panel.getChildren().add(miniMap);
	}
	
    
    
    
    /*
    //Objet immobile : cerise
    Circle ceriseAvatar = new Circle(2,  Color.rgb(255,100,10));
    ceriseAvatar.setEffect(new Lighting());
    ceriseAvatar.setTranslateX(data.getCerisePosition().x);
    ceriseAvatar.setTranslateY(data.getCerisePosition().y);
    
    //Objet mobile : monster
    Circle monsterAvatar = new Circle(2,  Color.rgb(10,100,255));
    monsterAvatar.setEffect(new Lighting());
    monsterAvatar.setTranslateX(data.getMonsterPosition().x);
    monsterAvatar.setTranslateY(data.getMonsterPosition().y);
    */

    
    panel.getChildren().add(heroesAvatar);
    panel.getChildren().add(limitMap);
    //panel.getChildren().add(grille);
    //panel.getChildren().add(grille2);
    //panel.getChildren().add(ceriseAvatar);
    return panel;
  }
}
