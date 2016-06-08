package Client.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MenuWindow extends JFrame implements Observer, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4506602204785588655L;
	private static final String windowName = "Proconsul - Liber qui praesunt - Menu - Client v0.1";
	private static final int initialWidth = 580;
	private static final int initialHeight = 660;
	
	private Logic logic;	// MenuWindow Logic nicht Clientgamelogic
	
	private static final String WINDOWNAME = "Proconsul";
	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;

	private StartView joinCreateView;
	private JoinView joinView;
	private CreateView createView;

	public MenuWindow(Logic logic) {
		this.logic = logic;
		logic.registerObserver(this); // REGISTER OBSERVER

		setBounds(200, 200, WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new CardLayout(0, 0));
		setResizable(false);
		setTitle(WINDOWNAME);
		setLocationRelativeTo(null);

		joinCreateView = new StartView(logic);
		joinView = new JoinView(logic);
		createView = new CreateView(logic);

		this.getContentPane().add(joinCreateView);
		this.getContentPane().add(joinView);
		this.getContentPane().add(createView);

		update();
		this.setVisible(true);
	}
	
	@Override
	public void update() {

		System.out.println("update"); // UPDATE OBSERVER

		if (logic.getCurrentView() == View.START) {
			// other views OFF
			joinView.setVisible(false);
			createView.setVisible(false);
			// curr view ON
			joinCreateView.setVisible(true);

		} else if (logic.getCurrentView()== View.CREATE) {
			// other views OFF
			joinCreateView.setVisible(false);
			joinView.setVisible(false);
			// curr view ON
			createView.setVisible(true);
		} else if (logic.getCurrentView() == View.JOIN) {
			// other views OFF
			joinCreateView.setVisible(false);
			createView.setVisible(false);
			// curr view ON
			joinView.setVisible(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());

	}
}