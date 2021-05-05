package game.beatemup.enemies;

import static game.beatemup.global.Functions.*;
import game.beatemup.effect.Explosion;
import game.beatemup.enums.EnemyType;
import game.beatemup.enums.ID;
import game.beatemup.manager.GameObject;
import game.beatemup.manager.Healthbar;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author ADMIN
 */
public class Enemy extends GameObject {

    public Enemy(float x, float y, ID id, EnemyType type) {
        super(x, y, id);
        this.type = type;
    }

    protected EnemyType type;
    private boolean isDestroyByTower = true;

    //Move
    protected float maxSpeed;
    protected float acc, maxAcc;
    protected String path;
    protected float angle_target;
    protected int last_pos = real2Grid(x) * 10 + real2Grid(y);

    //Flash
    public int flash_alpha = 0;
    public Color flash_color = new Color(255, 255, 255);

    //Health
    public Healthbar health;

    //Price
    protected int price;

    //Path
    protected int path_step = 0;

    @Override
    public void create() {

        //Price
        price = type.price;

        //Image and collider
        image = type.image;
        x_offset = image.getWidth() / 2;
        y_offset = image.getHeight() / 2;
        circleCollider.setR(image.getWidth() / 2);

        //Path
        image_angle = char2Angle(path.charAt(0));
        angle_target = char2Angle(path.charAt(0));

        //Speed and acceleration
        speed = type.speed;
        maxSpeed = speed;
        acc = maxSpeed / 120;
        maxAcc = acc;

        //Health
        health = new Healthbar(x, y - 25, ID.Healthbar);
        health.MaxHP = type.hp;

        //Turn speed
        turnSpeed = 0.1f * randomRange(18, 32);
    }

    private float turnSpeed;

    @Override
    public void update() {
        //Speed
        speedBoost();

        //Destroy when HP = 0
        if (health.HP == 0) {
            destroySelf();
        }

        //HP bar position
        health.setX(x);
        health.setY(y - 25);

        //Move follow the way
        int current_pos = real2Grid(x) * 10 + real2Grid(y);
        if (current_pos != last_pos) {
            path_step++;
            last_pos = current_pos;
            if (path_step < path.length()) {
                angle_target = char2Angle(path.charAt(path_step));
            }
        }
        image_angle += Math.sin(Math.toRadians(angle_target - image_angle)) * turnSpeed * speed;

        //Flashing
        flash_alpha -= 10;
        flash_alpha = clamp(flash_alpha, 0, 255);
    }

    public void speedBoost() {
        acc = clamp(acc + Math.max(0.000001f, maxAcc / 60), 0, maxAcc);
        speed = clamp(speed + acc, 0, maxSpeed);
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        if (flash_alpha != 0) {
            BufferedImage tmp = colorImage(image, flash_color);
            tmp = alphaImage(tmp, flash_alpha);
            g.drawImage(tmp, op, (int) Math.round(x - x_offset), (int) Math.round(y - y_offset));
        }
    }

    @Override
    public void destroySelf() {
        if (isDestroyByTower) {
            money += price;
        }
        new Explosion(x, y, ID.Explosion);
        health.destroySelf();
        super.destroySelf();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public float getAcc() {
        return acc;
    }

    public void setAcc(float acc) {
        this.acc = acc;
    }

    public float getMaxAcc() {
        return maxAcc;
    }

    public void setMaxAcc(float maxAcc) {
        this.maxAcc = maxAcc;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public boolean isIsDestroyByTower() {
        return isDestroyByTower;
    }

    public void setIsDestroyByTower(boolean isDestroyByTower) {
        this.isDestroyByTower = isDestroyByTower;
    }

}
