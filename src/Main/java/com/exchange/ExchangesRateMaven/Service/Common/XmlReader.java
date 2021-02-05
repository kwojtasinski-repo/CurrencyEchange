package com.exchange.ExchangesRateMaven.Service.Common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlReader { // there is nothing like static class only methods can be static
	public static BigDecimal getCurrencyRateFromXml(String txt) throws Exception {
		// TODO Auto-generated method stub
		BigDecimal rate = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder;
	    Document document = null;
		try {
			builder = factory.newDocumentBuilder();
			InputStream inputStream = new ByteArrayInputStream(txt.getBytes());
		    document = builder.parse(inputStream);
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
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		System.out.println(root.getNodeName());

		//all currencies
		NodeList nList = document.getElementsByTagName("Rate");
		for (int i = 0; i < nList.getLength(); i++){
			Node node = nList.item(i);
			System.out.println("");    //Just a separator
			if (node.getNodeType() == Node.ELEMENT_NODE){
			    //Print each employee's detail
			    Element element = (Element) node;
			    rate = new BigDecimal(element.getElementsByTagName("Mid").item(0).getTextContent());
			    System.out.println("Mid : " + rate);
			}
		}
		if(rate == null) {
			throw new Exception("Check xml format : " + txt);
		}
		
		return rate;
	}
}
