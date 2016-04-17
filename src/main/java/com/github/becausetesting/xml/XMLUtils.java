/**
 * Project Name:becausejmeter
 * File Name:XMLUtils.java
 * Package Name:com.github.becausetesting.becausejmeter.utilities
 * Date:2016年4月5日下午9:19:14
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
*/
/**
 * Project Name:becausejmeter
 * File Name:XMLUtils.java
 * Package Name:com.github.becausetesting.becausejmeter.utilities
 * Date:2016年4月5日下午9:19:14
 * Copyright (c) 2016, alterhu2020@gmail.com All Rights Reserved.
 *
 */

package com.github.becausetesting.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * ClassName: XMLUtils Function: TODO ADD FUNCTION. Reason: TODO ADD REASON(可选).
 * date: 
 *
 * @author Administrator
 * @version
 * @since JDK 1.8
 */
public class XMLUtils {

	/**
	 * Parse:
	 *
	 * @author Administrator
	 * @param xmlfile
	 * @param nodename
	 * @since JDK 1.8
	 */
	public void SAXParse(File xmlfile, String nodename) {
		try {
			XMLParser parser = new XMLParser(nodename);
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			saxParser.parse(xmlfile, parser);
			
			parser.getResult();
			
		} catch (ParserConfigurationException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (SAXException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public class XMLParser extends DefaultHandler {

		private String nodename;
		private boolean findnode=false;
		private StringBuilder builder;

		public XMLParser(String nodename) {
			this.nodename = nodename;

		}

		/**
		 * TODO begin to parse the element.
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
		 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {

			// TODO Auto-generated method stub
			if (nodename.equalsIgnoreCase(qName)) {
				findnode = true;
				builder=new StringBuilder();
			}
		}

		/**
		 * Get the xml node value.
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {

			// TODO Auto-generated method stub
			String nodevalue = new String(ch,start,length);
			builder.append(nodevalue);
		}

		
		/**
		 * TODO
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
		 *      java.lang.String, java.lang.String)
		 */
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {

			// TODO find this node then exit parse the document
			if (nodename.equalsIgnoreCase(qName)) {
				findnode=false;
			}
		}

		
		/**
		 * TODO.
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
		 */
		@Override
		public void endDocument() throws SAXException {

			// TODO Auto-generated method stub
			super.endDocument();
		}
		
		
		/**
		 * getResult:
		 *
		 * @author Administrator
		 * @return
		 * @since JDK 1.8
		 */
		public String getResult(){
			return builder.toString();
		}
	}
}
