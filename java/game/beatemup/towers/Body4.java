package game.beatemup.towers;

import game.beatemup.manager.GameObject;
import game.beatemup.enums.ID;
import static game.beatemup.global.Functions.*;
import game.beatemup.enemies.Enemy;
import static game.beatemup.manager.Handler.OBJECT;
import static game.beatemup.manager.Handler.spr_body4;
import java.awt.Color;
import java.awt.Graphics2D;
import static java.lang.Math.min;

/**
 *
 * @author ADMIN
 */
public class Body4 extends TowerBody {

    public Body4(float x, float y, ID id) {
        super(x, y, id);
    }

    private float angle_target;
    private Bullet4 laser = null;

    @Override
    public void create() {
        price = 200;
        slowdown = 7f/8;
        damage = 0.3f;
        range = 120;
        x_offset = 27;
        y_offset = 21;
        image_angle = 270;
        angle_target = 90;
        image = pixelImage(spr_body4, 54, 42);
        laser = new Bullet4(x, y, ID.Bullet);
    }

    @Override
    public void update() {
        float minDistance = range;
        Enemy enemy = null;
        for (GameObject tmp : OBJECT) {
            if (tmp.getId() == ID.Enemy) {
                float distance = point_distance(x, y, tmp.getX(), tmp.getY());
                if (distance < minDistance) {
                    angle_target = point_direction(x, y, tmp.getX(), tmp.getY());
                    minDistance = distance;
                    enemy = (Enemy) tmp;
                }
            }
        }
        image_angle += Math.sin(Math.toRadians(angle_target - image_angle)) * 30;
        if (angle_different(image_angle, angle_target) <= 3 && minDistance < range) {
            laser.setImage(pixelImage(laser.getImage(), (int) minDistance, 16));
            laser.setImage_angle(image_angle);
            laser.setActive(true);
            if (enemy.health != null) {
                if (enemy.health.HP != 0) {
                    enemy.health.HP -= damage;
                    if(enemy.flash_alpha < 150){
                        enemy.flash_alpha = 255;
                        enemy.flash_color = new Color(0, 176, 80);
                    }
                    enemy.setSpeed(min(enemy.getSpeed(), enemy.getMaxSpeed()*slowdown));
                    enemy.setAcc(min(enemy.getAcc(), enemy.getMaxAcc()*slowdown));
                }
            }
        } else {
            laser.setActive(false);
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
    }
    
    @Override
    public void destroySelf(){
        laser.destroySelf();
        super.destroySelf();
    }

}
