package com.groupchat.code;

import java.io.*;
import java.net.Socket;

public class ServerReaderThread extends Thread {
    private final Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //从socket得到输入字节流，并包装成数据流
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            while (true) {
                try {
                    //读取数据并转发
                    String msg = dis.readUTF();
                    System.out.println(msg);
                    transferToOthers(msg);
                } catch (IOException e) {
                    Server.onlineSockets.remove(socket);
                    transferToOthers(socket.getLocalAddress() + "下线了");
                    dis.close();
                    socket.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void transferToOthers(String msg) {
        for (Socket socket : Server.onlineSockets) {
            //向管道中发送消息
            try {
                OutputStream os = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeUTF(msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
