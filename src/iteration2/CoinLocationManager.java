package iteration2;

import java.util.Map;

import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.CoinReceptacle;

public interface CoinLocationManager {
	public Map<Integer, CoinRack> getCoinRackMap();
	public CoinRack getCoinRackForValue(int value);
	public CoinReceptacle getCoinReceptacle();
}
