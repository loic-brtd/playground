package other;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InFrame {

	private JFrame frame;
	
	public InFrame(JPanel panel) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
}
