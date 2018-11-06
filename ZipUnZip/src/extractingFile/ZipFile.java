package extractingFile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFile {
	public static void Extract(final String OUTPUT_FOLDER,String FILE_PATH) {
		File folder=new File(OUTPUT_FOLDER);
		//if folder does not exist
		if(!folder.exists())
			folder.mkdirs();
		//create a buffer to write data
		byte[] buffer=new byte[1024];
		ZipInputStream zipIS=null;  //use to read file
		try {
			zipIS=new ZipInputStream(new BufferedInputStream(new FileInputStream(FILE_PATH)));
			ZipEntry entry=null;
			while((entry=zipIS.getNextEntry())!=null) { //if next entry exists
				String entryName=entry.getName();
				// for example : if this entry is a folder, outFileName will equivalent as "D:/folderName"
				//if it is a file, will equivalent as "D:/fileName" or "D:/folderName/fileName" 
				String outFileName=OUTPUT_FOLDER+File.separator+entryName;  
				System.out.println("Unzip: "+outFileName);
				
				if(entry.isDirectory()) {
					new File(outFileName).mkdirs();  //if this entry is a folder, create a new folder in destination dir 
				}else {
					BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(outFileName));
					int len;
					while((len=zipIS.read(buffer))>0) {
						bos.write(buffer,0,len);
					}
					bos.close();
				}
			}
			 System.out.println("Extracted completely!");
			
		}catch (IOException e) {
			System.out.println(e.getMessage());;
		}finally {
			try {
				zipIS.close();
			}catch (Exception e) {

			}
		}
	}
	public static void ListEntryView(String FILE_PATH) {
		//create zipis object to read zip file
		ZipInputStream zipIS=null;
		//zip entry to use as a temp unit 
		ZipEntry entry=null;
		//browse each entry (up to down)
		try {
		zipIS=new ZipInputStream(new BufferedInputStream(new FileInputStream(FILE_PATH)));
		while((entry=zipIS.getNextEntry())!=null) {
			if(entry.isDirectory())
				System.out.print("Directory: ");
			else 
				System.out.print("File: ");
			System.out.println(entry.getName());
		}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				zipIS.close();
			}catch (Exception e) {
				
			}
		}
	}
	public static void ZipDirectory(File inputDir, File outputZipFile) {
		//create parent folder for output file
		outputZipFile.getParentFile().mkdirs();
		String inputDirPath=inputDir.getAbsolutePath();
		
		byte[] buffer=new byte[1024];
		
		
		ZipOutputStream zipOutputStream=null;
		
		try {
			List<File> allFiles=listChildFiles(inputDir);
			
			zipOutputStream=new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outputZipFile)));
			for(File file:allFiles) {
				String filePath=file.getAbsolutePath();
				System.out.println("Zipping "+filePath);
				// for example, the file path is : "D/abc/text.txt", the inputDir path is D:/abc
				//,filePath[inputDirpath.length()] ='/', so the substring will begin after '/'
				String entryName=filePath.substring(inputDirPath.length()+1); 
				ZipEntry zipEntry=new ZipEntry(entryName);
				
				//add entry to zipOS
				zipOutputStream.putNextEntry(zipEntry);
				
				//read file data and write to ZipOS
				FileInputStream fileInputStream=new FileInputStream(filePath);
				BufferedInputStream bufferedInputStream=new BufferedInputStream(fileInputStream);
				int len;
				while((len=bufferedInputStream.read(buffer))>0) {
					zipOutputStream.write(buffer,0,len);
				}
				
				fileInputStream.close();
				bufferedInputStream.close();
			}
			System.out.println("Compress folder successfully!");
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
			zipOutputStream.close();
			}catch (Exception e) {
				System.out.println("Error when closing zip output stream!");
			}
		}
	}
	public static List<File> listChildFiles(File dir) throws IOException{   //tra ve 1 list cac file va thu muc con cua 1 thuc muc
		List<File> allFiles=new ArrayList<File>();
		File[] childFiles=dir.listFiles();
		for(File file:childFiles) {
			if(file.isFile()) {
				allFiles.add(file);
			}else {
				List<File> files=listChildFiles(file);
				allFiles.addAll(files);
			}
		}
		return allFiles;
			
	}
	
	static public void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter zip file path: ");
		//String filePath=scanner.nextLine();
		/*System.out.println("Enter output folder path: ");
		String output_folder=scanner.nextLine();
		ZipFile.Extract(output_folder, filePath);*/
		ZipFile.ZipDirectory(new File("D:/Web"),new File( "D:/CV.zip"));
		scanner.close();
	}
}
