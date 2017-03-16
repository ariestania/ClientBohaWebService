/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ariestania.client.logger;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author ariestania.winda
 */
public class SoapLogHandler extends BasicHandler {

    @Override
    public void invoke(MessageContext mc) throws AxisFault {
        if (mc.getResponseMessage() != null && mc.getResponseMessage().getSOAPPartAsString() != null) {
            String resMsg = mc.getResponseMessage().getSOAPPartAsString();
            System.out.println("Response: \r\n" + resMsg);
            LogFactory.getLog("bohayMessage").info("Response: " + resMsg);
        } else if (mc.getRequestMessage() != null && mc.getRequestMessage().getSOAPPartAsString() != null) {
            String reqMsg = mc.getRequestMessage().getSOAPPartAsString();
            System.out.println("Request: \r\n" + reqMsg);
            LogFactory.getLog("bohayMessage").info("Request: " + reqMsg);
        }
    }
}
