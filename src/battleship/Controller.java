package battleship;

import java.util.Scanner;

public class Controller {
    public static void placeShips(Player player) {
        System.out.println(player.getPlayerName() + ", place your ships on the game field");
        player.showBattleField();
        player.placeShips();
        waitForEnter();
    }

    public static void waitForEnter() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        scan.nextLine();
    }

    public static void startGame(Player player1, Player player2) {
        int counter = 0;

        while (player1.getCurrentShipsCount() > 0 && player2.getCurrentShipsCount() > 0) {
            if (counter % 2 == 0) {
                player1.showEnemyBattleField();
                System.out.println("---------------------");
                player1.showBattleField();
                takeShot(player1, player2);
            } else {
                player2.showEnemyBattleField();
                System.out.println("---------------------");
                player2.showBattleField();
                takeShot(player2, player1);
            }

            ++counter;

            if (player1.getCurrentShipsCount() > 0 && player2.getCurrentShipsCount() > 0) {
                waitForEnter();
            }
        }

        if (player1.getCurrentShipsCount() == 0) {
            System.out.println(player1.getPlayerName() + ", you sank the last ship. You won. Congratulations!");
        } else {
            System.out.println(player2.getPlayerName() + ", you sank the last ship. You won. Congratulations!");
        }
    }

    public static void takeShot(Player player1, Player player2) {
        System.out.println(player1.getPlayerName() + ", it's your turn:");
        player1.takeShot(player2);
    }
}
