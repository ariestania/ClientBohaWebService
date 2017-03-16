/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ariestania.client.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ariestania.winda
 */
public class Main {

    public static void main(String[] args) {
        TelnetOpenSocket telnetOpenSocket = new TelnetOpenSocket(8889);
        telnetOpenSocket.start();
    }

}
