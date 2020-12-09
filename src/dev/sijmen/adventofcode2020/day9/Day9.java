package dev.sijmen.adventofcode2020.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day9 {

    public static void main(String[] args) throws IOException {
        new Day9();
    }

    int lookback = 25;

    public Day9() throws IOException {
        long[] input = Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day9/input.txt"))
                            .mapToLong(Long::valueOf)
                            .toArray();
        int i;
        for (i = lookback; i < input.length; i++) {
            if (!getTwoValuesEqualling(input, i - lookback, i - 1, input[i])) {
                break;
            }
        }
        System.out.println("Part 1: " + input[i]);

        Range part2 = findContinuousSetOfNumbersInList(input, input[i]);

        System.out.println("Part 2: " + (part2.max(input) + part2.min(input)));
    }

    public static boolean getTwoValuesEqualling(long[] input, int startI, int endI, long searchVal) {
        for (int i = startI; i <= endI; i++) {
            long a = input[i];
            for (int j = startI; j <= endI; j++) {
                long b = input[j];
                if (a + b == searchVal) {
                    return true;
                }
            }
        }
        return false;
    }

    public Range findContinuousSetOfNumbersInList(long[] input, long needle) {
        Range r = new Range(0, 0);
        long magic = 0;
        do {
            if (magic < needle) {
                r.end++;
            } else {
                r.start++;
                r.end = r.start + 1;
            }
            magic = r.sum(input);
        } while (magic != needle);
        return r;
    }

    class Range {
        int start, end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public long sum(long[] input) {
            long total = 0;
            for (int i = start; i <= end; i++) {
                total += input[i];
            }
            return total;
        }

        public long max(long[] input) {
            long max = 0;
            for (int i = start; i <= end; i++) {
                if (input[i] > max) {
                    max = input[i];
                }
            }
            return max;
        }

        public long min(long[] input) {
            long min = 999999999;
            for (int i = start; i <= end; i++) {
                if (input[i] < min) {
                    min = input[i];
                }
            }
            return min;
        }
    }

}
