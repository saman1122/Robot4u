package engine;

import tools.Position;

public class SensorSimulator {
	private Position obstacleL;
	private Position obstacleR;
	private Position obstacleU;
	private Position obstacleD;
	
	public Position getObstacleL() {
		return obstacleL;
	}
	public void setObstacleL(Position obstacleL) {
		this.obstacleL = obstacleL;
	}
	public Position getObstacleR() {
		return obstacleR;
	}
	public void setObstacleR(Position obstacleR) {
		this.obstacleR = obstacleR;
	}
	public Position getObstacleU() {
		return obstacleU;
	}
	public void setObstacleU(Position obstacleU) {
		this.obstacleU = obstacleU;
	}
	public Position getObstacleD() {
		return obstacleD;
	}
	public void setObstacleD(Position obstacleD) {
		this.obstacleD = obstacleD;
	}
	public SensorSimulator() {
		obstacleL = new Position(0, 0);
		obstacleR = new Position(0, 0);
		obstacleU = new Position(0, 0);
		obstacleD = new Position(0, 0);
	}
}
