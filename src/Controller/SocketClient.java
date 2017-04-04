package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import View.KitchenInterface;

import com.example.ramen.menu.Model.IdentifySelf;
import com.example.ramen.menu.Model.Order;

/**
 *
 * @author shreejal
 */
public class SocketClient implements Runnable {
	public int port;
	public String serverAddr;
	public Socket socket;
//	public CashierInterface ui;
	public KitchenInterface ui;
	public ObjectInputStream In;
	public ObjectOutputStream Out;

	public SocketClient(KitchenInterface frame) throws IOException {
		ui = frame;
		this.serverAddr = ui.serverAddr;
		this.port = ui.port;
		socket = new Socket(InetAddress.getByName(serverAddr), port);

		Out = new ObjectOutputStream(socket.getOutputStream());
		// Out.flush();
		In = new ObjectInputStream(socket.getInputStream());

		IdentifySelf id = new IdentifySelf();
		id.setName("Kitchen");
		Out.writeObject(id);
		Out.flush();

	}

	@Override
	public void run() {
		
		while (socket.isConnected()) {
			try {
				Order msg = (Order) In.readObject();
				ui.addToOrderList(msg);

			} catch (IOException | ClassNotFoundException e) {
			}

		}
	}

	public void send(Order order) {
		try {
			Out.writeObject(order);
			Out.flush();
			System.out.println("Outgoing : " + order.toString());

			
		} catch (IOException ex) {
			System.out.println("Exception SocketClient send()");
		}
	}

	public void closeThread(Thread t) {
		t = null;
	}

}
