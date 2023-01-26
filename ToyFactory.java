import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Random;

public class ToyFactory {
    private static final int NUM_SPACES = 36;
    private static final int MIN_TRAPS_BONUSES = 9;

    private Digraph board;
    private int numPlayers;
    private int[] playerPositions;
    private int[] playerFuel;
    private boolean[] traps;
    private boolean[] bonuses;
    private boolean[] roadBlocks;
    private String[] weatherConditions;
    private Random rand;

    public ToyFactory() {
        rand = new Random();
    }

    public void initBoard() {
        // read in node table from text file
        In in = new In("toy_factory_table.txt");
        board = new Digraph(in);

        // get number of players
        numPlayers = getNumPlayers();

        // get number of traps and bonuses
        int numTrapsBonuses = getNumTrapsBonuses();

        // initialize player positions, fuel, and traps/bonuses arrays
        playerPositions = new int[numPlayers];
        playerFuel = new int[numPlayers];
        traps = new boolean[NUM_SPACES];
        bonuses = new boolean[NUM_SPACES];
        roadBlocks = new boolean[NUM_SPACES];
        weatherConditions = new String[NUM_SPACES];

        for (int i = 0; i < numPlayers; i++) {
            playerPositions[i] = 0;
            playerFuel[i] = 5;
        }

        for (int i = 0; i < numTrapsBonuses; i++) {
            int trapBonusSpace = rand.nextInt(NUM_SPACES);
            if (!traps[trapBonusSpace] && !bonuses[trapBonusSpace]) {
                int trapBonus = rand.nextInt(2);
                if (trapBonus == 0) {
                    traps[trapBonusSpace] = true;
                } else {
                    bonuses[trapBonusSpace] = true;
                }
            } else {
                i--;
            }
        }
        // add road blocks
        for (int i = 0; i < NUM_SPACES; i++) {
            roadBlocks[i] = rand.nextBoolean();
        }
        // add weather conditions
        for (int i = 0; i < NUM_SPACES; i++) {
            weatherConditions[i] = rand.nextBoolean() ? "Sunny" : "Rainy";
        }
    }

    public void play() {
        boolean condition = true;
        while (condition) {
            int numTurns = 100;
            for (int turn = 0; turn < numTurns; turn++) {
                for (int i = 0; i < numPlayers; i++) {
                    StdOut.println("Player " + (i + 1) + " is at space " + playerPositions[i]);
                    int roll = rand.nextInt(6) + 1;
                    StdOut.println("Player " + (i + 1) + " rolled a " + roll);
                    playerPositions[i] += roll;
                    if (playerPositions[i] >= NUM_SPACES) {
                        playerPositions[i] = NUM_SPACES - 1;
                    }
                    if (playerFuel[i] > 0) {
                        playerFuel[i]--;
                    }
                    StdOut.println("Player " + (i + 1) + " is at space " + playerPositions[i]);
                    if (traps[playerPositions[i]]) {
                        StdOut.println("Player " + (i + 1) + " encountered a trap at space " + playerPositions[i]);
                    } else if (bonuses[playerPositions[i]]) {
                        StdOut.println("Player " + (i + 1) + " encountered a bonus at space " + playerPositions[i]);
                    }
                    if (roadBlocks[playerPositions[i]]) {
                        StdOut.println("Player " + (i + 1) + " encountered a road block at space " + playerPositions[i]);
                    }
                    if (weatherConditions[playerPositions[i]].equals("Rainy")) {
                        StdOut.println("Player " + (i + 1) + " encountered Rainy weather at space " + playerPositions[i]);
                    }
                    if (weatherConditions[playerPositions[i]].equals("Sunny")) {
                        StdOut.println("Player " + (i + 1) + " encountered Sunny weather at space " + playerPositions[i]);
                    }
                    if (playerFuel[i] == 0) {
                        StdOut.println("Player " + (i + 1) + " ran out of fuel!");
                        condition = false;
                    } else if (playerPositions[i] == NUM_SPACES - 1) {
                        StdOut.println("Player " + (i + 1) + " reached the destination!");
                        condition = false;

                    }
                }
            }
        }
    }

    private int getNumPlayers() {
        StdOut.print("Enter number of players (1-4): ");
        int players = StdIn.readInt();
        while (players < 1 || players > 4) {
            StdOut.print("Invalid input. Enter number of players (1-4): ");
            players = StdIn.readInt();
        }
        return players;
    }

    private int getNumTrapsBonuses() {
        StdOut.print("Enter number of Turns and Conditions: ");
        int trapsBonuses = StdIn.readInt();
        while (trapsBonuses < MIN_TRAPS_BONUSES) {
            StdOut.print("Invalid input. Enter number of traps and bonuses (minimum 9): ");
            trapsBonuses = StdIn.readInt();
        }
        return trapsBonuses;
    }

    public static void main(String[] args) {
        ToyFactory game = new ToyFactory();
        game.initBoard();
        game.play();
    }
}

