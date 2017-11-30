/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/AlgorithmService.java 2015-03-09 buixuan.
 * ******************************************************/
package specifications;

import java.util.ArrayList;

import data.Obstacle;
import tools.Position;

public interface AlgorithmService{
	public void activation();
	public void stepAction();
	public int[][] getMapping();
	public ArrayList<Position> getListPositionAlle();
	public ArrayList<Obstacle> getListObstacle();
}
