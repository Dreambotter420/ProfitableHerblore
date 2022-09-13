package script.paint;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WindowBuilderCopyOfGUI extends JFrame {

	private JPanel contentPane;
	private JTextField minProfitMarginTextField;
	private JTextField maxGrimyTextField;
	private JTextField undercuttingMinSellUnfTextField;
	private JTextField undercuttingMaxGrimyBuyTextField;

	/**
	 * Create the frame.
	 */
	public WindowBuilderCopyOfGUI() {
		setResizable(false);
		setTitle("Profitable Herblore Settings");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 432, 333);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Approved Herbs List");
		lblNewLabel_1.setBounds(147, 111, 126, 17);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		minProfitMarginTextField = new JTextField();
		minProfitMarginTextField.setBounds(353, 11, 53, 20);
		contentPane.add(minProfitMarginTextField);
		minProfitMarginTextField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Minimum profit margin per grimy -> unf herb (default 50 gp):");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 11, 333, 20);
		contentPane.add(lblNewLabel);
		
		JCheckBox XPModeBox = new JCheckBox("");
		XPModeBox.setBounds(87, 36, 21, 23);
		contentPane.add(XPModeBox);
		
		JLabel lblXpMode = new JLabel("XP Mode?");
		lblXpMode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblXpMode.setBounds(20, 39, 65, 20);
		contentPane.add(lblXpMode);
		
		JCheckBox botModeBox = new JCheckBox("");
		botModeBox.setBounds(87, 63, 21, 23);
		contentPane.add(botModeBox);
		
		JLabel lblBotMode = new JLabel("Bot Mode?");
		lblBotMode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBotMode.setBounds(20, 65, 65, 20);
		contentPane.add(lblBotMode);
		
		JLabel lblMaximumBuyQuantity = new JLabel("Maximum Grimy herb buy quantity:");
		lblMaximumBuyQuantity.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMaximumBuyQuantity.setBounds(147, 34, 196, 20);
		contentPane.add(lblMaximumBuyQuantity);
		
		maxGrimyTextField = new JTextField();
		maxGrimyTextField.setColumns(10);
		maxGrimyTextField.setBounds(353, 34, 53, 20);
		contentPane.add(maxGrimyTextField);
		
		JPanel panel = new JPanel();
		panel.setBounds(20, 125, 386, 92);
		contentPane.add(panel);
		
		JCheckBox Guam = new JCheckBox("Guam");
		Guam.setSelected(true);
		panel.add(Guam);
		
		JCheckBox Harralander = new JCheckBox("Harralander");
		Harralander.setSelected(true);
		panel.add(Harralander);
		
		JCheckBox Ranarr = new JCheckBox("Ranarr");
		Ranarr.setSelected(true);
		panel.add(Ranarr);
		
		JCheckBox Toadflax = new JCheckBox("Toadflax");
		Toadflax.setSelected(true);
		panel.add(Toadflax);
		
		JCheckBox Irit = new JCheckBox("Irit");
		Irit.setSelected(true);
		panel.add(Irit);
		
		JCheckBox Avantoe = new JCheckBox("Avantoe");
		Avantoe.setSelected(true);
		panel.add(Avantoe);
		
		JCheckBox Kwuarm = new JCheckBox("Kwuarm");
		Kwuarm.setSelected(true);
		panel.add(Kwuarm);
		
		JCheckBox Snapdragon = new JCheckBox("Snapdragon");
		Snapdragon.setSelected(true);
		panel.add(Snapdragon);
		
		JCheckBox Cadantine = new JCheckBox("Cadantine");
		Cadantine.setSelected(true);
		panel.add(Cadantine);
		
		JCheckBox Lantadyme = new JCheckBox("Lantadyme");
		Lantadyme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Lantadyme.setSelected(true);
		panel.add(Lantadyme);
		
		JCheckBox Dwarfweed = new JCheckBox("Dwarf weed");
		Dwarfweed.setSelected(true);
		panel.add(Dwarfweed);
		
		JCheckBox Torstol = new JCheckBox("Torstol");
		Torstol.setSelected(true);
		panel.add(Torstol);
		
		JButton StartButton = new JButton("Start!");
		StartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		StartButton.setForeground(Color.BLACK);
		StartButton.setBackground(Color.GRAY);
		StartButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		StartButton.setBounds(10, 228, 396, 32);
		contentPane.add(StartButton);
		
		JLabel lblundercuttingSellUnf = new JLabel("[Undercutting] Sell unf more than min sell:");
		lblundercuttingSellUnf.setHorizontalAlignment(SwingConstants.RIGHT);
		lblundercuttingSellUnf.setBounds(108, 57, 235, 20);
		contentPane.add(lblundercuttingSellUnf);
		
		undercuttingMinSellUnfTextField = new JTextField();
		undercuttingMinSellUnfTextField.setColumns(10);
		undercuttingMinSellUnfTextField.setBounds(353, 57, 53, 20);
		contentPane.add(undercuttingMinSellUnfTextField);
		
		JLabel lblundercuttingBuyGrimy = new JLabel("[Undercutting] Buy grimy more than max buy:");
		lblundercuttingBuyGrimy.setHorizontalAlignment(SwingConstants.RIGHT);
		lblundercuttingBuyGrimy.setBounds(30, 80, 313, 20);
		contentPane.add(lblundercuttingBuyGrimy);
		
		undercuttingMaxGrimyBuyTextField = new JTextField();
		undercuttingMaxGrimyBuyTextField.setColumns(10);
		undercuttingMaxGrimyBuyTextField.setBounds(353, 80, 53, 20);
		contentPane.add(undercuttingMaxGrimyBuyTextField);
		
		JLabel lblNewLabel_2 = new JLabel("NOTE: Script will not start if you put anything other than numbers in text fields!");
		lblNewLabel_2.setFont(new Font("Verdana", Font.PLAIN, 9));
		lblNewLabel_2.setBounds(10, 269, 416, 14);
		contentPane.add(lblNewLabel_2);
	}
}
