package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
    private static final int WORD_LENGTH = 5;
    private static final int STEPS_COUNT = 6;

    public static void main(String[] args) {

        try (FileWriter fw = new FileWriter(LOGS_FILE_NAME, StandardCharsets.UTF_8)) {

            PrintWriter logger = new PrintWriter(fw, true);

            WordleDictionaryLoader wdl = new WordleDictionaryLoader(logger);
            WordleDictionary dictionary = wdl.downloadDictionary(DICTIONARY_FILE_NAME, WORD_LENGTH);

            WordleGame game = new WordleGame(logger, dictionary, STEPS_COUNT);
            startGame(game);

        } catch (Exception exp) {
            exp.printStackTrace();
        }

    }

    public static void startGame(WordleGame game) {
        String word;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Игра началась! Угадайте загаданное слово");

        System.out.println("ТЕСТ: слово " + game.getAnswer());

        while (game.getStepsCount() > game.getSteps()) {
            System.out.printf("Осталось попыток: %d\n", (game.getStepsCount() - game.getSteps()));
            word = scanner.nextLine();

            // проверка на пустую строку и вызов получения слова-подсказки от программы
            if (word.isEmpty()) {
                // todo
            }

            // валидация (есть ли слово в словаре) (возврат boolean)
            try {
                game.validateUserWord(word);

                if (game.getAnswer().equals(word)) {
                    System.out.println("Вы угадали! Победа!");
                    break;
                }

                // проверка-сравнение слова пользователя и ответа (возврат подсказки)
                System.out.println(game.createHint(word));

                // занесение ответа в ответы пользователя
                game.addNewWord(word);

            } catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
            // изменение шагов
            game.addStep();
        }

        if (game.getStepsCount() == game.getSteps()) {
            System.out.printf("Вы проиграли. Неугаданное слово: %s\n", game.getAnswer());
        }
    }

}
