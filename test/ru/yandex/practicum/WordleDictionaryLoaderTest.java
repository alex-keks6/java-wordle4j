package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

class WordleDictionaryLoaderTest {

    private static final String DICTIONARY_FILE_NAME = "words_ru.txt";
    PrintWriter logger = new PrintWriter(System.out, true);

    @Test
    public void shouldDownload4159WordWithLength5() {
        WordleDictionaryLoader wdl = new WordleDictionaryLoader(logger);

        WordleDictionary dictionary = wdl.downloadDictionary(DICTIONARY_FILE_NAME, 5);

        Assertions.assertEquals(4159, dictionary.getDictionarySize());
    }

}
