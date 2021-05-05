package game.beatemup.towers;

import static game.beatemup.global.Functions.*;
import static game.beatemup.towers.TowerSelector.draw_grid;
import game.beatemup.enums.ID;
import game.beatemup.enums.TowerType;
import game.beatemup.manager.GameObject;
import static game.beatemup.manager.Handler.spr_einfor1;
import static game.beatemup.manager.Handler.spr_einfor2;
import static game.beatemup.manager.Handler.spr_einfor3;
import static game.beatemup.manager.Handler.spr_einfor4;
import static game.beatemup.manager.Handler.spr_einfor5;
import static game.beatemup.manager.Handler.spr_evolve1;
import static game.beatemup.manager.Handler.spr_evolve1_maxed;
import static game.beatemup.manager.Handler.spr_evolve2;
import static game.beatemup.manager.Handler.spr_evolve3;
import static game.beatemup.manager.Handler.spr_evolve4;
import static game.beatemup.manager.Handler.spr_evolve5;
import static game.beatemup.manager.Handler.spr_evolve_main;
import static game.beatemup.manager.Handler.spr_evolve_select;
import static game.beatemup.manager.Handler.spr_insufficient_money;
import static game.beatemup.manager.Handler.spr_range_simple;
import static game.beatemup.manager.Handler.spr_tier;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 *
 * @author ADMIN
 */
public class TowerEvolver extends GameObject {

    private boolean active = false;
    private TowerType type;
    private Tower tower;
    private LinkedList<BufferedImage> slot = new LinkedList<>();
    private LinkedList<BufferedImage> infor = new LinkedList<>();
    private BufferedImage select = null;
    private int tier = 1;
    private static final float pdamage = 1.5f, prange = 1.2f, pslowdown = 0.75f, preload = 0.85f, pprice = 1.5f, pcost = 0.5f;
    private static final float maxRange = 500f;

    public TowerEvolver(float x, float y, ID id, TowerType type) {
        super(x, y, id);
        this.type = type;
    }

    //1 = range, 2 = reload, 3 = slowdown, 4 = damage, 5 = sell
    private void addSlot() {
        slot.add(pixelImage(spr_evolve1, 60, 60));
        infor.add(spr_einfor1);
        if (type == TowerType.normal_gun || type == TowerType.cannon) {
            slot.add(pixelImage(spr_evolve2, 60, 60));
            infor.add(spr_einfor2);
            slot.add(pixelImage(spr_evolve4, 60, 60));
            infor.add(spr_einfor4);
        }
        if (type == TowerType.sonic_wave) {
            slot.add(pixelImage(spr_evolve2, 60, 60));
            infor.add(spr_einfor2);
            slot.add(pixelImage(spr_evolve3, 60, 60));
            infor.add(spr_einfor3);
        }
        if (type == TowerType.laser) {
            slot.add(pixelImage(spr_evolve4, 60, 60));
            infor.add(spr_einfor4);
            slot.add(pixelImage(spr_evolve3, 60, 60));
            infor.add(spr_einfor3);
        }
        slot.add(pixelImage(spr_evolve5, 60, 60));
        infor.add(spr_einfor5);
    }

    private BufferedImage rangeImage;
    private int range = 0, rangeO = 0;

    @Override
    public void create() {
        image = pixelImage(spr_evolve_main, 180, 180);
        select = pixelImage(spr_evolve_select, 60, 60);
        x_offset = 90;
        y_offset = 90;
        addSlot();
    }

    private int t_destroy = -1;
    private boolean stop = false;
    private boolean selected = false;

