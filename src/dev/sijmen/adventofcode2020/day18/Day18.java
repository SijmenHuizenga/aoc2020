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

        result = input.stream().map(this::advancedMath).map(Equation::value).reduce(Long::sum).get();
        System.out.println("Part 2: " + result);
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
            if (value == 0) {
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

    public Equation advancedMath(String input) {
        return advancedMathParser(input.replace(" ", "")).e;
    }

    public JavaPleaseGiveMeTuples advancedMathParser(String input) {
        char operation = 0;
        Equation equation = null;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '+' || c == '*') {
                operation = c;
                continue;
            }

            Equation e;

            if(c == '(') {
                JavaPleaseGiveMeTuples x = advancedMathParser(input.substring(i + 1));
                e = new CombinedEquation(x.e);
                i += x.eqCharLength;
            } else if (c == ')') {
                if(operation != 0) {
                    throw new IllegalStateException("BUG");
                }
                return new JavaPleaseGiveMeTuples(equation, i+1);
            } else {
                e = new Constant(c - '0');
            }

            if (equation == null) {
                equation = e;
                continue;
            }

            if (operation == '*') {
                equation = new MulEquation(equation, e);
                operation = 0;
            } else if (operation == '+') {
                equation = summy(equation, e);
                operation = 0;
            }
        }
        return new JavaPleaseGiveMeTuples(equation, input.length());
    }

    private Equation summy(Equation left, Equation right) {
        if (left instanceof MulEquation) {
            // A*B => +C => (A*B)+C => A*(B+C)
            return new MulEquation(((MulEquation) left).left, new SumEquation(((MulEquation) left).right, right));
        } else {
            return new SumEquation(left, right);
        }
    }

    class JavaPleaseGiveMeTuples {
        Equation e;
        int eqCharLength;

        public JavaPleaseGiveMeTuples(Equation e, int eqCharLength) {
            this.e = e;
            this.eqCharLength = eqCharLength;
        }

        public Equation getE() {
            return e;
        }
    }

    abstract class Equation {
        abstract long value();
    }

    class Constant extends Equation {
        long value;

        public Constant(long value) {
            this.value = value;
        }

        public long value() {
            return value;
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    class SumEquation extends Equation {
        Equation left, right;

        public SumEquation(Equation left, Equation right) {
            this.left = left;
            this.right = right;
        }

        long value() {
            return left.value() + right.value();
        }

        @Override
        public String toString() {
            return "(" + left + "+" + right + ')';
        }
    }

    class MulEquation extends Equation {
        Equation left, right;

        public MulEquation(Equation left, Equation right) {
            this.left = left;
            this.right = right;
        }

        long value() {
            return left.value() * right.value();
        }

        @Override
        public String toString() {
            return "(" + left + "*" + right + ')';
        }
    }

    class CombinedEquation extends Equation {

        Equation inside;

        public CombinedEquation(Equation inside) {
            this.inside = inside;
        }

        @Override
        long value() {
            return inside.value();
        }

        @Override
        public String toString() {
            return inside.toString();
        }
    }
}
