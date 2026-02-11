package ru.yandex.practicum;

import ru.yandex.practicum.exception.WordNotFoundInDictionaryException;

import java.util.List;
import java.util.Map;
import java.util.Random;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words;
    private final int wordLength;

    public WordleDictionary(List<String> words, int wordLength) {
        this.words = words;
        this.wordLength = wordLength;
    }

    public String getRandomWord() {
        Random random = new Random();
        int indexRandomWord = random.nextInt(words.size());

        return words.get(indexRandomWord);
    }

    public int getWordLength() {
        return wordLength;
    }

    public boolean containsWord(String word) {
        return words.contains(word);
    }

    public int getDictionarySize() {
        return words.size();
    }

    public String getDictionaryWord(int index) {
        return words.get(index);
    }

    public void removeDictionaryWord(int index) {
        words.remove(index);
    }
}
