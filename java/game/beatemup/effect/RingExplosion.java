package game.beatemup.effect;

import static game.beatemup.global.Functions.*;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Handler.OBJECT;
import static game.beatemup.manager.Handler.spr_ring;
import game.beatemup.enums.ID;
import game.beatemup.enemies.Enemy;
import java.awt.Color;

/**
 *
 * @author ADMIN
 */
public class RingExplosion extends GameObject{
    
    public RingExplosion(float x, float y, ID id) {
        super(x, y, id);
    }
    
    private Color color = new Color(197, 90, 17);
    
    public void setColor(Color color){
        this.color = color;
    }
    
    private float damage = 0;

    @Override
    public void create() {
        circleCollider.setPosition(x, y);
        image = spr_ring;
        image = colorImage(image, color);
        image_xscale = 8f/image.getWidth();
        image_yscale = 8f/image.getHeight();
        x_offset = image.getWidth()*image_xscale/2;
        y_offset = image.getHeight()*image_yscale/2;
        circleCollider.setR(x_offset);
    }
    
    int t = 0;
    @Override
    public void update() {
        image_xscale += 0.05f;
        image_yscale += 0.05f;
        x_offset = image.getWidth()*image_xscale/2;
        y_offset = image.getHeight()*image_yscale/2;
        circleCollider.setR(x_offset);
        image = addAlphaImage(image, -255/15);
        t++;
        if(t == 15) destroySelf();
        collision();
    }
    
    public void collision(){
        for(GameObject tmp: OBJECT){
            if(tmp.getId() == ID.Enemy){
                Enemy enemy = (Enemy) tmp;
                if(circleCollision(circleCollider, enemy.circleCollider)){
                    if(enemy.health != null){
                        if (enemy.health.HP != 0) {
                            enemy.health.HP -= damage;
                        }
                        enemy.flash_alpha = 255;
                        enemy.flash_color = new Color(197, 90, 17);
                    } 
                }
            }
        }
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
    
}
