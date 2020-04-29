package tests;

import abalone.ComputerPlayer;
import abalone.Game2P;
import abalone.Game3P;
import abalone.Game4P;
import abalone.HumanPlayer;
import abalone.Marble;
import abalone.NaiveStrategy;
import abalone.Player;
import abalone.SmartStrategy;
import abalone.Strategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Abalone {

    /**
     * A method to test the full game without networking.
     * @throws IOException when an I/O exception comes up
     */
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean name = false;
        Strategy smart = new SmartStrategy();
        Strategy naive = new NaiveStrategy();
        boolean again;
        again = true;
        while (again) {
            System.out.println("Who is playing? )");
            System.out.println("For two players, input = 'name1' 'name2'");
            System.out.println("For three players, input = 'name1' 'name2' 'name3'");
            System.out.println("For four players, input = 'name1' 'name2' 'name3' 'name4'");
            System.out.println("For smart computer, put in '<s>'. For less smart computer, put in '<n>'");
            String input = reader.readLine();
            String[] arr = input.split(" ");
            int i = arr.length;
            while (!name) {
                if (i > 1 && i < 5) {
                    break;
                }
                System.out.println("Please follow the instructions below! \n");
                System.out.println("Who is playing? )");
                System.out.println("For two players, input = 'name1' 'name2'");
                System.out.println("For three players, input = 'name1' 'name2' 'name3'");
                System.out.println("For four players, input = 'name1' 'name2' 'name3' 'name4'");
                System.out.println("For smart computer, put in '<s>'. For less smart computer, put in '<n>'");
                input = reader.readLine();
                arr = input.split(" ");
            }

            if (arr.length == 2) {
                Player player1;
                Player player2;
                if (arr[0].equals("<s>") || arr[1].equals("<s>")) {
                    player1 = new HumanPlayer(arr[0], Marble.B, 2);
                    player2 = new ComputerPlayer(Marble.W, smart);
                } else if (arr[0].equals("<n>") || arr[1].equals("<n>")) {
                    player1 = new HumanPlayer(arr[0], Marble.B, 2);
                    player2 = new ComputerPlayer(Marble.W, naive);
                } else {
                    player1 = new HumanPlayer(arr[0], Marble.B, 2);
                    player2 = new HumanPlayer(arr[1], Marble.W, 2);
                }
                Player[] players = { player1, player2 };
                Game2P game = new Game2P(player1, player2);
                System.out.println(player1.getName() + " is playing Black");
                System.out.println(player2.getName() + " is playing White");
                game.start();
                if (player2 instanceof ComputerPlayer) {
                    while (!game.checkIfGameOver()) {
                        Player currentPlayer = players[0];
                        Player opponent = players[1];
                        String question = "> What is your choice, " + currentPlayer.getName() + "? ";
                        System.out.println(question);
                        String choice;

                        choice = reader.readLine();
                        String[] array = choice.split(",");
                        while (array.length != 3) {
                            if (choice.equals("hint") || choice.equals("Hint")) {
                                String hint = smart.determineMove(game.getBoard(), currentPlayer, opponent);
                                System.out.println(hint);
                            } else {
                                System.out.println("Not valid input, please use a;1,a;1,D");
                            }
                            choice = reader.readLine();
                            array = null;
                            array = choice.split(",");
                        }

                        while (currentPlayer.determineMove(game.getBoard(), opponent, choice) != choice) {
                            System.out.println("Invalid Move");
                            System.out.println(question);
                            choice = reader.readLine();
                            array = null;
                            array = choice.split(",");
                            while (array.length != 3) {
                                if (choice.equals("hint") || choice.equals("Hint")) {
                                    String hint = smart.determineMove(game.getBoard(), currentPlayer, opponent);
                                    System.out.println(hint);
                                } else {
                                    System.out.println("Not valid input, please use a;1,a;1,D");
                                }
                                choice = reader.readLine();
                                array = null;
                                array = choice.split(",");
                            }

                        }
                        String first = array[0];
                        String second = array[1];
                        String destination = array[2];
                        game.move(first, second, destination);
                        game.moveComputer();
                    }
                    System.out.println(game.printResult());

                } else {
                    while (!game.checkIfGameOver()) {

                        String question = "> What is your choice, " + game.getCurrentPlayer().getName() + "? ";
                        System.out.println(question);

                        String choice;

                        choice = reader.readLine();
                        String[] array = choice.split(",");
                        while (array.length != 3) {
                            if (choice.equals("hint") || choice.equals("Hint")) {
                                System.out.println(game.hint());
                            } else {
                                System.out.println("Not valid input, please use a;1,a;1,D");
                            }
                            choice = reader.readLine();
                            array = null;
                            array = choice.split(",");
                        }

                        while (game.getCurrentPlayer().determineMove(game.getBoard(), game.getOpponentPlayer(),
                                choice) != choice) {
                            System.out.println("Invalid Move");
                            System.out.println(question);
                            choice = reader.readLine();
                            array = null;
                            array = choice.split(",");
                            while (array.length != 3) {
                                if (choice.equals("hint") || choice.equals("Hint")) {
                                    System.out.println(game.hint());
                                } else {
                                    System.out.println("Not valid input, please use a;1,a;1,D");
                                }
                                choice = reader.readLine();
                                array = null;
                                array = choice.split(",");
                            }

                        }
                        String first = array[0];
                        String second = array[1];
                        String destination = array[2];
                        game.move(first, second, destination);
                    }
                    System.out.println(game.printResult());
                }
            } else if (arr.length == 3) {
                HumanPlayer player1 = new HumanPlayer(arr[0], Marble.B, 3);
                HumanPlayer player2 = new HumanPlayer(arr[1], Marble.W, 3);
                HumanPlayer player3 = new HumanPlayer(arr[2], Marble.Y, 3);
                System.out.println(player1.getName() + " is playing Black");
                System.out.println(player2.getName() + " is playing White");
                System.out.println(player3.getName() + " is playing Yellow");
                Game3P game = new Game3P(player1, player2, player3);
                game.start();
                while (!game.checkIfGameOver()) {
                    String question = "> What is your choice, " + game.getCurrentPlayer().getName() + "? ";
                    System.out.println(question);

                    String choice;

                    choice = reader.readLine();
                    String[] array = choice.split(",");
                    while (array.length != 3) {
                        if (choice.equals("hint") || choice.equals("Hint")) {
                            System.out.println(game.hint());
                        } else {
                            System.out.println("Not valid input, please use a;1,a;1,D");
                        }
                        choice = reader.readLine();
                        array = null;
                        array = choice.split(",");
                    }

                    while (game.getCurrentPlayer().determineMove(game.getBoard(), game.getOpponentPlayer(),
                            choice) != choice) {
                        System.out.println("Invalid Move");
                        System.out.println(question);
                        choice = reader.readLine();
                        array = null;
                        array = choice.split(",");
                        while (array.length != 3) {
                            if (choice.equals("hint") || choice.equals("Hint")) {
                                System.out.println(game.hint());
                            } else {
                                System.out.println("Not valid input, please use a;1,a;1,D");
                            }
                            choice = reader.readLine();
                            array = null;
                            array = choice.split(",");
                        }

                    }
                    String first = array[0];
                    String second = array[1];
                    String destination = array[2];
                    game.move(first, second, destination);
                }
                System.out.println(game.printResult());
            } else if (arr.length == 4) {
                HumanPlayer player1 = new HumanPlayer(arr[0], Marble.B, 4);
                HumanPlayer player2 = new HumanPlayer(arr[1], Marble.Y, 4);
                HumanPlayer player3 = new HumanPlayer(arr[2], Marble.W, 4);
                HumanPlayer player4 = new HumanPlayer(arr[3], Marble.R, 4);
                System.out.println(player1.getName() + " is playing Black");
                System.out.println(player2.getName() + " is playing Yellow");
                System.out.println(player3.getName() + " is playing White");
                System.out.println(player4.getName() + " is playing Red");
                System.out.println("The teams are the following:");
                System.out.println("Team 1: " + player1.getName() + " + " + player3.getName());
                System.out.println("Team 2: " + player2.getName() + " + " + player4.getName());
                Game4P game = new Game4P(player1, player2, player3, player4, "1", "2");
                game.start();
                while (!game.checkIfGameOver()) {
                    String question = "> What is your choice, " + game.getCurrentPlayer().getName() + "? ";
                    System.out.println(question);

                    String choice;

                    choice = reader.readLine();
                    String[] array = choice.split(",");
                    while (array.length != 3) {
                        if (choice.equals("hint") || choice.equals("Hint")) {
                            System.out.println(game.hint());
                        } else {
                            System.out.println("Not valid input, please use a;1,a;1,D");
                        }
                        choice = reader.readLine();
                        array = null;
                        array = choice.split(",");
                    }

                    while (game.getCurrentPlayer().determineMove(game.getBoard(), game.getOpponentPlayer(),
                            choice) != choice) {
                        System.out.println("Invalid Move");
                        System.out.println(question);
                        choice = reader.readLine();
                        array = null;
                        array = choice.split(",");
                        while (array.length != 3) {
                            if (choice.equals("hint") || choice.equals("Hint")) {
                                System.out.println(game.hint());
                            } else {
                                System.out.println("Not valid input, please use a;1,a;1,D");
                            }
                            choice = reader.readLine();
                            array = null;
                            array = choice.split(",");
                        }

                    }
                    String first = array[0];
                    String second = array[1];
                    String destination = array[2];
                    game.move(first, second, destination);
                    game.update();
                }
                System.out.println(game.printResult());
            }
            System.out.println("\n> Play another time? (y/n)");
            String answer = reader.readLine();
            if (!answer.equals("y")) {
                again = false;
            }
        }
    }
}