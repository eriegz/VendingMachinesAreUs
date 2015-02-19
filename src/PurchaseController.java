package iteration2;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.CapacityExceededException;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.SelectionButton;
import com.vendingmachinesareus.SelectionButtonListener;

public class PurchaseController implements SelectionButtonListener {
	private PopInventory popInventory;
	private CoinInventory coinInventory;
	private CoinLocationManager coinLocationManager;
	private CardManager cardManager;

	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pressed(SelectionButton arg0) {
		int cost = popInventory.getCost(arg0);
		if (!popInventory.hasPop(arg0)) {
			return;
		}
		if (coinInventory.getReceptacleAmount() >= cost) {
			if (ChangeMaker
					.makeChange(coinInventory.getReceptacleAmount() - cost,
							coinLocationManager.getCoinRackMap(), coinInventory)) {
				popInventory.dispense(arg0);
			} else {
				// Display cannot make change
			}
		} else {
			if (cardManager.hasCard()) {
				// Display pay for remainder on card?
				// Display please enter pin
				String PIN = null;
				if (cardManager.verify(PIN)) {
					cost -= coinInventory.getReceptacleAmount();
					if (cardManager.charge(cost, PIN)) {
						try {
							coinLocationManager.getCoinReceptacle().storeCoins();
						} catch (CapacityExceededException | DisabledException e) {
							//Should disable machine
						}
						popInventory.dispense(arg0);
					} else {
						// Display cannot charge card
					}
				}
			}
		}
	}

}
