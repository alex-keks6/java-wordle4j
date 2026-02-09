package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final String logsFileName;

    public WordleDictionaryLoader(String logsFileName) {
        this.logsFileName = logsFileName;
    }

    public WordleDictionary downloadDictionary(String fileName) {
        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            while (br.ready()) {
                words.add(br.readLine());
            }
        } catch (IOException exp) {
            WordleLogging.writeLog(logsFileName, exp);
        }

        return new WordleDictionary(words);
    }
}
