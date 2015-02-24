package iteration2;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.CardSlot;
import com.vendingmachinesareus.CardSlotListener;

/**
 * The Interface CardManager.
 * Classes which implement Card Manager will have the ability to tell the current state of the card slot
 * as well as to verify correct Pin numbers and charge the card
 */
public interface CardManager extends CardSlotListener{
	
	/* (non-Javadoc)
	 * @see com.vendingmachinesareus.AbstractHardwareListener#disabled(com.vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0);
	
	/* (non-Javadoc)
	 * @see com.vendingmachinesareus.AbstractHardwareListener#enabled(com.vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0);
	
	/* (non-Javadoc)
	 * @see com.vendingmachinesareus.CardSlotListener#cardEjected(com.vendingmachinesareus.CardSlot)
	 */
	@Override
	public void cardEjected(CardSlot arg0);
	
	/* (non-Javadoc)
	 * @see com.vendingmachinesareus.CardSlotListener#cardInserted(com.vendingmachinesareus.CardSlot)
	 */
	@Override
	public void cardInserted(CardSlot arg0);
	
	/**
	 * Charge.
	 *
	 * @param cost The amount to charge the card
	 * @param pin the PIN number
	 * @return true, if successfully charged, false otherwise
	 */
	public boolean charge(int cost, String pin);
	
	/**
	 * Checks for card.
	 *
	 * @return true, if inserted false otherwise
	 */
	public boolean hasCard();
	
	/**
	 * Verify.
	 *
	 * @param PIN the PIN number
	 * @return true, if it is the correct PIN number, false otherwise
	 */
	public boolean verify(String PIN) ;
}
