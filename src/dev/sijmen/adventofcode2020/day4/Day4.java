package dev.sijmen.adventofcode2020.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {

    public static HashMap<String, Predicate<String>> requiredFields = new HashMap<>();
    static {
        requiredFields.put("byr", (in) -> {
            int i = Integer.parseInt(in);
            return i >= 1920 && i <= 2002;
        });
        requiredFields.put("iyr", (in) -> {
            int i = Integer.parseInt(in);
            return i >= 2010 && i <= 2020;
        });
        requiredFields.put("eyr", (in) -> {
            int i = Integer.parseInt(in);
            return i >= 2020 && i <= 2030;
        });
        requiredFields.put("hgt", (in) -> {
            int i = Integer.parseInt(in.substring(0, in.length()-2));
            if(in.endsWith("cm")) {
                return i >= 150 && i <= 193;
            } else if (in.endsWith("in")) {
                return i >= 59 && i <= 76;
            }
            return false;
        });
        requiredFields.put("hcl", (in) -> Pattern.compile("#[0-9a-f]{6}").matcher(in).matches());
        requiredFields.put("ecl", (in) -> List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(in));
        requiredFields.put("pid", (in) -> Pattern.compile("[0-9]{9}").matcher(in).matches());
    }

    public static void main(String[] args) throws IOException {
        String input = Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day4/input.txt"))
                .collect(Collectors.joining("\n"));

        int correctPart1 = 0, correctPart2 = 0;

        outer : for(String passport : input.split("\n\n")) {
            HashMap<String, String> decoded = new HashMap<>();
            for (String field : passport.split("[ \n]")) {
                String[] parts = field.split(":");
                decoded.put(parts[0], parts[1]);
            }
            if(!decoded.keySet().containsAll(requiredFields.keySet())) {
                continue;
            }
            correctPart1++;

            for (Map.Entry<String, String> see : decoded.entrySet()) {
                if(requiredFields.containsKey(see.getKey()) && !requiredFields.get(see.getKey()).test(see.getValue())) {
                    continue outer;
                }
            }
            correctPart2++;
        }
        System.out.println("Part 1: " + correctPart1);
        System.out.println("Part 2: " + correctPart2);
    }

}
