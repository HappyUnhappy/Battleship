package battleship;

public class Main {
    public static void main(String[] args) {
        Player playerOne = new Player("Player 1");
        Player playerTwo = new Player("Player 2");

        Controller.placeShips(playerOne);
        Controller.placeShips(playerTwo);
        Controller.startGame(playerOne, playerTwo);
    }
}
