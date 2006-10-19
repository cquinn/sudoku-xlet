package org.quinn.sudoku.xlet;

import java.awt.Container;
import java.awt.EventQueue;

import javax.microedition.xlet.UnavailableContainerException;
import javax.microedition.xlet.Xlet;
import javax.microedition.xlet.XletContext;
import javax.microedition.xlet.XletStateChangeException;
import javax.swing.JInternalFrame;

public abstract class SimpleXlet implements Xlet {

	protected XletContext context;

	protected Container rootContainer;

	protected JInternalFrame screen;

	/**
	 * Construct the Xlet. Here we can do some initial setup - but not too much, most
	 * setup should be done in the initXlet() body. Nothing graphical should be
	 * done until much later.
	 */
	public SimpleXlet() {
		System.out.println("Xlet constructor");
	}

	/**
	 * Called once during the Xlet life cyle. Here we can do some more setup,
	 * but like the constructor, not much is usually done here.
	 * 
	 * @throws XletStateChangeException
	 */
	public void initXlet(final XletContext xletContext)
			throws XletStateChangeException {
		System.out.println("Xlet.initXlet()");

		// we will delay other setup until later.
		context = xletContext;
		if (rootContainer == null) {
			try {
				// This call to getContainer() tells the OS we want to be a
				// graphical app.
				rootContainer = context.getContainer();

			} catch (UnavailableContainerException e) {
				System.out.println("Could not get our container!");
				// If we can't get the root container,
				// abort the initialization
				throw new XletStateChangeException(
						"Start aborted -- no container: " + e.getMessage());
			}
		}
	}

	/**
	 * Called whenever the Xlet is made active. It is here that the bulk of
	 * creating the application should be done. Things to keep in mind: + This
	 * method should excecute reasonably quickly and then return + This method
	 * can be called multiple times (with a pauseXlet() between them) + Swing
	 * operations need to been done on a diffent thread.
	 * 
	 * @throws XletStateChangeException
	 */
	public void startXlet() throws XletStateChangeException {
		System.out.println("Xlet.startXlet()");

		// notice that we are assuming we will be called multiple times, and are
		// using
		// a variable to see if we have anything to do.
		if (screen == null) {
			// Note: Swing thread constraints still apply in an Xlet... most
			// operations
			// need to be on the event thread, and this invokeLater does that.
			try {
				// using invokeAndWait to avoid writing synchornization code.
				// invokeLater would work just as well in most cases.
				EventQueue.invokeAndWait(new Runnable() {
					public void run() {
						screen = newScreen();
						screen.setVisible(true);
						rootContainer.add(screen);
						// This is needed - or nothing will be displayed.
						rootContainer.setVisible(true);
					}
				});
			} catch (Exception e) {
				System.out.println("Exception in invokeAndWait()");
				e.printStackTrace();
				exit();
			}
		}
	}

	/**
	 * Called when the Xlet is no longer visible/active. A well behaved Xlet
	 * will free up any resources that can be recreated easily during the call
	 * to startXlet(). Given that an Xlet is intended to run in a constrained
	 * environment, it may make sense to free up large images, complicated
	 * screens and so forth.
	 */
	public void pauseXlet() {
		System.out.println("Xlet.pauseXlet()");

		// This is pure overkill for this application, but is done to
		// demonstrate the point.
		// We are freeing up our only resources (the screen), and we will
		// rebuild it when
		// we get started again. If you took out this block - the application
		// should still
		// run perfectly, and the screen should only be created once.
		try {
			// using invokeAndWait to avoid writing synchornization code.
			// invokeLater would work just as well in most cases.
			EventQueue.invokeAndWait(new Runnable() {
				public void run() {
					rootContainer.remove(screen);
					screen = null;
				}
			});
		} catch (Exception e) {
			System.out.println("Ouch - exception in invokeAndWait()");
			e.printStackTrace();
			exit();
		}
	}

	/**
	 * Called when the Xlet is being terminated by the system. This gives the
	 * Xlet a chance to quickly save state, and in more advanced uses - ask for
	 * a bit more time.
	 * 
	 * @throws XletStateChangeException
	 */
	public void destroyXlet(final boolean unconditional)
			throws XletStateChangeException {
		System.out.println("Xlet.destroylet() - goodbye");
	}

	/**
	 * a utility to close the Xlet. Much more polite than System.exit(0); Note
	 * that our destroyXlet() will be called by the system short after this
	 * returns.
	 */
	protected void exit() {
		rootContainer.setVisible(false);
		context.notifyDestroyed();
	}

	/**
	 * Override this method to create the main internal frame for the app.
	 */
	protected abstract JInternalFrame newScreen();
}
