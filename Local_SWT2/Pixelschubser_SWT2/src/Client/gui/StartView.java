package Client.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StartView extends JPanel implements ActionListener{
	
	private Logic logic;
	private JButton btnJoin;
	private JButton btnCreate;

	public StartView(Logic logic) {
		
		this.logic = logic;
		
		this.setLayout(null);
		btnJoin = new JButton("Join");
		btnCreate = new JButton("Create");

		btnJoin.setLocation(350, 200);
		btnCreate.setLocation(350, 250);

		btnJoin.setSize(100, 25);
		btnCreate.setSize(100, 25);

		btnJoin.setActionCommand("Join");
		btnCreate.setActionCommand("Create");

		btnJoin.addActionListener(this);
		btnCreate.addActionListener(this);

		this.add(btnJoin);
		this.add(btnCreate);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Join")){
			logic.joinView();
		}else if(e.getActionCommand().equals("Create")){
			logic.createView();;
		}
	}

	

}
