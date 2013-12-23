package ro.anproca.examples.ldapfilter;


public class LdapQuery {

    public LdapQuery present(String attributeName) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public LdapQuery approxMatch(String attributeName, String attributeValue) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public LdapQuery lessOrEqual(String attributeName, String attributeValue) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public LdapQuery greaterOrEqual(String attributeName, String attributeValue) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public LdapQuery equalityMatch(String attributeName, String attributeValue) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public LdapQuery and(Iterable<LdapQuery> andQueries) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public LdapQuery or(Iterable<LdapQuery> orQueries) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public LdapQuery not(LdapQuery negativeQuery) {
        throw new UnsupportedOperationException("Not implemented");
    }


}
