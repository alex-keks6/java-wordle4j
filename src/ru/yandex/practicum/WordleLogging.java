package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class WordleLogging {

    public static void writeLog(String fileName, Exception exp) {
        try (PrintWriter pw = new PrintWriter(fileName, StandardCharsets.UTF_8)) {
            exp.printStackTrace(pw);
            pw.println('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
