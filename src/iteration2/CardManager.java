package iteration2;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.Card;
import com.vendingmachinesareus.CardSlot;
import com.vendingmachinesareus.CardSlotListener;
import com.vendingmachinesareus.EmptyException;

public class CardManager implements CardSlotListener{

	private Card card;
	public CardManager(){
		card = null;
	}
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0) {
		
	}

	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0) {
		
	}

	@Override
	public void cardEjected(CardSlot arg0) {
		card = null;
	}

	@Override
	public void cardInserted(CardSlot arg0) {
		try {
			card = arg0.readCardData();
		} catch (EmptyException e) {
			//Impossible to reach here
		}
	}
	public boolean charge(int cost, String pin) {
		if(hasCard()){
			return card.requestFunds(cost, pin);
		}
		return false;
	}
	public boolean hasCard() {
		if(card == null){
			return false;
		}
		return true;
	}
	public boolean verify(String PIN) {
		if(hasCard()){
			return card.checkPin(PIN);
		}
		return false;
	}

	

}
