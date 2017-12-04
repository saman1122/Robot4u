package tools;

import java.util.ArrayList;

public class Canape extends ObjectObstacle {
	public Canape(Position first, ArrayList<Obstacle> points) {
		this.first = first;
		this.listPoints = points;
		setHW();
	}

	public Canape() {
		setHW();
	}
	
	private void setHW() {
		this.height = 1;
		this.width = 3;		
	}
}
