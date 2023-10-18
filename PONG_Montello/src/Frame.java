import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame{

	Panel panel;
	
	Frame(){
		panel = new Panel();
        getContentPane().add(panel);
        setBackground(Color.BLACK); 
		this.setTitle("Pong Game");
		this.setResizable(false);
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}
}