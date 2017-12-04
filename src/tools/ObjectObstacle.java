package tools;

import java.util.ArrayList;

public class ObjectObstacle {
	protected int height;
	protected int width;
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	Position first;
	ArrayList<Obstacle> listPoints;
	
	public Position getFirst() {
		return first;
	}

	public void setFirst(Position first) {
		this.first = first;
	}

	public ArrayList<Obstacle> getListPoints() {
		return listPoints;
	}

	public void setListPoints(ArrayList<Obstacle> listPoints) {
		this.listPoints = listPoints;
	}
}
