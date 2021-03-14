package battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Battlefield {
    private char fog = '~';  // empty cell or unknown area
    private char cell = 'O'; // cell of the ship
    private char hit = 'X';  // hit cell of the ship
    private char miss = 'M'; // missed hit
    private int height = 10; // height of the battlefield
    private int width = 10;  // wide of the battlefield
    private Player player1;  // first player
    private Player player2;  // second player

    private final UserInputHandler inputHandler = new UserInputHandler(); // object for handling user input

    public Battlefield() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        initField(player1.getField());
        initField(player1.getFogField());
        initField(player2.getField());
        initField(player2.getFogField());


    }

    public Battlefield(char fog, char cell, char hit, char miss, int height, int width, String player1, String player2) {
        this.fog = fog;
        this.cell = cell;
        this.hit = hit;
        this.miss = miss;
        this.height = height;
        this.width = width;
        this.player1 = new Player(player1);
        this.player2 = new Player(player2);
        initField(this.player1.getField());
        initField(this.player1.getFogField());
        initField(this.player2.getField());
        initField(this.player2.getFogField());
    }

    /**
     * Initiate starting condition of the field
     */
    private void initField(char[][] array) {
        for (char[] row : array)
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
     * get the game field width
     * @return game field width
     */
    public int getWidth() {
        return width;
    }

    /**
     * get the first player
     * @return first player
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * set the first player
     * @param player1 first player
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * get the second player
     * @return second player
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * set the second player
     * @param player2 second player
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * Prints the current game field
     * @param gameField field or fogField
     */
    public void printField(char[][] gameField) {
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
                System.out.print(" " + gameField[i][j]);
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
     * @param field battlefield where the ship should be placed
     */
    public void setTheShip(int rowFrom, int rowTo, int columnFrom, int columnTo, char[][] field) {
        for (int i = rowFrom; i < rowTo + 1; i++) {
            Arrays.fill(field[i], columnFrom, columnTo + 1, cell);
        }
    }


    /**
     * Filling the battlefield with all the ships
     * @param player the player, on which field the ships should be placed
     */
    public void fillTheField(Player player) {
        System.out.printf("%s, place your ships on the game field\n", player.getName());
        printField(player.getField());
        for (Ships ship : Ships.values()) {
            int[] coordinates = inputHandler.getShipCoordinates(ship, player);
            setTheShip(coordinates[0], coordinates[2], coordinates[1], coordinates[3], player.getField());
            printField(player.getField());
        }
    }

    /**
     * place all the ships of both players
     */
    public void fillPlayersFields() {
        fillTheField(player1);
        passTheMove();
        fillTheField(player2);
        passTheMove();
    }

    /**
     * stop the game before player presses Enter for passing the move to another one
     */
    public void passTheMove() {
        System.out.println("Press Enter and pass the move to another player\n");
        inputHandler.scanner.nextLine();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    /**
     * Start the game (gets the user shots until all the ships sink)
     */
    public void startTheGame() {
        System.out.println("\nThe game starts!");

        // setting current player, current foe and number of alive cells of current foe
        Player currentPlayer = player1;
        Player currentFoe = player2;
        int aliveCellsPlayer1 = Arrays.stream(Ships.values())
                .map(Ships::getValue)
                .reduce(0, Integer::sum);
        int aliveCellsPlayer2 = aliveCellsPlayer1;
        int currentAliveCells = aliveCellsPlayer2;

        // taking a shot
        while(true) {
            printField(currentFoe.getFogField());
            System.out.println("---------------------");
            printField(currentPlayer.getField());

            System.out.printf("\n%s, it's your turn:\n", currentPlayer.getName());
            ShotResult shotResult = takeAShot(currentFoe);
            if (shotResult == ShotResult.HIT) {
                currentAliveCells--;
                System.out.println("\nYou hit a ship!");
            } else if (shotResult == ShotResult.SANK) {
                currentAliveCells--;
                if (currentAliveCells != 0)
                    System.out.println("\nYou sank a ship!");
                else {
                    System.out.println("\nYou sank the last ship. You won. Congratulations!");
                    return;
                }
            } else {
                System.out.println("\nYou missed!");
            }

            // change current player
            if (currentPlayer == player1) {
                currentPlayer = player2;
                currentFoe = player1;
                aliveCellsPlayer2 = currentAliveCells;
                currentAliveCells = aliveCellsPlayer1;
            } else {
                currentPlayer = player1;
                currentFoe = player2;
                aliveCellsPlayer1 = currentAliveCells;
                currentAliveCells = aliveCellsPlayer2;
            }
            passTheMove();
        }
    }

    /**
     * Take a shot
     * @return ShotResult result of the shot (MISS, SANK or HIT)
     */
    private ShotResult takeAShot(Player currentFoe) {
        int[] coord = inputHandler.getShotCoordinates();
        char[][] field = currentFoe.getField();
        char[][] fogField = currentFoe.getFogField();
        ShotResult result;

        if (field[coord[0]][coord[1]] == cell) {
            field[coord[0]][coord[1]] = hit;
            fogField[coord[0]][coord[1]] = hit;
            if (checkShipSank(coord, field))
                result = ShotResult.SANK;
            else
                result = ShotResult.HIT;
        }
        else if (field[coord[0]][coord[1]] == fog) {
            field[coord[0]][coord[1]] = miss;
            fogField[coord[0]][coord[1]] = miss;
            result = ShotResult.MISS;
        }
        else
            result = ShotResult.MISS;
        return result;
    }

    /**
     * Checks if there is no ship cells next to the successive shot
     * @param coord successive shot coordinates
     * @return true if there is no ship cells next to successive shot coordinates, false otherwise
     */
    private boolean checkShipSank(int[] coord, char[][] field) {

        return (coord[0] == 0 || field[coord[0] - 1][coord[1]] != cell) &&
                (coord[0] == (height - 1) || field[coord[0] + 1][coord[1]] != cell) &&
                (coord[1] == 0 || field[coord[0]][coord[1] - 1] != cell) &&
                (coord[1] == width - 1 || field[coord[0]][coord[1] + 1] != cell);

    }


    /**
     *  Class for user input handling: getting the coordinates to place the ship and
     *  checking if there is an opportunity for ship placement using the coordinates
     */
    class UserInputHandler {
        private final Scanner scanner = new Scanner(System.in);

        /**
         * Get the ship coordinates from user input
         * @param ship the ship which coordinates we want to get
         * @return array with ship coordinates
         */
        public int[] getShipCoordinates(Ships ship, Player player) {
            System.out.printf("\nEnter the coordinates of the %s (%d cells):\n", ship.getDescription(), ship.getValue());
            String stringCoordinates;
            do {
                stringCoordinates = scanner.nextLine();
            } while (!checkCoordinates(stringCoordinates, ship, player));

            return getIntCoordinatesFromString(stringCoordinates);
        }

        /**
         * Check if the coordinates are appropriate for ship placement
         * @param coordinates coordinates from user input
         * @param ship ship type
         * @return result of the checking
         */
        private boolean checkCoordinates(String coordinates, Ships ship, Player player) {

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
                else if (!checkShipPlacement(intCoordinates, player.getField()))
                    System.out.println("Error! You placed it too close to another one. Try again:\n");

                else
                    return true;
            }
            return false;
        }

        /**
         * Check if the ship coordinates are not crossing or too close to another ship
         * @param coord ship coordinates
         * @return result of the checking
         */
        private boolean checkShipPlacement(int[] coord, char[][] field) {
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

        /**
         * Convert string ship coordinates to int array
         * @param stringCoordinates ship coordinates
         * @return array of ship coordinates
         */
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

        /**
         * Get shot coordinates from user input
         * @return array of coordinates
         */
        public int[] getShotCoordinates() {
            String stringCoordinates;

            do {
                stringCoordinates = scanner.nextLine();
            } while (!checkCoordinates(stringCoordinates));

            int[] intCoordinates = new int[2];
            intCoordinates[0] = stringCoordinates.charAt(0) - 'A';
            intCoordinates[1] = Integer.parseInt(stringCoordinates.substring(1)) - 1;

            return intCoordinates;
        }

        /**
         * Check if the user entered coordinates in an appropriate format
         * @param stringCoordinates shot coordinates
         * @return result of the checking
         */
        private boolean checkCoordinates(String stringCoordinates) {
            if (!stringCoordinates.matches("[A-J]\\d+") ||
                    Integer.parseInt(stringCoordinates.substring(1)) > 10
            ) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                return false;
            }
            return true;
        }
    }

    enum ShotResult {
        HIT,
        SANK,
        MISS
    }

    class Player {
        private String name;
        private final char[][] field = new char[height][width]; // main game field, ships are hidden from player
        private final char[][] fogField = new char[height][width]; // game field, showing players shots without ships

        public Player(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public char[][] getField() {
            return field;
        }

        public char[][] getFogField() {
            return fogField;
        }


    }

}
