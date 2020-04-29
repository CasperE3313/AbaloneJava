package tests;

import static org.junit.Assert.assertEquals;

import abalone.Board;
import abalone.HumanPlayer;
import abalone.Marble;
import abalone.Player;
import org.junit.Before;
import org.junit.Test;



public class HumanPlayerTest {

    private Board board;
    private Player player1;
    private Player player2;
    
    /**
     * Sets up the board and two players.
     */
    @Before
    public void setUp() {
        board = new Board();
        board.initialiseBoard2P();
        player1 = new HumanPlayer("Bob", Marble.B, 2);
        player2 = new HumanPlayer("Will", Marble.W, 2);     
    }
    
    @Test
    public void testDetermineMove() {
        String cb1 = "i;8,g;6,DL";
        String cb2 = "i;5,h;4,DL";
        String cb3 = "g;5,g;7,DR";
        
        assertEquals(cb1, player1.determineMove(board, player2, cb1));
        assertEquals(cb2, player1.determineMove(board, player2, cb2));
        assertEquals(cb3, player1.determineMove(board, player2, cb3));
        String d = "default";
        assertEquals(d, player2.determineMove(board, player1, cb1));
        assertEquals(d, player2.determineMove(board, player1, cb2));
        assertEquals(d, player2.determineMove(board, player1, cb3));
        
        board.setField("a;3", Marble.B);
        String cw1 = "c;3,b;3,DR";
        String cw2 = "c;5,b;4,DL";
        String cw3 = "b;3,b;3,DR";

        assertEquals(cw1, player2.determineMove(board, player1, cw1));
        assertEquals(cw2, player2.determineMove(board, player1, cw2));
        assertEquals(d, player2.determineMove(board, player1, cw3));
        assertEquals(d, player1.determineMove(board, player2, cw1));
        assertEquals(d, player1.determineMove(board, player2, cw2));
        assertEquals(d, player1.determineMove(board, player2, cw3));
        String cb4 = "a;3,a;3,UL";
        assertEquals(d, player1.determineMove(board, player2, cb4));
        board.setField("a;3", Marble.W);
        
        board.setField("c;2", Marble.B);
        String cw4 = "a;2,b;2,UL";
        String cw5 = "c;3,c;4,L";
        assertEquals(cw4, player2.determineMove(board, player1, cw4));
        assertEquals(cw5, player2.determineMove(board, player1, cw5));
        assertEquals(d, player1.determineMove(board, player2, cw4));
        assertEquals(d, player1.determineMove(board, player2, cw5));
        board.setField("c;2", Marble.o);
        
        String e1 = "f;3,e;4,DL";
        String e2 = "i;7,e;6,R";
        assertEquals(d, player1.determineMove(board, player2, e1));
        assertEquals(d, player1.determineMove(board, player2, e2));
        assertEquals(d, player2.determineMove(board, player1, e2));
        String e3 = "b;3,c;f,UL";
        assertEquals(d, player2.determineMove(board, player1, e3));
                
        
    }
    
}

