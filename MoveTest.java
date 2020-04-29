package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import abalone.Board;
import abalone.HumanPlayer;
import abalone.Marble;
import abalone.Move;
import abalone.Player;
import org.junit.Before;
import org.junit.Test;



public class MoveTest {

    private Move move1;
    private Move move2;
    private Board board;
    private Player player1;
    private Player player2;
    
    /**
     * Sets up the board with two players.
     */
    @Before
    public void setUp() {
        board = new Board();
        board.initialiseBoard2P();
        player1 = new HumanPlayer("Bob", Marble.B, 2);
        player2 = new HumanPlayer("Will", Marble.W, 2);
        move1 = new Move(board, player1);
        move2 = new Move(board, player2);
    }
    
    @Test
    public void testOpponent() {
        assertTrue(move1.checkIfOpponentMarble(Marble.W));
        assertFalse(move1.checkIfOpponentMarble(Marble.B));
        assertTrue(move2.checkIfOpponentMarble(Marble.B));
        assertFalse(move2.checkIfOpponentMarble(Marble.W));
    }
    
    @Test
    public void testLineMove() {
        assertTrue(move1.isLineMove("h;6", "g;5", "DL"));
        assertTrue(move1.isLineMove("a;3", "c;3", "UL"));
        assertTrue(move1.isLineMove("g;5", "g;7", "L"));
        assertFalse(move1.isLineMove("h;6", "g;5", "R"));
        assertFalse(move1.isLineMove("a;3", "c;3", "UR"));
        assertFalse(move1.isLineMove("f;4", "f;6", "DR"));
        assertFalse(move1.isLineMove("h;6", "g;7", "DL"));
        assertFalse(move1.isLineMove("a;2", "c;3", "UL"));
        assertFalse(move1.isLineMove("f;4", "e;6", "L"));
    }
    
    @Test
    public void testSumitoMove() {
        board.setField("d;4", Marble.B);
        assertTrue(move2.isSumito("a;4", "c;4", "UL"));
        assertFalse(move2.isSumito("a;5", "c;5", "UL"));
        assertFalse(move1.isSumito("a;4", "c;4", "UL"));
        board.setField("d;4", Marble.o);
        
        board.setField("g;4", Marble.W);
        assertTrue(move1.isSumito("i;6", "h;5", "DL"));
        assertTrue(move1.isSumito("g;5", "g;7", "L"));
        assertFalse(move1.isSumito("i;9","h;9", "DR"));
        assertFalse(move2.isSumito("g;5", "g;7", "L"));
        board.setField("g;4", Marble.o);    
    }
    
    @Test
    public void testPushOff() {
        board.setField("i;7", Marble.W);
        assertTrue(move1.isPushOff("h;7", "g;7", "UL"));
        assertTrue(move1.isPushOff("h;6", "g;5", "UR"));
        assertFalse(move2.isPushOff("h;7", "g;7", "UL"));
        assertFalse(move2.isPushOff("h;6", "g;5", "UR"));
        assertFalse(move1.isPushOff("h;8", "g;7", "UR"));
        assertFalse(move1.isPushOff("h;6", "g;6", "UL"));
        board.setField("i;7", Marble.B);
        
        board.setField("c;6", Marble.B);
        board.setField("c;7", Marble.B);
        assertTrue(move2.isPushOff("c;3", "c;5", "R"));
        assertFalse(move1.isPushOff("c;3", "c;5", "R"));
        assertFalse(move2.isPushOff("a;5", "b;6", "R"));
        board.setField("c;6", Marble.o);
        board.setField("c;7", Marble.o);
    }
    
    @Test 
    public void testGetFront() {
        String h6 = "h;6";
        String h7 = "h;7";
        String h8 = "h;8";
        assertEquals(h6, move1.getFrontMarble(h6, h8,"L"));
        assertEquals(h7, move1.getFrontMarble(h7, h8,"L"));
        assertEquals(h8, move1.getFrontMarble(h6, h8,"R"));
        String g5 = "g;5";
        String g6 = "g;6";
        String g7 = "g;7";
        assertEquals(g5, move1.getFrontMarble(h6, g5,"DL"));
        assertEquals(g6, move1.getFrontMarble(h6, g6,"DR"));
        assertEquals(g7, move1.getFrontMarble(g5, g7, "R"));
        assertEquals(h8, move1.getFrontMarble(g7, h8,"UR"));
                
    }
    
    @Test
    public void testGetBack() {
        String h6 = "h;6";
        String h7 = "h;7";
        String h8 = "h;8";
        assertEquals(h6, move1.getBackMarble(h6, h8,"R"));
        assertEquals(h7, move1.getBackMarble(h7, h8,"R"));
        assertEquals(h8, move1.getBackMarble(h6, h8,"L"));
        String g5 = "g;5";
        String g6 = "g;6";
        String g7 = "g;7";
        assertEquals(g5, move1.getBackMarble(h6, g5,"UR"));
        assertEquals(g6, move1.getBackMarble(h6, g6,"UL"));
        assertEquals(g7, move1.getBackMarble(g5, g7,"L"));
        assertEquals(h8, move1.getBackMarble(g7, h8,"DL"));
    }
    
    @Test
    public void testMoveMarble() {
        move1.moveMarble("g;5", "DL", Marble.B);
        assertEquals(Marble.B, board.getField("f;4"));
        assertEquals(Marble.o, board.getField("g;5"));
        move1.moveMarble("f;4", "DR", Marble.B);
        assertEquals(Marble.o, board.getField("f;4"));
        assertEquals(Marble.B, board.getField("e;4"));
        move1.moveMarble("e;4", "R", Marble.B);
        assertEquals(Marble.B, board.getField("e;5"));
        assertEquals(Marble.o, board.getField("e;4"));
        move1.moveMarble("e;5", "L", Marble.B);
        assertEquals(Marble.o, board.getField("e;5"));
        assertEquals(Marble.B, board.getField("e;4"));
        move1.moveMarble("e;4", "UL", Marble.B);
        assertEquals(Marble.B, board.getField("f;4"));
        assertEquals(Marble.o, board.getField("e;4"));
        move1.moveMarble("f;4", "UR", Marble.B);
        assertEquals(Marble.o, board.getField("f;4"));
        assertEquals(Marble.B, board.getField("g;5"));
    }
}
