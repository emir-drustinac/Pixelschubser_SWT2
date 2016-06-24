package Client;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * @author Emir
 * 
 */
public class DiceAnimation {

	private int timerCount;
	private int rndDiceNr;
	private final int DELAY = 100;
	private int duration; // in ms
	private boolean proconsul;

	/**
	 * If proconsul is true then the animation show number between 1 and 5. The
	 * last shown number is the proconsul.
	 * 
	 * @author Emir
	 * @param proconsul
	 * @return
	 */
	public DiceAnimation(boolean proconsul) {
		this.proconsul = proconsul;
	}

	/**
	 * changes mercenary icon with dice icon that randomly changes very quickly
	 * for 2 - 4 seconds. Returns the number from the dice, where animation
	 * stopped.
	 * 
	 * @author Emir
	 * @param mercLabel
	 * @return
	 */
	public int runDiceAnimation(JLabel mercLabel) {
		// duration between 2000ms and 4000ms
		duration = new Random().nextInt(2000) + 2000;
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// run dice animation for 2-4 secs
				if (timerCount >= duration) {
					timer.cancel();
					timer.purge();
					timerCount = 0;
				}
				rndDiceNr = new Random().nextInt(6) + 1;
				java.net.URL imgUrl = getClass().getResource("/images/dice_side_" + rndDiceNr + ".png");
				ImageIcon diceIcon = new ImageIcon(imgUrl);
				mercLabel.setIcon(diceIcon);
				timerCount += DELAY;
			}
		};
		// run the task every DELAY ms
		timer.schedule(task, 0, DELAY);
		return rndDiceNr;
	}
	
	/**
	 * Animation :)
	 * @param mercenary
	 * @return number from the dice
	 */
	public int simpleAnimation(JLabel mercenary) {
		rndDiceNr = new Random().nextInt(proconsul ? 5 : 6) + 1;
		ImageIcon diceIcon = new ImageIcon(getClass().getResource("/images/dice_side_" + rndDiceNr + ".png"));
		mercenary.setIcon(diceIcon);
		return rndDiceNr;
	}

}
