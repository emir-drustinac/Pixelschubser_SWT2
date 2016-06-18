package Client.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuWindow extends JFrame implements /* Observer, */ ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4506602204785588655L;
	private static final String windowName = "Proconsul - Liber qui praesunt - Menu - Client v0.1";
	private static final int initialWidth = 800;
	private static final int initialHeight = 500;
	private static final int maxWidth = 800;
	private static final int maxHeight = 500;
	
	private static final String STANDARDIP = "127.0.0.1";
	
	// Start
	private JButton btnJoinView;
	private JButton btnCreateView;
	private static final String JOINVIEW = "JoinView";
	private static final String CREATEVIEW = "CreateView";

	// Join View
	private JButton btnConnect;
	private JButton btnBack;
	private JLabel ipLabel;
	private JLabel nameLabel;
	private JTextField ip;
	private JTextField playerName;
	private JLabel status;
	private static final String JOIN = "Join";
	private static final String BACK = "Back";

	// Create View
	private JButton btnCreate;
	private JButton btnBackCreate;
	private JLabel gameNameLabel;
	private JLabel nameLabelCreate;
	private JTextField gameName;
	private JTextField playerNameCreate;
	private JLabel statusCreate;
	private static final String CREATE = "Create";

	private MenuWindowLogic logic; // MenuWindow Logic nicht Clientgamelogic

	// private StartView joinCreateView;
	// private JoinView joinView;
	// private CreateView createView;

	private JPanel viewStart;
	private JPanel viewJoin;
	private JPanel viewCreate;

	public MenuWindow(MenuWindowLogic logic) {
		this.logic = logic;
		// logic.registerObserver(this); // REGISTER OBSERVER

		setBounds(200, 200, maxWidth, maxHeight);
		setPreferredSize(new Dimension(initialWidth, initialHeight));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new CardLayout(0, 0));
		setResizable(false);
		setTitle(windowName);
		setLocationRelativeTo(null);

		viewStart = new JPanel();
		viewJoin = new JPanel();
		viewCreate = new JPanel();

		initStartView();
		initJoinView();
		initCreatetView();

		// joinCreateView = new StartView(logic);
		// joinView = new JoinView(logic);
		// createView = new CreateView(logic);
		//
		// this.getContentPane().add(joinCreateView);
		// this.getContentPane().add(joinView);
		// this.getContentPane().add(createView);

		// update();

		viewJoin.setVisible(false);
		viewCreate.setVisible(false);
		viewStart.setVisible(true);

		setVisible(true);
	}

	private void initStartView() {
		viewStart.setLayout(null);
		btnJoinView = new JButton("Join");
		btnCreateView = new JButton("Create");

		btnJoinView.setLocation(350, 200);
		btnCreateView.setLocation(350, 250);

		btnJoinView.setSize(100, 25);
		btnCreateView.setSize(100, 25);

		btnJoinView.setActionCommand(JOINVIEW);
		btnCreateView.setActionCommand(CREATEVIEW);

		btnJoinView.addActionListener(this);
		btnCreateView.addActionListener(this);

		viewStart.add(btnJoinView);
		viewStart.add(btnCreateView);
		getContentPane().add(viewStart);
	}

	private void initJoinView() {
		viewJoin.setLayout(null);

		btnConnect = new JButton("Connect");
		btnBack = new JButton("Back");
		ipLabel = new JLabel("IP:");
		nameLabel = new JLabel("Player Name:");
		ip = new JTextField(STANDARDIP);
		playerName = new JTextField();
		status = new JLabel();

		// set position
		ipLabel.setLocation(200, 100);
		ip.setLocation(300, 100);
		nameLabel.setLocation(200, 150);
		playerName.setLocation(300, 150);
		btnConnect.setLocation(350, 200);
		btnBack.setLocation(350, 250);
		status.setLocation(325, 400);

		// size
		ip.setSize(200, 25);
		playerName.setSize(200, 25);
		ipLabel.setSize(100, 25);
		nameLabel.setSize(100, 25);
		btnConnect.setSize(100, 25);
		btnBack.setSize(100, 25);
		status.setSize(200, 25);
		status.setForeground(Color.RED);

		// action command
		btnConnect.setActionCommand(JOIN);
		btnBack.setActionCommand(BACK);

		// add action listener
		btnConnect.addActionListener(this);
		btnBack.addActionListener(this);

		viewJoin.add(btnConnect);
		viewJoin.add(btnBack);
		viewJoin.add(ipLabel);
		viewJoin.add(ip);
		viewJoin.add(playerName);
		viewJoin.add(nameLabel);
		viewJoin.add(status);

		getContentPane().add(viewJoin);
	}

	private void initCreatetView() {
		viewCreate.setLayout(null);

		// init
		btnCreate = new JButton("Create");
		btnBackCreate = new JButton("Back");
		gameNameLabel = new JLabel("GameName:");
		nameLabelCreate = new JLabel("Player Name:");
		gameName = new JTextField();
		playerNameCreate = new JTextField();
		
		statusCreate = new JLabel();

		// set position
		gameNameLabel.setLocation(200, 100);
		gameName.setLocation(300, 100);
		nameLabelCreate.setLocation(200, 150);
		playerNameCreate.setLocation(300, 150);
		btnCreate.setLocation(350, 200);
		btnBackCreate.setLocation(350, 250);
		
		statusCreate.setLocation(325, 400);

		// size
		gameName.setSize(200, 25);
		playerNameCreate.setSize(200, 25);
		gameNameLabel.setSize(100, 25);
		nameLabelCreate.setSize(100, 25);
		btnCreate.setSize(100, 25);
		btnBackCreate.setSize(100, 25);
		
		statusCreate.setSize(200, 25);
		statusCreate.setForeground(Color.RED);

		// action command
		btnCreate.setActionCommand("Create");
		btnBackCreate.setActionCommand("Back");

		// add action listener
		btnCreate.addActionListener(this);
		btnBackCreate.addActionListener(this);

		viewCreate.add(btnCreate);
		viewCreate.add(btnBackCreate);
		viewCreate.add(gameNameLabel);
		viewCreate.add(gameName);
		viewCreate.add(playerNameCreate);
		viewCreate.add(nameLabelCreate);
		viewCreate.add(statusCreate);

		getContentPane().add(viewCreate);
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println(e.getActionCommand());

		if (e.getActionCommand().equals(JOINVIEW)) {
			viewStart.setVisible(false);
			viewCreate.setVisible(false);
			viewJoin.setVisible(true);
		} else if (e.getActionCommand().equals(CREATEVIEW)) {
			viewStart.setVisible(false);
			viewCreate.setVisible(true);
			viewJoin.setVisible(false);
		} else if (e.getActionCommand().equals(JOIN)) {
			
			if (logic.connectToGame(ip.getText(), playerName.getText())) {
				viewStart.setVisible(false);
				viewCreate.setVisible(false);
				viewJoin.setVisible(false);
				setVisible(false);
				this.dispose();
			} else {
				status.setText("IP falsch oder Name leer!!!");
			}

		} else if (e.getActionCommand().equals(CREATE)) {
			
			if(logic.createGame(gameName.getText(), playerNameCreate.getText())){
				viewStart.setVisible(false);
				viewCreate.setVisible(false);
				viewJoin.setVisible(false);
				setVisible(false);
				this.dispose();
			} else {
				statusCreate.setText("Game Name oder Player Name leer!!!");
			}
			
		} else if (e.getActionCommand().equals(BACK)) {
			viewStart.setVisible(true);
			viewCreate.setVisible(false);
			viewJoin.setVisible(false);
		}

	}
}
