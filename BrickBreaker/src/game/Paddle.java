package game;

public class Paddle extends Sprite{
	private int width;
	private int height;
	public Paddle(int width, int height) {
		setWidth(80);
		setHeight(10);
		setX(width/2 - getWidth()/2);
		setY(height*4/5);
		this.width = width;
		this.height = height;
	}
	
	public void move(int dir) {
		setX(getX()+dir);
	}
	
	public void reset() {
		setX(width/2 - getWidth()/2);
		setY(height*4/5);
	}
}
