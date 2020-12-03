package dev.sijmen.adventofcode2020.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 {

    public static void main(String[] args) throws IOException {

        System.out.println("Part 1: " + hitTrees(1, 3));
        System.out.println("Part 2: " + (hitTrees(1, 1) * hitTrees(1, 3) * hitTrees(1, 5) * hitTrees(1, 7) * hitTrees(2, 1)));
    }

    public static long hitTrees(int down, int side) throws IOException {
        List<String> treemap = Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day3/input.txt"))
                .collect(Collectors.toList());

        long hitTrees = 0;
        for (int i = 0; i < treemap.size(); i+=down) {
            String line = treemap.get(i);
            if(line.charAt(((i / down) * side) % line.length()) == '#') {
                hitTrees ++;
            }
        }
        return hitTrees;
    }

}
