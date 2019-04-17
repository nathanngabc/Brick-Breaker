package game;
import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Timer;

	
public class listener implements KeyListener {
	private panel p;
	public listener(panel a) {
		p = a;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == e.VK_RIGHT) {
			p.movePaddle(20);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			p.movePaddle(-20);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
