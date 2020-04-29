package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import abalone.Game2P;
import abalone.Game3P;
import abalone.Game4P;
import abalone.HumanPlayer;
import abalone.Marble;
import abalone.Neighbor;
import abalone.Player;
import java.net.Socket;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    private Game2P game2;
    private Game3P game3;
    private Game4P game4;
    private Player p21;
    private Player p22;
    private Player p31;
    private Player p32;
    private Player p33;
    private Player p41;
    private Player p42;
    private Player p43;
    private Player p44;
    private Neighbor ne;

    /**
     * The method to set up the beginning of the test.
     */
    @Before
    public void setUp() {
        p21 = new HumanPlayer("p21", Marble.B, 2);
        p22 = new HumanPlayer("p22", Marble.W, 2);
        game2 = new Game2P(p21, p22);
        ne = new Neighbor();
        game2.start();
        p31 = new HumanPlayer("p31", Marble.B, 3);
        p32 = new HumanPlayer("p32", Marble.W, 3);
        p33 = new HumanPlayer("p33", Marble.Y, 3);
        game3 = new Game3P(p31,p32,p33);
        game3.start();
        p41 = new HumanPlayer("p41", Marble.B, 4);
        p42 = new HumanPlayer("p42", Marble.Y, 4);
        p43 = new HumanPlayer("p43", Marble.W, 4);
        p44 = new HumanPlayer("p44", Marble.R, 4);
        game4 = new Game4P(p41,p42,p43,p44, "team1", "team2");
        game4.start();

    }

    @Test
    public void testTurn() {
        assertTrue(game2.getTurnServer().contains("t;Black"));
        String turn = game2.getTurn();
        assertTrue(turn.contains("p21"));
        assertTrue(turn.contains("B"));
        assertFalse(turn.contains("p22"));
        assertFalse(turn.contains("W"));

    }

    @Test
    public void testConvert() {
        String i1 = "m;a;3;a;4;a;4";
        String i2 = "a;3,a;4,R";
        assertEquals(i2, game2.convertInput(i1));
        assertEquals(i2, game3.convertInput(i1));
        assertEquals(i2, game4.convertInput(i1));
        String r1 = "m;f;4;e;4;e;4";
        String r2 = "f;4,e;4,DR";
        assertEquals(r2, game2.convertInput(r1));
        assertEquals(r2, game3.convertInput(r1));
        assertEquals(r2, game4.convertInput(r1));
        String t1 = "m;d;1;d;2;c;1";
        String t2 = "d;1,d;2,DR";
        assertEquals(t2, game2.convertInput(t1));
        assertEquals(t2, game3.convertInput(t1));
        assertEquals(t2, game4.convertInput(t1));
        String c1 = "m;a;1;a;2;b;1";
        String c2 = "a;1,a;2,UL";
        assertEquals(c2, game2.convertInput(c1));
        assertEquals(c2, game3.convertInput(c1));
        assertEquals(c2, game4.convertInput(c1));
    }

    @Test
    public void testCurrent() {
        assertEquals(0, game2.getCurrent());
        assertEquals(p21, game2.getCurrentPlayer());
        assertEquals(p22, game2.getOpponentPlayer());
        assertEquals(0, game3.getCurrent());
        assertEquals(p31, game3.getCurrentPlayer());
        assertEquals(p32, game3.getOpponentPlayer());
        assertEquals(0, game4.getCurrent());
        assertEquals(p41, game4.getCurrentPlayer());
        assertEquals(p42, game4.getOpponentPlayer());
    }

    @Test
    public void testMarble() {
        assertEquals("Black", game2.getMarblePlayer(0));
        assertEquals("White", game2.getMarblePlayer(1));
        assertEquals("Black", game3.getMarblePlayer(0));
        assertEquals("White", game3.getMarblePlayer(1));
        assertEquals("Yellow", game3.getMarblePlayer(2));
        assertEquals("Black", game4.getMarblePlayer(0));
        assertEquals("Yellow", game4.getMarblePlayer(1));
        assertEquals("White", game4.getMarblePlayer(2));
        assertEquals("Red", game4.getMarblePlayer(3));
    }

    @Test
    public void testSockets() {
        Socket s1 = new Socket();
        Socket s2 = new Socket();
        
        game2.addSockets(s1);
        assertEquals(1, game2.getSockets().size());
        game2.addSockets(s2);
        assertEquals(2, game2.getSockets().size());
        assertEquals(s1, game2.getSocket());
        assertFalse(game2.getSocket() == s2);
        
        game3.addSockets(s1);
        assertEquals(1, game3.getSockets().size());
        game3.addSockets(s2);
        assertEquals(2, game3.getSockets().size());
        Socket s3 = new Socket();
        game3.addSockets(s3);
        assertEquals(3, game3.getSockets().size());
        assertEquals(s1, game3.getSocket());
        assertFalse(game3.getSocket() == s2);
        
        game4.addSockets(s1);
        assertEquals(1, game4.getSockets().size());
        game4.addSockets(s2);
        assertEquals(2, game4.getSockets().size());
        game4.addSockets(s3);
        assertEquals(3, game4.getSockets().size());
        Socket s4 = new Socket();
        game4.addSockets(s4);
        assertEquals(4, game4.getSockets().size());
        assertEquals(s1, game4.getSocket());
        assertFalse(game4.getSocket() == s2);
        
    }

    @Test
    public void testHint() {
        String hint2 = game2.hint();
        String[] ht2 = hint2.split(",");
        assertTrue(game2.getBoard().isField(ht2[0]));
        assertTrue(game2.getBoard().isField(ht2[1]));
        assertTrue(game2.getBoard().getField(ht2[0]).equals(p21.getMarble()));
        assertTrue(game2.getBoard().getField(ht2[1]).equals(p21.getMarble()));
        assertTrue(ne.isDirection(ht2[2]));
        
        String hint3 = game3.hint();
        String[] ht3 = hint3.split(",");
        assertTrue(game3.getBoard().isField(ht3[0]));
        assertTrue(game3.getBoard().isField(ht3[1]));
        assertTrue(game3.getBoard().getField(ht3[0]).equals(p31.getMarble()));
        assertTrue(game3.getBoard().getField(ht3[1]).equals(p31.getMarble()));
        assertTrue(ne.isDirection(ht3[2]));

        String hint4 = game4.hint();
        String[] ht = hint4.split(",");
        assertTrue(game4.getBoard().isField(ht[0]));
        assertTrue(game4.getBoard().isField(ht[1]));
        assertTrue(game4.getBoard().getField(ht[0]).equals(p21.getMarble()));
        assertTrue(game4.getBoard().getField(ht[1]).equals(p21.getMarble()));
        assertTrue(ne.isDirection(ht[2]));
    }
}
