package abalone;

import java.util.Arrays;

public class Board {

    private static final int ROW = 9;
    private static final int COLUMN = 9;
    public static final String NUMBERING = "    i5 i6 i7 i8 i9" + "\n" + "   h4 h5 h6 h7 h8 h9" + "\n"
            + "  g3 g4 g5 g6 g7 g8 g9" + "\n" + " f2 f3 f4 f5 f6 f7 f8 f9" + "\n" + "e1 e2 e3 e4 e5 e6 e7 e8 e9" + "\n"
            + " d1 d2 d3 d4 d5 d6 d7 d8" + "\n" + "  c1 c2 c3 c4 c5 c6 c7" + "\n" + "   b1 b2 b3 b4 b5 b6" + "\n"
            + "    a1 a2 a3 a4 a5";
    public static final String[] BASICBOARD = { "     i5 i6 i7 i8 i9" + "\n", "   h4 h5 h6 h7 h8 h9" + "\n",
        "  g3 g4 g5 g6 g7 g8 g9" + "\n", " f2 f3 f4 f5 f6 f7 f8 f9" + "\n", "e1 e2 e3 e4 e5 e6 e7 e8 e9" + "\n",
        " d1 d2 d3 d4 d5 d6 d7 d8" + "\n", "  c1 c2 c3 c4 c5 c6 c7" + "\n", "   b1 b2 b3 b4 b5 b6" + "\n",
        "    a1 a2 a3 a4 a5" };
    public static final String[] coordinates = { "i;5", "i;6", "i;7", "i;8", "i;9", "h;4", "h;5", "h;6", "h;7", "h;8",
        "h;9", "g;3", "g;4", "g;5", "g;6", "g;7", "g;8", "g;9", "f;2", "f;3", "f;4", "f;5", "f;6", "f;7", "f;8",
        "f;9", "e;1", "e;2", "e;3", "e;4", "e;5", "e;6", "e;7", "e;8", "e;9", "d;1", "d;2", "d;3", "d;4", "d;5",
        "d;6", "d;7", "d:8", "c;1", "c;2", "c;3", "c;4", "c;5", "c;6", "c;7", "b;1", "b;2", "b;3", "b;4", "b;5",
        "b;6", "a;1", "a;2", "a;3", "a;4", "a;5" };
    public static final String basicarray1 = "     i5 i6 i7 i8 i9";
    public static final String basicarray2 = "   h4 h5 h6 h7 h8 h9";
    public static final String basicarray3 = "  g3 g4 g5 g6 g7 g8 g9";
    public static final String basicarray4 = " f2 f3 f4 f5 f6 f7 f8 f9";
    public static final String basicarray5 = "e1 e2 e3 e4 e5 e6 e7 e8 e9";
    public static final String basicarray6 = " d1 d2 d3 d4 d5 d6 d7 d8";
    public static final String basicarray7 = "  c1 c2 c3 c4 c5 c6 c7";
    public static final String basicarray8 = "   b1 b2 b3 b4 b5 b6";
    public static final String basicarray9 = "    a1 a2 a3 a4 a5";
    private Marble[] array1 = new Marble[5];
    private Marble[] array2 = new Marble[6];
    private Marble[] array3 = new Marble[7];
    private Marble[] array4 = new Marble[8];
    private Marble[] array5 = new Marble[9];
    private Marble[] array6 = new Marble[8];
    private Marble[] array7 = new Marble[7];
    private Marble[] array8 = new Marble[6];
    private Marble[] array9 = new Marble[5];
    private Marble[][] boardArray = { array1, array2, array3, array4, array5, array6, array7, array8, array9 };

    // --------Methods----------

    /**
     * Returns an array of all the coordinates on the board.
     * 
     * @invariant coordinates never changes 
     * @return array of coordinates
     */
    public String[] getCoordinates() {
        return coordinates;
    }

    /**
     * Initializes the initial starting position of the board with two players.
     */
    public void initialiseBoard2P() {
        fillBoardBlack2P();
        fillBoardWhite2P();
        fillBoardEmpty2P();
    }

