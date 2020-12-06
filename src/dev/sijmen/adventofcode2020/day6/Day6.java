package dev.sijmen.adventofcode2020.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Day6 {

    public static void main(String[] args) throws IOException {
        String input = Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day6/input.txt"))
                .collect(Collectors.joining("\n"));

        Arrays.stream(input.split("\n\n"))
                .map((group) -> group.chars().filter((i) -> i != '\n').distinct().count())
                .reduce(Long::sum)
                .map((s) -> "Part 1: " + s)
                .ifPresent(System.out::println);

        Arrays.stream(input.split("\n\n"))
                .map((group) ->
                        group.chars()
                                .filter((c) -> c != '\n')
                                .filter((c) -> group.chars().filter(ch -> ch == c).count() == group.split("\n").length)
                                .distinct().count())
                .reduce(Long::sum)
                .map((s) -> "Part 2: " + s)
                .ifPresent(System.out::println);
    }

}
