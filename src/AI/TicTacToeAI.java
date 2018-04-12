package AI;

public class TicTacToeAI {
    private int[][] currentField;
    private boolean[] moves;
    private int[] availableMoves;
    private int[] myMoves;
    private int[] moveScores = {1, 2, 4 ,8, 16, 32, 64, 128, 256};
    private int[] winScores = {7, 56, 746, 73, 146, 292, 273, 84};
    private int[] currentSmallScores;
    private int doThisMove=999;

    public TicTacToeAI(int[][] field) {
        //moves houdt met een boolean bij of een plek vrij is op het bord
        moves = new boolean[]{true, true, true, true, true, true, true, true, true};
        //currentField houdt met een int (0, 1, 2) bij wat de status van een plek op het bord is
        currentField = field;
    }

    public void updateField(int[][] field) {
        int iter=0;
        for(int[] colomn:field) {
            for(int pos: colomn) {
                if(pos!=0) {
                    moves[iter] = false;
                }
                iter++;
            }
        }
        currentField = field;
    }

    public void getAvailableMoves() {
        int moveIter = 0;
        int movePos = 0;
        for(boolean move:moves) {
            if(move) {
                availableMoves[moveIter++] = movePos;
            }
            movePos++;
        }
    }

    public void getMyMoves() {
        int moveIter = 0;
        int movePos = 0;
        for(int[] colomn:currentField) {
            for(int move:colomn) {
                if(move==1) {
                    myMoves[moveIter++] = movePos;
                }
                movePos++;

            }
        }
    }

    public void decidingMove() {
        if(myMoves.length>1) {
            int iter = 0;
            for(int i = 0; i<myMoves.length; i++) {
                for(int j = 0; j<myMoves.length; j++) {
                    if(myMoves[i]!=myMoves[j]) {
                        currentSmallScores[iter++] = moveScores[myMoves[i]] + moveScores[myMoves[j]];
                    }
                }
            }
            outerloop:
            for(int winScore:winScores) {
                for(int smallScore:currentSmallScores) {
                    int bestMoveIter=0;
                    for(int moveScore:moveScores) {
                        if(winScore-smallScore==moveScore) {
                            doThisMove=bestMoveIter;
                            break outerloop;
                        }
                        bestMoveIter++;
                    }
                }
            }
            if(doThisMove==999) {
                //Hier minimax doen!!
            }
        } else if(myMoves.length==1) {

        } else { //zelf nog geen zetten, check of bord leeg is
            boolean emptyField=true;
            int counter = 0;
            for(boolean move:moves) {
                if(!move) {
                    emptyField=false;
                    int firstEnemyMove = counter;
                    //bord is niet leeg, tegenspeler heeft een zet
                    break;
                }
                counter++;
            }
        }
    }

}
