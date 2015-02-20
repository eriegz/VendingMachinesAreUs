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

	}
	
	@After
	public void tearDown(){
		
	}
	
	
	
	@Test
	public void testCoinAdded1()
	{
		//
	}

	private CoinRack[] coinRacks;
	private CoinReceptacle coinReceptacle;
	private Map<Integer, CoinRack> coinRackMap;
	private int [] coinValues;
	private StandardPopVendingMachine machine;
}
