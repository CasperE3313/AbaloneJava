package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import abalone.Neighbor;
import org.junit.Before;
import org.junit.Test;


public class NeighborTest {

    private Neighbor neighbor;

    @Before
    public void setUp() {
        neighbor = new Neighbor();
    }

    @Test
    public void testGetUL() {

        assertEquals("g;4", neighbor.getUL("f;4"));
        assertEquals("h;4", neighbor.getUL("g;4"));
        assertEquals("h;7", neighbor.getUL("g;7"));
        assertEquals("i;5", neighbor.getUL("h;5"));
        assertEquals("e;2", neighbor.getUL("d;2"));
        assertEquals("e;5", neighbor.getUL("d;5"));
        assertEquals("e;8", neighbor.getUL("d;8"));
        assertEquals("c;1", neighbor.getUL("b;1"));
        assertEquals("b;1", neighbor.getUL("a;1"));
        assertEquals("b;5", neighbor.getUL("a;5"));
        assertEquals("c;6", neighbor.getUL("b;6"));

    }

    @Test
    public void testGetUR() {

        assertEquals("b;2", neighbor.getUR("a;1"));
        assertEquals("c;5", neighbor.getUR("b;4"));
        assertEquals("d;5", neighbor.getUR("c;4"));
        assertEquals("i;9", neighbor.getUR("h;8"));
        assertEquals("h;6", neighbor.getUR("g;5"));
        assertEquals("g;3", neighbor.getUR("f;2"));
        assertEquals("f;3", neighbor.getUR("e;2"));
        assertEquals("e;6", neighbor.getUR("d;5"));
        assertEquals("d;8", neighbor.getUR("c;7"));
        assertEquals("c;7", neighbor.getUR("b;6"));
        assertEquals("b;6", neighbor.getUR("a;5"));

    }

    @Test
    public void testGetR() {

        assertEquals("i;6", neighbor.getR("i;5"));
        assertEquals("h;5", neighbor.getR("h;4"));
        assertEquals("g;4", neighbor.getR("g;3"));
        assertEquals("f;7", neighbor.getR("f;6"));
        assertEquals("e;2", neighbor.getR("e;1"));
        assertEquals("d;3", neighbor.getR("d;2"));
        assertEquals("c;5", neighbor.getR("c;4"));
        assertEquals("b;6", neighbor.getR("b;5"));
        assertEquals("a;4", neighbor.getR("a;3"));

        assertEquals("b;6", neighbor.getR("b;5"));
        assertEquals("c;6", neighbor.getR("c;5"));
        assertEquals("d;7", neighbor.getR("d;6"));
        assertEquals("e;8", neighbor.getR("e;7"));

    }

    @Test
    public void testGetL() {

        assertEquals("i;6", neighbor.getL("i;7"));
        assertEquals("h;5", neighbor.getL("h;6"));
        assertEquals("g;4", neighbor.getL("g;5"));
        assertEquals("f;3", neighbor.getL("f;4"));
        assertEquals("e;2", neighbor.getL("e;3"));
        assertEquals("d;1", neighbor.getL("d;2"));
        assertEquals("c;3", neighbor.getL("c;4"));
        assertEquals("b;2", neighbor.getL("b;3"));
        assertEquals("a;1", neighbor.getL("a;2"));

    }

    @Test
    public void testGetDL() {

        assertEquals("a;1", neighbor.getDL("b;2"));
        assertEquals("b;2", neighbor.getDL("c;3"));
        assertEquals("c;3", neighbor.getDL("d;4"));
        assertEquals("d;4", neighbor.getDL("e;5"));
        assertEquals("e;5", neighbor.getDL("f;6"));
        assertEquals("f;6", neighbor.getDL("g;7"));
        assertEquals("g;7", neighbor.getDL("h;8"));
        assertEquals("h;8", neighbor.getDL("i;9"));

        assertEquals("d;6", neighbor.getDL("e;7"));
        assertEquals("f;7", neighbor.getDL("g;8"));
        assertEquals("b;2", neighbor.getDL("c;3"));

    }

