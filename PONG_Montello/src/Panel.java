import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.ArrayList;

public class Panel extends JPanel implements Runnable{

	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int Pelota_DIAMETER = 20;
	static final int Paleta_WIDTH = 25;
	static final int Paleta_HEIGHT = 100;

	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paleta Paleta1;
	Pelota Pelota;
	Marcador Marcador;
	static final int initialSpeed = 2;
    private ArrayList<Bloque> bloques;
    private int numFilas = 2; 
    private int numColumnas = 1; 
    private int anchoBloque = 200; 
    private int altoBloque = 60; 
    private int espacioEntreBloques = 10;
    private float incrementoVelocidad = (float) 0.50; 

	Panel(){
        setFocusable(true);
        addKeyListener(new AL());
        setPreferredSize(SCREEN_SIZE);
        setBackground(Color.BLACK);
        setOpaque(true);
		newPaletas();
		newPelota();
	    Marcador = new Marcador(GAME_WIDTH, GAME_HEIGHT, 3); 
		gameThread = new Thread(this);
		gameThread.start();
		requestFocusInWindow();
		   bloques = new ArrayList<>();
	        for (int i = 0; i < numFilas; i++) {
	            for (int j = 0; j < numColumnas; j++) {
	                int bloqueX = j * (anchoBloque + espacioEntreBloques);
	                int bloqueY = i * (altoBloque + espacioEntreBloques);
	                bloques.add(new Bloque(bloqueX, bloqueY, anchoBloque, altoBloque, true, Color.YELLOW));
	            }
	        }

	}
	
