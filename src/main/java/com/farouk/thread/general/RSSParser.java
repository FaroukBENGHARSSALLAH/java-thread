package com.farouk.thread.general;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;



public class RSSParser  {
	
	 private static final String rss_feed = " RSS Feed";
	 private static final String date_format = "EEE, dd MMM yyyy HH:mm:ss";
	 private static final String attr_url = "url";
	 private static final String rssFeedBaseUrl = "http://www.abc.net.au/news/";
	 public  static  final String CHANNEL = "channel";
	 public  static  final String TITLE = "title";
	 public  static  final String DESCRIPTION = "description";
	 public static  final String LINK = "link";
     public  static  final String ITEM = "item";
     public  static  final String PUB_DATE = "pubDate";
     public  static  final String MEDIA_THUMBNAIL = "thumbnail";
     public  static  final String MEDIA_CONTENT = "content"; 
     public  static  final String ABC = "a23";

	 		  
	 public static List<String> getNewsList(String feedUrl) {
		                   List<String> list = new LinkedList<String>();
		                   URL url = null;
						try {
							url = new URL(rssFeedBaseUrl + feedUrl);
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					       try {
					    	          boolean isHeader = true;
					    	          String title = null;
								      String category = null;
								      String description = null;
								      String date = null;
								      String link = null;
								      String thumbnail_picture = null;
								      String content_picture_1210 = null;
								      String content_picture_705 = null;
								      String content_picture_627 = null;
								      String content_picture_1253 = null;
								      String content_picture_1400 = null;
								  	
								  	  
								      // First create a new XMLInputFactory
								      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
								      // Setup a new eventReader
								      InputStream in = null;
									try {
										in = url.openStream();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
								      // read the XML document
								      while (eventReader.hasNext()) {
												        XMLEvent event = eventReader.nextEvent();
												        if (event.isStartElement()) {
																          String localPart = event.asStartElement().getName() .getLocalPart();
																          if(localPart.equals(CHANNEL) && isHeader){
																        	      event = eventReader.nextEvent();
																        	      event = eventReader.nextEvent();
																        	      category = getCharacterData(event, eventReader);
																        	      int i=0;
																        	      while(i<14){
																        	    	           event = eventReader.nextEvent();
																        	    	           i++;
																        	                  }
																        	      isHeader = false;
																          }
																          if(localPart.equals(ITEM)){
																              event = eventReader.nextEvent();
																            }
																          if(localPart.equals(TITLE)){
																                  title = getCharacterData(event, eventReader);
																                              }
																          if(localPart.equals(LINK)){
															                       link = getCharacterData(event, eventReader);
															                              }
																          if(localPart.equals(DESCRIPTION)){
																                  description = getCharacterData(event, eventReader);
																                              }
																          if(localPart.equals(PUB_DATE)){
																                   date = getCharacterData(event, eventReader);
																                               }
																          if(localPart.equals(MEDIA_CONTENT)){
																        	       if(content_picture_1210 == null) 
																        	    	                    content_picture_1210 = getPictureURL(event, eventReader);
																        	       else if(content_picture_705 == null) 
																        	    	                    content_picture_705 = getPictureURL(event, eventReader);
																        	       else if(content_picture_627 == null) 
																        	    	                    content_picture_627 = getPictureURL(event, eventReader);
																        	       else if(content_picture_1253 == null) 
																        	    	                    content_picture_1253 = getPictureURL(event, eventReader);
																        	       else if(content_picture_1400 == null) 
																        	    	                    content_picture_1400 = getPictureURL(event, eventReader);
																                               }
																          if(localPart.equals(MEDIA_THUMBNAIL)){
															        	           thumbnail_picture = getPictureURL(event, eventReader);
															                               }
												                   } 
												        else if (event.isEndElement()) {
																          if (event.asEndElement().getName().getLocalPart() == ITEM) {
																        	        News news = new News();
																        	        news.setTitle(title);
																        	        news.setCategory(category.replace(rss_feed, ""));
																        	        news.setDescription(description);
																        	        news.setDate(formatDate(date.substring(0, date.lastIndexOf(":") + 2), date_format));
																        	        news.setLink(link);
																        	        news.setThumbnail_picture(thumbnail_picture);
																        	        news.setContent_picture_1210(content_picture_1210);
																        	        news.setContent_picture_705(content_picture_705);
																        	        news.setContent_picture_627(content_picture_627);
																        	        news.setContent_picture_1253(content_picture_1253);
																        	        news.setContent_picture_1400(content_picture_1400);
																        	                {
																        	        if(news.getContent_picture_627() != null)
																        	        	                 news.setImage(news.getContent_picture_627());
												        	                        else if(news.getContent_picture_705() != null)
								        	                                                              news.setImage(news.getContent_picture_705());
												        	                        else if(news.getContent_picture_1210() != null)
								        	                                                              news.setImage(news.getContent_picture_1210());
												        	                        else if(news.getContent_picture_1253() != null)
								        	                                                              news.setImage(news.getContent_picture_1253());
												        	                        else if(news.getContent_picture_1400() != null)
								        	                                                              news.setImage(news.getContent_picture_1400());
																        	                 }
																        	        news.setReference(generateReference(news, ABC));
																		            list.add(news.toString());
																		                 {
															                        thumbnail_picture = null;
																			        content_picture_1210 = null;
																			        content_picture_705 = null;
																			        content_picture_627 = null;
																			        content_picture_1253 = null;
																			        content_picture_1400 = null;     	 
																		                 }
																		            event = eventReader.nextEvent();
																                    continue;
												                                       }
												                   }
								                         }
					                            } 
					       catch (XMLStreamException e) {
					                               throw new RuntimeException(e);
					                            } catch (ParseException e) {
							e.printStackTrace();
						}
					      return list;
		          }
		
		
		
				
				  
				     protected static String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
									    String result = "";
									    event = eventReader.nextEvent();
									    if (event instanceof Characters) {
									                          result = event.asCharacters().getData();
									                  }
									    return result;
					               }
		
				     
				     
					  
				     protected static String getPictureURL(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException {
											Iterator<Attribute> attributes =  event.asStartElement().getAttributes();
										    while(attributes.hasNext()) {
														    	 Attribute attribute = attributes.next();
														    	 if(attribute.getName().getLocalPart().equals(attr_url))
														    		                  return attribute.getValue();
									                        }
									 	    return null;
						             }
				     
		
				       
				     protected static String formatDate(String date_value, String format) throws ParseException{
						    	      Date date = new SimpleDateFormat(format, Locale.ENGLISH).parse(date_value);
						    	      return new SimpleDateFormat("HH:mm dd/MM").format(date);
				    	         }
				     
			
				     
				     protected static String generateReference(News news, String source) {
								           String cat = news.getCategory().substring(0,3).toLowerCase();
								           String dat = news.getDate().replace(":", "").replace("/", "").replace(" ", "");
								           return cat + dat + source;
				                     }
	       
     }