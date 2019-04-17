package game;

public class Brick extends Sprite{
	private Boolean hit = false;
	private int score;
	public Brick(int x, int y, int s) {
		setX(x);
		setY(y);
		setHeight(20);
		setWidth(60);
		score = s;
	}
	
	public Boolean getHit() {
		return hit;
	}
	
	public void setHit() {
		hit = true;
	}
	
	public int getScore() {
		return score;
	}
	
	public void reset() {
		hit = false;
	}
}
