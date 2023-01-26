import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Random;

public class ToyFactory {
    private static final int NUM_SPACES = 36;
    private static final int MIN_damaged_BONUSES = 9;

    private Digraph board;
    private int numPlayers;
    private int[] playerPositions;
    private int[] factoryParts;
    private boolean[] damaged;
    private boolean[] bonuses;
    private boolean[] roadBlocks;
    private String[] Conditions;
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

        // get number of damaged and bonuses
        int numdamagedBonuses = getNumdamagedBonuses();

        // initialize player positions, fuel, and damaged/bonuses arrays
        playerPositions = new int[numPlayers];
        factoryParts = new int[numPlayers];
        damaged = new boolean[NUM_SPACES];
        bonuses = new boolean[NUM_SPACES];
        roadBlocks = new boolean[NUM_SPACES];
        Conditions = new String[NUM_SPACES];

        for (int i = 0; i < numPlayers; i++) {
            playerPositions[i] = 0;
            factoryParts[i] = 5;
        }

        for (int i = 0; i < numdamagedBonuses; i++) {
            int trapBonusSpace = rand.nextInt(NUM_SPACES);
            if (!damaged[trapBonusSpace] && !bonuses[trapBonusSpace]) {
                int trapBonus = rand.nextInt(2);
                if (trapBonus == 0) {
                    damaged[trapBonusSpace] = true;
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
            Conditions[i] = rand.nextBoolean() ? "day" : "night";
        }
    }

    boolean cond = true;

    public void play() {
        while (cond) {
            int numTurns = 100;
            for (int turn = 0; turn < numTurns; turn++) {
                for (int i = 0; i < numPlayers; i++) {
                    StdOut.println("Employee " + (i + 1) + " is at space " + playerPositions[i]);
                    int roll = rand.nextInt(6) + 1;
                    StdOut.println("Employee " + (i + 1) + " rolled a " + roll);
                    playerPositions[i] += roll;
                    if (playerPositions[i] >= NUM_SPACES) {
                        playerPositions[i] = NUM_SPACES - 1;
                    }
                    if (factoryParts[i] > 0) {
                        factoryParts[i]--;
                    }
                    StdOut.println("Employee " + (i + 1) + " is at space " + playerPositions[i]);
                    if (damaged[playerPositions[i]]) {
                        StdOut.println("Employee " + (i + 1) + " encountered a damaged at space " + playerPositions[i]);
                    } else if (bonuses[playerPositions[i]]) {
                        StdOut.println("Employee " + (i + 1) + " encountered a bonus at space " + playerPositions[i]);
                    }
                    if (roadBlocks[playerPositions[i]]) {
                        StdOut.println("Employee " + (i + 1) + " encountered a stop at space " + playerPositions[i]);
                    }
                    if (Conditions[playerPositions[i]].equals("day")) {
                        StdOut.println("Employee " + (i + 1) + " building at day " + playerPositions[i]);
                    }
                    if (Conditions[playerPositions[i]].equals("night")) {
                        StdOut.println("Employee " + (i + 1) + " building at night " + playerPositions[i]);
                    }
                    if (factoryParts[i] == 0) {
                        StdOut.println("Employee " + (i + 1) + " ran out of parts!");
                        cond = false;
                    }
                    if (playerPositions[i] == NUM_SPACES - 1) {
                        StdOut.println("Employee " + (i + 1) + " reached building the factory!");
                        cond = false;
                    }
                }
            }
        }
    }

    private int getNumPlayers() {
        StdOut.print("Enter number of Employees (1-4): ");
        int players = StdIn.readInt();
        while (players < 1 || players > 4) {
            StdOut.print("Invalid input. Enter number of Employeesclear" +
                    "s (1-4): ");
            players = StdIn.readInt();
        }
        return players;
    }

    private int getNumdamagedBonuses() {
        StdOut.print("Enter number of damaged and bonuses (minimum 9): ");
        int damagedBonuses = StdIn.readInt();
        while (damagedBonuses < MIN_damaged_BONUSES) {
            StdOut.print("Invalid input. Enter number of damaged and bonuses (minimum 9): ");
            damagedBonuses = StdIn.readInt();
        }
        return damagedBonuses;
    }

    public static void main(String[] args) {
        ToyFactory game = new ToyFactory();
        game.initBoard();
        game.play();
    }
}