    @Test
    public void testGetDR() {

        assertEquals("b;5", neighbor.getDR("c;5"));
        assertEquals("c;5", neighbor.getDR("d;5"));
        assertEquals("d;5", neighbor.getDR("e;5"));
        assertEquals("e;5", neighbor.getDR("f;5"));
        assertEquals("f;5", neighbor.getDR("g;5"));
        assertEquals("g;5", neighbor.getDR("h;5"));
        assertEquals("h;5", neighbor.getDR("i;5"));
        assertEquals("a;5", neighbor.getDR("b;5"));

        assertEquals("f;8", neighbor.getDR("g;8"));
        assertEquals("c;7", neighbor.getDR("d;7"));

    }

    @Test
    public void checkIfNeighbour() {

        assertTrue(neighbor.checkIfNeighbor("c;4", "c;5"));
        assertTrue(neighbor.checkIfNeighbor("e;5", "d;4"));
        assertTrue(neighbor.checkIfNeighbor("i;8", "i;7"));
        assertTrue(neighbor.checkIfNeighbor("a;1", "b;2"));
        assertTrue(neighbor.checkIfNeighbor("b;2", "b;3"));
        assertTrue(neighbor.checkIfNeighbor("c;3", "b;2"));
        assertTrue(neighbor.checkIfNeighbor("d;4", "d;3"));
        assertTrue(neighbor.checkIfNeighbor("e;5", "f;5"));
        assertTrue(neighbor.checkIfNeighbor("f;6", "f;7"));
        assertTrue(neighbor.checkIfNeighbor("g;7", "f;6"));

        assertFalse(neighbor.checkIfNeighbor("c;2", "f;9"));
        assertFalse(neighbor.checkIfNeighbor("d;3", "c;6"));
        assertFalse(neighbor.checkIfNeighbor("e;4", "z;12")); 
        assertFalse(neighbor.checkIfNeighbor("f;5", "b;2"));
        assertFalse(neighbor.checkIfNeighbor("g;6", "c;3"));
        assertFalse(neighbor.checkIfNeighbor("h;7", "i;9"));

    }

    @Test
    public void checkIfSharedNeighbour() {

        assertTrue(neighbor.checkIfSharedNeighbor("a;1", "c;3"));
        assertTrue(neighbor.checkIfSharedNeighbor("d;5", "f;5"));
        assertTrue(neighbor.checkIfSharedNeighbor("e;1", "e;3"));
        assertTrue(neighbor.checkIfSharedNeighbor("g;5", "e;3"));
        assertTrue(neighbor.checkIfSharedNeighbor("h;7", "h;9"));
        assertTrue(neighbor.checkIfSharedNeighbor("c;2", "c;4"));
        assertTrue(neighbor.checkIfSharedNeighbor("f;5", "d;3"));
        assertTrue(neighbor.checkIfSharedNeighbor("g;3", "e;1"));
        assertTrue(neighbor.checkIfSharedNeighbor("c;5", "c;3"));

        assertFalse(neighbor.checkIfSharedNeighbor("e;3", "e;4"));
        assertFalse(neighbor.checkIfSharedNeighbor("d;5", "d;4"));
        assertFalse(neighbor.checkIfSharedNeighbor("i;8", "b;2"));

        assertFalse(neighbor.checkIfSharedNeighbor("h;7", "i;8"));

    }

    @Test
    public void testGetSharedNeighbour() {

        assertEquals("c;3", neighbor.getSharedNeighbor("b;2", "d;4"));
        assertEquals("e;2", neighbor.getSharedNeighbor("e;1", "e;3"));
        assertEquals("e;6", neighbor.getSharedNeighbor("f;6", "d;6"));
        assertEquals("b;5", neighbor.getSharedNeighbor("b;4", "b;6"));
        assertEquals("a;3", neighbor.getSharedNeighbor("a;4", "a;2"));
        assertEquals("f;8", neighbor.getSharedNeighbor("e;7", "g;9"));
        assertEquals("h;6", neighbor.getSharedNeighbor("h;7", "h;5"));

    }

}