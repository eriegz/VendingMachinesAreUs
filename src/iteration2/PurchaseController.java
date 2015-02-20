package iteration2;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.SelectionButton;
import com.vendingmachinesareus.SelectionButtonListener;

// TODO: Auto-generated Javadoc
/**
 * The Interface PurchaseController. Classes which implement this interface will
 * be the main controller for the vending machine during a purchase and controls
 * the payment, dispensing and updating of stock of pop
 */
public interface PurchaseController extends SelectionButtonListener {

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
	 * @see com.vendingmachinesareus.SelectionButtonListener#pressed(com.
	 * vendingmachinesareus.SelectionButton)
	 */
	@Override
	public void pressed(SelectionButton arg0);
}
