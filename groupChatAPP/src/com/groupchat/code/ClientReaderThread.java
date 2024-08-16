package com.groupchat.code;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

//从管道读取信息并展示
public class ClientReaderThread extends Thread {
    private Socket socket;

    public ClientReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //从socket得到输入字节流，并包装成数据流
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            while(true){
                try {
                    String msg = dis.readUTF();
                    System.out.println(msg);
                } catch (IOException e) {
                    dis.close();
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
