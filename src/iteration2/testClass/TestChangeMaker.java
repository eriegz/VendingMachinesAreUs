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
import org.junit.runner.RunWith;

import com.vendingmachinesareus.CapacityExceededException;
import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.EmptyException;


public class TestChangeMaker {
	@Before
	public void setUp(){
		mockeryContext = new JUnit4Mockery(){{
				setImposteriser(ClassImposteriser.INSTANCE);	
			}};
	}
	@After
	public void tearDown(){
		
	}
	public void testMakeChangeWithEnoughChange(){
		CoinRack rack = makeCoinRack(0);
		try {
			rack.releaseCoin();
		} catch (CapacityExceededException | EmptyException | DisabledException e) {
			e.printStackTrace();
		}
		assertEquals(numberOfCoinsReleased[0], 1);
	}
	private CoinRack makeCoinRack(final int arrayIndex){
		final CoinRack ret = mockeryContext.mock(CoinRack.class);
		try {
			mockeryContext.checking(new Expectations(){{
				allowing(ret).releaseCoin();
				will(new Action(){

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
			}});
		} catch (CapacityExceededException | EmptyException | DisabledException e) {
			e.printStackTrace();
		}
		return ret;
	}
	private Mockery mockeryContext;
	private int[] numberOfCoinsReleased;
}
