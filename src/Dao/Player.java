package Dao;

import java.io.Serializable;

public class Player implements Serializable{
    private static final Player instance = new Player();

    public int score;
    public int hp;
    public String name;
    public String passWord = null;

    public static Player getInstance(){
        return instance;
    }
}