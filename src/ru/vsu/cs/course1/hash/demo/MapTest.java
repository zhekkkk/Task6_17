package ru.vsu.cs.course1.hash.demo;

import java.lang.reflect.Array;
import java.util.*;

import ru.vsu.cs.course1.hash.SimpleHashMap;

public class MapTest {

    public static final int TEST_MAP_SIZE = 1000;
    public static final int MAX_KEY_VALUE = 1000000;

    private static <K, V> String toString(Map<K, V> map, boolean ordered) {
        if (map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Map.Entry<K, V> firstEntry = map.entrySet().iterator().next();
        Map.Entry<K, V>[] entries = map.entrySet().stream().toArray(
                size -> (Map.Entry<K, V>[]) Array.newInstance(firstEntry.getClass(), size)
        );
        if (!ordered && firstEntry.getKey() instanceof Comparable) {
            Arrays.sort(entries, (a, b) -> ((Comparable) a.getKey()).compareTo(b.getKey()));
        }
        for (Map.Entry<K, V> kv : entries) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(String.format("(%s: %s)", kv.getKey(), kv.getValue()));
        }
        return sb.toString();
    }

    private static <K, V> void printMapsState(Map<K, V> rightMap, Map<K, V> testMap, boolean ordered) {
        System.out.printf("%d, %d, %s%n", testMap.size(), rightMap.size(), testMap.size() == rightMap.size());
        String s1 = toString(rightMap, true);
        String s2 = toString(testMap, ordered);
        System.out.println(s1);
        System.out.println(s2);
        if (testMap instanceof SimpleHashMap) {
            SimpleHashMap shMap = (SimpleHashMap) testMap;
            System.out.printf("capacity = %d%n", shMap.getCapacity());
            System.out.printf("loadFactor = %f%n", shMap.getLoadFactor());
            System.out.printf("avgCountInBackets = %f%n", shMap.getAvgCountInBackets());
            System.out.printf("maxCountInBuckets = %d%n", shMap.getMaxCountInBuckets());
        }
        System.out.printf("%s%n%n", s1.equals(s2));
    }

    public static void correctTest(Map<Integer, Integer> testMap, boolean ordered) {
        Map<Integer, Integer> rightMap = new TreeMap<>();
        testMap.clear();
        Random rnd = new Random();

        // ???????????????????? ??????????????????
        for (int i = 0; i < TEST_MAP_SIZE; i++) {
            int key = rnd.nextInt(MAX_KEY_VALUE);
            int value = rnd.nextInt(MAX_KEY_VALUE);
            rightMap.put(key, value);
            testMap.put(key, value);
            //System.out.printf("(%s, %s)%n", key, value);
        }
        printMapsState(rightMap, testMap, ordered);

        // ???????????????? ???????????????? ??????????????????
        Integer[] keys = rightMap.keySet().toArray(new Integer[0]);
        for (Integer key : keys) {
            if (rnd.nextInt(2) == 0) {
                rightMap.remove(key);
                testMap.remove(key);
                //System.out.printf("(%s)%n", key);
            }
        }
        printMapsState(rightMap, testMap, ordered);

        // ?????????????????? ???????????????????? ??????????????????
        for (int i = 0; i < TEST_MAP_SIZE / 2; i++) {
            int key = rnd.nextInt(MAX_KEY_VALUE);
            int value = rnd.nextInt(MAX_KEY_VALUE);
            rightMap.put(key, value);
            testMap.put(key, value);
        }
        printMapsState(rightMap, testMap, ordered);
    }

    public static void correctTest(Map<Integer, Integer> testMap) {
        correctTest(testMap, true);
    }
}
