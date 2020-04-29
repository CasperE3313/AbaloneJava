package server;

import abalone.ComputerPlayer;
import abalone.Game2P;
import abalone.Game3P;
import abalone.Game4P;
import abalone.HumanPlayer;
import abalone.Marble;
import abalone.NaiveStrategy;
import abalone.SmartStrategy;
import abalone.Strategy;
import java.awt.AWTException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import utils.TextIO;

public class Server {

    private static Server serverObject;
    public ArrayList<ServerPlayer> players = new ArrayList<>();
    public ArrayList<Lobby> lobbies = new ArrayList<>();
    public ArrayList<Game2P> twoPlayerGames = new ArrayList<>();
    public ArrayList<Game3P> threePlayerGames = new ArrayList<>();
    public ArrayList<Game4P> fourPlayerGames = new ArrayList<>();

    /**
     * Main method to establish connections.
     * 
     * @param args String[]
     * @throws IOException when an I/O operation has failed.
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = setup();
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                System.out.println("Client connected: " + socket);
                DataInputStream datainputstream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataoutputstream = new DataOutputStream(socket.getOutputStream());
                System.out.println("Setting up new thread for this client");
                Thread t = new ClientHandler(socket, datainputstream, dataoutputstream);
                t.start();
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }

    }

    /**
     * Method used to get a user-input integer.
     * 
     * @ensures integer which user specified is returned.
     * @return int port
     */
    public static int getPort() {
        int port = 0;
        while (port == 0) {
            System.out.println("Please give an integer for the server port");
            port = TextIO.getlnInt();
        }
        return port;
    }

