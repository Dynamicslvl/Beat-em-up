package game.beatemup.effect;

import static game.beatemup.global.Functions.*;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Handler.spr_square;
import game.beatemup.enums.ID;

/**
 *
 * @author ADMIN
 */
public class FadeOut extends GameObject {

    public FadeOut(float x, float y, ID id) {
        super(x, y, id);
    }
    
    @Override
    public void create() {
        image = pixelImage(spr_square, 600, 720);
        image = colorImage(image, 0, 0, 0);
    }
    
    private int image_alpha = 255;

    @Override
    public void update() {
        image_alpha -= 15;
        image_alpha = clamp(image_alpha, 0, 255);
        image = alphaImage(image, image_alpha);
        if(image_alpha == 0) destroySelf();
    }
    
}
