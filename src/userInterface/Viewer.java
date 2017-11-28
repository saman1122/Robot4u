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
    
	  
	  
	    
	    
	  Group panel = new Group();
	  Rectangle heroesAvatar = new Rectangle(data.getRobotPosition().x*HardCodedParameters.zoom,data.getRobotPosition().y*HardCodedParameters.zoom,HardCodedParameters.zoom,HardCodedParameters.zoom);
    heroesAvatar.setFill(Color.rgb(10,10,10));
    heroesAvatar.setEffect(new Lighting());
    //heroesAvatar.setTranslateX(data.getHeroesPosition().x);
    //heroesAvatar.setTranslateY(data.getHeroesPosition().y);
    
    //Limite map
    //Image image = new Image("fond.jpg");
    Rectangle limitMap = new Rectangle(data.getMapMinX()*HardCodedParameters.zoom,data.getMapMinY()*HardCodedParameters.zoom,(data.getMapMaxX()+1)*HardCodedParameters.zoom,(data.getMapMaxY()+1)*HardCodedParameters.zoom);
    limitMap.setFill(Color.rgb(156,216,255,0.2));
    
    //Grille
    double epaisseurLine = 0.5;
    int tailleCase = HardCodedParameters.zoom;
    
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
    Rectangle checked  = new Rectangle(p.x*HardCodedParameters.zoom,p.y*HardCodedParameters.zoom,HardCodedParameters.zoom,HardCodedParameters.zoom);
    checked.setFill(Color.rgb(50,200,50,0.2));
    panel.getChildren().add(checked);
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
