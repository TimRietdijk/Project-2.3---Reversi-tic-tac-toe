public class AI {
    public class Board {

        private Tile myTile;
        Board() {
            setState(new Empty());
        }

        void setState(final Tile newState) {
            myTile = newState;
        }
    }

    public interface Tile {

    }

    public class Empty implements Tile {

    }

    public class Ours implements Tile {

    }

    public class Traitors implements Tile {

    }
}
