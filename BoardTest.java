package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import abalone.Board;
import abalone.Marble;
import org.junit.Before;
import org.junit.Test;



public class BoardTest {

    private Board board;

    /**
    * Sets up a board for two player game.
    */

    @Before
    public void setUp() {
        board = new Board();
        board.initialiseBoard2P();

    }

    @Test
    public void testGetFirst() {

        assertEquals(4, board.getFirst("e;6"));
        assertEquals(0, board.getFirst("i;3"));
        assertEquals(2, board.getFirst("g;3"));
        assertEquals(7, board.getFirst("b;5"));
        assertEquals(8, board.getFirst("a;2"));

    }

    @Test
    public void testGetSecond() {

        assertEquals(5, board.getSecond("d;6"));
        assertEquals(3, board.getSecond("i;8"));
        assertEquals(3, board.getSecond("e;4"));
        assertEquals(3, board.getSecond("d;4"));
        assertEquals(3, board.getSecond("c;4"));
        assertEquals(3, board.getSecond("b;4"));
        assertEquals(3, board.getSecond("a;4"));

        assertEquals(4, board.getSecond("e;5"));
        assertEquals(4, board.getSecond("f;6"));
        assertEquals(4, board.getSecond("h;8"));

        assertEquals(6, board.getSecond("e;7"));
        assertEquals(6, board.getSecond("d;7"));
        assertEquals(6, board.getSecond("c;7"));

        assertEquals(8, board.getSecond("e;9"));
        assertEquals(7, board.getSecond("f;9"));
        assertEquals(6, board.getSecond("g;9"));
        assertEquals(5, board.getSecond("h;9"));

    }

    @Test
    public void testIntegersToStringCoordinate() {

        assertEquals("d;7", board.integersToStringCoordinate(5, 6));
        assertEquals("e;3", board.integersToStringCoordinate(4, 2));
        assertEquals("f;2", board.integersToStringCoordinate(3, 0));
        assertEquals("g;4", board.integersToStringCoordinate(2, 1));
        assertEquals("a;5", board.integersToStringCoordinate(8, 4));
        assertEquals("d;2", board.integersToStringCoordinate(5, 1));
        assertEquals("e;4", board.integersToStringCoordinate(4, 3));
        assertEquals("i;7", board.integersToStringCoordinate(0, 2));
        assertEquals("b;2", board.integersToStringCoordinate(7, 1));

    }

    @Test
    public void testIsField() {

        assertTrue(board.isField(0, 0));
        assertTrue(board.isField(0, 4));
        assertTrue(board.isField(4, 8));
        assertTrue(board.isField(8, 4));
        assertTrue(board.isField(8, 0));
        assertTrue(board.isField(4, 0));
        assertTrue(board.isField(2, 3));
        assertTrue(board.isField(3, 7));
        assertTrue(board.isField(3, 2));
        assertTrue(board.isField(5, 5));
        assertTrue(board.isField(6, 6));
        assertTrue(board.isField(7, 5));
        assertTrue(board.isField(8, 2));

        assertFalse(board.isField(-3, 33));
        assertFalse(board.isField(-2, 6));
        assertFalse(board.isField(0, 35));
        assertFalse(board.isField(9, 3));
        assertFalse(board.isField(22, 37));
        assertFalse(board.isField(-5, -3));
        assertFalse(board.isField(3, 8));
        assertFalse(board.isField(12414, 2412412));
        assertFalse(board.isField(12, 5));
        assertFalse(board.isField(5, 9));
        assertFalse(board.isField(6, 7));

    }

    @Test
    public void testGetFieldAndSetField() {
        board.setField(3, 3, Marble.B);
        assertEquals(Marble.B, board.getField(3, 3));

        board.setField(5, 5, Marble.W);
        assertEquals(Marble.W, board.getField(5, 5));

        board.setField(6, 6, Marble.R);
        assertEquals(Marble.R, board.getField(6, 6));

        assertEquals(Marble.o, board.getField(4, 4));
        assertEquals(Marble.o, board.getField(6, 1));
        assertEquals(Marble.o, board.getField(5, 7));

        board.setField(2, 7, Marble.Y);
        assertFalse(board.getField(2, 7) == Marble.Y);

    }

    @Test
    public void testSetFieldString() {

        board.setField("d;2", Marble.B);

        board.setField("c;1", Marble.R);
        assertEquals(Marble.R, board.getField(6, 0));

        board.setField("c;7", Marble.Y);
        assertEquals(Marble.Y, board.getField(6, 6));

        board.setField("d;2", Marble.W);
        assertEquals(Marble.W, board.getField(5, 1));

        board.setField("d;3", Marble.R);
        assertEquals(Marble.R, board.getField(5, 2));

        board.setField("d;8", Marble.B);
        assertEquals(Marble.B, board.getField(5, 7));

        board.setField("e;4", Marble.R);
        assertEquals(Marble.R, board.getField(4, 3));

        board.setField("e;5", Marble.Y);
        assertEquals(Marble.Y, board.getField(4, 4));

        board.setField("f;2", Marble.Y);
        assertEquals(Marble.Y, board.getField(3, 0));

        board.setField("f;8", Marble.Y);
        assertEquals(Marble.Y, board.getField(3, 6));

        board.setField("f;9", Marble.W);
        assertEquals(Marble.W, board.getField(3, 7));

        board.setField("g;4", Marble.R);
        assertEquals(Marble.R, board.getField(2, 1));

        board.setField("g;8", Marble.B);
        assertEquals(Marble.B, board.getField(2, 5));

    }

    @Test
    public void isEmptyFieldTest() {

        assertTrue(board.isEmptyField("f;2"));
        assertTrue(board.isEmptyField("g;9"));
        assertTrue(board.isEmptyField("d;4"));
        assertTrue(board.isEmptyField("e;1"));
        assertTrue(board.isEmptyField("c;1"));
        assertTrue(board.isEmptyField("e;6"));
        
        assertFalse(board.isEmptyField("i;6"));
        assertFalse(board.isEmptyField("h;7"));
        assertFalse(board.isEmptyField("b;4"));
        assertFalse(board.isEmptyField("a;1"));
        assertFalse(board.isEmptyField("c;4"));
        assertFalse(board.isEmptyField("h;9"));
        assertFalse(board.isEmptyField("b;6"));

    }

}