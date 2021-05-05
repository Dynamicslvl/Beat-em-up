package game.beatemup.global;

import game.beatemup.enums.EnemyType;
import game.beatemup.manager.CircleCollider;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author ADMIN
 */
public class Functions {

    public static boolean pause = false;
    public static int wave = 0; 
    public static int maxWave = 30;
    public static Random random = new Random();
    public static int money = 0;
    public static float mouse_x = 0, mouse_y = 0;
    public static boolean mouse_clicked = false;
    public static boolean mouse_pressed = false;
    public static boolean mouse_dragged = false;
    public static boolean evolver_active = false;
    public static boolean tileColoring = true;

    public static EnemyType intToEnemyType(int n) {
        switch (n) {
            case 0 -> {
                return EnemyType.Tiny;
            }
            case 1 -> {
                return EnemyType.Small;
            }
            case 2 -> {
                return EnemyType.Medium;
            }
            case 3 -> {
                return EnemyType.Big;
            }
            case 4 -> {
                return EnemyType.Huge;
            }
            case 5 -> {
                return EnemyType.Lord;
            }
        }
        return EnemyType.Overlord;
    }

    public static int numberOfDigits(int n) {
        if (n == 0) {
            return 1;
        }
        return (int) Math.floor(Math.log((double) n) / Math.log(10)) + 1;
    }

    public static boolean circleCollision(CircleCollider tmp1, CircleCollider tmp2) {
        return tmp1.r + tmp2.r > point_distance(tmp1.x, tmp1.y, tmp2.x, tmp2.y);
    }

    public static float angle_different(float angle1, float angle2) {
        angle1 -= Math.floor(angle1 / 360) * 360;
        angle2 -= Math.floor(angle2 / 360) * 360;
        float sub_angle = Math.abs(angle1 - angle2);
        if (sub_angle > 360 - sub_angle) {
            sub_angle = 360 - sub_angle;
        }
        return sub_angle;
    }

    public static float point_distance(float x1, float y1, float x2, float y2) {
        float diff1 = x2 - x1, diff2 = y2 - y1;
        return (float) Math.sqrt(diff1 * diff1 + diff2 * diff2);
    }

    public static float point_direction(float x1, float y1, float x2, float y2) {
        return (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
    }

    public static int randomRange(int l, int r) {
        return l + random.nextInt(r - l + 1);
    }

    public static int sign(float x) {
        if (x < 0) {
            return -1;
        }
        if (x == 0) {
            return 0;
        }
        return +1;
    }

    public static float char2Angle(char x) {
        if (x == 'D') {
            return 90;
        }
        if (x == 'L') {
            return 180;
        }
        if (x == 'U') {
            return 270;
        }
        return 0;
    }

    public static float lengthdirX(float length, float direction) {
        return (float) ((float) length * Math.cos(Math.toRadians(direction)));
    }

    public static float lengthdirY(float length, float direction) {
        return (float) ((float) length * Math.sin(Math.toRadians(direction)));
    }

    public static int real2Grid(float pos) {
        int tmp = Math.round(pos);
        return tmp / 60;
    }

    public static float grid2Real(int pos) {
        return pos * 60;
    }

    public static int clamp(int value, int minValue, int maxValue) {
        if (value < minValue) {
            return minValue;
        }
        if (value > maxValue) {
            return maxValue;
        }
        return value;
    }

    public static float clamp(float value, float minValue, float maxValue) {
        if (value < minValue) {
            return minValue;
        }
        if (value > maxValue) {
            return maxValue;
        }
        return value;
    }

    public static float clamp(float value, int minValue, int maxValue) {
        if (value < minValue) {
            return minValue;
        }
        if (value > maxValue) {
            return maxValue;
        }
        return value;
    }

    public static double clamp(double value, double minValue, double maxValue) {
        if (value < minValue) {
            return minValue;
        }
        if (value > maxValue) {
            return maxValue;
        }
        return value;
    }

    public static BufferedImage colorImage(BufferedImage image, Color color) {

        int R = color.getRed(), G = color.getGreen(), B = color.getBlue();

        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);

        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, a;
                p = tmp.getRGB(xx, yy);
                a = (p >> 24) & 0xff;
                p = (a << 24) | (R << 16) | (G << 8) | B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage colorImage(BufferedImage image, float R, float G, float B) {

        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);

        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, a;
                p = tmp.getRGB(xx, yy);
                a = (p >> 24) & 0xff;
                p = (a << 24) | ((int) R << 16) | ((int) G << 8) | (int) B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage colorImage(BufferedImage image, int R, int G, int B) {

        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);

        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, a;
                p = tmp.getRGB(xx, yy);
                a = (p >> 24) & 0xff;
                p = (a << 24) | (R << 16) | (G << 8) | B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage alphaImage(BufferedImage image, int a) {

        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);

        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, A, R, G, B;
                p = tmp.getRGB(xx, yy);
                A = (p >> 24) & 0xff;
                if (A == 0) {
                    continue;
                }
                R = (p >> 16) & 0xff;
                G = (p >> 8) & 0xff;
                B = p & 0xff;
                p = (a << 24) | (R << 16) | (G << 8) | B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage addAlphaImage(BufferedImage image, int a) {

        if (image == null) {
            return null;
        }
        BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = tmp.getGraphics();
        g.drawImage(image, 0, 0, null);

        int width = tmp.getWidth();
        int height = tmp.getHeight();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int p, A, R, G, B;
                p = tmp.getRGB(xx, yy);
                A = (p >> 24) & 0xff;
                A = clamp(A + a, 0, 255);
                R = (p >> 16) & 0xff;
                G = (p >> 8) & 0xff;
                B = p & 0xff;
                p = (A << 24) | (R << 16) | (G << 8) | B;
                tmp.setRGB(xx, yy, p);
            }
        }
        return tmp;
    }

    public static BufferedImage pixelImage(BufferedImage image, int xpixel, int ypixel) {

        BufferedImage tmp = new BufferedImage(xpixel, ypixel, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) tmp.getGraphics();
        AffineTransform at = new AffineTransform();
        at.scale((float) xpixel / image.getWidth(), (float) ypixel / image.getHeight());
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        g.drawImage(image, op, 0, 0);

        return tmp;
    }
}
