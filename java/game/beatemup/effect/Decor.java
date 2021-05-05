package game.beatemup.effect;

import static game.beatemup.global.Functions.alphaImage;
import static game.beatemup.global.Functions.clamp;
import static game.beatemup.global.Functions.colorImage;
import static game.beatemup.global.Functions.pixelImage;
import static game.beatemup.global.Functions.random;
import static game.beatemup.manager.Handler.spr_square;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author ADMIN
 */
public class Decor {
    private BufferedImage decor;
    private int decor_x = 0, decor_y = 0, decor_alpha = 0, max_alpha = 0;
    
    public Decor(int alpha){
        max_alpha = alpha;
        decor_alpha = max_alpha;
        decor = pixelImage(spr_square, 60, 58);
        decor = alphaImage(decor, alpha);
        decor = colorImage(decor, 0, 176, 240);
    }
    
    public void create(int alpha){
        max_alpha = alpha;
        decor_alpha = max_alpha;
        decor = pixelImage(spr_square, 60, 60);
        decor = alphaImage(decor, alpha);
        decor = colorImage(decor, 0, 176, 240);
    }
    
    public void update(){
        decor = alphaImage(decor, decor_alpha);
        decor_alpha = clamp(decor_alpha - 3, 0, 255);
        if (decor_alpha == 0) {
            decor_alpha = max_alpha;
            decor_y = (int) (random.nextInt(12) * 58.333333f);
            decor_x = random.nextInt(10) * 60;
        }
    }
    
    public void render(Graphics2D g){
        g.drawImage(decor, decor_x, decor_y, null);
    }
}
