package program;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame{

	public JPanel panel;

	
	public JTextField textFields[] = new JTextField[8];
	
	public JButton button;
	public int width;
	public int height;
	public String data = "";
	public String outputData = "";
	
	
	public Window(int w, int h) {
		
		
		this.setSize(w,h);
		this.setTitle("Hotel Check In");
		this.setLocationRelativeTo(null);
		this.setMinimumSize(new Dimension(200,200));
		
		width = w;
		height = h;
		loadComponents();
		
		//asking if we are sure to exit the program
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we)
		    { 
		        String ObjButtons[] = {"Yes","No"};
		        int PromptResult = JOptionPane.showOptionDialog(null,"do you want to back up your records?","",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		        	// BS FUNCTION CALLING
		        	try {
						backup();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	
		        	
		            System.exit(0);
		        }
		        else if(PromptResult==JOptionPane.NO_OPTION) {
		        	System.exit(0);
		        }
		    }
		});
		
	}
	
	
	private void loadComponents() {
		loadPanel();
		
		loadLabel(0,0,255,50, "Name");
		loadTextField(0,52, 255, 30, 0);
		
		loadLabel(width - 280,0,255,50, "Last Name");
		loadTextField(width - 280 ,52, 255, 30, 1);
		
		loadLabel(0,100,255,50, "Phone #");
		loadTextField(0,158, 255, 30, 2);
		
		loadLabel(width - 280,100,255,50, "Room #");
		loadTextField(width - 280 ,158, 255, 30, 3);	
		
		loadLabel(0,200,255,50, "Room Type");
		loadTextField(0,258, 255, 30, 4);
		
		
		loadLabel(width - 280,200,255,50, "Booking Date");
		loadTextField(width - 280 ,258, 255, 30, 5);
		
		loadLabel(0,300,255,50, "Last date");
		loadTextField(0,358, 255, 30, 6);
		
		
		loadLabel(width - 280,300,255,50, "Total amount");
		loadTextField(width - 280 ,358, 255, 30, 7);
		
		loadButton();
	}


	private void loadPanel() {
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.black);
		
		this.getContentPane().add(panel);
	}
	
	private void loadLabel(int x, int y, int w, int h, String text) {
		JLabel label = new JLabel();
		
		label.setBounds(x,y,w,h);
		label.setText(text);
		
		label.setForeground(Color.green);
		label.setBackground(Color.black);
		
		label.setFont(new Font("arial", Font.BOLD, 26));
		
		label.setOpaque(true);
		panel.add(label);
		
	}

	private void loadTextField(int x, int y, int w, int h, int index) {
		textFields[index] = new JTextField();
		textFields[index].setBounds(x,y,w,h);
		textFields[index].setFont(new Font("arial", Font.BOLD, 26));
		textFields[index].setForeground(Color.black);
		textFields[index].setBackground(Color.orange);
		panel.add(textFields[index]);
		
	}
	
	private void loadButton() {
		button = new JButton("submit");
		button.setBounds((width / 2) - 75, height - 80, 150, 40);
		panel.add(button);
		
		
		
		ActionListener event = new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent ae) {
				
				for(int i = 0; i < 8; i++) {
					String currentField = textFields[i].getText();
					data += currentField + "\n";
					
					if(i > 0) outputData += ", ";
					
					outputData += currentField;
				}
				outputData += "\n";
				
				
				JOptionPane.showMessageDialog(null,"Data entered: \n" + data);
				int choice = JOptionPane.showConfirmDialog(null, "is this data correct?");
				
				if(choice == 0) {
					// calling the function that writes into file
					try {
						writeData(outputData);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					for(int i = 0; i < 8; i++) {
						textFields[i].setText("");
					}
				}
				
				if(choice == 1) {
					int secondChoice = JOptionPane.showConfirmDialog(null, "do you want to clear the data?");
					if(secondChoice == 0) {
						for(int i = 0; i < 8; i++) {
							textFields[i].setText("");
						}
					}
				}
				
				data = "";
				
			}
			
		};
	
		button.addActionListener(event);
	}

	private void writeData(String data) throws IOException {
		File file = new File("data.csv");
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		
		pw.println(data);
		pw.close();

	}
	
	private void backup() throws IOException {
		Process proc = Runtime.getRuntime().exec("./script.sh");
    	try {
			proc.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
