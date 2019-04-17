package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class panel extends JPanel{
	
	private int lives = 3;
	private int score = 0;
	private Timer timer;
	private int height, width;
	private Paddle p;
	private Ball b;
	private int numBricks = 10;
	Brick[] bricks;
	private int numRows = 3;
	private int level = 1;
	final Font scoreFont = new Font("SansSerif", Font.PLAIN, 18);
	final Font msgFont = new Font("SansSerif", Font.BOLD, 38);
	enum State { CONTINUE, GAMEOVER, UPLEVEL };
	private State gameState;

	public panel(int w, int h) {
		height = h;
		width = w;
		initGame();
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);

	}
	
	public void drawMsg(String msg, int y, Font f, Color c, Graphics g) {
		g.setFont(f);
		g.setColor(c);
		FontMetrics fm = g.getFontMetrics();
		g.drawString(msg, ((width - fm.stringWidth(msg))/2), fm.getHeight()+y);
	}
	
	public void paintComponent(Graphics g) {
		this.setBackground(Color.BLACK);
		g.setColor(Color.WHITE);
		g.fillRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
		g.fillRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		
		for (int j = 0; j<numRows; j++) {
			for (int i = 0; i<numBricks; i++) {
				Brick temp = bricks[i + j*numBricks];
				if (temp.getHit() == false) {
					g.setColor(Color.BLACK);
					g.drawRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
					if (temp.getScore() == 1)
						g.setColor(Color.BLUE);
					if (temp.getScore() == 2)
						g.setColor(Color.GREEN);
					if (temp.getScore() == 3)
						g.setColor(Color.RED);
					g.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
				}
			}
		}
		
		String scoreMsg = "Score: " + score + "     Lives: " + lives + "     Level: " + level;
		drawMsg(scoreMsg, 0, scoreFont, Color.CYAN, g);
		
		if (gameState == State.GAMEOVER) {
			String msg = "Game Over!";
			drawMsg(msg, height*3/8, msgFont, Color.RED, g);
		} else if (gameState == State.UPLEVEL) {
			String msg = "Move to Level " + level + "!";
			drawMsg(msg, height*3/8, msgFont, Color.GREEN, g);
		}
	}
	
	public void nextLevel() throws InterruptedException {
		Thread.sleep(300);

		for (int j = 0; j < numRows; j++) {
			for (int i = 0; i < numBricks; i++) {
				bricks[i + j * numBricks].reset();
			}
		}
		b.levelUp();
		b.Reset();
		p.reset();
		level++;
		gameState = State.CONTINUE;
		
	}
	
	public void movePaddle (int dir) {
		p.move(dir);
	}
	
	public void checkCollision() {

		//collisions on walls
		if (b.getX() <= 0) {
			b.setX(1);
			b.setDeltaX(-b.getDeltaX());
		}
		if (b.getX()+b.getWidth() >= width) b.setDeltaX(-b.getDeltaX());
		if (b.getY()+b.getHeight() >= height) {
			lives--;
			b.Reset();
			p.reset();

		}
		if (b.getY() <= 0) b.setDeltaY(-b.getDeltaY());
		
		//collision on paddle
		if ((b.getX() + b.getWidth() >= p.getX() && b.getX() + b.getWidth() <= p.getX() + p.getWidth()) ||
			(b.getX() >= p.getX() && b.getX() <= p.getX() + p.getWidth())) {
			if (b.getY() + b.getHeight() >= p.getY() && b.getY() + b.getHeight() <= p.getY() + p.getHeight()) {
				b.setDeltaY(-b.getDeltaY());
				//if (b.getX() + b.getWidth()/2 < p.getX() + p.getWidth()/2)b.setDeltaX(-b.getDeltaX());
				//else b.setDeltaX(b.getDeltaX());
				int bCenterX = b.getX() + b.getWidth()/2;
				int pCenterX = p.getX() + p.getWidth()/2;
				if (bCenterX < pCenterX) {
					if (b.getDeltaX() > 0) b.setDeltaX(-b.getDeltaX());
				} else {
					if (b.getDeltaX() < 0) b.setDeltaX(-b.getDeltaX());
				}
				
			}
			
		}
		
		//collision on bricks
		Boolean hitY = false;
		Boolean hitX = false;
		Boolean breaick = false;
		for (int j = 0; j<numRows; j++) {
			if (breaick == true) break;
			for (int i = 0; i<numBricks; i++) {
				
				Brick temp = bricks[i + j*numBricks];
				if (temp.getHit()==false) {
				
					//handle side collisions
					//set up all variables
					int bLeft = b.getX();
					int bRight = b.getX() + b.getWidth();
					int brLeft = temp.getX();
					int brRight = temp.getWidth() + temp.getX();
					
					int bTop = b.getY();
					int bBottom = b.getY() + b.getHeight();
					int brTop = temp.getY();
					int brBottom = temp.getY() + temp.getHeight();
					Boolean scored = false;
					if ((bTop >= brTop && bBottom  <= brBottom) || (bTop <= brBottom && bBottom >=brBottom) || (bTop <= brTop && bBottom >= brTop)) {
						if (bRight >= brRight && bLeft <= brRight) {
							temp.setHit();
							hitX = true;
							b.setX(temp.getX()+temp.getWidth());
							breaick = true;
							if (scored==false) {
								score += temp.getScore();
								scored = true;
							}
						}
						else if (bRight >= brLeft && bLeft <= brLeft) {
							temp.setHit();
							hitX = true;
							b.setX(temp.getX()-b.getWidth());
							breaick = true;
							if (scored==false) {
								score += temp.getScore();
								scored = true;
							}
						}
					}
					if ((bLeft <= brLeft && bRight >= brLeft) || (bLeft >= brLeft && bRight <= brRight) || (bRight >= brRight && bLeft <= brRight)) {
						if (b.getY() <= temp.getY() + temp.getHeight() && b.getY() + b.getHeight() >= temp.getY() + temp.getHeight()) {
							temp.setHit();
							hitY = true;
							//b.setY(temp.getY() + temp.getHeight());
							breaick = true;
							if (scored==false) {
								score += temp.getScore();
								scored = true;
							}
						}
						else if (b.getY() <= temp.getY() && b.getY() + b.getHeight() >= temp.getY()) {
							temp.setHit();
							//b.setY(temp.getY() - b.getHeight());
							hitY = true;
							breaick = true;
							if (scored==false) {
								score += temp.getScore();
								scored = true;
							}
						}
					}
				}
			}
		}
		if (hitY) b.setDeltaY(-b.getDeltaY());
		if (hitX) b.setDeltaX(-b.getDeltaX());
	}
	
	public void initGame() {
		p = new Paddle(width, height);
		b = new Ball(width, height, p.getX(), p.getWidth());
		
		//init bricks
		bricks = new Brick[numBricks*3];
		for (int j = 0; j<numRows; j++) {
			for (int i = 0; i<numBricks; i++) {
				bricks[i + j*numBricks] = new Brick (i * width/numBricks, j * 20 + 100, 3-j);
			}
		}
		
		score = 0;
		lives = 3;
		gameState = State.CONTINUE;
	}
	
	public Boolean checkGameEnd() {
		for (int j = 0; j<numRows; j++) {
			for (int i = 0; i<numBricks; i++) {
				if (bricks[i + j*numBricks].getHit() == false) return false;
			}
		}
		return true;
	}

	private class ScheduleTask extends TimerTask {
		@Override
		public void run() {
			b.Move();
			checkCollision();
			if (lives == 0) {
				gameState = State.GAMEOVER;
			} else if (checkGameEnd()) {
				gameState = State.UPLEVEL;
			} else {
				gameState = State.CONTINUE;
			}
			repaint();


			if (gameState == State.GAMEOVER) {
				timer.cancel();
			} else if (gameState == State.UPLEVEL) {
				try {
					nextLevel();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}

	}
	
}
