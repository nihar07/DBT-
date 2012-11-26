import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class UserInterface extends JFrame{

	private JTextField prompt;
	private JTextPane console;
	private JScrollPane scroll;
	private JFrame frame;
	
	public UserInterface(){
		
		console = new JTextPane();
		frame = new JFrame();
		prompt = new JTextField();
		scroll = new JScrollPane(console);
		
		console.setBackground(new Color(0,0,0));
		console.setForeground(new Color(255, 255, 255));
		
		scroll.setBorder(null);

		prompt.setEnabled(true);
		prompt.setCaretColor(Color.GREEN);
		prompt.setText("DbtSQL> ");
		
		frame.setTitle("DBT SQL");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(prompt, BorderLayout.SOUTH);
		frame.add(scroll, BorderLayout.CENTER);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		prompt.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				String text = prompt.getText();

				console.setText(text);
				
				prompt.setText("DbtSQL> ");
				
			}
		});
		
		prompt.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_ENTER){
					//When enter is pressed send the command enterd in the prompt to dbt to be analyzed.
				}
			}
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e) {}
		});
	}

}
