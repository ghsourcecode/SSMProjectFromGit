package com.decom.utilClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class DeleteFileUtil 
{
	 /**   
     * 删除文件，可以是单个文件或文件夹   
     * @param   fileName    待删除的文件名   
     * @return 文件删除成功返回true,否则返回false   
     */   
	public static boolean delete(String fileName)
	{
		File file = new File(fileName);
		if(!file.exists())
			throw new IllegalArgumentException("文件不存在！");
		
		if(file.isFile())
		{
			return deleteFile(fileName);
		}
		else
		{
			return deleteDirectory(fileName);
		}
	}
	/**   
     * 删除单个文件   
     * @param   fileName    被删除文件的文件名   
     * @return 单个文件删除成功返回true,否则返回false   
     */    
	private static boolean deleteFile(String fileName) {
		try 
		{
			File file = new File(fileName);
			if(file.isFile() && file.exists())
				return file.delete();
			else
				throw new FileNotFoundException();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	 /**   
     * 删除目录（文件夹）以及目录下的文件   
     * @param   dirName 被删除目录的文件路径
     * @return  目录删除成功返回true,否则返回false   
     */ 
	private static boolean deleteDirectory(String dirName) {

		if(!dirName.endsWith(File.separator))
			dirName += File.separator;
		try 
		{
			File dirFile = new File(dirName);
			if(!(dirFile.exists() || dirFile.isDirectory()))
				throw new FileNotFoundException();

			//标记是否删除成功
			boolean flag = true;
			File[] listFiles = dirFile.listFiles();
			for(int i = 0; i < listFiles.length; i++)
			{
				File file = listFiles[i];
				if(file.isFile())
				{
					flag = deleteFile(file.getAbsolutePath());
					if(!flag)
						break;
				}
				else
				{
					flag = deleteDirectory(file.getAbsolutePath());
					if(!flag)
						break;
				}
			}
			if(!flag)
				return flag;
			
			return dirFile.delete();

		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	/** 
	 * 通过调用系统命令删除一个文件夹及下面的所有文件 
	 * @param file 
	 */  
	public static void deleteFileByWinCom(File file){  
		Runtime rt = Runtime.getRuntime();  
		String cmd = null;  
		try{  
			if(file.isFile()){  
				cmd = "cmd.exe /c del /q/a/f/s "+file.getAbsolutePath();  
			}else{  
				cmd = "cmd.exe /c rd /s/q "+file.getAbsolutePath();  
			}  
			rt.exec(cmd);  
			System.out.println("成功执行了命令...");  
		}catch(Exception e){  
			System.out.println("调用系统命令失败了...");  
		}  
	}  

	/** 
	 * 通过递归调用删除一个文件夹及下面的所有文件 
	 * @param file 
	 */  
	public static void deleteFile(File file){  
		if(file.isFile()){//表示该文件不是文件夹  
			file.delete();
		}else{  
			//首先得到当前的路径  
			String[] childFilePaths = file.list();  
			for(String childFilePath : childFilePaths){  
				File childFile=new File(file.getAbsolutePath()+"\\"+childFilePath);  
				deleteFile(childFile);  
			}  
			file.delete();  
		}  
	}  

	/**
	 * 递归删除文件夹内容
	 * @param filePath 文件夹绝对路径
	 * @return
	 */
	public static boolean deleteFileByPath(String filePath){  
		File file = new File(filePath);
		if(file.isFile()){				//表示该文件不是文件夹  
			return file.delete();  
		}else{  
			boolean flag = false;
			//首先得到当前的路径  
			String[] childFilePaths = file.list();  
			for(String childFilePath : childFilePaths){  
				File childFile = new File(file.getAbsolutePath() + "\\" + childFilePath);  
				flag = deleteFileByPath(childFile.getAbsolutePath()); 
				if(!flag)
					break;
			}  
			if(!flag)
				return flag;
			return file.delete();  
		}  
	} 
	
	/**
	 * 递归删除filePath下的所有内容（包括文件夹）
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFileByPath(Path filePath){  
		try 
		{
			File file = filePath.toFile();
			if(file.isFile())
				Files.deleteIfExists(filePath);
			else
			{
				Files.walkFileTree(filePath, new FileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(Path dir,
							BasicFileAttributes attrs) throws IOException {
						// TODO Auto-generated method stub
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Path file,
							BasicFileAttributes attrs) throws IOException {
						// TODO Auto-generated method stub
						
						File f = file.toFile();
						if(f.isFile())
						{
							Files.deleteIfExists(file);
							return FileVisitResult.CONTINUE;
						}
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file,
							IOException exc) throws IOException {
						// TODO Auto-generated method stub
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir,
							IOException exc) throws IOException {
						// TODO Auto-generated method stub
						
						File dirFile = dir.toFile();
						if(dirFile.isDirectory())
						{
							Files.deleteIfExists(dir);
							return FileVisitResult.CONTINUE;
						}
						return FileVisitResult.CONTINUE;
					}
					
				});
			}
		
			return Files.deleteIfExists(filePath);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
