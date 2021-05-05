package game.beatemup.manager;

import game.beatemup.enemies.Enemy;
import game.beatemup.enums.ID;
import static game.beatemup.global.Functions.addAlphaImage;
import static game.beatemup.global.Functions.circleCollision;
import static game.beatemup.global.Functions.clamp;
import static game.beatemup.global.Functions.numberOfDigits;
import static game.beatemup.global.Functions.pixelImage;
import static game.beatemup.manager.Handler.OBJECT;
import static game.beatemup.manager.Handler.spr_protect_square;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author ADMIN
 */
public class ProtectSquare extends GameObject {

    public ProtectSquare(float x, float y, ID id) {
        super(x, y, id);
    }

    @Override
    public void create() {
        image = pixelImage(spr_protect_square, 60, 60);
        x_offset = 30;
        y_offset = 30;
        circleCollider.setR(10);
    }

    @Override
    public void update() {
        collision();

        //Flashing
        flash_alpha -= 3;
        flash_alpha = clamp(flash_alpha, 0, 255);
    }

    private int shield = 20;

    public void collision() {
        int i = 0;
        while (i < OBJECT.size()) {
            GameObject tmp = OBJECT.get(i);
            if (tmp.getId() == ID.Enemy) {
                Enemy enemy = (Enemy) tmp;
                if (circleCollision(circleCollider, enemy.circleCollider)) {
                    enemy.setIsDestroyByTower(false);
                    enemy.destroySelf();
                    i--;
                    shield--;
                    flash_alpha = 225;
                }
            }
            i++;
        }
        shield = clamp(shield, 0, 20);
    }

    public int flash_alpha = 0;
    private BufferedImage flash;

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        //Flashing
        if (flash_alpha != 0) {
            flash = addAlphaImage(image, flash_alpha - 255 / 2);
            g.drawImage(flash, (int) (x - x_offset), (int) (y - y_offset), null);
        }
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        g.setColor(new Color(0, 200, 255));
        g.drawString("" + shield, (int) x + 1 - numberOfDigits(shield) * 4, (int) y + 3);
    }

    public int getShield() {
        return shield;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

}
