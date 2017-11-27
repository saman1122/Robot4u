/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

import data.Position;

public interface WriteService {
  public void setHeroesPosition(Position p);
  public void setStepNumber(int n);
void setCerisePosition(Position cerisePosition);
void setHealthHero(int healthHero);
void addHealthHero();
void setMonsterPosition(Position monsterPosition);
void addCheckedPositions(double x, double y);
void addKnownPositions(double x1, double y1, int state1, double x2, double y2, int state2, double x3, double y3,
		int state3, double x4, double y4, int state4, double x5, double y5, int state15);
}
