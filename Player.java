package abalone;

public abstract class Player {

    private String name;
    private Marble marble;
    private int points;
    Board board;
    Neighbor neighbor;
    int players;

    /**
     * The constructor of Player.
     * 
     * @param name String
     * @param marble Marble
     * @param players integer
     */
    public Player(String name, Marble marble, int players) {
        this.name = name;
        this.marble = marble;
        this.points = 0;
        neighbor = new Neighbor();
        this.players = players;
    }

    /** 
     * This method gets how many players this player is in a game with.
     * 
     * @ensures the amount of players that this player is in a game with returned
     * @return integer players
     */
    public int getPlayers() {
        return players;
    }

    /**
     * This method gets the name of the specific player.
     * 
     * @ensures the name of the player returned
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * This method gets the marble of the player.
     * 
     * @ensures the marble of the player returned
     * @return Marble marble
     */
    public Marble getMarble() {
        return marble;
    }

    /**
     * Returns the amount of points a player has gained in the game.
     * 
     * @ensures a return of points that is between 0 and 6
     * @return integer points
     */
    public int getPoints() {
        return points;
    }

    /**
     * This method sets the point amount of the player to a specific amount.
     * 
     * @param points integer
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * This method tries to make a move on the board, given the move and the
     * opponent and returns if it was successful.
     * 
     * @requires board != null && opponent != null && playermove != null
     * @param board Board
     * @param opponent Player
     * @param playermove Player
     * @ensures true is the move is successful, false if the move was not valid
     *          and thus unsuccessful
     * @return true if the move was successful
     */
    public boolean makeMove(Board board, Player opponent, String playermove) {
        String choice = determineMove(board, opponent, playermove);
        if (!choice.equals("default")) {
            String[] choicearr = choice.split(",");
            String crd1 = choicearr[0];
            String crd2 = choicearr[1];
            String drc = choicearr[2];
            Move move = new Move(board, this);
            if (move.isPushOff(crd1, crd2, drc)) {
                if (move.howManyMarbles(crd1, crd2) == 2) {
                    if (move.getFrontMarble(crd1, crd2, drc).equals(crd1)) {
                        Marble marble1 = board.getField(crd1);
                        Marble marble2 = board.getField(crd2);
                        move.moveMarble(crd1, drc, marble1);
                        move.moveMarble(crd2, drc, marble2);
                    } else {
                        Marble marble1 = board.getField(crd1);
                        Marble marble2 = board.getField(crd2);
                        move.moveMarble(crd2, drc, marble2);
                        move.moveMarble(crd1, drc, marble1);
                    }
                    points = points + 1;
                    return true;
                } else if (move.howManyMarbles(crd1, crd2) == 3) {
                    if (move.isThreeToOnePush(crd1, crd2, drc)) {
                        if (move.getFrontMarble(crd1, crd2, drc).equals(crd1)) {
                            Marble marble1 = board.getField(crd1);
                            Marble marble2 = board.getField(crd2);
                            Marble marble3 = board.getField(neighbor.getSharedNeighbor(crd1, crd2));
                            move.moveMarble(crd1, drc, marble1);
                            move.moveMarble(neighbor.getSharedNeighbor(crd1, crd2), drc, marble3);
                            move.moveMarble(crd2, drc, marble2);
                        } else {
                            Marble marble1 = board.getField(crd1);
                            Marble marble2 = board.getField(crd2);
                            Marble marble3 = board.getField(neighbor.getSharedNeighbor(crd1, crd2));
                            move.moveMarble(crd2, drc, marble2);
                            move.moveMarble(neighbor.getSharedNeighbor(crd1, crd2), drc, marble3);
                            move.moveMarble(crd1, drc, marble1);
                        }
                        points = points + 1;
                        return true;
                    } else if (move.isThreeToTwoPush(crd1, crd2, drc)) {
                        if (move.getFrontMarble(crd1, crd2, drc).equals(crd1)) {
                            String crd4 = neighbor.getNeighbor(crd1, drc);
                            Marble marble1 = board.getField(crd1);
                            Marble marble2 = board.getField(crd2);
                            Marble marble3 = board.getField(neighbor.getSharedNeighbor(crd1, crd2));
                            Marble opp = board.getField(crd4);
                            move.moveMarble(crd4, drc, opp);
                            move.moveMarble(crd1, drc, marble1);
                            move.moveMarble(neighbor.getSharedNeighbor(crd1, crd2), drc, marble3);
                            move.moveMarble(crd2, drc, marble2);
                        } else {
                            String crd4 = neighbor.getNeighbor(crd2, drc);
                            Marble marble1 = board.getField(crd1);
                            Marble marble2 = board.getField(crd2);
                            Marble marble3 = board.getField(neighbor.getSharedNeighbor(crd1, crd2));
                            Marble opp = board.getField(crd4);
                            move.moveMarble(crd4, drc, opp);
                            move.moveMarble(crd2, drc, marble2);
                            move.moveMarble(neighbor.getSharedNeighbor(crd1, crd2), drc, marble3);
                            move.moveMarble(crd1, drc, marble1);
                        }
                        points = points + 1;
                        return true;
                    }
                }
            } else if (move.isSumito(crd1, crd2, drc)) {
                if (move.howManyMarbles(crd1, crd2) == 2) {
                    if (move.getFrontMarble(crd1, crd2, drc).equals(crd1)) {
                        String crd3 = neighbor.getNeighbor(crd1, drc);
                        Marble opp = board.getField(crd3);
                        Marble marble1 = board.getField(crd1);
                        Marble marble2 = board.getField(crd2);
                        move.moveMarble(crd3, drc, opp);
                        move.moveMarble(crd1, drc, marble1);
                        move.moveMarble(crd2, drc, marble2);
                        return true;
                    } else {
                        String crd3 = neighbor.getNeighbor(crd2, drc);
                        Marble opp = board.getField(crd3);
                        Marble marble1 = board.getField(crd1);
                        Marble marble2 = board.getField(crd2);
                        move.moveMarble(crd3, drc, opp);
                        move.moveMarble(crd2, drc, marble2);
                        move.moveMarble(crd1, drc, marble1);
                        return true;
                    }
                } else if (move.howManyMarbles(crd1, crd2) == 3) {
                    if (move.isThreeToOne(crd1, crd2, drc)) {
                        if (move.getFrontMarble(crd1, crd2, drc).equals(crd1)) {
                            String crd3 = neighbor.getNeighbor(crd1, drc);
                            Marble opp = board.getField(crd3);
                            Marble marble1 = board.getField(crd1);
                            Marble marble2 = board.getField(crd2);
                            Marble marble3 = board.getField(neighbor.getSharedNeighbor(crd1, crd2));
                            move.moveMarble(crd3, drc, opp);
                            move.moveMarble(crd1, drc, marble1);
                            move.moveMarble(neighbor.getSharedNeighbor(crd1, crd2), drc, marble3);
                            move.moveMarble(crd2, drc, marble2);
                            return true;
                        } else {
                            String crd3 = neighbor.getNeighbor(crd2, drc);
                            Marble opp = board.getField(crd3);
                            Marble marble1 = board.getField(crd1);
                            Marble marble2 = board.getField(crd2);
                            Marble marble3 = board.getField(neighbor.getSharedNeighbor(crd1, crd2));
                            move.moveMarble(crd3, drc, opp);
                            move.moveMarble(crd2, drc, marble2);
                            move.moveMarble(neighbor.getSharedNeighbor(crd1, crd2), drc, marble3);
                            move.moveMarble(crd1, drc, marble1);
                            return true;
                        }
                    } else if (move.isThreeToTwo(crd1, crd2, drc)) {
                        if (move.getFrontMarble(crd1, crd2, drc).equals(crd1)) {
                            String crd3 = neighbor.getNeighbor(crd1, drc);
                            String crd4 = neighbor.getNeighbor(crd3, drc);
                            Marble opp = board.getField(crd4);
                            Marble opp1 = board.getField(crd3);
                            Marble marble1 = board.getField(crd1);
                            Marble marble2 = board.getField(crd2);
                            Marble marble3 = board.getField(neighbor.getSharedNeighbor(crd1, crd2));
                            move.moveMarble(crd4, drc, opp);
                            move.moveMarble(crd3, drc, opp1);
                            move.moveMarble(crd1, drc, marble1);
                            move.moveMarble(neighbor.getSharedNeighbor(crd1, crd2), drc, marble3);
                            move.moveMarble(crd2, drc, marble2);
                            return true;
                        } else {
                            String crd3 = neighbor.getNeighbor(crd2, drc);
                            String crd4 = neighbor.getNeighbor(crd3, drc);
                            Marble opp = board.getField(crd4);
                            Marble opp1 = board.getField(crd3);
                            Marble marble1 = board.getField(crd1);
                            Marble marble2 = board.getField(crd2);
                            Marble marble3 = board.getField(neighbor.getSharedNeighbor(crd1, crd2));
                            move.moveMarble(crd4, drc, opp);
                            move.moveMarble(crd3, drc, opp1);
                            move.moveMarble(crd2, drc, marble2);
                            move.moveMarble(neighbor.getSharedNeighbor(crd1, crd2), drc, marble3);
                            move.moveMarble(crd1, drc, marble1);
                            return true;
                        }
                    }
                }
            } else {
                if (move.howManyMarbles(crd1, crd2) == 1) {
                    move.moveMarble(crd1, drc, this.getMarble());
                    return true;
                } else if (move.howManyMarbles(crd1, crd2) == 2) {
                    if (move.isLineMove(crd1, crd2, drc)) {
                        if (move.getFrontMarble(crd1, crd2, drc).equals(crd1)) {
                            move.moveMarble(crd1, drc, this.getMarble());
                            move.moveMarble(crd2, drc, this.getMarble());
                            return true;
                        } else {
                            move.moveMarble(crd2, drc, this.getMarble());
                            move.moveMarble(crd1, drc, this.getMarble());
                            return true;
                        }
                    } else {
                        move.moveMarble(crd1, drc, this.getMarble());
                        move.moveMarble(crd2, drc, this.getMarble());
                        return true;
                    }
                } else if (move.howManyMarbles(crd1, crd2) == 3) {
                    if (move.isLineMove(crd1, crd2, drc)) {
                        if (move.getFrontMarble(crd1, crd2, drc).equals(crd1)) {
                            move.moveMarble(crd1, drc, this.getMarble());
                            move.moveMarble(neighbor.getSharedNeighbor(crd1, crd2), drc, this.getMarble());
                            move.moveMarble(crd2, drc, this.getMarble());
                            return true;
                        } else {
                            move.moveMarble(crd2, drc, this.getMarble());
                            move.moveMarble(neighbor.getSharedNeighbor(crd1, crd2), drc, this.getMarble());
                            move.moveMarble(crd1, drc, this.getMarble());
                            return true;
                        }
                    } else {
                        move.moveMarble(crd1, drc, this.getMarble());
                        move.moveMarble(crd2, drc, this.getMarble());
                        move.moveMarble(neighbor.getSharedNeighbor(crd1, crd2), drc, this.getMarble());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public abstract String determineMove(Board board, Player player, String move);

}