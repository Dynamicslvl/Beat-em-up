package game.beatemup.towers;

import static game.beatemup.global.Functions.*;
import static game.beatemup.towers.TowerSelector.draw_grid;
import game.beatemup.enums.TowerType;
import game.beatemup.manager.GameObject;
import game.beatemup.enums.ID;
import static game.beatemup.manager.Grid.GRID;
import static game.beatemup.manager.Handler.spr_base;
import static game.beatemup.manager.Handler.spr_base2;
import static game.beatemup.manager.Handler.spr_base3;
import static game.beatemup.manager.Handler.spr_base4;

/**
 *
 * @author ADMIN
 */
public class Tower extends GameObject {

    private TowerType type = null;

    public Tower(float x, float y, ID id, TowerType type) {
        super(x, y, id);
        this.type = type;
    }

    private TowerEvolver evolver;
    private TowerBody body;
    private boolean drawRange = false;

    @Override
    public void create() {
        evolver = new TowerEvolver(x, y, ID.Healthbar, type);
        evolver.setTower(this);
        if (type == TowerType.normal_gun) {
            x_offset = 23;
            y_offset = 23;
            image = pixelImage(spr_base, 46, 46);
            body = new Body1(x, y, id);
        }
        if (type == TowerType.cannon) {
            x_offset = 28;
            y_offset = 25;
            image = pixelImage(spr_base2, 56, 50);
            body = new Body2(x, y, id);
        }
        if (type == TowerType.sonic_wave) {
            x_offset = 23;
            y_offset = 23;
            image = pixelImage(spr_base3, 46, 46);
            body = new Body3(x, y, id);
        }
        if (type == TowerType.laser) {
            x_offset = 23;
            y_offset = 26;
            image = pixelImage(spr_base4, 46, 52);
            body = new Body4(x, y, id);
        }
    }

    private int t = 11;

    @Override
    public void update() {
        t = clamp(t - 1, -1, 10);
        if (mouse_clicked && !draw_grid) {
            if (point_distance(mouse_x, mouse_y, x, y) < 24 && !evolver_active) {
                evolver_active = true;
                evolver.setActive(!evolver.isActive());
            } 
            else if (evolver.isActive()) {
                t = 10;
            }
        }
        if (t == 0) {
            evolver_active = false;
            evolver.setActive(false);
        }
        if (point_distance(mouse_x, mouse_y, x, y) < 24) {
            drawRange = true;
        } else {
            drawRange = false;
        }
    }

    @Override
    public void destroySelf() {
        GRID[real2Grid(y)][real2Grid(x)] = 1;
        evolver.destroySelf();
        body.destroySelf();
        super.destroySelf();
    }

    public TowerBody getBody() {
        return body;
    }

    public void setBody(TowerBody body) {
        this.body = body;
    }

    public boolean isDrawRange() {
        return drawRange;
    }

    public void setDrawRange(boolean drawRange) {
        this.drawRange = drawRange;
    }

}
