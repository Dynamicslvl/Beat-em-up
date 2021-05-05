package game.beatemup.manager;

import static game.beatemup.global.Functions.*;
import game.beatemup.enums.ID;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author ADMIN
 */
public class Handler {
    public static LinkedList<GameObject> OBJECT = new LinkedList<GameObject>();
    public static BufferedImage spr_base, spr_base2, spr_square, spr_bullet1, spr_grid, spr_rim, spr_body1;
    public static BufferedImage spr_range_circle, spr_range_simple, spr_explosion, spr_body2, spr_bullet2;
    public static BufferedImage spr_ring, spr_back_menu, spr_motion_back, spr_title, spr_play, spr_setting, spr_about;
    public static BufferedImage spr_front_menu, spr_about_content, spr_attack_square, spr_protect_square;
    public static BufferedImage spr_base3, spr_body3, spr_bullet3, spr_base4, spr_body4, spr_bullet4, spr_tower_selector;
    public static BufferedImage spr_infor1, spr_infor2, spr_infor3, spr_infor4, spr_infor5, spr_towers_title;
    public static BufferedImage spr_evolve_main, spr_evolve1, spr_evolve1_maxed, spr_evolve2,  spr_evolve3, spr_evolve4, spr_evolve5, spr_evolve_select;
    public static BufferedImage spr_einfor1, spr_einfor2, spr_einfor3, spr_einfor4, spr_einfor5, spr_tier, spr_insufficient_money;
    public static BufferedImage spr_enemy1, spr_enemy2, spr_enemy3, spr_enemy4, spr_enemy5, spr_enemy6, spr_enemy7;
    public static BufferedImage spr_sc_left, spr_sc_right, spr_tutorial;
    
    public void tick(){
        for(int i = 0; i<OBJECT.size(); i++){
            OBJECT.get(i).tick();
        }
        mouse_clicked = false;
    }
    
    public void loadImages(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            spr_base = loader.loadImage("/spr_base.png");
            spr_bullet1 = loader.loadImage("/spr_bullet1.png");
            spr_bullet2 = loader.loadImage("/spr_bullet2.png");
            spr_square = loader.loadImage("/spr_square.png");
            spr_grid = loader.loadImage("/spr_grid.png");
            spr_rim = loader.loadImage("/spr_rim.png");
            spr_body1 = loader.loadImage("/spr_body1.png");
            spr_range_circle = loader.loadImage("/spr_range_circle.png");
            spr_range_simple= loader.loadImage("/spr_range_simple.png");
            spr_explosion = loader.loadImage("/spr_explosion.png");
            spr_body2 = loader.loadImage("/spr_body2.png");
            spr_ring = loader.loadImage("/spr_ring.png");
            spr_back_menu = loader.loadImage("/spr_back_menu.png");
            spr_front_menu = loader.loadImage("/spr_front_menu.png");
            spr_motion_back = loader.loadImage("/spr_motion_back.png");
            spr_title = loader.loadImage("/spr_title.png");
            spr_play = loader.loadImage("/spr_play.png");
            spr_setting = loader.loadImage("/spr_setting.png");
            spr_about = loader.loadImage("/spr_about.png");
            spr_about_content = loader.loadImage("/spr_about_content.png");
            spr_attack_square = loader.loadImage("/spr_attack_square.png");
            spr_protect_square = loader.loadImage("/spr_protect_square.png");
            spr_base3 = loader.loadImage("/spr_base3.png");
            spr_body3 = loader.loadImage("/spr_body3.png");
            spr_bullet3 = loader.loadImage("/spr_bullet3.png");
            spr_base4 = loader.loadImage("/spr_base4.png");
            spr_body4 = loader.loadImage("/spr_body4.png");
            spr_bullet4 = loader.loadImage("/spr_bullet4.png");
            spr_base2 = loader.loadImage("/spr_base2.png");
            spr_tower_selector = loader.loadImage("/spr_tower_selector.png");
            spr_infor1 = loader.loadImage("/spr_infor1.png");
            spr_infor2 = loader.loadImage("/spr_infor2.png");
            spr_infor3 = loader.loadImage("/spr_infor3.png");
            spr_infor4 = loader.loadImage("/spr_infor4.png");
            spr_infor5 = loader.loadImage("/spr_infor5.png");
            spr_evolve_main = loader.loadImage("/spr_evolve_main.png");
            spr_evolve1 = loader.loadImage("/spr_evolve1.png");
            spr_evolve1_maxed = loader.loadImage("/spr_evolve1_maxed.png");
            spr_evolve2 = loader.loadImage("/spr_evolve2.png");
            spr_evolve3 = loader.loadImage("/spr_evolve3.png");
            spr_evolve4 = loader.loadImage("/spr_evolve4.png");
            spr_evolve5 = loader.loadImage("/spr_evolve5.png");
            spr_towers_title = loader.loadImage("/spr_towers_title.png");
            spr_evolve_select = loader.loadImage("/spr_evolve_select.png");
            spr_einfor1 = pixelImage(loader.loadImage("/spr_einfor1.png"), 120, 26);
            spr_einfor2 = pixelImage(loader.loadImage("/spr_einfor2.png"), 120, 26);
            spr_einfor3 = pixelImage(loader.loadImage("/spr_einfor3.png"), 120, 26);
            spr_einfor4 = pixelImage(loader.loadImage("/spr_einfor4.png"), 120, 26);
            spr_einfor5 = pixelImage(loader.loadImage("/spr_einfor5.png"), 120, 26);
            spr_tier = pixelImage(loader.loadImage("/spr_tier.png"), 20, 20);
            spr_insufficient_money = pixelImage(loader.loadImage("/spr_insufficient_money.png"), 190, 37);
            spr_enemy1 = pixelImage(loader.loadImage("/spr_enemy1.png"), 15, 15);
            spr_enemy2 = pixelImage(loader.loadImage("/spr_enemy2.png"), 20, 20);
            spr_enemy3 = pixelImage(loader.loadImage("/spr_enemy3.png"), 25, 25);
            spr_enemy4 = pixelImage(loader.loadImage("/spr_enemy4.png"), 30, 30);
            spr_enemy5 = pixelImage(loader.loadImage("/spr_enemy5.png"), 35, 35);
            spr_enemy6 = pixelImage(loader.loadImage("/spr_enemy6.png"), 40, 40);
            spr_enemy7 = pixelImage(loader.loadImage("/spr_enemy7.png"), 50, 50);
            spr_sc_left = pixelImage(loader.loadImage("/spr_sc_left.png"), 600, 700);
            spr_sc_right = pixelImage(loader.loadImage("/spr_sc_right.png"), 600, 700);
            spr_tutorial = pixelImage(loader.loadImage("/spr_tutorial.png"), 60*3, 60*3);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void render(Graphics2D g){
        for(GameObject tmp: OBJECT){
            if(tmp.getId() != ID.TowerSelector && tmp.getId() != ID.Healthbar && tmp.getId() != ID.Bullet)
            tmp.render(g);
        }
        for(GameObject tmp: OBJECT){
            if(tmp.getId() == ID.Bullet)
            tmp.render(g);
        }
        for(GameObject tmp: OBJECT){
            if(tmp.getId() == ID.Healthbar)
            tmp.render(g);
        }
        for(GameObject tmp: OBJECT){
            if(tmp.getId() == ID.TowerSelector)
            tmp.render(g);
        }
    }
    
    public void addObject(GameObject object){
        OBJECT.add(object);
    }
    
    public void removeObject(GameObject object){
        OBJECT.remove(object);
    }
    
}
