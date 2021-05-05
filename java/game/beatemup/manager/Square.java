package game.beatemup.manager;

import static game.beatemup.manager.GameController.isVictory;
import game.beatemup.enums.ID;
import static game.beatemup.global.Functions.*;
import static game.beatemup.manager.Handler.spr_square;
import java.awt.Graphics2D;

/**
 *
 * @author ADMIN
 */
public class Square extends GameObject {

    public Square(float x, float y, ID id) {
        super(x, y, id);
    }

    private float r, g, b;
    private int ro, go, bo;

    @Override
    public void create() {
        image = pixelImage(spr_square, 60, 60);
        int tmp = randomRange(20, 40);
        r = tmp;
        g = tmp;
        b = tmp + 20;
        ro = (int)r; go = (int)g; bo = (int)b;
        image = colorImage(image, r, g, b);
    }

    private int inc = random.nextInt(2);
    private float colorSpeed = 0.2f;
    int t = 0, tmax = randomRange(10, 30);

    @Override
    public void update() {
        //Coloring
        if (tileColoring) {
            if (inc == 0) {
                g = clamp(g - colorSpeed, 20, 40);
                b = clamp(b - colorSpeed, 40, 60);
                if (g == 20) {
                    inc = 1;
                }
            } else {
                g = clamp(g + colorSpeed, 20, 40);
                b = clamp(b + colorSpeed, 40, 60);
                if (g == 40) {
                    inc = 0;
                }
            }
            r = g;
            if(ro != (int)r || go != (int)g || bo != (int)b){
                ro = (int)r; go = (int)g; bo = (int)b;
                image = colorImage(image, r, g, b);
            }
        }
        //Victory
        if(isVictory()){
            tileColoring = false;
            t++;
            if(t == tmax){
                t = 0;
                r = randomRange(0, 255);
                g = randomRange(0, 255);
                b = randomRange(0, 255);
                image = colorImage(image, r, g, b);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
    }

}
