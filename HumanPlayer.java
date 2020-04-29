package abalone;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HumanPlayer extends Player {

    BufferedReader reader;
    Neighbor neighbor;
    Edge edge;
    boolean team;
    Marble teammarble;
    SmartStrategy strategy = new SmartStrategy();

    /**
     * The constructor of HumanPlayer.
     * 
     * @requires name != null, marble != null, players != null
     * @param name String
     * @param marble Marble
     * @param players integer
     */
    public HumanPlayer(String name, Marble marble, int players) {
        super(name, marble, players);
        reader = new BufferedReader(new InputStreamReader(System.in));
        neighbor = new Neighbor();
        edge = new Edge();
        if (players == 4) {
            team = true;
        } else {
            team = false;
        }
    }

    /**
     * This method determines a checks if the given move is a valid move.
     * 
     * @requires board != null, opponent != null, choice != null
     * @param board      Board
     * @param opponent   Player
     * @param playermove String
     * @ensures String returned will be either the playermove given to the method in 
     *          case the move is valid, or default in case the move is invalid
     * @return String of the move that is valid, or "default" if the move is invalid
     */
    @Override
    public String determineMove(Board board, Player opponent, String playermove) {
        String choice;
        choice = playermove;
        if (choice.equals("hint")) {
            String hint = strategy.determineMove(board, this, opponent);
            System.out.println(hint);
        }
        String[] arr = choice.split(",");
        boolean valid = false;
        while (!valid) {
            if (arr.length == 3) {
                String crd1 = arr[0];
                String crd2 = arr[1];
                String direction = arr[2];
                if (board.isField(crd1) && board.isField(crd2) && neighbor.isDirection(direction)) {
                    Move move = new Move(board, this);
                    int marbles;
                    marbles = move.howManyMarbles(crd1, crd2);
                    if (marbles == 1) {
                        if (board.getField(crd1) == this.getMarble()) {
                            if (!edge.isEdgeField(crd1, direction)) {
                                if (board.getField(neighbor.getNeighbor(crd1, direction)) == Marble.o) {
                                    valid = true;
                                    break;
                                }
                            }
                        }
                    } else if (marbles == 2) {
                        if (move.isLineMove(crd1, crd2, direction)) {
                            if (move.isPushOff(crd1, crd2, direction)) {
                                if (team && move.checkIfTeammember(board.getField(crd1))
                                        && move.checkIfTeammember(board.getField(crd2))
                                        && board.getField(move.getBackMarble(crd1, crd2, direction)) == this
                                                .getMarble()) {
                                    valid = true;
                                    break;
                                } else if (!team && board.getField(crd1) == this.getMarble()
                                        && board.getField(crd2) == this.getMarble()) {
                                    valid = true;
                                    break;
                                }
                            } else if (move.isSumito(crd1, crd2, direction)) {
                                if (team && move.checkIfTeammember(board.getField(crd1))
                                        && move.checkIfTeammember(board.getField(crd2))
                                        && board.getField(move.getBackMarble(crd1, crd2, direction)) == this
                                                .getMarble()) {
                                    valid = true;
                                    break;
                                } else if (!team && board.getField(crd1) == this.getMarble()
                                        && board.getField(crd2) == this.getMarble()) {
                                    valid = true;
                                    break;
                                }
                            } else {
                                if (board.getField(crd1) == this.getMarble()
                                        && board.getField(crd2) == this.getMarble()) {
                                    if (!edge.isEdgeField(move.getFrontMarble(crd1, crd2, direction), direction)
                                            && board.getField(
                                                    neighbor.getNeighbor(move.getFrontMarble(crd1, crd2, direction),
                                                            direction)) == Marble.o) {
                                        valid = true;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (board.getField(crd1) == this.getMarble() && board.getField(crd2) == this.getMarble()) {
                                if (!edge.isEdgeField(crd1, direction) & !edge.isEdgeField(crd2, direction)) {
                                    if (board.getField(neighbor.getNeighbor(crd1, direction)) == Marble.o
                                            && board.getField(neighbor.getNeighbor(crd2, direction)) == Marble.o) {
                                        valid = true;
                                        break;
                                    }
                                }
                            }
                        }
                    } else if (marbles == 3) {
                        if (move.isLineMove(crd1, crd2, direction)) {
                            if (move.isPushOff(crd1, crd2, direction)) {
                                if (team && move.checkIfTeammember(board.getField(crd1))
                                        && move.checkIfTeammember(board.getField(crd2))
                                        && move.checkIfTeammember(
                                                board.getField(neighbor.getSharedNeighbor(crd1, crd2)))
                                        && board.getField(move.getBackMarble(crd1, crd2, direction)) == this
                                                .getMarble()) {
                                    valid = true;
                                    break;
                                } else if (!team && board.getField(crd1) == this.getMarble()
                                        && board.getField(crd2) == this.getMarble()
                                        && board.getField(neighbor.getSharedNeighbor(crd1, crd2)) == this.getMarble()) {
                                    valid = true;
                                    break;
                                }
                            } else if (move.isSumito(crd1, crd2, direction)) {
                                if (team && move.checkIfTeammember(board.getField(crd1))
                                        && move.checkIfTeammember(board.getField(crd2))
                                        && move.checkIfTeammember(
                                                board.getField(neighbor.getSharedNeighbor(crd1, crd2)))
                                        && board.getField(move.getBackMarble(crd1, crd2, direction)) == this
                                                .getMarble()) {
                                    valid = true;
                                    break;
                                } else if (!team && board.getField(crd1) == this.getMarble()
                                        && board.getField(crd2) == this.getMarble()
                                        && board.getField(neighbor.getSharedNeighbor(crd1, crd2)) == this.getMarble()) {
                                    valid = true;
                                    break;
                                }
                            } else {
                                if (!edge.isEdgeField(move.getFrontMarble(crd1, crd2, direction), direction)
                                        && board.getField(neighbor.getNeighbor(
                                                move.getFrontMarble(crd1, crd2, direction), direction)) == Marble.o) {
                                    if (board.getField(crd1) == this.getMarble()
                                            && board.getField(crd2) == this.getMarble()
                                            && board.getField(neighbor.getSharedNeighbor(crd1, crd2)) == this
                                                    .getMarble()) {
                                        valid = true;
                                        break;
                                    }
                                }
                            }
                        } else {
                            if (!edge.isEdgeField(crd1, direction) && !edge.isEdgeField(crd2, direction)
                                    && !edge.isEdgeField(neighbor.getSharedNeighbor(crd1, crd2), direction)) {
                                if (board.getField(neighbor.getNeighbor(crd1, direction)) == Marble.o
                                        && board.getField(neighbor.getNeighbor(crd2, direction)) == Marble.o
                                        && board.getField(neighbor.getNeighbor(neighbor.getSharedNeighbor(crd1, crd2),
                                                direction)) == Marble.o) {
                                    if (board.getField(crd1) == this.getMarble()
                                            && board.getField(crd2) == this.getMarble()
                                            && board.getField(neighbor.getSharedNeighbor(crd1, crd2)) == this
                                                    .getMarble()) {
                                        valid = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("ERROR: this is not a valid choice, please give 'coordinate1,coordinate2,direction' "
                    + "with valid values");
            return "default";
        }
        System.out.println("Move is valid");
        return choice;
    }

}