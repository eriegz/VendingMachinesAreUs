/**
 * 
 */
package iteration2.testClass;

import static org.junit.Assert.*;
import iteration2.Iteration2PopInventory;
import iteration2.Iteration2PopInventory.PopInfo;
import iteration2.PopInventory;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import GUI.VendingMachineGUI;

import com.vendingmachinesareus.CapacityExceededException;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.EmptyException;
import com.vendingmachinesareus.PopCanRack;
import com.vendingmachinesareus.SelectionButton;
import com.vendingmachinesareus.StandardPopVendingMachine;

/**
 * @author Taylor
 *
 */
public class TestPopInventory {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		numberOfPopsReleased = new int[6];
		new VendingMachineGUI();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getPopInfoTest() {
		SelectionButton[] sbArray = new SelectionButton[6];
		for(int i = 0; i< 6; i++){
			sbArray[i] = mockingContext.mock(SelectionButton.class, nameArray[i]);
		}
		Iteration2PopInventory inventory = new Iteration2PopInventory(getVendingMachine(sbArray));
		assertEquals(inventory.getPopInfo(sbArray[0]), inventory. new PopInfo());
	}
	
	@Test
	public void getCostTest(){
		SelectionButton[] sbArray = new SelectionButton[6];
		for(int i = 0;  i < 6; i++){
			sbArray[i] = mockingContext.mock(SelectionButton.class, nameArray[i]);
		}
		Iteration2PopInventory inventory = new Iteration2PopInventory(getVendingMachine(sbArray));
		assertEquals(inventory.getCost(sbArray[0]),200);
	}
	@Test
	public void getStockTest(){
		SelectionButton[] sbArray = new SelectionButton[6];
		for(int i = 0;  i < 6; i++){
			sbArray[i] = mockingContext.mock(SelectionButton.class, nameArray[i]);
		}
		Iteration2PopInventory inventory = new Iteration2PopInventory(getVendingMachine(sbArray));
		assertEquals(inventory.getStock(sbArray[0]),0);
	}
	@Test
	public void setCostTest(){
		SelectionButton[] sbArray = new SelectionButton[6];
		for(int i = 0;  i < 6; i++){
			sbArray[i] = mockingContext.mock(SelectionButton.class, nameArray[i]);
		}
		Iteration2PopInventory inventory = new Iteration2PopInventory(getVendingMachine(sbArray));
		inventory.setCost(sbArray[0], 250);
		assertEquals(inventory.getCost(sbArray[0]),250);
	}
	
	@Test
	public void setStockTest(){
		SelectionButton[] sbArray = new SelectionButton[6];
		for(int i = 0;  i < 6; i++){
			sbArray[i] = mockingContext.mock(SelectionButton.class, nameArray[i]);
		}
		Iteration2PopInventory inventory = new Iteration2PopInventory(getVendingMachine(sbArray));
		inventory.setStock(sbArray[0], 10);
		assertEquals(inventory.getStock(sbArray[0]),10);
	}
	
	@Test
	public void hasPopTest(){
		SelectionButton[] sbArray = new SelectionButton[6];
		for(int i = 0;  i < 6; i++){
			sbArray[i] = mockingContext.mock(SelectionButton.class, nameArray[i]);
		}
		Iteration2PopInventory inventory = new Iteration2PopInventory(getVendingMachine(sbArray));
		assertFalse(inventory.hasPop(sbArray[0]));
		inventory.setStock(sbArray[0], 10);
		assertTrue(inventory.hasPop(sbArray[0]));
	}
	
	@Test
	public void dispenseTest(){
		SelectionButton[] sbArray = new SelectionButton[6];
		for(int i = 0;  i < 6; i++){
			sbArray[i] = mockingContext.mock(SelectionButton.class, nameArray[i]);
		}
		Iteration2PopInventory inventory = new Iteration2PopInventory(getVendingMachine(sbArray));
		inventory.getPopInfo(sbArray[0]).setRackID(getPopCanRack(1,"name"));
		assertFalse(inventory.dispense(sbArray[0]));
		inventory.setStock(sbArray[0], 10);
		assertTrue(inventory.dispense(sbArray[0]));
	}

	private StandardPopVendingMachine getVendingMachine(
			final SelectionButton[] sbArray) {
		final StandardPopVendingMachine VMmock = mockingContext
				.mock(StandardPopVendingMachine.class);

		mockingContext.checking(new Expectations() {
			{
				allowing(VMmock).getSelectionButton(0);
				will(returnValue(sbArray[0]));
				allowing(VMmock).getSelectionButton(1);
				will(returnValue(sbArray[1]));
				allowing(VMmock).getSelectionButton(2);
				will(returnValue(sbArray[2]));
				allowing(VMmock).getSelectionButton(3);
				will(returnValue(sbArray[3]));
				allowing(VMmock).getSelectionButton(4);
				will(returnValue(sbArray[4]));
				allowing(VMmock).getSelectionButton(5);
				will(returnValue(sbArray[5]));
			}
		});
		return VMmock;
	}

	private PopCanRack getPopCanRack(final int arrayIndex, String name) {
		final PopCanRack rackMock = mockingContext.mock(PopCanRack.class, name);

		try {
			mockingContext.checking(new Expectations() {
				{
					allowing(rackMock).dispensePop();
					will(new Action() {

						@Override
						public void describeTo(Description description) {
							// TODO Auto-generated method stub

						}

						@Override
						public Object invoke(Invocation invocation)
								throws Throwable {
							numberOfPopsReleased[arrayIndex]++;
							return null;
						}

					});
				}
			});
		} catch (DisabledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CapacityExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rackMock;
	}

	private Mockery mockingContext = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	private int[] numberOfPopsReleased;
	private String nameArray[] = {"1","2","3","4","5","6"};

}
