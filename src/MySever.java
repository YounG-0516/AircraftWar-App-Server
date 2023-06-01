import Dao.Player;
import Dao.PlayerDaoImpl;
//import net.sf.json.JSONObject;
import com.sun.jdi.IntegerType;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MySever implements Runnable{

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private String request = "";
    public PlayerDaoImpl playerDao;
    public String userID;
    public String userPassword;
    public Player player;
    public static int count=0;
    public static HashMap<Integer,String> map = new HashMap<>();

    public MySever(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

        playerDao = new PlayerDaoImpl();
        player = new Player();
    }

    @Override
    public void run() {

        System.out.println("waiting client message");

        try {
            String content = "";
            while((content = reader.readLine())!=null){
                parserJson(content);

                // 解析客户端发送的数据
                switch (request){
                    case "register":
                        judgeRegister();
                        break;

                    case "login":
                        judgeLogin();
                        break;

                    default:
                        break;
                }

            }

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }

    //Json对象解析
    public void parserJson(String content) throws JSONException {
        // 将JSON数据转换为JSON对象
        JSONObject jsonObject=new JSONObject(content);

        // 从JSON对象中获取用户ID和密码进行验证
        request = jsonObject.getString("operation");
        userID = jsonObject.getString("ID");
        userPassword = jsonObject.getString("PSW");
        System.out.println(request+userID+userPassword);
    }

    private void judgeRegister() throws IOException {
        boolean flag1 = false;
        ArrayList<Player> playersList = playerDao.getPlayers();
        for(Player player1 : playersList){
            if(Objects.equals(player1.name, userID)){
                flag1 = true;
                writer.println("register_failed");
                System.out.println("register_failed");
                break;
            }
        }

        if(!flag1){
            playerDao.addPlayers(userID,userPassword);
            writer.println("register_success");
            System.out.println("register_success");
        }
    }

    private void judgeLogin() {
        boolean flag2 = false;

        ArrayList<Player> playersList = playerDao.getPlayers();
        for(Player player2 : playersList){
            if(Objects.equals(player2.name, userID)){
                flag2 = true;
                // 进行验证逻辑，根据结果发送相应的响应信息
                if (Objects.equals(player2.passWord, userPassword)) {
                    writer.println("login_success");
                    System.out.println("login_success");
                    count++;
                    map.put(count,userID);
                    System.out.println(count+userID);
                    break;
                }else{
                    writer.println("password_failed");
                    System.out.println("password_failed");
                }
            }
        }

        if(!flag2){
            writer.println("userID_failed");
            System.out.println("userID_failed");
        }
    }
}
