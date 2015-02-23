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

import com.vendingmachinesareus.Coin;
import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.CoinReceptacle;

public class TestCoinInventory {


	@Before
	public void SetUp(){
		coinValues = new int[]{5, 10, 25, 100, 200};
		coinRackValue = 0;
		receptacleCoinValue = 0;
		coinInventory = new Iteration2CoinInventory(coinValues);
	}
	
	@After
	public void tearDown(){
		
	}
	
	
	
	@Test
	public void testCoinAdded1()
	{
		
		Mockery mockingContext = new Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);}};
		final Coin coin = mockingContext.mock(Coin.class);
		mockingContext.checking(new Expectations() {
			{
				one(coin).getValue();
				will(returnValue(5));
			}}
		);
		final Map <Integer, Integer> valueToRackMap = mockingContext.mock(Map.class);
		mockingContext.checking(new Expectations() {
			{
				one(valueToRackMap).get(5);
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
	
	@Test
	public void testCoinRemoved1()
	{
		
		Mockery mockingContext = new Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);}};
		final Coin coin = mockingContext.mock(Coin.class);
		mockingContext.checking(new Expectations() {
			{
				one(coin).getValue();
				will(returnValue(5));
			}}
		);
		final Map <Integer, Integer> valueToRackMap = mockingContext.mock(Map.class);
		mockingContext.checking(new Expectations() {
			{
				one(valueToRackMap).get(5);
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
	
	@Test
	public void testCoinAdded2()
	{
		
		Mockery mockingContext = new Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);}};
		final Coin coin = mockingContext.mock(Coin.class);
		mockingContext.checking(new Expectations() {
			{
				one(coin).getValue();
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
	
	@Test
	public void testGetCoinValues()
	{
		assertEquals(coinInventory.getCoinValues(), coinValues);
	}
	
	private CoinInventory coinInventory;
	private int coinRackValue;
	private int receptacleCoinValue;
	private int [] coinValues;
}
