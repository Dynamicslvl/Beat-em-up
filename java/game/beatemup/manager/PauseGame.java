package game.beatemup.manager;

import static game.beatemup.manager.GameController.isDefeated;
import game.beatemup.enums.ID;
import game.beatemup.enums.ScType;
import game.beatemup.enums.State;
import static game.beatemup.global.Functions.*;
import static game.beatemup.manager.Handler.spr_square;
import static game.beatemup.towers.TowerSelector.draw_grid;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author ADMIN
 */
public class PauseGame extends GameObject {

    public PauseGame(float x, float y, ID id) {
        super(x, y, id);
    }

    @Override
    public void tick() {
        //Timing
        tScWait = (int) Math.max(-1, tScWait - 1);
        if (tScWait == 0) {
            Game.sc.setActive(true);
            Game.gameState = State.Menu;
            Game.menu.setPlay(colorImage(Game.menu.getPlay(), 255, 255, 255));
            pause = false;
        }
        
        //Flashing
        flash_alpha = clamp(flash_alpha - 5, 0, 255);

        if (isDefeated()) {
            pause = true;
        }

        //Mouse click event
        if (draw_grid) {
            return;
        }
        if (mouse_clicked && !Game.sc.isActive()) {
            if (mouse_x == clamp(mouse_x, x, x + 30) && mouse_y == clamp(mouse_y, y, y + 30) && !isDefeated()) {
                flash_alpha = 255;
                pause = !pause;
            }
            if (pause) {
                if (mouse_x == clamp(mouse_x, 300 - 135, 300 - 135 + 270) && mouse_y == clamp(mouse_y, 300 + 45, 300 + 45 + 30)) {
                    tScWait = 60;
                    Game.sc.setActive(true);
                }
                if (mouse_x == clamp(mouse_x, 300 - 135, 300 - 135 + 270) && mouse_y == clamp(mouse_y, 300 + 80, 300 + 80 + 30)) {
                    Game.clearObjects();
                    Game.initObjects();
                    pause = false;
                    Game.sc.setActive(true);
                    Game.sc.setType(ScType.open);
                    Game.sc.init();
                }
            }
        }
        super.tick();
    }

    private BufferedImage back1, back2, back3;
    private int flash_alpha = 0;
    private BufferedImage flash;

    @Override
    public void create() {
        image = pixelImage(spr_square, 30, 30);
        image = colorImage(image, 0, 0, 15);
        image = alphaImage(image, 255 / 2);

        back1 = pixelImage(spr_square, 4 * 60 + 30, 60);
        back1 = colorImage(back1, 0, 0, 15);
        back1 = alphaImage(back1, 200);

        back2 = pixelImage(spr_square, 4 * 60 + 30, 30);
        back2 = colorImage(back2, 0, 0, 15);
        back2 = alphaImage(back2, 255 / 2);

        back3 = pixelImage(spr_square, 4 * 60 + 30, 30);
        back3 = colorImage(back3, 0, 0, 15);
        back3 = alphaImage(back3, 255 / 2);
    }

    
    private int tScWait = -1;
    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics2D g) {
        
        //Flashing
        if (flash_alpha != 0) {
            flash = colorImage(image, 191, 144, 0);
            flash = alphaImage(flash, flash_alpha);
            g.drawImage(flash, (int) x, (int) y, null);
        }

        if (draw_grid) {
            return;
        }

        boolean defeat = isDefeated();

        if (!defeat) {
            super.render(g);
        }

        if (mouse_x == clamp(mouse_x, x, x + 30) && mouse_y == clamp(mouse_y, y, y + 30) && flash_alpha == 0) {
            image = colorImage(image, 0, 176, 240);
        } else {
            image = colorImage(image, 0, 0, 15);
        }
        if (pause) {
            //Play button
            if (!defeat) {
                g.setColor(Color.white);
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                g.drawString("▶", x + 7, y + 22);
            }

            //GAME PAUSED
            g.drawImage(back1, 300 - 135, 300 - 20, null);
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
            if (!defeat) {
                g.drawString("GAME PAUSED", 190, 300 + 20);
            } else {
                g.drawString("  GAME OVER", 190, 300 + 20);
            }

            ///OPTIONS
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

            //Mainmenu
            if (mouse_x == clamp(mouse_x, 300 - 135, 300 - 135 + 270) && mouse_y == clamp(mouse_y, 300 + 45, 300 + 45 + 30)) {
                back2 = colorImage(back2, 0, 176, 240);
                if (mouse_pressed) {
                    back2 = colorImage(back2, 191, 144, 0);
                }
            } else {
                back2 = colorImage(back2, 0, 0, 15);
            }
            g.drawImage(back2, 300 - 135, 300 + 45, null);
            g.drawString("mainmenu", 250, 300 + 45 + 20);

            //Restart
            if (mouse_x == clamp(mouse_x, 300 - 135, 300 - 135 + 270) && mouse_y == clamp(mouse_y, 300 + 80, 300 + 80 + 30)) {
                back3 = colorImage(back3, 0, 176, 240);
                if (mouse_pressed) {
                    back3 = colorImage(back3, 191, 144, 0);
                }
            } else {
                back3 = colorImage(back3, 0, 0, 15);
            }
            g.drawImage(back3, 300 - 135, 300 + 80, null);
            g.drawString("restart", 265, 300 + 80 + 20);

        } else {
            if (!defeat) {
                g.setColor(Color.white);
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                g.drawString("II", x + 10, y + 22);
            }
        }
    }

    private boolean isVictoty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
