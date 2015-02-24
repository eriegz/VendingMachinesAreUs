package GUI;

import iteration2.SoftwareSimulator;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.vendingmachinesareus.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class VendingMachineGUI{//implements IndicatorLightSimulatorListener {

	private JFrame myFrame;
	//private JTextField myLCDscreen;
	private JButton Pop_1, Pop_2, Pop_3, Pop_4, Pop_5, Pop_6;
	private JButton coinReturn, Exit;
	private JButton takeItems;
	private JTextArea textPane, textPane2;
	private JCheckBox outOfOrderLight, exactChangeLight;
	// Used to redirect stdout to GUI
	private OutputStream outStream;
	public static PrintStream out;
	private SoftwareSimulator softwareSimulator;
	private Display display;

	//For Testing only
	public VendingMachineGUI(){
		out= System.out;
	}
	
	public VendingMachineGUI(final StandardPopVendingMachine myMachine) {
		
		softwareSimulator = new SoftwareSimulator(myMachine);
		display = myMachine.getDisplay();
		
		outStream = new OutputStream() {
			@Override
			public void write(int b) throws IOException {
				updateTextPane(String.valueOf((char) b), 0);
			}

			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				if(new String(b,off,len).contains("Notice")|| new String(b, off,len).contains("|"))
					updateTextPane(new String(b, off, len), 0);
				else{
					updateTextPane(new String(b, off, len), 1);
				}
			}

			@Override
			public void write(byte[] b) throws IOException {
				write(b, 0, b.length);
			}
		};
		out = new PrintStream(outStream, true);
		//Create new frame
		myFrame = new JFrame();
		// Set the size of our window, and center it in the screen
		myFrame.setSize(950, 610);
		myFrame.setLocationRelativeTo(null);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Use the GridBag layout manager
		myFrame.getContentPane().setLayout(new GridBagLayout());
		Insets zeroInsets = new Insets(0, 0, 0, 0);
		Insets tenInsets = new Insets(10, 10, 10, 10); 
		int centerInt = GridBagConstraints.CENTER;
		int northeastInt = GridBagConstraints.NORTHEAST;
		int fillBothInt = GridBagConstraints.BOTH;
		int fillNoneInt = GridBagConstraints.NONE;

		//Updateable GUI display
		textPane = new JTextArea();
		textPane2 = new JTextArea();
		textPane.setVisible(true);
		textPane.setEditable(false);
		textPane.setVisible(true);
		myFrame.getContentPane().add(textPane, getNewConstraints(0,0,2,1,1.0,1.0, centerInt, fillBothInt, zeroInsets, 0, 0));
		myFrame.getContentPane().add(textPane2, getNewConstraints(2,0,1,1,1.0,1.0, centerInt, fillBothInt, zeroInsets,0,0));
		
		// ===================== Coin buttons =============================
		// Coin entry label text
		JLabel lblEnterCoin = new JLabel("Payment Method:");
		myFrame.getContentPane().add(
				lblEnterCoin,
				getNewConstraints(0, 2, 1, 3, 1.0, 1.0, northeastInt,
						fillNoneInt, tenInsets, 0, 0));

		// $0.05 button
		JButton fiveCents = new JButton("$0.05");
		myFrame.getContentPane().add(
				fiveCents,
				getNewConstraints(1, 2, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		fiveCents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Coin c = new Coin(5);
				try {
					myMachine.getCoinSlot().addCoin(c);
				} catch (com.vendingmachinesareus.DisabledException exc) {
					// TODO Auto-generated catch block
					exc.printStackTrace();
				}
			}
		});


		// $0.10 button
		JButton tenCents = new JButton("$0.10");
		myFrame.getContentPane().add(
				tenCents,
				getNewConstraints(2, 2, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		tenCents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Coin c = new Coin(10);
				try {
					myMachine.getCoinSlot().addCoin(c);
				} catch (com.vendingmachinesareus.DisabledException exc) {
					// TODO Auto-generated catch block
					exc.printStackTrace();
				}
			}
		});

		// $0.25 button
		JButton twentyFiveCents = new JButton("$0.25");
		myFrame.getContentPane().add(
				twentyFiveCents,
				getNewConstraints(1, 3, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		twentyFiveCents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Coin c = new Coin(25);
				try {
					myMachine.getCoinSlot().addCoin(c);
				} catch (com.vendingmachinesareus.DisabledException exc) {
					// TODO Auto-generated catch block
					exc.printStackTrace();
				}
			}
		});

		// $1.00 button
		JButton oneDollar = new JButton("$1.00");
		myFrame.getContentPane().add(
				oneDollar,
				getNewConstraints(2, 3, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		oneDollar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Coin c = new Coin(100);
				try {
					myMachine.getCoinSlot().addCoin(c);
				} catch (com.vendingmachinesareus.DisabledException exc) {
					// TODO Auto-generated catch block
					exc.printStackTrace();
				}
			}
		});

		// $2.00 button
		JButton twoDollars = new JButton("$2.00");
		myFrame.getContentPane().add(
				twoDollars,
				getNewConstraints(1, 4, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		twoDollars.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Coin c = new Coin(200);
				try {
					myMachine.getCoinSlot().addCoin(c);
				} catch (com.vendingmachinesareus.DisabledException exc) {
					// TODO Auto-generated catch block
					exc.printStackTrace();
				}
			}
		});

		/*
		fiveCents.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Coin c = new Coin(5);
				try {
					myMachine.getCoinSlot().addCoin(c);
				} catch (DisabledException exc) {
					// TODO Auto-generated catch block
					exc.printStackTrace();
				}
			}
		});*/
		
		// ========================== Pop buttons ===============================
		// Pop selection label text
		JLabel lblSelectPop = new JLabel("Select pop:");
		myFrame.getContentPane().add(
				lblSelectPop,
				getNewConstraints(0, 5, 1, 3, 1.0, 1.0, northeastInt,
						fillNoneInt, tenInsets, 0, 0));

		// First pop button
		Pop_1 = new JButton(new ImageIcon("Resources/Pops/1.png"));
		myFrame.getContentPane().add(
				Pop_1,
				getNewConstraints(1, 5, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		Pop_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				myMachine.getSelectionButton(0).press();
			}
		});
		
		// Second pop button
		Pop_2 = new JButton(new ImageIcon("Resources/Pops/2.jpg"));
		myFrame.getContentPane().add(
				Pop_2,
				getNewConstraints(2, 5, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		Pop_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				myMachine.getSelectionButton(1).press();
			}
		});

		// Third pop button
		Pop_3 = new JButton(new ImageIcon("Resources/Pops/3.jpg"));
		myFrame.getContentPane().add(
				Pop_3,
				getNewConstraints(1, 6, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		Pop_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				myMachine.getSelectionButton(2).press();
			}
		});

		// Fourth pop button
		Pop_4 = new JButton(new ImageIcon("Resources/Pops/4.jpg"));
		myFrame.getContentPane().add(
				Pop_4,
				getNewConstraints(2, 6, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		Pop_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				myMachine.getSelectionButton(3).press();
			}
		});

		// Fifth pop button
		Pop_5 = new JButton(new ImageIcon("Resources/Pops/5.png"));
		myFrame.getContentPane().add(
				Pop_5,
				getNewConstraints(1, 7, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		Pop_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				myMachine.getSelectionButton(4).press();
			}
		});

		// Sixth pop button
		Pop_6 = new JButton(new ImageIcon("Resources/Pops/6.jpg"));
		myFrame.getContentPane().add(
				Pop_6,
				getNewConstraints(2, 7, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		Pop_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				myMachine.getSelectionButton(5).press();
			}
		});
		
		//Don't know how to implement this yet... Probably need some type of store string funciton which another class could call to get the pin
		//========================= Number Pad ==================================
		JLabel lblPINPad = new JLabel("Enter PIN:");
		myFrame.getContentPane().add(
				lblPINPad,
				getNewConstraints(3, 5, 1, 3, 1.0, 1.0, northeastInt,
						fillNoneInt, tenInsets, 0, 0));
		
		JButton pin1 = new JButton(new ImageIcon("Resources/PinPad/1.jpg"));
		myFrame.getContentPane().add(
				pin1,
				getNewConstraints(4, 5, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		
		JButton pin2 = new JButton(new ImageIcon("Resources/PinPad/2.jpg"));
		myFrame.getContentPane().add(
				pin2,
				getNewConstraints(5, 5, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));

		JButton pin3 = new JButton(new ImageIcon("Resources/PinPad/3.jpg"));
		myFrame.getContentPane().add(
				pin3,
				getNewConstraints(6, 5, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));

		JButton pin4 = new JButton(new ImageIcon("Resources/PinPad/4.jpg"));
		myFrame.getContentPane().add(
				pin4,
				getNewConstraints(4, 6, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));

		JButton pin5 = new JButton(new ImageIcon("Resources/PinPad/5.jpg"));
		myFrame.getContentPane().add(
				pin5,
				getNewConstraints(5, 6, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));

		JButton pin6 = new JButton(new ImageIcon("Resources/PinPad/6.jpg"));
		myFrame.getContentPane().add(
				pin6,
				getNewConstraints(6, 6, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));

		JButton pin7 = new JButton(new ImageIcon("Resources/PinPad/7.jpg"));
		myFrame.getContentPane().add(
				pin7,
				getNewConstraints(4, 7, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));

		JButton pin8 = new JButton(new ImageIcon("Resources/PinPad/8.jpg"));
		myFrame.getContentPane().add(
				pin8,
				getNewConstraints(5, 7, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));

		JButton pin9 = new JButton(new ImageIcon("Resources/PinPad/9.jpg"));
		myFrame.getContentPane().add(
				pin9,
				getNewConstraints(6, 7, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		
		JButton pin0 = new JButton(new ImageIcon("Resources/PinPad/0.jpg"));
		myFrame.getContentPane().add(
				pin0,
				getNewConstraints(5, 8, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));

		JButton pinX = new JButton(new ImageIcon("Resources/PinPad/X.jpg"));
		myFrame.getContentPane().add(
				pinX,
				getNewConstraints(4, 9, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));

		JButton pinC = new JButton(new ImageIcon("Resources/PinPad/C.jpg"));
		myFrame.getContentPane().add(
				pinC,
				getNewConstraints(5, 9, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));

		JButton pinCheck = new JButton(new ImageIcon("Resources/PinPad/checkm.jpg"));
		myFrame.getContentPane().add(
				pinCheck,
				getNewConstraints(6, 9, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));

		//=====================admin, credit card, =============================
		JButton creditCard = new JButton("creditCard");
		myFrame.getContentPane().add(
				creditCard,
				getNewConstraints(2, 4, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		
		JButton Admin = new JButton("Admin");
		myFrame.getContentPane().add(
				Admin,
				getNewConstraints(6, 2, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		
		// ==================== Coin return, exit, etc. ========================
		// Coin return button
		//Jamie: Hasn't been implemented yet
		/*coinReturn = new JButton("Coin Return");
		myFrame.getContentPane().add(
				coinReturn,
				getNewConstraints(0, 10, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
*/
		// Take items button
		takeItems = new JButton("Take Items");
		myFrame.getContentPane().add(
				takeItems,
				getNewConstraints(1, 10, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		takeItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				Object[] items = myMachine.getDeliveryChute().removeItems();
				for (Object item : items) {
					System.out.println("Removed: " + item);
				}
			}
		});

		// Exit button
		Exit = new JButton("Exit");
		// Exit.setBounds(179, 199, 78, 52);
		myFrame.getContentPane().add(
				Exit,
				getNewConstraints(2, 10, 1, 1, 1.0, 1.0, centerInt, fillBothInt,
						zeroInsets, 0, 0));
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
				myFrame.dispose();

			}
		});

		exactChangeLight = new JCheckBox("ExactChange");
		exactChangeLight.setEnabled(false);
		exactChangeLight.setVisible(true);
		myFrame.getContentPane().add(exactChangeLight, getNewConstraints(0,1,2,1,1.0,1.0, centerInt, fillBothInt, zeroInsets, 0,0));
		outOfOrderLight	= new JCheckBox("OutOfOrder");
		outOfOrderLight.setEnabled(false);
		outOfOrderLight.setVisible(true);
		myFrame.getContentPane().add(outOfOrderLight, getNewConstraints(2,1,1,1,1.0,1.0, centerInt, fillBothInt, zeroInsets, 0,0));
		
		//Default the displays
		out.print("$0.00");
		out.print("|");
		
		myFrame.setVisible(true);
	}

	private void updateTextPane(final String text, int paneNumber) {
		if(paneNumber==0){
			textPane.setText(text);
		}else{
			textPane2.setText(text);
		}
		display.display(text);
	}

	public OutputStream getOutputStream(){
		return out;
	}
	// This function lets us avoid having to make a new GridBagConstraints
	// object every time
	public GridBagConstraints getNewConstraints(int cellX, int cellY,
			int cellHeight, int cellWidth, double weightX, double weightY,
			int anchor, int fill, Insets ins, int padX, int padY) {
		return new GridBagConstraints(cellX, cellY, cellHeight, cellWidth,
				weightX, weightY, anchor, fill, ins, padX, padY);
	}

	public static void main(String [] args){
		StandardPopVendingMachine machine = StandardPopVendingMachine.getInstance();
		VendingMachineGUI myGUI = new VendingMachineGUI(machine);
	}
}
