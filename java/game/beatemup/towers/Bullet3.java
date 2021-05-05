package game.beatemup.towers;

import game.beatemup.effect.Explosion;
import static game.beatemup.global.Functions.*;
import static game.beatemup.manager.Game.HEIGHT;
import static game.beatemup.manager.Game.WIDTH;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Handler.OBJECT;
import static game.beatemup.manager.Handler.spr_bullet3;
import game.beatemup.enums.ID;
import game.beatemup.enemies.Enemy;
import java.awt.Color;
import static java.lang.Math.min;

/**
 *
 * @author ADMIN
 */
public class Bullet3 extends GameObject{

    public Bullet3(float x, float y, ID id) {
        super(x, y, id);
    }
    
    private float damage = 0;
    private float slowdown = 0;
    private int hp;
    
    @Override
    public void create() {
        hp = (int) (1f/slowdown);
        image = pixelImage(spr_bullet3, 60, 40);
        x_offset = 40;
        y_offset = 20;
        circleCollider.setR(20);
    }
    
    @Override
    public void update() {
        if(x < 0 - x_offset || y < 0 - y_offset || x > WIDTH + x_offset || y > HEIGHT + y_offset){
            destroySelf();
        }
        collision();
    }

    public void collision(){
        for(GameObject tmp: OBJECT){
            if(tmp.getId() == ID.Enemy){
                Enemy enemy = (Enemy) tmp;
                if(circleCollision(circleCollider, enemy.circleCollider)){
                    if(enemy.health != null) enemy.health.HP -= damage;
                    enemy.setSpeed(min(enemy.getSpeed(), enemy.getMaxSpeed()*slowdown));
                    enemy.setAcc(min(enemy.getAcc(), enemy.getMaxAcc()*slowdown));
                    enemy.flash_alpha = 255;
                    enemy.flash_color = new Color(0, 176, 240);
                    hp--;
                    if(hp == 0){
                        Explosion explosion = new Explosion(x, y, ID.Explosion);
                        explosion.setColor(new Color(0, 176, 240));
                        destroySelf();
                        break;
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
    
    public float getSlowdown() {
        return slowdown;
    }

    public void setSlowdown(float slowdown) {
        this.slowdown = slowdown;
    }
}
