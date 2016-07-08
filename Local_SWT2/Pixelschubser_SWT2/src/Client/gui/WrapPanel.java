package Client.gui;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class WrapPanel extends JPanel {
	
	public WrapPanel() {
		super(new WrapLayout());
		
		class resizeListener extends ComponentAdapter {
			private final WrapPanel p;
			public resizeListener(WrapPanel p) {
				this.p = p;
			}
			public void componentResized(ComponentEvent e) {
					p.revalidate();
			}
		}
		addComponentListener(new resizeListener(this));
	}
	
}
