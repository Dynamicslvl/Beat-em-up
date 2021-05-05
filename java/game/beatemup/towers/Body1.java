package game.beatemup.towers;

import game.beatemup.manager.GameObject;
import game.beatemup.enums.ID;
import static game.beatemup.global.Functions.*;
import static game.beatemup.manager.Handler.OBJECT;
import static game.beatemup.manager.Handler.spr_body1;

/**
 *
 * @author ADMIN
 */
public class Body1 extends TowerBody{

    public Body1(float x, float y, ID id) {
        super(x, y, id);
    }
    
    private float angle_target;
    
    int t = 0;
    @Override
    public void create() {
        price = 20;
        damage = 0.5f;
        range = 120;
        reload = 15;
        x_offset = 15;
        y_offset = 15;
        image_angle = 270;
        angle_target = 90;
        image = pixelImage(spr_body1, 40, 30);
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
        image_angle += Math.sin(Math.toRadians(angle_target - image_angle))*20;
        t++;
        t = clamp(t, 0, (int)reload);
        if(t == (int)reload && angle_different(image_angle, angle_target) < 7 && minDistance < range){
            Bullet1 a = new Bullet1(x + lengthdirX(15, image_angle), y + lengthdirY(15, image_angle), ID.Bullet);
            a.setImage_angle(image_angle);
            a.setSpeed(10);
            a.setDamage(damage);
            t = 0;
        }
    }
    
}
