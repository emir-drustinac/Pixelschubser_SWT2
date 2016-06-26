package Client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SharedData.ActionCard;

public class GuiActionCard extends JPanel {
	
	private final ActionCard card;
	
	public GuiActionCard(ActionCard a) {
		this(a, false);
	}
	public GuiActionCard(ActionCard a, boolean small) {
		card = a;
		//build gui of component
		int textPadding = small ? 4 : 8;
		int borderWidth = small ? 4 : 8;
		float fontSizeTitle = small ? 12f : 18f;
		Dimension prefDim = small ? new Dimension(105, 150) : new Dimension(210, 300);
		Color bgColor = new Color(200, 200, 250);
		
		this.setBackground(bgColor);
		this.setBorder(//BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.white, borderWidth, true)
				//BorderFactory.createEmptyBorder(padding,padding,padding,padding)
				);
		this.setLayout(new BorderLayout());
		
		// oberer Teil mit Name und Bild
		JLabel title = getIconLabel("/images/actioncards/ac_title.png");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setHorizontalTextPosition(JLabel.CENTER);
		title.setVerticalTextPosition(JLabel.CENTER);
		title.setText(a.getNameHTML(true, true).toUpperCase());
		title.setForeground(Color.white);
		title.setFont(title.getFont().deriveFont(fontSizeTitle));
		
		JLabel image = getIconLabel(a.getImagePath());

		// add elements
		this.add(title, BorderLayout.NORTH);
		this.add(image, BorderLayout.CENTER);
		
		// unterer teil mit Text
		if (!small) {
		JPanel desc = new JPanel(new BorderLayout());
		desc.setBackground(bgColor);
		desc.setBorder(BorderFactory.createEmptyBorder(0,textPadding,textPadding,textPadding));
		
		JLabel time_label = getIconLabel("/images/actioncards/ac_time.png");
		time_label.setHorizontalTextPosition(JLabel.RIGHT);
		time_label.setText(a.getTimeUsableHTML());
		time_label.setFont(time_label.getFont().deriveFont(11f));
		
		JPanel sep = new JPanel();
		sep.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(-3,50,7,50),
				BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black)
				));
		sep.setBackground(bgColor);

		JLabel action_label = getIconLabel("/images/actioncards/ac_action.png");
		action_label.setHorizontalTextPosition(JLabel.RIGHT);
		action_label.setText(a.getActionConsequencesHTML());
		action_label.setFont(time_label.getFont());

		// add elements
		desc.add(time_label, BorderLayout.NORTH);
		desc.add(sep, BorderLayout.CENTER);
		desc.add(action_label, BorderLayout.SOUTH);
		this.add(desc, BorderLayout.SOUTH);
		}
		
		this.setPreferredSize(prefDim);
	}

	public GuiActionCard toBigCard() {
		return new GuiActionCard(card, false);
	}

	public GuiActionCard toSmallCard() {
		return new GuiActionCard(card, true);
	}

	private static final long serialVersionUID = 3393147751236399060L;
	
	public static JLabel getIconLabel(String path) {
		java.net.URL imgUrl = GuiActionCard.class.getResource(path);
		ImageIcon icon = new ImageIcon(imgUrl); //Toolkit.getDefaultToolkit().getImage(path)
		return new JLabel(icon);
	}
}
