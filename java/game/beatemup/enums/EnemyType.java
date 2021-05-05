package game.beatemup.enums;

import static game.beatemup.manager.Handler.spr_enemy1;
import static game.beatemup.manager.Handler.spr_enemy2;
import static game.beatemup.manager.Handler.spr_enemy3;
import static game.beatemup.manager.Handler.spr_enemy4;
import static game.beatemup.manager.Handler.spr_enemy5;
import static game.beatemup.manager.Handler.spr_enemy6;
import static game.beatemup.manager.Handler.spr_enemy7;
import java.awt.image.BufferedImage;

/**
 *
 * @author ADMIN
 */
public enum EnemyType {
    //HP, speed, price, spawntime
    Tiny(1.5f, 2f, 5, 30, spr_enemy1), //0
    Small(16, 1f, 10, 60, spr_enemy2), //1
    Medium(64, 0.8f, 20, 90, spr_enemy3), //2
    Big(256, 0.7f, 40, 180, spr_enemy4), //3
    Huge(1000, 0.5f, 60, 210, spr_enemy5), //4
    Lord(4000, 0.4f, 100, 300, spr_enemy6), //5
    Overlord(7000, 0.3f, 200, 400, spr_enemy7); //6

    public final int price, spawntime;
    public final BufferedImage image;
    public final float hp, speed;

    private EnemyType(float hp, float speed, int price, int spawntime, BufferedImage image) {
        this.hp = hp;
        this.speed = speed;
        this.price = price;
        this.spawntime = spawntime;
        this.image = image;
    }

}
