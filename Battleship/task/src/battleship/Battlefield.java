package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Battlefield {
    private char fog = '~';  // empty cell or unknown area
    private char cell = 'O'; // cell of the ship
    private char hit = 'X';  // hit cell of the ship
    private char miss = 'M'; // missed hit
    private int height = 10; // height of the battlefield
    private int width = 10;   // wide of the battlefield

    private char[][] field = new char[height][width]; // main game field

    public Battlefield() {
        initField();
    }

    /**
     * Initiate starting condition of the field
     */
    private void initField() {
        for (char[] row : field)
            Arrays.fill(row, fog);
    }

    /**
     * Get the "fog" symbol
     * @return "fog" symbol
     */
    public char getFog() {
        return fog;
    }

    /**
     * Set the "fog" symbol
     * @param fog "fog" symbol
     */
    public void setFog(char fog) {
        this.fog = fog;
    }

    /**
     * Get the ship cell symbol
     * @return ship cell symbol
     */
    public char getCell() {
        return cell;
    }

    /**
     * Set the ship cell symbol
     * @param cell ship cell symbol
     */
    public void setCell(char cell) {
        this.cell = cell;
    }

    /**
     * Get the "hit" symbol
     * @return "hit" symbol
     */
    public char getHit() {
        return hit;
    }

    /**
     * Set the "hit" symbol
     * @param hit "hit" symbol
     */
    public void setHit(char hit) {
        this.hit = hit;
    }

    /**
     * Get the "miss" symbol
     * @return "miss" symbol
     */
    public char getMiss() {
        return miss;
    }

    /**
     * Set the "miss" symbol
     * @param miss miss symbol
     */
    public void setMiss(char miss) {
        this.miss = miss;
    }

    /**
     * Get the game field height
     * @return game field height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the game field height
     * @param height game field height
     */
    public void setHeight(int height) {
        this.height = height;
        this.field = new char[height][width];
    }

    /**
     * get the game field width
     * @return game field width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the game field width
     * @param width game field width
     */
    public void setWidth(int width) {
        this.width = width;
        this.field = new char[height][width];
    }

    /**
     * Get the game field
     * @return field
     */
    public char[][] getField() {
        return field;
    }

    /**
     * Prints the current game field
     */
    public void printField() {
        System.out.print("\n  ");
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

    /**
     * Set the ship using the coordinates of rows and columns of the battlefield
     * @param rowFrom number of field row where ship placement starts (from 0 to height exclusively)
     * @param rowTo number of field row where ship placement ends (from 0 to height exclusively)
     * @param columnFrom number of field column where ship placement starts (from 0 to width exclusively)
     * @param columnTo number of field column where ship placement ends (from 0 to width exclusively)
     */
    public void setTheShip(int rowFrom, int rowTo, int columnFrom, int columnTo) {
        for (int i = rowFrom; i < rowTo + 1; i++) {
            Arrays.fill(field[i], columnFrom, columnTo + 1, cell);
        }
    }


    /**
     * Filling the battlefield with all the ships
     */
    public void fillTheField() {
        for (Ships ship : Ships.values()) {
            int[] coordinates = new UserInputHandler().getUserInput(ship);
            setTheShip(coordinates[0], coordinates[2], coordinates[1], coordinates[3]);
            printField();
        }
    }


    /**
     *  Class for user input handling: getting the coordinates to place the ship and
     *  checking if there is an opportunity for ship placement using the coordinates
     */
    class UserInputHandler {
        private final Scanner scanner = new Scanner(System.in);
        private final int[] coordinates = new int[4];

        public int[] getUserInput(Ships ship) {
            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n", ship.getDescription(), ship.getValue());
            String stringCoordinates;
            do {
                stringCoordinates = scanner.nextLine();
            } while (!checkCoordinates(stringCoordinates, ship));

            return getIntCoordinatesFromString(stringCoordinates);
        }

        public boolean checkCoordinates(String coordinates, Ships ship) {

            // check the initial format of user input
            if (!coordinates.matches("[A-J]\\d+ [A-J]\\d+"))
                System.out.println("Error! Wrong coordinates! Try again:\n");
            else {
                int[] intCoordinates = getIntCoordinatesFromString(coordinates);

                // check if the coordinates fit current ship
                if (!(intCoordinates[2] + 1 - intCoordinates[0] == 1 || intCoordinates[3] + 1 - intCoordinates[1] == 1))
                    System.out.println("Error! Wrong ship location! Try again:\n");

                else if (!(intCoordinates[3] + 1 - intCoordinates[1] == ship.getValue() || intCoordinates[2] + 1 - intCoordinates[0] == ship.getValue()))
                    System.out.printf("Error! Wrong length of the %s! Try again:\n", ship.getDescription());

                // check if the coordinates are close to or crossing another ship
                else if (!checkShipPlacement(intCoordinates))
                    System.out.println("Error! You placed it too close to another one. Try again:\n");

                else
                    return true;
            }
            return false;
        }

        private boolean checkShipPlacement(int[] coord) {
            int iStart = coord[0] != 0 ? coord[0] - 1 : coord[0];
            int iEnd = coord[2] != height - 1 ? coord[2] + 1 : coord[2];
            int jStart = coord[1] != 0 ? coord[1] - 1 : coord[1];
            int jEnd = coord[3] != width - 1 ? coord[3] + 1 : coord[3];
            for (int i = iStart; i <= iEnd; i++) {
                for (int j = jStart; j <= jEnd; j++) {
                    if (field[i][j] != fog)
                        return false;
                }
            }
            return true;
        }

        private int[] getIntCoordinatesFromString(String stringCoordinates) {
            String[] tmpCoordinatesArray = stringCoordinates.split("\\s");
            int[] coordinates = new int[4];
            coordinates[0] = tmpCoordinatesArray[0].charAt(0) - 'A';
            coordinates[1] = Integer.parseInt(tmpCoordinatesArray[0].substring(1)) - 1;
            coordinates[2] = tmpCoordinatesArray[1].charAt(0) - 'A';
            coordinates[3] = Integer.parseInt(tmpCoordinatesArray[1].substring(1)) - 1;

            // swap coordinates so that the bigger one is on the second place
            if (coordinates[0] > coordinates[2]) {
                int tmp = coordinates[0];
                coordinates[0] = coordinates[2];
                coordinates[2] = tmp;
            } else if (coordinates[1] > coordinates[3]) {
                int tmp = coordinates[1];
                coordinates[1] = coordinates[3];
                coordinates[3] = tmp;
            }
            return coordinates;
        }

    }

}
