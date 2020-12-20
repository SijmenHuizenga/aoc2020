package dev.sijmen.adventofcode2020.day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;

public class Day18 {

    public static void main(String[] args) throws IOException {
        new Day18();
    }

    public Day18() throws IOException {
        List<String> input = Files.lines(Paths.get("src/dev/sijmen/adventofcode2020/day18/input.txt"))
                .collect(Collectors.toList());

        long result = input.stream().map(this::basicMath).map(SolvePart::getValue).reduce(Long::sum).get();
        System.out.println("Part 1: " + result);
    }

    static HashMap<Character, LongBinaryOperator> operators = new HashMap<>();

    static {
        operators.put('+', Long::sum);
        operators.put('*', (a, b) -> a * b);
    }

    class SolvePart {
        int continueAtIndex;
        long value;

        public SolvePart(int continueAtIndex, long value) {
            this.continueAtIndex = continueAtIndex;
            this.value = value;
        }

        public long getValue() {
            return value;
        }
    }

    public SolvePart basicMath(String equation) {
        char operation = 0;
        long value = 0;
        for (int i = 0; i < equation.length(); i++) {
            char c = equation.charAt(i);
            if (operators.containsKey(c)) {
                operation = c;
                continue;
            }
            if (c == '(') {
                SolvePart betweenBrackets = basicMath(equation.substring(i + 1));
                if (operation == 0) {
                    value = betweenBrackets.value;
                } else {
                    value = operators.get(operation).applyAsLong(value, betweenBrackets.value);
                    operation = 0;
                }
                i += betweenBrackets.continueAtIndex;
                continue;
            }
            if (c == ')') {
                return new SolvePart(i + 1, value);
            }
            if(value == 0) {
                value = c - '0';
                continue;
            }
            if (operation != 0) {
                value = operators.get(operation).applyAsLong(value, c - '0');
                operation = 0;
            }

        }
        return new SolvePart(equation.length(), value);
    }


}
