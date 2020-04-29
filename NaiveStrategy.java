package abalone;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class NaiveStrategy implements Strategy {

    private static final String name = "Naive";
    private Board board = new Board();
    private Move move;
    private Edge ed = new Edge();
    private Neighbor ne = new Neighbor();

    /**
     * This method gets the name of the strategy.
     * 
     * @return name of strategy
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * This method determines a move for the ComputerPlayer.
     * 
     * @requires board != null && player != null && opponent != null
     * @param board Board
     * @param player Player
     * @param opponent Player
     * @return a valid move
     */
    @Override
    public String determineMove(Board board, Player player, Player opponent) {
        this.board = board;
        this.move = new Move(board, player);
        if (this.checkAtRisk(1, opponent)) {
            String field = this.getAtRiskField(opponent);
            String drc = this.atRiskDirectionOne(field, board);
            String opp = ne.getNeighbor(field, drc);
            String opp2 = ne.getNeighbor(opp, drc);
            if (board.getField(opp) == player.getMarble() && board.getField(opp2) == player.getMarble()) {
                if (board.getField(ne.getNeighbor(opp2, drc)) == player.getMarble()) {
                    String opp3 = ne.getNeighbor(opp2, drc);
                    return opp + "," + opp3 + "," + ne.getOppositeDirection(drc);
                } else {
                    return opp + "," + opp2 + "," + ne.getOppositeDirection(drc);
                }
            }
        }
        if (this.checkAtRisk(2, opponent)) {
            String field = this.getAtRiskField(opponent);
            String drc = this.atRiskDirectionOne(field, board);
            String opp = ne.getNeighbor(field, drc);
            String opp2 = ne.getNeighbor(opp, drc);
            String opp3 = ne.getNeighbor(opp2, drc);
            if (board.getField(opp) == player.getMarble() && board.getField(opp2) == player.getMarble()
                    && board.getField(opp3) == player.getMarble()) {
                return opp + "," + opp3 + "," + ne.getOppositeDirection(drc);
            }
        }
        if (!this.getRandomMove(player).equals("default")) {
            return this.getRandomMove(player);
        }
        return null;
    }

    /**
     * This method gets a random move.
     * 
     * @param player Player
     * @return string of random move
     */
    public String getRandomMove(Player player) {
        String[] crds = board.getCoordinates();
        List<String> crdslist = Arrays.asList(crds);
        Collections.shuffle(crdslist);
        String[] drcs = ne.getDrc();
        List<String> drcslist = Arrays.asList(drcs);
        Collections.shuffle(drcslist);
        for (String crd : crdslist) {
            if (board.getField(crd) == player.getMarble()) {
                for (String drc : drcslist) {
                    if (!ed.isEdgeField(crd, drc)) {
                        if (board.isEmptyField(ne.getNeighbor(crd, drc))) {
                            return crd + "," + crd + "," + drc;
                        }
                    }
                }
            }
        }
        return "default";
    }

    /**
     * This method gets a field that is at risk for a push-off, if a player is at
     * risk.
     * 
     * @requires player != null
     * @param player Player
     * @return a field at risk, or null if there are no fields at risk
     */
    public String getAtRiskField(Player player) {
        for (String edge : ed.edges) {
            if (board.getField(edge) == player.getMarble()) {
                if (this.atRiskOne(edge, board)) {
                    return edge;
                }
                if (this.atRiskTwo(edge, player, board)) {
                    return edge;
                }
            }
        }
        return null;
    }

    /**
     * This method gets the direction at which the given field is at risk of
     * getting pushed off.
     * 
     * @requires player != null & crd != null
     * @param player Player
     * @param crd String
     * @return a string with a direction, or null if no at risk direction is found
     */
    public String getAtRiskDirection(Player player, String crd) {
        if (ed.isEdgeField(crd)) {
            if (this.atRiskOne(crd, board)) {
                return this.atRiskDirectionOne(crd, board);
            } else if (this.atRiskTwo(crd, player, board)) {
                return this.atRiskDirectionTwo(crd, player, board);
            }
        }
        return null;
    }

    /**
     * This method checks whether a player is at risk of a push-off with a certain
     * amount of marbles.
     * 
     * @requires own != null && player != null
     * @requires own == 1 || own == 2
     * @param own integer
     * @param player Player
     * @return true if player is at risk of push off for a certain amount of marbles
     */
    public boolean checkAtRisk(int own, Player player) {
        for (String edge : ed.edges) {
            if (own == 1 && board.getField(edge) == player.getMarble()) {
                if (this.atRiskOne(edge, board)) {
                    return true;
                }
            } else if (own == 2 && board.getField(edge) == player.getMarble()) {
                if (this.atRiskTwo(edge, player, board)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method checks whether a player is at risk of getting pushed off with one
     * marble.
     * 
     * @requires edge != null && board != null
     * @param edge String
     * @param board Board
     * @return true if the player has a marble at risk of getting pushed off
     */
    public boolean atRiskOne(String edge, Board board) {
        if (ed.containsValue(edge, ed.edgeDL)) {
            String ur = ne.getUR(edge);
            String urur = ne.getUR(ur);
            Marble marble1 = board.getField(ur);
            Marble marble2 = board.getField(urur);
            if (move.checkIfOpponentMarble(marble1) && move.checkIfOpponentMarble(marble2)) {
                return true;
            }
        }
        if (ed.containsValue(edge, ed.edgeDR)) {
            String ul = ne.getUL(edge);
            String ulul = ne.getUL(ul);
            if (move.checkIfOpponentMarble(board.getField(ul)) && move.checkIfOpponentMarble(board.getField(ulul))) {
                return true;
            }
        }
        if (ed.containsValue(edge, ed.edgeL)) {
            String r = ne.getR(edge);
            String rr = ne.getR(r);
            if (move.checkIfOpponentMarble(board.getField(r)) && move.checkIfOpponentMarble(board.getField(rr))) {
                return true;
            }
        }
        if (ed.containsValue(edge, ed.edgeR)) {
            String l = ne.getL(edge);
            String ll = ne.getL(l);
            if (move.checkIfOpponentMarble(board.getField(l)) && move.checkIfOpponentMarble(board.getField(ll))) {
                return true;
            }
        }
        if (ed.containsValue(edge, ed.edgeUL)) {
            String dr = ne.getDR(edge);
            String drdr = ne.getDR(dr);
            if (move.checkIfOpponentMarble(board.getField(dr)) && move.checkIfOpponentMarble(board.getField(drdr))) {
                return true;
            }
        }
        if (ed.containsValue(edge, ed.edgeUR)) {
            String dl = ne.getDL(edge);
            String dldl = ne.getDL(dl);
            if (move.checkIfOpponentMarble(board.getField(dl)) && move.checkIfOpponentMarble(board.getField(dldl))) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method gets the direction at which a one push-off is at risk.
     * 
     * @requires edge != null && board != null
     * @param edge String
     * @param board Board
     * @return the direction at which the given coordinate 'edge' is at risk
     */
    public String atRiskDirectionOne(String edge, Board board) {
        if (ed.containsValue(edge, ed.edgeDL)) {
            String ur = ne.getUR(edge);
            String urur = ne.getUR(ur);
            if (move.checkIfOpponentMarble(board.getField(ur)) && move.checkIfOpponentMarble(board.getField(urur))) {
                return "UR";
            }
        }
        if (ed.containsValue(edge, ed.edgeDR)) {
            String ul = ne.getUL(edge);
            String ulul = ne.getUL(ul);
            if (move.checkIfOpponentMarble(board.getField(ul)) && move.checkIfOpponentMarble(board.getField(ulul))) {
                return "UL";
            }
        }
        if (ed.containsValue(edge, ed.edgeL)) {
            String r = ne.getR(edge);
            String rr = ne.getR(r);
            if (move.checkIfOpponentMarble(board.getField(r)) && move.checkIfOpponentMarble(board.getField(rr))) {
                return "R";
            }
        }
        if (ed.containsValue(edge, ed.edgeR)) {
            String l = ne.getL(edge);
            String ll = ne.getL(l);
            if (move.checkIfOpponentMarble(board.getField(l)) && move.checkIfOpponentMarble(board.getField(ll))) {
                return "L";
            }
        }
        if (ed.containsValue(edge, ed.edgeUL)) {
            String dr = ne.getDR(edge);
            String drdr = ne.getDR(dr);
            if (move.checkIfOpponentMarble(board.getField(dr)) && move.checkIfOpponentMarble(board.getField(drdr))) {
                return "DR";
            }
        }
        if (ed.containsValue(edge, ed.edgeUR)) {
            String dl = ne.getDL(edge);
            String dldl = ne.getDL(dl);
            if (move.checkIfOpponentMarble(board.getField(dl)) && move.checkIfOpponentMarble(board.getField(dldl))) {
                return "DL";
            }
        }
        return null;
    }

    /**
     * This method gets the direction at which a two marble push-off is at risk.
     * 
     * @requires edge != null && player != null && board != null
     * @param edge String
     * @param player Player
     * @param board Board
     * @return the direction at which the given coordinate 'edge' is at risk
     */
    public boolean atRiskTwo(String edge, Player player, Board board) {
        if (ed.containsValue(edge, ed.edgeDL)) {
            String ownur = ne.getUR(edge);
            String ur = ne.getUR(ownur);
            String urur = ne.getUR(ur);
            String ururur = ne.getUR(urur);
            if (board.getField(ownur) == player.getMarble() && move.checkIfOpponentMarble(board.getField(ur))
                    && move.checkIfOpponentMarble(board.getField(urur))
                    && move.checkIfOpponentMarble(board.getField(ururur))) {
                return true;
            }
        }
        if (ed.containsValue(edge, ed.edgeDR)) {
            String ownul = ne.getUL(edge);
            String ul = ne.getUL(ownul);
            String ulul = ne.getUL(ul);
            String ululul = ne.getUL(ulul);
            if (board.getField(ownul) == player.getMarble() && move.checkIfOpponentMarble(board.getField(ul))
                    && move.checkIfOpponentMarble(board.getField(ulul))
                    && move.checkIfOpponentMarble(board.getField(ululul))) {
                return true;
            }
        }
        if (ed.containsValue(edge, ed.edgeL)) {
            String ownr = ne.getR(edge);
            String r = ne.getR(ownr);
            String rr = ne.getR(r);
            String rrr = ne.getR(rr);
            if (board.getField(ownr) == player.getMarble() && move.checkIfOpponentMarble(board.getField(rr))
                    && move.checkIfOpponentMarble(board.getField(rr))
                    && move.checkIfOpponentMarble(board.getField(rrr))) {
                return true;
            }
        }
        if (ed.containsValue(edge, ed.edgeR)) {
            String ownl = ne.getL(edge);
            String l = ne.getL(edge);
            String ll = ne.getL(l);
            String lll = ne.getL(ll);
            if (board.getField(ownl) == player.getMarble() && move.checkIfOpponentMarble(board.getField(l))
                    && move.checkIfOpponentMarble(board.getField(ll))
                    && move.checkIfOpponentMarble(board.getField(lll))) {
                return true;
            }
        }
        if (ed.containsValue(edge, ed.edgeUL)) {
            String owndr = ne.getDR(edge);
            String dr = ne.getDR(owndr);
            String drdr = ne.getDR(dr);
            String drdrdr = ne.getDR(drdr);
            if (board.getField(owndr) == player.getMarble() && move.checkIfOpponentMarble(board.getField(dr))
                    && move.checkIfOpponentMarble(board.getField(drdr))
                    && move.checkIfOpponentMarble(board.getField(drdrdr))) {
                return true;
            }
        }
        if (ed.containsValue(edge, ed.edgeUR)) {
            String owndl = ne.getDL(edge);
            String dl = ne.getDL(owndl);
            String dldl = ne.getDL(dl);
            String dldldl = ne.getDL(dldl);
            if (board.getField(owndl) == player.getMarble() && move.checkIfOpponentMarble(board.getField(dl))
                    && move.checkIfOpponentMarble(board.getField(dldl))
                    && move.checkIfOpponentMarble(board.getField(dldldl))) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method gets the direction at which a two marble push-off is at risk.
     * 
     * @requires edge != null && player != null && board != null
     * @param edge String
     * @param player Player
     * @param board Board
     * @return the direction at which the given coordinate 'edge' is at risk
     */
    public String atRiskDirectionTwo(String edge, Player player, Board board) {
        if (ed.containsValue(edge, ed.edgeDL)) {
            String ownur = ne.getUR(edge);
            String ur = ne.getUR(ownur);
            String urur = ne.getUR(ur);
            String ururur = ne.getUR(urur);
            if (board.getField(ownur) == player.getMarble() && move.checkIfOpponentMarble(board.getField(ur))
                    && move.checkIfOpponentMarble(board.getField(urur))
                    && move.checkIfOpponentMarble(board.getField(ururur))) {
                return "UR";
            }
        }
        if (ed.containsValue(edge, ed.edgeDR)) {
            String ownul = ne.getUL(edge);
            String ul = ne.getUL(ownul);
            String ulul = ne.getUL(ul);
            String ululul = ne.getUL(ulul);
            if (board.getField(ownul) == player.getMarble() && move.checkIfOpponentMarble(board.getField(ul))
                    && move.checkIfOpponentMarble(board.getField(ulul))
                    && move.checkIfOpponentMarble(board.getField(ululul))) {
                return "UL";
            }
        }
        if (ed.containsValue(edge, ed.edgeL)) {
            String ownr = ne.getR(edge);
            String r = ne.getR(ownr);
            String rr = ne.getR(r);
            String rrr = ne.getR(rr);
            if (board.getField(ownr) == player.getMarble() && move.checkIfOpponentMarble(board.getField(rr))
                    && move.checkIfOpponentMarble(board.getField(rr))
                    && move.checkIfOpponentMarble(board.getField(rrr))) {
                return "R";
            }
        }
        if (ed.containsValue(edge, ed.edgeR)) {
            String ownl = ne.getL(edge);
            String l = ne.getL(edge);
            String ll = ne.getL(l);
            String lll = ne.getL(ll);
            if (board.getField(ownl) == player.getMarble() && move.checkIfOpponentMarble(board.getField(l))
                    && move.checkIfOpponentMarble(board.getField(ll))
                    && move.checkIfOpponentMarble(board.getField(lll))) {
                return "L";
            }
        }
        if (ed.containsValue(edge, ed.edgeUL)) {
            String owndr = ne.getDR(edge);
            String dr = ne.getDR(owndr);
            String drdr = ne.getDR(dr);
            String drdrdr = ne.getDR(drdr);
            if (board.getField(owndr) == player.getMarble() && move.checkIfOpponentMarble(board.getField(dr))
                    && move.checkIfOpponentMarble(board.getField(drdr))
                    && move.checkIfOpponentMarble(board.getField(drdrdr))) {
                return "DR";
            }
        }
        if (ed.containsValue(edge, ed.edgeUR)) {
            String owndl = ne.getDL(edge);
            String dl = ne.getDL(owndl);
            String dldl = ne.getDL(dl);
            String dldldl = ne.getDL(dldl);
            if (board.getField(owndl) == player.getMarble() && move.checkIfOpponentMarble(board.getField(dl))
                    && move.checkIfOpponentMarble(board.getField(dldl))
                    && move.checkIfOpponentMarble(board.getField(dldldl))) {
                return "DL";
            }
        }
        return null;
    }
}
