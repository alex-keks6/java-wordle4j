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
    private WordleDictionary dictionary;
    private String answer;
    private int steps;
    private List<String> answers;
    private int stepsCount;

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
        StringBuilder hint = new StringBuilder();

        for (int i = 0; i < userWord.length(); i++) {
            if (answer.contains(userWord.substring(i, i + 1))) {
                if (answer.charAt(i) == userWord.charAt(i)) {
                    hint.append("+");
                } else {
                    hint.append("^");
                }
            } else {
                hint.append("-");
            }
        }

        return hint.toString();
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
}
