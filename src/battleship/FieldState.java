package battleship;

enum FieldState {
    SHIP("O"),
    FOG("~"),
    MISS("M"),
    HIT("X"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E"),
    F("F"),
    G("G"),
    H("H"),
    I("I"),
    J("J"),
    SPACE(" ");

    private final String visualState;

    FieldState(String visualState) {
        this.visualState = visualState;
    }

    public String getVisualState() {
        return this.visualState;
    }

    public static FieldState getState(PossibleFieldValues value) {
        switch (value) {
            case A: return A;
            case B: return B;
            case C: return C;
            case D: return D;
            case E: return E;
            case F: return F;
            case G: return G;
            case H: return H;
            case I: return I;
            case J: return J;
            case ONE: return ONE;
            case TWO: return TWO;
            case THREE: return THREE;
            case FOUR: return FOUR;
            case FIVE: return FIVE;
            case SIX: return SIX;
            case SEVEN: return SEVEN;
            case EIGHT: return EIGHT;
            case NINE: return NINE;
            case TEN: return TEN;
            case SPACE: return SPACE;
            default:
                return FOG;
        }
    }
}
