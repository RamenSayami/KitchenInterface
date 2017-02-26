/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kitchen.Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import kitchen.Model.Order;
import kitchen.View.KitchenInterface;

/**
 * This class will handle all the message sending karekrams... 
 * @author Ramen
 */
public class Socket implements Runnable{
 
    
    public int port;
    public String serverAddr;
    public java.net.Socket socket;
    public KitchenInterface ui;
    public ObjectInputStream In;
    public ObjectOutputStream Out;
    private Order order;

    
    public Socket(KitchenInterface ki){
        ui=ki;
        serverAddr = "localhost";
        port=13000;
    }
    
    void SendToCashier(Order sendingOrder){
        this.order=sendingOrder;
        try {
            Out.writeObject(order);
            Out.flush();
            System.out.println("Outgoing : "+order.toString());
            
            if(msg.type.equals("message") && !msg.content.equals(".bye")){
                String msgTime = (new Date()).toString();
                try{
                    hist.addMessage(msg, msgTime);               
                    DefaultTableModel table = (DefaultTableModel) ui.historyFrame.jTable1.getModel();
                    table.addRow(new Object[]{"Me", msg.content, msg.recipient, msgTime});
                }
                catch(Exception ex){}
            }
        } 
        catch (IOException ex) {
            System.out.println("Exception SocketClient send()");
        }
        
        
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}