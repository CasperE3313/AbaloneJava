package server;

import java.net.Socket;

public class ServerPlayer {

    private String name;
    private String teamname;
    private Socket socket;
    private boolean ready;

    /**
     * Constructor of the Serverplayer.
     * 
     * @param name     String
     * @param teamname String
     * @param s        Socket
     */
    public ServerPlayer(String name, String teamname, Socket s) {
        this.name = name;
        this.teamname = teamname;
        this.socket = s;
    }

    /**
     * Return the name of the ServerPlayer.
     * 
     * @return String name.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the team name of the player.
     * 
     * @return String teamname.
     */
    public String getTeamName() {
        return teamname;
    }

    /**
     * Return the socket of a ServerPlayer.
     * 
     * @return Socket socket.
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Sets the name of the ServerPlayer.
     * 
     * @param input String
     */
    public void setName(String input) {
        this.name = input;

    }

    /**
     * Sets the teamname of the ServerPlayer.
     * 
     * @param input String
     */
    public void setTeamName(String input) {
        this.teamname = input;
    }

    /**
     * Sets the boolean ready of the ServerPlayer to false or true.
     * 
     * @param r boolean
     */
    public void setReady(boolean r) {
        ready = r;
    }

    /**
     * Checks if the ServerPlayer is ready or not.
     * 
     * @return true if ready == true
     */
    public boolean isReady() {
        if (ready == true) {
            return true;
        }
        return false;
    }
}
