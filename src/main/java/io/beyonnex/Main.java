package io.beyonnex;

import io.beyonnex.data.InputtedText;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Main {

    private static final List<String> OPTIONS = List.of(
            "1- Check two texts that it is anagram for each other",
            "2- Find all anagrams in previous input",
            "3- Exit"
    );

    private static final String ERROR_MESSAGE = "Please enter an integer value between 1 and " + OPTIONS.size();

    private static final Map<String, Set<InputtedText>> STORED_INPUTS = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int selected = 0;
        while (selected != 3) {
            printMenu();
            try {
                selected = scanner.nextInt();
                switch (selected) {
                    case 1 -> checkAnagramPair();
                    case 2 -> findAllInputtedAnagrams();
                    case 3 -> exit(0);
                    default -> System.out.println(ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                System.out.println(ERROR_MESSAGE);
                scanner.next();
            }
        }
    }

    private static void checkAnagramPair() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input your subject here: ");
        InputtedText subject = new InputtedText(scanner.nextLine());
        System.out.print("Input your anagram here: ");
        InputtedText anagram = new InputtedText(scanner.nextLine());

        storeInput(subject);
        storeInput(anagram);

        System.out.println(
                subject.getKey().equals(anagram.getKey()) && !subject.equals(anagram)
                ? "It is an anagram!"
                : "It isn't an anagram :("
        );
    }

    private static void findAllInputtedAnagrams() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input your subject here: ");
        InputtedText subject = new InputtedText(scanner.nextLine());

        List<String> result = STORED_INPUTS.getOrDefault(subject.getKey(), new HashSet<>()).stream()
                .filter(e -> !e.getNormalisedText().equals(subject.getNormalisedText()))
                .map(InputtedText::getOriginalInput)
                .collect(Collectors.toList());

        printArray(result);
    }

    private static void storeInput(InputtedText input) {
        STORED_INPUTS.computeIfAbsent(input.getKey(), key -> new HashSet<>()).add(input);
    }

    private static void printMenu() {
        OPTIONS.forEach(System.out::println);
        System.out.print("Choose your option : ");
    }

    private static void printArray(List<String> printable) {
        System.out.println(printable.stream().collect(Collectors.joining(", ", "[", "]")));
    }
}