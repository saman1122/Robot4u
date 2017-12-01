/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-09 buixuan.
 * ******************************************************/
package userInterface;

import tools.HardCodedParameters;
import tools.Obstacle;
import tools.Position;
import specifications.ViewerService;
import specifications.Engine4ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.RequireStartEngineService;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Viewer implements ViewerService, RequireReadService, RequireStartEngineService{
	private ReadService data;
	private Engine4ViewerService engine;

	public Viewer(){}

	@Override
	public void bindReadService(ReadService service){
		data=service;
	}

	@Override
	public void bindStartEngineService(Engine4ViewerService service){
		engine=service;
	}

	@Override
	public void init(){
	}

	@Override
	public void startViewer(){
		engine.start();
	}

	@Override
	public Group getPanel(){

		final int zoom=HardCodedParameters.zoom;

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
		for(Position p:engine.getListPositionAlle())
		{
			Rectangle checked  = new Rectangle(p.x*HardCodedParameters.zoom,p.y*HardCodedParameters.zoom,HardCodedParameters.zoom,HardCodedParameters.zoom);
			checked.setFill(Color.rgb(50,200,50,0.2));
			panel.getChildren().add(checked);
		}

		//Génération d'obstacles
		for(Obstacle o:data.getObstaclePositions())
		{
			Rectangle checked  = new Rectangle(o.p.x*HardCodedParameters.zoom,o.p.y*HardCodedParameters.zoom,HardCodedParameters.zoom,HardCodedParameters.zoom);
			checked.setFill(Color.rgb(255,0,0,1));
			panel.getChildren().add(checked);
		}

		//Génération miniMap

		ArrayList<Obstacle> obstacles = engine.getListObstacles();
		ArrayList<Position> positions = engine.getListPositionAlle();

		for (Obstacle obs : obstacles) {
			Rectangle miniMap = new Rectangle(data.getMiniMapMinX()*zoom+zoom+obs.p.x*zoom/2,data.getMiniMapMinY()*zoom+zoom+obs.p.y*zoom/2,zoom/2,zoom/2);

			miniMap.setFill(Color.BLACK);
			panel.getChildren().add(miniMap);
		}

		for (Position p : positions) {
			Rectangle miniMap = new Rectangle(data.getMiniMapMinX()*zoom+zoom+p.x*zoom/2,data.getMiniMapMinY()*zoom+zoom+p.y*zoom/2,zoom/2,zoom/2);

			miniMap.setFill(Color.BLUE);
			panel.getChildren().add(miniMap);
		}

		panel.getChildren().add(heroesAvatar);
		panel.getChildren().add(limitMap);
		return panel;
	}
}
