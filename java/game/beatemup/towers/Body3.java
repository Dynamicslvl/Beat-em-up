package game.beatemup.towers;

import game.beatemup.effect.Explosion;
import static game.beatemup.global.Functions.*;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Handler.OBJECT;
import static game.beatemup.manager.Handler.spr_body3;
import game.beatemup.enums.ID;
import java.awt.Color;

/**
 *
 * @author ADMIN
 */
public class Body3 extends TowerBody{

    public Body3(float x, float y, ID id) {
        super(x, y, id);
    }
    
    private float angle_target;
    
    int t = 0;
    @Override
    public void create() {
        price = 100;
        slowdown = 1f/6;
        damage = 0.3f;
        range = 100;
        reload = 100;
        x_offset = 15;
        y_offset = 28;
        image_angle = 270;
        angle_target = 90;
        image = pixelImage(spr_body3, 30, 56);
    }

    @Override
    public void update() {
        float minDistance = range;
        for(GameObject tmp : OBJECT){
            if(tmp.getId() == ID.Enemy){
                float distance = point_distance(x, y, tmp.getX(), tmp.getY());
                if(distance < minDistance){
                    angle_target = point_direction(x, y, tmp.getX(), tmp.getY());
                    minDistance = distance;
                } 
            }
        }
        image_angle += Math.sin(Math.toRadians(angle_target - image_angle))*10f;
        t++;
        t = clamp(t, 0, (int)reload);
        if(t == (int)reload && angle_different(image_angle, angle_target) < 15 && minDistance < range){
            Explosion tmp = new Explosion(x, y, ID.Explosion);
            tmp.setColor(new Color(0, 176, 240));
            Bullet3 a = new Bullet3(x, y, ID.Bullet);
            a.setImage_angle(image_angle);
            a.setSpeed(5);
            a.setDamage(damage);
            a.setSlowdown(slowdown);
            t = 0;
        }
    }
}
