package org.elementarclash.ui;

import java.util.Scanner;

/**
 * Handles console input and user prompts.
 * Single Responsibility: User interaction via console.
 */
public class ConsoleUI {
    private final Scanner scanner;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public String promptAction() {
        System.out.print(System.lineSeparator() + "Aktion [B]ewegen | [A]ngreifen | " +
                "[U]ndo | [R]edo | [Z]ug beenden | [Q] Beenden: ");
        return scanner.nextLine().trim().toUpperCase();
    }

    public String promptUnitSelection(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim().toUpperCase();
    }

    public String promptPosition(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public String promptViewLog(String prompt){
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public void showError(String message) {
        System.out.println("Fehler: " + message);
    }

    public void showSuccess(String message) {
        System.out.println(message);
    }

    public void display(String content) {
        System.out.println(content);
    }

    public void close() {
        scanner.close();
    }
}
