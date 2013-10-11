package ro.anproca.examples;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MarshallAndUnmarshall
{

	public static void main(String[] args)
	{
		JAXBContext jc = JAXBContext.newInstance("myPackageName");
		//Create marshaller
		Marshaller m = jc.createMarshaller();
		//Marshal object into file.
		m.marshal(myJAXBObject, myOutputStream);
		
		Unmarshaller um = jc.createUnmarshaller();
		//Unmarshal XML contents of the file myDoc.xml into your Java 
		MyJAXBObject myJAXBObject = (MyJAXBObject) 
		um.unmarshal(new java.io.FileInputStream( "myDoc.xml" ));
		
	}
	
}
