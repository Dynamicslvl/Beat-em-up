package game.beatemup.effect;

import static game.beatemup.global.Functions.*;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Handler.spr_explosion;
import game.beatemup.enums.ID;
import java.awt.Color;

/**
 *
 * @author ADMIN
 */
public class SmallExplosion extends GameObject{
    
    public SmallExplosion(float x, float y, ID id) {
        super(x, y, id);
    }
    
    private Color color = new Color(255, 0, 0);
    
    public void setColor(Color color){
        this.color = color;
    }

    @Override
    public void create() {
        image = pixelImage(spr_explosion, 10, 10);
        image = colorImage(image, color);
        x_offset = 5; y_offset = 5;
    }
    
    int t = 0;
    @Override
    public void update() {
        image_xscale += 0.6f;
        image_yscale += 0.6f;
        x_offset = image.getWidth()*image_xscale/2;
        y_offset = image.getHeight()*image_yscale/2;
        image = addAlphaImage(image, -255/15);
        t++;
        if(t == 10) destroySelf();
    }
    
}
