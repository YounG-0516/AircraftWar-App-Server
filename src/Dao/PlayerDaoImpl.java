package Dao;


import java.util.ArrayList;
import java.util.HashMap;

public class PlayerDaoImpl {


    private ArrayList<Player> players;

    public PlayerDaoImpl(){
        players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        HashMap<String, String> list = MyDataBase.getAllUser();
        for(String i : list.keySet()){
            Player player = new Player();
            player.name=i;
            player.passWord=list.get(i);
            players.add(player);
        }
        return players;
    }

    public void addPlayers(String name,String password){
        MyDataBase.createNewAccount(name,password);
    }


    /**
    private ArrayList<Player> players = new ArrayList<>();

    public PlayerDaoImpl() throws IOException, ClassNotFoundException{
        File file = new File("verify");
        if(file.exists()){
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            players =(ArrayList<Player>) ois.readObject();
            ois.close();
        }else{
            file.createNewFile();
        }
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void add(Player player) {
        players.add(player);
    }

    public void storage() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("verify"));
        oos.writeObject(players);
        oos.flush();
        oos.close();
    }
    */
}