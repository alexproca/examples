package ro.anproca.examples.ldapfilter;

import org.junit.BeforeClass;
import org.junit.Test;
import ro.anproca.examples.ldapfilter.model.AttributeValueAssertion;
import ro.anproca.examples.ldapfilter.model.Filter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

public class FilterTest {

    @BeforeClass
    public static void startServer() {
//        EmbeddedADSVer157.main(null);
    }

    @Test
    public void testFilter() throws Exception {

        Filter filter = new Filter()
                .withAnd(
                        new Filter()
                                .withEqualityMatch(
                                        new AttributeValueAssertion()
                                                .withAttributeDesc("one").withAssertionValue("1")
                                )
                )
                .withAnd(
                        new Filter()
                                .withEqualityMatch(
                                        new AttributeValueAssertion()
                                                .withAttributeDesc("one").withAssertionValue("1")
                                )
                );

        JAXBContext context = JAXBContext.newInstance(ro.anproca.examples.ldapfilter.model.LdapQuery.class);

        Marshaller m = context.createMarshaller();

        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_FRAGMENT, true);

        StringWriter out = new StringWriter();

        m.marshal(new ro.anproca.examples.ldapfilter.model.LdapQuery().withFilter(filter), out);

        System.out.println(out);

    }

}
