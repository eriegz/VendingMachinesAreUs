package iteration2;

import com.vendingmachinesareus.CapacityExceededException;
import com.vendingmachinesareus.Coin;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.PopCan;
import com.vendingmachinesareus.StandardPopVendingMachine;

public class SoftwareSimulator {
	private CardManager cardManager;
	private CoinInventory coinInventory;
	private CoinLocationManager coinLocationManager;
	private PopInventory popInventory;
	private PurchaseController purchaseController;
	// This is editable here and will be the global constant for coin costs
	static final public int coinCosts[] = { 5, 10, 25, 100, 200 };

	public SoftwareSimulator(StandardPopVendingMachine machine) {
		// Initialize and register listeners
		cardManager = new Iteration2CardManager();
		machine.getCardSlot().register(cardManager);

		coinInventory = new Iteration2CoinInventory(coinCosts);
		machine.getCoinReceptacle().register(coinInventory);
		for (int i = 0; i < machine.getNumberOfCoinRacks(); i++) {
			machine.getCoinRack(i).register(coinInventory);
		}

		setCoinLocationManager(new Iteration2CoinLocationManager(coinCosts,
				machine));

		popInventory = new Iteration2PopInventory(machine);
		for (int i = 0; i < machine.getNumberOfPopCanRacks(); i++) {
			machine.getPopCanRack(i).register(
					popInventory.getPopInfo(machine.getSelectionButton(i)));
			popInventory.setCost(machine.getSelectionButton(i),
					machine.getPopCost(i));
			popInventory.getPopInfo(machine.getSelectionButton(i)).setRackID(machine.getPopCanRack(i));
		}

		purchaseController = new Iteration2PurchaseController(popInventory,
				coinInventory, coinLocationManager, cardManager);
		for (int i = 0; i < machine.getNumberOfSelectionButtons(); i++) {
			machine.getSelectionButton(i).register(purchaseController);
		}
		for (int i = 0; i < machine.getNumberOfCoinRacks(); i++) {
			for (int j = 0; j < 5; j++) {
				try {
					machine.getCoinRack(i).acceptCoin(new Coin(coinCosts[i]));
				} catch (CapacityExceededException | DisabledException e) {
					e.printStackTrace();
				}
			}
		}
		for(int i = 0; i < machine.getNumberOfPopCanRacks(); i++){
			for(int j = 0; j<5;j++){
				try {
					machine.getPopCanRack(i).addPop(new PopCan());
				} catch (CapacityExceededException | DisabledException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public CoinLocationManager getCoinLocationManager() {
		return coinLocationManager;
	}

	public void setCoinLocationManager(CoinLocationManager coinLocationManager) {
		this.coinLocationManager = coinLocationManager;
	}
}
