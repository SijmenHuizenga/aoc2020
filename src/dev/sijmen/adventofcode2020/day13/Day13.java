package dev.sijmen.adventofcode2020.day13;

import java.util.Arrays;

public class Day13 {
//    static int in = 939;
//    static String inb = "7,13,x,x,59,x,31,19";

    static int in = 1000655;
    static String inb = "17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,571,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,23,x,x,x,x,x,29,x,401,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19";

    static int[] busses = Arrays.stream(inb.split(",")).filter(x -> !"x".equals(x)).mapToInt(Integer::parseInt).sorted().toArray();
    static int whildcards = (int) inb.chars().filter(x -> x == 'x').count();

    public static void main(String[] args) {

        int bestBusId = 0;
        int bestWaitTime = 99999;
        for (int buss : busses) {
            int waitTime = buss - (in % buss);
            if (waitTime < bestWaitTime) {
                bestWaitTime = waitTime;
                bestBusId = buss;
            }
        }

        System.out.println("Part 1: " + (bestBusId * bestWaitTime ));

        //part 2: AC too high, can't slay it
    }


}
