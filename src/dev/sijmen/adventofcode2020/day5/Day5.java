package dev.sijmen.adventofcode2020.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day5/input.txt"))
                .collect(Collectors.toList());

        List<Integer> seatIds = input.stream().map(Day5::seatId).sorted().collect(Collectors.toList());
        int max = seatIds.stream().max(Integer::compareTo).get();
        int min = seatIds.stream().min(Integer::compareTo).get();
        System.out.println("Largest seatId: " + max);

        for (int i = min; i < max; i++) {
            if(seatIds.get(i-min) != i) {
                System.out.println("My seat: " + i);
                break;
            }
        }
    }

    public static int seatId(String input) {
        int row = decode(input.substring(0, 7), 0, 127);
        int column = decode(input.substring(7, 10), 0, 7);

        return row * 8 + column;
    }

    public static int decode(String input, int min, int max) {
        for (char c : input.toCharArray()) {
            float diff = Math.round((max - min) / 2f);
            if (c == 'F' || c == 'L') {
                // lower half
                max -= Math.nextDown(diff);
            }else {
                // upper half
                min += Math.nextUp(diff);
            }
        }
        return min;
    }

}
