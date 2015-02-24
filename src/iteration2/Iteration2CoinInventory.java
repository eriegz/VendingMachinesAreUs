package iteration2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import GUI.VendingMachineGUI;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.Coin;
import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.CoinReceptacle;

/**
 * The Class Iteration2CoinInventory.
 * Implements CoinInventory and manages coins for iteration2 see @CoinInventory
 */
public class Iteration2CoinInventory implements CoinInventory{
	/** The receptacle coin value in cents. */
	private int receptacleCoinValue;
	/** The amount of coins in each coin rack stored in number of coins */
	private int [] coinRackAmountArray;
	/** This stores the value of the coinRack and the index for the coinRackValue array associated with it */
	private Map <Integer, Integer> valueToRackMap;
	
	/** The coin denominations allowed in the machine. */
	private int [] coinValues;
	
	/**
	 * Instantiates a new iteration2 coin inventory.
	 *
	 * @param coinValues the coin denominations allowed in the machine
	 */
	public Iteration2CoinInventory(int [] coinValues){
		receptacleCoinValue = 0;
		coinRackAmountArray = new int[coinValues.length];
		valueToRackMap = new HashMap<Integer, Integer>();
		ArrayList <Integer> sortedSet = new ArrayList <Integer>();
		Collections.sort(sortedSet);
		
		//Sorts coin values so my array is sorted as well
		for(int i = 0; i < coinValues.length; i++){
			sortedSet.add(coinValues[i]);
		}
		
		int j = 0;
		for(int coinValue: sortedSet){
			valueToRackMap.put(coinValue, j++);
		}
		
		this.coinValues = coinValues;
	}
	
	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#disabled(com.vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0) {

	}

	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#enabled(com.vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0) {
		
	}

	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#coinAdded(com.vendingmachinesareus.CoinRack, com.vendingmachinesareus.Coin)
	 */
	@Override
	public void coinAdded(CoinRack arg0, Coin arg1) {
		coinRackAmountArray[valueToRackMap.get(arg1.getValue())] += 1;
	}

	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#coinRemoved(com.vendingmachinesareus.CoinRack, com.vendingmachinesareus.Coin)
	 */
	@Override
	public void coinRemoved(CoinRack arg0, Coin arg1) {
		coinRackAmountArray[valueToRackMap.get(arg1.getValue())] -= 1;
	}

	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#coinsEmpty(com.vendingmachinesareus.CoinRack)
	 */
	@Override
	public void coinsEmpty(CoinRack arg0) {
		
	}

	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#coinsFull(com.vendingmachinesareus.CoinRack)
	 */
	@Override
	public void coinsFull(CoinRack arg0) {
		
	}

	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#coinAdded(com.vendingmachinesareus.CoinReceptacle, com.vendingmachinesareus.Coin)
	 */
	@Override
	public void coinAdded(CoinReceptacle arg0, Coin arg1) {
		receptacleCoinValue+= arg1.getValue();
		VendingMachineGUI.out.print(ChangeMaker.toMoney(receptacleCoinValue));
		
	}

	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#coinsFull(com.vendingmachinesareus.CoinReceptacle)
	 */
	@Override
	public void coinsFull(CoinReceptacle arg0) {

		
	}

	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#coinsRemoved(com.vendingmachinesareus.CoinReceptacle)
	 */
	@Override
	public void coinsRemoved(CoinReceptacle arg0) {
		receptacleCoinValue = 0;
		VendingMachineGUI.out.print((ChangeMaker.toMoney(0)));
	}

	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#disabled(com.vendingmachinesareus.CoinReceptacle)
	 */
	@Override
	public void disabled(CoinReceptacle arg0) {
		
	}

	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#enabled(com.vendingmachinesareus.CoinReceptacle)
	 */
	@Override
	public void enabled(CoinReceptacle arg0) {
		
	}
	
	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#getNumberOfCoinsInRack(int)
	 */
	public int getNumberOfCoinsInRack(int coinRackValueToReturn){
		return coinRackAmountArray[valueToRackMap.get(coinRackValueToReturn)];
	}
	
	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#getReceptacleAmount()
	 */
	public int getReceptacleAmount(){
		return receptacleCoinValue;
	}
	
	/* (non-Javadoc)
	 * @see iteration2.CoinInventory#getCoinValues()
	 */
	public int[] getCoinValues(){
		return coinValues;
	}
}
