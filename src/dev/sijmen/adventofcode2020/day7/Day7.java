package dev.sijmen.adventofcode2020.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Day7 {

    public static void main(String[] args) throws IOException {
        new Day7();
    }

    HashMap<String, Bag> allBags = new HashMap<>();

    class Bag {
        final String name;
        HashMap<Bag, Integer> contains;
        Set<Bag> partOf = new HashSet<>();

        Bag(String name) {
            this.name = name;
        }

        public void setContains(String input) {
            contains = new HashMap<>();
            if("no other bags".equals(input))
                return;
            for (String rule : input.split(", ")) {
                String[] ruleParts = rule.split(" ", 2);
                String ruleBagName = toBagname(ruleParts[1]);
                if(!allBags.containsKey(ruleBagName)) {
                    allBags.put(ruleBagName, new Bag(ruleBagName));
                }
                contains.put(allBags.get(ruleBagName), Integer.parseInt(ruleParts[0]));
                allBags.get(ruleBagName).partOf.add(this);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Bag bag = (Bag) o;
            return name.equals(bag.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }

    static String toBagname(String rawName) {
        return rawName.replaceAll("\\W*bags?\\W*$", "");
    }

    Day7() throws IOException {
        List<String> input = Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day7/input.txt"))
                                  .collect(Collectors.toList());
        for(String line : input) {
            String[] split = line.substring(0, line.length() - 1).strip().split(" bags contain ");
            String name = toBagname(split[0]);
            if(!allBags.containsKey(name)) {
                allBags.put(name, new Bag(name));
            }
            allBags.get(name).setContains(split[1]);
        }

        Set<Bag> canContainShinyGold = new HashSet<>();
        part1(canContainShinyGold, allBags.get("shiny gold"));
        System.out.println("Part 1: " + canContainShinyGold.size());

        System.out.println("Part 2: " + (part2(allBags.get("shiny gold")) - 1));
    }

    void part1(Set<Bag> canContainShinyGold, Bag bag) {
        bag.partOf.forEach(b -> {
            if(!canContainShinyGold.contains(b)) {
                canContainShinyGold.add(b);
                part1(canContainShinyGold, b);
            }
        });
    }

    int part2(Bag bag) {
        return 1 + bag.contains.entrySet().stream()
                               .map((entry) -> entry.getValue() * part2(entry.getKey()))
                               .reduce(Integer::sum)
                               .orElse(0);
    }


}
