package ru.yandex.practicum;

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
    private final String logsFileName;
    private WordleDictionary dictionary;
    private String answer;
    private int steps;
    private List<String> userAnswers;

    public WordleGame(String logsFileName, WordleDictionary dictionary) {
        this.logsFileName = logsFileName;
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.steps = 0;
        userAnswers = new ArrayList<>();
    }



}
