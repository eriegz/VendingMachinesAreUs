package iteration2;

import com.vendingmachinesareus.*;


/**
 * The Interface CoinInventory. A class which implments coinInventory will be
 * able to have all information needed to know the location and value of coins
 * in the receptacle and coinRacks Important functionality includes returning
 * the value of coins in the receptacle and the number of each coin denomination
 * in the coin racks. It also returns all coin denominations supported by this
 * inventory
 */
public interface CoinInventory extends CoinReceptacleListener, CoinRackListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vendingmachinesareus.AbstractHardwareListener#disabled(com.
	 * vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vendingmachinesareus.AbstractHardwareListener#enabled(com.
	 * vendingmachinesareus.AbstractHardware)
	 */
	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vendingmachinesareus.CoinRackListener#coinAdded(com.vendingmachinesareus
	 * .CoinRack, com.vendingmachinesareus.Coin)
	 */
	@Override
	public void coinAdded(CoinRack arg0, Coin arg1);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vendingmachinesareus.CoinRackListener#coinRemoved(com.
	 * vendingmachinesareus.CoinRack, com.vendingmachinesareus.Coin)
	 */
	@Override
	public void coinRemoved(CoinRack arg0, Coin arg1);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vendingmachinesareus.CoinRackListener#coinsEmpty(com.vendingmachinesareus
	 * .CoinRack)
	 */
	@Override
	public void coinsEmpty(CoinRack arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.vendingmachinesareus.CoinRackListener#coinsFull(com.vendingmachinesareus
	 * .CoinRack)
	 */
	@Override
	public void coinsFull(CoinRack arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vendingmachinesareus.CoinReceptacleListener#coinAdded(com.
	 * vendingmachinesareus.CoinReceptacle, com.vendingmachinesareus.Coin)
	 */
	@Override
	public void coinAdded(CoinReceptacle arg0, Coin arg1);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vendingmachinesareus.CoinReceptacleListener#coinsFull(com.
	 * vendingmachinesareus.CoinReceptacle)
	 */
	@Override
	public void coinsFull(CoinReceptacle arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vendingmachinesareus.CoinReceptacleListener#coinsRemoved(com.
	 * vendingmachinesareus.CoinReceptacle)
	 */
	@Override
	public void coinsRemoved(CoinReceptacle arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vendingmachinesareus.CoinReceptacleListener#disabled(com.
	 * vendingmachinesareus.CoinReceptacle)
	 */
	@Override
	public void disabled(CoinReceptacle arg0);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.vendingmachinesareus.CoinReceptacleListener#enabled(com.
	 * vendingmachinesareus.CoinReceptacle)
	 */
	@Override
	public void enabled(CoinReceptacle arg0);

	/**
	 * Gets the number of coins in the rack corresponding to that denomination.
	 *
	 * @param coinRackValueToReturn
	 *            the coin rack value to return
	 * @return the number of coins in rack
	 */
	public int getNumberOfCoinsInRack(int coinRackValueToReturn);

	/**
	 * Gets the amount of money in the receptacle.
	 *
	 * @return the amount of money in cents in the receptacle
	 */
	public int getReceptacleAmount();

	/**
	 * Gets the coin values.
	 *
	 * @return the coin values
	 */
	public int[] getCoinValues();
}
