package ro.anproca.examples.ldapfilter.api;

import java.io.UnsupportedEncodingException;

public class Util {

    /**
     * Escapes any special chars (RFC 4515) from a string representing a
     * a search filter assertion value.
     *
     * @param input The input string.
     * @return A assertion value string ready for insertion into a
     *         search filter string.
     */
    public static String sanitize(final String input) {

        StringBuilder s = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {

            char c = input.charAt(i);

            switch (c) {
                case '*':
                    // escape asterisk
                    s.append("\\2a");
                    break;
                case ')':
                    // escape right parenthesis
                    s.append("\\29");
                    break;
                case '\\':
                    // escape backslash
                    s.append("\\5c");
                    break;
                case '\u0000':
                    // escape NULL char
                    s.append("\\00");
                    break;
                default:
                    if (c <= 0x7f) {
                        // regular 1-byte UTF-8 char
                        s.append(String.valueOf(c));
                    } else if (c >= 0x080) {

                        // higher-order 2, 3 and 4-byte UTF-8 chars

                        try {
                            byte[] utf8bytes = String.valueOf(c).getBytes("UTF8");

                            for (byte b : utf8bytes)
                                s.append(String.format("\\%02x", b));

                        } catch (UnsupportedEncodingException e) {
                            // ignore
                        }
                    }
                    break;
            }
        }
        return s.toString();
    }
}
