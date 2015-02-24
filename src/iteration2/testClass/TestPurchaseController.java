package iteration2.testClass;

import static org.junit.Assert.*;

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
import com.vendingmachinesareus.Coin;
import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.EmptyException;
import com.vendingmachinesareus.SelectionButton;

public class TestPurchaseController {

	@Before
	public void SetUp() {
		new VendingMachineGUI();
		coinValues = new int[] { 5, 10, 25, 100, 200 };
		racks = new CoinRack[5];
		for (int i = 0; i < racks.length; i++) {
			racks[i] = makeCoinRack(i);
		}
		hasDispensedPop = false;
		mockingContext = new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};
		final SelectionButton button = mockingContext
				.mock(SelectionButton.class);
		mockingContext.checking(new Expectations() {
			{
			}
		});
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations() {
			{
				
			}
		});
		final CoinInventory coin = mockingContext.mock(CoinInventory.class);
		mockingContext.checking(new Expectations() {
			{
				
			}
		});

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
				
			}
		});
		final CardManager card = mockingContext.mock(CardManager.class);
		mockingContext.checking(new Expectations() {
			{
			
			}
		});
		final ChangeMaker changeMaker = mockingContext.mock(ChangeMaker.class);
		mockingContext.checking(new Expectations() {
			{
				
			}
		});
		
		purchaseController = new Iteration2PurchaseController(pop, coin, coinLocation, card);
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testPressed0() {
		final SelectionButton button = mockingContext
				.mock(SelectionButton.class);
		mockingContext.checking(new Expectations() {
			{
			}
		});
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 0
			oneOf(pop).getCost(button);
			will(returnValue(10));
			oneOf(pop).hasPop(button);
			will(returnValue(false));
		}});
		purchaseController.pressed(button);
		assertEquals("Notice: No Pop of that type", System.out.toString());
	}
	
	@Test
	public void testPressed1() {
		final SelectionButton button = mockingContext
				.mock(SelectionButton.class);
		mockingContext.checking(new Expectations() {
			{
			}
		});
		final PopInventory pop = mockingContext.mock(PopInventory.class);
		mockingContext.checking(new Expectations()
		{{
			// 1
			oneOf(pop).getCost(button);
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
		purchaseController.pressed(button);
		assertEquals("Notice: Price of Pop is $0.10", System.out.toString());
	}
	
	@Test
	public void testPressed2() {
		final SelectionButton button = mockingContext
				.mock(SelectionButton.class);
		mockingContext.checking(new Expectations() {
			{
			}
		});
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
			oneOf(coin).getReceptacleAmount();
			will(returnValue(0));
		}});
		final CardManager card = mockingContext.mock(CardManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 2
			oneOf(card).hasCard();
			will(returnValue(true));
			oneOf(card).verify("1234");
			oneOf(card).charge(10, "1234");
			will(returnValue(true));
		}});
		purchaseController.pressed(button);
		assertEquals(true, hasDispensedPop);
	}
	
	@Test
	public void testPressed3() {
		final SelectionButton button = mockingContext
				.mock(SelectionButton.class);
		mockingContext.checking(new Expectations() {
			{
			}
		});
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
			// 3
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
			// 3
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
		}});
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
		purchaseController.pressed(button);
		assertEquals(true, hasDispensedPop);
	}
	
	@Test
	public void testPressed4() {
		final SelectionButton button = mockingContext
				.mock(SelectionButton.class);
		mockingContext.checking(new Expectations() {
			{
			}
		});
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
			// 4
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
			// 4
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
		}});
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
		purchaseController.pressed(button);
		assertEquals(true, hasDispensedPop);
	}
	
	@Test
	public void testPressed5() {
		final SelectionButton button = mockingContext
				.mock(SelectionButton.class);
		mockingContext.checking(new Expectations() {
			{
			}
		});
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
			// 5
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
			// 5
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
		}});
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
		purchaseController.pressed(button);
		assertEquals(true, hasDispensedPop);
	}
	
	@Test
	public void testPressed6() {
		final SelectionButton button = mockingContext
				.mock(SelectionButton.class);
		mockingContext.checking(new Expectations() {
			{
			}
		});
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
			oneOf(coin).getReceptacleAmount();
			will(returnValue(0));
		}});
		final CardManager card = mockingContext.mock(CardManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 6
			oneOf(card).hasCard();
			will(returnValue(true));
			// 6
			oneOf(card).verify("1234");
			will(returnValue(true));
			// 6
			oneOf(card).charge(10, "1234");
			will(returnValue(true));
		}});
		purchaseController.pressed(button);
		assertEquals(false, hasDispensedPop);
	}
	
	@Test
	public void testPressed7() {
		final SelectionButton button = mockingContext
				.mock(SelectionButton.class);
		mockingContext.checking(new Expectations() {
			{
			}
		});
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
			oneOf(coin).getReceptacleAmount();
			will(returnValue(0));
		}});
		final CardManager card = mockingContext.mock(CardManager.class);
		mockingContext.checking(new Expectations() {
		{
			// 7
			oneOf(card).hasCard();
			will(returnValue(true));
			// 7
			oneOf(card).verify("1234");
			will(returnValue(true));
			// 7
			oneOf(card).charge(10, "1234");
			will(returnValue(false));
		}});
		purchaseController.pressed(button);
		assertEquals("Notice: Cannot Charge Card", System.out.toString());
	}
	
	@Test
	public void testPressed8() {
		final SelectionButton button = mockingContext
				.mock(SelectionButton.class);
		mockingContext.checking(new Expectations() {
			{
			}
		});
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
			oneOf(card).verify("4321");
			will(returnValue(false));
		}});
		purchaseController.pressed(button);
		assertEquals("Notice: PIN Not Valid", System.out.toString());
	}
	
	@Test
	public void testPressed9() {
		final SelectionButton button = mockingContext
				.mock(SelectionButton.class);
		mockingContext.checking(new Expectations() {
			{
			}
		});
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
			// 9
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
			// 9
			oneOf(coin).getReceptacleAmount();
			will(returnValue(500));
		}});
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
			// 9
			oneOf(changeMaker).makeChange(490, coinRackMap, coin);
			will(returnValue(false));
		}});
		purchaseController.pressed(button);
		assertEquals("Notice: Cannot Make Change", System.out.toString());
	}

	private CoinRack makeCoinRack(final int arrayIndex) {
		final CoinRack ret = mockingContext.mock(CoinRack.class);
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
	private Iteration2PurchaseController purchaseController;
	private Mockery mockingContext;
	private PopInventory popInventory;
	private CoinInventory coinInventory;
	private CoinLocationManager coinLocationManager;
	private CardManager cardManager;
	private int[] coinValues;
	private int[] numberOfCoinsReleased;
	private boolean hasDispensedPop;
	private int arrayIndex;
	private CoinRack[] racks;
}
