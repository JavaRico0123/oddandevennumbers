import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Aplikace {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("""
                    Příkaldy kódu pro příkazový řádek:
                    Aplikace přečte čísla ze s. vstupu, výsledek zapíše na s. výstup:
                    aplikace 4

                    Aplikace přečte čísla ze souboru, výsledek zapíše do souboru:
                    aplikace input.txt output.txt

                    Aplikace přečte čísla ze souboru, výsledek zapíše na s. výstup:
                    aplikace input.txt\s
                    """);
            System.exit(1);
        }

        String inputParam = args[0];
        String outputParam = args.length > 1 ? args[1] : null;

        List<Integer> numbers;
        try {
            if (isNumeric(inputParam)) {
                System.out.println("Zadejte čísla, každé na novém řádku. Pro ukončení zadejte prázdný řádek.");
                numbers = readNumbersFromStdin();
            } else {
                numbers = readNumbersFromFile(inputParam);
            }

            List<Integer> result = processNumbers(numbers);

            if (outputParam != null) {
                writeResultToFile(result, outputParam);
            } else {
                    for (int num : result) {
                    System.out.println(num);
                }
            }
        } catch (IOException e) {
            System.out.println("Chyba při čtení/zápisu souboru: " + e.getMessage());
        }
    }

    private static List<Integer> readNumbersFromStdin() throws IOException {
        List<Integer> numbers = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            try {
                int number = Integer.parseInt(line);
                numbers.add(number);
            } catch (NumberFormatException e) {
                System.out.println("Chybný vstup, očekává se číslo.");
            }
        }
        return numbers;
    }

    private static List<Integer> readNumbersFromFile(String filename) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int number = Integer.parseInt(line);
                    numbers.add(number);
                } catch (NumberFormatException e) {
               }
            }
        }
        return numbers;
    }

    private static List<Integer> processNumbers(List<Integer> numbers) {
        int count = numbers.size();
        List<Integer> result;
        if (count % 2 == 0) { // Sudá čísela
            result = new ArrayList<>();
            for (int num : numbers) {
                if (num % 2 == 0) {
                    result.add(num);
                }
            }
        } else { // Lichý čísla
            result = new ArrayList<>();
            for (int num : numbers) {
                if (num % 2 != 0) {
                    result.add(num);
                }
            }
        }
        return result;
    }

    private static void writeResultToFile(List<Integer> result, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int num : result) {
                writer.println(num);
            }
        }
    }

    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
