package iteration2;

import iteration2.Iteration2PopInventory.PopInfo;
import com.vendingmachinesareus.*;

/**
 * The Interface PopInventory. Classes which implements PopInventory will be
 * able to manage the amount of pop in the machine, which rack it is stored in
 * and the respective cost all keyed off the button which corresponds to that pop
 */
public interface PopInventory {

	/**
	 * Gets the pop info.
	 *
	 * @param sb the selection button to be used as a key
	 * @return the popInfo which has methods for returning the cost, location and amount
	 */
	public PopInfo getPopInfo(SelectionButton sb);

	/**
	 * Gets the cost.
	 *
	 * @param sb the selection button to be used as a key
	 * @return the cost of the corresponding pop
	 */
	public int getCost(SelectionButton sb);

	/**
	 * Gets the stock.
	 *
	 * @param sb the selection button to be used as a key
	 * @return the amount of stock of the corresponding pop
	 */
	public int getStock(SelectionButton sb);

	/**
	 * Sets the cost.
	 *
	 * @param sb the selection button to be used as a key
	 * @param newCost the new cost for the corresponding pop
	 */
	public void setCost(SelectionButton sb, int newCost);

	/**
	 * Sets the stock.
	 *
	 * @param sb the selection button to be used as a key
	 * @param newStock the new amount of stock for the corresponding pop
	 */
	public void setStock(SelectionButton sb, int newStock);

	/**
	 * Checks for pop.
	 *
	 * @param sb the selection button to be used as a key
	 * @return true, if the amount of pop is equal to or greater than 1. false otherwise
	 */
	public boolean hasPop(SelectionButton sb);

	/**
	 * Dispense pop from the vending machine for given selection button.
	 *
	 * @param sb the selection button to be used as a key
	 * @return true, if successful, false otherwise
	 */
	public boolean dispense(SelectionButton sb);

}
