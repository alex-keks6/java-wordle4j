package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exception.WordNotMatchOnLengthException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WordleGameTest {
    private static final String DICTIONARY_FILE_NAME = "words_ru.txt";
    private static final int WORD_LENGTH = 5;
    private static final int STEPS_COUNT = 6;
    WordleGame game;

    @BeforeEach
    void createGame() {
        PrintWriter logger = new PrintWriter(System.out, true);

        WordleDictionaryLoader wdl = new WordleDictionaryLoader(logger);
        WordleDictionary dictionary = wdl.downloadDictionary(DICTIONARY_FILE_NAME, WORD_LENGTH);
        WordleDictionary currentDictionary = wdl.downloadDictionary(DICTIONARY_FILE_NAME, WORD_LENGTH);

        game = new WordleGame(logger, dictionary, STEPS_COUNT, currentDictionary);
    }

    @Test
    public void shouldCreateCorrectHintWithPlus() {
        StringBuilder targetWordBuilder = new StringBuilder("почка");
        StringBuilder hintWordBuilder = new StringBuilder("кочка");

        game.createHintSymbolsPlus(targetWordBuilder, hintWordBuilder);

        Assertions.assertEquals("п____", targetWordBuilder.toString(),
                "Неправильная обработка целевого слова");
        Assertions.assertEquals("к++++", hintWordBuilder.toString(),
                "Неправильная обработка слова-подсказки");
    }

    @Test
    public void shouldCreateCorrectHintWithoutPlus() {
        StringBuilder targetWordBuilder = new StringBuilder("трава");
        StringBuilder hintWordBuilder = new StringBuilder("кочки");

        game.createHintSymbolsPlus(targetWordBuilder, hintWordBuilder);

        Assertions.assertEquals("трава", targetWordBuilder.toString(),
                "Неправильная обработка целевого слова");
        Assertions.assertEquals("кочки", hintWordBuilder.toString(),
                "Неправильная обработка слова-подсказки");
    }

    @Test
    public void shouldCreateCorrectHintWithCaret() {
        StringBuilder targetWordBuilder = new StringBuilder("около");
        StringBuilder hintWordBuilder = new StringBuilder("ворон");

        game.createHintSymbolsCaret(targetWordBuilder, hintWordBuilder);

        Assertions.assertEquals("_к_ло", targetWordBuilder.toString(),
                "Неправильная обработка целевого слова");
        Assertions.assertEquals("в^р^н", hintWordBuilder.toString(),
                "Неправильная обработка слова-подсказки");
    }

    @Test
    public void shouldCreateCorrectHintWithoutCaret() {
        StringBuilder targetWordBuilder = new StringBuilder("около");
        StringBuilder hintWordBuilder = new StringBuilder("арбуз");

        game.createHintSymbolsCaret(targetWordBuilder, hintWordBuilder);

        Assertions.assertEquals("около", targetWordBuilder.toString(),
                "Неправильная обработка целевого слова");
        Assertions.assertEquals("арбуз", hintWordBuilder.toString(),
                "Неправильная обработка слова-подсказки");
    }

    @Test
    public void shouldReplaceSymbolsOnDash() {
        StringBuilder hintWordBuilder = new StringBuilder("а^б+з");

        game.replaceSymbolsOnDash(hintWordBuilder);

        Assertions.assertEquals("-^-+-", hintWordBuilder.toString(),
                "Неправильная обработка слова-подсказки");
    }

    @Test
    void shouldCreateCorrectHint() {
        String answer = "трава";
        String userWord = "арбуз";
        String hint;

        game.setAnswer(answer);
        hint = game.createHint(userWord);

        Assertions.assertEquals("^+---", hint);
    }

    @Test
    void shouldValidateUserWordAndReturnWordNotMatchOnLengthException() {
        String word = "вишенка";
        String exceptionTest = "";

        try {
            game.validateUserWord(word);
        } catch (Exception exp) {
            exceptionTest = exp.getMessage();
        }

        Assertions.assertEquals("Введенное слово не подходит по длине", exceptionTest);
    }

    @Test
    void shouldValidateUserWordAndReturnWordNotFoundInDictionaryException() {
        String word = "абвгд";
        String exceptionTest = "";

        try {
            game.validateUserWord(word);
        } catch (Exception exp) {
            exceptionTest = exp.getMessage();
        }

        Assertions.assertEquals("Введенного слова нет в словаре", exceptionTest);

    }

    @Test
    void shouldClearCurrentDictionary() {
        String answer = "трава";
        List<String> words = new ArrayList<>();
        words.add("клара");
        words.add("трава");
        words.add("плато");
        words.add("ковер");
        WordleDictionary currentDictionary = new WordleDictionary(words, 5);

        game.setCharAnswerStatus(new char[]{'-', '^', '+', '-', '-'});
        game.setAnswer(answer);
        game.setCurrentDictionary(currentDictionary);

        game.clearCurrentDictionary();

        Assertions.assertEquals(2, currentDictionary.getDictionarySize());
    }

    @Test
    void shouldModificateCharAnswerStatus() {
        String answer = "трава";
        String word = "арест";

        game.setCharAnswerStatus(new char[]{'-', '^', '+', '-', '-'});
        game.setAnswer(answer);

        game.modificateCharAnswerStatus(word);

        Assertions.assertArrayEquals(new char[]{'^', '+', '+', '-', '-'}, game.getCharAnswerStatus());
    }
}
