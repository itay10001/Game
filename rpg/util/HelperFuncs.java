package rpg.util;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * General-purpose Java 8+ helpers. No external dependencies.
 * Import with: import static utils.HelperFuncs.*;
 *
 * Console input functions are intended for command-line applications.
 * File functions use UTF-8 and throw UncheckedIOException on I/O errors.
 */
public final class HelperFuncs {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final DateTimeFormatter CLOCK = DateTimeFormatter.ofPattern("HH:mm:ss");

    private HelperFuncs() {
        throw new AssertionError("Utility class; do not instantiate");
    }

    // ---------- Output ----------

    /** Print without a newline. */
    public static void p(Object value) {
        System.out.print(str(value));
    }

    public static String SOPL(String something){
        System.out.println(something);
        return something;
    }

    /** Print with a newline. */
    public static void pl(Object value) {
        System.out.println(str(value));
    }

    /** Print an empty line. */
    public static void pl() {
        System.out.println();
    }

    /** Formatted output, like printf (no automatic newline). */
    public static void pf(String format, Object... args) {
        System.out.printf(format, args);
    }

    /** Human-readable text for objects and arrays (including primitive arrays). */
    public static String str(Object value) {
        if (value == null) return "null";
        if (!value.getClass().isArray()) return String.valueOf(value);
        String wrapped = Arrays.deepToString(new Object[]{value});
        return wrapped.substring(1, wrapped.length() - 1);
    }

    /** Log a message with a local-time timestamp. */
    public static void log(Object message) {
        System.out.println("[" + LocalTime.now().format(CLOCK) + "] " + str(message));
    }

    // ---------- Console input ----------

    /** Read one full line (including spaces). */
    public static String ask(String prompt) {
        p(prompt);
        return INPUT.nextLine();
    }

