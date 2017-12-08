package data;

import java.util.ArrayList;

import tools.Position;

public class TableBasse extends ObjectObstacle {

	public TableBasse(Position first, ArrayList<Obstacle> points) {
		this.first = first;
		this.listPoints = points;
		setHW();
	}

	public TableBasse() {
		setHW();
	}
	
	private void setHW() {
		this.height = 2;
		this.width = 2;		
	}
}
