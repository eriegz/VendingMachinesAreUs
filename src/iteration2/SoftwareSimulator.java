package iteration2;

import com.vendingmachinesareus.StandardPopVendingMachine;

public class SoftwareSimulator {
	private CardManager cardManager;
	private CoinInventory coinInventory;
	private CoinLocationManager coinLocationManager;
	private PopInventory popInventory;
	private PurchaseController purchaseController;
	//This is editable here and will be the global constant for coin costs
	static final public int coinCosts[] = {5,10,25,100,200};
	
	public SoftwareSimulator(StandardPopVendingMachine machine){
		//Initialize and register listeners
		cardManager = new Iteration2CardManager();
		machine.getCardSlot().register(cardManager);
		
		coinInventory = new Iteration2CoinInventory(coinCosts);
		machine.getCoinReceptacle().register(coinInventory);
		for (int i = 0; i < machine.getNumberOfCoinRacks(); i ++){
			machine.getCoinRack(i).register(coinInventory);
		}
		
		setCoinLocationManager(new Iteration2CoinLocationManager(coinCosts, machine));
		
		popInventory = new Iteration2PopInventory(machine);
		for(int i = 0; i<machine.getNumberOfPopCanRacks(); i++){
			machine.getPopCanRack(i).register(popInventory.getPopInfo(machine.getSelectionButton(i)));
			popInventory.setCost(machine.getSelectionButton(i), machine.getPopCost(i));
		}
		
		purchaseController = new Iteration2PurchaseController(popInventory, coinInventory, coinLocationManager, cardManager);
		for(int i = 0; i < machine.getNumberOfSelectionButtons(); i++){
			machine.getSelectionButton(i).register(purchaseController);
		}
	}

	public CoinLocationManager getCoinLocationManager() {
		return coinLocationManager;
	}

	public void setCoinLocationManager(CoinLocationManager coinLocationManager) {
		this.coinLocationManager = coinLocationManager;
	}
}
