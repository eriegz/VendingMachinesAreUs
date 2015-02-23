package iteration2.testClass;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.CoinReceptacle;
import com.vendingmachinesareus.StandardPopVendingMachine;

public class TestCoinLocationManager {

	@Before
	public void SetUp(){
		coinValues = new int[]{5, 10, 25, 100, 200};
		coinRacks = new CoinRack[coinValues.length];
		coinRackMap = new HashMap<Integer,CoinRack>();
		for(int i = 0; i< coinValues.length; i++){
			coinRacks[i] = machine.getCoinRack(i);
			coinRackMap.put(coinValues[i], coinRacks[i]);
		}
		coinReceptacle = machine.getCoinReceptacle();
	}
	
	@After
	public void tearDown(){
		
	}
	
	
	
	@Test
	public void testCoinAdded1()
	{
		
	}

	private CoinRack[] coinRacks;
	private CoinReceptacle coinReceptacle;
	private Map<Integer, CoinRack> coinRackMap;
	private int [] coinValues;
	private StandardPopVendingMachine machine;
}
