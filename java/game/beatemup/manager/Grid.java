package game.beatemup.manager;

import game.beatemup.enums.ID;
import game.beatemup.towers.TowerSelector;
import game.beatemup.enemies.AttackSquare;
import static game.beatemup.global.Functions.grid2Real;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author ADMIN
 */
public class Grid {
    
    public static int[][] GRID = new int[10][10];

    public static void loadGrid(){
        //Default
        for(int i = 0; i<10; i++){
            for(int j = 0; j<10; j++){
                GRID[i][j] = 1;
            }
        }
        //Load from file grid.txt
        try {
            File f = new File("C:\\Users\\ADMIN\\Documents\\NetBeansProjects\\BeatEmUp\\src\\main\\resources\\grid.txt");
            Scanner cin = new Scanner(f);
            int i = 0, j = 0;
            while(cin.hasNextInt()){
                GRID[i][j] = cin.nextInt();
                j++;
                if(j == 10){
                    j = 0; i++;
                }
                if(i == 10) break;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File is not found! Please update the link!");
        }
    }
    
    public static void loadObject(){
        for(int i = 0; i<10; i++){
            for(int j = 0; j<10; j++){
                if(GRID[i][j] == 1){
                    new Square(grid2Real(j), grid2Real(i), ID.Square);
                }
                if(GRID[i][j] == 2){
                    new AttackSquare(grid2Real(j) + 30, grid2Real(i) + 30, ID.Healthbar);
                }
                if(GRID[i][j] == 3){
                    new ProtectSquare(grid2Real(j) + 30, grid2Real(i) + 30, ID.ProtectSquare);
                }
            }
        }
        new MoneyManager(0, 600 - 30, ID.TowerSelector);
        new TowerSelector(0, 600, ID.TowerSelector);
        new GameController(0, 0, ID.TowerSelector);
        new PauseGame(600 - 30, 0, ID.TowerSelector);
    }
}
