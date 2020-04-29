package server;

import java.awt.AWTException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


class ClientHandler extends Thread {

    final DataInputStream datainput;
    final DataOutputStream dataoutput;
    final Socket socket;

    public ClientHandler(Socket s, DataInputStream datainput, DataOutputStream dataoutput) throws IOException {
        this.socket = s;
        this.datainput = datainput;
        this.dataoutput = dataoutput;
    }

    /**
     * Return the DataOutputStream object of this ClientHandler.
     * 
     * @return DataOutputStream
     */
    public DataOutputStream getDataOutput() {
        return dataoutput;
    }

    /**
     * Return the DataInputStream object of this ClientHandler.
     * 
     * @return DataInputStream
     */
    public DataInputStream getDataInput() {
        return datainput;
    }

    /**
     * Run method which receives user input of a client, and handles accordingly.
     */
    @Override
    public void run() {
        String input;
        try {
            boolean test = false;
            while (!test) {
                dataoutput.writeUTF("Please mention the specified handshake");
                input = datainput.readUTF();
                String[] splitted = input.split(";");
                if (splitted.length > 1 && splitted[1].contentEquals("other3V1.3") && splitted[0].contentEquals("h")) {
                    test = true;
                } else {
                    dataoutput.writeUTF("Incorrect version, try " + "h;other3V1.3");
                }
            }
            dataoutput.writeUTF("h");
            if (Server.getServer().lobbies.isEmpty()) {
                dataoutput.writeUTF("---EOT---");
            }
            for (Lobby lobby : Server.getServer().lobbies) {
                dataoutput.writeUTF(lobby.getPlayers());
            }
            while (true) {
                dataoutput.writeUTF("What would you like to do..?\n" + "Type help for the help menu..");
                input = datainput.readUTF();
                if (input.contains(";")) {
                    String[] handle = input.split(";");
                    switch (handle[0]) {
                        case "r":
                            Server.getServer().startAgainstComputer(handle[1], this);
                            break;
                        case "j":
                            if (handle.length == 4) {
                                String lobbyname = handle[1];
                                String playername = handle[2];
                                String teamname = handle[3];
                                boolean exists = false;
                                Lobby lobby2 = null;
                                for (Lobby lobby : Server.getServer().lobbies) {
                                    if (lobbyname.contentEquals(lobby.getName())) {
                                        exists = true;
                                        lobby2 = lobby;
                                    }
                                }
                                if (!exists) {
                                    lobby2 = new Lobby(lobbyname);
                                    Server.getServer().addToLobbies(lobby2);
                                }
                                if (lobby2.getSize() == 4) {
                                    dataoutput.writeUTF("e;3;lobby is full");
                                    break;
                                }
                                exists = false;
                                for (ServerPlayer player : lobby2.getPlayerArrayList()) {
                                    if (player.getName().contentEquals(playername)
                                        && player.getTeamName().contentEquals(teamname)) {
                                        exists = true;
                                    }
                                }
                                if (exists) {
                                    dataoutput.writeUTF("e;3;playername with teamname already exists");
                                    break;
                                }
                                ServerPlayer player = null;
                                if (Server.getServer().getPlayer(socket) == null) {
                                    player = new ServerPlayer(playername, teamname, socket);
                                } else {
                                    player = Server.getServer().getPlayer(socket);
                                    player.setName(playername);
                                    player.setTeamName(teamname);
                                }
                                Server.getServer().addToPlayerlist(player);
                                lobby2.addToPlayers(player);
                                lobby2.addToHandlers(this);
                                dataoutput.writeUTF("You've joined the lobby");
                                Server.getServer().notifyOtherClientsInLobby(this, "j;" + playername + ";" + teamname);
                                for (ServerPlayer players : lobby2.players) {
                                    players.setReady(false);
                                }
                            }
                            break;
                        case "t":
                            Server.getServer().notifyOtherClientsInLobby(this, handle[1]);
                            break;
                        case "m":
                            if (handle.length == 4) {
                                String[] one = handle[1].split("");
                                String[] two = handle[2].split("");
                                String[] three = handle[3].split("");
                                if (one.length == 2 && two.length == 2 && three.length == 2) {
                                    String crd1 = one[0] + ";" + one[1];
                                    String crd2 = two[0] + ";" + two[1];
                                    String crd3 = three[0] + ";" + three[1];
                                    String move = "m;" + crd1 + ";" + crd2 + ";" + crd3;
                                    Server.getServer().move(move, input, this.socket, this);
                                    String board = Server.getServer().returnBoard(this.socket);
                                    Server.getServer().notifyAllClientsInLobby(board, this);
                                } else {
                                    dataoutput.writeUTF("e;2;wrong input");
                                }
                            } else {
                                dataoutput.writeUTF("e;2;wrong input");
                            }
                            break;
                        default:
                            dataoutput.writeUTF("");
                            break;
                    }
                } else {
                    switch (input) {
                        default:
                            dataoutput.writeUTF("");
                            break;
                        case "hint":
                            Server.getServer().hint(this.socket, this);
                            break;
                        case "players":
                            String players = "p;";
                            if (Server.getServer().players.isEmpty()) {
                                dataoutput.writeUTF("No players currently.");
                                break;
                            }
                            for (ServerPlayer player : Server.getServer().players) {
                                players += (player.getName() + ";");
                            }
                            dataoutput.writeUTF(players);
                            break;
                        case "l":
                            if (Server.getServer().lobbies.isEmpty()) {
                                dataoutput.writeUTF("---EOT---");
                            }
                            for (Lobby lobby : Server.getServer().lobbies) {
                                dataoutput.writeUTF(lobby.getPlayers());
                            }
                            break;
                        case "help":
                            dataoutput.writeUTF("j;<lobbyname>;<Playername>;<Teamname> to get in a lobby\n"
                                + "l to see which lobbies are available..\n"
                                + "r to indicate you are ready to start\n"
                                + "m;<coordinates of marble 1>;<coordinates of marble 2>;<destination of marble 1>" 
                                + " to move the coherent marbles..\n"
                                + "hint when it's your turn to receive a hint.. \n"
                                + "players to get a list of all players in the server.. \n"
                                + "x when you want to leave a game/lobby/server");
                            break;
                        case "x":
                            if (Server.getServer().lobbies.size() >= 1) {
                                boolean inlobby = false;
                                for (Lobby lobby : Server.getServer().lobbies) {
                                    for (ClientHandler handler : lobby.handlers) {
                                        if (this == handler) {
                                            inlobby = true;
                                        }
                                    }
                                }
                                if (inlobby) {
                                    if (Server.getServer().isInTwoPlayerGame(this.socket)
                                        || Server.getServer().isInThreePlayerGame(this.socket)
                                        || Server.getServer().isInFourPlayerGame(this.socket)) {
                                        Server.getServer().deleteGame(this.socket, this);
                                        break;
                                    } else {
                                        Server.getServer().notifyOtherClientsInLobby(this,
                                            "x;" + Server.getServer().getPlayer(socket).getName() + ";"
                                                    + Server.getServer().getPlayer(socket).getTeamName());
                                        Server.getServer().setLobbyReadyFalse(socket);
                                        if (Server.getServer().exitLobby(socket) == true
                                            && Server.getServer().removeHandlerFromLobby(this) == true) {
                                            dataoutput.writeUTF("Player removed succesfully");
                                        } else {
                                            dataoutput.writeUTF("datainputconnecting.. (final else statement)");
                                        }
                                    }
                                    break;
                                } else {
                                    System.out.println("Client datainputconnected");
                                    this.socket.close();
                                    datainput.close();
                                    dataoutput.close();
                                    break;
                                }
                            } else {
                                System.out.println("Client datainputconnected");
                                this.socket.close();
                                datainput.close();
                                dataoutput.close();
                                break;
                            }
                        case "r":
                            if (Server.getServer().lobbies.size() >= 1) {
                                Lobby lobby3 = null;
                                boolean inlobby = false;
                                for (Lobby lobby : Server.getServer().lobbies) {
                                    for (ClientHandler handler : lobby.handlers) {
                                        if (this == handler) {
                                            inlobby = true;
                                            lobby3 = lobby;
                                        }
                                    }
                                }
                                if (inlobby && lobby3.players.size() > 1) {
                                    Server.getServer().setPlayerReady(Server.getServer().getPlayer(socket), true);
                                    Server.getServer()
                                        .notifyAllClientsInLobby(
                                                "r;" + Server.getServer().getPlayer(this.socket).getName() + ";"
                                                        + Server.getServer().getPlayer(this.socket).getTeamName(),
                                                this);
                                    boolean allready = true;
                                    for (ServerPlayer player : lobby3.players) {
                                        if (!player.isReady()) {
                                            allready = false;
                                        }
                                    }
                                    if (allready) {
                                        if (lobby3.players.size() == 4) {
                                            String teamname1 = lobby3.players.get(0).getTeamName();
                                            String teamname2 = lobby3.players.get(1).getTeamName();
                                            String teamname3 = lobby3.players.get(2).getTeamName();
                                            String teamname4 = lobby3.players.get(3).getTeamName();
                                            boolean okay = false;
                                            if (teamname1.contentEquals(teamname2) && teamname3.contentEquals(teamname4)
                                                && !teamname1.contentEquals(teamname3)) {
                                                okay = true;
                                            } else if (teamname1.contentEquals(teamname3)
                                                && teamname2.contentEquals(teamname4)
                                                && !teamname1.contentEquals(teamname4)) {
                                                okay = true;
                                            } else if (teamname1.contentEquals(teamname4)
                                                && teamname2.contentEquals(teamname3)
                                                && !teamname1.contentEquals(teamname3)) {
                                                okay = true;
                                            }
                                            if (okay == false) {
                                                Server.getServer().notifyAllClientsInLobby("e;3;teams do not match",
                                                        this);
                                                for (ServerPlayer player : lobby3.players) {
                                                    player.setReady(false);
                                                }
                                                break;
                                            }
                                            if (teamname1.contentEquals(teamname2) && teamname3.contentEquals(teamname4)
                                                && !teamname1.contentEquals(teamname3)) {
                                                lobby3.players = Server.getServer().switchArray(lobby3.players.get(0),
                                                    lobby3.players.get(2), lobby3.players.get(1),
                                                    lobby3.players.get(3));
                                            } else if (teamname1.contentEquals(teamname4)
                                                && teamname2.contentEquals(teamname3)
                                                && !teamname1.contentEquals(teamname3)) {
                                                lobby3.players = Server.getServer().switchArray(lobby3.players.get(0),
                                                    lobby3.players.get(2), lobby3.players.get(3),
                                                    lobby3.players.get(1));
                                            }
                                        }
                                        Server.getServer().notifyAllClientsInLobby(lobby3.getStartPlayers(), this);
                                        Server.getServer().start(this);
                                    }
                                } else if (inlobby && lobby3.players.size() == 1) {
                                    dataoutput.writeUTF("e;3;only one player in lobby");
                                    dataoutput.writeUTF("hint: if you want to play against AI, "
                                            + "try typing r;smart or r;naive");
                                } else {
                                    dataoutput.writeUTF("e;3;no lobby to be ready for");
                                }
                            } else {
                                dataoutput.writeUTF("e;3;no lobby to be ready for");
                            }
                    }
                }
            }
        } catch (AWTException | IOException e) {
            System.out.println("A client exited, server is still running.");
            try {
                Server.getServer().deleteGame(socket, this);
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        }
    }
}
