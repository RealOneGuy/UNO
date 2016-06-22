package ru.badboy.uno;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Евгений on 02.01.2016.
 */
public class UnoGame {
    private List<Player> players;
    private List<Integer> currentScores;
    private List<List<Integer>> history;
    private boolean over;
    private int limit;
    private List<Player> winners;
    private List<Player> losers;
    private int maxScore;
    private int minScore;
    private int round;



    public UnoGame(List<Player> players, int limit) {
        this.players = players;
        this.limit = limit;
        winners = new ArrayList<>();
        losers = new ArrayList<>();
        maxScore = 0;
        minScore = 0;
        round = 0;
        over = false;
        currentScores = new ArrayList<>();
        history = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            currentScores.add(0);
        }
        addScoresToHistory();
    }

    public void oneStepAdd(List<Integer> currentScores){ //нажатие кнопки Добавить

        for (int i = 0; i < this.currentScores.size(); i++) {//добавляем очки
            this.currentScores.set(i,this.currentScores.get(i)+currentScores.get(i));
        }
        addScoresToHistory(); //обновляем таблицу
        round++;

        for (int i = 0; i < this.currentScores.size(); i++) { //определяем, закончена ли игра
            if (this.currentScores.get(i) >= limit){
                over = true;
                break;
            }
        }

        findMaxScore();
        findMinScore();
        whoIsLooser(); //определяем победителя(-ей)
        whoIsWinner(); //определяем проигравшего(-их)
    }

    public void oneStepUndo(){
        removeLastScores();
        findMaxScore();
        findMinScore();
        whoIsLooser(); //определяем победителя(-ей)
        whoIsWinner(); //определяем проигравшего(-их)
        over = false;
    }

    public void oneStepClear(){
        winners.clear();
        losers.clear();
        maxScore = 0;
        minScore = 0;
        round = 0;
        over = false;
        for (int i = 0; i < currentScores.size(); i++) {
            currentScores.set(i,0);
        }
        history.clear();
        addScoresToHistory();
    }

    public void addScoresToHistory(){
        List<Integer> curScores = new ArrayList<>(currentScores);
        history.add(curScores);
    }

    public void findMaxScore(){
        int maxCurScore = currentScores.get(0); //находим максимальное число очков
        for (int i = 1; i < currentScores.size(); i++){
            if (currentScores.get(i)>maxCurScore){
                maxCurScore = currentScores.get(i);
            }
        }
        maxScore = maxCurScore;
    }

    public void findMinScore(){
        int minCurScore = currentScores.get(0); //находим минимальное число очков
        for (int i = 1; i < currentScores.size(); i++){
            if (currentScores.get(i)<minCurScore){
                minCurScore = currentScores.get(i);
            }
        }
        minScore = minCurScore;
    }

    public void removeLastScores(){
        if (round>0) {
            history.remove(round);
            round--;
            setCurrentScores(history.get(round));
        }
    }

    public void whoIsLooser(){ //определяем проигравших или проигрывающих
        losers.clear();
        for (int i = 0; i < currentScores.size(); i++) {
            if (currentScores.get(i)==maxScore){
                losers.add(players.get(i));
            }
        }
    }

    public void whoIsWinner(){
        winners.clear();
        for (int i = 0; i < currentScores.size(); i++) {
            if (currentScores.get(i)==minScore){
                winners.add(players.get(i));
            }
        }
    }

    public List<Player> getWinners() {
        return winners;
    }

    public List<Player> getLosers() {
        return losers;
    }

    public boolean isOver(){
        return over;
    }

    public List<Integer> getCurrentScores() {
        return currentScores;
    }

    public void setCurrentScores(List<Integer> currentScores) {
        this.currentScores = new ArrayList<>(currentScores);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<List<Integer>> getHistory() {
        return history;
    }

    public List<Integer> getLosersNumbers(){
        List<Integer> losersNumbers = new ArrayList<>();
        for (int i = 0; i < losers.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                if (losers.get(i).getName().equals(players.get(j).getName())){
                    losersNumbers.add(j);
                    break;
                }
            }
        }
        return losersNumbers;
    }

    public List<Integer> getWinnersNumbers(){
        List<Integer> winnersNumbers = new ArrayList<>();
        for (int i = 0; i < winners.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                if (winners.get(i).getName().equals(players.get(j).getName())){
                    winnersNumbers.add(j);
                    break;
                }
            }
        }
        return winnersNumbers;
    }

    public int getRound() {
        return round;
    }
}
