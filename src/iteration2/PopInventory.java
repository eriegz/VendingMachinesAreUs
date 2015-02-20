package iteration2;

import iteration2.Iteration2PopInventory.PopInfo;
import GUI.VendingMachineGUI;

import com.vendingmachinesareus.*;

public interface PopInventory{
	public PopInfo getPopInfo(SelectionButton sb);
	public int getCost(SelectionButton sb);
	public int getStock(SelectionButton sb);
	public void setCost(SelectionButton sb, int newCost);
	public void setStock(SelectionButton sb, int newStock);
	public boolean hasPop(SelectionButton sb);
	public boolean dispense(SelectionButton sb);
	 

}

