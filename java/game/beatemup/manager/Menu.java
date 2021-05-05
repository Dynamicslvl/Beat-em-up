package game.beatemup.manager;

import game.beatemup.enums.State;
import static game.beatemup.global.Functions.*;
import game.beatemup.effect.Decor;
import static game.beatemup.manager.Handler.spr_about;
import static game.beatemup.manager.Handler.spr_about_content;
import static game.beatemup.manager.Handler.spr_back_menu;
import static game.beatemup.manager.Handler.spr_front_menu;
import static game.beatemup.manager.Handler.spr_motion_back;
import static game.beatemup.manager.Handler.spr_play;
import static game.beatemup.manager.Handler.spr_setting;
import static game.beatemup.manager.Handler.spr_title;
import static game.beatemup.manager.Handler.spr_tutorial;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 *
 * @author ADMIN
 */
public class Menu extends MouseAdapter {

    private boolean init = true;
    private BufferedImage background;
    private BufferedImage frontground;
    private BufferedImage motionback;
    private float motionbackAngle = 0;
    private BufferedImage title;
    private BufferedImage play;
    private BufferedImage setting;
    private BufferedImage about;
    private BufferedImage aboutcontent;
    private LinkedList<Decor> decor = new LinkedList<>();

    public void create() {
        background = pixelImage(spr_back_menu, 600, 700);
        frontground = pixelImage(spr_front_menu, 600, 700);
        motionback = pixelImage(spr_motion_back, 700, 700);
        title = pixelImage(spr_title, 600, 516);
        play = pixelImage(spr_play, 170, 130);
        setting = pixelImage(spr_setting, 190, 120);
        about = pixelImage(spr_about, 180, 120);
        aboutcontent = pixelImage(spr_about_content, 300, 240);
        decor.add(new Decor(100));
        decor.add(new Decor(150));
        decor.add(new Decor(125));
        decor.add(new Decor(175));
    }

    private int tScWait = -1;

    public void update() {
        //Timing
        tScWait = (int) Math.max(-1, tScWait - 1);
        if (tScWait == 0) {
            Game.sc.setActive(true);
            Game.gameState = State.Game;
            Game.clearObjects();
            Game.initObjects();
            pause = false;
        }

        //Something below
        motionbackAngle -= 0.2f;

        if (!Game.sc.isActive() && Game.gameState == State.Menu) {
            if (mouse_x == clamp(mouse_x, 60, 3 * 60)) {
                if (mouse_y == clamp(mouse_y, 5 * 60, 6 * 60)) {
                    play = colorImage(play, 0, 176, 240);
                } else {
                    play = colorImage(play, 255, 255, 255);
                }
                if (mouse_y == clamp(mouse_y, 6 * 60, 7 * 60)) {
                    setting = colorImage(setting, 0, 176, 240);
                } else {
                    setting = colorImage(setting, 255, 255, 255);
                }
                if (mouse_y == clamp(mouse_y, 7 * 60, 8 * 60)) {
                    about = colorImage(about, 0, 176, 240);
                } else {
                    about = colorImage(about, 255, 255, 255);
                }
            } else {
                play = colorImage(play, 255, 255, 255);
                setting = colorImage(setting, 255, 255, 255);
                about = colorImage(about, 255, 255, 255);
            }
        }
        for (Decor tmp : decor) {
            tmp.update();
        }
    }

    public void render(Graphics2D g) {

        g.drawImage(background, 0, 0, null);
        for (Decor tmp : decor) {
            tmp.render(g);
        }

        AffineTransform at = new AffineTransform();
        at.rotate(Math.toRadians(motionbackAngle), 350, 350);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(motionback, op, (int) 250, (int) 0);
        g.drawImage(title, 130, 170, null);

        if (Game.gameState == State.Menu) {
            g.drawImage(play, 30, 265, null);
            g.drawImage(setting, 30, 265 + 60, null);
            g.drawImage(about, 30, 265 + 60 + 60, null);
            if (mouse_x == clamp(mouse_x, 60, 3 * 60)) {
                if (mouse_y == clamp(mouse_y, 6 * 60, 7 * 60)) {
                    g.drawImage(spr_tutorial, (int) mouse_x, (int) mouse_y - 60*3, null);
                }
            }
        }

        if (Game.gameState == State.About) {
            g.drawImage(aboutcontent, 43, 60 * 6 + 42, null);
        }

        //Must be last
        g.drawImage(frontground, 0, 0, null);
    }

    public void tick() {
        if (init) {
            create();
            init = false;
        } else {
            update();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Game.sc.isActive()) {
            return;
        }
        if (Game.gameState == State.About) {
            Game.gameState = State.Menu;
        } else if (Game.gameState == State.Menu) {
            if (mouse_x == clamp(mouse_x, 60, 3 * 60)) {
                if (mouse_y == clamp(mouse_y, 5 * 60, 6 * 60)) {
                    Game.sc.setActive(true);
                    tScWait = 60;
                    play = colorImage(play, 191, 144, 0);
                }
            }
            if (mouse_x == clamp(mouse_x, 60, 3 * 60)) {
                if (mouse_y == clamp(mouse_y, 6 * 60, 7 * 60)) {

                }
            }
            if (mouse_x == clamp(mouse_x, 60, 3 * 60)) {
                if (mouse_y == clamp(mouse_y, 7 * 60, 8 * 60)) {
                    Game.gameState = State.About;
                }
            }
        }

    }

    public BufferedImage getPlay() {
        return play;
    }

    public void setPlay(BufferedImage play) {
        this.play = play;
    }
    
}
