package dev.sijmen.adventofcode2020.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day11 {

    public static void main(String[] args) throws IOException {
        char[][] input =
                Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day11/input.txt"))
                        .map(String::toCharArray)
                        .toArray(c -> new char[c][c]);

        part1(clone(input));
        part2(clone(input));
    }

    private static void part1(char[][] state) {
        int changes;
        do {
            changes = 0;
            char[][] next = clone(state);

            for (int row = 0; row < state.length; row++) {
                for (int col = 0; col < state[row].length; col++) {

                    short occupiedAdjacentSeats = countOccupiedAdjacentSeats(state, row, col);

                    //If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
                    if (state[row][col] == 'L' && occupiedAdjacentSeats == 0) {
                        next[row][col] = '#';
                        changes++;
                    }

                    //If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
                    if (state[row][col] == '#' && occupiedAdjacentSeats >= 4) {
                        next[row][col] = 'L';
                        changes++;
                    }
                }
            }

            state = next;
        } while (changes > 0);

        System.out.println("Part 1: " + countOccupiedSeats(state));
    }

    private static void part2(char[][] state) {
        int changes;
        do {
            changes = 0;
            char[][] next = clone(state);

            for (int row = 0; row < state.length; row++) {
                for (int col = 0; col < state[row].length; col++) {

                    short occupiedAdjacentSeats = countOccupiedEyelineSeats(state, row, col);

                    //If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
                    if (state[row][col] == 'L' && occupiedAdjacentSeats == 0) {
                        next[row][col] = '#';
                        changes++;
                    }

                    //If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
                    if (state[row][col] == '#' && occupiedAdjacentSeats >= 5) {
                        next[row][col] = 'L';
                        changes++;
                    }
                }
            }


            state = next;
//            print(state);
        } while (changes > 0);

        System.out.println("Part 2: " + countOccupiedSeats(state));
    }

    private static short countOccupiedSeats(char[][] input) {
        short occupiedSeats = 0;
        for (char[] chars : input) {
            for (char aChar : chars) {
                if (aChar == '#') {
                    occupiedSeats++;
                }
            }
        }
        return occupiedSeats;
    }

    private static short countOccupiedEyelineSeats(char[][] input, int rowQ, int colQ) {
        short occupiedSeats = 0;
        for (int row = Math.max(0, rowQ - 1); row < Math.min(rowQ + 2, input.length); row++) {
            for (int col = Math.max(0, colQ - 1); col < Math.min(colQ + 2, input[row].length); col++) {
                if (row == rowQ && col == colQ) {
                    continue;
                }

                // directional thing
                int dRow = row - rowQ;
                int dCol = col - colQ;

                for (int mul = 0; ; mul++) {
                    int aRow = row + mul * dRow;
                    int aCol = col + mul * dCol;

                    if(aRow < 0 || aCol < 0 || aRow >= input.length || aCol >= input[aRow].length) {
                        break;
                    }
                    if (input[aRow][aCol] == 'L') {
                        break;
                    }
                    if (input[aRow][aCol] == '#') {
                        occupiedSeats++;
                        break;
                    }
                }
            }
        }
        return occupiedSeats;
    }

    private static short countOccupiedAdjacentSeats(char[][] input, int rowQ, int colQ) {
        short occupiedSeats = 0;
        for (int row = Math.max(0, rowQ - 1); row < Math.min(rowQ + 2, input.length); row++) {
            for (int col = Math.max(0, colQ - 1); col < Math.min(colQ + 2, input[row].length); col++) {
                if (row == rowQ && col == colQ) {
                    continue;
                }
                if (input[row][col] == '#') {
                    occupiedSeats++;
                }
            }
        }
        return occupiedSeats;
    }

    private static void print(char[][] input) {
        for (char[] chars : input) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static char[][] clone(char[][] input) {
        char[][] output = input.clone();
        for (int i = 0; i < output.length; i++) {
            output[i] = input[i].clone();
        }
        return output;
    }

}
