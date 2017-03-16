/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ariestania.client.main;

import com.ariestania.example.HelloBohayWebServicePortBindingStub;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.axis.AxisFault;

/**
 *
 * @author ariestania.winda
 */
public class TelnetWorker extends Thread {

    private BufferedReader buffRead;
    private BufferedWriter buffWrite;
    private Socket socket;

    public TelnetWorker(Socket socket) {
        this.socket = socket;
        try {
            buffRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            buffWrite = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ex) {
            Logger.getLogger(TelnetWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            showOptions();
            String chsOpt = buffRead.readLine();
            processChosed(chsOpt);
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(TelnetWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showOptions() {
        try {
            buffWrite.write("Welcome to ClientTest BohayWebService. Please select your action:\r\n"
                    + "1 - Send Hello Operation\r\n"
                    + "2 - Send doSummary Operation\r\n"
                    + "3 - Exit\r\n");
            buffWrite.newLine();
            buffWrite.write("Please choose your action:\r\n");
            buffWrite.flush();
        } catch (IOException ex) {
            Logger.getLogger(TelnetWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void processChosed(String chsString) throws IOException {
        switch (chsString) {
            case "1": //send hello operation
                buffWrite.write("Please input your name : \r\n");
                buffWrite.newLine();
                buffWrite.flush();
                String nm = buffRead.readLine();
                buffWrite.newLine();
                buffWrite.write("Result : " + sendHelloMsg(nm));
                buffWrite.newLine();
                buffWrite.newLine();
                showOptions();
                String chsOpt = buffRead.readLine();
                processChosed(chsOpt);
                break;
            case "2": //send doSummary operation
                buffWrite.write("Please input 2 number separated by | : \r\n");
                buffWrite.newLine();
                buffWrite.flush();
                String dta = buffRead.readLine();
                buffWrite.newLine();
                buffWrite.write("Result : " + sendDoSummaryMsg(dta));
                buffWrite.newLine();
                buffWrite.newLine();
                showOptions();
                String chsOpts = buffRead.readLine();
                processChosed(chsOpts);
                break;
            case "3": //exit
                buffWrite.write("Bye..");
                buffWrite.newLine();
                buffWrite.flush();
                break;
            default:
                buffWrite.write("Invalid option");
                buffWrite.newLine();
                buffWrite.flush();
                showOptions();
                String optdt = buffRead.readLine();
                processChosed(optdt);
        }
    }

    private String sendHelloMsg(String name) {
        String result = "";
        try {
            URL url = new URL("http://localhost:8084/BohayWebService/HelloBohayWebService");
            HelloBohayWebServicePortBindingStub stub = new HelloBohayWebServicePortBindingStub(url, null);
            result = stub.hello(name);
        } catch (MalformedURLException | AxisFault ex) {
            Logger.getLogger(TelnetWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TelnetWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    private int sendDoSummaryMsg(String dta) {
        String dt[] = dta.split("\\|");
        int x = Integer.parseInt(dt[0]);
        int y = Integer.parseInt(dt[1]);
        int result = 0;

        try {
            URL url = new URL("http://localhost:8084/BohayWebService/HelloBohayWebService");
            HelloBohayWebServicePortBindingStub stub = new HelloBohayWebServicePortBindingStub(url, null);
            result = stub.doSummary(x, y);
        } catch (MalformedURLException | AxisFault ex) {
            Logger.getLogger(TelnetWorker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TelnetWorker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

}
