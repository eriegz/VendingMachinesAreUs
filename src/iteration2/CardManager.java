package iteration2;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.CardSlot;
import com.vendingmachinesareus.CardSlotListener;

public interface CardManager extends CardSlotListener{
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0);

	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0);

	@Override
	public void cardEjected(CardSlot arg0);
	@Override
	public void cardInserted(CardSlot arg0);
	public boolean charge(int cost, String pin);
	public boolean hasCard();
	public boolean verify(String PIN) ;
}
