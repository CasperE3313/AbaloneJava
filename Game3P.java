package abalone;

import java.net.Socket;
import java.util.ArrayList;

public class Game3P extends Game {

    public static final int NUMBER_PLAYERS = 3;
    private Board board;
    private Player[] players;
    private int current;
    private int moves;
    private ArrayList<Socket> sockets;
    private Strategy strategy;
    private Neighbor neighbor;

    // --Constructor-------------------

    /**
     * The constructor for Game3P.
     * 
     * @requires s0 != null && s1 != null && s2 != null
     * @invariant s0, s1 and s2 are never null
     * @param s0 Player
     * @param s1 Player
     * @param s2 Player
     */
    public Game3P(Player s0, Player s1, Player s2) {
        board = new Board();
        players = new Player[NUMBER_PLAYERS];
        players[0] = s0;
        players[1] = s1;
        players[2] = s2;
        current = 0;
        moves = 0;
        this.sockets = new ArrayList<>();
        strategy = new SmartStrategy();
        neighbor = new Neighbor();
    }

    // --Methods-----------------------

    public Game3P getGame() {
        return this;
    }

    /**
     * This method starts the game, by resetting the board and calling the update
     * method.
     */
    @Override
    public void start() {
        reset();
        update();
    }

    /**
     * This method resets the game, by putting values at 0 again, and initializing
     * the board again.
     */
    @Override
    public void reset() {
        current = 0;
        players[0].setPoints(0);
        players[1].setPoints(0);
        players[2].setPoints(0);
        moves = 0;
        board.initialiseBoard3P();
    }

    /**
     * Print the current game situation.
     */
    @Override
    public void update() {
        System.out.println("\n current game situation: \n\n");
        board.boardToString();
        System.out.println("\n");
        System.out.println("Current points:");
        System.out.println("Player " + players[0].getName() + " : " + players[0].getPoints() + " points");
        System.out.println("Player " + players[1].getName() + " : " + players[1].getPoints() + " points");
        System.out.println("Player " + players[2].getName() + " : " + players[2].getPoints() + " points");
        System.out.println("\n");
        System.out.println(this.getTurn());
    }

    /**
     * This method makes a move on the board for the current player, but first
     * checks if the move is valid.
     * 
     * @requires first != null, second != null, destination != null
     * @requires first and second to be a string character from a - i followed by
     *           ";" followed by an integer from 1-9.
     * @requires destination to be one of the following: UL,UR,DL,DR,L,R
     * @param first       String
     * @param second      String
     * @param destination String
     * @ensures true is returned when the move was successful, false is returned
     *          when the move as not valid and thus not successful
     * @returns true if the move was valid and successful
     */
    @Override
    public boolean move(String first, String second, String destination) {
        Player currentPlayer = this.players[current];
        int noncurrent = (current + 1) % NUMBER_PLAYERS;
        Player opponent = this.players[noncurrent];
        String move = first + "," + second + "," + destination;
        if (currentPlayer.makeMove(board, opponent, move)) {
            current = (current + 1) % NUMBER_PLAYERS;
            this.update();
            moves++;
            return true;
        }
        return false;
    }

    /**
     * This method gets a string with a hint for the current player.
     * 
     * @ensures a hint in the form of a move command is returned
     * @return hint as string
     */
    @Override
    public String hint() {
        String hint = strategy.determineMove(this.getBoard(), this.players[current],
                this.players[(current + 1) % NUMBER_PLAYERS]);
        return hint;
    }

    /**
     * Returns a string with a hint conform to protocol.
     * 
     * @ensures a hint in the form of a move command, conform to protocol, is
     *          returned
     * @return hint string
     */
    @Override
    public String hintServer() {
        String hint = this.hint();
        String[] ht = hint.split(",");
        String first = ht[0];
        String[] ft = first.split(";");
        String f = ft[0] + ft[1];
        String back;
        back = "";
        back = back + f;
        String second = ht[1];
        String[] sd = second.split(";");
        String s = sd[0] + sd[1];
        back = back + ";" + s;
        String dir = ht[2];
        String third = neighbor.getNeighbor(first, dir);
        String[] td = third.split(";");
        String t = td[0] + td[1];
        back = back + ";" + t;
        return back;
    }

    /**
     * This method converts input from the server to input this class can use.
     * 
     * @requires input != null and when split, in[] should have length 7
     * @param input String
     * @ensures a converted string is returned
     * @return String of converted input
     */
    @Override
    public String convertInput(String input) {
        String[] in = input.split(";");
        String crd1 = in[1] + ";" + in[2];
        String crd2 = in[3] + ";" + in[4];
        String crdd = in[5] + ";" + in[6];
        String dir = neighbor.getDirection(crd1, crdd);
        String out = crd1 + "," + crd2 + "," + dir;
        return out;
    }