	public void newPelota() {
		random = new Random();
		Pelota = new Pelota((GAME_WIDTH/2)-(Pelota_DIAMETER/2),random.nextInt(GAME_HEIGHT-Pelota_DIAMETER),Pelota_DIAMETER,Pelota_DIAMETER);
	}
	public void newPaletas() {
        Paleta1 = new Paleta((GAME_WIDTH / 2) - (Paleta_WIDTH / 2), GAME_HEIGHT - Paleta_HEIGHT - -60, Paleta_WIDTH, Paleta_HEIGHT, 1);
	}
	  	public void paint(Graphics g) {
	        super.paint(g);
        setBackground(Color.BLACK);
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
		for (Bloque bloque : bloques) {
            bloque.draw(g);
        }

	}
	public void draw(Graphics g) {
		Paleta1.draw(g);
		Pelota.draw(g);
		Marcador.draw(g);
        setBackground(Color.BLACK);
Toolkit.getDefaultToolkit().sync(); 

	}
	public void move() {
		Paleta1.move();
		Pelota.move();
	    for (Bloque bloque : bloques) {
	        if (Pelota.intersects(bloque) && bloque.isVisible()) {
	            bloque.setInvisible();
	    }
	    }

	}
	public void checkCollision() {
		
		

		Rectangle pelotaRect = Pelota.getBounds();
	    Iterator<Bloque> iterator = bloques.iterator();
	    while (iterator.hasNext()) {
	        Bloque bloque = iterator.next();
	        Rectangle bloqueRect = bloque.getBounds();

	        if (pelotaRect.intersects(bloqueRect)) {
	            iterator.remove();
	            
	            
	            Pelota.invertirDireccionX();
	            Pelota.invertirDireccionY();
	            
	            break; 
	        }
	    }

		if (Pelota.intersects(Paleta1)) {
		    int ballTop = Pelota.y;
		    int paddleBottom = Paleta1.y + Paleta1.height;

		    if (ballTop >= Paleta1.y && ballTop <= paddleBottom) {
		        Pelota.setYDirection(-Pelota.getYVelocity());

		        Pelota.y = Paleta1.y - Pelota.height;
		    }
		 }

	    // ColisiÃ³n con la pared izquierda
	    if (Pelota.x <= 0) {
	        Pelota.x = 0;
	        Pelota.setXVelocity(Math.abs(Pelota.getXVelocity()));
	    }

	    // ColisiÃ³n con la pared derecha
	    if (Pelota.x >= GAME_WIDTH - Pelota_DIAMETER) {
	        Pelota.x = GAME_WIDTH - Pelota_DIAMETER;
	        Pelota.setXVelocity(-Math.abs(Pelota.getXVelocity()));
	    }

	    if (Pelota.y <= 0) {
	        Pelota.y = 0;
	        Pelota.setYDirection(Math.abs(Pelota.yVelocity));
	    }

	    if (Pelota.y >= GAME_HEIGHT - Pelota_DIAMETER) {
	        Marcador.playerLosesLife();
	        if (Marcador.playerLives > 0) {
	            Pelota.x = (GAME_WIDTH / 2) - (Pelota_DIAMETER / 2);
	            Pelota.y = random.nextInt(GAME_HEIGHT - Pelota_DIAMETER);
	            Pelota.setYVelocity(random.nextBoolean() ? initialSpeed : -initialSpeed);
	        } else {
	            JOptionPane.showMessageDialog(null, "Perdiste todas tus vidas!", "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
	            System.exit(0);
	        }
	    }
	}
	public void checkCollisionWithBlocks() {
	    Rectangle pelotaRect = Pelota.getBounds();

	    Iterator<Bloque> iterator = bloques.iterator();
	    while (iterator.hasNext()) {
	        Bloque bloque = iterator.next();
	        Rectangle bloqueRect = bloque.getBounds();

	        if (pelotaRect.intersects(bloqueRect)) {
	            iterator.remove();

	            aumentarVelocidadPelota();

	            break; 
	        }
	    }
	}
	public void resetPelota() {
	    Pelota.x = (GAME_WIDTH / 2) - (Pelota_DIAMETER / 2);
	    Pelota.y = random.nextInt(GAME_HEIGHT - Pelota_DIAMETER);
	    Pelota.setXVelocity(random.nextBoolean() ? initialSpeed : -initialSpeed);
	    Pelota.setYVelocity(random.nextBoolean() ? initialSpeed : -initialSpeed);
	}

	public void newLevel(int nivel) {
	    bloques.clear(); 

	    int numFilas = 2;
	    int numColumnas = 2;

	    if (nivel == 1) {
	    	numFilas = 2;
		    numColumnas = 1;
	    } else if (nivel == 2) {
	    	numFilas = 3;
		    numColumnas = 2;
	    } else if (nivel == 3) {
	    	numFilas = 4;
		    numColumnas = 3;
	    }

	    for (int i = 0; i < numFilas; i++) {
	        for (int j = 0; j < numColumnas; j++) {
	            int bloqueX = j * (anchoBloque + espacioEntreBloques);
	            int bloqueY = i * (altoBloque + espacioEntreBloques);
	            	            bloques.add(new Bloque(bloqueX, bloqueY, anchoBloque, altoBloque, true, Color.YELLOW));
	        }
	    }
	

	    for (int i = 0; i < numFilas; i++) {
	        for (int j = 0; j < numColumnas; j++) {
	            int bloqueX = j * (anchoBloque + espacioEntreBloques);
	            int bloqueY = i * (altoBloque + espacioEntreBloques);
	            
	            bloques.add(new Bloque(bloqueX, bloqueY, anchoBloque, altoBloque, true, Color.YELLOW));
	        }
	    }
        Marcador.setNivel(nivel); 

	}

    public void aumentarVelocidadPelota() {
        if (Pelota.xVelocity > 0) {
            Pelota.xVelocity += incrementoVelocidad;
        } else {
            Pelota.xVelocity -= incrementoVelocidad;
        }

        if (Pelota.yVelocity > 0) {
            Pelota.yVelocity += incrementoVelocidad;
        } else {
            Pelota.yVelocity -= incrementoVelocidad;
        }
    }



		
	
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int nivel = 1;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                move();
                checkCollision();
                checkCollisionWithBlocks();
                aumentarVelocidadPelota();
                repaint();
                delta--;
            }

            try {
                Thread.sleep(2); // Pequeño retraso para evitar consumo excesivo de CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (bloques.isEmpty()) {
                nivel++;
                if (nivel <= 3) {
                    newLevel(nivel);
                    resetPelota();
                } else {
                    JOptionPane.showMessageDialog(null, "¡Felicidades! Has completado todos los niveles.", "Fin del Juego", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
        }
    }
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			Paleta1.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			Paleta1.keyReleased(e);
		}
	}
    public static void main(String[] args) {
        JFrame frame = new JFrame("Arkanoid?");
        Panel Panel = new Panel();
        frame.add(Panel);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
