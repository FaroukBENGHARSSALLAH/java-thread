package com.farouk.thread.general;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class IOUtils {
	public static void writeLines(String file, String[] lines, boolean append) {
					PrintWriter writer = null;
					try {
						try {
							writer = new PrintWriter(new FileWriter(file, append));
						} catch (IOException e) {
						
							e.printStackTrace();
						}
						for (String lLine : lines) {
							writer.println(lLine);
						}
					} finally {
						if (writer != null) {
							writer.close();
							writer = null;
						}
					}
	}
	
	
	
	public static void copyFile(String destFileName, File srcFile, boolean append) {
						PrintWriter writer = null;
						Scanner in = null;
							try {
								writer = new PrintWriter(new FileWriter(destFileName, append));
								in = new Scanner(srcFile);
						        while (in.hasNextLine()){
						                  String line = in.nextLine();
						                  writer.println(line);
									           }
						               }
							catch(Exception e){
								 e.printStackTrace();
							}
							finally {
									if (writer != null) {
										writer.close();
										writer = null;
								                }
									if (in != null) {
										in.close();
										in = null;
								                 }
					      }
               }
}	
