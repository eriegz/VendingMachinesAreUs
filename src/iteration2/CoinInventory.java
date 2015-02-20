package iteration2;

import com.vendingmachinesareus.*;
public interface CoinInventory extends CoinReceptacleListener, CoinRackListener {
	@Override
	public void disabled(AbstractHardware<AbstractHardwareListener> arg0);

	@Override
	public void enabled(AbstractHardware<AbstractHardwareListener> arg0);

	@Override
	public void coinAdded(CoinRack arg0, Coin arg1);

	@Override
	public void coinRemoved(CoinRack arg0, Coin arg1);

	@Override
	public void coinsEmpty(CoinRack arg0);

	@Override
	public void coinsFull(CoinRack arg0);

	@Override
	public void coinAdded(CoinReceptacle arg0, Coin arg1);

	@Override
	public void coinsFull(CoinReceptacle arg0);

	@Override
	public void coinsRemoved(CoinReceptacle arg0);

	@Override
	public void disabled(CoinReceptacle arg0);

	@Override
	public void enabled(CoinReceptacle arg0);
	
	public int getNumberOfCoinsInRack(int coinRackValueToReturn);
	public int getReceptacleAmount();
	public int[] getCoinValues();
}