    /**
     * This method gets a string with the current points per player in the game.
     * 
     * @ensures the current score is returned in string form
     * @return string of current score
     */
    @Override
    public String getScore() {
        String zero = "Current points:";
        String first = "Player " + players[0].getName() + " : " + players[0].getPoints() + " points";
        String second = "Player " + players[1].getName() + " : " + players[1].getPoints() + " points";
        String third = "Player " + players[2].getName() + " : " + players[2].getPoints() + " points";
        String total = "\n" + zero + "\n" + first + "\n" + second + "\n" + third + "\n";
        return total;
    }

    /**
     * This method checks whether it is GameOver, by checking if the board has a
     * winner, or if the max moves is surpassed.
     * 
     * @ensures either true when game is over or false when game is not over
     * @return true if it is game over
     */
    @Override
    public boolean checkIfGameOver() {
        if (board.hasWinner(players)) {
            return true;
        } else if (moves == 96) {
            return true;
        }
        return false;
    }

    /**
     * This method gets the result when the game has ended as a string.
     * 
     * @ensures the String with the print of the result of the game is returned
     * @return string of the result
     */
    @Override
    public String printResult() {
        Player winner;
        String result = null;
        if (board.hasWinner(players)) {
            if (board.isWinner(players[0])) {
                winner = players[0];
            } else if (board.isWinner(players[1])) {
                winner = players[1];
            } else {
                winner = players[2];
            }
            result = "Player " + winner.getName() + " (" + winner.getMarble().toString() + ") has won!";
        } else if (moves == 96) {
            result = "Draw. There is no winner!";
        }
        return result;
    }

    /**
     * Returns the string state of the outcome conform to protocol.
     * 
     * @ensures the String with the print of the result of the game, conform to
     *          protocol, is returned
     * @return string of the result according to protocol
     */
    @Override
    public String printResultProtocol() {

        Player winner;
        String result = null;
        if (board.hasWinner(players)) {
            if (board.isWinner(players[0])) {
                winner = players[0];
            } else if (board.isWinner(players[1])) {
                winner = players[1];
            } else {
                winner = players[2];
            }
            result = "g;game won;" + winner.getMarble().getName(winner.getMarble());
        } else if (moves == 96) {
            result = "g;draw";
        }
        return result;

    }

    /**
     * Method which prints out the game result. We access this method from the
     * server to let the server also print out the fact that the game has ended.
     */
    @Override
    public void printOutResult() {
        System.out.println(printResult());
    }

    /**
     * This method gives a String which says which player's turn it is, and which
     * marble that player has.
     * 
     * @ensures a string with the correct turn to be returned
     * @return turn as a string
     */
    @Override
    public String getTurn() {
        String name = this.getCurrentPlayer().getName();
        String marble = this.getCurrentPlayer().getMarble().toString();
        String turn = "It's " + name + "'s turn, with Marble " + marble;
        return turn;
    }

    /**
     * This method gives back the turn in a string form.
     * 
     * @ensures a string with the correct turn conform to protocol to be returned
     * @return turn indicating which marble
     */
    @Override
    public String getTurnServer() {
        String marble = this.getCurrentPlayer().getMarble().getName(this.getCurrentPlayer().getMarble());
        String turn = "t;" + marble;
        return turn;
    }

    /**
     * This method gets the current as an integer.
     * 
     * @ensures an integer of either 0, 1 or 2 to be returned
     * @return current
     */
    @Override
    public int getCurrent() {
        return current;
    }

    /**
     * This method gets the current player.
     * 
     * @ensures the current player to be returned
     * @return current player
     */
    @Override
    public Player getCurrentPlayer() {
        return this.players[current];
    }

    /**
     * This method gives back the opponent player of the current player.
     * 
     * @ensures the player after the currentplayer to be returned
     * @return opponent player
     */
    @Override
    public Player getOpponentPlayer() {
        return this.players[(current + 1) % NUMBER_PLAYERS];
    }

    /**
     * This method gets the string version of the marble from a player with index i
     * on the array players.
     * 
     * @requires i either 0, 1 or 2
     * @param i integer
     * @ensures the marble of the specified player to be returned
     * @return marble of the player
     */
    @Override
    public String getMarblePlayer(int i) {
        if (i + 1 <= NUMBER_PLAYERS) {
            Player playerm = players[i];
            String marble = playerm.getMarble().getName(playerm.getMarble());
            return marble;
        }
        return null;
    }

    /**
     * This method gets the board of the game.
     * 
     * @ensures the current board to be returned
     * @return board
     */
    @Override
    public Board getBoard() {
        return board;
    }

    /**
     * This method adds sockets to an ArrayList.
     * 
     * @requires s != null
     * @param s Socket
     */
    @Override
    public void addSockets(Socket s) {
        sockets.add(s);

    }

    /**
     * This method gets the ArrayList of sockets.
     * 
     * @ensures the socket of the current player to be returned
     * @return ArrayList sockets where all the sockets in this game are stored
     */
    @Override
    public ArrayList<Socket> getSockets() {
        return sockets;
    }

    /**
     * This method gets the socket of the current player.
     * 
     * @ensures an ArrayList of all sockets in the game to be returned
     * @return socket of the current player
     */
    @Override
    public Socket getSocket() {
        return sockets.get(getCurrent());
    }

}
