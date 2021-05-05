package game.beatemup.towers;

import game.beatemup.effect.SmallExplosion;
import static game.beatemup.global.Functions.circleCollision;
import static game.beatemup.global.Functions.colorImage;
import static game.beatemup.global.Functions.pixelImage;
import static game.beatemup.manager.Game.HEIGHT;
import static game.beatemup.manager.Game.WIDTH;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Handler.OBJECT;
import static game.beatemup.manager.Handler.spr_bullet1;
import game.beatemup.enums.ID;
import game.beatemup.enemies.Enemy;
import java.awt.Color;

/**
 *
 * @author ADMIN
 */
public class Bullet1 extends GameObject{

    private float damage = 0f;
    
    public Bullet1(float x, float y, ID id) {
        super(x, y, id);
    }
    
    @Override
    public void create() {
        image = pixelImage(spr_bullet1, 56, 32);
        x_offset = 28;
        y_offset = 16;
        image = colorImage(image, 191, 144, 0);
        circleCollider.setR(3);
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
                    SmallExplosion explosion = new SmallExplosion(x, y, ID.Explosion);
                    explosion.setColor(new Color(191, 144, 0));
                    if(enemy.health != null) enemy.health.HP -= damage;
                    if(enemy.flash_alpha < 200){
                        enemy.flash_alpha = 255;
                        enemy.flash_color = new Color(191, 144, 0);
                    }
                    destroySelf();
                    break;
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
