package dev.sijmen.adventofcode2020.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day12 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day12/input.txt"))
                .collect(Collectors.toList());

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        int x = 0; //positive=north, negative=south
        int y = 0; //positive=east, negative=west
        // north=0, east=90, south=180, west=270
        int direction = 90;
        for (String c : input) {
            int amount = Integer.parseInt(c.substring(1));
            switch (c.charAt(0)) {
//              Action N means to move north by the given value.
                case 'N':
                    x += amount;
                    break;
//              Action S means to move south by the given value.
                case 'S':
                    x -= amount;
                    break;
//              Action E means to move east by the given value.
                case 'E':
                    y += amount;
                    break;
//              Action W means to move west by the given value.
                case 'W':
                    y -= amount;
                    break;
//              Action L means to turn left the given number of degrees.
                case 'L':
                    direction = (direction + 360 - amount) % 360;
                    break;
//              Action R means to turn right the given number of degrees.
                case 'R':
                    direction = (direction + amount) % 360;
                    break;
//              Action F means to move forward by the given value in the direction the ship is currently facing.
                case 'F':
                    switch (direction) {
                        case 0:
                            x += amount;
                            break;
                        case 90:
                            y += amount;
                            break;
                        case 180:
                            x -= amount;
                            break;
                        case 270:
                            y -= amount;
                            break;
                        default:
                            throw new IllegalStateException("BAAAL");
                    }
            }
        }
        System.out.println("Part 1: " + Math.abs(x) + Math.abs(y));
    }

    static final int N = 0;
    static final int E = 1;
    static final int S = 2;
    static final int W = 3;

    private static void part2(List<String> input) {
        int shipNorth = 0; //negative = south
        int shipEast = 0; //negative = west
        int[] waypoint = new int[]{1, 10, 0, 0};

        for (String c : input) {
            int amount = Integer.parseInt(c.substring(1));
            switch (c.charAt(0)) {
//              Action N means to move north by the given value.
                case 'N':
                    waypoint[N] += amount;
                    break;
//              Action S means to move south by the given value.
                case 'S':
                    waypoint[S] += amount;
                    break;
//              Action E means to move east by the given value.
                case 'E':
                    waypoint[E] += amount;
                    break;
//              Action W means to move west by the given value.
                case 'W':
                    waypoint[W] += amount;
                    break;
//              Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
                case 'L':
                    for(int i = 0; i < (amount/90); i++) {
                        int first = waypoint[0];
                        waypoint[0] = waypoint[1];
                        waypoint[1] = waypoint[2];
                        waypoint[2] = waypoint[3];
                        waypoint[3] = first;
                    }
                    break;
//              Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
                case 'R':
                    for(int i = 0; i < (amount/90); i++) {
                        int last = waypoint[3];
                        waypoint[3] = waypoint[2];
                        waypoint[2] = waypoint[1];
                        waypoint[1] = waypoint[0];
                        waypoint[0] = last;
                    }
                    break;
//              Action F means to move forward to the waypoint a number of times equal to the given value.
                case 'F':
                    shipNorth += (waypoint[N] - waypoint[S]) * amount;
                    shipEast += (waypoint[E] - waypoint[W]) * amount;
                    break;
            }
            System.out.println("Ship: N" + shipNorth + "; E" + shipEast + "   waypoint: N" + Arrays.toString(waypoint));
        }
        System.out.println("Part 2: " + (Math.abs(shipNorth) + Math.abs(shipEast)));
    }

}
