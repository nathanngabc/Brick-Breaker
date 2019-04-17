package game;

public class Ball extends Sprite{
	private int deltaX, deltaY;
	private int initX, initY;
	private int level = 1;
	
	public Ball(int width, int height, int pos, int paddleWidth) {
		setWidth(15);
		setHeight(15);

		initX = pos - getWidth()/2 - paddleWidth;
		initY = height * 2/3 - 60;
		Reset();
	}
	
	public void Reset() {	
		deltaX = level;
		deltaY = level;
		
		setX(initX);
		setY(initY);
	}
	

	public int getDeltaX() {
		return deltaX;
	}
	
	public int getDeltaY() {
		return deltaY;
	}
	
	public void setDeltaX(int a) {
		deltaX = a;
	}

	public void setDeltaY(int a) {
		deltaY = a;
	}
	
	public void Move() {
		setX(getX()+deltaX);
		setY(getY()+deltaY);
	}
	
	public void levelUp () {
		level++;
	}

}