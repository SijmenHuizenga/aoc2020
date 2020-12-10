package dev.sijmen.adventofcode2020.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {

    public static void main(String[] args) throws IOException {
        List<Integer> input = Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day10/input.txt"))
                .map(Integer::valueOf).sorted().collect(Collectors.toList());
        input.add(0, 0);
        input.add(input.get(input.size() - 1) + 3);

        int occ1 = 0, occ3 = 0;

        for (int i = 1; i < input.size(); i++) {
            int prev = input.get(i - 1);
            int diff = input.get(i) - prev;
            switch (diff) {
                case 1:
                    occ1++;
                    break;
                case 3:
                    occ3++;
                    break;
            }
        }

        System.out.println("Part 1: " + occ1 * occ3);
        long start = System.nanoTime();
        long result = nrOfOptionsToEnd(input.stream().mapToInt(i -> i).toArray(), 0);
        System.out.println(System.nanoTime() - start);
        System.out.println("Part 2: " + result);
    }

    static HashMap<Integer, Long> nrOfOptionsFromPosition = new HashMap<>();

    public static long nrOfOptionsToEnd(int[] input, int startingIndex) {
        if(nrOfOptionsFromPosition.containsKey(startingIndex)) {
            return nrOfOptionsFromPosition.get(startingIndex);
        }
        if (startingIndex == input.length - 1) {
            return 1;
        }

        int currentValue = input[startingIndex];

        long out = nrOfOptionsToEnd(input, startingIndex + 1);
        if (startingIndex + 2 < input.length && input[startingIndex + 2] <= currentValue + 3) {
            out += nrOfOptionsToEnd(input, startingIndex + 2);
        }
        if (startingIndex + 3 < input.length && input[startingIndex + 3] <= currentValue + 3) {
            out += nrOfOptionsToEnd(input, startingIndex + 3);
        }
        nrOfOptionsFromPosition.put(startingIndex, out);
        return out;
    }


}
