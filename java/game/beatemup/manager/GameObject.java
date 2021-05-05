package game.beatemup.manager;

import game.beatemup.enums.ID;
import static game.beatemup.global.Functions.*;
import static game.beatemup.manager.Handler.*;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 *
 * @author ADMIN
 */
public abstract class GameObject {

    protected float x, y;
    protected float image_angle = 0;
    protected float image_xscale = 1, image_yscale = 1;
    protected float x_offset = 0, y_offset = 0;
    protected ID id;
    protected float speed = 0;
    protected float velX = 0, velY = 0;
    protected BufferedImage image = null;
    protected boolean init = true;

    public CircleCollider circleCollider = new CircleCollider();

    public GameObject(float x, float y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
        OBJECT.add(this);
    }

    public void destroySelf() {
        OBJECT.remove(this);
    }

    public void tick() {
        if (pause) {
            return;
        }
        if (init) {
            create();
            init = false;
        } else {
            if (!Game.sc.isActive()) {
                circleCollider.setPosition(x, y);
                update();
                velX = lengthdirX(speed, image_angle);
                velY = lengthdirY(speed, image_angle);
                x += velX;
                y += velY;
            }
        }
    }

    protected AffineTransformOp op;

    public void render(Graphics2D g) {
        if (image == null || init || image_xscale == 0 || image_yscale == 0) {
            return;
        }
        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(image_angle), x_offset, y_offset);
        at.scale(image_xscale, image_yscale);
        op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(image, op, (int) Math.round(x - x_offset), (int) Math.round(y - y_offset));
    }

    public abstract void create();

    public abstract void update();

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public float getImage_angle() {
        return image_angle;
    }

    public void setImage_angle(float image_angle) {
        this.image_angle = image_angle;
    }

    public float getImage_xscale() {
        return image_xscale;
    }

    public void setImage_xscale(float image_xscale) {
        this.image_xscale = image_xscale;
    }

    public float getImage_yscale() {
        return image_yscale;
    }

    public void setImage_yscale(float image_yscale) {
        this.image_yscale = image_yscale;
    }

    public float getX_offset() {
        return x_offset;
    }

    public void setX_offset(int x_offset) {
        this.x_offset = x_offset;
    }

    public float getY_offset() {
        return y_offset;
    }

    public void setY_offset(int y_offset) {
        this.y_offset = y_offset;
    }

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
