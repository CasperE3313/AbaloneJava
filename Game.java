package abalone;

import java.net.Socket;
import java.util.ArrayList;

public abstract class Game {

    public abstract void start();
    
    public abstract void reset();
    
    public abstract void update();
    
    public abstract boolean move(String first, String second, String direction);
    
    public abstract String hint();
    
    public abstract String hintServer();
    
    public abstract String convertInput(String input);
    
    public abstract String getScore();
    
    public abstract boolean checkIfGameOver();
    
    public abstract String printResult();
    
    public abstract String printResultProtocol();
    
    public abstract void printOutResult();
    
    public abstract String getTurn();
    
    public abstract String getTurnServer();
    
    public abstract int getCurrent();
    
    public abstract Player getCurrentPlayer();
    
    public abstract Player getOpponentPlayer();
    
    public abstract String getMarblePlayer(int i);
    
    public abstract Board getBoard();
    
    public abstract void addSockets(Socket s);
    
    public abstract Socket getSocket();
    
    public abstract ArrayList<Socket> getSockets();
    
    
}
