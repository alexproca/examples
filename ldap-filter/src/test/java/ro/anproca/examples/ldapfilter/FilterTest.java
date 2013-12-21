package ro.anproca.examples.ldapfilter;

import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

public class FilterTest {

    @BeforeClass
    public static void startServer()
    {
        EmbeddedADSVer157.main(null);
    }

    @Test
    public void testFilter() throws Exception {

        ObjectFactory factory = new ObjectFactory();

        Filter filter = factory.createFilter();


        Filter.And and = factory.createFilterAnd();

    }

    @Test
    public void testDSL() throws Exception {


        System.out.println("ok");
//        Splitter.on(' ').trimResults();

//        FluentIterable.from(Collections.emptyList()).anyMatch(null);

    }
}
