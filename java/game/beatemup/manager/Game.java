package game.beatemup.manager;

import game.beatemup.effect.ScreenChange;
import game.beatemup.enums.ScType;
import game.beatemup.enums.State;
import static game.beatemup.manager.Grid.*;
import static game.beatemup.manager.Handler.OBJECT;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

/**
 *
 * @author ADMIN
 */
public class Game extends Canvas implements Runnable{
    
    public static final int WIDTH = 615, HEIGHT = 735;
    public static final String TITLE = "BEAT'EM UP";
    public static State gameState = State.Menu;
    
    public static ScreenChange sc = new ScreenChange(ScType.open);
    
    private Thread thread;
    private boolean running = false;
    
    private Handler handler;
    public static Menu menu;
    
    public static void clearObjects(){
        OBJECT.clear();
    }
    
    public static void initObjects(){
        loadGrid();
        loadObject();
    }
    
    public Game(){
        handler = new Handler();
        new Window(WIDTH, HEIGHT, TITLE, this);
    }
    
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        } catch(Exception e){
            e.printStackTrace();
        }
        running = false;
    }
    
    @Override
    public void run(){
        //Init
        initObjects();
        loadImages();
        sc.init();
        sc.setActive(true);
        MouseInput mouseInput = new MouseInput();
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        menu = new Menu();
        this.addMouseListener(menu);
        
        //Running...
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running){
                render();
            }
            frames++;
            if(System.currentTimeMillis()-timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }
    
    private void loadImages(){
        handler.loadImages();
    }
    
    private void tick(){
        sc.tick();
        if(gameState == State.Game) handler.tick();
        if(gameState == State.Menu || gameState == State.About) menu.tick();
    }
    
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(new Color(0, 5, 10));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if(gameState == State.Game) handler.render(g);
        if(gameState == State.Menu || gameState == State.About) menu.render(g);
        sc.render(g);
        g.dispose();
        bs.show();
    }
    
    public static void main(String args[]){
        new Game();
    }
    
}
