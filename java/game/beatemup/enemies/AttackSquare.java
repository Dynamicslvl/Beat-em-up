package game.beatemup.enemies;

import game.beatemup.enums.ID;
import static game.beatemup.global.Functions.*;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Grid.GRID;
import static game.beatemup.manager.Handler.spr_attack_square;
import static game.beatemup.manager.Handler.spr_square;
import static game.beatemup.towers.TowerSelector.draw_grid;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class AttackSquare extends GameObject {

    public AttackSquare(float x, float y, ID id) {
        super(x, y, id);
    }

    private int t = 0;
    private int second = 30;
    private int skip_time = 45;
    private BufferedImage back_infor, back_infor2;

    private int ist, jst;
    private LinkedList<String> way = new LinkedList<String>(); //Containing all the paths

    private boolean isAvainable(int i, int j) {
        if (i < 0 || j < 0 || i >= 10 || j >= 10) {
            return false;
        }
        if (GRID[i][j] == 1 || (GRID[i][j] == 2 && i != ist && j != jst)) {
            return false;
        }
        return true;
    }

    private boolean isTarget(int i, int j) {
        if (GRID[i][j] == 3) {
            return true;
        }
        return false;
    }

    public void findWay(int i, int j, String s) {
        if (!isAvainable(i, j)) {
            return;
        }
        if (isTarget(i, j)) {
            way.add(s);
            return;
        }
        GRID[i][j] = 1;
        findWay(i - 1, j, s + "U");
        findWay(i + 1, j, s + "D");
        findWay(i, j + 1, s + "R");
        findWay(i, j - 1, s + "L");
        GRID[i][j] = 0;
    }

    @Override
    public void create() {
        loadWaves();
        image = pixelImage(spr_attack_square, 60, 60);

        x_offset = 30;
        y_offset = 30;
        back_infor = pixelImage(spr_square, 100, 20);
        back_infor = colorImage(back_infor, 0, 0, 15);
        back_infor = alphaImage(back_infor, 255 / 2);
        back_infor2 = pixelImage(spr_square, 80, 16);
        back_infor2 = colorImage(back_infor2, 0, 0, 15);
        back_infor2 = alphaImage(back_infor2, 255 / 2);

        //Create ways
        ist = real2Grid(y);
        jst = real2Grid(x);
        findWay(ist, jst, "");
    }

    @Override
    public void update() {
        
        //Flashing
        flash_alpha -= 3;
        flash_alpha = clamp(flash_alpha, 0, 255);
        
        if(wave >= maxWave) return;

        //Mouse clicked
        if (mouse_clicked && ! draw_grid) {
            if (mouse_x == clamp(mouse_x, x - 30, x + 30) && mouse_y == clamp(mouse_y, y - 30, y + 30) && second <= skip_time) {
                t = 1;
                second = 0;
            }
        }

        if (second == 0 && t == 1) {
            flash_alpha = 255;
            second = 60;
            if (wave < maxWave) {
                EnemySpawner spawner = new EnemySpawner(x, y, ID.EnemySpawner);
                spawner.setWave(way.get(random.nextInt(way.size())), intToEnemyType(typeAtWave.get(wave)), amountAtWave.get(wave));
                spawner.setAs(this);
                skip_time = (int) Math.max(45, 60 - (int) Math.round(((amountAtWave.get(wave) - 1) * intToEnemyType(typeAtWave.get(wave)).spawntime) * 1f / 60f));
                wave = clamp(wave + 1, 0, maxWave);
            }
        }

        //Timing
        t = clamp(t + 1, 0, 60);
        if (t == 60) {
            t = 1;
            second--;
        }

        if (second == -1) {
            second = 60;
        }

        
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

        if (mouse_x == clamp(mouse_x, x - 30, x + 30) && mouse_y == clamp(mouse_y, y - 30, y + 30) && wave < maxWave) {

            //Next in ...
            g.drawImage(back_infor, (int) x - 50, (int) y - 32 - 15 - 5, null);
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g.drawString("NEXT IN " + second + "s", x - 30 - numberOfDigits(second) * 6, y - 32 - 5);

            //click to skip
            g.drawImage(back_infor2, (int) x - 40, (int) y + 28 + 5, null);
            g.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 10));
            if (second <= skip_time) {
                g.drawString("click to skip", x - 28, y + 40 + 5);
            } else {
                g.drawString("skip in " + (second - skip_time) + "s", x - 25, y + 40 + 5);
            }
        } else if (wave < maxWave) {
            g.setColor(new Color(255, 80, 80));
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
            g.drawString(second + "", x + 2 - numberOfDigits(second) * 5, y + 5);
        }
    }

    private LinkedList<Integer> typeAtWave = new LinkedList<>(), amountAtWave = new LinkedList<>();

    public void loadWaves() {
        //Load from file wave.txt
        try {
            File f = new File("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\BeatEmUp\\src\\main\\resources\\wave.txt");
            Scanner cin = new Scanner(f);
            for (int i = 0; i < 30; i++) {
                typeAtWave.add(cin.nextInt());
                amountAtWave.add(cin.nextInt());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File is not found! Please update the link!");
        }
    }

}
