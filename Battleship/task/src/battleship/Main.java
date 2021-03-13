package battleship;

public class Main {

    public static void main(String[] args) {

        Battlefield battlefield = new Battlefield();
        battlefield.printField();
        battlefield.fillTheField();
        battlefield.startTheGame();

    }
}
