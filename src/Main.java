import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * 服务器的创建及初始化
 * @author young
 */

public class Main {
    public static List<Socket> mList = new ArrayList<>();
    public static void main(String[] args) throws IOException{
        int port1 = 9999; // 第一个端口号
        int port2 = 8888; // 第二个端口号

        ServerSocket serverSocket1 = new ServerSocket(port1);
        ServerSocket serverSocket2 = new ServerSocket(port2);

        System.out.println(InetAddress.getLocalHost());

        // 启动两个线程处理连接请求
        Thread userThread = new Thread(() -> {
            try {
                acceptConnections1(serverSocket1);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Thread gameThread = new Thread(() -> {
            try {
                acceptConnections2(serverSocket2);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        userThread.start();
        gameThread.start();

        /**
        ServerSocket s = new ServerSocket(9999);
        System.out.println(InetAddress.getLocalHost());
        
        while(true){
            System.out.println("Waiting client connect");
            Socket socket = s.accept();
            System.out.println("accept client connect " + socket);
            new Thread(new MySever(socket)).start();
        }
        */
    }

    private static void acceptConnections1(ServerSocket serverSocket) throws IOException, ClassNotFoundException {
        while (true) {
            // 进行连接请求的接受
            Socket socket = serverSocket.accept();
            // 处理Socket连接
            new Thread(new MySever(socket)).start();
        }
    }

    private static void acceptConnections2(ServerSocket serverSocket) throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("Waiting client connect");
            // 进行连接请求的接受
            Socket socket1 = serverSocket.accept();
            Socket socket2 = serverSocket.accept();
            mList.add(socket1);
            mList.add(socket2);
            System.out.println(mList.size());

            // 处理Socket连接
            new Thread(new OnlineSever(socket1,socket2,MySever.map.get(1),MySever.map.get(2))).start();
            new Thread(new OnlineSever(socket2,socket1,MySever.map.get(2),MySever.map.get(1))).start();
        }
    }

}