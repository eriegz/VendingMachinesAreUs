package iteration2;

import java.util.HashMap;
import java.util.Map;

import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.CoinReceptacle;
import com.vendingmachinesareus.StandardPopVendingMachine;

// TODO: Auto-generated Javadoc
/**
 * The Class Iteration2CoinLocationManager.
 * Implements @CoinLocationManager
 */
public class Iteration2CoinLocationManager implements CoinLocationManager{
	
	/** The coin racks. */
	private CoinRack[] coinRacks;
	
	/** The coin receptacle. */
	private CoinReceptacle coinReceptacle;
	
	/** A Mapping of coin denominations to CoinRacks which hold the denomination. */
	private Map<Integer, CoinRack> coinRackMap;
	
	/**
	 * Instantiates a new iteration2 coin location manager.
	 *
	 * @param coinValues the coin denominations in the machine
	 * @param machine The Standard Pop Vending Machine given
	 */
	public Iteration2CoinLocationManager(int[] coinValues, StandardPopVendingMachine machine){
		coinRacks = new CoinRack[coinValues.length];
		coinRackMap = new HashMap<Integer,CoinRack>();
		for(int i = 0; i< coinValues.length; i++){
			coinRacks[i] = machine.getCoinRack(i);
			coinRackMap.put(coinValues[i], coinRacks[i]);
		}
		coinReceptacle = machine.getCoinReceptacle();
	}
	
	/* (non-Javadoc)
	 * @see iteration2.CoinLocationManager#getCoinRackMap()
	 */
	public Map<Integer, CoinRack> getCoinRackMap(){
		return coinRackMap;
	}
	
	/* (non-Javadoc)
	 * @see iteration2.CoinLocationManager#getCoinRackForValue(int)
	 */
	public CoinRack getCoinRackForValue(int value){
		return coinRackMap.get(value);
	}
	
	/* (non-Javadoc)
	 * @see iteration2.CoinLocationManager#getCoinReceptacle()
	 */
	public CoinReceptacle getCoinReceptacle(){
		return coinReceptacle;
	}
}
