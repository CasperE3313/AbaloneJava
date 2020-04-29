package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import abalone.Board;
import abalone.Edge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class EdgeTest {

    Board board;
    Edge edge;
    
    /**
     * Sets up before every method.
     */
    @BeforeEach
    public void setup() {
        board = new Board();
        edge = new Edge();
        
    }
    
    @Test
    public void testIsEdgeField() {
        String field1 = "i;5";
        assertTrue(edge.isEdgeField(field1));
        String field2 = "d;1";
        assertTrue(edge.isEdgeField(field2));
        String field3 = "c;7";
        assertTrue(edge.isEdgeField(field3));
        String field4 = "e;9";
        assertTrue(edge.isEdgeField(field4));
        String field5 = "f;3";
        assertFalse(edge.isEdgeField(field5));
        String field6 = "d;5";
        assertFalse(edge.isEdgeField(field6));
        String field7 = "g;6";
        assertFalse(edge.isEdgeField(field7));
        String field8 = "b;3";
        assertFalse(edge.isEdgeField(field8));



    }
    
    @Test
    public void testIsEdgeFieldDirection() {
        String field1 = "i;5";
        assertTrue(edge.isEdgeField(field1, "UL"));
        assertFalse(edge.isEdgeField(field1, "DR"));
        String field2 = "d;1";
        assertTrue(edge.isEdgeField(field2, "L"));
        assertFalse(edge.isEdgeField(field2, "R"));
        String field3 = "c;7";
        assertTrue(edge.isEdgeField(field3, "R"));
        assertFalse(edge.isEdgeField(field3, "L"));
        String field4 = "e;9";
        assertTrue(edge.isEdgeField(field4, "DR"));
        assertFalse(edge.isEdgeField(field4, "UL"));
    }
    
    @Test
    public void testGetEdgeDirections() {
        String field1 = "i;5";
        String field2 = "d;1";
        String drc1 = edge.getEdgeDirections(field1);
        String drc2 = edge.getEdgeDirections(field2);
        String [] one = drc1.split(" ");
        String [] two = drc2.split(" ");
        assertTrue(edge.containsValue("UL", one));
        assertFalse(edge.containsValue("DL", one));
        assertTrue(edge.containsValue("DL", two));
        assertFalse(edge.containsValue("UR", two));
        String field3 = "c;7";
        String field4 = "e;9";
        String drc3 = edge.getEdgeDirections(field3);
        String drc4 = edge.getEdgeDirections(field4);
        String [] three = drc3.split(" ");
        String [] four = drc4.split(" ");
        assertTrue(edge.containsValue("DR", three));
        assertFalse(edge.containsValue("UL", three));
        assertTrue(edge.containsValue("R", four));
        assertFalse(edge.containsValue("L", four));
    }
}