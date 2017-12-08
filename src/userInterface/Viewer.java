/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-09 buixuan.
 * ******************************************************/
package userInterface;

import tools.HardCodedParameters;
import tools.Position;
import specifications.ViewerService;
import specifications.Engine4ViewerService;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.RequireStartEngineService;

import java.net.NetworkInterface;
import java.util.ArrayList;

import data.Canape;
import data.Chaise;
import data.Lit;
import data.ObjectObstacle;
import data.Obstacle;
import data.TableBasse;
import data.TypeVetement;
import data.Vetement;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Viewer implements ViewerService, RequireReadService, RequireStartEngineService{
	private ReadService data;
	private Engine4ViewerService engine;
	private Image imgTableBasse, imgLit, imgCanape, imgChaise, imgRobotN, imgRobotS, imgRobotE, imgRobotW, 
	imgVetementHaut, imgVetementBas;

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
		imgCanape = new Image("file:src/images/canapeVueHaut.jpg");
		imgLit = new Image("file:src/images/litVueHaut.jpg");
		imgChaise = new Image("file:src/images/chaise.jpg");
		imgTableBasse = new Image("file:src/images/tableBasse.png");
		imgRobotN = new Image("file:src/images/robotN.png");
		imgRobotS = new Image("file:src/images/robotS.png");
		imgRobotE = new Image("file:src/images/robotE.png");
		imgRobotW = new Image("file:src/images/robotW.png");
		imgVetementBas = new Image("file:src/images/vetementBas.png");
		imgVetementHaut = new Image("file:src/images/vetementHaut.png");
	}

	@Override
	public void startViewer(){
		engine.start();
	}

	@Override
	public Group getPanel(){

		final int zoom=HardCodedParameters.zoom;

		Group panel = new Group();
		ImageView heroesAvatar = null;

		switch(data.getRobotDirection()) {
		case NORD:
			heroesAvatar = new ImageView(imgRobotN);
			break;
		case SUD:
			heroesAvatar = new ImageView(imgRobotS);
			break;
		case EST:
			heroesAvatar = new ImageView(imgRobotE);
			break;
		case OUEST:
			heroesAvatar = new ImageView(imgRobotW);
			break;
		}

		heroesAvatar.setTranslateX(data.getRobotPosition().x*zoom);
		heroesAvatar.setTranslateY(data.getRobotPosition().y*zoom);
		heroesAvatar.setFitHeight(1*zoom);
		heroesAvatar.setFitWidth(1*zoom);

		//Limite map
		//Image image = new Image("fond.jpg");
		Rectangle limitMap = new Rectangle(data.getMapMinX()*zoom,data.getMapMinY()*zoom,(data.getMapMaxX()+1)*zoom,(data.getMapMaxY()+1)*zoom);
		limitMap.setFill(Color.rgb(156,216,255,0.2));


		//Marquer passage checké
		for(Position p:engine.getListPositionAlle())
		{
			Rectangle checked  = new Rectangle(p.x*zoom,p.y*zoom,zoom,zoom);
			checked.setFill(Color.rgb(50,200,50,0.2));
			panel.getChildren().add(checked);
		}

		//Génération d'obstacles
		for(Obstacle o:data.getObstaclePositions())
		{
			Rectangle checked  = new Rectangle(o.p.x*zoom,o.p.y*zoom,zoom,zoom);
			checked.setFill(Color.RED);
			panel.getChildren().add(checked);
		}

		//Génération vetements
		for(Vetement v:data.getVetements())
		{
			ImageView img = null;
			Rectangle checked  = new Rectangle(v.getPosition().x*zoom,v.getPosition().y*zoom,zoom,zoom);
			
			img = new ImageView(imgVetementBas);
			if (v.getType() == TypeVetement.HAUT) {
				img = new ImageView(imgVetementHaut);
			}
			
			img.setTranslateX(v.getPosition().x*zoom);
			img.setTranslateY(v.getPosition().y*zoom);
			img.setFitHeight(1*zoom);
			img.setFitWidth(1*zoom);
			
			panel.getChildren().add(img);
		}

		//Generation obstacle objets

		for(Object obj: data.getObstacleObject()) {

			ObjectObstacle objtO = (ObjectObstacle) obj;
			ImageView img = null;

			if (obj.getClass() == Canape.class) {
				img = new ImageView(imgCanape);
			} else if (obj.getClass() == Lit.class) {
				img = new ImageView(imgLit);
			} else if (obj.getClass() == Chaise.class) {
				img = new ImageView(imgChaise);
			} else if (obj.getClass() == TableBasse.class) {
				img = new ImageView(imgTableBasse);
			}

			img.setTranslateX(objtO.getFirst().x*zoom);
			img.setTranslateY(objtO.getFirst().y*zoom);
			img.setFitHeight(objtO.getHeight()*zoom);
			img.setFitWidth(objtO.getWidth()*zoom);

			panel.getChildren().add(img);

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
