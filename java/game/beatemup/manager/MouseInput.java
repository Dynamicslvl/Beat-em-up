package game.beatemup.manager;

import java.awt.event.MouseEvent;
import static game.beatemup.global.Functions.*;
import java.awt.event.MouseAdapter;

/**
 *
 * @author ADMIN
 */
public class MouseInput extends MouseAdapter {
    
    @Override
    public void mouseClicked(MouseEvent e) {
        mouse_clicked = true;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        mouse_pressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse_pressed = false;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouse_x = e.getX();
        mouse_y = e.getY();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        mouse_pressed = false;
        mouse_dragged = true;
        mouse_x = e.getX();
        mouse_y = e.getY();
    }
    
}
