package iteration2.testClass;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import iteration2.CardManager;
import iteration2.ChangeMaker;
import iteration2.CoinInventory;
import iteration2.CoinLocationManager;
import iteration2.Iteration2PurchaseController;
import iteration2.PopInventory;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GUI.VendingMachineGUI;

import com.vendingmachinesareus.CapacityExceededException;
import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.CoinReceptacle;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.EmptyException;
import com.vendingmachinesareus.SelectionButton;

/**
 * The Class TestPurchaseController.
 */
public class TestPurchaseController {

	/**
	 * Sets up.
	 */
	@Before
	public void SetUp() {
		
		mockingContext = new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
		//Used for output stream
		System.setOut(new PrintStream(outContent));
		new VendingMachineGUI();
		
		coinValues = new int[] { 5, 10, 25, 100, 200 };
		
		racks = new CoinRack[5];
		//Need Unique Names
		String coinRackNames[]= {"5","10","25","100", "200"};
		for (int i = 0; i < racks.length; i++) {
			racks[i] = makeCoinRack(i, coinRackNames[i]);
		}
		
		//Set as false to begin
		hasDispensedPop = false;
		
		button = mockingContext.mock(SelectionButton.class, "Selection Button");
		mockingContext.checking(new Expectations() {
			{
			}
		});
		defaultPop = mockingContext.mock(PopInventory.class, "DefaultPopInventory");
		mockingContext.checking(new Expectations() {
			{
				
			}
		});
		defaultCoin = mockingContext.mock(CoinInventory.class, "DefaultCoinInventory");
		mockingContext.checking(new Expectations() {
			{
				
			}
		});
		defaultCoinLocation = mockingContext
				.mock(CoinLocationManager.class, "DefaultCoin");
		mockingContext.checking(new Expectations() {
			{
				allowing(defaultCoinLocation).getCoinReceptacle();
				will(returnValue(coinReceptacle));
			}
		});
		defaultCard = mockingContext.mock(CardManager.class, "DefaultCardManager");
		mockingContext.checking(new Expectations() {
			{
			
			}
		});
		coinReceptacle = mockingContext.mock(CoinReceptacle .class, "CoinReceptacle");
		try {
			mockingContext.checking(new Expectations() {
				{
					allowing(coinReceptacle).storeCoins();
				}
			});
		} catch (CapacityExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DisabledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Tears down.
	 */
	@After
	public void tearDown() {
		mockingContext = null;
		purchaseController = null;
		button = null;
	}

	/**
	 * Test pressed0.
	 */
	@Test
	public void testPressed0() {
		
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 0
			oneOf(pop).getCost(button);
			will(returnValue(10));
			oneOf(pop).hasPop(button);
			will(returnValue(false));
		}});
		purchaseController = new Iteration2PurchaseController(pop, defaultCoin, defaultCoinLocation, defaultCard);
		purchaseController.pressed(button);
		assertEquals("Notice: No Pop of that type", outContent.toString());
		assertEquals(false, hasDispensedPop);
	}
	
	/**
	 * Test pressed1.
	 */
	@Test
	public void testPressed1() {
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 1
			allowing(pop).getCost(button);
			will(returnValue(10));
			oneOf(pop).hasPop(button);
			will(returnValue(true));
		}});
		final CoinInventory coin = mockingContext.mock(CoinInventory.class);
		mockingContext.checking(new Expectations() {
			{
			// 1
			oneOf(coin).getReceptacleAmount();
			will(returnValue(0));
		}});
		final CardManager card = mockingContext.mock(CardManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 1
			oneOf(card).hasCard();
			will(returnValue(false));
		}});
		purchaseController = new Iteration2PurchaseController(pop, coin, defaultCoinLocation, card);
		purchaseController.pressed(button);
		assertEquals("Notice: Price of Pop is $0.10", outContent.toString());
		assertEquals(false, hasDispensedPop);
	}
	
	/**
	 * Test pressed2.
	 */
	@Test
	public void testPressed2() {
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 2
			oneOf(pop).getCost(button);
			will(returnValue(10));
			oneOf(pop).hasPop(button);
			will(returnValue(true));
			oneOf(pop).dispense(button);
			will(new Action() {

				@Override
				public void describeTo(Description arg0) {
				}

				@Override
				public Object invoke(Invocation invocation)
						throws Throwable {
					hasDispensedPop = true;
					return true;
				}
			});
		}});
		final CoinInventory coin = mockingContext.mock(CoinInventory.class);
		mockingContext.checking(new Expectations() {
		{
			// 2
			allowing(coin).getReceptacleAmount();
			will(returnValue(0));
			allowing(coin).coinsRemoved(null);
		}});
		final CoinLocationManager coinLocation = mockingContext
				.mock(CoinLocationManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 2
			allowing(coinLocation).getCoinReceptacle();
			will(returnValue(coinReceptacle));
		}});
		final CardManager card = mockingContext.mock(CardManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 2
			oneOf(card).hasCard();
			will(returnValue(true));
			oneOf(card).verify(null);
			will(returnValue(true));
			oneOf(card).charge(10, null);
			will(returnValue(true));
		}});
		purchaseController = new Iteration2PurchaseController(pop, coin, defaultCoinLocation, card);
		purchaseController.pressed(button);
		assertEquals(true, hasDispensedPop);
	}
	
	/**
	 * Test pressed3.
	 */
	@Test
	public void testPressed3() {
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 3
			oneOf(pop).getCost(button);
			will(returnValue(10));
			oneOf(pop).hasPop(button);
			will(returnValue(true));
			oneOf(pop).dispense(button);
			will(new Action() {

				@Override
				public void describeTo(Description arg0) {
				}

				@Override
				public Object invoke(Invocation invocation)
						throws Throwable {
					hasDispensedPop = true;
					return true;
				}
			});
		}});
		final CoinInventory coin = mockingContext.mock(CoinInventory.class);
		mockingContext.checking(new Expectations() {
		{
			allowing(coin).getNumberOfCoinsInRack(200);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(100);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(50);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(25);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(10);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(5);
			will(returnValue(10));
			allowing(coin).getCoinValues();
			will(returnValue(coinValues));
			// 3
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
			// 3
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
		}});
		@SuppressWarnings("unchecked")
		final Map<Integer, CoinRack> coinRackMap = (Map<Integer, CoinRack>) mockingContext
				.mock(Map.class);
		mockingContext.checking(new Expectations() {
			{
				allowing(coinRackMap).get(coinValues[0]);
				will(returnValue(racks[0]));
				allowing(coinRackMap).get(coinValues[1]);
				will(returnValue(racks[1]));
				allowing(coinRackMap).get(coinValues[2]);
				will(returnValue(racks[2]));
				allowing(coinRackMap).get(coinValues[3]);
				will(returnValue(racks[3]));
				allowing(coinRackMap).get(coinValues[4]);
				will(returnValue(racks[4]));
			}
		});

		final CoinLocationManager coinLocation = mockingContext
				.mock(CoinLocationManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 3
			allowing(coinLocation).getCoinReceptacle();
			will(returnValue(coinReceptacle));
			oneOf(coinLocation).getCoinRackMap();
			will(returnValue(coinRackMap));
		}});
		final ChangeMaker changeMaker = mockingContext.mock(ChangeMaker.class);
		mockingContext.checking(new Expectations() {
		{
			// 3
			oneOf(changeMaker).makeChange(490, coinRackMap, coin);
			will(returnValue(true));
		}});
		purchaseController = new Iteration2PurchaseController(pop, coin, coinLocation, defaultCard);
		purchaseController.pressed(button);
		assertEquals(true, hasDispensedPop);
	}
	
	/**
	 * Test pressed4.
	 */
	@Test
	public void testPressed4() {
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 4
			oneOf(pop).getCost(button);
			will(returnValue(50));
			oneOf(pop).hasPop(button);
			will(returnValue(true));
			oneOf(pop).dispense(button);
			will(new Action() {

				@Override
				public void describeTo(Description arg0) {
				}

				@Override
				public Object invoke(Invocation invocation)
						throws Throwable {
					hasDispensedPop = true;
					return true;
				}
			});
		}});
		final CoinInventory coin = mockingContext.mock(CoinInventory.class);
		mockingContext.checking(new Expectations() {
		{
			allowing(coin).getNumberOfCoinsInRack(200);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(100);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(50);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(25);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(10);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(5);
			will(returnValue(10));
			allowing(coin).getCoinValues();
			will(returnValue(coinValues));
			// 4
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
			// 4
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
		}});
		@SuppressWarnings("unchecked")
		final Map<Integer, CoinRack> coinRackMap = (Map<Integer, CoinRack>) mockingContext
				.mock(Map.class);
		mockingContext.checking(new Expectations() {
			{
				allowing(coinRackMap).get(coinValues[0]);
				will(returnValue(racks[0]));
				allowing(coinRackMap).get(coinValues[1]);
				will(returnValue(racks[1]));
				allowing(coinRackMap).get(coinValues[2]);
				will(returnValue(racks[2]));
				allowing(coinRackMap).get(coinValues[3]);
				will(returnValue(racks[3]));
				allowing(coinRackMap).get(coinValues[4]);
				will(returnValue(racks[4]));
			}
		});

		final CoinLocationManager coinLocation = mockingContext
				.mock(CoinLocationManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 4
			allowing(coinLocation).getCoinReceptacle();
			will(returnValue(coinReceptacle));
			oneOf(coinLocation).getCoinRackMap();
			will(returnValue(coinRackMap));
		}});
		final ChangeMaker changeMaker = mockingContext.mock(ChangeMaker.class);
		mockingContext.checking(new Expectations() {
		{
			// 4
			oneOf(changeMaker).makeChange(450, coinRackMap, coin);
			will(returnValue(true));
		}});
		purchaseController = new Iteration2PurchaseController(pop, coin, coinLocation, defaultCard);
		purchaseController.pressed(button);
		assertEquals(true, hasDispensedPop);
	}
	
	/**
	 * Test pressed5.
	 */
	@Test
	public void testPressed5() {
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 5
			oneOf(pop).getCost(button);
			will(returnValue(250));
			oneOf(pop).hasPop(button);
			will(returnValue(true));
			oneOf(pop).dispense(button);
			will(new Action() {

				@Override
				public void describeTo(Description arg0) {
				}

				@Override
				public Object invoke(Invocation invocation)
						throws Throwable {
					hasDispensedPop = true;
					return true;
				}
			});
		}});
		final CoinInventory coin = mockingContext.mock(CoinInventory.class);
		mockingContext.checking(new Expectations() {
		{
			allowing(coin).getNumberOfCoinsInRack(200);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(100);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(50);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(25);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(10);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(5);
			will(returnValue(10));
			allowing(coin).getCoinValues();
			will(returnValue(coinValues));
			// 5
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
			// 5
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
		}});
		@SuppressWarnings("unchecked")
		final Map<Integer, CoinRack> coinRackMap = (Map<Integer, CoinRack>) mockingContext
				.mock(Map.class);
		mockingContext.checking(new Expectations() {
			{
				allowing(coinRackMap).get(coinValues[0]);
				will(returnValue(racks[0]));
				allowing(coinRackMap).get(coinValues[1]);
				will(returnValue(racks[1]));
				allowing(coinRackMap).get(coinValues[2]);
				will(returnValue(racks[2]));
				allowing(coinRackMap).get(coinValues[3]);
				will(returnValue(racks[3]));
				allowing(coinRackMap).get(coinValues[4]);
				will(returnValue(racks[4]));
			}
		});

		final CoinLocationManager coinLocation = mockingContext
				.mock(CoinLocationManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 5
			allowing(coinLocation).getCoinReceptacle();
			will(returnValue(coinReceptacle));
			oneOf(coinLocation).getCoinRackMap();
			will(returnValue(coinRackMap));
		}});
		final ChangeMaker changeMaker = mockingContext.mock(ChangeMaker.class);
		mockingContext.checking(new Expectations() {
		{
			// 5
			oneOf(changeMaker).makeChange(250, coinRackMap, coin);
			will(returnValue(true));
		}});
		purchaseController = new Iteration2PurchaseController(pop, coin, coinLocation, defaultCard);
		purchaseController.pressed(button);
		assertEquals(true, hasDispensedPop);
	}
	
	/**
	 * Test pressed6.
	 */
	@Test
	public void testPressed6() {
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 6
			oneOf(pop).getCost(button);
			will(returnValue(10));
			oneOf(pop).hasPop(button);
			will(returnValue(true));
			oneOf(pop).dispense(button);
			will(returnValue(false));
		}});
		final CoinInventory coin = mockingContext.mock(CoinInventory.class);
		mockingContext.checking(new Expectations() {
		{
			// 6
			allowing(coin).getReceptacleAmount();
			will(returnValue(0));
			allowing(coin).coinsRemoved(null);
		}});
		final CardManager card = mockingContext.mock(CardManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 6
			oneOf(card).hasCard();
			will(returnValue(true));
			// 6
			oneOf(card).verify(null);
			will(returnValue(true));
			// 6
			oneOf(card).charge(10, null);
			will(returnValue(true));
		}});
		purchaseController = new Iteration2PurchaseController(pop, coin, defaultCoinLocation, card);
		purchaseController.pressed(button);
		assertEquals(false, hasDispensedPop);
	}
	
	/**
	 * Test pressed7.
	 */
	@Test
	public void testPressed7() {
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 7
			oneOf(pop).getCost(button);
			will(returnValue(10));
			oneOf(pop).hasPop(button);
			will(returnValue(true));
		}});
		final CoinInventory coin = mockingContext.mock(CoinInventory.class);
		mockingContext.checking(new Expectations() {
		{
			// 7
			allowing(coin).getReceptacleAmount();
			will(returnValue(0));
		}});
		final CardManager card = mockingContext.mock(CardManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 7
			oneOf(card).hasCard();
			will(returnValue(true));
			// 7
			oneOf(card).verify(null);
			will(returnValue(true));
			// 7
			oneOf(card).charge(10, null);
			will(returnValue(false));
		}});
		purchaseController = new Iteration2PurchaseController(pop, coin, defaultCoinLocation, card);
		purchaseController.pressed(button);
		assertEquals("Notice: Please Enter PINNotice: Cannot Charge Card", outContent.toString());
		assertEquals(false, hasDispensedPop);
	}
	
	/**
	 * Test pressed8.
	 */
	@Test
	public void testPressed8() {
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 8
			oneOf(pop).getCost(button);
			will(returnValue(10));
			oneOf(pop).hasPop(button);
			will(returnValue(true));
		}});
		final CoinInventory coin = mockingContext.mock(CoinInventory.class);
		mockingContext.checking(new Expectations() {
		{
			// 8
			oneOf(coin).getReceptacleAmount();
			will(returnValue(0));
		}});
		final CardManager card = mockingContext.mock(CardManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 8
			oneOf(card).hasCard();
			will(returnValue(true));
			// 8
			oneOf(card).verify(null);
			will(returnValue(false));
		}});
		purchaseController = new Iteration2PurchaseController(pop, coin, defaultCoinLocation, card);
		purchaseController.pressed(button);
		assertEquals("Notice: Please Enter PINNotice: PIN Not Valid", outContent.toString());
		assertEquals(false, hasDispensedPop);
	}
	
	/**
	 * Test pressed9.
	 */
	@Test
	public void testPressed9() {
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 9
			oneOf(pop).getCost(button);
			will(returnValue(10));
			oneOf(pop).hasPop(button);
			will(returnValue(true));
		}});
		final CoinInventory coin = mockingContext.mock(CoinInventory.class);
		mockingContext.checking(new Expectations() {
		{
			allowing(coin).getNumberOfCoinsInRack(200);
			will(returnValue(0));
			allowing(coin).getNumberOfCoinsInRack(100);
			will(returnValue(0));
			allowing(coin).getNumberOfCoinsInRack(50);
			will(returnValue(0));
			allowing(coin).getNumberOfCoinsInRack(25);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(10);
			will(returnValue(10));
			allowing(coin).getNumberOfCoinsInRack(5);
			will(returnValue(10));
			allowing(coin).getCoinValues();
			will(returnValue(coinValues));
			// 9
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
			// 9
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
		}});
		@SuppressWarnings("unchecked")
		final Map<Integer, CoinRack> coinRackMap = (Map<Integer, CoinRack>) mockingContext
				.mock(Map.class);
		mockingContext.checking(new Expectations() {
			{
				allowing(coinRackMap).get(coinValues[0]);
				will(returnValue(racks[0]));
				allowing(coinRackMap).get(coinValues[1]);
				will(returnValue(racks[1]));
				allowing(coinRackMap).get(coinValues[2]);
				will(returnValue(racks[2]));
				allowing(coinRackMap).get(coinValues[3]);
				will(returnValue(racks[3]));
				allowing(coinRackMap).get(coinValues[4]);
				will(returnValue(racks[4]));
			}
		});

		final CoinLocationManager coinLocation = mockingContext
				.mock(CoinLocationManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 9
			oneOf(coinLocation).getCoinRackMap();
			will(returnValue(coinRackMap));
		}});
		final ChangeMaker changeMaker = mockingContext.mock(ChangeMaker.class);
		mockingContext.checking(new Expectations() {
		{
			oneOf(changeMaker);
			 //9
			ChangeMaker.makeChange(490, coinRackMap, coin);
			will(returnValue(false));
		}});
		purchaseController = new Iteration2PurchaseController(pop, coin, coinLocation, defaultCard);
		purchaseController.pressed(button);
		assertEquals("Notice: Cannot Make Change. Please call (555)555-5555.", outContent.toString());
		assertEquals(false, hasDispensedPop);
	}

	/**
	 * Make coin rack.
	 *
	 * @param arrayIndex the array index
	 * @param coinRackName the coin rack name
	 * @return the coin rack
	 */
	private CoinRack makeCoinRack(final int arrayIndex, String coinRackName) {
		final CoinRack ret = mockingContext.mock(CoinRack.class, coinRackName);
		try {
			mockingContext.checking(new Expectations() {
				{
					allowing(ret).releaseCoin();
					will(new Action() {

						@Override
						public void describeTo(Description description) {
						}

						@Override
						public Object invoke(Invocation invocation)
								throws Throwable {
							//numberOfCoinsReleased[arrayIndex]++;
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
	
	/** The out content. */
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	/** The purchase controller. */
	private Iteration2PurchaseController purchaseController;
	
	/** The mocking context. */
	private Mockery mockingContext;
	
	/** The default pop. */
	private PopInventory defaultPop;
	
	/** The default coin. */
	private CoinInventory defaultCoin;
	
	/** The default coin location. */
	private CoinLocationManager defaultCoinLocation;
	
	/** The default card. */
	private CardManager defaultCard;
	
	/** The coin values. */
	private int[] coinValues;
	
	/** The number of coins released. */
	private int[] numberOfCoinsReleased;
	
	/** The has dispensed pop. */
	private boolean hasDispensedPop;
	
	/** The racks. */
	private CoinRack[] racks;
	
	/** The button. */
	private SelectionButton button;
	private CoinReceptacle coinReceptacle;
}
