package mainClass;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;

import java.awt.MenuItem;
import java.awt.PopupMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import java.io.File;


import java.io.IOException;
import java.nio.file.Path;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import javax.swing.JSplitPane;

import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeSelectionModel;

import javax.swing.tree.TreeSelectionModel;

import GUI.FileNameRenderer;

import copyingFile.FileManipulation;
import extractingFile.ZipFile;
import model.ComputerDirectoryTreeModel;


import javax.swing.JScrollPane;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.border.SoftBevelBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;



public class FolderTree  {
	
	private JFrame frame;
	private JTree tree ;
	private JLabel label=new JLabel("Welcome...");
	private ComputerDirectoryTreeModel treeModel;
	private String[] pathCopy=new String[2];	//cap nhat cac duong dan cua file nguon va file dich sau moi lan click chuot phai
	private String filePathSrc="",filePathDes="";   //bien luu duong dan de copy file
	private boolean cutOrCopy=false; //xem nut vua nhan la copy hay cut
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FolderTree window = new FolderTree();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FolderTree() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		System.out.println(filePathSrc+" "+filePathDes);
		frame = new JFrame();
		frame.setTitle("File Manager");
		frame.setBounds(100, 100, 849, 515);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(10, 11, 600, 400);
		frame.getContentPane().add(splitPane);
		frame.setResizable(false);
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1=new JScrollPane();
		
		
		splitPane.setLeftComponent(scrollPane);
		splitPane.setRightComponent(scrollPane_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setSize(new Dimension(scrollPane_1.getWidth(), 40));
		scrollPane_1.setColumnHeaderView(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnRefreshApp = new JButton("Refresh App");
		btnRefreshApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.updateComponentTreeUI(frame);
			}
		});
		panel_1.add(btnRefreshApp);
		
		JButton btnCreateNewFolder = new JButton("Create New Folder");
		panel_1.add(btnCreateNewFolder);
		splitPane.setDividerLocation(0.4);
		for(int i=0;i<pathCopy.length;i++) {
			pathCopy[i]="";
		}
		tree = new JTree();
	
		tree.setCellRenderer(new FileNameRenderer());
		tree.setShowsRootHandles(true);
		
		
		treeModel=new ComputerDirectoryTreeModel();
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);
		tree.setCursor(new Cursor(Cursor.HAND_CURSOR));
		tree.setSelectionModel(new DefaultTreeSelectionModel());
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				try {
				String pathToRead=tree.getLastSelectedPathComponent().toString();
				if(pathToRead!=null) {
					pathToRead=pathToRead.replace('\\', '/');
					if(pathToRead.endsWith(".zip"))
						ZipFile.ListEntryView(pathToRead);
				}
				}catch (Exception ex) {
					System.out.println("Error");
				}
				
			}
		});
		//copy menu
		PopupMenu popupMenu = new PopupMenu();   
		
		MenuItem copyItem=new MenuItem("Copy");
		MenuItem cutItem=new MenuItem("Cut");
		MenuItem pasteItem=new MenuItem("Paste");
		
		MenuItem extractItem=new MenuItem("Extract to folder");	//for extract function
		MenuItem extractHereItem=new MenuItem("Extract here");	//for extract function
		MenuItem compressItem=new MenuItem("Compress quickly!"); //for compress function
		MenuItem compressOption=new MenuItem("Compress option");//
		MenuItem deleteItem=new MenuItem("Delete"); //for delete function
		MenuItem refreshItem=new MenuItem("Refresh");
		
		
		//mac dinh nut paste se disable
		pasteItem.setEnabled(false);
		extractItem.setEnabled(false);
		
		//menu copy va cut
		popupMenu.add(copyItem);
		popupMenu.add(cutItem);
		popupMenu.add(pasteItem);
		popupMenu.add(extractItem);
		popupMenu.add(extractHereItem);
		popupMenu.add(compressItem);
		popupMenu.add(compressOption);
		popupMenu.add(deleteItem);
		popupMenu.add(refreshItem);
		tree.add(popupMenu);
		
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setModel(treeModel);
		tree.setEditable(true);
	// su kien cho chuot khi nhan vao tree
		
		tree.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
			  
			 }

			  @Override
			  public void mouseExited(MouseEvent e) {
			        
			  }

			@Override
			public void mouseClicked(MouseEvent e) {
	
				 if(SwingUtilities.isRightMouseButton(e)) {   //neu nut nhan la chuot phai 
					 	
						 if(tree.getPathForLocation(e.getX(), e.getY())!=null)  //neu click vao mot node cua tree
						 pathCopy[pathCopy.length-1]=tree.getPathForLocation(e.getX(), e.getY()).getLastPathComponent().toString();
						 else System.out.println(" You're clicking outside the folder tree and the popupMenu won't be showed !");			 	
					 
				System.out.println("You are right-clicking on JTree and open popup menu ");
					if(tree.getLastSelectedPathComponent().toString().endsWith(".zip")) {  //neu la file zip
						extractItem.setEnabled(true);//cho phep giai nen
						extractHereItem.setEnabled(true);
						
						System.out.println("You are clicking on zip file!");
						}	
						else {
							extractItem.setEnabled(false); 
							extractHereItem.setEnabled(false);
							
					}
					if(tree.getLastSelectedPathComponent().toString()!=null) {
						String currentPath=tree.getLastSelectedPathComponent().toString();  
						currentPath.replace('\\', '/');
						
						File currentFile=new File(currentPath);
						if(currentFile.isDirectory()) {   //neu la 1 thu muc, cho phep nen lai thanh file zip
							compressItem.setEnabled(true);
						}
						else
							compressItem.setEnabled(false);
						
					}
				  popupMenu.show(tree, e.getX(), e.getY()); 
				  
				  /*for(int i=0;i<pathCopy.length;i++) 
					  System.out.println(pathCopy[i]);*/  //hien thi duong dan cua tree node vua click chuot phai
				  }
					
				 }
			 
		});
			
		
		//COPY VA CAT FILE (FOLDER)
		
		//xu ly khi nhan copy
		copyItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pasteItem.setEnabled(true);
				if(pathCopy[pathCopy.length-1]!=null)
					pathCopy[0]=pathCopy[pathCopy.length-1];  // tao duong dan file nguon
					
				
				filePathSrc=pathCopy[0].replace('\\', '/');
				cutOrCopy=false;
				label.setText("Please select destination folder to copy");
				
			}
		});
		
		//xu ly khi nhan cut
		
		cutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				pasteItem.setEnabled(true);
				if(pathCopy[pathCopy.length-1]!=null)
					pathCopy[0]=pathCopy[pathCopy.length-1];  // tao duong dan file nguon
				filePathSrc=pathCopy[0].replace('\\', '/');
				cutOrCopy=true;
				label.setText("Please select destination folder to move");
				
			}
		});
		
		// thuc hien copy
		pasteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(filePathSrc!=null) System.out.println("File path source: "+filePathSrc);
				if(pathCopy[pathCopy.length-1]!=null) 
					{
						//chuyen dau \ thanh dau / trong duong dan file
						filePathDes=pathCopy[pathCopy.length-1].replace('\\', '/');  //tao duong dan file dich
						System.out.println("File path des: "+filePathDes);
						
						//ghep noi de tao duong dan file dich
						String parentOfSource=(new File(filePathSrc).getParentFile()).getAbsolutePath();  
						// lấy đường dẫn của thư mục cha, sau đó lấy độ dài thư mục cha, và cắt tên của file để ghép vào đường dẫn của thư mục đích, nếu đường dẫn nguồn là 1 file
						String sourceFileName=filePathSrc.substring(parentOfSource.length());  //cắt tên của file or thư mục sắp copy
						filePathDes+="/"+sourceFileName; //nối vào đường dẫn đích
						 
						
					
						//copy file
						try {
							if(!cutOrCopy) {
								System.out.println("\n Perform Copy Function"+ filePathSrc+" to " +filePathDes+ "--" );
								FileManipulation.copyFolder(filePathSrc, filePathDes);
								label.setText("Copied successfully");
								SwingUtilities.updateComponentTreeUI(tree);							}
							else{
								System.out.println("\n Perform Copy Function"+ filePathSrc+" to " +filePathDes+ "--" );
								FileManipulation.cutFileOrFolder(filePathSrc, filePathDes);
								label.setText("Cut file successfully");
								SwingUtilities.updateComponentTreeUI(tree);	
							
							}
						}catch (Exception ex) {
								System.out.println(ex.getMessage());
						}
						
					}
				else 
					{
						System.out.println("Destination file path is null, you must right-clicking in folder to choose destination folder!");
						label.setText("Copy or paste failed");
					}
			
				
				pasteItem.setEnabled(false);  //disable nut paste
				
			}
		});
		
		extractItem.addActionListener(new ActionListener() {  
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String filePathToExtract=pathCopy[pathCopy.length-1];
				JFileChooser fileChooser=new JFileChooser("D:/");
				fileChooser.setDialogTitle("Select folder to extract to");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal=fileChooser.showDialog(frame, "Select Folder");
				if(returnVal==JFileChooser.APPROVE_OPTION) {
					File folder=fileChooser.getSelectedFile();
					String folderTOExtract=folder.getAbsolutePath();
					try {
						ZipFile.Extract(folderTOExtract, filePathToExtract);
						label.setText("Extracted successfully");
						SwingUtilities.updateComponentTreeUI(tree);	
					}catch (Exception ex) {
						ex.printStackTrace();
					}
				}else {
					System.out.println("Extracting action has been aborted! Please select folder to extract!");
					label.setText("Extract failed");
					SwingUtilities.updateComponentTreeUI(tree);	
				}
				
			}
		});
		
		extractHereItem.addActionListener(new ActionListener() { //giai nen ra 1 thu muc ngay tai thu muc chua file zip
			
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText("Extracting...");
				String filePathToExtract=tree.getLastSelectedPathComponent().toString();//lay duong dan file hien tai de giai nen
				filePathToExtract.replace('\\', '/');  //replace \ to / to create file path
				File fileToExtract=new File(filePathToExtract);
				String folderToExtract=fileToExtract.getParent().replace('\\', '/');  //create parent folder to extract
				folderToExtract+="/"+filePathToExtract.substring(folderToExtract.length()+1,filePathToExtract.length()-4); //create last path
				ZipFile.Extract(folderToExtract, filePathToExtract); //extract zip file
				label.setText("Extracted successfully");
				SwingUtilities.updateComponentTreeUI(tree);	
				
			}
		});
		
		compressItem.addActionListener(new ActionListener() {   //nen thu muc
			
			@Override
			public void actionPerformed(ActionEvent event) {
				String dirPath=tree.getLastSelectedPathComponent().toString();   //lay duong dan node hien tai dang tro vao
				String parentPath=new File(dirPath).getParent();   //lay duong dan thu muc cha
				if(dirPath!=null) {
					dirPath=dirPath.replace('\\', '/');    
					parentPath=parentPath.replace('\\', '/');
					String filePathAfterCompress=dirPath+".zip";   //tao duong dan cho file zip xuat ra
					try { 
					File folderToCompress=new File(parentPath);       
					File fileAfterCompress=new File(filePathAfterCompress);
					
					ZipFile.ZipDirectory(folderToCompress,fileAfterCompress);
					label.setText("Compress successfully");
					SwingUtilities.updateComponentTreeUI(tree);	
					}catch (Exception e) {
						System.out.println("Error when trying to extract!");
					}
					// System.out.println(dirPath+" "+filePathAfterCompress);			
				}else
					label.setText("Compress failed");
				SwingUtilities.updateComponentTreeUI(tree);	
			}
		});
		
		deleteItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String pathToDelete=tree.getLastSelectedPathComponent().toString();
				if(pathToDelete!=null) {
					pathToDelete=pathToDelete.replace('\\', '/');
					try {
					File fileToDelete=new File(pathToDelete);
					if(fileToDelete.isDirectory()) 
						{FileManipulation.deleteFolder(fileToDelete);   //xoa thu muc
						SwingUtilities.updateComponentTreeUI(tree);
						}
					else
						if(fileToDelete.isFile())
						FileManipulation.deleteFile(fileToDelete);   //xoa file
					label.setText("Deleted successfully");
					SwingUtilities.updateComponentTreeUI(tree);	  //cap nhat lai tree
					}catch (IOException e) {
						System.out.println(e.getMessage());
						
					}	
				}
				else 
					label.setText("Delete failed");
				
			}
		});
		refreshItem.addActionListener(new ActionListener() {      //nut refresh (JButton) 
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.updateComponentTreeUI(frame);
				
			}
		});
		scrollPane.setViewportView(tree);
		
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(0, 460, 800, 26);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(label);
		frame.getContentPane().add(panel);
		
		JPanel panel_2 = new JPanel() {          // cai hinh nen cho panel 2 ben phai
			ImageIcon icon= new ImageIcon("src/images/background_2.png");
			@Override
			public void paintComponent(Graphics g) {
				Dimension d= getSize();
				g.drawImage(icon.getImage(), 0, 0, d.width, d.height,null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		panel_2.setBounds(620, 11, 213, 400);
		frame.getContentPane().add(panel_2);
		
	
		
	}
}

