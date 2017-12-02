/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/SimulatorService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

import tools.Direction;
import tools.SensorSimulator;

public interface SimulatorService{
int moveLeftCheck(Direction direction);
int moveRightCheck(Direction direction);
int moveUpCheck(Direction direction);
int moveDownCheck(Direction direction);
void moveL(Direction direction);
void moveR(Direction direction);
void moveU(Direction direction);
void moveD(Direction direction);
SensorSimulator getSensorResult(Direction direction);
}
