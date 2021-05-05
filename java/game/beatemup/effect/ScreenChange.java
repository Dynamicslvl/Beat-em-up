package game.beatemup.effect;

import game.beatemup.enums.ScType;
import static game.beatemup.manager.Handler.spr_sc_left;
import static game.beatemup.manager.Handler.spr_sc_right;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author ADMIN
 */
public class ScreenChange {
    
    private ScType type;
    private BufferedImage sc_left, sc_right;
    
    public ScreenChange(ScType type){
        this.type = type;
    }
    
    private int x1, x2, speed = 10;
    private boolean active = false;
    
    public void init(){
        sc_left = spr_sc_left;
        sc_right = spr_sc_right;
        if(type == ScType.close){
            x1 = -600;
            x2 = +600;
        } else {
            x1 = 0; x2 = 0;
        }
    }
    
    public void tick(){
        if(!active) return;
        if(type == ScType.close){
            x1 = Math.min(0, x1 + speed);
            x2 = Math.max(0, x2 - speed);
            if(x1 == 0 && x2 == 0){
                active = false;
                type = ScType.open;
            }
        } else {
            x1 = Math.max(-600, x1 - speed);
            x2 = Math.min(+600, x2 + speed);
            if(x1 == -600 && x2 == 600){
                active = false;
                type = ScType.close;
            }
        }
    }
    
    public void render(Graphics2D g){
        if(!active) return;
        g.drawImage(sc_left, x1, 0, null);
        g.drawImage(sc_right, x2, 0, null);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ScType getType() {
        return type;
    }

    public void setType(ScType type) {
        this.type = type;
    }
}