    /**
     * Setup() method used to create a ServerSocket in a user-friendly while loop.
     * 
     * @ensures ServerSocket is created with portnumber specified by user and returned.
     * @return ServerSocket
     * @throws IOException when an I/O operation has failed.
     */
    public static ServerSocket setup() throws IOException {
        ServerSocket serverSocket = null;
        while (serverSocket == null) {
            try {
                int port = getPort();
                System.out.println("Attempting to start server on port.." + port);
                serverSocket = new ServerSocket(port);
            } catch (BindException e) {
                System.out.println("Port number already in use..");
                System.out.println("Would you like to try again?");
                boolean maybe = TextIO.getlnBoolean();
                if (maybe == false) {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("You've used an illegal port");
            }
        }
        return serverSocket;
    }

    /**
     * Method used to add to our overall player list.
     * @invariant player != null
     * @param player ServerPlayer
     * @ensures ServerPlayer is added to the list of players, and players.size() is incremented by 1.
     */
    public void addToPlayerlist(ServerPlayer player) {
        players.add(player);
    }

    /**
     * Returns position of player at socket S.
     * 
     * @param s Socket
     * @invariant s is never null
     * @ensures integer is returned of position of the ServerPlayer the lobby players list.
     * @return integer of position
     */
    public int getPosition(Socket s) {
        ServerPlayer player = getPlayer(s);
        for (Lobby lobby : lobbies) {
            for (ServerPlayer playera : lobby.players) {
                if (player == playera) {
                    int size = lobby.players.size();
                    for (int i = 0; i < size; i++) {
                        if (lobby.players.get(i) == player) {
                            return i;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Adding a lobby to the lobby List.
     * 
     * @invariant lobby != null
     * @param lobby Lobby
     * @ensures lobby is added to the list of lobbies and lobbies.size() is incremented by 1.
     */
    public void addToLobbies(Lobby lobby) {
        lobbies.add(lobby);
    }

    /**
     * Unique method which is used to return the serverObject, in order to call
     * Server specific methods.
     * 
     * @ensures object of type Server is returned
     * @return serverObject.
     */
    public static Server getServer() {
        if (serverObject == null) {
            serverObject = new Server();
        }
        return serverObject;
    }

    /**
     * This method will remove the player object from a lobby.
     * 
     * @invariant s != null
     * @param s Socket
     * @ensures boolean true or false is returned after removing a ServerPlayer from the lobby.
     * @return true || false
     */
    public synchronized boolean exitLobby(Socket s) {
        for (Lobby lobby : lobbies) {
            for (ServerPlayer player : lobby.getPlayerArrayList()) {
                if (player.getSocket() == s) {
                    lobby.removePlayer(player);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method will remove the corresponding ClientHandler object from the
     * lobby.
     * 
     * @invariant handle != null
     * @param handle ClientHandler
     * @ensures boolean true or false is returned after removing a ClientHandler object from the lobby
     * @return
     */
    public synchronized boolean removeHandlerFromLobby(ClientHandler handle) {
        for (Lobby lobby : lobbies) {
            for (ClientHandler handler : lobby.handlers) {
                if (handle == handler) {
                    lobby.removeHandler(handle);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Communication method used to communicate to all the clients in the lobby.
     * 
     * @invariant message != null && handle != null
     * @param message String
     * @param handle  ClientHandler
     * @ensures all clients in the lobby receive the String message
     * @throws IOException  when an I/O operation has failed
     * @throws AWTException when an abstract window toolkit exception has occurred
     */
    public void notifyAllClientsInLobby(String message, ClientHandler handle) throws IOException, AWTException {
        for (Lobby lobby : lobbies) {
            if (lobby.getHandlers().contains(handle)) {
                for (int i = 0; i < lobby.handlers.size(); i++) {
                    DataOutputStream dos = lobby.handlers.get(i).getDataOutput();
                    dos.writeUTF(message);
                    dos.flush();
                }
            }
        }
    }

    /**
     * Communicating method to communicate to all other clients in a lobby.
     * 
     * @invariant handler != null && message != null
     * @param handler ClientHandler
     * @param message String
     * @ensures all clients in the lobby receive the String message, except the client who accessed this method
     * @throws IOException  when an I/O operation has failed
     * @throws AWTException when an abstract window toolkit exception has occurred
     */
    public void notifyOtherClientsInLobby(ClientHandler handler, String message) throws IOException, AWTException {
        for (Lobby lobby : lobbies) {
            for (ClientHandler testhandler : lobby.getHandlers()) {
                if (lobby.getHandlers().contains(handler)) {
                    if (testhandler != handler) {
                        testhandler.getDataOutput().writeUTF(message);
                        testhandler.getDataOutput().flush();
                    }
                }
            }
        }
    }

    /**
     * The start method will iterate over all lobbies and start a game according to
     * the size of the lobby. During the for loops, it will add sockets to the right
     * game objects, as well as add the game object to the corresponding list.
     * 
     * @invariant handle != null
     * @param handle ClientHandler
     * @ensures the correct game object is started and the correct clients are notified
     * @throws AWTException when an abstract window toolkit exception has occurred
     * @throws IOException  when an I/O operation has failed
     */
    public void start(ClientHandler handle) throws AWTException, IOException {
        for (Lobby lobby : lobbies) {
            for (ClientHandler handler : lobby.getHandlers()) {
                if (lobby.getSize() == 2 && handle == handler) {
                    HumanPlayer player0 = new HumanPlayer(lobby.players.get(0).getName(), Marble.B, 2);
                    HumanPlayer player1 = new HumanPlayer(lobby.players.get(1).getName(), Marble.W, 2);
                    Game2P game2 = new Game2P(player0, player1);
                    Socket s1 = lobby.players.get(0).getSocket();
                    Socket s2 = lobby.players.get(1).getSocket();
                    game2.addSockets(s1);
                    game2.addSockets(s2);
                    twoPlayerGames.add(game2);
                    game2.start();
                    String color1 = player0.getName() + " is playing black";
                    String color2 = player1.getName() + " is playing white";
                    String totalboard = color1 + "\n" + color2 + "\n" + "\n" + game2.getBoard().printboardArray2()
                            + "\n" + "\n" + game2.getBoard().printBasicBoard2String() + "\n" + game2.getTurnServer();
                    notifyAllClientsInLobby(totalboard, handle);
                }
                if (lobby.getSize() == 3 && handle == handler) {
                    HumanPlayer player2 = new HumanPlayer(lobby.players.get(0).getName(), Marble.B, 3);
                    HumanPlayer player3 = new HumanPlayer(lobby.players.get(1).getName(), Marble.W, 3);
                    HumanPlayer player4 = new HumanPlayer(lobby.players.get(2).getName(), Marble.Y, 3);
                    Game3P game3 = new Game3P(player2, player3, player4);
                    Socket s3 = lobby.players.get(0).getSocket();
                    Socket s4 = lobby.players.get(1).getSocket();
                    Socket s5 = lobby.players.get(2).getSocket();
                    game3.addSockets(s3);
                    game3.addSockets(s4);
                    game3.addSockets(s5);
                    threePlayerGames.add(game3);
                    game3.start();
                    String color1 = player2.getName() + " is playing black";
                    String color2 = player3.getName() + " is playing white";
                    String color3 = player4.getName() + " is playing yellow";
                    String totalboard = color1 + "\n" + color2 + "\n" + color3 + "\n" + "\n"
                            + game3.getBoard().printboardArray2() + "\n" + "\n"
                            + game3.getBoard().printBasicBoard2String() + "\n" + game3.getTurnServer();
                    notifyAllClientsInLobby(totalboard, handle);
                }
                if (lobby.getSize() == 4 && handle == handler) {
                    HumanPlayer player2 = new HumanPlayer(lobby.players.get(0).getName(), Marble.B, 4);
                    HumanPlayer player3 = new HumanPlayer(lobby.players.get(1).getName(), Marble.Y, 4);
                    HumanPlayer player4 = new HumanPlayer(lobby.players.get(2).getName(), Marble.W, 4);
                    HumanPlayer player5 = new HumanPlayer(lobby.players.get(3).getName(), Marble.R, 4);
                    String teamname1 = lobby.players.get(0).getTeamName();
                    String teamname2 = lobby.players.get(1).getTeamName();
                    Game4P game4 = new Game4P(player2, player3, player4, player5, teamname1, teamname2);
                    Socket s0 = lobby.players.get(0).getSocket();
                    Socket s1 = lobby.players.get(1).getSocket();
                    Socket s2 = lobby.players.get(2).getSocket();
                    Socket s3 = lobby.players.get(3).getSocket();
                    game4.addSockets(s0);
                    game4.addSockets(s1);
                    game4.addSockets(s2);
                    game4.addSockets(s3);
                    fourPlayerGames.add(game4);
                    game4.start();
                    String color1 = player2.getName() + " is playing black";
                    String color2 = player3.getName() + " is playing yellow";
                    String color3 = player4.getName() + " is playing white";
                    String color4 = player5.getName() + " is playing red";
                    String totalboard = color1 + "\n" + color2 + "\n" + color3 + "\n" + color4 + "\n" + "\n"
                            + game4.getBoard().printboardArray2() + "\n" + "\n"
                            + game4.getBoard().printBasicBoard2String() + "\n" + game4.getTurnServer();
                    notifyAllClientsInLobby(totalboard, handle);
                }
            }
        }
    }

    /**
     * Specific method used to start a game against a computer.
     * 
     * @invariant call != null && handle != null
     * @param call   String
     * @param handle ClientHandler
     * @ensures a game is start against a computer player with intelligent behavior
     * @throws IOException  when an I/O operation has failed
     * @throws AWTException when an abstract window toolkit exception has occurred
     */
    public void startAgainstComputer(String call, ClientHandler handle) throws IOException, AWTException {
        for (Lobby lobby : lobbies) {
            for (ClientHandler handler : lobby.getHandlers()) {
                if (lobby.getSize() == 1 && handle == handler) {
                    HumanPlayer player0 = new HumanPlayer(lobby.players.get(0).getName(), Marble.B, 2);
                    ComputerPlayer cplayer = null;
                    if (call.contentEquals("smart")) {
                        Strategy smart = new SmartStrategy();
                        cplayer = new ComputerPlayer(Marble.W, smart);
                    } else {
                        Strategy naive = new NaiveStrategy();
                        cplayer = new ComputerPlayer(Marble.W, naive);
                    }
                    Game2P game2 = new Game2P(player0, cplayer);
                    Socket s1 = lobby.players.get(0).getSocket();
                    game2.addSockets(s1);
                    twoPlayerGames.add(game2);
                    game2.start();
                    String color1 = player0.getName() + " is playing black";
                    String color2 = "Computer is playing white";
                    String totalboard = color1 + "\n" + color2 + "\n" + "\n" + game2.getBoard().printboardArray2()
                            + "\n" + "\n" + game2.getBoard().printBasicBoard2String() + "\n" + game2.getTurnServer();
                    notifyAllClientsInLobby(totalboard, handle);
                }
            }
        }
    }

    /**
     * Move method to make a move for a client to his or her current game being
     * played. We use a socket as input in this method to compare with the sockets
     * in the game, to support multiple-games being played. The handler is also
     * given as input for the purpose of notifying other clients. During the move,
     * we check whether a game is finished or not, should it be finished, we notify
     * all clients in the lobby the result, let the server print out the result, and
     * remove the game in order for clients to start a new game safely. We use an
     * outer loop to prevent a CurrentModificationException, which is thrown when
     * trying to modify lists in a running program.
     * 
     * @invariant input != null && received != null && s != null && handler !=null
     * @param input   String
     * @param s       Socket
     * @param handler ClientHandler
     * @ensures a move is made on the correct board, and all clients are notified. Should the game be 
     * over, all correct clients are notified with the result
     * @throws IOException  when an I/O operation has failed
     * @throws AWTException when an abstract window toolkit exception has occurred
     */
    public void move(String input, String received, Socket s, ClientHandler handler) throws IOException, AWTException {
        outerloop: for (Game2P game2p : twoPlayerGames) {
            for (Socket socket : game2p.getSockets()) {
                if (s == socket) {
                    if (s == game2p.getSocket()) {
                        String move = game2p.convertInput(input);
                        String[] moveA = move.split(",");
                        if (!game2p.move(moveA[0], moveA[1], moveA[2])) {
                            handler.getDataOutput().writeUTF("u");
                        } else {
                            notifyAllClientsInLobby("\n" + received + "\n", handler);

                            if (game2p.checkIfGameOver()) {
                                notifyAllClientsInLobby(game2p.printResultProtocol(), handler);
                                game2p.printOutResult();
                                this.setLobbyReadyFalse(s);
                                removeTwoPlayerGame(game2p);
                                break outerloop;
                            }
                            if (game2p.getSockets().size() == 1) {
                                game2p.moveComputer();
                                if (game2p.checkIfGameOver()) {
                                    notifyAllClientsInLobby(game2p.printResultProtocol(), handler);
                                    game2p.printOutResult();
                                    removeTwoPlayerGame(game2p);
                                    break outerloop;
                                }
                            }
                        }
                    } else {
                        handler.getDataOutput().writeUTF("e;3;not your turn");
                    }
                }
            }
        }
        outerloop: for (Game3P game3p : threePlayerGames) {
            for (Socket socket : game3p.getSockets()) {
                if (s == socket) {
                    if (s == game3p.getSocket()) {
                        String move = game3p.convertInput(input);
                        String[] moveA = move.split(",");
                        if (!game3p.move(moveA[0], moveA[1], moveA[2])) {
                            handler.getDataOutput().writeUTF("u");
                        } else {
                            notifyAllClientsInLobby("\n" + received + "\n", handler);
                            if (game3p.checkIfGameOver()) {
                                notifyAllClientsInLobby(game3p.printResultProtocol(), handler);
                                game3p.printOutResult();
                                this.setLobbyReadyFalse(s);
                                removeThreePlayerGame(game3p);
                                break outerloop;
                            }
                        }
                    } else {
                        handler.getDataOutput().writeUTF("e;3;not your turn");
                    }
                }
            }
        }
        outerloop: for (Game4P game4p : fourPlayerGames) {
            for (Socket socket : game4p.getSockets()) {
                if (s == socket) {
                    if (s == game4p.getSocket()) {
                        String move = game4p.convertInput(input);
                        String[] moveA = move.split(",");
                        if (!game4p.move(moveA[0], moveA[1], moveA[2])) {
                            handler.getDataOutput().writeUTF("u");
                        } else {
                            notifyAllClientsInLobby("\n" + received + "\n", handler);
                            if (game4p.checkIfGameOver()) {
                                notifyAllClientsInLobby(game4p.printResultProtocol(), handler);
                                game4p.printOutResult();
                                this.setLobbyReadyFalse(s);
                                removeFourPlayerGame(game4p);
                                break outerloop;
                            }
                        }
                    } else {
                        handler.getDataOutput().writeUTF("e;3;not your turn");
                    }
                }
            }
        }
    }

    /**
     * Clients can call the hint method to receive a hint for a move for their
     * current game.
     * 
     * @invariant s != null && handler != null
     * @param s       Socket
     * @ensures a String hint is sent to the client asking for a hint
     * @param handler Clienthandler
     * @throws IOException  when an I/O operation has failed
     * @throws AWTException when an abstract window toolkit exception has occurred
     */
    public void hint(Socket s, ClientHandler handler) throws IOException, AWTException {
        String hint = "";
        for (Game2P game2p : twoPlayerGames) {
            for (Socket socket : game2p.getSockets()) {
                if (s == socket) {
                    if (s == game2p.getSocket()) {
                        hint = game2p.hintServer();
                        handler.getDataOutput().writeUTF(hint);
                    } else {
                        handler.getDataOutput().writeUTF("e;3;not your turn");
                    }
                }
            }
        }
        for (Game3P game3p : threePlayerGames) {
            for (Socket socket : game3p.getSockets()) {
                if (s == socket) {
                    if (s == game3p.getSocket()) {
                        hint = game3p.hintServer();
                        handler.getDataOutput().writeUTF(hint);
                    } else {
                        handler.getDataOutput().writeUTF("e;3;not your turn");
                    }
                }
            }
        }
        for (Game4P game4p : fourPlayerGames) {
            for (Socket socket : game4p.getSockets()) {
                if (s == socket) {
                    if (s == game4p.getSocket()) {
                        hint = game4p.hintServer();
                        handler.getDataOutput().writeUTF(hint);
                    } else {
                        handler.getDataOutput().writeUTF("e;3;not your turn");
                    }
                }
            }
        }
    }

    /**
     * return2Game will returns the corresponding Game2P object in which the
     * client's socket is located.
     * 
     * @invariant s != null
     * @param s Socket
     * @ensures a Game2P object is returned in which the socket is located
     * @return Game2P object
     */
    public Game2P return2Game(Socket s) {
        Game2P game2 = null;
        for (Game2P game2p : twoPlayerGames) {
            for (Socket socket : game2p.getSockets()) {
                if (s == socket) {
                    game2 = game2p.getGame();
                }
            }
        }
        return game2;
    }

    /**
     * return3Game will returns the corresponding Game3P object in which the
     * client's socket is located.
     * 
     * @invariant s != null
     * @param s Socket
     * @ensures a Game3P object is returned in which the socket is located
     * @return Game3P object
     */
    public Game3P return3Game(Socket s) {
        Game3P game3 = null;
        for (Game3P game3p : threePlayerGames) {
            for (Socket socket : game3p.getSockets()) {
                if (s == socket) {
                    game3 = game3p.getGame();
                }
            }
        }
        return game3;
    }

    /**
     * return4Game will returns the corresponding Game4P object in which the
     * client's socket is located.
     * 
     * @invariant s != null
     * @param s Socket
     * @ensures a Game4P object is returned in which the socket is located
     * @return Game4P object
     */
    public Game4P return4Game(Socket s) {
        Game4P game4 = null;
        for (Game4P game4p : fourPlayerGames) {
            for (Socket socket : game4p.getSockets()) {
                if (s == socket) {
                    game4 = game4p.getGame();
                }
            }
        }
        return game4;
    }

    /**
     * Remove a two player game from the twoPlayerGames list, we realize this with
     * an iterator to prevent a CurrentModificationException.
     * 
     * @invariant game2 != null
     * @param game2 Game2P
     * @ensures twoPlayerGames.size() is decremented by one
     */
    public synchronized void removeTwoPlayerGame(Game2P game2) {
        Iterator<Game2P> iter = twoPlayerGames.iterator();
        while (iter.hasNext()) {
            Game2P game2p = iter.next();
            if (game2p == game2) {
                iter.remove();
            }
        }
    }

    /**
     * Remove a three player game from the threePlayerGames list, we realize this
     * with an iterator to prevent a CurrentModificationException.
     * 
     * @invariant game3 != null
     * @param game3 Game3P
     * @ensures threePlayerGames.size() is decremented by one
     */

    public synchronized void removeThreePlayerGame(Game3P game3) {
        Iterator<Game3P> iter = threePlayerGames.iterator();
        while (iter.hasNext()) {
            Game3P game3p = iter.next();
            if (game3p == game3) {
                iter.remove();
            }
        }
    }

    /**
     * Remove a four player game from the fourPlayerGames list, we realize this with
     * an iterator to prevent a CurrentModificationException.
     * 
     * @invariant game4 != null
     * @param game4 Game4P
     * @ensures fourPlayerGames.size() is decremented by one
     */

    public synchronized void removeFourPlayerGame(Game4P game4) {
        Iterator<Game4P> iter = fourPlayerGames.iterator();
        while (iter.hasNext()) {
            Game4P game4p = iter.next();
            if (game4p == game4) {
                iter.remove();
            }
        }
    }

    /**
     * Boolean method to check whether a Client's socket is located in a two player
     * game.
     * 
     * @invariant s != null
     * @param s Socket
     * @ensures true when a client is in a Game2P object, false otherwise
     * @return true || false
     */
    public boolean isInTwoPlayerGame(Socket s) {
        for (Game2P game2p : twoPlayerGames) {
            for (Socket socket : game2p.getSockets()) {
                if (s == socket) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Boolean method to check whether a Client's socket is located in a three
     * player game.
     * 
     * @invariant s != null
     * @param s Socket
     * @ensures true when a client is in a Game3P object, false otherwise
     * @return true || false
     */
    public boolean isInThreePlayerGame(Socket s) {
        for (Game3P game3p : threePlayerGames) {
            for (Socket socket : game3p.getSockets()) {
                if (s == socket) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Boolean method to check whether a Client's socket is located in a four player
     * game.
     * 
     * @invariant s != null
     * @param s Socket
     * @ensures true when a client is in a Game4P object, false otherwise
     * @return true || false
     */
    public boolean isInFourPlayerGame(Socket s) {
        for (Game4P game4p : fourPlayerGames) {
            for (Socket socket : game4p.getSockets()) {
                if (s == socket) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return the current state of a board of the corresponding game object for a
     * client. We use a socket to compare in the twoPlayerGames list to check which
     * board belongs to which client.
     * 
     * @invariant s != null
     * @param s Socket
     * @ensures the correct board for the client is returned in String representation
     * @return String totalboard.
     */
    public String returnBoard(Socket s) {
        String totalboard = "";
        for (Game2P game2p : twoPlayerGames) {
            for (Socket socket : game2p.getSockets()) {
                if (s == socket) {
                    totalboard = game2p.getBoard().printboardArray2() + "\n" + "\n"
                            + game2p.getBoard().printBasicBoard2String() + "\n" + game2p.getTurnServer();
                }
            }
        }
        for (Game3P game3p : threePlayerGames) {
            for (Socket socket : game3p.getSockets()) {
                if (s == socket) {
                    totalboard = game3p.getBoard().printboardArray2() + "\n" + "\n"
                            + game3p.getBoard().printBasicBoard2String() + "\n" + game3p.getTurnServer();
                }
            }
        }
        for (Game4P game4p : fourPlayerGames) {
            for (Socket socket : game4p.getSockets()) {
                if (s == socket) {
                    totalboard = game4p.getBoard().printboardArray2() + "\n" + "\n"
                            + game4p.getBoard().printBasicBoard2String() + "\n" + game4p.getTurnServer();
                }
            }
        }
        return totalboard;
    }

    /**
     * Gets the ServerPlayer linked to the Socket s.
     * 
     * @invariant s != null
     * @param s Socket
     * @ensures the client's corresponding ServerPlayer object is returned
     * @return corresponding Serverplayer
     */
    public ServerPlayer getPlayer(Socket s) {
        for (ServerPlayer player : players) {
            if (s == player.getSocket()) {
                return player;
            }
        }
        return null;
    }

    /**
     * Sets a player in a lobby ready.
     * 
     * @invariant player != null && r != null
     * @param player ServerPlayer
     * @param r      boolean
     * @ensures the client's corresponding ServerPlayer's object ready status is set to true
     */
    public void setPlayerReady(ServerPlayer player, boolean r) {
        player.setReady(r);
    }

    /**
     * Deletes a game.
     * 
     * @invariant s != null && handler !=null
     * @param s       Socket
     * @param handler ClientHandler
     * @ensures the correct ServerPlayer will leave the lobby, the correct ClientHandler object will be removed from the lobby.
     * Also, notify the other clients about his leave and the correct game object in which the client was located is removed.
     * Moreover, all the other clients' ServerPlayerobjects ready status in the same lobby is set to false.
     * @throws IOException  when an I/O operation has failed
     * @throws AWTException when an abstract window toolkit exception has occurred
     */
    public void deleteGame(Socket s, ClientHandler handler) throws IOException, AWTException {
        System.out.println("Client " + s + " indicated to disconnect...");
        if (isInTwoPlayerGame(s) == true) {
            Game2P game2 = return2Game(s);
            int position = getPosition(s);
            String message = "g;disconnected;" + game2.getMarblePlayer(position);
            notifyOtherClientsInLobby(handler, message);
            for (Lobby lobby : lobbies) {
                if (lobby.players.contains(this.getPlayer(s))) {
                    for (ServerPlayer player : lobby.players) {
                        player.setReady(false);
                    }
                }
            }
            exitLobby(s);
            removeHandlerFromLobby(handler);
            removeTwoPlayerGame(return2Game(s));
        } else if (isInThreePlayerGame(s) == true) {
            Game3P game3 = return3Game(s);
            int position = getPosition(s);
            String message = "g;disconnected;" + game3.getMarblePlayer(position);
            notifyOtherClientsInLobby(handler, message);
            for (Lobby lobby : lobbies) {
                if (lobby.players.contains(this.getPlayer(s))) {
                    for (ServerPlayer player : lobby.players) {
                        player.setReady(false);
                    }
                }
            }
            exitLobby(s);
            removeHandlerFromLobby(handler);
            removeThreePlayerGame(return3Game(s));
        } else if (isInFourPlayerGame(s) == true) {
            Game4P game4 = return4Game(s);
            int position = getPosition(s);
            String message = "g;disconnected;" + game4.getMarblePlayer(position);
            notifyOtherClientsInLobby(handler, message);
            for (Lobby lobby : lobbies) {
                if (lobby.players.contains(this.getPlayer(s))) {
                    for (ServerPlayer player : lobby.players) {
                        player.setReady(false);
                    }
                }
            }
            exitLobby(s);
            removeHandlerFromLobby(handler);
            removeFourPlayerGame(return4Game(s));
        } else {
            exitLobby(s);
            removeHandlerFromLobby(handler);
        }
        System.out.println("Closing this connection.");
        s.close();
    }

    /**
     * Method dedicated to getting corresponding team names of server players in the
     * same team.
     * 
     * @invariant player1 != null && player2 != null && player3 != null && player4 != null
     * @param player1 Serverplayer
     * @param player2 Serverplayer
     * @param player3 Serverplayer
     * @param player4 Serverplayer
     * @ensures an array of the specified list of players is returned.
     * @return an arraylist of the altered arraylist
     */
    public ArrayList<ServerPlayer> switchArray(ServerPlayer player1, ServerPlayer player2, ServerPlayer player3,
            ServerPlayer player4) {
        ServerPlayer p1 = player1;
        ServerPlayer p2 = player2;
        ServerPlayer p3 = player3;
        ServerPlayer p4 = player4;
        ArrayList<ServerPlayer> arry = new ArrayList<>();
        arry.add(p1);
        arry.add(p2);
        arry.add(p3);
        arry.add(p4);
        return arry;
    }

    /**
     * Setting all players' ready status in a certain lobby to false.
     * 
     * @invariant socket != null
     * @param socket Socket
     * @ensures all ServerPlayer's ready status in lobby is set to false.
     */

    public void setLobbyReadyFalse(Socket socket) {

        for (Lobby lobby : lobbies) {
            if (lobby.players.contains(this.getPlayer(socket))) {
                for (ServerPlayer player : lobby.players) {
                    player.setReady(false);
                }
            }
        }

    }
}
