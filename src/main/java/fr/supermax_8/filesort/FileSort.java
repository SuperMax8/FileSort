package fr.supermax_8.filesort;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FileSort {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify a path to sort.");
            return;
        }
        long start = System.currentTimeMillis();
        System.out.println("Starting sort...");
        System.out.println("Path: " + args[0]);
        try {
            sort(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Sort finished in " + (end - start) + "ms.");
    }


    public static void sort(String path) throws IOException {
        File dir = new File(path);
        File sortDir = new File(dir, "sorted");
        if (sortDir.exists()) {
            System.out.println("Sorted folder already exists.");
            return;
        }
        sortDir.mkdir();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            System.out.println(file.getName());
        }
        List<File> sorted = Arrays.stream(files).sorted(Comparator.comparing(f -> new Date(f.lastModified()))).toList();

        int i = 0;
        for (File file : sorted) {
            if (file.isDirectory()) {
                continue;
            }
            Files.copy(file.toPath(), new File(sortDir, i + file.getName()).toPath());
            i++;
        }
    }

}