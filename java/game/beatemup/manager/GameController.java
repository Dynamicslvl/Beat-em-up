package game.beatemup.manager;

import game.beatemup.enums.ID;
import static game.beatemup.global.Functions.*;
import static game.beatemup.manager.Handler.OBJECT;
import static game.beatemup.manager.Handler.spr_square;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author ADMIN
 */
public class GameController extends GameObject {

    public GameController(float x, float y, ID id) {
        super(x, y, id);
    }

    @Override
    public void create() {
        image = pixelImage(spr_square, 120, 30);
        image = colorImage(image, 0, 0, 15);
        image = alphaImage(image, 255 / 2);
        tileColoring = true;
        wave = 0;
        maxWave = 30;
    }

    @Override
    public void update() {

    }

    private int t = 0, R = random.nextInt(255), G = random.nextInt(255), B = random.nextInt(255);

    @Override
    public void render(Graphics2D g) {
        if (isVictory()) {
            super.render(g);
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.drawString("VICTORY!", x + 15, y + 22);
            t = clamp(t + 1, 0, 60);
            if (t == 60) {
                t = 0;
                R = random.nextInt(255);
                G = random.nextInt(255);
                B = random.nextInt(255);
            }
            g.setColor(new Color(R, G, B));
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
            g.drawString("CONGRATULATIONS!", 140, 300 + 20);
        } else if (mouse_x != clamp(mouse_x, x, x + 120) || mouse_y != clamp(mouse_y, y, y + 60)) {
            super.render(g);
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.drawString("WAVE " + wave + "/" + maxWave, Math.max(x + 3, x + 20 - numberOfDigits(wave) * 10), y + 22);
        }
    }

    public static boolean isVictory() {
        if (wave < maxWave) {
            return false;
        }
        for (GameObject tmp : OBJECT) {
            if (tmp.getId() == ID.Enemy || tmp.getId() == ID.EnemySpawner) {
                return false;
            }
            if (tmp.getId() == ID.ProtectSquare) {
                ProtectSquare ps = (ProtectSquare) tmp;
                if (ps.getShield() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isDefeated() {
        for (GameObject tmp : OBJECT) {
            if (tmp.getId() == ID.ProtectSquare) {
                ProtectSquare ps = (ProtectSquare) tmp;
                if (ps.getShield() == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
