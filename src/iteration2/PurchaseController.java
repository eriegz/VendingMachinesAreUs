package iteration2;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.SelectionButton;
import com.vendingmachinesareus.SelectionButtonListener;

public interface PurchaseController extends SelectionButtonListener {
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0);
	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0);
	@Override
	public void pressed(SelectionButton arg0);
}
