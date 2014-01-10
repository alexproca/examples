package ro.anproca.examples.ldapfilter.api;

public class AttributeValueAssertion {

    private String attributeDesc;
    private String assertionValue;

    public String getAttributeDesc() {
        return attributeDesc;
    }

    public void setAttributeDesc(String attributeDesc) {
        this.attributeDesc = attributeDesc;
    }

    public String getAssertionValue() {
        return assertionValue;
    }

    public void setAssertionValue(String assertionValue) {
        this.assertionValue = assertionValue;
    }

    @Override
    public String toString() {
        return attributeDesc + "=" + assertionValue;
    }
}
