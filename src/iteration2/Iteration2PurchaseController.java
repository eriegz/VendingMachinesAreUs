package iteration2;

import GUI.VendingMachineGUI;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.CapacityExceededException;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.SelectionButton;

// TODO: Auto-generated Javadoc
/**
 * The Class Iteration2PurchaseController.
 * Implements @PurchaseController
 * NOTE: There is a serious but in V0.3 for receptacle.notifyCoinsRemoved which is why there is an ugly workaround here
 */
public class Iteration2PurchaseController implements PurchaseController {
	
	/** The pop inventory. */
	private PopInventory popInventory;
	
	/** The coin inventory. */
	private CoinInventory coinInventory;
	
	/** The coin location manager. */
	private CoinLocationManager coinLocationManager;
	
	/** The card manager. */
	private CardManager cardManager;

	/**
	 * Instantiates a new iteration2 purchase controller.
	 *
	 * @param pop the pop inventory manager for the machine
	 * @param coin the coin inventory manager for the machine
	 * @param coinLocation the coin location manager for the machine
	 * @param card the card manager for the machine
	 */
	public Iteration2PurchaseController(PopInventory pop, CoinInventory coin,
			CoinLocationManager coinLocation, CardManager card) {
		popInventory = pop;
		coinInventory = coin;
		coinLocationManager = coinLocation;
		cardManager = card;
	}

	/* (non-Javadoc)
	 * @see iteration2.PurchaseController#disabled(com.vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see iteration2.PurchaseController#enabled(com.vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see iteration2.PurchaseController#pressed(com.vendingmachinesareus.SelectionButton)
	 */
	@Override
	public void pressed(SelectionButton arg0) {
		int cost = popInventory.getCost(arg0);
		if (!popInventory.hasPop(arg0)) {
			VendingMachineGUI.out.print("Notice: No Pop of that type");
			return;
		}
		if (coinInventory.getReceptacleAmount() >= cost) {
			if (ChangeMaker
					.makeChange(coinInventory.getReceptacleAmount() - cost,
							coinLocationManager.getCoinRackMap(), coinInventory)) {
				popInventory.dispense(arg0);
				try {
					coinLocationManager.getCoinReceptacle().storeCoins();
				} catch (Exception e) {
					/*
					 * This is a workaround for a bug in v0.3.jar where coins
					 * removed is calling functions called coinsAdded which of
					 * course do not exists in classes that implement
					 * coinLocationManager so we will have to manually call the
					 * function
					 */
					coinInventory.coinsRemoved(coinLocationManager
							.getCoinReceptacle());
				}
			} else {
				VendingMachineGUI.out.print("Notice: Cannot Make Change");
			}
		} else if (cardManager.hasCard()) {
			VendingMachineGUI.out.print("Notice Please Enter PIN");
			// Display please enter pin
			String PIN = null;
			if (cardManager.verify(PIN)) {
				cost -= coinInventory.getReceptacleAmount();
				if (cardManager.charge(cost, PIN)) {
					try {
						coinLocationManager.getCoinReceptacle().storeCoins();
					} catch (CapacityExceededException | DisabledException e) {
						// Should disable machine
					} catch (Exception e) {
						/*
						 * This is a workaround for a bug in v0.3.jar where
						 * coins removed is calling functions called coinsAdded
						 * which of course do not exists in classes that
						 * implement coinLocationManager so we will have to
						 * manually call the function
						 */
						coinInventory.coinsRemoved(coinLocationManager
								.getCoinReceptacle());
					}
					popInventory.dispense(arg0);
				} else {
					VendingMachineGUI.out.print("Notice: Cannot Charge Card");
				}
			} else {
				VendingMachineGUI.out.print("Notice: PIN Not Valid");
			}
		} else {
			VendingMachineGUI.out.print("Notice: Price of Pop is "
					+ ChangeMaker.toMoney(popInventory.getCost(arg0)));
		}
	}
}
