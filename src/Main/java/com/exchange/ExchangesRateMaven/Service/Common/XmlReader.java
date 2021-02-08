package com.exchange.ExchangesRateMaven.Service.Common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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
	
	public static void main(String[] args) throws Exception {
	    XmlMapper xmlMapper = new XmlMapper();
	    SimpleBean value = xmlMapper.readValue("<SimpleBean><x>1</x><y>2</y></SimpleBean>", SimpleBean.class);
	    System.out.println(value.getX());
		//System.out.println(loadXMLToObject("<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Table>A</Table><Currency>frank szwajcarski</Currency><Code>CHF</Code><Rates><Rate><No>024/A/NBP/2021</No><EffectiveDate>2021-02-05</EffectiveDate><Mid>4.1590</Mid></Rate></Rates></ExchangeRatesSeries>"));
	    ExchangeRatesSeriesXml ers = xmlMapper.readValue("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n"
	    		+ "<ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\r\n"
	    		+ "                     xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n"
	    		+ "	<Table>A</Table>\r\n"
	    		+ "	<Currency>frank szwajcarski</Currency>\r\n"
	    		+ "	<Code>CHF</Code>\r\n"
	    		+ "	<Rates>\r\n"
	    		+ "		<Rate>\r\n"
	    		+ "			<No>024/A/NBP/2021</No>\r\n"
	    		+ "			<EffectiveDate>2021-02-05</EffectiveDate>\r\n"
	    		+ "			<Mid>4.1590</Mid>\r\n"
	    		+ "		</Rate>\r\n"
	    		+ "	</Rates>\r\n"
	    		+ "</ExchangeRatesSeries>\r\n"
	    		+ "", ExchangeRatesSeriesXml.class);
	    for(RatesXml rate : ers.getRates()) {
	    	System.out.println(rate.getNo() + ";  " + rate.getEffectiveDate() + ";  " + rate.getMid());
	    }
	}
	
	public static Object loadXMLToObject(String xml) throws Exception
	{
	    JAXBContext jaxbContext = JAXBContext.newInstance(Object.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	   //jaxbUnmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	    StringReader reader = new StringReader(xml);
	    Object object = (Object) jaxbUnmarshaller.unmarshal(reader);

	    return object;
	}
}
