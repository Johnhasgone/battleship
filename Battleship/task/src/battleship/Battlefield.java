package battleship;

import java.util.Arrays;

public class Battlefield {
    private char fog = '~';  // empty cell or unknown area
    private char cell = 'O'; // cell of the ship
    private char hit = 'X';  // hit cell of the ship
    private char miss = 'M'; // missed hit
    private int height = 10; // height of the battlefield
    private int width = 10;   // wide of the battlefield

    private char[][] field = new char[height][width];

    public Battlefield() {
        initField();
    }

    private void initField() {
        for (char[] row : field)
            Arrays.fill(row, fog);
    }

    public char getFog() {
        return fog;
    }

    public void setFog(char fog) {
        this.fog = fog;
    }

    public char getCell() {
        return cell;
    }

    public void setCell(char cell) {
        this.cell = cell;
    }

    public char getHit() {
        return hit;
    }

    public void setHit(char hit) {
        this.hit = hit;
    }

    public char getMiss() {
        return miss;
    }

    public void setMiss(char miss) {
        this.miss = miss;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        this.field = new char[height][width];
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        this.field = new char[height][width];
    }

    public char[][] getField() {
        return field;
    }

    public void printField() {
        System.out.print("  ");
        for (int i = 1; i <= width; i++) {
            if (i != width)
                System.out.print(i + " ");
            else
                System.out.println(i);
        }
        for (int i = 0; i < height; i++) {
            System.out.print((char) ('A' + i));
            for (int j = 0; j < width; j++) {
                System.out.print(" " + field[i][j]);
            }
            System.out.println();
        }
    }

    public void setTheShip(int rowFrom, int rowTo, int columnFrom, int columnTo) {
        for (int i = rowFrom; i < rowTo; i++) {
            Arrays.fill(field[i], columnFrom, columnTo, cell);
        }
    }

    //public void fillTheField

}
