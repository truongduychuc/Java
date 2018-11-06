package model;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;



import javax.swing.event.TreeModelListener;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class ComputerDirectoryTreeModel implements TreeModel {
	private DefaultMutableTreeNode root = new DefaultMutableTreeNode();


	public ComputerDirectoryTreeModel() {
		createRootLayout();
	}


	private void createRootLayout() {
		for (File path : File.listRoots()) { // Loops through the "root" directories on a computer - Determined by
												// system
			if (path.exists())
				
					if (listDirectories(path).length != 0)
						root.add(new DefaultMutableTreeNode(path));
		}
	}

	// Checks whether the drive can be added
	// Only adds local drives or USBs
	// Not used
	@Override
	public void addTreeModelListener(TreeModelListener l) {
		
	}
		
	@Override
	public Object getChild(Object parent, int index) {
		if (parent != root && parent instanceof DefaultMutableTreeNode) {
			File f = (File) (((DefaultMutableTreeNode) parent).getUserObject());
			return listDirectories(f)[index];
		} else if (parent != root) {
			File f = (File) parent;
			return listDirectories(f)[index];
		}
		return root.getChildAt(index);
	}

	// Overrides the original getChildCount so that
	// the method returns the correct value
	@Override
	public int getChildCount(Object parent) {
		if (parent != root && parent instanceof DefaultMutableTreeNode) {
			File f = (File) (((DefaultMutableTreeNode) parent).getUserObject());
			if (!f.isDirectory())
				return 0;
			else
				return listDirectories(f).length;
		} else if (parent != root) {
			File f = (File) parent;
			if (!f.isDirectory())
				return 0;
			else
				return listDirectories(f).length;
		}
		return root.getChildCount();
	}

	// Overrides the original hasChildren so that
	// the method returns the correct value
	

	// Overrides the original getIndexOfChild so that
	// the method returns the correct value
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent != root && parent instanceof DefaultMutableTreeNode) {
			File par = (File) (((DefaultMutableTreeNode) parent).getUserObject());
			File ch = (File) child;
			return Arrays.asList(listDirectories(par)).indexOf(ch);
		} else if (parent != root) {
			File par = (File) parent;
			File ch = (File) child;
			return Arrays.asList(listDirectories(par)).indexOf(ch);
		}

		return root.getIndex((TreeNode) child);
	}

	@Override
	public Object getRoot() {
		return root;
	}


	@Override
	public boolean isLeaf(Object node) {
		if(node!=root && node instanceof DefaultMutableTreeNode) {   //default mutable node cannot cast to java.io.File
				File f=(File)((DefaultMutableTreeNode)node).getUserObject(); 
				if(f.isDirectory()) return false;   //if this node is a folder
				else return true;   //if this node is a file
		}
		else
			if(node!=root) {   //node which isn't DefaultMutableTreeNode can cast to java.io.File
				File f=(File)node;
				return f.isFile();   
			}
		return false;
		
	}

	
	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		
	}


	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	
		
	}

	public boolean hasChildren(Object parent) {
		if(parent!=root && parent instanceof DefaultMutableTreeNode) {
			File f=(File)(((DefaultMutableTreeNode)parent).getUserObject());
			if(!f.isDirectory())
				return false;
			else 
				return hasDirectory(f);
		}else if(parent!=root) {
			File f=(File)parent;
			if(!f.isDirectory())
				return false;
			else 
				return hasDirectory(f);
		}
		return root.getChildCount()!=0?true:false;
	}
	private boolean hasDirectory(File path) {
		for(File temp:path.listFiles()) {
			if(temp.isDirectory()&&!temp.isHidden())
				if(temp.listFiles()!=null)
					return true;
		}
		return false;
	}
	private File[] listDirectories(File path) {
		ArrayList<File> arrayList = new ArrayList<File>();
		for (File temp : path.listFiles()) {
			if(!temp.isHidden())
					arrayList.add(temp);
		}

		return arrayList.toArray(new File[0]);
	}


}

