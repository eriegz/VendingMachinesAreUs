package iteration2;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.Display;
import com.vendingmachinesareus.DisplayListener;

/**
 * The Interface DisplayManager. Classes which implement this interface will be
 * able to manage the display
 */
public interface DisplayManager extends DisplayListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vendingmachinesareus.AbstractHardwareListener#disabled(com.
	 * vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vendingmachinesareus.AbstractHardwareListener#enabled(com.
	 * vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vendingmachinesareus.DisplayListener#messageChange(com.
	 * vendingmachinesareus.Display, java.lang.String, java.lang.String)
	 */
	@Override
	public void messageChange(Display arg0, String arg1, String arg2);

}
