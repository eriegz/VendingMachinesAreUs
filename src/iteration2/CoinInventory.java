package iteration2;

import com.vendingmachinesareus.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
public class CoinInventory implements CoinReceptacleListener, CoinRackListener {
	//This is the value in the receptacle
	private int receptacleCoinValue;
	//This is the number of coins in the receptacle
	private int [] coinRackValue;
	//This stores the value of the coinRack and the index for the coinRackValue array associated with it
	private Map <Integer, Integer> valueToRackMap;
	private int [] coinValues;
	
	public CoinInventory(int [] coinValues){
		receptacleCoinValue = 0;
		coinRackValue = new int[coinValues.length];
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
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0) {

	}

	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0) {
		
	}

	@Override
	public void coinAdded(CoinRack arg0, Coin arg1) {
		coinRackValue[valueToRackMap.get(arg1.getValue())] += 1;
	}

	@Override
	public void coinRemoved(CoinRack arg0, Coin arg1) {
		coinRackValue[valueToRackMap.get(arg1.getValue())] -= 1;
	}

	@Override
	public void coinsEmpty(CoinRack arg0) {
		
	}

	@Override
	public void coinsFull(CoinRack arg0) {
		
	}

	@Override
	public void coinAdded(CoinReceptacle arg0, Coin arg1) {
		receptacleCoinValue+= arg1.getValue();
		
	}

	@Override
	public void coinsFull(CoinReceptacle arg0) {

		
	}

	@Override
	public void coinsRemoved(CoinReceptacle arg0) {
		receptacleCoinValue = 0;
		
	}

	@Override
	public void disabled(CoinReceptacle arg0) {
		
	}

	@Override
	public void enabled(CoinReceptacle arg0) {
		
	}
	
	public int getNumberOfCoinsInRack(int coinRackValueToReturn){
		return coinRackValue[valueToRackMap.get(coinRackValueToReturn)];
	}
	public int getReceptacleAmount(){
		return receptacleCoinValue;
	}
	public int[] getCoinValues(){
		return coinValues;
	}
}
