package battleship;

public class Main {

    public static void main(String[] args) {

        Battlefield battlefield = new Battlefield();
        //battlefield.printField();
        battlefield.setTheShip(4, 5, 2, 5);
        battlefield.printField();
        battlefield.setCell('X');
        battlefield.setTheShip(2, 5, 3, 4);
        battlefield.printField();
    }
}
