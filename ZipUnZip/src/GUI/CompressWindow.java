package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CompressWindow extends JFrame implements CompressGUI {

	private JPanel contentPane;
	static final long serialVersionUID= 111111198309209345L;
	private JCheckBox deleteAfterCompress;
	private JTextField fileName;
	private JButton btnOK,btnCancel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompressWindow frame = new CompressWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CompressWindow() {
		setTitle("Option");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 209);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		fileName = new JTextField();
		fileName.setBounds(67, 65, 188, 20);
		panel.add(fileName);
		fileName.setColumns(10);
		
		btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				System.out.println(getFileName());
				System.out.println(isDeleteAfterCompress());
				
			}
		});
		btnOK.setBounds(261, 64, 89, 23);
		panel.add(btnOK);
		
		deleteAfterCompress = new JCheckBox("Delete old file after compress");
		deleteAfterCompress.setBounds(67, 95, 182, 23);
		panel.add(deleteAfterCompress);
		
		JLabel lblEnterZipFile = new JLabel("Enter zip file name: ");
		lblEnterZipFile.setBounds(67, 40, 120, 14);
		panel.add(lblEnterZipFile);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFileName("");
				setDeleteAfterCompress(false);
				dispose();
			}
		});
		btnCancel.setBounds(360, 64, 89, 23);
		panel.add(btnCancel);
	}


	public String getFileName() {
		return fileName.getText();
	}

	public void setFileName(String fileName) {
		this.fileName.setText(fileName);
	}

	public boolean isDeleteAfterCompress() {
		return deleteAfterCompress.isSelected();
	}

	public void setDeleteAfterCompress(boolean deleteAfterCompress) {
		this.deleteAfterCompress.setSelected(deleteAfterCompress); 
	}
}
