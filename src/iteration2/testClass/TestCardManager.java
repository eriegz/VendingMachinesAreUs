package iteration2.testClass;

import static org.junit.Assert.*;
import iteration2.CardManager;
import iteration2.Iteration2CardManager;

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

/**
 * The Class TestCardManager.
 */
public class TestCardManager {

	/**
	 * Sets the up.
	 */
	@Before
	public void SetUp(){
		cardManager = new Iteration2CardManager();
		//Need this line so no null pointer exceptions happen
		new VendingMachineGUI();
	}
	
	/**
	 * Tear down.
	 */
	@After
	public void tearDown(){
		
	}
	
	
	
	/**
	 * Testcard inserted.
	 */
	@Test
	public void testcardInserted()
	{
		Mockery mockingContext = new Mockery(){{
		setImposteriser(ClassImposteriser.INSTANCE);}};
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
			e.printStackTrace();
		}
			cardManager.cardInserted(cardSlot);
			assertTrue(cardManager.hasCard());
	}
	
	/**
	 * Test card ejected.
	 */
	@Test
	public void testCardEjected()
	{
		Mockery mockingContext = new Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);}};
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
			e.printStackTrace();
		}
			cardManager.cardInserted(cardSlot);
			cardManager.cardEjected(cardSlot);
			assertFalse(cardManager.hasCard());
	}
	
	/**
	 * Test has card.
	 */
	@Test
	public void testHasCard()
	{

		Mockery mockingContext = new Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);}};
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
			e.printStackTrace();
		}
			assertFalse(cardManager.hasCard());
			cardManager.cardInserted(cardSlot);
			assertTrue(cardManager.hasCard());
			cardManager.cardEjected(cardSlot);
			assertFalse(cardManager.hasCard());
	}
	
	/**
	 * Test charge.
	 */
	@Test
	public void testCharge()
	{

		Mockery mockingContext = new Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);}};
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
				oneOf(card).requestFunds(10, "4321");
				will(returnValue(false));
			}}
		);
		final CardSlot cardSlot = mockingContext.mock(CardSlot.class);
		try {
			mockingContext.checking(new Expectations() {
				{
					allowing(cardSlot).readCardData();
					will(returnValue(card));
				}}
			);
		} catch (EmptyException e) {
			e.printStackTrace();
		}
		cardManager.cardInserted(cardSlot);
		assertTrue(cardManager.charge(10, "1234"));
		cardManager.cardInserted(cardSlot);
		assertFalse(cardManager.charge(10, "4321"));
		cardManager.cardEjected(cardSlot);
		assertFalse(cardManager.charge(10, "4321"));
	}
	
	/**
	 * Test verify.
	 */
	@Test
	public void testVerify()
	{

		Mockery mockingContext = new Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);}};
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
					allowing(cardSlot).readCardData();
					will(returnValue(card));
				}}
			);
		} catch (EmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cardManager.cardInserted(cardSlot);
		assertTrue(cardManager.verify("1234"));
		cardManager.cardInserted(cardSlot);
		assertFalse(cardManager.verify("4321"));
		cardManager.cardEjected(cardSlot);
		assertFalse(cardManager.verify("4321"));
	}
	
	/** The card manager. */
	private CardManager cardManager;
}
