package com.farouk.thread.general;

import java.io.File;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GeneralCase {

	
	private static boolean cancel = false;
	private static final int maxFolderFilesNumber = 2;
	private static final String liveFolder = "./live/";
	private static final String archFolder = "./arch/";
	private Lock lock = new ReentrantLock();
	private final String[] url;
	
	
	
	
	

	public GeneralCase(String ... url) {
		     this.url = url;
	}

	

	public void start() {
		startArchiverThread();
		Thread[] arrayThread = new Thread[url.length];
		int i =0;
		for(String ur : url){
			        arrayThread[i]  = createScanThread(ur);
			        i++;
		         }
		for(Thread d : arrayThread){
			  try {
				d.start();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
	}
	
	
	   // this thread reads RSS Feed data and writes them in  files
	
	private Thread createScanThread(final String ur) {
		
		return new Thread(new Runnable() {
			public void run() {
				int index =  0;
				while (!cancel) {
					              final String path = liveFolder + ur.replaceAll("news/feed/", "").replaceAll("radionational/feed/", "").replaceAll("/rss.xml", "") + "-v" + index +".txt";
					              index++;
					              List<String> newsList = RSSParser.getNewsList(ur);
					              String[] lines = newsList.toArray(new String[newsList.size()]);
									try {
										lock.lock();
										       IOUtils.writeLines(path, lines, false);
									} catch (Exception e) {
										
										e.printStackTrace();
									}
									finally {
										lock.unlock();
									}
					       
				}
			}
		});
	}
	
	
	
	// this thread transfer files from live folder to the archive folder

	private void startArchiverThread() {
		Thread archiverThread = new Thread(new Runnable() {
			public void run() {
				while (!cancel) {
					    File liveFolderFile = new File(liveFolder);
					    File[] liveFolderFiles = liveFolderFile.listFiles();
					    int filesNumber = liveFolderFiles.length;
                        if(filesNumber > maxFolderFilesNumber){
                        	            for(File file : liveFolderFiles){
			                        	try {
			                        		       String path = archFolder + file.getName().replaceAll("-v", "-archV");
			                        		       lock.lock();
			                        		       IOUtils.copyFile(path, file, false);
			                        		       file.delete();
			                        	} catch (Exception e) {
			                        	    e.printStackTrace();
			                        	}
			                        	finally {
			                        		lock.unlock();
										}
			                        	
                                   }
                          }
				}
			}
		});
		archiverThread.start();
	}

	public static void cancel() {
		cancel = true;
	}

	public static void main(String[] args) {
		                GeneralCase scanner = new GeneralCase("news/feed/45910/rss.xml", 
		        		                                       "news/feed/52278/rss.xml", 
		        		                                       "news/feed/46800/rss.xml",
		        		                                       "news/feed/51892/rss.xml",
		        		                                       "radionational/feed/2895774/rss.xml", 
		        		                                       "radionational/feed/2894880/rss.xml", 
		        		                                       "radionational/feed/2891138/rss.xml", 
		        		                                       "radionational/feed/2886252/rss.xml");
		                scanner.start();
		         try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
		         cancel();
		          
	                }
}
