package iteration2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.vendingmachinesareus.CapacityExceededException;
import com.vendingmachinesareus.CoinRack;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.EmptyException;

public class ChangeMaker {
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
}
