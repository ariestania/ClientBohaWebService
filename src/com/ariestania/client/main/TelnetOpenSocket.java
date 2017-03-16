/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ariestania.client.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ariestania.winda
 */
public class TelnetOpenSocket extends Thread{
    
    private ServerSocket serverSocket;
    
    public TelnetOpenSocket(int port){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(TelnetOpenSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        Socket socket = null;
        while(true){
            try {
                socket = serverSocket.accept();
                if ("127.0.0.1".equals(socket.getInetAddress().getHostAddress())) {
                    new Thread(new TelnetWorker(socket)).start();
                } else {
                    BufferedWriter buffw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    buffw.write("ACCESS DENIED");
                    buffw.newLine();
                    buffw.flush();
                    socket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(TelnetOpenSocket.class.getName()).log(Level.SEVERE, null, ex);
                try {
                    sleep(999999);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(TelnetOpenSocket.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }            
        }
    }
    
    
}
