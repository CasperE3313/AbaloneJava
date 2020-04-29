package server;

import java.util.ArrayList;
import java.util.Iterator;

public class Lobby {

    String lobbyname;
    public ArrayList<ServerPlayer> players;
    public ArrayList<ClientHandler> handlers;

    // ---------------------Lobby constructor-------------------------

    /**
     * Constructor of the class Lobby.
     * 
     * @param lobbyname String
     */
    public Lobby(String lobbyname) {
        this.lobbyname = lobbyname;
        this.players = new ArrayList<>();
        this.handlers = new ArrayList<>();
    }

    // -------------------------------------------------------------------

    /**
     * Method which will return the size of the players List.
     * 
     * @return Integer equal to the size of list of players.
     */

    public int getSize() {
        return players.size();
    }

    /**
     * Return the ArrayList of all ServerPlayers in the lobby.
     * 
     * @return players
     */

    public ArrayList<ServerPlayer> getPlayerArrayList() {
        return players;
    }

    /**
     * Method to add a clients' ClientHandler object to the list of handlers.
     * 
     * @param h ClientHandler
     */

    public void addToHandlers(ClientHandler h) {
        handlers.add(h);
    }

    /**
     * Return the ArrayList of all ClientHandlers in the lobby.
     * 
     * @return handlers
     */

    public ArrayList<ClientHandler> getHandlers() {
        return handlers;
    }

    /**
     * Method to remove a player from the players ArrayList.
     * 
     * @param player ServerPlayer
     */
    public synchronized void removePlayer(ServerPlayer player) {

        Iterator<ServerPlayer> iter = players.iterator();
        while (iter.hasNext()) {

            ServerPlayer serverplayer = iter.next();

            if (serverplayer == player) {
                iter.remove();
            }

        }

    }

    /**
     * Method to remove a ClientHandler from the handlers ArrayList.
     * 
     * @param handle ClientHandler
     */

    public synchronized void removeHandler(ClientHandler handle) {
        Iterator<ClientHandler> iter = handlers.iterator();
        while (iter.hasNext()) {

            ClientHandler handler = iter.next();

            if (handler == handle) {
                iter.remove();
            }

        }
    }

    /**
     * Return the name of the lobby.
     * 
     * @return String lobbyname.
     */

    public String getName() {
        return lobbyname;
    }

    /**
     * Method that adds a ServerPlayer to the players ArrayList.
     * 
     * @param player ServerPlayer
     */

    public void addToPlayers(ServerPlayer player) {
        players.add(player);
    }

    /**
     * Method that returns the players as String.
     * 
     * @return lobbyname as String + all players in this lobby as String
     */

    public String getPlayers() {

        String playerinfo = "";

        for (ServerPlayer player : players) {
            String name = player.getName();
            String teamname = player.getTeamName();

            playerinfo += "p;" + name + ";" + teamname + ";";

        }

        return "l;" + getName() + ";" + playerinfo + " ---EOT---";
    }

    /**
     * Returns all the startplayers with their teamname.
     * 
     * @return String playerinfo
     */
    public String getStartPlayers() {
        String playerinfo = "s;";

        for (ServerPlayer player : players) {
            String name = player.getName();
            String teamname = player.getTeamName();

            playerinfo += name + ";" + teamname + ";";

        }

        return playerinfo;
    }

}
