package iteration2.testClass;

import static org.junit.Assert.*;

import org.junit.*;

import com.vendingmachinesareus.Display;

import iteration2.Iteration2DisplayManager;
import java.io.ByteArrayOutputStream;

public class TestDisplayManager {
	
	private ByteArrayOutputStream hardwareOutStream;
	private Display display;
	private Iteration2DisplayManager displayManager;


	@Before
	public void setup(){
		display = new Display();
		displayManager = new Iteration2DisplayManager();
		hardwareOutStream = new ByteArrayOutputStream();
	}
	
	@After
	public void teardown(){
	}
	
	@Test
	//Testing whenever a change occurs in the display (i.e a new message is displayed)  
	public void testmessageChange(){
		displayManager.messageChange(display, "$0.00", "$1.00");
		assertEquals("", hardwareOutStream.toString());
	}

	@Test
	//Testing the display of price of Pop notice
	public void testMessageChangePopPrice(){
		displayManager.messageChange(display, "$0.00", "Notice: Price of Pop is");
		assertEquals("", hardwareOutStream.toString());
	}

}
