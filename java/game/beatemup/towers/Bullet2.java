package game.beatemup.towers;

import game.beatemup.effect.RingExplosion;
import game.beatemup.effect.SmallExplosion;
import static game.beatemup.global.Functions.*;
import static game.beatemup.manager.Game.HEIGHT;
import static game.beatemup.manager.Game.WIDTH;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Handler.spr_bullet2;
import game.beatemup.enums.ID;
import java.awt.Color;

/**
 *
 * @author ADMIN
 */
public class Bullet2 extends GameObject{

    public Bullet2(float x, float y, ID id) {
        super(x, y, id);
    }
    
    private int time = 0, timeTotal = 0;
    private float damage = 0;
    
    @Override
    public void create() {
        image = pixelImage(spr_bullet2, 60, 60);
        image_xscale = 0.5f;
        image_yscale = 0.5f;
        x_offset = image.getWidth()*image_xscale/2;
        y_offset = image.getHeight()*image_yscale/2;
        circleCollider.r = 3;
        time = timeTotal;
    }

    @Override
    public void update() {
        if(x < 0 - x_offset || y < 0 - y_offset || x > WIDTH + x_offset || y > HEIGHT + y_offset){
            destroySelf();
        }
        time--;
        time = clamp(time, 0, 400);
        if(time == 0) explosion();
        parabolScale();
        x_offset = image.getWidth()*image_xscale/2;
        y_offset = image.getHeight()*image_yscale/2;
    }
    
    public void explosion(){
        RingExplosion ring = new RingExplosion(x, y, ID.Explosion);
        ring.setDamage(damage);
        SmallExplosion tmp = new SmallExplosion(x, y, ID.Explosion);
        tmp.setColor(new Color(197, 90, 17));
        destroySelf();
    }
    
    public void parabolScale(){
        float a, b;
        float x = timeTotal;
        a = -0.5f/(x*x/4); b = -a*x;
        image_xscale = a*(time*time) + b*time + 0.5f;
        image_yscale = a*(time*time) + b*time + 0.5f;
    }

    public void setTime(int time) {
        this.timeTotal = time;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
    
}
