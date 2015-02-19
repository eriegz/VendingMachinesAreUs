package iteration2;

import java.util.HashMap;
import java.util.Map;

import com.vendingmachinesareus.*;

public class PopInventory {
	//inner class to store rack pop information
	public class PopInfo{
		private int price;
		private PopCanRack rackID;
		private int stockAmt;
		
		public PopInfo(){
			price = 200;
			rackID = null;
			stockAmt = 0;
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
	}//end inner class
	
	//map pop info to buttons
	private Map <SelectionButton, PopInfo> buttonToPopMap;
	
	public PopInventory(StandardPopVendingMachine VM){
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	 

	
	}