    @Override
    public void update() {
        if (t_destroy != -1) {
            t_destroy = clamp(t_destroy - 1, 0, 10);
        }
        if (t_destroy == 0) {
            tower.destroySelf();
        }
        if (draw_grid) {
            active = false;
            return;
        }
        if (active) {
            calculatePos();
            if (mouse_clicked) {
                //Slot 1: Inc range
                if (point_distance(mouse_x, mouse_y, x + x1, y + y1) <= 20) {
                    if (money >= (int) (tower.getBody().getPrice() * pcost)) {
                        selected = true;
                        select = colorImage(select, 191, 144, 0);
                        if (tower.getBody().getRange() < maxRange) {
                            money -= (int) (tower.getBody().getPrice() * pcost);
                            tier++;
                            tower.getBody().setRange(Math.min(tower.getBody().getRange() * prange, maxRange));
                            tower.getBody().setPrice((int) (tower.getBody().getPrice() * pprice));
                        }
                    }
                }
                //Slot 2
                if (point_distance(mouse_x, mouse_y, x + x2, y + y2) <= 20) {
                    if (money >= (int) (tower.getBody().getPrice() * pcost)) {
                        money -= (int) (tower.getBody().getPrice() * pcost);
                        selected = true;
                        select = colorImage(select, 191, 144, 0);
                        tier++;
                        if (type == TowerType.normal_gun || type == TowerType.cannon || type == TowerType.sonic_wave) {
                            tower.getBody().setReload(tower.getBody().getReload() * preload);
                        } else {
                            tower.getBody().setDamage(tower.getBody().getDamage() * pdamage);
                        }
                        tower.getBody().setPrice((int) (tower.getBody().getPrice() * pprice));
                    }
                }
                //Slot 3
                if (point_distance(mouse_x, mouse_y, x - x2, y - y2) <= 20) {
                    if (money >= (int) (tower.getBody().getPrice() * pcost)) {
                        money -= (int) (tower.getBody().getPrice() * pcost);
                        selected = true;
                        select = colorImage(select, 191, 144, 0);
                        tier++;
                        if (type == TowerType.normal_gun || type == TowerType.cannon) {
                            tower.getBody().setDamage(tower.getBody().getDamage() * pdamage);
                        } else {
                            tower.getBody().setSlowdown(tower.getBody().getSlowdown() * pslowdown);
                        }
                        tower.getBody().setPrice((int) (tower.getBody().getPrice() * pprice));
                    }
                }
                //Slot 4: SELL
                if (point_distance(mouse_x, mouse_y, x - x1, y - y1) <= 20) {
                    money += tower.getBody().getPrice() / 2;
                    selected = true;
                    select = colorImage(select, 191, 144, 0);
                    t_destroy = 10;
                }
            }
        } else {
            resetPos();
            select = colorImage(select, 0, 176, 240);
            selected = false;
        }
        range = (int) tower.getBody().getRange();
        if (range != rangeO) {
            rangeO = range;
            rangeImage = pixelImage(spr_range_simple, range * 2, range * 2);
            if (range == maxRange) {
                slot.remove(0);
                slot.addFirst(pixelImage(spr_evolve1_maxed, 60, 60));
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (!draw_grid) {
            if (tower.isDrawRange()) {
                g.drawImage(rangeImage, (int) x - range, (int) y - range, null);
            }
            if (active) {
                super.render(g);
                if (!selected) {
                    select = colorImage(select, 0, 176, 240);
                }

                g.drawImage(slot.get(0), (int) (x + x1) - 30, (int) (y + y1) - 30, null);
                g.drawImage(slot.get(1), (int) (x + x2) - 30, (int) (y + y2) - 30, null);
                g.drawImage(slot.get(2), (int) (x - x2) - 30, (int) (y - y2) - 30, null);
                g.drawImage(slot.get(3), (int) (x - x1) - 30, (int) (y - y1) - 30, null);

                if (point_distance(mouse_x, mouse_y, x + x1, y + y1) <= 20) {
                    if (t < 60) {
                        stop = true;
                    }
                    if (money < (int) (tower.getBody().getPrice() * pcost) || (range == maxRange)) {
                        select = colorImage(select, 255, 0, 0);
                        if (!selected && !(range == 500f)) {
                            g.drawImage(spr_insufficient_money, 600 - 185, 600 - 30, null);
                        }
                    }
                    g.drawImage(select, (int) (x + x1) - 30, (int) (y + y1) - 30, null);
                    if (range != maxRange) {
                        g.drawImage(infor.get(0), (int) mouse_x, (int) mouse_y - 26, null);
                    }

                    if (!selected && range != maxRange) {
                        g.setColor(Color.white);
                        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                        g.drawString("-$" + (int) (tower.getBody().getPrice() * pcost), 125, 600 - 30 + 22);
                    }
                } else if (point_distance(mouse_x, mouse_y, x - x2, y - y2) <= 20) {
                    if (t < 60) {
                        stop = true;
                    }
                    if (money < (int) (tower.getBody().getPrice() * pcost)) {
                        select = colorImage(select, 255, 0, 0);
                        if (!selected) {
                            g.drawImage(spr_insufficient_money, 600 - 185, 600 - 30, null);
                        }
                    }
                    g.drawImage(select, (int) (x - x2) - 30, (int) (y - y2) - 30, null);
                    g.drawImage(infor.get(2), (int) mouse_x, (int) mouse_y - 26, null);

                    if (!selected) {
                        g.setColor(Color.white);
                        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                        g.drawString("-$" + (int) (tower.getBody().getPrice() * pcost), 125, 600 - 30 + 22);
                    }
                } else if (point_distance(mouse_x, mouse_y, x + x2, y + y2) <= 20) {
                    if (t < 60) {
                        stop = true;
                    }
                    if (money < (int) (tower.getBody().getPrice() * pcost)) {
                        select = colorImage(select, 255, 0, 0);
                        if (!selected) {
                            g.drawImage(spr_insufficient_money, 600 - 185, 600 - 30, null);
                        }
                    }
                    g.drawImage(select, (int) (x + x2) - 30, (int) (y + y2) - 30, null);
                    g.drawImage(infor.get(1), (int) mouse_x, (int) mouse_y - 26, null);

                    if (!selected) {
                        g.setColor(Color.white);
                        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                        g.drawString("-$" + (int) (tower.getBody().getPrice() * pcost), 125, 600 - 30 + 22);
                    }
                } else if (point_distance(mouse_x, mouse_y, x - x1, y - y1) <= 20) {
                    if (t < 60) {
                        stop = true;
                    }
                    g.drawImage(select, (int) (x - x1) - 30, (int) (y - y1) - 30, null);
                    g.drawImage(infor.get(3), (int) mouse_x, (int) mouse_y - 26, null);

                    if (!selected) {
                        g.setColor(Color.white);
                        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                        g.drawString("+$" + (int) (tower.getBody().getPrice() / 2), 125, 600 - 30 + 22);
                    }
                } else {
                    stop = false;
                }
            }
        }
        //Tier
        g.drawImage(spr_tier, (int) x + 9, (int) y + 5, null);
        g.setColor(Color.white);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        g.drawString("" + tier, x + 19 - numberOfDigits(tier) * 4, y + 20);
    }

    private float x1 = -60, y1 = 0, x2 = 0, y2 = -60;
    private boolean xinc = true, yinc = false, x2inc = true, y2inc = true;
    private float rotate_speed = 3f;
    private int t = 80;

    private void resetPos() {
        t = 80;
        x1 = -60;
        y1 = 0;
        x2 = 0;
        y2 = -60;
        xinc = true;
        yinc = false;
        x2inc = true;
        y2inc = true;
    }

    private void calculatePos() {
        if (stop) {
            return;
        }
        if (t == 0) {
            t = 80;
        }
        t = clamp(t - 1, 0, 80);
        if (t < 60) {
            return;
        }
        if (xinc) {
            x1 = clamp(x1 + rotate_speed, -60, 60);
            if (x1 == 60) {
                xinc = false;
            }
        } else {
            x1 = clamp(x1 - rotate_speed, -60, 60);
            if (x1 == -60) {
                xinc = true;
            }
        }
        if (yinc) {
            y1 = clamp(y1 + rotate_speed, -60, 60);
            if (y1 == 60) {
                yinc = false;
            }
        } else {
            y1 = clamp(y1 - rotate_speed, -60, 60);
            if (y1 == -60) {
                yinc = true;
            }
        }
        if (x2inc) {
            x2 = clamp(x2 + rotate_speed, -60, 60);
            if (x2 == 60) {
                x2inc = false;
            }
        } else {
            x2 = clamp(x2 - rotate_speed, -60, 60);
            if (x2 == -60) {
                x2inc = true;
            }
        }
        if (y2inc) {
            y2 = clamp(y2 + rotate_speed, -60, 60);
            if (y2 == 60) {
                y2inc = false;
            }
        } else {
            y2 = clamp(y2 - rotate_speed, -60, 60);
            if (y2 == -60) {
                y2inc = true;
            }
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

}
