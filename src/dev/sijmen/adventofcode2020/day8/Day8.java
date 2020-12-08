package dev.sijmen.adventofcode2020.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day8 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day8/input.txt"))
                .collect(Collectors.toList());

        try {
            runProgram(input);
        }catch (IllegalArgumentException e) {
            System.out.println("Part 1: " + e.getMessage());
        }

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            if(line.startsWith("jmp")) {
                input.set(i, line.replace("jmp", "nop"));
            } else if(line.startsWith("nop")) {
                input.set(i, line.replace("nop", "jmp"));
            }
            try {
                int result = runProgram(input);
                System.out.println("Part 2: " + result);
                break;
            } catch (IllegalArgumentException e) {
                // pass
            }
            input.set(i, line);
        }
    }

    public static int runProgram(List<String> input) throws IllegalArgumentException{
        int accumulator = 0;
        Set<Integer> visited = new HashSet<>();
        for (int i = 0; i < input.size();) {
            if(visited.contains(i)) {
                throw new IllegalArgumentException("Recursion detected at i="+i + " accumulator=" + accumulator);
            }
            visited.add(i);

            String line = input.get(i);
            String[] parts = line.strip().split(" ");
            String operation = parts[0];
            int number = Integer.parseInt(parts[1]);
            switch (operation) {
                case "acc":
                    accumulator += number;
                    i++;
                    break;
                case "jmp":
                    i += number;
                    break;
                case "nop":
                    i++;
                    break;
            }
        }
        return accumulator;
    }

}
