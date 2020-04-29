package abalone;

/**
 * Represents a 'marble' in the game abalone. In order to represent the colors
 * of a marble we use the capital letters, which will be shown in the TUI.
 *
 */ 

public enum Marble {
    o, B, W, R, Y;
    
    /**
     * Returns the marble in String form, written fully.
     * 
     * @param m Marble
     * @return name of marble as String
     */
    public String getName(Marble m) {
        if (m == B) {
            return "Black";
        } else if (m == W) {
            return "White";
        } else if (m == R) {
            return "Red";
        } else if (m == Y) {
            return "Yellow";
        }
        return null;
    }
}