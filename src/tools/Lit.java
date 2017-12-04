package tools;

import java.util.ArrayList;

public class Lit extends ObjectObstacle {

	public Lit(Position first, ArrayList<Obstacle> points) {
		this.first = first;
		this.listPoints = points;
		setHW();
	}

	public Lit() {
		setHW();
	}
	
	private void setHW() {
		this.height = 4;
		this.width = 3;		
	}
}