    /**
     * Initializes all the black marbles on the board for a two player game.
     */
    public void fillBoardWhite2P() {
        for (int i = 6; i < boardArray.length; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 6 && (j < 2 || j > 4)) {
                    boardArray[i][j] = Marble.o;
                } else if (i == 6 && j > 1 && j < 5) {
                    boardArray[i][j] = Marble.W;
                }
                if (i == 7 && j < 6) {
                    boardArray[i][j] = Marble.W;
                }
                if (i == 8 && j < 5) {
                    boardArray[i][j] = Marble.W;
                }
            }
        }
    }

    /**
     * Initializes all the white marbles on the board for a two player game.
     */
    public void fillBoardBlack2P() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && j < 5) {
                    boardArray[i][j] = Marble.B;
                }
                if (i == 1 && j < 6) {
                    boardArray[i][j] = Marble.B;
                }
                if (i == 2 && (j < 2 || j > 4)) {
                    boardArray[i][j] = Marble.o;
                } else if (i == 2 && j > 1 && j < 5) {
                    boardArray[i][j] = Marble.B;
                }
            }
        }
    }

    /**
     * Initializes the empty spaces in the board for a two player game.
     */
    public void fillBoardEmpty2P() {
        for (int i = 3; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == 3 && j < 8) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 4 && j < 9) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 5 && j < 8) {
                    boardArray[i][j] = Marble.o;
                }
            }
        }
    }

    /**
     * Initializes the initial starting position of the board with three players.
     */
    public void initialiseBoard3P() {
        this.fillBoardBlack3P();
        this.fillBoardEmpty3P();
        this.fillBoardWhite3P();
        this.fillBoardYellow3P();
    }

    /**
     * Initializes all the black marbles on the board for a three player game.
     */
    public void fillBoardBlack3P() {
        for (int i = 0; i < 6; i++) {
            for (int j = 3; j < 9; j++) {
                if (i == 0 && j < 5 && j > 2) {
                    boardArray[i][j] = Marble.B;
                }
                if (i == 1 && j < 6 && j > 3) {
                    boardArray[i][j] = Marble.B;
                }
                if (i == 2 && j < 7 && j > 4) {
                    boardArray[i][j] = Marble.B;
                }
                if (i == 3 && j < 8 && j > 5) {
                    boardArray[i][j] = Marble.B;
                }
                if (i == 4 && j < 9 && j > 6) {
                    boardArray[i][j] = Marble.B;
                }
                if (i == 5 && j < 8 && j > 6) {
                    boardArray[i][j] = Marble.B;
                }
            }
        }
    }

    /**
     * Initializes all the white marbles on the board for a three player game.
     */
    public void fillBoardWhite3P() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                if (i == 5 && j < 1) {
                    boardArray[i][j] = Marble.W;
                } else if (i < 5 && j < 2) {
                    boardArray[i][j] = Marble.W;
                }
            }
        }
    }

    /**
     * Initializes all the yellow marbles on the board for a three player game.
     */
    public void fillBoardYellow3P() {
        for (int i = 7; i < 9; i++) {
            for (int j = 0; j < 6; j++) {
                if (i == 7 && j < 6) {
                    boardArray[i][j] = Marble.Y;
                } else if (i == 8 & j < 5) {
                    boardArray[i][j] = Marble.Y;
                }
            }
        }
    }

    /**
     * Initializes the empty spaces on the board for a three player game.
     */
    public void fillBoardEmpty3P() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && j == 2) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 1 && j < 4 && j > 1) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 2 && j < 5 && j > 1) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 3 && j < 6 && j > 1) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 4 && j < 7 && j > 0) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 5 && j < 7) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 6 && j < 7) {
                    boardArray[i][j] = Marble.o;
                }
            }
        }
    }

    /**
     * Initializes the initial starting position of the board with four players.
     */
    public void initialiseBoard4P() {
        fillBoardBlack4P();
        fillBoardWhite4P();
        fillBoardRed4P();
        fillBoardYellow4P();
        fillBoardEmpty4P();
    }

    /**
     * Initializes all the black marbles on the board for a four player game.
     */
    public void fillBoardBlack4P() {
        for (int i = 1; i < 5; i++) {
            for (int j = 5; j < 9; j++) {
                if (i == 1 && j == 5) {
                    boardArray[i][j] = Marble.B;
                }
                if (i == 2 && j > 4 && j < 7) {
                    boardArray[i][j] = Marble.B;
                }
                if (i == 3 && j > 4 && j < 8) {
                    boardArray[i][j] = Marble.B;
                }
                if (i == 4 && j > 5 && j < 9) {
                    boardArray[i][j] = Marble.B;
                }
            }
        }
    }

    /**
     * Initializes all the white marbles on the board for a four player game.
     */
    public void fillBoardWhite4P() {
        for (int i = 4; i < 8; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 4 && j < 3) {
                    boardArray[i][j] = Marble.W;
                }
                if (i == 5 && j < 3) {
                    boardArray[i][j] = Marble.W;
                }
                if (i == 6 && j < 2) {
                    boardArray[i][j] = Marble.W;
                }
                if (i == 7 && j < 1) {
                    boardArray[i][j] = Marble.W;
                }
            }
        }
    }

    /**
     * Initializes all the red marbles on the board for a four player game.
     */
    public void fillBoardRed4P() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 && j < 4) {
                    boardArray[i][j] = Marble.R;
                }
                if (i == 1 && j < 4 && j > 0) {
                    boardArray[i][j] = Marble.R;
                }
                if (i == 2 && j < 4 && j > 1) {
                    boardArray[i][j] = Marble.R;
                }
            }
        }
    }

    /**
     * Initializes all the yellow marbles on the board for a four player game.
     */
    public void fillBoardYellow4P() {
        for (int i = 6; i < 9; i++) {
            for (int j = 1; j < 5; j++) {
                if (i == 6 && j > 2 && j < 5) {
                    boardArray[i][j] = Marble.Y;
                }
                if (i == 7 && j > 1 && j < 5) {
                    boardArray[i][j] = Marble.Y;
                }
                if (i == 8 && j > 0 && j < 5) {
                    boardArray[i][j] = Marble.Y;
                }
            }
        }
    }

    /**
     * Initializes the empty spaces on the board for a four player game.
     */
    public void fillBoardEmpty4P() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0 && j == 4) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 1 && j == 0) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 1 && j == 4) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 2 && j < 2) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 2 && j == 4) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 3 && j < 5) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 4 && j < 6 && j > 2) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 5 && j < 8 && j > 2) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 6 && j == 2) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 6 && j > 4 && j < 7) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 7 && j == 1) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 7 && j == 5) {
                    boardArray[i][j] = Marble.o;
                }
                if (i == 8 && j == 0) {
                    boardArray[i][j] = Marble.o;
                }
            }
        }
    }

    /**
     * Creates a copy of the current board on the field.
     * 
     * @ensures a copy of the current board is returned
     * @return a copy of the current board being played on.
     */
    public Board deepCopy() {
        Board copy = new Board();
        for (int i = 0; i < boardArray.length; i++) {
            for (int j = 0; j < boardArray.length; j++) {
                if (i == 0 && j < 5) {
                    copy.setField(i, j, this.getField(i, j));
                }
                if (i == 1 && j < 6) {
                    copy.setField(i, j, this.getField(i, j));
                }
                if (i == 2 && j < 7) {
                    copy.setField(i, j, this.getField(i, j));
                }
                if (i == 3 && j < 8) {
                    copy.setField(i, j, this.getField(i, j));
                }
                if (i == 4 && j < 9) {
                    copy.setField(i, j, this.getField(i, j));
                }
                if (i == 5 && j < 8) {
                    copy.setField(i, j, this.getField(i, j));
                }
                if (i == 6 && j < 7) {
                    copy.setField(i, j, this.getField(i, j));
                }
                if (i == 7 && j < 6) {
                    copy.setField(i, j, this.getField(i, j));
                }
                if (i == 8 && j < 5) {
                    copy.setField(i, j, this.getField(i, j));
                }
            }
        }
        return copy;
    }

    /**
     * Receives a coordinate string (for example "c;4") and filters the first
     * character of importance out and returns this value as an integer.
     * 
     * @requires a string character from a - i followed by ";" followed by an
     *           integer from 1-9.
     * @invariant getFirst is always larger or equal to 0
     * @param coordinate String
     * @return an integer ranging from 0-10
     * @ensures the first coordinate as integer is returned.
     */
    public int getFirst(String coordinate) {
        String[] split = coordinate.split(";");
        switch (split[0]) {
            case "a":
                return 8;
            case "b":
                return 7;
            case "c":
                return 6;
            case "d":
                return 5;
            case "e":
                return 4;
            case "f":
                return 3;
            case "g":
                return 2;
            case "h":
                return 1;
            case "i":
                return 0;
            default:
                break;
        }
        return 10;
    }

    /**
     * Receives a coordinate string (for example "d;6") and filters the second
     * character of importance out and returns this value as an integer representing
     * the correct index of the two dimensional array.
     * 
     * @requires a string character from a - i followed by ";" followed by an
     *           integer from 1-9.
     * @invariant getSecond is always larger or equal to 0
     * @param coordinate String
     * @return the second character of the String as integer.
     * @ensures the second value of the coordinate is returned.
     */
    public int getSecond(String coordinate) {
        String[] split = coordinate.split(";");
        if (split.length != 2) {
            return 10;
        }
        char c = split[1].charAt(0);
        if (!Character.isDigit(c)) {
            return 10;
        }
        int temp = Integer.parseInt(split[1]);
        int second = -1;
        if (temp > 9 || temp < 0) {
            return 10;
        } else if (getFirst(coordinate) == 3) {
            second = temp - 2;
        } else if (getFirst(coordinate) == 2) {
            second = temp - 3;
        } else if (getFirst(coordinate) == 1) {
            second = temp - 4;
        } else if (getFirst(coordinate) == 0) {
            second = temp - 5;
        } else {
            second = temp - 1;
        }
        return second;
    }

    /**
     * This method will check if a coordinate has the marble of a player, thus is
     * not empty.
     * 
     * @requires a string character from a - i followed by ";" followed by an
     *           integer from 1-9.
     * @param coordinate String
     * @ensures that if the coordinate doesn't contain Marble.o, true is returned
     * @return true if the coordinate is not empty
     */
    public boolean hasMarble(String coordinate) {
        if (getField(coordinate) != Marble.o) {
            return true;
        }
        return false;
    }

    /**
     * This method will take two integers, and return it as a string coordinate with
     * a semicolumn in between. like "d;7" if the integers 5 and 6 are given, since
     * 5 and 6 correspond to the coordinate d7 in the 2 dimensional array.
     * 
     * @requires two integers between 0-8
     * @invariant first & second are always larger or equal to 0
     * @param first integer
     * @param second integer
     * @ensures that the input of integers is converted to a string of that coordinate
     * @return String coordinate
     */
    public String integersToStringCoordinate(int first, int second) {
        String i = null;
        String j = null;
        String coordinate = null;
        if (isField(first, second)) {
            switch (first) {
                case 0:
                    i = "i";
                    break;
                case 1:
                    i = "h";
                    break;
                case 2:
                    i = "g";
                    break;
                case 3:
                    i = "f";
                    break;
                case 4:
                    i = "e";
                    break;
                case 5:
                    i = "d";
                    break;
                case 6:
                    i = "c";
                    break;
                case 7:
                    i = "b";
                    break;
                case 8:
                    i = "a";
                    break;
                default:
                    break;
            }
            if (first == 0) {
                j = Integer.toString(second + 5);
            } else if (first == 1) {
                j = Integer.toString(second + 4);
            } else if (first == 2) {
                j = Integer.toString(second + 3);
            } else if (first == 3) {
                j = Integer.toString(second + 2);
            } else {
                j = Integer.toString(second + 1);
            }
            coordinate = i + ";" + j;
        }
        return coordinate;
    }

    /**
     * This method will check whether the field in the two-dimensional array exists.
     * 
     * @requires i & j between 0 - 8
     * @param i integer
     * @param j integer
     * @ensures true if the given integers are a field on the board
     * @return boolean true if valid field and false otherwise.
     */

    public boolean isField(int i, int j) {
        boolean isfield = false;
        try {
            if ((i == 0 || i == 8) && j < 5) {
                isfield = true;
            } else if ((i == 1 || i == 7) && j < 6) {
                isfield = true;
            } else if ((i == 2 || i == 6) && j < 7) {
                isfield = true;
            } else if ((i == 3 || i == 5) && j < 8) {
                isfield = true;
            } else if ((i == 4 && j < 9)) {
                isfield = true;
            } else {
                isfield = false;
            }
        } catch (NullPointerException e) {
            System.out.println("Please enter valid integers");
        }
        return isfield;
    }

    /**
     * This method tests if a given coordinate is a Field on the board.
     * 
     * @requires a string character from a - i followed by ";" followed by an
     *           integer from 1-9.
     * @param coordinate String
     * @ensures true if given coordinate is a field on the board
     * @return true if the given coordinate is a field on the board
     */
    public boolean isField(String coordinate) {
        int x = getFirst(coordinate);
        int y = getSecond(coordinate);
        if (x == 10 || y == 10) {
            return false;
        }
        return isField(x, y);
    }

    /**
     * This method will return the enum value of the marble on the the given
     * position in the two-dimensional array.
     * 
     * @requires integer i and j, both between 0-8
     * @param i integer
     * @param j integer
     * @ensures the marble on the specific coordinate is returned 
     * @returns the Marble on the specific field
     */
    public Marble getField(int i, int j) {
        if (isField(i, j)) {
            Marble marble = boardArray[i][j];
            return marble;
        } else {
            return null;
        }
    }

    /**
     * This method will return the enum value of the marble on the given position in
     * the two-dimensional array.
     * 
     * @requires a string character from a - i followed by ";" followed by an
     *           integer from 1-9.
     * @param coordinate String
     * @ensures the marble on the specific coordinate is returned 
     * @return
     */
    public Marble getField(String coordinate) {
        int x = getFirst(coordinate);
        int y = getSecond(coordinate);
        return getField(x, y);
    }

    /**
     * This method will put a marble on the given position.
     * 
     * @requires i and j to be a valid field together, integers between 0-8, i !=
     *           null, j != null, m != null
     * @invariant ROW & COLUMN are static and will never change
     * @param i integer
     * @param j integer
     * @param m Marble
     * @ensures specified marble m is placed on the the specified position
     */
    public void setField(int i, int j, Marble m) {
        try {
            if (i < ROW && j < COLUMN && i >= 0 && j >= 0 && isField(i, j) == true) {
                boardArray[i][j] = m;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Cannot put marble there.");
        }
    }

    /**
     * This method will put a marble in a valid field, given a string coordinate.
     * 
     * @requires a string character from a - i followed by ";" followed by an
     *           integer from 1-9.
     * @requires coordinate != null && m != null.
     * @param coordinate String
     * @param m Marble
     * @ensures the specified marble is placed on the correct coordinate, given the
     *          string coordinate.
     */
    public void setField(String coordinate, Marble m) {
        if (coordinate != null && m != null) {
            int i = getFirst(coordinate);
            int j = getSecond(coordinate);
            if (isField(i, j)) {
                setField(i, j, m);
            }
        }
    }

    /**
     * Checks if the given field is empty.
     * 
     * @requires a string character from a - i followed by ";" followed by an
     *           integer from 1-9.
     * @requires coordinate != null
     * @param coordinate String
     * @return true when the field is empty
     */
    public boolean isEmptyField(String coordinate) {
        if (this.getField(coordinate) == Marble.o) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method will check whether the game being currently played has a winner.
     * 
     * @requires players != null
     * @param players Player[]
     * @return true if the game has a winner, false if otherwise.
     * @ensures investigate every player whether they are a winner
     */
    public boolean hasWinner(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (this.isWinner(players[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method will check whether a player is a winner.
     * 
     * @requires player != null
     * @param player Player
     * @ensures true is returned when there is a winner
     * @return true if the player has six points
     */
    public boolean isWinner(Player player) {
        if (player.getPoints() == 6) {
            return true;
        }
        return false;
    }

    /**
     * This method will check whether a particular team is a winner when a four
     * player game is played.
     * 
     * @requires player1 != null, player2 !=null and player1 and player2 are in a
     *           team together
     * @param player1 Player
     * @param player2 Player
     * @ensures true is returned when a team has won 
     * @return true when there is a team winner
     */
    public boolean isWinnerTeams(Player player1, Player player2) {
        if ((player1.getPoints() + player2.getPoints()) == 6) {
            return true;
        }
        return false;
    }

    /**
     * This method will check whether there is a winner when a four player game is
     * played.
     * 
     * @requires players != null, players.length = 4
     * @param players Player[]
     * @ensures true returns when one of the teams has won
     * @return true when a team has won
     */
    public boolean hasWinnerTeams(Player[] players) {
        for (int i = 0; i < 2; i++) {
            if (this.isWinnerTeams(players[i], players[i + 2])) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method prints out the board, as well as the basic board for
     * coordinations.
     * 
     */
    public void boardToString() {
        printBoardArray();
        System.out.println("                         ");
        printBasicBoard2();
    }

    /**
     * This method will print out all the arrays of the two-dimensional board.
     */
    public void printBoardArray() {
        System.out.println("    " + Arrays.toString(boardArray[0]));
        System.out.println("   " + Arrays.toString(boardArray[1]));
        System.out.println("  " + Arrays.toString(boardArray[2]));
        System.out.println(" " + Arrays.toString(boardArray[3]));
        System.out.println(Arrays.toString(boardArray[4]));
        System.out.println(" " + Arrays.toString(boardArray[5]));
        System.out.println("  " + Arrays.toString(boardArray[6]));
        System.out.println("   " + Arrays.toString(boardArray[7]));
        System.out.println("    " + Arrays.toString(boardArray[8]));
    }

    /**
     * This method will give the current board as string.
     * 
     * @ensures the print of the board will be returned in a String
     * @return a string of the current board
     */
    public String printboardArray2() {
        String array0 = "    " + Arrays.deepToString(boardArray[0]) + "\n";
        String array1 = "   " + Arrays.deepToString(boardArray[1]) + "\n";
        String array2 = "  " + Arrays.deepToString(boardArray[2]) + "\n";
        String array3 = " " + Arrays.deepToString(boardArray[3]) + "\n";
        String array4 = "" + Arrays.deepToString(boardArray[4]) + "\n";
        String array5 = " " + Arrays.deepToString(boardArray[5]) + "\n";
        String array6 = "  " + Arrays.deepToString(boardArray[6]) + "\n";
        String array7 = "   " + Arrays.deepToString(boardArray[7]) + "\n";
        String array8 = "    " + Arrays.deepToString(boardArray[8]);
        String board = array0 + array1 + array2 + array3 + array4 + array5 + array6 + array7 + array8;
        return board;
    }

    /**
     * This method will print out the basic board.
     * 
     * @invariant basicarray1 & basicarray2 & basicarray3 & basicarray4 & basicarray5 & basicarray6 & 
     *            basicarray7 & basicarray8 & basicarray9 are never changed
     */
    public void printBasicBoard2() {
        System.out.println(basicarray1);
        System.out.println(basicarray2);
        System.out.println(basicarray3);
        System.out.println(basicarray4);
        System.out.println(basicarray5);
        System.out.println(basicarray6);
        System.out.println(basicarray7);
        System.out.println(basicarray8);
        System.out.println(basicarray9);

    }

    /**
     * This method will give the basic board as a string.
     * 
     * @invariant basicarray1 & basicarray2 & basicarray3 & basicarray4 & basicarray5 & basicarray6 & 
     *            basicarray7 & basicarray8 & basicarray9 are never changed
     * @ensures the print of the basic board will be returned as string
     * @return the basic board as a string
     */
    public String printBasicBoard2String() {
        String array0 = basicarray1 + "\n";
        String array1 = basicarray2 + "\n";
        String array2 = basicarray3 + "\n";
        String array3 = basicarray4 + "\n";
        String array4 = basicarray5 + "\n";
        String array5 = basicarray6 + "\n";
        String array6 = basicarray7 + "\n";
        String array7 = basicarray8 + "\n";
        String array8 = basicarray9 + "\n";
        String board = array0 + array1 + array2 + array3 + array4 + array5 + array6 + array7 + array8;
        return board;
    }
}