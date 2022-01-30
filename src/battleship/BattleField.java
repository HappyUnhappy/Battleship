package battleship;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class BattleField {
    static final int FIELD_SQUARE_SIZE = 11;
    final Map<Position, FieldState> fieldsMap;
    List<Ship> ships;

    BattleField() {
        fieldsMap = new LinkedHashMap<>();
        ships = new ArrayList<>();
        createField();
    }

    private void createField() {
        for (PossibleFieldValues fieldValue : PossibleFieldValues.values()) {
            this.fieldsMap.put(fieldValue.getPosition(), FieldState.getState(fieldValue));
        }
    }

    public void showFields() {
        for (Map.Entry<Position, FieldState> field : fieldsMap.entrySet()) {
            System.out.print(field.getValue().getVisualState());
            System.out.print(" ");

            if (field.getKey().getX() == 10) {
                System.out.println();
            }
        }
    }

    public boolean placeShip(String shipPosition1, String shipPosition2, int shipSize) {
        try {
            PossibleFieldValues field1 = PossibleFieldValues.valueOf(shipPosition1);
            PossibleFieldValues field2 = PossibleFieldValues.valueOf(shipPosition2);

            if (field1.getPosition().getX() > field2.getPosition().getX() || field1.getPosition().getY() > field2.getPosition().getY()) {
                PossibleFieldValues temp = field1;
                field1 = field2;
                field2 = temp;
            }

            boolean check = checkShipsCoordinates(field1, field2, shipSize);
            if (check) {
                placeShip(field1, field2, shipSize);
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }

        return true;

    }

    private boolean checkShipsCoordinates(PossibleFieldValues field1, PossibleFieldValues field2, int shipSize) {
        if (field1.getPosition().getX() != field2.getPosition().getX() && field1.getPosition().getY() != field2.getPosition().getY()) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        } else if (field1.getPosition().getX() == field2.getPosition().getX() && (Math.abs(field1.getPosition().getY() - field2.getPosition().getY()) + 1 != shipSize)) {
            System.out.println("Error! Wrong length of the ship! Try again:");
            return false;
        } else if (field1.getPosition().getY() == field2.getPosition().getY() && (Math.abs(field1.getPosition().getX() - field2.getPosition().getX()) + 1 != shipSize)) {
            System.out.println("Error! Wrong length of the ship! Try again:");
            return false;
        }

        final Position minPosition = new Position(Math.max(field1.getPosition().getX() - 1, 1), Math.max(field1.getPosition().getY() - 1, 1));
        final Position maxPosition = new Position(Math.min(field2.getPosition().getX() + 1, FIELD_SQUARE_SIZE - 1), Math.min(field2.getPosition().getY() + 1, FIELD_SQUARE_SIZE - 1));

        for (Map.Entry<Position, FieldState> field : fieldsMap.entrySet()) {
           if (minPosition.getX() <= field.getKey().getX() && field.getKey().getX() <= maxPosition.getX()
                   && field.getKey().getY() >= minPosition.getY() && field.getKey().getY() <= maxPosition.getY()
                   && !field.getValue().equals(FieldState.FOG)) {
               System.out.println("Error! Wrong ship location! Try again:");
               return false;
           }
        }

        return true;
    }

    private void placeShip(PossibleFieldValues field1, PossibleFieldValues field2, int shipSize) {
        Ship ship = new Ship(shipSize);

        for (Map.Entry<Position, FieldState> field : fieldsMap.entrySet()) {
            if (field1.getPosition().getX() <= field.getKey().getX() && field.getKey().getX() <= field2.getPosition().getX()
                    && field1.getPosition().getY() <= field.getKey().getY() && field.getKey().getY() <= field2.getPosition().getY()) {
                field.setValue(FieldState.SHIP);
                ship.addPosition(field.getKey());
            }
        }

        ships.add(ship);
    }

    public ShotStates checkShot(String shotField) {
        try {
            PossibleFieldValues fieldValue = PossibleFieldValues.valueOf(shotField);

            if (fieldsMap.get(fieldValue.getPosition()).equals(FieldState.FOG)) {
                fieldsMap.compute(fieldValue.getPosition(), (position, fieldState) -> FieldState.MISS);
                return ShotStates.MISS;
            } else if (fieldsMap.get(fieldValue.getPosition()).equals(FieldState.SHIP)) {
                fieldsMap.compute(fieldValue.getPosition(), (position, fieldState) -> FieldState.HIT);
                setHit(fieldValue);

                if (checkSank(fieldValue)) {
                    return ShotStates.SANK;
                } else {
                    return ShotStates.HIT;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error! You entered the wrong coordinate! Try again:");
            return ShotStates.ERROR;
        }

        return ShotStates.DOUBLE_SHOT;
    }

    private void setHit(PossibleFieldValues fieldValue) {
        Ship hittedShip = null;
        for (Ship ship : ships) {
            for (Position position : ship.getPositions()) {
                if (position.getX() == fieldValue.getPosition().getX() && position.getY() == fieldValue.getPosition().getY()) {
                    hittedShip = ship;
                    break;
                }
            }

            if (hittedShip != null) {
                break;
            }
        }

        assert hittedShip != null;
        hittedShip.descreaseSize();
    }

    private boolean checkSank(PossibleFieldValues fieldValue) {
        Ship targetShip = null;

        for (Ship ship : ships) {
            for (Position position : ship.getPositions()) {
                if (position.getX() == fieldValue.getPosition().getX() && position.getY() == fieldValue.getPosition().getY()) {
                    targetShip = ship;
                    break;
                }
            }

            if (targetShip != null) {
                break;
            }
        }

        assert targetShip != null;

        if (targetShip.getSize() == 0) {
            ships.remove(targetShip);
            return true;
        }

        return false;

        //return ships.stream().findFirst().filter(ship1 -> ship1.getPositions().stream().anyMatch(position -> position.getX() == fieldValue.getPosition().getX() && position.getY() == fieldValue.getPosition().getY())).get().getSize() == 0;
    }

    public void setHit(String shotPosition) {
        PossibleFieldValues fieldValue = PossibleFieldValues.valueOf(shotPosition);
        fieldsMap.compute(fieldValue.getPosition(), (position, fieldState) -> FieldState.HIT);
    }



    public void setMiss(String shotPosition) {
        PossibleFieldValues fieldValue = PossibleFieldValues.valueOf(shotPosition);
        fieldsMap.compute(fieldValue.getPosition(), (position, fieldState) -> FieldState.MISS);
    }

    public int getShipSize() {
        return ships.size();
    }
}