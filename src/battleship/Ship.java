package battleship;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private int size;
    private List<Position> positions;

    Ship(int size) {
        positions = new ArrayList<>();
        this.size = size;
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public List<Position> getPositions() {
        return positions;
    }

    public int getSize() {
        return size;
    }

    public void descreaseSize() {
        --size;
    }
}
