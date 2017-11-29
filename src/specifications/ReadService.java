/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import tools.Direction;
import tools.Position;

public interface ReadService {
  public Position getRobotPosition();
  public int getStepNumber();
double getMapMinX();
double getMapMaxX();
double getMapMinY();
double getMapMaxY();
ArrayList<Position> getCheckedPositions();
ConcurrentHashMap<Position,Integer> getKnownPositions();
Direction getRobotDirection();
}
