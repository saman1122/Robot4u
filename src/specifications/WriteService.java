/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

import java.util.ArrayList;

import tools.Direction;
import tools.Obstacle;
import tools.Position;

public interface WriteService {
	public void setRobotInitPosition(Position p);
	public void setRobotPosition(Position p);
	public void setStepNumber(int n);
	void setRobotDirection(Direction direction);
	void addObstaclePositions(double x, double y);
	void addObstacleObject(Object obj);
	void addObstacleList(ArrayList<Obstacle> list);
}
