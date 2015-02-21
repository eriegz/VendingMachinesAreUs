package iteration2.testClass;

import static org.junit.Assert.*;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.*;

import GUI.VendingMachineGUI;

import com.vendingmachinesareus.Display;

import iteration2.DisplayManager;
import iteration2.Iteration2DisplayManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;

// TODO: Auto-generated Javadoc
/**
 * The Class TestDisplayManager.
 */
public class TestDisplayManager {

	// Declare it here for convience and has to be declared because its final
	// (this is just my preference)
	/** The out content to test with. */
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	/** The display manager. */
	private final DisplayManager displayManager = new Iteration2DisplayManager();
	
	/** The display. */
	private Display display;

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		// Dont need an actual display need a Mock
		// display = new Display();

		// Use Vending machine GUI default constructor to set out to System.out
		// then set System.out to be outContent
		System.setOut(new PrintStream(outContent));
		new VendingMachineGUI();
	}

	/**
	 * Teardown.
	 */
	@After
	public void teardown() {
	}

	/**
	 * Testmessage change.
	 */
	@Test
	// Testing whenever a change occurs in the display (i.e a new message is
	// displayed)
	public void testmessageChange() {
		display = makeDisplay("$1.00");
		displayManager.messageChange(display, "$0.00", "$1.00");
		assertEquals("", outContent.toString());
		try {
			Thread.sleep(5000);
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		//Shouldn't have changed
		assertEquals("", outContent.toString());
	}

	/**
	 * Test message change pop price.
	 */
	@Test
	// Testing the display of price of Pop notice
	public void testMessageChangePopPrice() {
		display = makeDisplay("|");
		displayManager.messageChange(display, "$0.00",
				"Notice: Price of Pop is");
		assertEquals("", outContent.toString());
		final Timer timer = new Timer();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals("|", outContent.toString());
	}

	// This function will mock a display that will only change message once
	/**
	 * Make display.
	 *
	 * @param message the message
	 * @return the display
	 */
	private Display makeDisplay(final String message){
		Mockery mockingContext = new JUnit4Mockery(){{
			setImposteriser(ClassImposteriser.INSTANCE);
		}};
		final Display ret = mockingContext.mock(Display.class);
		mockingContext.checking(new Expectations(){{
			oneOf(ret).display(message);
			will(new Action(){

				@Override
				public void describeTo(Description description) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public Object invoke(Invocation invocation) throws Throwable {
					VendingMachineGUI.out.print(message);
					return null;
				}
				
			});
		}});
		return ret;
	}
}
