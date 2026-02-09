package ru.yandex.practicum;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {
    private static final String LOGS_FILE_NAME = "logs.txt";
    private static final String DICTIONARY_FILE_NAME = "words_ru.txt";

    public static void main(String[] args) {

        try (FileOutputStream fos = new FileOutputStream(LOGS_FILE_NAME);
             Writer writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {

            PrintWriter logger = new PrintWriter(writer, true);

            WordleDictionaryLoader wdl = new WordleDictionaryLoader(LOGS_FILE_NAME);
            WordleDictionary dictionary = wdl.downloadDictionary(DICTIONARY_FILE_NAME);

            WordleGame game = new WordleGame(LOGS_FILE_NAME, dictionary);
            WordleGameStarter starter = new WordleGameStarter();
            starter.start(game);

        } catch (Exception exp) {
            // todo
        }

    }

    public static void playWordleGame(WordleGame game, WordleDictionary dictionary) {
        String userWord;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Игра началась! Угадайте загаданное слово");
        while (true) {
            System.out.printf("Количество попыток: %d\n", game.);
        }
    }

}
