package ru.netology;

import java.util.*;

public class Main {
    public static final int THREAD_COUNT = 1000;
    public static String LETTERS = "RLRFR";
    public static int LENGTH = 100;

    public static char NEED_COUNT_CHAR = 'R';
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {

        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(
                    () -> {
                        String tmp = generateRoute(LETTERS, LENGTH);
                        int count = (int) tmp.chars().filter(ch -> ch == NEED_COUNT_CHAR).count();
                        synchronized (sizeToFreq) {
                            if (sizeToFreq.containsKey(count)) {
                                sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                            } else {
                                sizeToFreq.put(count, 1);
                            }
                        }
                    }
            ).start();
        }

        List<Map.Entry<Integer, Integer>> result = sizeToFreq.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .toList();

        Iterator<Map.Entry<Integer, Integer>> iterator = result.iterator();
        Map.Entry<Integer, Integer> entry = iterator.next();
        System.out.println("Самое частое количество повторений " + result.get(0).getKey() + " (встретилось " + result.get(0).getValue() + " раз)");

        System.out.println("Другие размеры:");
        while (iterator.hasNext()) {
            entry = iterator.next();
            System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}