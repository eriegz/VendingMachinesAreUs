package iteration2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.vendingmachinesareus.CapacityExceededException;
import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.EmptyException;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangeMaker.
 * Contains useful static procedural methods for making change and changing an integer number representing cents to a properly formatted string
 */
public class ChangeMaker {
	
	/**
	 * Makes change given paramets.
	 *
	 * @param amountOfChange the amount of change
	 * @param coinRackMap a mapping of the cent value to CoinRack which holds the coins of that denomination
	 * @param inventory Coin Inventory class for the machine
	 * @return true, if change was successfully made, false otherwise
	 */
	static public boolean makeChange(int amountOfChange, Map<Integer, CoinRack> coinRackMap, CoinInventory inventory){
		//Sort coin values
		int coinValues[] = inventory.getCoinValues();
		ArrayList <Integer> sortedSet = new ArrayList <Integer>();
		for(int i = 0; i < coinValues.length; i++){

			sortedSet.add(coinValues[i]);
		}
		Collections.sort(sortedSet);

		// Coin Racks are sorted by value so should line up with the sortedSet
		int numberNeeded[] = new int[sortedSet.size()];
		for(int i = sortedSet.size()-1; i >=0; i--){
			if(amountOfChange/sortedSet.get(i)< inventory.getNumberOfCoinsInRack(sortedSet.get(i))){
				numberNeeded[i] = amountOfChange/sortedSet.get(i).intValue();
				amountOfChange= amountOfChange%sortedSet.get(i);
			}else{
				numberNeeded[i] = inventory.getNumberOfCoinsInRack(sortedSet.get(i));
				amountOfChange = amountOfChange - inventory.getNumberOfCoinsInRack(sortedSet.get(i))*sortedSet.get(i);
			}
		}

		// If amountOfChange is not equal to zero at this point change cannot be
		// given
		if (amountOfChange != 0) {
			return false;
		}

		// Release change
		for (int i = sortedSet.size() - 1; i >= 0; i--) {
			for (int numberToRelease = 0; numberToRelease < numberNeeded[i]; numberToRelease++) {
				try {
					coinRackMap.get(sortedSet.get(i)).releaseCoin();
				} catch (DisabledException e) {
					// No Requirement on how to handle this
				} catch (EmptyException e) {
					// Should never get here but if we do then notifyExactChange
					// even though we dispensed some coins potentiall?
					return false;
				} catch (CapacityExceededException e) {
					return false;
				}
			}
		}
		return true;

	}
	
	/**
	 * Transforms an integer number representing cents to a string formatted as "$*.XX" where * is the cost in dollars and XX is the cost in cents
	 *
	 * @param cost The amount of cents to be represented
	 * @return String in the form of $*.XX (example $430.42)
	 */
	public static String toMoney(int cost){
		String finalString = new String("$");
		int dollars = cost / 100;
		int cents = cost % 100;
		if (dollars < 1) {
			finalString += "0.";
		} else {
			finalString += (dollars + ".");
		}
		if (cents < 10) {
			finalString += ("0" + cents);
		} else {
			finalString += cents;
		}
		return finalString;
	}

}
