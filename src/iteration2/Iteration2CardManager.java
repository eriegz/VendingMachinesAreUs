package iteration2;

import GUI.VendingMachineGUI;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.Card;
import com.vendingmachinesareus.CardSlot;
import com.vendingmachinesareus.EmptyException;

/**
 * The Class Iteration2CardManager.
 * Implements @CardManager
 */
public class Iteration2CardManager implements CardManager{
	
	/** The card in the machine. */
	private Card card;
	
	/**
	 * Instantiates a new iteration2 card manager.
	 */
	public Iteration2CardManager(){
		card = null;
	}
	
	/* (non-Javadoc)
	 * @see iteration2.CardManager#disabled(com.vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0) {
		
	}

	/* (non-Javadoc)
	 * @see iteration2.CardManager#enabled(com.vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0) {
		
	}

	/* (non-Javadoc)
	 * @see iteration2.CardManager#cardEjected(com.vendingmachinesareus.CardSlot)
	 */
	@Override
	public void cardEjected(CardSlot arg0) {
		card = null;
		VendingMachineGUI.out.print("Notice: Card Ejecting");
	}

	/* (non-Javadoc)
	 * @see iteration2.CardManager#cardInserted(com.vendingmachinesareus.CardSlot)
	 */
	@Override
	public void cardInserted(CardSlot arg0) {
		try {
			card = arg0.readCardData();
		} catch (EmptyException e) {
			//Impossible to reach here
		}
		VendingMachineGUI.out.print("Notice: Card Inserted");
	}
	
	/* (non-Javadoc)
	 * @see iteration2.CardManager#charge(int, java.lang.String)
	 */
	public boolean charge(int cost, String pin) {
		if(hasCard()){
			return card.requestFunds(cost, pin);
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see iteration2.CardManager#hasCard()
	 */
	public boolean hasCard() {
		if(card == null){
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see iteration2.CardManager#verify(java.lang.String)
	 */
	public boolean verify(String PIN) {
		if(hasCard()){
			return card.checkPin(PIN);
		}
		return false;
	}

}
