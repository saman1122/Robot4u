/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/SimulatorService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

public interface SimulatorService{
  public boolean moveLeft();
  public boolean moveRight();
  public boolean moveUp();
  public boolean moveDown();
int moveLeftCheck();
int moveRightCheck();
int moveUpCheck();
int moveDownCheck();
void moveL();
void moveR();
void moveU();
void moveD();
}
