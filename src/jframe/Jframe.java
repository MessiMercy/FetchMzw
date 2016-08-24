package jframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Jframe extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JButton jButton = getInstaceJbutton();

	private static JButton getInstaceJbutton() {
		jButton = new JButton("start");
		jButton.setBounds(50, 50, 100, 100);
		jButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						com.MIAN.main(new String[] {});
					}
				}).start();
			}
		});

		return jButton;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("FetchMzw");
		frame.add(jButton);
		frame.setLayout(null);
		frame.setSize(200, 200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
