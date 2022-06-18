package ru.vsu.cs.course1.hash.demo;

import ru.vsu.cs.course1.hash.SimpleHashMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Program {

    /**
     * Основная функция программы
     *
     * @param args Параметры командной строки
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        solution();
    }

    public static void solution() throws Exception {
        String[] input = readTextFromFile("input01.txt");
        SimpleHashMap<String, Integer> properNames = new SimpleHashMap<>(input.length);
        ArrayList<String> keys = new ArrayList<>();
        for(String word: input) {
            char[] letters = word.toCharArray();
            int count = 0;
            for(int i = 0; i < letters.length; i++) {
                if (Character.isUpperCase(letters[i])) {
                    count++;
                }
            }
            if((count==letters.length)&&(letters.length<6)) {
                if(!properNames.containsKey(word)) {
                    properNames.put(word, 1);
                    keys.add(word);
                } else {
                    int value = properNames.get(word);
                    properNames.put(word, value+1);
                }
            }
        }
        double length = properNames.getLoadedBucketsCount();
        for(String key: keys) {
            System.out.println(key + ": " + properNames.get(key));
        }
    }

    public static String[] readTextFromFile(String filename) throws FileNotFoundException {
        try {
            Scanner scanner = new Scanner(new File(filename));
            String text = "";
            while (scanner.hasNextLine()) {
                text = text + scanner.nextLine();
            }
            scanner.close();
            String[] words = text.split("[ «».(),!?;:\"-]+");
            return words;
        } catch (Exception e) {
            return null;
        }
    }
}
