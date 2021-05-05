package game.beatemup.towers;

import static game.beatemup.global.Functions.*;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Grid.GRID;
import static game.beatemup.manager.Handler.spr_grid;
import static game.beatemup.manager.Handler.spr_range_circle;
import static game.beatemup.manager.Handler.spr_square;
import game.beatemup.enums.ID;
import game.beatemup.enums.TowerType;
import static game.beatemup.manager.Handler.spr_infor1;
import static game.beatemup.manager.Handler.spr_infor2;
import static game.beatemup.manager.Handler.spr_infor3;
import static game.beatemup.manager.Handler.spr_infor4;
import static game.beatemup.manager.Handler.spr_infor5;
import static game.beatemup.manager.Handler.spr_insufficient_money;
import static game.beatemup.manager.Handler.spr_tower_selector;
import static game.beatemup.manager.Handler.spr_towers_title;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 *
 * @author ADMIN
 */
public class TowerSelector extends GameObject {

    public TowerSelector(float x, float y, ID id) {
        super(x, y, id);
    }

    private TowerType tower_type = TowerType.normal_gun;

    @Override
    public void create() {
        image = pixelImage(spr_tower_selector, 600, 100);
        background = pixelImage(spr_square, 600, 100);
        background = colorImage(background, 0, 0, 15);

        grid = pixelImage(spr_grid, 600, 600);
        grid = alphaImage(grid, 20);

        //Glid selector
        selectingSquare = pixelImage(spr_square, 60, 60);
        selectingSquare = alphaImage(selectingSquare, 255 / 5);

        //Tower selector
        selectingTower = pixelImage(spr_square, 120, 120);
        selectingTower = alphaImage(selectingTower, 255 / 5);
        selectingTower = colorImage(selectingTower, 0, 176, 240);

        //Infors
        infor.add(pixelImage(spr_infor1, 180, 180));
        infor.add(pixelImage(spr_infor2, 180, 180));
        infor.add(pixelImage(spr_infor3, 180, 180));
        infor.add(pixelImage(spr_infor4, 180, 180));
        infor.add(pixelImage(spr_infor5, 180, 180));

        //Title
        title_back = pixelImage(spr_towers_title, 180, 30);
        title_back = colorImage(title_back, 0, 0, 15);
        title_back = alphaImage(title_back, 200);
    }

    @Override
    public void update() {
        mouseClicked();
        if (draw_grid) {
            if (mouse_y < 600) {
                if (GRID[real2Grid(mouse_y)][real2Grid(mouse_x)] == 1 && money >= cost) {
                    can_place = true;
                } else {
                    can_place = false;
                }
            }
        }
    }

    public void mouseClicked() {
        if (mouse_clicked) {
            if (mouse_y >= 600 && mouse_x <= 480) {
                if (draw_grid) {
                    draw_grid = false;
                    tileColoring = true;
                } else if(money >= cost) {
                    draw_grid = true;
                    tileColoring = false;
                }
            }
            if (draw_grid) {
                if (mouse_y < 600) {
                    if (GRID[real2Grid(mouse_y)][real2Grid(mouse_x)] == 1 && money >= cost) {
                        GRID[real2Grid(mouse_y)][real2Grid(mouse_x)] = -1;
                        new Tower(grid2Real(real2Grid(mouse_x)) + 30, grid2Real(real2Grid(mouse_y)) + 30, ID.Tower, tower_type);
                        money -= cost;
                    } else {
                        draw_grid = false;
                        tileColoring = true;
                    }
                }
            }
        }
    }

    private int cost = 0;

    public void calculate() {
        if (tower_type == TowerType.normal_gun) {
            range = 120;
            cost = 20;
        }
        if (tower_type == TowerType.cannon) {
            range = 200;
            cost = 60;
        }
        if (tower_type == TowerType.sonic_wave) {
            range = 100;
            cost = 100;
        }
        if (tower_type == TowerType.laser) {
            range = 120;
            cost = 200;
        }
    }

    private boolean can_place = true;
    public static boolean draw_grid = false;
    private BufferedImage grid = null;
    private BufferedImage selectingSquare = null;
    private BufferedImage selectingTower = null;
    private float angle_range = 0;
    private int range = 200, last_range = 0;
    private BufferedImage range_circle = null;
    private boolean colorBlue = false;
    private int xx = 0, yy = 600;
    private BufferedImage background = null;
    private final LinkedList<BufferedImage> infor = new LinkedList<>();

