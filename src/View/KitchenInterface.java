package View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import Controller.SocketClient;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.example.ramen.menu.Model.Order;
import com.example.ramen.menu.Model.OrderStatus;

/**
 *
 * @author Ramen
 */
public class KitchenInterface extends javax.swing.JFrame {
	private static Order order;

	public List<Order> orderInList = new ArrayList<Order>();
	public List<Order> deliveriesList = new ArrayList<Order>();

	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	public Thread clientThread;
	DataInputStream dis = null;

	public String serverAddr;
	public int port;
	public SocketClient client;


	public KitchenInterface() {
		initComponents();
		
//		ActorSystem system = ActorSystem.create();
//		
//		ActorRef master = system.actorOf(Props.create(FindServer.class));
//		master.tell(n, null);
//		
		
//		this.serverAddr = "172.16.4.138";
//		this.port = 13000;
//		try {
//			client = new SocketClient(this);
//			clientThread = new Thread(client);
//			clientThread.start();
//			System.out.println("Server accepted request");
//		} catch (Exception ex) {
//			System.out.println("[Application > Me] : Server not found\n");
//		}
	}

	
	@SuppressWarnings("unchecked")
	private void initComponents() {

		OrderPane = new javax.swing.JScrollPane();
		OrderInTable = new javax.swing.JTable();
		StartButton = new JButton();
		CookingButton = new JButton();
		CookedButton = new JButton();
		TopText = new javax.swing.JLabel();
		DeliveryPane = new javax.swing.JScrollPane();
		DeliveryTable = new javax.swing.JTable();
		BottomText = new javax.swing.JLabel();
		DeliveredButton = new javax.swing.JButton();
		DeliveredButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeliveredButtonActionPerformed(e);

			}
		});

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		OrderInTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {}, new String[] { "Table", "Dish", "Quantity",
						"Status" }) {
			private static final long serialVersionUID = 1L;
			Class[] types = new Class[] { java.lang.Integer.class,
					java.lang.String.class, java.lang.Integer.class,
					java.lang.String.class, java.lang.Integer.class };
			boolean[] canEdit = new boolean[] { false, false, false, false };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		OrderPane.setViewportView(OrderInTable);

		StartButton.setText("Start Server");
		StartButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				StartButtonActionPerformed(evt);
			}
		});

		CookingButton.setText("Cooking");
		CookingButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CookingButtonActionPerformed(evt);
			}
		});

		CookedButton.setText("Cooked");
		CookedButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				CookedButtonActionPerformed(evt);
			}
		});

		TopText.setText("ORDERS IN :::");

		DeliveryTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {}, new String[] { "Table Number", "Dish",
						"Quantity" }) {
			Class[] types = new Class[] { java.lang.Integer.class,
					java.lang.String.class, java.lang.Integer.class };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		DeliveryPane.setViewportView(DeliveryTable);

		BottomText.setText("DELIVERIES :::");

		DeliveredButton.setText("Delivered");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		layout.setHorizontalGroup(layout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(32)
								.addGroup(
										layout.createParallelGroup(
												Alignment.LEADING)
												.addComponent(BottomText)
												.addGroup(
														layout.createParallelGroup(
																Alignment.LEADING,
																false)
																.addComponent(
																		DeliveryPane,
																		GroupLayout.DEFAULT_SIZE,
																		730,
																		Short.MAX_VALUE)
																.addComponent(
																		TopText)
																.addComponent(
																		OrderPane,
																		GroupLayout.PREFERRED_SIZE,
																		816,
																		GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(29, Short.MAX_VALUE))
				.addGroup(
						layout.createSequentialGroup().addGap(74)
								.addComponent(StartButton).addGap(206)
								.addComponent(CookingButton).addGap(215)
								.addComponent(CookedButton)
								.addContainerGap(84, Short.MAX_VALUE))
				.addGroup(
						layout.createSequentialGroup().addGap(370)
								.addComponent(DeliveredButton)
								.addContainerGap(406, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(8)
								.addComponent(TopText)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(OrderPane,
										GroupLayout.PREFERRED_SIZE, 288,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addGroup(
										layout.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(StartButton)
												.addComponent(CookingButton)
												.addComponent(CookedButton))
								.addGap(48)
								.addComponent(BottomText)
								.addGap(18)
								.addComponent(DeliveryPane,
										GroupLayout.PREFERRED_SIZE, 100,
										GroupLayout.PREFERRED_SIZE).addGap(18)
								.addComponent(DeliveredButton)
								.addContainerGap(66, Short.MAX_VALUE)));
		getContentPane().setLayout(layout);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void StartButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_UnCookedButtonActionPerformed
		int timeout=500;
        int port = 13000;

        try {
            String currentIP = InetAddress.getLocalHost().getHostAddress().toString();
            System.out.println("Current Ip"+currentIP);
            String subnet = getSubnet(currentIP);
            System.out.println("subnet: " + subnet);

            for (int i=1;i<254;i++){

                String host = subnet + i;
                System.out.println("Checking :" + host);

                if (InetAddress.getByName(host).isReachable(timeout)){
                    System.out.println(host + " is reachable");
                    try {
                        Socket connected = new Socket(subnet, port);
                    }
                    catch (Exception s) {
                        System.out.println(s);
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
	}
	public static String getSubnet(String currentIP) {
        int firstSeparator = currentIP.lastIndexOf("/");
        int lastSeparator = currentIP.lastIndexOf(".");
        return currentIP.substring(firstSeparator+1, lastSeparator+1);
    }

	private void CookingButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CookingButtonActionPerformed
		int[] index = OrderInTable.getSelectedRows();

		boolean changes = false;
		if (index.length > 0) {
			for (int i = 0; i < index.length; i++) {

				Order selectedOrder = orderInList.get(index[i]);
				if (selectedOrder.getStatusString().equals(
						OrderStatus.ORDER_IN.toString())) {
					changes = true;

					Order changedOrder = new Order(selectedOrder.getTableNo(),
							selectedOrder.getDishName(),
							selectedOrder.getQuantity(), OrderStatus.COOKING,
							selectedOrder.getUnitPrice());
					updateOrderList(index[i], changedOrder);
				} else {
					JOptionPane.showMessageDialog(null,
							"Only select ORDER_IN row");
				}
			}
		}
	}

	private void DeliveredButtonActionPerformed(java.awt.event.ActionEvent evt) {

		int[] index = DeliveryTable.getSelectedRows();

		for (int i = 0; i < index.length; i++) {
			Order selectedOrder = deliveriesList.get(index[i]);
			client.send(selectedOrder);
		}

	}

	private void CookedButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_CookedButtonActionPerformed
		
		int[] index = OrderInTable.getSelectedRows();

		boolean changes = false;
		if (index.length > 0) {
			for (int i = 0; i < index.length; i++) {

				Order selectedOrder = orderInList.get(index[i]);
				if (selectedOrder.getStatusString().equals(
						OrderStatus.COOKING.toString())) {
					changes = true;
					// System.out.println("Cooking Changes = true");
					Order changedOrder = new Order(selectedOrder.getTableNo(),
							selectedOrder.getDishName(),
							selectedOrder.getQuantity(), OrderStatus.COOKED,
							selectedOrder.getUnitPrice());

					orderInList.remove(index[i]);
					addToDeliverList(changedOrder);
					updateOrderTable();

				} else {
					JOptionPane.showMessageDialog(null,
							"Only select COOKING row");

				}

			}
		}

	}

	private javax.swing.JLabel BottomText;
	private JButton CookedButton;
	private JButton CookingButton;
	private JButton DeliveredButton;
	private javax.swing.JScrollPane DeliveryPane;
	private javax.swing.JTable DeliveryTable;
	public javax.swing.JTable OrderInTable;
	private javax.swing.JScrollPane OrderPane;
	private javax.swing.JLabel TopText;
	private JButton StartButton;

	
	public static void main(String args[]) {
		
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger
					.getLogger(KitchenInterface.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger
					.getLogger(KitchenInterface.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger
					.getLogger(KitchenInterface.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger
					.getLogger(KitchenInterface.class.getName()).log(
							java.util.logging.Level.SEVERE, null, ex);
		}
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new KitchenInterface().setVisible(true);

			}
		});

		
	}

	public void addToOrderList(Order order) {
		orderInList.add(order);
		updateOrderTable();
	}

	public void updateOrderList(int pos, Order order) {
		orderInList.set(pos, order);
		updateOrderTable();

	}

	public void updateOrderTable() {
		DefaultTableModel model = (DefaultTableModel) OrderInTable.getModel();
		if (!orderInList.isEmpty()) {
			while (model.getRowCount() != 0) {
				model.removeRow(model.getRowCount() - 1);
			}

			for (Order orderRow : orderInList) {
				model.addRow(new Object[] { orderRow.getTableNo(),
						orderRow.getDishName(), orderRow.getQuantity(),
						orderRow.getStatusString() });
			}
		} else {
			while (model.getRowCount() != 0) {
				model.removeRow(model.getRowCount() - 1);
			}

		}
	}

	public void addToDeliverList(Order order) {
		deliveriesList.add(order);
		updateDeliverTable();
	}

	public void updateDeliverTable() {
		DefaultTableModel model1 = (DefaultTableModel) DeliveryTable.getModel();
		if (!deliveriesList.isEmpty()) {
			while (model1.getRowCount() != 0) {
				model1.removeRow(model1.getRowCount() - 1);
			}
			for (Order order : deliveriesList) {
				model1.addRow(new Object[] { order.getTableNo(),
						order.getDishName(), order.getQuantity() });
			}
		} else {
			while (model1.getRowCount() != 0) {
				model1.removeRow(model1.getRowCount() - 1);
			}
		}
	}
}
