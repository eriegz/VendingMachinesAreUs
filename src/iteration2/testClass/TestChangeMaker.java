package iteration2.testClass;

import static org.junit.Assert.*;

import java.util.Map;

import iteration2.ChangeMaker;
import iteration2.CoinInventory;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vendingmachinesareus.CapacityExceededException;
import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.EmptyException;

/**
 * The Class TestChangeMaker.
 */
public class TestChangeMaker {
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		mockeryContext = new JUnit4Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
		mockingInterfacesContext = new JUnit4Mockery();
		numberOfCoinsReleased = new int[5];
	}

	/**
	 * Tear down.
	 */
	@After
	public void tearDown() {

	}

	/**
	 * Test make change with enough change.
	 */
	@Test
	public void testMakeChangeWithEnoughChange() {
		final CoinRack rack []= new CoinRack[5];
		for(int i = 0; i < rack.length; i++){
			rack[i] = makeCoinRack(i, rackNames[i]);
		}
		final CoinInventory inventory = makeInventory(new int[]{1,1,1,1,1});
		final Map<Integer, CoinRack> map = makeMap(rack);
		assertTrue(ChangeMaker.makeChange(10, map, inventory));
		assertEquals(numberOfCoinsReleased[1], 1);
	}
	
	/**
	 * Test make change without enough change.
	 */
	@Test
	public void testMakeChangeWithoutEnoughChange(){
		final CoinRack rack []= new CoinRack[5];
		for(int i = 0; i < rack.length; i++){
			rack[i] = makeCoinRack(i, rackNames[i]);
		}
		final CoinInventory inventory = makeInventory(new int[]{0,0,0,0,0});
		final Map<Integer, CoinRack> map = makeMap(rack);
		assertFalse(ChangeMaker.makeChange(10, map, inventory));
	}
	
	/**
	 * Test make change capacity exceeded exception.
	 */
	@Test
	public void testMakeChangeCapacityExceededException(){
		final CoinRack rack []= new CoinRack[5];
		for(int i = 0; i < rack.length; i++){
			rack[i] = makeCoinRackThrowException(rackNames[i]);
		}
		final CoinInventory inventory = makeInventory(new int[]{0,0,0,0,0});
		final Map<Integer, CoinRack> map = makeMap(rack);
		assertFalse(ChangeMaker.makeChange(10, map, inventory));
	}
	
	/**
	 * Test to money full digits.
	 */
	@Test
	public void testToMoneyFullDigits(){
		assertEquals(ChangeMaker.toMoney(111), "$1.11");
	}
	
	/**
	 * Test to money no cents.
	 */
	@Test
	public void testToMoneyNoCents(){
		assertEquals(ChangeMaker.toMoney(100), "$1.00");
	}
	
	/**
	 * Test to money no dollars.
	 */
	@Test
	public void testToMoneyNoDollars(){
		assertEquals(ChangeMaker.toMoney(88), "$0.88");
	}
	
	/**
	 * Test to money no tens cents.
	 */
	@Test
	public void testToMoneyNoTensCents(){
		assertEquals(ChangeMaker.toMoney(101), "$1.01");
	}
	
	/**
	 * Test to money ten cents but no cents.
	 */
	@Test
	public void testToMoneyTenCentsButNoCents(){
		assertEquals(ChangeMaker.toMoney(110), "$1.10");
	}
	
	/**
	 * Make map.
	 *
	 * @param racks the racks
	 * @return the map
	 */
	private Map<Integer,CoinRack> makeMap(final CoinRack[] racks){
		@SuppressWarnings("unchecked")
		final Map<Integer,CoinRack> ret = (Map<Integer,CoinRack>)mockingInterfacesContext.mock(Map.class);
		mockingInterfacesContext.checking(new Expectations(){{
			allowing(ret).get(coinValues[0]);
			will(returnValue(racks[0]));
			allowing(ret).get(coinValues[1]);
			will(returnValue(racks[1]));
			allowing(ret).get(coinValues[2]);
			will(returnValue(racks[2]));
			allowing(ret).get(coinValues[3]);
			will(returnValue(racks[3]));
			allowing(ret).get(coinValues[4]);
			will(returnValue(racks[4]));
		}});
		return ret;		
	}
	
	/**
	 * Make inventory.
	 *
	 * @param numberOfCoinsInRack the number of coins in rack
	 * @return the coin inventory
	 */
	private CoinInventory makeInventory(final int[] numberOfCoinsInRack) {
		assert (numberOfCoinsInRack.length == 5);
		final CoinInventory ret = mockingInterfacesContext.mock(CoinInventory.class);
		mockingInterfacesContext.checking(new Expectations() {
			{
				allowing(ret).getNumberOfCoinsInRack(coinValues[0]);
				will(returnValue(numberOfCoinsInRack[0]));
				allowing(ret).getNumberOfCoinsInRack(coinValues[1]);
				will(returnValue(numberOfCoinsInRack[1]));
				allowing(ret).getNumberOfCoinsInRack(coinValues[2]);
				will(returnValue(numberOfCoinsInRack[2]));
				allowing(ret).getNumberOfCoinsInRack(coinValues[3]);
				will(returnValue(numberOfCoinsInRack[3]));
				allowing(ret).getNumberOfCoinsInRack(coinValues[4]);
				will(returnValue(numberOfCoinsInRack[4]));
				allowing(ret).getCoinValues();
				will(returnValue(coinValues));
			}
		});
		return ret;

	}

	/**
	 * Make coin rack.
	 *
	 * @param arrayIndex the array index
	 * @param name the name
	 * @return the coin rack
	 */
	private CoinRack makeCoinRack(final int arrayIndex, String name) {
		final CoinRack ret = mockeryContext.mock(CoinRack.class, name);
		try {
			mockeryContext.checking(new Expectations() {
				{
					allowing(ret).releaseCoin();
					will(new Action() {
						@Override
						public void describeTo(Description description) {
						}

						@Override
						public Object invoke(Invocation invocation)
								throws Throwable {
							numberOfCoinsReleased[arrayIndex]++;
							return null;
						}
					});
				}
			});
		} catch (CapacityExceededException | EmptyException | DisabledException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * Make coin rack throw exception.
	 *
	 * @param name the name
	 * @return the coin rack
	 */
	private CoinRack makeCoinRackThrowException(String name){
		final CoinRack ret = mockeryContext.mock(CoinRack.class, name);
		try {
			mockeryContext.checking(new Expectations() {
				{
					allowing(ret).releaseCoin();
					will(throwException(new CapacityExceededException()));
				}
			});
		} catch (CapacityExceededException | EmptyException | DisabledException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/** The mockery context. */
	private Mockery mockeryContext;
	
	/** The mocking interfaces context. */
	private Mockery mockingInterfacesContext;
	
	/** The number of coins released. */
	private int[] numberOfCoinsReleased;
	
	/** The rack names. */
	final String rackNames[] = {"5","10","25","100", "200"};
	
	/** The coin values. */
	final private int[] coinValues = { 5, 10, 25, 100, 200 };
}
