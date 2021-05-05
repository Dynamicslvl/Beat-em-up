package game.beatemup.enemies;

import game.beatemup.enums.EnemyType;
import game.beatemup.manager.GameObject;
import game.beatemup.enums.ID;

/**
 *
 * @author ADMIN
 */
public class EnemySpawner extends GameObject {

    public EnemySpawner(float x, float y, ID id) {
        super(x, y, id);
    }
    
    private AttackSquare as;
    private String path = "";
    private EnemyType type;
    private int amount;
    
    public void setWave(String path, EnemyType type, int amount){
        this.path = path;
        this.type = type;
        this.amount = amount;
    }
    
    private int t;
    
    @Override
    public void create() {
        t = (amount-1)*type.spawntime;
    }

    @Override
    public void update() {
        if(t % type.spawntime == 0){
            Enemy enemy = new Enemy(x, y, ID.Enemy, type);
            enemy.setPath(path);
            as.flash_alpha = 255;
        }
        if(t == 0){
            destroySelf();
        }
        t--;
    }  

    public AttackSquare getAs() {
        return as;
    }

    public void setAs(AttackSquare as) {
        this.as = as;
    }

}