    /** Keep prompting until a valid int is entered. */
    public static int askInt(String prompt) {
        while (true) {
            String value = ask(prompt).trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                pl("Please enter a valid whole number.");
            }
        }
    }

    /** Keep prompting until a finite double is entered. */
    public static double askDouble(String prompt) {
        while (true) {
            String value = ask(prompt).trim();
            try {
                double number = Double.parseDouble(value);
                if (Double.isFinite(number)) return number;
            } catch (NumberFormatException ignored) {
                // Ask again below.
            }
            pl("Please enter a valid number.");
        }
    }

    /** Accept y/yes or n/no (case-insensitive). */
    public static boolean askYesNo(String prompt) {
        while (true) {
            String value = ask(prompt + " (y/n): ").trim();
            if (value.equalsIgnoreCase("y") || value.equalsIgnoreCase("yes")) return true;
            if (value.equalsIgnoreCase("n") || value.equalsIgnoreCase("no")) return false;
            pl("Please enter y or n.");
        }
    }

    // ---------- Randomness ----------

    /** Random int, including both ends; supports the full int range. */
    public static int randInt(int min, int max) {
        if (min > max) throw new IllegalArgumentException("min must be <= max");
        return (int) ThreadLocalRandom.current().nextLong((long) min, (long) max + 1L);
    }

    /** Random double: min inclusive, max exclusive (or min if equal). */
    public static double randDouble(double min, double max) {
        if (!Double.isFinite(min) || !Double.isFinite(max) || min > max) {
            throw new IllegalArgumentException("Expected finite min <= max");
        }
        return min == max ? min : ThreadLocalRandom.current().nextDouble(min, max);
    }

    /** Return true with the specified probability, from 0 to 100 percent. */
    public static boolean chance(double percent) {
        if (!Double.isFinite(percent) || percent < 0 || percent > 100) {
            throw new IllegalArgumentException("percent must be between 0 and 100");
        }
        return ThreadLocalRandom.current().nextDouble(100) < percent;
    }

    /** Pick a random item from one or more values. */
    @SafeVarargs
    public static <T> T pick(T... values) {
        Objects.requireNonNull(values, "values");
        if (values.length == 0) throw new IllegalArgumentException("Cannot pick from an empty array");
        return values[randInt(0, values.length - 1)];
    }

    /** Pick a random item from a nonempty list. */
    public static <T> T pick(List<T> values) {
        Objects.requireNonNull(values, "values");
        if (values.isEmpty()) throw new IllegalArgumentException("Cannot pick from an empty list");
        return values.get(randInt(0, values.size() - 1));
    }

    /** Shuffle a list in place, then return it. */
    public static <T> List<T> shuffle(List<T> values) {
        Collections.shuffle(values);
        return values;
    }

    // ---------- Numbers ----------

    public static int clamp(int value, int min, int max) {
        if (min > max) throw new IllegalArgumentException("min must be <= max");
        return Math.max(min, Math.min(max, value));
    }

    public static double clamp(double value, double min, double max) {
        if (Double.isNaN(value) || Double.isNaN(min) || Double.isNaN(max) || min > max) {
            throw new IllegalArgumentException("Invalid value or bounds");
        }
        return Math.max(min, Math.min(max, value));
    }

    /** True when value lies within the inclusive interval. */
    public static boolean between(double value, double min, double max) {
        if (min > max) throw new IllegalArgumentException("min must be <= max");
        return value >= min && value <= max;
    }

    /** Convert a number from one scale to another; permits extrapolation. */
    public static double map(double value, double fromMin, double fromMax,
                             double toMin, double toMax) {
        if (fromMin == fromMax) throw new IllegalArgumentException("Source range cannot be zero");
        return toMin + (value - fromMin) * (toMax - toMin) / (fromMax - fromMin);
    }

    /** Linear interpolation; t=0 gives a, t=1 gives b. */
    public static double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    /** Round a finite number to the specified number of decimal places. */
    public static double round(double value, int decimals) {
        if (!Double.isFinite(value) || decimals < 0 || decimals > 15) {
            throw new IllegalArgumentException("Expected finite value and 0..15 decimals");
        }
        return BigDecimal.valueOf(value).setScale(decimals, RoundingMode.HALF_UP).doubleValue();
    }

    /** 2D Euclidean distance. */
    public static double dist(double x1, double y1, double x2, double y2) {
        return Math.hypot(x2 - x1, y2 - y1);
    }

    public static double sum(double... values) {
        double result = 0;
        for (double value : values) result += value;
        return result;
    }

    /** Arithmetic mean; an empty input is an error. */
    public static double avg(double... values) {
        if (values.length == 0) throw new IllegalArgumentException("Cannot average empty input");
        return sum(values) / values.length;
    }

    /** Parse a whole number, or return fallback if invalid. */
    public static int intOr(String text, int fallback) {
        if (text == null) return fallback;
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    /** Parse a finite decimal number, or return fallback if invalid. */
    public static double doubleOr(String text, double fallback) {
        if (text == null) return fallback;
        try {
            double value = Double.parseDouble(text.trim());
            return Double.isFinite(value) ? value : fallback;
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    // ---------- Strings ----------

    /** Empty, null, or whitespace-only. */
    public static boolean blank(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static boolean containsIgnoreCase(String text, String part) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(part, "part");
        return text.toLowerCase(java.util.Locale.ROOT)
                   .contains(part.toLowerCase(java.util.Locale.ROOT));
    }

    public static String reverse(String text) {
        return new StringBuilder(text).reverse().toString();
    }

    /** Repeat text; works on Java 8 (unlike String.repeat). */
    public static String repeat(String text, int times) {
        Objects.requireNonNull(text, "text");
        if (times < 0 || (long) text.length() * times > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Invalid repeat count");
        }
        StringBuilder result = new StringBuilder(text.length() * times);
        for (int i = 0; i < times; i++) result.append(text);
        return result.toString();
    }

    /** Split on one or more whitespace characters. */
    public static String[] words(String text) {
        if (blank(text)) return new String[0];
        return text.trim().split("\\s+");
    }

    // ---------- Collections ----------

    /** Create a resizable ArrayList from values. */
    @SafeVarargs
    public static <T> ArrayList<T> list(T... values) {
        ArrayList<T> result = new ArrayList<T>(values.length);
        for (T value : values) result.add(value);
        return result;
    }

    /** Test whether an item equals any of the options (null-safe). */
    @SafeVarargs
    public static <T> boolean oneOf(T item, T... options) {
        for (T option : options) {
            if (Objects.equals(item, option)) return true;
        }
        return false;
    }

    /** Integers from 0 up to end (exclusive). */
    public static List<Integer> range(int end) {
        return range(0, end, 1);
    }

    /** Integers from start to end (exclusive). */
    public static List<Integer> range(int start, int end) {
        return range(start, end, 1);
    }

    /** Python-style range, supporting a negative step. */
    public static List<Integer> range(int start, int end, int step) {
        if (step == 0) throw new IllegalArgumentException("step cannot be zero");
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (long i = start; step > 0 ? i < end : i > end; i += step) {
            result.add((int) i);
        }
        return result;
    }

    // ---------- Time ----------

    /** Monotonic stopwatch (not affected by wall-clock adjustments). */
    public static final class Stopwatch {
        private long startNanos = System.nanoTime();

        public void reset() {
            startNanos = System.nanoTime();
        }

        public long elapsedMs() {
            return (System.nanoTime() - startNanos) / 1_000_000L;
        }

        public double elapsedSeconds() {
            return (System.nanoTime() - startNanos) / 1_000_000_000.0;
        }
    }

    /** Run an action, returning its duration in milliseconds. */
    public static long timeMs(Runnable action) {
        Objects.requireNonNull(action, "action");
        long start = System.nanoTime();
        action.run();
        return (System.nanoTime() - start) / 1_000_000L;
    }

    /** Pause the current thread (avoid inside a robot's periodic control loop). */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Sleep interrupted", ex);
        }
    }

    // ---------- UTF-8 files ----------

    public static boolean fileExists(String path) {
        return Files.isRegularFile(Paths.get(path));
    }

    /** Read a whole file. I/O failures become UncheckedIOException. */
    public static String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    public static List<String> readLines(String path) {
        try {
            return Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /** Create parent folders if needed; overwrite the file. */
    public static void writeFile(String path, String content) {
        writeBytes(path, content, false);
    }

    /** Create parent folders if needed; append without adding a newline. */
    public static void appendFile(String path, String content) {
        writeBytes(path, content, true);
    }

    private static void writeBytes(String path, String content, boolean append) {
        Objects.requireNonNull(content, "content");
        try {
            Path file = Paths.get(path);
            Path parent = file.toAbsolutePath().getParent();
            if (parent != null) Files.createDirectories(parent);
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            if (append) {
                Files.write(file, bytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } else {
                Files.write(file, bytes, StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
