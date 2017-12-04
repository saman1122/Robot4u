/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

import java.util.ArrayList;

import tools.Direction;
import tools.Obstacle;
import tools.Position;

public interface ReadService {
	public Position getRobotInitPosition();
	public Position getRobotPosition();
	public int getStepNumber();
	double getMapMinX();
	double getMapMaxX();
	double getMapMinY();
	double getMapMaxY();
	double getMiniMapMinX();
	double getMiniMapMinY();
	Direction getRobotDirection();
	ArrayList<Obstacle> getObstaclePositions();
	ArrayList<Object> getObstacleObject();
}
