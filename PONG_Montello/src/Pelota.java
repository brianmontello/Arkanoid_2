import java.awt.*;
import java.util.*;

public class Pelota extends Rectangle{

	Random random;
	int xVelocity;
	int yVelocity;
	int initialSpeed = 2;
	
	Pelota(int x, int y, int width, int height){
	    super(x, y, width, height);
	    random = new Random();
	    setXDirection(-initialSpeed); 
	    int randomYDirection = random.nextInt(2);
	    if(randomYDirection == 0)
	        randomYDirection--;
	    setYDirection(randomYDirection * initialSpeed);
        setXVelocity(-initialSpeed);
        setYVelocity(random.nextBoolean() ? initialSpeed : -initialSpeed); 
        setBackground(Color.BLACK);
        setOpaque(true);
		
	
		
	}
	public boolean intersects(Rectangle r) {
	    return r.intersects(this);
	}

	 public void invertirDireccionX() {
	        xVelocity = -xVelocity;
	    }

	    public void invertirDireccionY() {
	        yVelocity = -yVelocity;
	    }
	
	private void setOpaque(boolean b) {
		// TODO Auto-generated method stub
		
	}
	private void setBackground(Color black) {
		// TODO Auto-generated method stub
		
	}
	public void aumentarVelocidad(int incremento) {
	    // Incrementar la velocidad de la pelota en ambos ejes
	    if (xVelocity > 0) {
	        xVelocity += incremento;
	    } else {
	        xVelocity -= incremento;
	    }

	    if (yVelocity > 0) {
	        yVelocity += incremento;
	    } else {
	        yVelocity -= incremento;
	    }
	}

	public void setXDirection(int randomXDirection) {
		xVelocity = randomXDirection;
	}
	public void setYDirection(int randomYDirection) {
		yVelocity = randomYDirection;
	}
	public void move() {
	    x += xVelocity;
	    y += yVelocity;
	}
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, height, width);
	}
    public int getYVelocity() {
        return yVelocity;
    }
    public int getXVelocity() {
        return xVelocity;
    }


	  public void setXVelocity(int xVelocity) {
	        this.xVelocity = xVelocity;
	    }

	    public void setYVelocity(int yVelocity) {
	        this.yVelocity = yVelocity;
	    }
}
