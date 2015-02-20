package iteration2;

import java.util.Map;

import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.CoinReceptacle;

/**
 * The Interface CoinLocationManager.
 * Classes that implement this will manage the mapping of CoinRacks to coin denominations
 */
public interface CoinLocationManager {
	
	/**
	 * Gets the coin rack map.
	 *
	 * @return the coin rack map
	 */
	public Map<Integer, CoinRack> getCoinRackMap();
	
	/**
	 * Gets the coin rack for value.
	 *
	 * @param value the value
	 * @return the coin rack for value
	 */
	public CoinRack getCoinRackForValue(int value);
	
	/**
	 * Gets the coin receptacle.
	 *
	 * @return the coin receptacle
	 */
	public CoinReceptacle getCoinReceptacle();
}
