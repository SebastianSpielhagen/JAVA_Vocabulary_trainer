package org.spielhagen;
import java.io.*;
import java.util.*;

public class Vokabeltrainer {
    private final Map<String, String> vokabeln;
    private final Scanner scanner;
    private final String dateiName;

    public Vokabeltrainer(String dateiName) {
        vokabeln = new HashMap<>();
        scanner = new Scanner(System.in);
        this.dateiName = dateiName;
        ladeVokabeln();
    }

    public void vokabelHinzufuegen(String deutsch, String englisch) {
        vokabeln.put(deutsch, englisch);
        speichereVokabeln();
    }

    public void starten() {
        System.out.println("Willkommen beim Vokabeltrainer!");

        while (true) {
            System.out.println("Bitte geben Sie das deutsche Wort ein:");
            String deutsch = scanner.nextLine();

            if (deutsch.equals("exit")) {
                break;
            }

            String englisch = vokabeln.get(deutsch);

            if (englisch != null) {
                System.out.println("Die englische Übersetzung ist: " + englisch);
            } else {
                System.out.println("Das Wort ist nicht im Vokabeltrainer vorhanden.");
                System.out.println("Möchten Sie eine neue Vokabel hinzufügen? (ja/nein)");
                String antwort = scanner.nextLine();

                if (antwort.equalsIgnoreCase("ja")) {
                    System.out.println("Bitte geben Sie die englische Übersetzung ein:");
                    String neueEnglisch = scanner.nextLine();
                    vokabelHinzufuegen(deutsch, neueEnglisch);
                    System.out.println("Die Vokabel wurde hinzugefügt.");
                }
            }
        }

        System.out.println("Auf Wiedersehen!");
    }

    private void ladeVokabeln() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dateiName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    vokabeln.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Vokabeln: " + e.getMessage());
        }
    }

    private void speichereVokabeln() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dateiName))) {
            for (Map.Entry<String, String> entry : vokabeln.entrySet()) {
                writer.write(entry.getKey() + ";" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Vokabeln: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Vokabeltrainer vokabeltrainer = new Vokabeltrainer("vokabeln.txt");

        vokabeltrainer.starten();
    }
}