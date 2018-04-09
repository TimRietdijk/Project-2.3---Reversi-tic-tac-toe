public class AI {
    public class Board {

        private Tile myTile0;
        private Tile myTile1;
        private Tile myTile2;
        private Tile myTile3;
        private Tile myTile4;
        private Tile myTile5;
        private Tile myTile6;
        private Tile myTile7;
        private Tile myTile8;
        Board() {
            setState(new Empty(), myTile0);
            setState(new Empty(), myTile1);
            setState(new Empty(), myTile2);
            setState(new Empty(), myTile3);
            setState(new Empty(), myTile4);
            setState(new Empty(), myTile5);
            setState(new Empty(), myTile6);
            setState(new Empty(), myTile7);
            setState(new Empty(), myTile8);
        }

        void setState(final Tile newState, Tile tile) {
            tile = newState;
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

    public class TicTacToeAI
}
