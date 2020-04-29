package abalone;

public class Move {
    Board board;
    Player player;
    String firstmarble;
    String secondmarble;
    String direction;
    Neighbor neighbor;
    Edge edge;

    /**
     * The constructor of Move.
     * 
     * @requires board != null && player != null
     * @param board Board
     * @param player Player
     */
    public Move(Board board, Player player) {
        this.board = board;
        this.player = player;
        this.neighbor = new Neighbor();
        this.edge = new Edge();
    }

    // ------METHODS-------------------

    /**
     * This method checks if a given marble is the marble of an opponent.
     * 
     * @require marble != null
     * @param marble Marble
     * @return true if the given marble is the opponent's marble
     */
    public boolean checkIfOpponentMarble(Marble marble) {
        if (player.getPlayers() == 4) {
            Marble pm = player.getMarble();
            if (pm == Marble.B) {
                if (marble == Marble.R || marble == Marble.Y) {
                    return true;
                }
            } else if (pm == Marble.R) {
                if (marble == Marble.B || marble == Marble.W) {
                    return true;
                }
            } else if (pm == Marble.W) {
                if (marble == Marble.R || marble == Marble.Y) {
                    return true;
                }
            } else if (pm == Marble.Y) {
                if (marble == Marble.B || marble == Marble.W) {
                    return true;
                }
            }
        } else {
            if (marble != player.getMarble() && marble != Marble.o) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if a marble is in the same team as the player.
     * 
     * @requires marble != null
     * @param marble Marble
     * @return true if the marble is in the same team
     */
    public boolean checkIfTeammember(Marble marble) {
        if (player.getPlayers() == 4) {
            if (player.getMarble() == Marble.B || player.getMarble() == Marble.W) {
                if (marble == Marble.B || marble == Marble.W) {
                    return true;
                }
            } else if (player.getMarble() == Marble.R || player.getMarble() == Marble.Y) {
                if (marble == Marble.R || marble == Marble.Y) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether a given move is a line move, meaning that the marbles will
     * move in a line.
     * 
     * @requires crd1 != null && crd2 != null && direction != null
     * @param crd1 String
     * @param crd2 String
     * @param direction String
     * @return true if the given move is a line move
     */
    public boolean isLineMove(String crd1, String crd2, String direction) {
        if (this.howManyMarbles(crd1, crd2) == 1) {
            return false;
        } else if (this.howManyMarbles(crd1, crd2) == 2) {
            if (neighbor.getDirection(crd1, crd2).equals(direction)
                    || neighbor.getDirection(crd2, crd1).equals(direction)) {
                return true;
            }
        } else if (this.howManyMarbles(crd1, crd2) == 3) {
            String marble1 = crd1;
            String marble2 = neighbor.getSharedNeighbor(crd1, crd2);
            if (neighbor.getDirection(marble1, marble2).equals(direction)
                    || neighbor.getDirection(marble2, marble1).equals(direction)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks whether the given move is a Sumito, without pushing a
     * marble off the edge.
     * 
     * @requires crd1 != null && crd2 != null && direction != null
     * @param crd1 String
     * @param crd2 String
     * @param direction String
     * @return true if it is a sumito
     */
    public boolean isSumito(String crd1, String crd2, String direction) {
        if (this.isLineMove(crd1, crd2, direction)) {
            if (this.howManyMarbles(crd1, crd2) == 2) {
                if (this.isTwoToOne(crd1, crd2, direction)) {
                    return true;
                }
            } else if (this.howManyMarbles(crd1, crd2) == 3) {
                if (this.isThreeToOne(crd1, crd2, direction)) {
                    return true;
                } else if (this.isThreeToTwo(crd1, crd2, direction)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method checks whether a move is a push-off, meaning checking if the
     * player will push a opponent's marble off the board.
     * 
     * @requires crd1 != null && crd2 != null && direction != null
     * @param crd1 String
     * @param crd2 String
     * @param direction String
     * @return true if the move is a push-off
     */
    public boolean isPushOff(String crd1, String crd2, String direction) {
        if (this.isLineMove(crd1, crd2, direction)) {
            if (this.howManyMarbles(crd1, crd2) == 2) {
                if (this.isTwoToOnePush(crd1, crd2, direction)) {
                    return true;
                }
            } else if (this.howManyMarbles(crd1, crd2) == 3) {
                if (this.isThreeToOnePush(crd1, crd2, direction)) {
                    return true;
                } else if (this.isThreeToTwoPush(crd1, crd2, direction)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method gets the coordinate of the front marble of the line of marbles
     * given, with the given direction.
     * 
     * @requires crd1 != null && crd2 != null && direction != null
     * @param crd1 String
     * @param crd2 String
     * @param direction String
     * @return the coordinates of the front marble
     */
    public String getFrontMarble(String crd1, String crd2, String direction) {
        if (this.howManyMarbles(crd1, crd2) == 2) {
            if (neighbor.getNeighbor(crd1, direction).equals(crd2)) {
                return crd2;
            } else if (neighbor.getNeighbor(crd2, direction).equals(crd1)) {
                return crd1;
            }
        } else if (this.howManyMarbles(crd1, crd2) == 3) {
            String m1 = crd1;
            String m2 = neighbor.getSharedNeighbor(crd1, crd2);
            String m3 = crd2;
            if (neighbor.getNeighbor(m2, direction).equals(m3)) {
                return m3;
            } else if (neighbor.getNeighbor(m2, direction).equals(m1)) {
                return m1;
            }
        }
        return null;
    }

    /**
     * This method gets the coordinate of the back marble of the line of marbles
     * given, with the given direction.
     * 
     * @requires crd1 != null && crd2 != null && direction != null
     * @param crd1 String
     * @param crd2 String
     * @param direction String
     * @return the coordinate of the back marble
     */
    public String getBackMarble(String crd1, String crd2, String direction) {
        if (this.howManyMarbles(crd1, crd2) == 2) {
            if (neighbor.getNeighbor(crd1, direction).equals(crd2)) {
                return crd1;
            } else if (neighbor.getNeighbor(crd2, direction).equals(crd1)) {
                return crd2;
            }
        } else if (this.howManyMarbles(crd1, crd2) == 3) {
            String m1 = crd1;
            String m2 = neighbor.getSharedNeighbor(crd1, crd2);
            String m3 = crd2;
            if (neighbor.getNeighbor(m2, direction).equals(m3)) {
                return m1;
            } else if (neighbor.getNeighbor(m2, direction).equals(m1)) {
                return m3;
            }
        }
        return null;
    }

    /**
     * This method checks how many marbles are in the given move.
     * 
     * @requires crd1 != null && crd2 != null
     * @param crd1 String
     * @param crd2 String
     * @return the amount of marbles in the move
     */
    public int howManyMarbles(String crd1, String crd2) {
        if (board.hasMarble(crd1) && board.hasMarble(crd2)) {
            if (crd1.equals(crd2)) {
                return 1;
            } else if (neighbor.checkIfNeighbor(crd1, crd2)) {
                return 2;
            } else if (neighbor.checkIfSharedNeighbor(crd1, crd2)) {
                return 3;
            }
        }
        return 0;
    }

    /**
     * This method checks whether a given move is a push-off, with three of the
     * current player's marbles, and two of the opponent player's marbles.
     * 
     * @requires crd1 != null && crd2 != null && direction != null
     * @param crd1 String
     * @param crd2 String
     * @param direction String
     * @return true if it is a three to two push-off
     */
    public boolean isThreeToTwoPush(String crd1, String crd2, String direction) {
        String after = neighbor.getNeighbor(this.getFrontMarble(crd1, crd2, direction), direction);
        if (this.checkIfOpponentMarble(board.getField(after))) {
            String afterafter = neighbor.getNeighbor(after, direction);
            if (this.checkIfOpponentMarble(board.getField(afterafter))) {
                if (edge.isEdgeField(afterafter, direction)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method checks whether a given move is a Sumito without pushing a marble
     * off the board, with three of the current player's marbles, and two of the
     * opponent player's marbles.
     * 
     * @requires crd1 != null && crd2 != null && direction != null
     * @param crd1 String
     * @param crd2 String
     * @param direction String
     * @return true if the move is a three to two sumito
     */
    public boolean isThreeToTwo(String crd1, String crd2, String direction) {
        String after = neighbor.getNeighbor(this.getFrontMarble(crd1, crd2, direction), direction);
        if (this.checkIfOpponentMarble(board.getField(after))) {
            String afterafter = neighbor.getNeighbor(after, direction);
            if (this.checkIfOpponentMarble(board.getField(afterafter))) {
                String afterafterafter = neighbor.getNeighbor(afterafter, direction);
                if (board.getField(afterafterafter) == Marble.o) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method checks whether a given move is a push-off, with three of the
     * current player's marbles, and one of the opponent player's marbles.
     * 
     * @requires crd1 != null && crd2 != null && direction != null
     * @param crd1 String
     * @param crd2 String
     * @param direction String
     * @return true if the move is a three to one push-off
     */
    public boolean isThreeToOnePush(String crd1, String crd2, String direction) {
        String after = neighbor.getNeighbor(this.getFrontMarble(crd1, crd2, direction), direction);
        if (this.checkIfOpponentMarble(board.getField(after))) {
            if (edge.isEdgeField(after, direction)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks whether a given move is a Sumito without pushing a marble
     * off the board, with three of the current player's marbles, and one of the
     * opponent player's marbles.
     * 
     * @requires crd1 != null && crd2 != null && direction != null
     * @param crd1 String
     * @param crd2 String
     * @param direction String
     * @return true if the move is a three to one sumito
     */
    public boolean isThreeToOne(String crd1, String crd2, String direction) {
        String after = neighbor.getNeighbor(this.getFrontMarble(crd1, crd2, direction), direction);
        if (this.checkIfOpponentMarble(board.getField(after))) {
            String afterafter = neighbor.getNeighbor(after, direction);
            if (board.getField(afterafter) == Marble.o) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks whether a given move is a push-off, with two of the
     * current player's marbles, and one of the opponent player's marbles.
     * 
     * @requires crd1 != null && crd2 != null && direction != null
     * @param crd1 String
     * @param crd2 String
     * @param direction String
     * @return true if the move is a two to one push-of
     */
    public boolean isTwoToOnePush(String crd1, String crd2, String direction) {
        String next = neighbor.getNeighbor(this.getFrontMarble(crd1, crd2, direction), direction);
        if (this.checkIfOpponentMarble(board.getField(next))) {
            if (edge.isEdgeField(next, direction)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks whether a given move is a Sumito without pushing a marble
     * off the board, with two of the current player's marbles, and one of the
     * opponent player's marbles.
     * 
     * @requires crd1 != null && crd2 != null && direction != null
     * @param crd1 String
     * @param crd2 String
     * @param direction String
     * @return true if the move is a two to one sumito
     */
    public boolean isTwoToOne(String crd1, String crd2, String direction) {
        String next = neighbor.getNeighbor(this.getFrontMarble(crd1, crd2, direction), direction);
        if (this.checkIfOpponentMarble(board.getField(next))) {
            String nextnext = neighbor.getNeighbor(next, direction);
            if (board.getField(nextnext) == Marble.o) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method moves a marble, given a the position of the marble, the direction
     * and which marble.
     *
     * @requires crd != null && direction != null && marble != null
     * @param crd String
     * @param direction String
     * @param marble Marble
     */
    public void moveMarble(String crd, String direction, Marble marble) {
        if (direction.equals("UL")) {
            moveUL(crd, marble);
        } else if (direction.equals("UR")) {
            moveUR(crd, marble);
        } else if (direction.equals("R")) {
            moveR(crd, marble);
        } else if (direction.equals("L")) {
            moveL(crd, marble);
        } else if (direction.equals("DL")) {
            moveDL(crd, marble);
        } else if (direction.equals("DR")) {
            moveDR(crd, marble);
        }
    }

    /**
     * This method moves a marble to the space Up-Left.
     * 
     * @requires coordinate != null && marble != null
     * @param coordinate String
     * @param marble Marble
     */
    public void moveUL(String coordinate, Marble marble) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        String newcrd = neighbor.getUL(coordinate);
        int newfirst = board.getFirst(newcrd);
        int newsecond = board.getSecond(newcrd);
        board.setField(newfirst, newsecond, marble);
        board.setField(first, second, Marble.o);
    }

    /**
     * This method moves a marble to the space Up-Right.
     * 
     * @requires coordinate != null && marble != null
     * @param coordinate String
     * @param marble Marble
     */
    public void moveUR(String coordinate, Marble marble) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        String newcrd = neighbor.getUR(coordinate);
        int newfirst = board.getFirst(newcrd);
        int newsecond = board.getSecond(newcrd);
        board.setField(newfirst, newsecond, marble);
        board.setField(first, second, Marble.o);
    }

    /**
     * This method moves a marble to the space Left.
     * 
     * @requires coordinate != null && marble != null
     * @param coordinate String
     * @param marble Marble
     */
    public void moveL(String coordinate, Marble marble) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        String newcrd = neighbor.getL(coordinate);
        int newfirst = board.getFirst(newcrd);
        int newsecond = board.getSecond(newcrd);
        board.setField(newfirst, newsecond, marble);
        board.setField(first, second, Marble.o);
    }

    /**
     * This method moves a marble to the space Right.
     * 
     * @requires coordinate != null && marble != null
     * @param coordinate String
     * @param marble Marble
     */
    public void moveR(String coordinate, Marble marble) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        String newcrd = neighbor.getR(coordinate);
        int newfirst = board.getFirst(newcrd);
        int newsecond = board.getSecond(newcrd);
        board.setField(newfirst, newsecond, marble);
        board.setField(first, second, Marble.o);
    }

    /**
     * This method moves a marble to the space Down-Left.
     * 
     * @requires coordinate != null && marble != null
     * @param coordinate String
     * @param marble Marble
     */
    public void moveDL(String coordinate, Marble marble) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        String newcrd = neighbor.getDL(coordinate);
        int newfirst = board.getFirst(newcrd);
        int newsecond = board.getSecond(newcrd);
        board.setField(newfirst, newsecond, marble);
        board.setField(first, second, Marble.o);
    }

    /**
     * This method moves a marble to the space Down-Right.
     * 
     * @requires coordinate != null && marble != null
     * @param coordinate String
     * @param marble Marble
     */
    public void moveDR(String coordinate, Marble marble) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        String newcrd = neighbor.getDR(coordinate);
        int newfirst = board.getFirst(newcrd);
        int newsecond = board.getSecond(newcrd);
        board.setField(newfirst, newsecond, marble);
        board.setField(first, second, Marble.o);
    }

}