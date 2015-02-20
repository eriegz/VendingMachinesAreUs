package iteration2.testClass;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Map;

import iteration2.CoinLocationManager;
import iteration2.Iteration2CoinLocationManager;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.CoinReceptacle;
import com.vendingmachinesareus.StandardPopVendingMachine;

// TODO: Auto-generated Javadoc
/**
 * The Class TestCoinLocation.
 * Note the getters are not tested
 */
public class TestCoinLocation {
	
	/** The coin rack array. */
	private CoinRack[] coinRackArray;
	
	/** The coin value. */
	private final int[] coinValue ={5,10,25,100,200};
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp(){
		String names[] = {"1","2","3","4","5"};
		coinRackArray = new CoinRack[5];
		for(int i = 0; i < names.length; i++){
			coinRackArray[i]  = getCoinRack(names[i]);
		}
		coinLocationManager = new Iteration2CoinLocationManager(coinValue, getVendingMachine(coinRackArray));
	}
	
	/**
	 * Tear down.
	 */
	@After
	public void tearDown(){
	}
	
	/**
	 * Test constructor.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConstructor(){
		
		
		//Get Rack using reflection
		Map<Integer, CoinRack> rack = null;
		Field rackField = null;
		try {
			rackField = coinLocationManager.getClass().getDeclaredField("coinRackMap");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		rackField.setAccessible(true);
		try {
			rack = (Map<Integer, CoinRack>) rackField.get(coinLocationManager);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < 5; i++){
			assertEquals(coinRackArray[i],rack.get(coinValue[i]));
		}
	}
	
	/**
	 * Test get coin rack map.
	 */
	@Test
	public void testGetCoinRackMap(){
		//Get Rack using reflection
		Map<Integer, CoinRack> rack = null;
		Field rackField = null;
		try {
			rackField = coinLocationManager.getClass().getDeclaredField("coinRackMap");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		rackField.setAccessible(true);
		try {
			rack = (Map<Integer, CoinRack>) rackField.get(coinLocationManager);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		assertEquals(rack, coinLocationManager.getCoinRackMap());
	}
	
	/**
	 * Gets the coin rack.
	 *
	 * @param name the name
	 * @return the coin rack
	 */
	private CoinRack getCoinRack(String name){
		final CoinRack ret = mockingContext.mock(CoinRack.class, name);
		return ret;
	}
	/**
	 * Gets the vending machine.
	 *
	 * @param coinRackArray the CoinRack array
	 * @return the vending machine
	 */
	private StandardPopVendingMachine getVendingMachine(
			final CoinRack[] coinRackArray) {
		final StandardPopVendingMachine VMmock = mockingContext
				.mock(StandardPopVendingMachine.class);
		final CoinReceptacle coinReceptacle = mockingContext.mock(CoinReceptacle.class);

		mockingContext.checking(new Expectations() {
			{
				allowing(VMmock).getCoinRack(0);
				will(returnValue(coinRackArray[0]));
				allowing(VMmock).getCoinRack(1);
				will(returnValue(coinRackArray[1]));
				allowing(VMmock).getCoinRack(2);
				will(returnValue(coinRackArray[2]));
				allowing(VMmock).getCoinRack(3);
				will(returnValue(coinRackArray[3]));
				allowing(VMmock).getCoinRack(4);
				will(returnValue(coinRackArray[4]));
				allowing(VMmock).getCoinReceptacle();;
				will(returnValue(coinReceptacle));
			}
		});
		return VMmock;
	}

	/** The mocking context. */
	private Mockery mockingContext = new JUnit4Mockery(){{
		setImposteriser(ClassImposteriser.INSTANCE);
	}
	};
	
	/** The coin location manager. */
	private CoinLocationManager coinLocationManager ;
}
