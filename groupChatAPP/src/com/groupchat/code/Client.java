package com.groupchat.code;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入您的姓名：");
        String name = sc.next();
        //主线程用于发送消息，子线程用于接收消息
        Socket socket = new Socket("127.0.0.1", 8888);
        //创建子线程
        new ClientReaderThread(socket).start();

        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        while (true) {
            String msg = sc.next();
            if (msg.equals("exit")) {
                dos.close();
                socket.close();
                System.out.println("退出成功！");
                break;
            }
            dos.writeUTF(name + "： " + msg);
            dos.flush();
        }
    }
}
