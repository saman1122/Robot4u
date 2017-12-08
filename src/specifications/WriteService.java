/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

import java.util.ArrayList;

import data.Obstacle;
import data.Vetement;
import tools.Direction;
import tools.Position;

public interface WriteService {
	public void setRobotInitPosition(Position p);
	public void setRobotPosition(Position p);
	public void setStepNumber(int n);
	void setRobotDirection(Direction direction);
	void addObstaclePositions(double x, double y);
	void addObstacleObject(Object obj);
	void addObstacleList(ArrayList<Obstacle> list);
	void setMapMinX(double mapMinX);
	void setMapMaxX(double mapMaxX);
	void setMapMinY(double mapMinY);
	void setMapMaxY(double mapMaxY);
	void addVetement(Vetement vetement);
	void setVetements(ArrayList<Vetement> listVetement);
}
