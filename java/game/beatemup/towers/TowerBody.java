package game.beatemup.towers;

import game.beatemup.enums.ID;
import game.beatemup.manager.GameObject;

/**
 *
 * @author ADMIN
 */
public abstract class TowerBody extends GameObject {
    
    protected float range;
    protected float damage;
    protected float reload;
    protected float slowdown;
    protected int price;
    
    public TowerBody(float x, float y, ID id) {
        super(x, y, id);
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getReload() {
        return reload;
    }

    public void setReload(float reload) {
        this.reload = reload;
    }

    public float getSlowdown() {
        return slowdown;
    }

    public void setSlowdown(float slowdown) {
        this.slowdown = slowdown;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    
}
