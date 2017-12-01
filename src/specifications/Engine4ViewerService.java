package specifications;

import java.util.ArrayList;

import tools.Obstacle;
import tools.Position;

public interface Engine4ViewerService extends StartEngineService {
	public ArrayList<Obstacle> getListObstacles();
	public ArrayList<Position> getListPositionAlle();
	public int[][] getMapping();
}
