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
import tools.HardCodedParameters;
import tools.Position;
import specifications.AlgorithmService;
import specifications.RequireAlgorithmService;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import data.Canape;
import data.Chaise;
import data.Lit;
import data.ObjectObstacle;
import data.Obstacle;
import data.TableBasse;
import data.TypeVetement;
import data.Vetement;

import java.util.Timer;
import java.util.TimerTask;

public class Engine implements EngineService, RequireDataService, RequireAlgorithmService{
	private Timer engineClock;
	private DataService data;
	private AlgorithmService algorithm;
	private static final int nbrEssaiMaxRandom = 10;

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

		//Generation taille map

		int maxX = HardCodedParameters.maxX;
		int maxY = HardCodedParameters.maxY;

		data.setMapMinY(0);
		data.setMapMinX(0);
		data.setMapMaxY(ThreadLocalRandom.current().nextInt(4, maxY + 1));
		data.setMapMaxX(ThreadLocalRandom.current().nextInt(8, maxX + 1));

		//initialisation position robot

		boolean onObstacle = true;
		int nbrEssai = 0;
		Position initialPosRobot = null;

		while (onObstacle) {
			onObstacle = false;
			initialPosRobot = new Position(ThreadLocalRandom.current().nextInt((int)data.getMapMinX(), (int)data.getMapMaxX() + 1),
					ThreadLocalRandom.current().nextInt((int)data.getMapMinY(), (int)data.getMapMaxY() + 1));

			ArrayList<Obstacle> obstacles = data.getObstaclePositions();

			for (Obstacle obs:obstacles) {
				if(obs.p.equals(initialPosRobot)) onObstacle = true;
			}

			nbrEssai++;

			if (nbrEssai > nbrEssaiMaxRandom) {
				initialPosRobot = new Position(0, 0);
				onObstacle = false;
			}

		}

		data.setRobotPosition(initialPosRobot);
		data.setRobotInitPosition(initialPosRobot);

		//		//Creation des obstacles
		//		int nbrObstacles = 15;
		//
		//		for (int i = 0; i < nbrObstacles; i++) {
		//			boolean isNotNewPosition = true;
		//			Position p = null;
		//			while(isNotNewPosition) {
		//				isNotNewPosition = false;
		//
		//				p = new Position(ThreadLocalRandom.current().nextInt((int)data.getMapMinX(), (int)data.getMapMaxX() + 1),
		//						ThreadLocalRandom.current().nextInt((int)data.getMapMinY(), (int)data.getMapMaxY() + 1));
		//
		//				ArrayList<Obstacle> listObstacles = data.getObstaclePositions();
		//				for(Obstacle obs: listObstacles) {
		//					if (p.equals(obs.p)) isNotNewPosition = true;
		//				}
		//			}
		//
		//			data.addObstaclePositions(p.x,p.y);
		//		}

		//Creation object obstacles
		int nbrCanape = 1;
		int nbrLit = 1;
		int nbrChaise = 2;
		int nbrTableBasse = 1;
		try {
			for (int i = 0; i < nbrCanape;i++)
				data.addObstacleObject(createCanape());
		}catch (Exception e) {
			// TODO: handle exception
		}
		try {
			for (int i = 0; i < nbrLit;i++)
				data.addObstacleObject(createLit());
		}catch (Exception e) {
			// TODO: handle exception
		}
		try {
			for (int i = 0; i < nbrChaise;i++)
				data.addObstacleObject(createChaise());
		}catch (Exception e) {
			// TODO: handle exception
		}
		try {
			for (int i = 0; i < nbrTableBasse;i++)
				data.addObstacleObject(createTableBasse());
		}catch (Exception e) {
			// TODO: handle exception
		}
		

		//Ajout vetements
		int nbrVetement = 4;

