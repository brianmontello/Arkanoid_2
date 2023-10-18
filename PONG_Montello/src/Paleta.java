import java.awt.*;
import java.awt.event.*;

public class Paleta extends Rectangle{

	int id;
	int xVelocity;
	int speed = 10;
	
	Paleta(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id){
	    super(x, y, PADDLE_HEIGHT, PADDLE_WIDTH); 
	    this.id = id;
	     setBackground(Color.BLACK);
	        setOpaque(true);
	}
	
	private void setBackground(Color black) {
		// TODO Auto-generated method stub
		
	}

	private void setOpaque(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void keyPressed(KeyEvent e) {
		switch(id) {
		case 1:
			if(e.getKeyCode()==KeyEvent.VK_A) {
				setYDirection(-speed);
			}
			if(e.getKeyCode()==KeyEvent.VK_D) {
				setYDirection(speed);
			}
			break;
		case 2:
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				setYDirection(-speed);
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				setYDirection(speed);
			}
			break;
		}
	}
	public void keyReleased(KeyEvent e) {
		switch(id) {
		case 1:
			if(e.getKeyCode()==KeyEvent.VK_A) {
				setYDirection(0);
			}
			if(e.getKeyCode()==KeyEvent.VK_D) {
				setYDirection(0);
			}
			break;
		case 2:
			if(e.getKeyCode()==KeyEvent.VK_LEFT) {
				setYDirection(0);
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
				setYDirection(0);
			}
			break;
		}
	}
	public void setYDirection(int xDirection) {
		xVelocity = xDirection;
	}
	public void move() {
	    x = x + xVelocity; 
	    if (x <= 0) {
	        x = 0;
	    } else if (x >= Panel.GAME_WIDTH - width) {
	        x = Panel.GAME_WIDTH - width;
	    }
	}
	public void draw(Graphics g) {
		if(id==1)
			g.setColor(Color.blue);
		else
			g.setColor(Color.blue);
		g.fillRect(x, y, width, height);
	}
}
