package ro.anproca.examples;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class MarshallAndUnmarshall {

	public static void main(String[] args) {
		try {
			JAXBContext jc = JAXBContext.newInstance(Shiporder.class);
			// Create marshaller
			Marshaller m = jc.createMarshaller();

			OutputStream os = new ByteArrayOutputStream();

			ObjectFactory factory = new ObjectFactory();

			Shiptotype type = factory.createShiptotype();

			type.setAddress("Antarctica");
			type.setCountry("No Country");
			type.setFullName("Polar Bear");
			type.setHomeCity("South Pole");

			ShipOrderClass shipOrder = factory.createShipOrderClass();

			shipOrder.setOrderid("256");
			shipOrder.setOrderperson("John Doe");
			shipOrder.setShipto(type);

			
			Shiporder test = factory.createShiporder();
			test.setContent(shipOrder);
			
			// Marshal object into file.

			m.marshal(test, os);

			System.out.println(os.toString());

			 Unmarshaller um = jc.createUnmarshaller();
			
			 // Unmarshal XML contents of the file myDoc.xml into your Java
			 
			 InputStream xmlStream = MarshallAndUnmarshall.class.getClassLoader().getResourceAsStream("serialized.xml");
			 
			 Shiporder unmarshalled = (Shiporder)um.unmarshal(xmlStream);
			 
			 System.out.println("Order id : " + unmarshalled.getContent().getOrderid());
			 System.out.println("Order person : " + unmarshalled.getContent().getOrderperson());
			 
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
