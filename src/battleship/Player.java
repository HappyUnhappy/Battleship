package battleship;

import java.util.Scanner;

class Player {
    private final String playerName;
    private final BattleField personalBattleField;
    private final BattleField enemyBattleField;

    Player(String name) {
        playerName = name;
        personalBattleField = new BattleField();
        enemyBattleField = new BattleField();
    }

    public void showBattleField() {
        personalBattleField.showFields();
    }

    public void showEnemyBattleField() {
        enemyBattleField.showFields();
    }

    public void placeShips() {
        Scanner scanner = new Scanner(System.in);

        for (ShipCategories category : ShipCategories.values()) {
            System.out.printf("Enter the coordinates of the %s (%d cells):%n", category.getName(), category.getSize());

            while (true) {
                String shipCoordinate1 = scanner.next();
                String shipCoordinate2 = scanner.next();

                if (personalBattleField.placeShip(shipCoordinate1, shipCoordinate2, category.getSize())) {
                    break;
                }
            }

            this.showBattleField();
        }
    }

    public int getCurrentShipsCount() {
        return personalBattleField.getShipSize();
    }

    public void takeShot(Player enemy) {
        Scanner scanner = new Scanner(System.in);
        String shotPosition = scanner.next();

        ShotStates state = enemy.getPersonalBattleField().checkShot(shotPosition);

        switch (state) {
            case HIT:
                System.out.println("You hit a ship!");
                enemyBattleField.setHit(shotPosition);
                break;
            case MISS:
                System.out.println("You missed!");
                enemyBattleField.setMiss(shotPosition);
                break;
            case SANK:
                enemyBattleField.setHit(shotPosition);
                if (enemy.getCurrentShipsCount() == 0) {
                    System.out.println("You sank the last ship!");
                    break;
                }

                System.out.println("You sank a ship!");
                break;
            case DOUBLE_SHOT:
                System.out.println("You already tried this field.");
            default:
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public BattleField getPersonalBattleField() {
        return personalBattleField;
    }
}