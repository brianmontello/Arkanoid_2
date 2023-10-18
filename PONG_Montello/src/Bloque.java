import java.awt.*;

public class Bloque extends Rectangle {
    private boolean visible;
    private Color color;

    public Bloque(int x, int y, int width, int height, boolean visible, Color color) {
        super(x, y, width, height);
        this.visible = visible;
        this.color = color;
    }

    

	public void draw(Graphics g) {
        if (visible) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
    }

    public boolean isVisible() {
        return visible;
    }

    public void setInvisible() {
        visible = false;
    }
}