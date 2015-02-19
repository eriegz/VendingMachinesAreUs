package iteration2.testCases;

import static org.junit.Assert.*;
import iteration2.CoinInventory;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vendingmachinesareus.Card;
import com.vendingmachinesareus.CardSlot;
import com.vendingmachinesareus.EmptyException;

public class TestChangeMaker {

	@Before
	public void SetUp(){
		
	}
	
	@After
	public void tearDown(){
		
	}

	@Test
	public void testMakeChange()
	{
		final int[] coinValues = new int[]{5, 10, 25, 100, 200};
		Mockery mockingContext = new Mockery();
		final CoinInventory inventory = mockingContext.mock(CoinInventory.class);
		mockingContext.checking(new Expectations() {
			{
				one(inventory).getCoinValues();
				will(returnValue(coinValues));
			}}
		);
		
	}
	
}
