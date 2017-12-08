package data;

import java.util.ArrayList;

import tools.Position;

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
