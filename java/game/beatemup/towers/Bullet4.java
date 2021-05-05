package game.beatemup.towers;

import static game.beatemup.global.Functions.*;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Handler.spr_bullet1;
import static game.beatemup.manager.Handler.spr_bullet4;
import game.beatemup.enums.ID;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 *
 * @author ADMIN
 */
public class Bullet4 extends GameObject{

    public Bullet4(float x, float y, ID id) {
        super(x, y, id);
    }
    
    private boolean active = false;
    private BufferedImage beamPoint = null;
    
    @Override
    public void create() {
        beamPoint = pixelImage(spr_bullet1, 60, 60);
        beamPoint = colorImage(beamPoint, 0, 176, 80);
        image = pixelImage(spr_bullet4, 5, 16);
        x_offset = 0;
        y_offset = 8;
    }
    
    private boolean dec = true;

    @Override
    public void update() {
        if(active)
        if(dec){
            image_yscale -= 0.1f;
            image_yscale = clamp(image_yscale, 0.5f, 1f);
            y_offset = image_yscale*8;
            if(image_yscale == 0.5f) dec = false;
        } else {
            image_yscale += 0.1f;
            image_yscale = clamp(image_yscale, 0.5f, 1f);
            y_offset = image_yscale*8;
            if(image_yscale == 1f) dec = true;
        }
    }
    
    @Override
    public void render(Graphics2D g){
        if(active){
            super.render(g);
            AffineTransform at = new AffineTransform();
            at.rotate(Math.toRadians(image_angle), 30, 30);
            op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            g.drawImage(beamPoint, op, (int) Math.round(x + lengthdirX(image.getWidth(), image_angle) - 30), (int) Math.round(y - 30 + lengthdirY(image.getWidth(), image_angle)));
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    
}
