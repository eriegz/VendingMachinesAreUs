package iteration2.testClass;

import static org.junit.Assert.*;

import java.util.Map;

import iteration2.CoinInventory;
import iteration2.Iteration2CoinInventory;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GUI.VendingMachineGUI;

import com.vendingmachinesareus.Coin;
import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.CoinReceptacle;

/**
 * The Class TestCoinInventory.
 */
public class TestCoinInventory {


	/**
	 * Sets the up.
	 */
	@Before
	public void SetUp(){
		coinValues = new int[]{5, 10, 25, 100, 200};
		coinRackValue = 0;
		receptacleCoinValue = 0;
		coinInventory = new Iteration2CoinInventory(coinValues);
		new VendingMachineGUI();
	}
	
	/**
	 * Tear down.
	 */
	@After
	public void tearDown(){
		
	}
	
	
	
	/**
	 * Test coin added1.
	 */
	@Test
	public void testCoinAdded1()
	{
		
		Mockery mockingContext = new Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);}};
		final Coin coin = mockingContext.mock(Coin.class);
		mockingContext.checking(new Expectations() {
			{
				oneOf(coin).getValue();
				will(returnValue(5));
			}}
		);
		@SuppressWarnings("unchecked")
		final Map <Integer, Integer> valueToRackMap = (Map<Integer,Integer>)mockingContext.mock(Map.class);
		mockingContext.checking(new Expectations() {
			{
				oneOf(valueToRackMap).get(5);
				will(returnValue(0));
			}}
		);
		final CoinRack coinRack = mockingContext.mock(CoinRack.class);
		mockingContext.checking(new Expectations() {
			{
				
			}}
		);
		coinRackValue = coinInventory.getNumberOfCoinsInRack(5);
		coinInventory.coinAdded(coinRack, coin);
		assertEquals(coinInventory.getNumberOfCoinsInRack(5), coinRackValue + 1);
	}
	
	/**
	 * Test coin removed1.
	 */
	@Test
	public void testCoinRemoved1()
	{
		
		Mockery mockingContext = new Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);}};
		final Coin coin = mockingContext.mock(Coin.class);
		mockingContext.checking(new Expectations() {
			{
				oneOf(coin).getValue();
				will(returnValue(5));
			}}
		);
		@SuppressWarnings("unchecked")
		final Map <Integer, Integer> valueToRackMap = (Map<Integer,Integer>)mockingContext.mock(Map.class);
		mockingContext.checking(new Expectations() {
			{
				oneOf(valueToRackMap).get(5);
				will(returnValue(0));
			}}
		);
		final CoinRack coinRack = mockingContext.mock(CoinRack.class);
		mockingContext.checking(new Expectations() {
			{
				
			}}
		);
		coinRackValue = coinInventory.getNumberOfCoinsInRack(5);
		coinInventory.coinRemoved(coinRack, coin);
		assertEquals(coinInventory.getNumberOfCoinsInRack(5), coinRackValue - 1);
	}
	
	/**
	 * Test coin added2.
	 */
	@Test
	public void testCoinAdded2()
	{
		
		Mockery mockingContext = new Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);}};
		final Coin coin = mockingContext.mock(Coin.class);
		mockingContext.checking(new Expectations() {
			{
				oneOf(coin).getValue();
				will(returnValue(5));
			}}
		);
		final CoinReceptacle coinReceptacle = mockingContext.mock(CoinReceptacle.class);
		mockingContext.checking(new Expectations() {
			{
				
			}}
		);
		receptacleCoinValue = coinInventory.getReceptacleAmount();
		coinInventory.coinAdded(coinReceptacle, coin);
		assertEquals(receptacleCoinValue + 5, coinInventory.getReceptacleAmount());
	}
	
	/**
	 * Test coin removed2.
	 */
	@Test
	public void testCoinRemoved2()
	{
		Mockery mockingContext = new Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);}};
		final CoinReceptacle coinReceptacle = mockingContext.mock(CoinReceptacle.class);
		mockingContext.checking(new Expectations() {
			{
				
			}}
		);
		coinInventory.coinsRemoved(coinReceptacle);
		assertEquals(coinInventory.getReceptacleAmount(), 0);
	}
	
	/**
	 * Test get coin values.
	 */
	@Test
	public void testGetCoinValues()
	{
		assertEquals(coinInventory.getCoinValues(), coinValues);
	}
	
	/** The coin inventory. */
	private CoinInventory coinInventory;
	
	/** The coin rack value. */
	private int coinRackValue;
	
	/** The receptacle coin value. */
	private int receptacleCoinValue;
	
	/** The coin values. */
	private int [] coinValues;
}
