package dev.sijmen.adventofcode2020.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Day2 {

    public static void main(String[] args) {
        new Day2();
    }

    Day2() {
        passwords((stream -> {
            long correctA = stream.map(PasswordPolicy::new).map(PasswordPolicy::isCorrectPart1).filter(a -> a).count();
            System.out.println("Part 1: " + correctA);
        }));

        passwords((stream -> {
            long correctB = stream.map(PasswordPolicy::new).map(PasswordPolicy::isCorrectPart2).filter(a -> a).count();
            System.out.println("Part 2: " + correctB);
        }));
    }

    void passwords(Consumer<Stream<String>> s) {
        try (Stream<String> stream = Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day2/input.txt"))) {
            s.accept(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class PasswordPolicy {
        String password;
        char character;
        int nr1;
        int nr2;

        PasswordPolicy(String line) {
            String[] parts = line.split("[- :]");
            nr2 = Integer.parseInt(parts[0]);
            nr1 = Integer.parseInt(parts[1]);
            character = parts[2].charAt(0);
            password = parts[4];
        }

        boolean isCorrectPart1() {
            int nrOfOccurrences = nrOfCharsInString(character, password);
            return nrOfOccurrences >= nr2 && nrOfOccurrences <= nr1;
        }

        boolean isCorrectPart2() {
            boolean nr1C = password.charAt(nr1 - 1) == character;
            boolean nr2C = password.charAt(nr2 - 1) == character;
            return nr1C ^ nr2C;
        }
    }

    static int nrOfCharsInString(char someChar, String someString) {
        int count = 0;
        for (int i = 0; i < someString.length(); i++) {
            if (someString.charAt(i) == someChar) {
                count++;
            }
        }
        return count;
    }
}
