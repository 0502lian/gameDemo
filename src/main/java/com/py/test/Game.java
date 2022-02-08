package com.py.test;

import java.util.*;

/**
 * @Author: py
 *
 */
public class Game {
    /**
     * 玩家list
     */
    private List<Player> players = new ArrayList<>();
    /**
     * 玩家人数
     */
    private Integer playerNum;
    /**
     * 游戏轮数
     */
    private Integer rounds;
    /**
     * 每轮随机数汇总
     */
    private List<Integer> correctNumbers = new ArrayList<>();

    /**
     * 每轮赢家记录
     */
    private Map<Integer, List<Player>> winners = new HashMap<>();

    public static final String YES = "yes";

    private Random random = new Random();

    public Game(Integer playerNum, Integer rounds) {
        this.playerNum = playerNum;
        this.rounds = rounds;
    }


    private void addWinner(Integer round, Player winner){
        List<Player> players = winners.get(round);
        if(players == null){
            players = new ArrayList<>();
        }
        players.add(winner);
        winners.put(round, players);
    }


    public Integer getPlayerNum() {
        return playerNum;
    }


    public Integer getRounds() {
        return rounds;
    }

    public void prepareAndPlayGames(Scanner in){
        enterPlayerName(in);
        playGames(in);
    }


    /**
     *
     * @param in
     */
    public void playGames(Scanner in){
        for (int i = 0; i < getRounds(); i++) {
            System.out.println(String.format("Round %d", i+1 ));
            playerEnterNum(in);
        }
        System.out.println("##########################################################");
        for (int i = 0; i < getRounds(); i++) {
            playGame(i);
        }
        System.out.println("##########################################################");
        printPlayersHistory();
        printOverallWinner();
        playAgain(in);
    }

    public void playAgain(Scanner in){
        System.out.println("play again? yes or no");
        if(in.next().toLowerCase().trim().equals(YES)){
            for (Player player : players) {
                player.reset();
            }
            correctNumbers.clear();
            winners.clear();
            System.out.println("######################### again #################################");
            playGames(in);
        } else {
            System.out.println("game over");
        }
    }


    /**
     * 输入玩家名称
     * @param in
     */
    public void enterPlayerName( Scanner in){
        Set<String> names = new HashSet<>();
        for (int i = 0; i < getPlayerNum(); i++) {
            Player player = new Player();
            System.out.print("please enter player name :");
            while (in.hasNextLine()){
                String name = in.nextLine();
                if(names.add(name)){
                    player.setName(name);
                    break;
                } else {
                    System.out.print(String.format("name %s exist ,please enter another :", name));
                }
            }
            players.add(player);
        }
        System.out.println("##########################################################");
    }


    /**
     * 打印玩家历史记录
     */
    public void printPlayersHistory(){
        for (int i = 0; i < getPlayerNum(); i++) {
            Player player = players.get(i);
            System.out.println(String.format("Player name : %s ", player.getName()));
            for (int j = 0; j < getRounds(); j++) {
                System.out.println(String.format(" Round %d Guess value:%d Correct value:%d ", j , player.getInputNums().get(j), correctNumbers.get(j)));
            }
            System.out.println(String.format("Your score is: %d ", player.getScore()));
        }
        System.out.println("##########################################################");
    }

    /**
     * 打印最终玩家
     */
    public void printOverallWinner(){
        int largestScore = 0;
        List<Player> winners = new ArrayList<>();
        for (int i = 0; i < getPlayerNum(); i++) {
            if(players.get(i).getScore() > largestScore){
                largestScore = players.get(i).getScore();
                winners.clear();
                winners.add(players.get(i));
            } else if(players.get(i).getScore() == largestScore){
                winners.add(players.get(i));
            }
        }
        System.out.println("The Overall Winner/s :");
        if(winners.size() > 0 && largestScore != 0){
            for (Player player : winners){
                System.out.println(String.format("Player : %s", player.getName()));
            }
        } else {
            System.out.println("No Winner");
        }
        System.out.println("##########################################################");
    }



    /**
     * 玩游戏
     * @param
     */
    private void playGame(Integer round){
        Integer guess = generateRandom();
        correctNumbers.add(guess);
        System.out.println(String.format("Guess Number for round %d : %d", round+1 , guess));
        for (int i = 0; i < getPlayerNum(); i++) {
            if(guess.equals(players.get(i).getInputNums().get(round))){
                players.get(i).setScore(players.get(i).getScore() + 1);
                addWinner(round, players.get(i));
            }
        }
        System.out.println(" Winner/s :");
        List<Player> players = winners.get(round);
        if(players == null){
            System.out.println(" No Winner");
        } else {
            for (Player player : players){
                System.out.println(String.format(" Player : %s", player.getName()));
            }
        }

    }

    /**
     * 获取1-10的随机数
     * @return
     */
    public Integer generateRandom(){
        return random.nextInt(10) + 1;
    }

    /**
     * 玩家输入数据
     * @param in
     */
    public void playerEnterNum( Scanner in){
        for (int i = 0; i < getPlayerNum(); i++) {
            Player player = players.get(i);
            System.out.print(String.format("please enter your guess player %s : " , player.getName()));
            while (in.hasNext()){
                if(in.hasNextInt()){
                    int guess = in.nextInt();
                    if(guess > 0 && guess < 11){
                        player.addInputNum(guess);
                        break;
                    } else {
                        System.out.print("please enter 1-10 number :");
                    }
                } else {
                    in.next();
                    System.out.print("please enter 1-10 number :");
                }
            }
        }
    }


}
