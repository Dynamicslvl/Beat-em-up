package game.beatemup.manager;

import game.beatemup.enums.ID;
import static game.beatemup.global.Functions.*;
import static game.beatemup.manager.Handler.spr_square;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author ADMIN
 */
public class Healthbar extends GameObject{
    
    public Healthbar(float x, float y, ID id) {
        super(x, y, id);
    }
    
    public float HP = 100;
    public float MaxHP = 100;
    private int w = 36, h = 6;
    
    @Override
    public void create() {
        HP = MaxHP;
        image = pixelImage(spr_square, w, h-1);
        image = alphaImage(image, 255/3);
        image = colorImage(image, 0, 255, 0);
        x_offset = Math.round((float)w/2);
        y_offset = Math.round((float)h/2);
    }

    @Override
    public void update() {
        HP = clamp(HP, 0, MaxHP);
        image_xscale = HP/MaxHP;
        
        //Coloring
        float R = (MaxHP-HP)*255/MaxHP;
        float G = HP*255/MaxHP;
        if(G > R){
            R = 255*R/G; G = 255; 
        } else {
            G = 255*G/R; R = 255; 
        }
        image = colorImage(image, (int) R, (int) G, 0);
    }
    
}
