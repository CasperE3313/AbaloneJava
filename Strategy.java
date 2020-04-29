package abalone;

public interface Strategy {
    
    public String getName();
    
    public String determineMove(Board board, Player player, Player opponent);
}
