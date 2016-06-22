package ru.badboy.uno;

/**
 * Created by Евгений on 02.01.2016.
 */
public class Player {
    private String name;
    private int id;
    private static int playerCount = 0;
    private int currentScore;
    private int totalScore;

    public int getId() {
        return id;
    }

    public Player(String name) {
        this.currentScore = 0;
        this.totalScore = 0;
        this.name = name;
        this.id = playerCount;
        playerCount++;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
