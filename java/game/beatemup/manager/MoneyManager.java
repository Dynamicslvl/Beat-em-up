package game.beatemup.manager;

import static game.beatemup.global.Functions.*;
import game.beatemup.enums.ID;
import static game.beatemup.manager.Handler.spr_square;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author ADMIN
 */
public class MoneyManager extends GameObject {

    public MoneyManager(float x, float y, ID id) {
        super(x, y, id);
    }

    @Override
    public void create() {
        money = 50;
        image = pixelImage(spr_square, 120, 30);
        image = colorImage(image, 0, 0, 15);
        image = alphaImage(image, 200);
    }

    private double m = 0;

    @Override
    public void update() {
        double diff = money - m;
        if (Math.abs(diff) > 0.1f) {
            m = clamp(m + diff / 5, 0, money);
        } else {
            m = money;
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (mouse_x != clamp(mouse_x, 0, 120) || mouse_y != clamp(mouse_y, 540, 600)) {
            super.render(g);
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.drawString("$" + (int) Math.round(m), Math.max(0, x + 90 - numberOfDigits((int) Math.round(m)) * 10), y + 22);
        }
    }

}
