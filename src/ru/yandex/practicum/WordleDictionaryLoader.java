package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final PrintWriter logger;

    public WordleDictionaryLoader(PrintWriter logger) {
        this.logger = logger;
    }

    public WordleDictionary downloadDictionary(String fileName, int wordLength) {
        List<String> words = new ArrayList<>();
        String word;

        logger.println("Начата загрузка словаря из файла");

        try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            while (br.ready()) {
                word = br.readLine();
                if (word.length() == wordLength) {
                    words.add(word);
                }
            }
        } catch (IOException exp) {
            logger.println(exp.getMessage());
        }

        logger.printf("Загрузка словаря из файла окончена. Загружено %d слов\n", words.size());

        return new WordleDictionary(words, wordLength);
    }
}