    private BufferedImage title_back;

    @Override
    public void render(Graphics2D g) {

        //Draw circle range
        if (grid != null && draw_grid && mouse_y < 600) {
            angle_range += 0.3f;
            if (range != last_range) {
                range_circle = pixelImage(spr_range_circle, range * 2, range * 2);
                if (!can_place) {
                    range_circle = colorImage(range_circle, 255, 0, 0);
                }
                last_range = range;
            }
            AffineTransform at = new AffineTransform();
            at.rotate(Math.toRadians(angle_range), range, range);
            op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            g.drawImage(range_circle, op, (int) grid2Real(real2Grid(mouse_x)) + 30 - range, (int) grid2Real(real2Grid(mouse_y)) + 30 - range);
        }

        //Draw self
        g.drawImage(background, (int) x, (int) y, null);
        super.render(g);

        //Draw title
        if (mouse_x != clamp(mouse_x, 240 - 30, 360 + 30) || mouse_y != clamp(mouse_y, 540 - 30, 600)) {
            g.drawImage(title_back, 300 - 90, 600 - 30, null);
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.drawString("TOWERS", 300 - 45, 600 - 30 + 22);
        }

        //Draw selecting tower
        if (mouse_y >= 600 && !draw_grid) {
            xx = ((int) Math.round(mouse_x) / 120) * 120;
            selectingTower = colorImage(selectingTower, 0, 176, 240);
            if (mouse_x < 120) {
                tower_type = TowerType.normal_gun;
            }
            if (mouse_x >= 120 && mouse_x < 240) {
                tower_type = TowerType.cannon;
            }
            if (mouse_x >= 240 && mouse_x < 360) {
                tower_type = TowerType.sonic_wave;
            }
            if (mouse_x >= 360 && mouse_x < 480) {
                tower_type = TowerType.laser;
            }
            if (mouse_x > 480) {
                tower_type = TowerType.laser;
            }
            calculate();
            if (money < cost) {
                selectingTower = colorImage(selectingTower, 255, 0, 0);
                g.drawImage(spr_insufficient_money, 600 - 185, 600 - 30, null);
            }
            g.drawImage(selectingTower, xx, yy, null);

            //Infors
            int infor_index = (int) Math.round(mouse_x) / 120;
            infor_index = clamp(infor_index, 0, 4);

            //Draw cost
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            g.drawString("-$" + toMoney(infor_index), 125, 600 - 30 + 22);

            //Infors drawing
            if (mouse_x < 425) {
                g.drawImage(infor.get(infor_index), (int) mouse_x, (int) mouse_y - 180, null);
            } else {
                g.drawImage(infor.get(infor_index), (int) 425, (int) mouse_y - 180, null);
            }

        }

        if (draw_grid) {
            selectingTower = colorImage(selectingTower, 191, 144, 0);
            g.drawImage(selectingTower, xx, yy, null);
        }

        if (grid != null && draw_grid) {

            //Draw grid
            g.drawImage(grid, 0, 0, null);

            //Insufficient money
            if (money < cost) {
                g.drawImage(spr_insufficient_money, 600 - 185, 600 - 30, null);
            }

            //Draw selecting square
            if (mouse_y < 600) {
                if (can_place) {
                    if (!colorBlue) {
                        range_circle = colorImage(range_circle, 0, 176, 240);
                        selectingSquare = colorImage(selectingSquare, 0, 150, 255);
                        colorBlue = true;
                    }
                } else {
                    if (colorBlue) {
                        range_circle = colorImage(range_circle, 255, 0, 0);
                        selectingSquare = colorImage(selectingSquare, 255, 0, 0);
                        colorBlue = false;
                    }
                }
                g.drawImage(selectingSquare, (int) grid2Real(real2Grid(mouse_x)), (int) grid2Real(real2Grid(mouse_y)), null);
            }
        }
    }

    private int toMoney(int x) {
        if (x == 0) {
            return 20;
        }
        if (x == 1) {
            return 60;
        }
        if (x == 2) {
            return 100;
        }
        if (x == 3) {
            return 200;
        }
        return 0;
    }
}
