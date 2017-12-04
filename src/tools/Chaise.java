package tools;

import java.util.ArrayList;

public class Chaise extends ObjectObstacle {

	public Chaise(Position first, ArrayList<Obstacle> points) {
		this.first = first;
		this.listPoints = points;
		setHW();
	}

	public Chaise() {
		setHW();
	}
	
	private void setHW() {
		this.height = 1;
		this.width = 1;		
	}
}
