package iteration2;

import java.util.HashMap;
import java.util.Map;

import GUI.VendingMachineGUI;

import com.vendingmachinesareus.AbstractHardware;
import com.vendingmachinesareus.AbstractHardwareListener;
import com.vendingmachinesareus.CapacityExceededException;
import com.vendingmachinesareus.DisabledException;
import com.vendingmachinesareus.EmptyException;
import com.vendingmachinesareus.PopCan;
import com.vendingmachinesareus.PopCanRack;
import com.vendingmachinesareus.PopCanRackListener;
import com.vendingmachinesareus.SelectionButton;
import com.vendingmachinesareus.StandardPopVendingMachine;

public class Iteration2PopInventory implements PopInventory {
	//inner class to store rack pop information
		public class PopInfo implements PopCanRackListener{
			private int price;
			private PopCanRack rackID;
			private int stockAmt;
			
			public PopInfo(){
				price = 200;
				rackID = null;
				stockAmt = 0;
			}
			
			public PopInfo(int priceInit, PopCanRack rackInit, int stockInit){
				price = priceInit;
				rackID = rackInit;
				stockAmt = stockInit;
			}
			
			public int getPrice(){
				return price;
			}
			public PopCanRack getRackID(){
				return rackID;
			}
			public int getStockAmt(){
				return stockAmt;
			}
			public void setPrice(int x){
				price = x;
			}
			public void setRackID(PopCanRack x){
				rackID = x;
			}
			public void setStockAmt(int x){
				stockAmt = x;
			}
			
			@Override
			public boolean equals(Object other){
				if (other == null) return false;
				if (other == this) return true;
				if (!(other instanceof PopInfo))return false;
				PopInfo otherPopInfo = (PopInfo)other;
				if (this.getPrice() != otherPopInfo.getPrice()) return false;
				if (this.getRackID() != otherPopInfo.getRackID()) return false;
				if (this.getStockAmt() != otherPopInfo.getStockAmt()) return false;
				return true;
			}

			@Override
			public void disabled(AbstractHardware<AbstractHardwareListener> arg0) {
				//
				
			}

			@Override
			public void enabled(AbstractHardware<AbstractHardwareListener> arg0) {
				//
				
			}

			@Override
			public void popAdded(PopCanRack arg0, PopCan arg1) {
				stockAmt++;
				VendingMachineGUI.out.print("Notice: Pop Added");
			}

			@Override
			public void popEmpty(PopCanRack arg0) {
				stockAmt = 0;
				
			}

			@Override
			public void popFull(PopCanRack arg0) {
				//
				
			}

			@Override
			public void popRemoved(PopCanRack arg0, PopCan arg1) {
				stockAmt--;
				VendingMachineGUI.out.print("Notice: Pop Vending");
				
			}
		}//end inner class
		
		//map pop info to buttons
		private Map <SelectionButton, PopInfo> buttonToPopMap;
		
		
		public PopInfo getPopInfo(SelectionButton sb){
			return buttonToPopMap.get(sb);
		}
		public Iteration2PopInventory(StandardPopVendingMachine VM){
			buttonToPopMap = new HashMap<SelectionButton, PopInfo>();
			for(int i = 0; i < 6; i++){
				SelectionButton temp1 = VM.getSelectionButton(i);
				PopInfo temp2 = new PopInfo();
				buttonToPopMap.put(temp1, temp2);
			}
		}
		
		public int getCost(SelectionButton sb)
		{
			PopInfo temp = buttonToPopMap.get(sb);
			int ret = temp.getPrice();
			return ret;
		}
		public int getStock(SelectionButton sb)
		{
			PopInfo temp = buttonToPopMap.get(sb);
			int ret = temp.getStockAmt();
			return ret;
		}
		public void setCost(SelectionButton sb, int newCost)
		{
			PopInfo temp = buttonToPopMap.get(sb);
			temp.setPrice(newCost);
			buttonToPopMap.put(sb, temp);
			return;
		}
		
		public void setStock(SelectionButton sb, int newStock)
		{
			PopInfo temp = buttonToPopMap.get(sb);
			temp.setStockAmt(newStock);
			buttonToPopMap.put(sb, temp);
			return;
		}
		
		public boolean hasPop(SelectionButton sb)
		{
			PopInfo temp = buttonToPopMap.get(sb);
			int stock = temp.getStockAmt();
			if(stock > 0)
				return true;
			else
				return false;
		}
		
		public boolean dispense(SelectionButton sb)
		{
			PopInfo temp = buttonToPopMap.get(sb);
			PopCanRack rack = temp.getRackID();
			try {
				rack.dispensePop();
			} catch (DisabledException | EmptyException | CapacityExceededException e) {
				e.printStackTrace();
				return false;
			}
			return true;
			
		}
}
