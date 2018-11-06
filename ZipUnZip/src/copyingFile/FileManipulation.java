package copyingFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManipulation {
		public static void copyFile(String sourcePath, String destPath) throws FileNotFoundException, IOException,SecurityException {    //if you call this function as a copying File to Folder func 
			File sourceFile=new File(sourcePath); //create source file													, // let's piece folder path with "filenam.extension", filename.extension here as same as source file name
			File destFile=new File(destPath);  //create destination file
			
			
			if(sourceFile.exists()) {  //check if source file exists
				FileInputStream fis=new FileInputStream(sourceFile);   //create reading stream
				BufferedInputStream bis=new BufferedInputStream(fis);  //buffered
				
				FileOutputStream fos=new FileOutputStream(destFile);    //writing stream
				BufferedOutputStream bos=new BufferedOutputStream(fos);
				
				byte[] arr=new byte[1024];
				System.out.println("Copying file, please wait...");
				
				while ((bis.read(arr))!=-1) {
					bos.write(arr);
					bos.flush();   // push data remain in output stream
				}
				System.out.println("Done !");
				try {
					fis.close();
					bis.close();
					fos.close();
					bos.close();
				}catch (Exception e) {
					System.out.println("Error when closing file!");
				}
				
			}else throw new FileNotFoundException("File doesn't exist!");
		}
		
		public static void copyFolder(String srcPath, String destPath) throws IOException,SecurityException{
			File sourceFile=new File(srcPath);  //'file' can be a folder or a general file 
			File destFile=new File(destPath);   
			if(sourceFile.exists()) {
				if(sourceFile.isFile()) {      //if it is a file, call copyfile function above
					System.out.println("This is a file ! Calling copyFile func... ");
					FileManipulation.copyFile(srcPath, destPath);    
				}
				if(sourceFile.isDirectory()) {
					System.out.println("This is a folder, call copyFolder back!");
					if(!destFile.exists()) {
						System.out.println("Destination folder doesn't exists, create folder!");
						destFile.mkdirs();
					}
					System.out.println("Listing files and folders in folder!");
					File[] listFiles=sourceFile.listFiles();
					for(File f:listFiles) {
						System.out.println("Copying file in sub folder!");
						copyFolder(f.getAbsolutePath(), destPath+"/"+f.getName());
					}
				}
			}else throw new FileNotFoundException("Folder doesn't exists!");
			
		}
		
		static public void  cutFileOrFolder(String srcPath, String destPath) throws FileNotFoundException, IOException{
			File sourceFile=new File(srcPath);  //'file' can be a folder or a general file 
			File destFile=new File(destPath);   
			if(sourceFile.exists()) {
				if(sourceFile.isFile()) {      //if it is a file, call copyfile function above
					System.out.println("This is a file ! Calling copyFile func... ");
					FileManipulation.copyFile(srcPath, destPath);
					System.out.println("deleting source file -- ");
					sourceFile.delete();
					System.out.println("deleted success ! --");
				}
				if(sourceFile.isDirectory()) {
					System.out.println("This is a folder, call copyFolder back!");
					if(!destFile.exists()) {
						System.out.println("Destination folder doesn't exists, create folder!");
						destFile.mkdirs();
					}
					System.out.println("Listing files and folders in folder!");
					File[] listFiles=sourceFile.listFiles();
					for(File f:listFiles) {
						System.out.println("Copying file in sub folder!");
						copyFolder(f.getAbsolutePath(), destPath+"/"+f.getName());
					}
					System.out.println("deleting source file -- ");
					deleteFolder(sourceFile);
					System.out.println("deleted success ! --");
				}
						
			}else throw new FileNotFoundException("Folder doesn't exists!");
		}
		public static void deleteFolder(File dirFile) throws IOException,FileNotFoundException { //delete folder
			
			if(dirFile.isDirectory()) {
				String[] children=dirFile.list();
				for(int i=0;i<children.length;i++) {
					deleteFolder(new File(dirFile, children[i]));  
				}
			}
			dirFile.delete();
			System.out.println("Dir file has been deleted"); 
		}
		public static void deleteFile(File f) throws IOException, FileNotFoundException{ //delete file
			f.delete();
			System.out.println("File has beaen deleted");
		}
		public static void main(String[] args) throws IOException {
			
			//testing
			String sourcePath="D:\\Web\\CV";
			String destPath="E:\\CV";
			
			FileManipulation.cutFileOrFolder(sourcePath, destPath);
		}
}
