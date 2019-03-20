import java.awt.*;

import javax.swing.*;

public class Play2048 extends JFrame{
	private static final long serialVersionUID = 1L;


	public Play2048() {
		StringBuilder stringBuilder= new StringBuilder(" ");
		stringBuilder.append("\t\t\tRules For the Game\n\n");
		stringBuilder.append("1. Use arrows keys or a,d,s,w to move left,right,down,and up\n");
		stringBuilder.append("2. Press q  or r for quit or restart\n\n");
		stringBuilder.append("\t\t\tHave Fun ");
		GameShell canvas = new GameShell();
		setTitle("2048");


		JOptionPane.showMessageDialog(new JFrame().getContentPane(),stringBuilder.toString());
		setBackground(Color.decode("#91684a"));
		add(canvas);
		setSize(350, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Play2048();
		
	}
}
