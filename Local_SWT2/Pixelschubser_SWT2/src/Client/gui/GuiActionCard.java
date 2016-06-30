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
		float fontSizeTitle = small ? 12f : 16f;
		float descFontSize = 11f;
		Dimension prefDim = small ? new Dimension(90, 125) : new Dimension(180, 250);
		Color bgColor = new Color(200, 200, 250);
		
		String cardTitle = a == null ? "" : a.getNameHTML(true, true).toUpperCase();
		String imagePath = a == null ? "/images/actioncards/x.png" : a.getImagePath();
		String timeText = a == null ? "" : a.getTimeUsableText();
		String timeHTML = a == null ? "" : a.getTimeUsableHTML();
		String actionText = a == null ? "" : a.getActionConsequencesText();
		String actionHTML = a == null ? "" : a.getActionConsequencesHTML();
		
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
		title.setText(cardTitle);
		title.setForeground(Color.white);
		title.setFont(title.getFont().deriveFont(fontSizeTitle));
		
		JLabel image = getIconLabel(imagePath);

		// add elements
		this.add(title, BorderLayout.NORTH);
		this.add(image, BorderLayout.CENTER);
		
		// unterer teil mit Text
		if (!small) {
			JPanel desc = new JPanel(new BorderLayout());
			desc.setBackground(bgColor);
			desc.setBorder(BorderFactory.createEmptyBorder(0,textPadding,textPadding,textPadding));

			if (!timeText.isEmpty()) {
				JLabel time_label = getIconLabel("/images/actioncards/ac_time.png");
				time_label.setHorizontalTextPosition(JLabel.RIGHT);
				time_label.setText(timeHTML);
				time_label.setFont(time_label.getFont().deriveFont(descFontSize));
				desc.add(time_label, BorderLayout.NORTH);
			}
			if (!timeText.isEmpty() && !actionText.isEmpty()) {
				JPanel sep = new JPanel();
				sep.setBorder(BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(-3,50,7,50),
						BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black)
						));
				sep.setBackground(bgColor);
				desc.add(sep, BorderLayout.CENTER);
			}

			if (!actionText.isEmpty()) {
				JLabel action_label = getIconLabel("/images/actioncards/ac_action.png");
				action_label.setHorizontalTextPosition(JLabel.RIGHT);
				action_label.setText(actionHTML);
				action_label.setFont(action_label.getFont().deriveFont(descFontSize));
				desc.add(action_label, BorderLayout.SOUTH);
			}

			if (!timeText.isEmpty() || !actionText.isEmpty()) {
				// add elements
				this.add(desc, BorderLayout.SOUTH);
			}
		}
		
		this.setPreferredSize(prefDim);
		this.setMaximumSize(prefDim);
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
	
	public ActionCard getActionCard() {
		return card;
	}
}
