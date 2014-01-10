package ro.anproca.examples.ldapfilter.api;

import java.util.List;

    public class Filter {

        private List<Filter> and;
        private List<Filter> or;
        private Filter not;
        private AttributeValueAssertion equalityMatch;
        private SubstringFilter substrings;
        private AttributeValueAssertion greaterOrEqual;
        private AttributeValueAssertion lessOrEqual;
        private String present;
        private AttributeValueAssertion approxMatch;
        private MatchingRuleAssertion extensibleMatch;

        public List<Filter> getAnd() {
            return and;
        }

        public void setAnd(List<Filter> and) {
            this.and = and;
        }

        public List<Filter> getOr() {
            return or;
        }

        public void setOr(List<Filter> or) {
            this.or = or;
        }

        public Filter getNot() {
            return not;
        }

        public void setNot(Filter not) {
            this.not = not;
        }

        public AttributeValueAssertion getEqualityMatch() {
            return equalityMatch;
        }

        public void setEqualityMatch(AttributeValueAssertion equalityMatch) {
            this.equalityMatch = equalityMatch;
        }

        public SubstringFilter getSubstrings() {
            return substrings;
        }

        public void setSubstrings(SubstringFilter substrings) {
            this.substrings = substrings;
        }

        public AttributeValueAssertion getGreaterOrEqual() {
            return greaterOrEqual;
        }

        public void setGreaterOrEqual(AttributeValueAssertion greaterOrEqual) {
            this.greaterOrEqual = greaterOrEqual;
        }

        public AttributeValueAssertion getLessOrEqual() {
            return lessOrEqual;
        }

        public void setLessOrEqual(AttributeValueAssertion lessOrEqual) {
            this.lessOrEqual = lessOrEqual;
        }

        public String getPresent() {
            return present;
        }

        public void setPresent(String present) {
            this.present = present;
        }

        public AttributeValueAssertion getApproxMatch() {
            return approxMatch;
        }

        public void setApproxMatch(AttributeValueAssertion approxMatch) {
            this.approxMatch = approxMatch;
        }

        public MatchingRuleAssertion getExtensibleMatch() {
            return extensibleMatch;
        }

        public void setExtensibleMatch(MatchingRuleAssertion extensibleMatch) {
            this.extensibleMatch = extensibleMatch;
        }
    }
