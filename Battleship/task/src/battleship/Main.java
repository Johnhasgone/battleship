package battleship;

public class Main {

    public static void main(String[] args) {

        Battlefield battlefield = new Battlefield();
        battlefield.fillPlayersFields();
        battlefield.startTheGame();
    }
}
