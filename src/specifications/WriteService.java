/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

import data.Position;

public interface WriteService {
  public void setRobotPosition(Position p);
  public void setStepNumber(int n);
void addCheckedPositions(double x, double y);
void addKnownPositions(double x, double y, int state);
}
