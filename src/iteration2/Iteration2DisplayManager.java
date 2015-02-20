package iteration2;

import java.util.Timer;
import java.util.TimerTask;

import GUI.VendingMachineGUI;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.Display;

// TODO: Auto-generated Javadoc
/**
 * The Class Iteration2DisplayManager. Implements @DisplayManager
 */
public class Iteration2DisplayManager implements DisplayManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * iteration2.DisplayManager#disabled(com.vendingmachinesareus.AbstractHardware
	 * )
	 */
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * iteration2.DisplayManager#enabled(com.vendingmachinesareus.AbstractHardware
	 * )
	 */
	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * iteration2.DisplayManager#messageChange(com.vendingmachinesareus.Display,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void messageChange(final Display display, final String oldMsg,
			String newMsg) {
		if (newMsg.contains("Notice: Price of Pop is")) {
			final Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					VendingMachineGUI.out.print("|");
					timer.cancel();
				}
			}, 4000);

		} else if (newMsg.contains("Notice:")) {
			final Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					VendingMachineGUI.out.print("|");
					timer.cancel();
				}
			}, 5000);
		}
	}

}
