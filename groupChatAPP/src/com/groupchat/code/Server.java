package com.groupchat.code;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    //记录在线的Client，用于转发消息
    public static ArrayList<Socket> onlineSockets = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        //ServerSocket对象并指定服务器端口
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("=======服务器启动成功！=====");
        while (true) {
            //检测是否有客户端请求连接
            Socket socket = serverSocket.accept();
            //将socket加入在线的sockets
            // 新建一个服务器线程与客户端建立连接
            System.out.println(socket.getLocalAddress() + "上线了");
            onlineSockets.add(socket);
            new ServerReaderThread(socket).start();
        }
    }
}
