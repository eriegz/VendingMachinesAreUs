package iteration2.testClass;

import static org.junit.Assert.*;
import iteration2.CardManager;
import iteration2.Iteration2CardManger;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GUI.VendingMachineGUI;

import com.vendingmachinesareus.Card;
import com.vendingmachinesareus.CardSlot;
import com.vendingmachinesareus.EmptyException;

public class TestCardManager {

	@Before
	public void SetUp(){
		cardManager = new Iteration2CardManger();
		new VendingMachineGUI();
	}
	
	@After
	public void tearDown(){
		
	}
	
	
	
	@Test
	public void testCardInserted()
	{
		Mockery mockingContext = new Mockery(){{
			 setImposteriser(ClassImposteriser.INSTANCE);
		}};
		final Card card = mockingContext.mock(Card.class);
		mockingContext.checking(new Expectations() {
			{
				oneOf(card).getType();
				will(returnValue(0));
				oneOf(card).getNumber();
				will(returnValue("123456789"));
				oneOf(card).getName();
				will(returnValue("John Smith"));
			}}
		);
		final CardSlot cardSlot = mockingContext.mock(CardSlot.class);
		try {
			mockingContext.checking(new Expectations() {
				{
					oneOf(cardSlot).readCardData();
					will(returnValue(card));
				}}
			);
		} catch (EmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			cardManager.cardInserted(cardSlot);
			assertTrue(cardManager.hasCard());
	}
	
	@Test
	public void testCardEjected()
	{
		Mockery mockingContext = new Mockery(){{
			 setImposteriser(ClassImposteriser.INSTANCE);
		}};;
		final Card card = mockingContext.mock(Card.class);
		mockingContext.checking(new Expectations() {
			{
				oneOf(card).getType();
				will(returnValue(0));
				oneOf(card).getNumber();
				will(returnValue("123456789"));
				oneOf(card).getName();
				will(returnValue("John Smith"));
			}}
		);
		final CardSlot cardSlot = mockingContext.mock(CardSlot.class);
		try {
			mockingContext.checking(new Expectations() {
				{
					oneOf(cardSlot).readCardData();
					will(returnValue(card));
				}}
			);
		} catch (EmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			cardManager.cardInserted(cardSlot);
			cardManager.cardEjected(cardSlot);
			assertFalse(cardManager.hasCard());
	}
	
	@Test
	public void testHasCard()
	{

		Mockery mockingContext = new Mockery(){{
			 setImposteriser(ClassImposteriser.INSTANCE);
		}};;
		final Card card = mockingContext.mock(Card.class);
		mockingContext.checking(new Expectations() {
			{
				oneOf(card).getType();
				will(returnValue(0));
				oneOf(card).getNumber();
				will(returnValue("123456789"));
				oneOf(card).getName();
				will(returnValue("John Smith"));
			}}
		);
		final CardSlot cardSlot = mockingContext.mock(CardSlot.class);
		try {
			mockingContext.checking(new Expectations() {
				{
					oneOf(cardSlot).readCardData();
					will(returnValue(card));
				}}
			);
		} catch (EmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			assertFalse(cardManager.hasCard());
			cardManager.cardInserted(cardSlot);
			assertTrue(cardManager.hasCard());
			cardManager.cardEjected(cardSlot);
			assertFalse(cardManager.hasCard());
	}
	
	@Test
	public void testCharge()
	{

		Mockery mockingContext = new Mockery(){{
			 setImposteriser(ClassImposteriser.INSTANCE);
		}};;
		final Card card = mockingContext.mock(Card.class);
		mockingContext.checking(new Expectations() {
			{
				oneOf(card).getType();
				will(returnValue(0));
				oneOf(card).getNumber();
				will(returnValue("123456789"));
				oneOf(card).getName();
				will(returnValue("John Smith"));
				oneOf(card).checkPin("1234");
				will(returnValue(true));
				oneOf(card).checkPin("4321");
				will(returnValue(false));
				oneOf(card).requestFunds(10, "1234");
				will(returnValue(true));
				oneOf(card).requestFunds(10,"4321");
				will(returnValue(false));
			}}
		);
		final CardSlot cardSlot = mockingContext.mock(CardSlot.class);
		try {
			mockingContext.checking(new Expectations() {
				{
					oneOf(cardSlot).readCardData();
					will(returnValue(card));
				}}
			);
		} catch (EmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cardManager.cardInserted(cardSlot);
		assertEquals(cardManager.charge(10, "1234"), true);
		assertEquals(cardManager.charge(10, "4321"), false);
	}
	
	@Test
	public void testVerify()
	{

		Mockery mockingContext = new Mockery(){{
			 setImposteriser(ClassImposteriser.INSTANCE);
		}};;
		final Card card = mockingContext.mock(Card.class);
		mockingContext.checking(new Expectations() {
			{
				
				oneOf(card).checkPin("1234");
				will(returnValue(true));
				oneOf(card).checkPin("4321");
				will(returnValue(false));
			}}
		);
		final CardSlot cardSlot = mockingContext.mock(CardSlot.class);
		try {
			mockingContext.checking(new Expectations() {
				{
					oneOf(cardSlot).readCardData();
					will(returnValue(card));
				}}
			);
		} catch (EmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cardManager.cardInserted(cardSlot);
		assertEquals(cardManager.verify("1234"), true);
		assertEquals(cardManager.verify("4321"), false);
	}
	
	private CardManager cardManager;
}
