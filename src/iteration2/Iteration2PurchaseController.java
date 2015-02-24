package iteration2;

import GUI.VendingMachineGUI;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.CapacityExceededException;
import com.vendingmachinesareus.Coin;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.SelectionButton;

public class Iteration2PurchaseController implements PurchaseController{
	private PopInventory popInventory;
	private CoinInventory coinInventory;
	private CoinLocationManager coinLocationManager;
	private CardManager cardManager;

	public Iteration2PurchaseController(PopInventory pop, CoinInventory coin, CoinLocationManager coinLocation, CardManager card){
		popInventory = pop;
		coinInventory = coin;
		coinLocationManager = coinLocation;
		cardManager = card;
	}
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
				} catch (Exception e ) {
					/*
					 * This is a workaround for a bug in v0.3.jar where coins removed is calling functions called coinsAdded
					 */
					coinInventory.coinsRemoved(coinLocationManager.getCoinReceptacle());
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
							//Should disable machine
						}catch (Exception e){
							/*
							 * 
							 */
							coinInventory.coinsRemoved(coinLocationManager.getCoinReceptacle());
						}
						popInventory.dispense(arg0);
					} else {
						VendingMachineGUI.out.print("Notice: Cannot Charge Card");
					}
				}else {
					VendingMachineGUI.out.print("Notice: PIN Not Valid");
				}
			}
		else{
			VendingMachineGUI.out.print("Notice: Price of Pop is " + ChangeMaker.toMoney(popInventory.getCost(arg0)));
		}
	}
}
