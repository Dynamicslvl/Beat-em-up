package game.beatemup.towers;

import static game.beatemup.global.Functions.*;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Handler.OBJECT;
import static game.beatemup.manager.Handler.spr_body2;
import game.beatemup.enums.ID;

/**
 *
 * @author ADMIN
 */
public class Body2 extends TowerBody{

    public Body2(float x, float y, ID id) {
        super(x, y, id);
    }
    
    private float angle_target;
    
    int t = 0;
    @Override
    public void create() {
        price = 60;
        damage = 0.5f;
        range = 200;
        reload = 180;
        x_offset = 29;
        y_offset = 23;
        image_angle = 270;
        angle_target = 90;
        image = pixelImage(spr_body2, 58, 46);
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
        image_angle += Math.sin(Math.toRadians(angle_target - image_angle))*2f;
        t++;
        t = clamp(t, 0, (int)reload);
        if(t == (int)reload && angle_different(image_angle, angle_target) < 10 && minDistance < range){
            Bullet2 a = new Bullet2(x + lengthdirX(20, image_angle), y + lengthdirY(20, image_angle), ID.Bullet);
            a.setImage_angle(image_angle);
            a.setSpeed(2);
            a.setTime((int) (minDistance - 20)/2);
            a.setDamage(damage);
            t = 0;
        }
    }
    
}
