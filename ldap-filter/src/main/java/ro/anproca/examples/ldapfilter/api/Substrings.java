package ro.anproca.examples.ldapfilter.api;

import java.util.List;

public class Substrings {

    private String subInitial;
    private String subFinal;
    private List<String> subAny;

    public String getSubInitial() {
        return subInitial;
    }

    public void setSubInitial(String subInitial) {
        this.subInitial = subInitial;
    }

    public String getSubFinal() {
        return subFinal;
    }

    public void setSubFinal(String subFinal) {
        this.subFinal = subFinal;
    }

    public List<String> getSubAny() {
        return subAny;
    }

    public void setSubAny(List<String> subAny) {
        this.subAny = subAny;
    }
}
