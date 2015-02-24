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

// TODO: Auto-generated Javadoc
/**
 * The Class Iteration2PopInventory.
 * Implements @PopInventory
 */
public class Iteration2PopInventory implements PopInventory {
	//inner class to store rack pop information
	/**
	 * The Class PopInfo.
	 * Used to store information regarding each type of pop
	 */
	public class PopInfo implements PopCanRackListener{
			
			/** The price. */
			private int price;
			
			/** The rack id (or more specifically the rack itself). */
			private PopCanRack rackID;
			
			/** The amount in stock at the moment. */
			private int stockAmt;
			
			/**
			 * Instantiates a new pop info.
			 * Default values, price = 200, rackID = null and stock amount = 0
			 */
			public PopInfo(){
				price = 200;
				rackID = null;
				stockAmt = 0;
			}
			
			/**
			 * Instantiates a new pop info.
			 *
			 * @param priceInit the initial price
			 * @param rackInit the rack we are storing information about
			 * @param stockInit the initial amount of stock
			 */
			public PopInfo(int priceInit, PopCanRack rackInit, int stockInit){
				price = priceInit;
				rackID = rackInit;
				stockAmt = stockInit;
			}
			
			/**
			 * Gets the price.
			 *
			 * @return the price
			 */
			public int getPrice(){
				return price;
			}
			
			/**
			 * Gets the rack id.
			 *
			 * @return the rack id
			 */
			public PopCanRack getRackID(){
				return rackID;
			}
			
			/**
			 * Gets the stock amt.
			 *
			 * @return the stock amt
			 */
			public int getStockAmt(){
				return stockAmt;
			}
			
			/**
			 * Sets the price.
			 *
			 * @param newPrice the new price
			 */
			public void setPrice(int newPrice){
				price = newPrice;
			}
			
			/**
			 * Sets the rack id.
			 *
			 * @param popCanRack the new rack to store information about
			 */
			public void setRackID(PopCanRack popCanRack){
				rackID = popCanRack;
			}
			
			/**
			 * Sets the stock amt.
			 *
			 * @param newAmount the new stock amount
			 */
			public void setStockAmt(int newAmount){
				stockAmt = newAmount;
			}
			
			/* (non-Javadoc)
			 * @see java.lang.Object#equals(java.lang.Object)
			 */
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

			/* (non-Javadoc)
			 * @see com.vendingmachinesareus.AbstractHardwareListener#disabled(com.vendingmachinesareus.AbstractHardware)
			 */
			@Override
			public void disabled(AbstractHardware<AbstractHardwareListener> arg0) {
				//
				
			}

			/* (non-Javadoc)
			 * @see com.vendingmachinesareus.AbstractHardwareListener#enabled(com.vendingmachinesareus.AbstractHardware)
			 */
			@Override
			public void enabled(AbstractHardware<AbstractHardwareListener> arg0) {
				//
				
			}

			/* (non-Javadoc)
			 * @see com.vendingmachinesareus.PopCanRackListener#popAdded(com.vendingmachinesareus.PopCanRack, com.vendingmachinesareus.PopCan)
			 */
			@Override
			public void popAdded(PopCanRack arg0, PopCan arg1) {
				stockAmt++;
				//VendingMachineGUI.out.print("Notice: Pop Added");
			}

			/* (non-Javadoc)
			 * @see com.vendingmachinesareus.PopCanRackListener#popEmpty(com.vendingmachinesareus.PopCanRack)
			 */
			@Override
			public void popEmpty(PopCanRack arg0) {
				stockAmt = 0;
				
			}

			/* (non-Javadoc)
			 * @see com.vendingmachinesareus.PopCanRackListener#popFull(com.vendingmachinesareus.PopCanRack)
			 */
			@Override
			public void popFull(PopCanRack arg0) {
				//
				
			}

			/* (non-Javadoc)
			 * @see com.vendingmachinesareus.PopCanRackListener#popRemoved(com.vendingmachinesareus.PopCanRack, com.vendingmachinesareus.PopCan)
			 */
			@Override
			public void popRemoved(PopCanRack arg0, PopCan arg1) {
				stockAmt--;
				VendingMachineGUI.out.print("Notice: Pop Vending");
				
			}
		}//end inner class
		
		//map pop info to buttons
		/** The button to pop map. */
		private Map <SelectionButton, PopInfo> buttonToPopMap;
		
		
		/* (non-Javadoc)
		 * @see iteration2.PopInventory#getPopInfo(com.vendingmachinesareus.SelectionButton)
		 */
		public PopInfo getPopInfo(SelectionButton sb){
			return buttonToPopMap.get(sb);
		}
		
		/**
		 * Instantiates a new iteration2 pop inventory.
		 *
		 * @param VM an instance of the standing pop vending machine 
		 */
		public Iteration2PopInventory(StandardPopVendingMachine VM){
			buttonToPopMap = new HashMap<SelectionButton, PopInfo>();
			for(int i = 0; i < 6; i++){
				SelectionButton temp1 = VM.getSelectionButton(i);
				PopInfo temp2 = new PopInfo();
				buttonToPopMap.put(temp1, temp2);
			}
		}
		
		/* (non-Javadoc)
		 * @see iteration2.PopInventory#getCost(com.vendingmachinesareus.SelectionButton)
		 */
		public int getCost(SelectionButton sb)
		{
			PopInfo temp = buttonToPopMap.get(sb);
			int ret = temp.getPrice();
			return ret;
		}
		
		/* (non-Javadoc)
		 * @see iteration2.PopInventory#getStock(com.vendingmachinesareus.SelectionButton)
		 */
		public int getStock(SelectionButton sb)
		{
			PopInfo temp = buttonToPopMap.get(sb);
			int ret = temp.getStockAmt();
			return ret;
		}
		
		/* (non-Javadoc)
		 * @see iteration2.PopInventory#setCost(com.vendingmachinesareus.SelectionButton, int)
		 */
		public void setCost(SelectionButton sb, int newCost)
		{
			PopInfo temp = buttonToPopMap.get(sb);
			temp.setPrice(newCost);
			buttonToPopMap.put(sb, temp);
			return;
		}
		
		/* (non-Javadoc)
		 * @see iteration2.PopInventory#setStock(com.vendingmachinesareus.SelectionButton, int)
		 */
		public void setStock(SelectionButton sb, int newStock)
		{
			PopInfo temp = buttonToPopMap.get(sb);
			temp.setStockAmt(newStock);
			buttonToPopMap.put(sb, temp);
			return;
		}
		
		/* (non-Javadoc)
		 * @see iteration2.PopInventory#hasPop(com.vendingmachinesareus.SelectionButton)
		 */
		public boolean hasPop(SelectionButton sb)
		{
			PopInfo temp = buttonToPopMap.get(sb);
			int stock = temp.getStockAmt();
			if(stock > 0)
				return true;
			else
				return false;
		}
		
		/* (non-Javadoc)
		 * @see iteration2.PopInventory#dispense(com.vendingmachinesareus.SelectionButton)
		 */
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
