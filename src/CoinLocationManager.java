package iteration2;

import java.util.HashMap;
import java.util.Map;

import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.CoinReceptacle;
import com.vendingmachinesareus.StandardPopVendingMachine;

public class CoinLocationManager {
	private CoinRack[] coinRacks;
	private CoinReceptacle coinReceptacle;
	private Map<Integer, CoinRack> coinRackMap;
	public CoinLocationManager(int[] coinValues, StandardPopVendingMachine machine){
		coinRacks = new CoinRack[coinValues.length];
		coinRackMap = new HashMap<Integer,CoinRack>();
		for(int i = 0; i< coinValues.length; i++){
			coinRacks[i] = machine.getCoinRack(i);
			coinRackMap.put(coinValues[i], coinRacks[i]);
		}
		coinReceptacle = machine.getCoinReceptacle();
	}
	public Map<Integer, CoinRack> getCoinRackMap(){
		return coinRackMap;
	}
	public CoinRack getCoinRackForValue(int value){
		return coinRackMap.get(value);
	}
	public CoinReceptacle getCoinReceptacle(){
		return coinReceptacle;
	}
}