		for (int i = 0; i < nbrVetement; i++) {
			Vetement vetement = new Vetement();
			boolean isNotNewPosition = true;
			Position p = null;
			while(isNotNewPosition) {
				isNotNewPosition = false;

				p = new Position(ThreadLocalRandom.current().nextInt((int)data.getMapMinX(), (int)data.getMapMaxX() + 1),
						ThreadLocalRandom.current().nextInt((int)data.getMapMinY(), (int)data.getMapMaxY() + 1));

				ArrayList<Obstacle> listObstacles = data.getObstaclePositions();
				for(Obstacle obs: listObstacles) {
					if (p.equals(obs.p)) isNotNewPosition = true;
				}
			}
			TypeVetement typeVetement;

			//Aleatoire type vetement
			typeVetement = TypeVetement.BAS;

			if (new Random().nextBoolean()) {
				typeVetement = TypeVetement.HAUT;
			}

			vetement.setType(typeVetement);
			vetement.setPosition(p);
			
			data.addVetement(vetement);
		}


	}

	private ObjectObstacle createObjectObstacle(Class classN) throws InstantiationException, IllegalAccessException {
		ObjectObstacle obj = (ObjectObstacle) classN.newInstance();
		boolean isNotNewPosition = true;
		int nbrTentative = 0;
		Position p = null;
		ArrayList<Obstacle> listObstacle = null;
		while(isNotNewPosition) {
			isNotNewPosition = false;
			listObstacle = new ArrayList<Obstacle>();
			p = new Position(ThreadLocalRandom.current().nextInt((int)data.getMapMinX(), (int)(data.getMapMaxX() - obj.getHeight()) + 1 ),
					ThreadLocalRandom.current().nextInt((int)data.getMapMinY(), (int)(data.getMapMaxY()- obj.getWidth()) + 1 ));

			for (int i = 0; i < obj.getWidth(); i++) {
				for (int j = 0; j < obj.getHeight(); j++) {
					Position p2 = new Position(p.x+i, p.y+j);
					listObstacle.add(new Obstacle(p2));
				}
			}

			ArrayList<Obstacle> listTemp = data.getObstaclePositions();
			for(Obstacle obs: listTemp) {
				for (Obstacle obs2: listObstacle) {
					if (obs2.p.equals(obs.p)) isNotNewPosition = true;
				}
			}

			nbrTentative++;

			if (nbrTentative > nbrEssaiMaxRandom) new Exception();
		}

		obj.setFirst(p);
		obj.setListPoints(listObstacle);

		return obj;
	}

	private Canape createCanape() throws InstantiationException, IllegalAccessException {
		return (Canape) createObjectObstacle(Canape.class);
	}

	private Lit createLit() throws InstantiationException, IllegalAccessException {
		return (Lit) createObjectObstacle(Lit.class);
	}

	private Chaise createChaise() throws InstantiationException, IllegalAccessException {
		return (Chaise) createObjectObstacle(Chaise.class);
	}

	private TableBasse createTableBasse() throws InstantiationException, IllegalAccessException {
		return (TableBasse) createObjectObstacle(TableBasse.class);
	}

	@Override
	public void start(){
		algorithm.activation();
		engineClock.schedule(new TimerTask(){
			@Override
			public void run() {

				System.out.println("\n --------Heroes position: "+data.getRobotPosition().x+", "+data.getRobotPosition().y);

				algorithm.stepAction();

				data.setStepNumber(data.getStepNumber()+1);
				data.setRobotDirection(algorithm.getDirection());
			}
		},0,400);
	}


	@Override
	public void moveL(Direction direction){
		String nom = direction.getNom();
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
		Position position = new Position(data.getRobotPosition().x, data.getRobotPosition().y);

		if (nom.equals(Direction.NORD.getNom())) position.y -= 1;
		if (nom.equals(Direction.SUD.getNom())) position.y += 1;
		if (nom.equals(Direction.EST.getNom())) position.x += 1;
		if (nom.equals(Direction.OUEST.getNom())) position.x -= 1;

		data.setRobotPosition(position);
	}

	@Override
	public void moveD(Direction direction){
		String nom = direction.getNom();
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
	public ArrayList<Obstacle> getListObstacles() {
		ArrayList<Obstacle> returnList = new ArrayList<Obstacle>();
		ArrayList<Obstacle> list = algorithm.getListObstacle();
		Position initialRobot = data.getRobotInitPosition();

		for (Obstacle obs: list) {
			double x = initialRobot.x + obs.p.x;
			double y = initialRobot.y + obs.p.y;

			returnList.add(new Obstacle(new Position(x, y)));
		}

		return returnList;
	}

	@Override
	public ArrayList<Position> getListPositionAlle() {
		ArrayList<Position> returnList = new ArrayList<Position>();
		ArrayList<Position> list = algorithm.getListPositionAlle();
		Position initialRobot = data.getRobotInitPosition();

		for (Position p: list) {
			double x = initialRobot.x + p.x;
			double y = initialRobot.y + p.y;

			returnList.add(new Position(x, y));
		}

		return returnList;
	}

	@Override
	public int[][] getMapping() {
		int retour[][] = getMapping();

		for (int i=0; i < retour.length; i++) {
			for (int j=0; j < retour.length;j++);
		}

		return retour;
	}

	@Override
	public SensorSimulator getSensorResult(Direction direction) {
		SensorSimulator retour = new SensorSimulator();
		Position positionRobot = data.getRobotPosition();

		double xMin = 0 - (positionRobot.x - (data.getMapMinX() - 1));
		double xMax = 0 - (positionRobot.x - (data.getMapMaxX() + 1));
		double yMin = 0 - (positionRobot.y - (data.getMapMinY() - 1));
		double yMax = 0 - (positionRobot.y - (data.getMapMaxY() + 1));


		for (Obstacle obs:data.getObstaclePositions()) {

			if (obs.p.x == positionRobot.x) {
				double resultatY = positionRobot.y - obs.p.y;

				if (resultatY < 0 && 0 - resultatY < yMax) {
					yMax = 0 - resultatY;
				}else if (resultatY > 0 && 0 - resultatY > yMin) {
					yMin = 0 - resultatY;
				}
			}

			if (obs.p.y == positionRobot.y) {
				double resultatX = positionRobot.x - obs.p.x;

				if (resultatX < 0 && 0 - resultatX < xMax) {
					xMax = 0 - resultatX;
				}else if (resultatX > 0 && 0 - resultatX > xMin) {
					xMin = 0 - resultatX;
				}
			}
		}

		switch (direction) {
		case NORD:
			retour.setObstacleR(new Position(xMax, 0));
			retour.setObstacleL(new Position(xMin, 0));
			retour.setObstacleU(new Position(0, yMin));
			retour.setObstacleD(new Position(0, yMax));
			break;
		case SUD:
			retour.setObstacleR(new Position(xMin, 0));
			retour.setObstacleL(new Position(xMax, 0));
			retour.setObstacleU(new Position(0, yMax));
			retour.setObstacleD(new Position(0, yMin));
			break;
		case EST:
			retour.setObstacleD(new Position(xMin, 0));
			retour.setObstacleU(new Position(xMax, 0));
			retour.setObstacleR(new Position(0, yMax));
			retour.setObstacleL(new Position(0, yMin));
			break;
		case OUEST:
			retour.setObstacleD(new Position(xMin, 0));
			retour.setObstacleU(new Position(xMax, 0));
			retour.setObstacleR(new Position(0, yMin));
			retour.setObstacleL(new Position(0, yMax));
			break;
		}

		return retour;
	}

	@Override
	public void stop() {
		engineClock.cancel();
	}
}
