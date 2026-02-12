package ru.yandex.practicum;

import ru.yandex.practicum.exception.WordNotFoundInDictionaryException;
import ru.yandex.practicum.exception.WordNotMatchOnLengthException;

import java.io.PrintWriter;
import java.util.*;

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
    private String answer;
    private int stepsCount;
    private char[] charAnswerStatus;

    private WordleDictionary currentDictionary;

    public WordleGame(PrintWriter logger, WordleDictionary dictionary, int stepsCount,
                      WordleDictionary currentDictionary) {
        this.logger = logger;
        this.dictionary = dictionary;
        this.answer = dictionary.getRandomWord();
        this.stepsCount = stepsCount;
        charAnswerStatus = new char[answer.length()];
        Arrays.fill(charAnswerStatus, '-');
        this.currentDictionary = currentDictionary;
    }

    public int getStepsCount() {
        return stepsCount;
    }

    public String createHint(String userWord) {
        StringBuilder answerBuilder = new StringBuilder(answer);
        StringBuilder userWordBuilder = new StringBuilder(userWord);

        // проход для составления '+'
        createHintSymbolsPlus(answerBuilder, userWordBuilder);

        // проход для составления '^'
        createHintSymbolsCaret(answerBuilder, userWordBuilder);

        // замена оставшихся букв на '-' для создания подсказки
        replaceSymbolsOnDash(userWordBuilder);

        return userWordBuilder.toString();
    }

    public void createHintSymbolsPlus(StringBuilder targetWordBuilder, StringBuilder hintWordBuilder) {
        for (int i = 0; i < hintWordBuilder.length(); i++) {
            if (targetWordBuilder.charAt(i) == hintWordBuilder.charAt(i)) {
                hintWordBuilder.replace(i, i + 1, "+");
                targetWordBuilder.replace(i, i + 1, "_");
            }
        }
    }

    public void createHintSymbolsCaret(StringBuilder targetWordBuilder, StringBuilder hintWordBuilder) {
        for (int i = 0; i < hintWordBuilder.length(); i++) {
            int answerSymbolIndex = targetWordBuilder.indexOf(hintWordBuilder.substring(i, i + 1));
            if (answerSymbolIndex != -1) {
                hintWordBuilder.replace(i, i + 1, "^");
                targetWordBuilder.replace(answerSymbolIndex, answerSymbolIndex + 1, "_");
            }
        }
    }

    public void replaceSymbolsOnDash(StringBuilder hintWordBuilder) {
        for (int i = 0; i < hintWordBuilder.length(); i++) {
            if (hintWordBuilder.charAt(i) != '+' && hintWordBuilder.charAt(i) != '^') {
                hintWordBuilder.replace(i, i + 1, "-");
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

    public void lowerStep() {
        stepsCount--;
    }

    public void removeWordFromCurrentDictionary(String word) {
        currentDictionary.removeDictionaryWord(word);
    }

    public String getAnswer() {
        return answer;
    }

    public String getProgramWord() {
        return currentDictionary.getRandomWord();
    }

    public void clearCurrentDictionary() {
        StringBuilder testWord;
        boolean isRemoveWord;

        logger.println("Начато удаление неподходящих слов из словаря для подсказок");

        // удаление неподходящих под текущее положение игры слов
        for (int indexWord = 0; indexWord < currentDictionary.getDictionarySize(); ) {
            testWord = new StringBuilder(currentDictionary.getDictionaryWord(indexWord));
            isRemoveWord = false;

            // проверка по +
            for (int indexChar = 0; indexChar < charAnswerStatus.length; indexChar++) {
                if (charAnswerStatus[indexChar] == '+') {
                    if (testWord.charAt(indexChar) != answer.charAt(indexChar)) {
                        currentDictionary.removeDictionaryWord(indexWord);
                        isRemoveWord = true;
                        break;
                    } else {
                        testWord.replace(indexChar, indexChar + 1, "+");
                    }
                }
            }

            // если проверка пройдена и слово не удалено, то проверка по ^
            if (!isRemoveWord) {
                for (int indexChar = 0; indexChar < charAnswerStatus.length; indexChar++) {
                    if (charAnswerStatus[indexChar] == '^') {
                        int testWordSymbolIndex = testWord.indexOf(answer.substring(indexChar, indexChar + 1));
                        if (testWordSymbolIndex == -1) {
                            currentDictionary.removeDictionaryWord(indexWord);
                            isRemoveWord = true;
                            break;
                        } else {
                            testWord.replace(testWordSymbolIndex, testWordSymbolIndex + 1, "^");
                        }
                    }
                }

                // увеличение индекса в случае подходящего слова
                if (!isRemoveWord) {
                    indexWord++;
                }
            }
        }

        logger.printf("Удаление неподходящих слов из словаря для подсказок окончено. Осталось слов: %d\n",
                currentDictionary.getDictionarySize());

    }

    public void modificateCharAnswerStatus(String word) {
        // текущий статус ответа будет в answerBuilder
        StringBuilder answerBuilder = new StringBuilder(answer);
        StringBuilder userWordBuilder = new StringBuilder(word);

        // проход для составления +
        createHintSymbolsPlus(userWordBuilder, answerBuilder);

        // проход для составления ^
        createHintSymbolsCaret(userWordBuilder, answerBuilder);

        // объединение текущего ответа с глобальным состоянием слова
        for (int i = 0; i < charAnswerStatus.length; i++) {
            if (answerBuilder.charAt(i) == '+' && charAnswerStatus[i] != '+') {
                charAnswerStatus[i] = '+';
            } else if (answerBuilder.charAt(i) == '^' && charAnswerStatus[i] == '-') {
                charAnswerStatus[i] = '^';
            }
        }
    }

    // методы только для тестов
    public void setAnswer(String word) {
        this.answer = word;
    }

    public void setCurrentDictionary(WordleDictionary currentDictionary) {
        this.currentDictionary = currentDictionary;
    }

    public void setCharAnswerStatus(char[] charAnswerStatus) {
        this.charAnswerStatus = charAnswerStatus;
    }

    public char[] getCharAnswerStatus() {
        return charAnswerStatus;
    }

}
