package ro.anproca.examples.ldapfilter.api;

public class MatchingRuleAssertion {

    private String matchingRule;
    private String type;
    private String matchValue;
    private Boolean dnAttributes;

    public String getMatchingRule() {
        return matchingRule;
    }

    public void setMatchingRule(String matchingRule) {
        this.matchingRule = matchingRule;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMatchValue() {
        return matchValue;
    }

    public void setMatchValue(String matchValue) {
        this.matchValue = matchValue;
    }

    public Boolean getDnAttributes() {
        return dnAttributes;
    }

    public void setDnAttributes(Boolean dnAttributes) {
        this.dnAttributes = dnAttributes;
    }
}
