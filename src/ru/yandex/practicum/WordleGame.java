package ru.yandex.practicum;

import ru.yandex.practicum.exception.WordNotFoundInDictionaryException;
import ru.yandex.practicum.exception.WordNotMatchOnLengthException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {
    private final PrintWriter logger;
    private final WordleDictionary dictionary;
    private final String answer;
    private final int steps;
    private final List<String> answers;
    private final int stepsCount;
    // нужен список букв, которые уже точно в слове, плюс для некоторых букв угаданных их положение (как?)
    // на основе этого + использованных игроком слов (их не включать)
    // выдавать слово и считать его ответом пользователя

    public WordleGame(PrintWriter logger, WordleDictionary dictionary, int stepsCount) {
        this.logger = logger;
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.steps = 0;
        answers = new ArrayList<>();
        this.stepsCount = stepsCount;
    }

    public int getSteps() {
        return steps;
    }

    public int getStepsCount() {
        return stepsCount;
    }

    public String createHint(String userWord) {
        StringBuilder answerBuilder = new StringBuilder(answer);
        StringBuilder userWordBuilder = new StringBuilder(userWord);

        // проход для составления +
        createHintSymbolsCorrect(answerBuilder, userWordBuilder);

        // проход для составления ^
        createHintSymbolsExist(answerBuilder, userWordBuilder);

        return userWordBuilder.toString();
    }

    public void createHintSymbolsCorrect(StringBuilder answerBuilder, StringBuilder userWordBuilder) {
        for (int i = 0; i < userWordBuilder.length(); i++) {
            if (answerBuilder.charAt(i) == userWordBuilder.charAt(i)) {
                userWordBuilder.replace(i, i + 1, "+");
                answerBuilder.replace(i, i + 1, " ");
            }
        }
    }

    public void createHintSymbolsExist(StringBuilder answerBuilder, StringBuilder userWordBuilder) {
        for (int i = 0; i < userWordBuilder.length(); i++) {
            int answerSymbolIndex = answerBuilder.indexOf(userWordBuilder.substring(i, i + 1));
            if (answerSymbolIndex != -1) {
                userWordBuilder.replace(i, i + 1, "^");
                answerBuilder.replace(answerSymbolIndex, answerSymbolIndex + 1, " ");
            } else {
                if (userWordBuilder.charAt(i) != '+') {
                    userWordBuilder.replace(i, i + 1, "-");
                }
            }
        }
    }

    public void validateUserWord(String userWord)
            throws WordNotMatchOnLengthException, WordNotFoundInDictionaryException {
        if (userWord.length() != dictionary.getWordLength()) {
            throw new WordNotMatchOnLengthException("Введенное слово не подходит по длине");
        }
        if (!dictionary.containsWord(userWord)) {
            throw new WordNotFoundInDictionaryException("Введенного слова нет в словаре");
        }
    }

    public void addStep() {
        steps++;
    }

    public void addNewWord(String word) {
        answers.add(word);
    }

    public String getAnswer() {
        return answer;
    }

    public String getProgramWord() {

    }
}
