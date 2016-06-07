package Client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MenuWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4506602204785588655L;
	private static final String windowName = "Proconsul - Liber qui praesunt - Menu - Client v0.1";
	private static final int initialWidth = 580;
	private static final int initialHeight = 660;

	public MenuWindow() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(windowName);
		setSize(initialWidth, initialHeight);
		setMinimumSize(new Dimension(600, 500));
		setLayout(new BorderLayout());
		
		
		// center frame on screen
		setLocationRelativeTo(null);
		// show frame
		setVisible(true);
	}
}
