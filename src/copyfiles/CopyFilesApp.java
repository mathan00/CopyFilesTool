package copyfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class CopyFilesApp {

	public static void main(String[] args) {

		String dir1 = "/Users/mathan/Development/temp/dir1";
		String dir2 = "/Users/mathan/Development/temp/dir2";
		
		try {
			File dir = new File(dir1);
			if (dir.isDirectory()==false || dir.canRead() ==false){
				System.out.println(String.format("Provided source path of %s is not a valid directory or cannot be read. ",dir.toString()));
				return;
			}
			File[] listOfFiles = dir.listFiles();
			System.out.println(String.format("Total number of files found is %d " , listOfFiles.length));
			ArrayList<String> fileCopyStatus = new ArrayList<String>();
			long startTime = -1;
			long endTime = -1;
			long duration = 0;
			for (int i=0; i<listOfFiles.length; i++){
				startTime = System.currentTimeMillis();
				System.out.print(String.format("%d - Files %s %n",i+1,listOfFiles[i].getName()) );
				String fileName = listOfFiles[i].getName();
				File newFile = new File(dir2+"/"+fileName);
				if (newFile.exists()){
					System.out.println(String.format("File: %s already existed. Deleting and creating",newFile.getName()));
					System.out.println("Are you sure you want to replace the File. Enter Y/N respectivly");
					byte[] response=new byte[512];
					System.in.read(response);
					String responseStr = new String(response);
					duration += startTime;
					startTime =0;
					if ( responseStr != null && !responseStr.startsWith("Y") ){
						System.out.println(String.format("Skip copying file %s",newFile.getName()));
					}else{
						newFile.delete();
					}
					startTime = 0;
				}
				boolean success = newFile.createNewFile();
				int readInt = -1;
				ArrayList<Byte> readBytes = new ArrayList<Byte>();
				if (success){
					//1. read the file
					//2. write the file
					FileInputStream fis = new FileInputStream(listOfFiles[i]);
					FileOutputStream fos = new FileOutputStream(newFile);
					
					while ( (readInt = fis.read() ) !=-1){
//						System.out.println(String.format("read hex value: %x " , (byte)readInt));
						readBytes.add((byte)readInt);
						fos.write(readInt);
						fos.flush();
					}
					fos.close();
					fis.close();
					
				}else{
					fileCopyStatus.add(String.format("Following file %s was not copied Because it already exists", newFile.getName()));
				}
				endTime = System.currentTimeMillis();
				duration+=(endTime-startTime);
				System.out.println(String.format("Total time it took in msec %d ", duration/1000));
			}
			System.out.println("Copy File App closing");
		} catch (Exception e) {
			System.out.println(String.format("Exception is thrown: %s", e.getMessage()));
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
