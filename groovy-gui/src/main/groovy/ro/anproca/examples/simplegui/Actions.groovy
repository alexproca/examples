package ro.anproca.examples.simplegui

import au.com.bytecode.opencsv.CSVReader

import java.math.RoundingMode

/**
 * Created by alex on 1/19/14.
 */
//@CompileStatic
class Actions {

    static String defaultVatValue = '24'
    public static Character SEPARATOR = ';'

    public static BigDecimal plafonMaxim = 135000

    public
    static List<BigDecimal> getBigDecimalValues(String noVATString, String inclVATString, String vatString) {

        BigDecimal vat = vatString ? vatString.trim() as BigDecimal : defaultVatValue as BigDecimal

        def values = []

        if (noVATString) {
            BigDecimal noVAT = noVATString.trim() as BigDecimal
            values = [noVAT, noVAT * (1 + vat / 100), noVAT * vat / 100]
        } else if (inclVATString) {
            BigDecimal inclVAT = inclVATString.trim() as BigDecimal
            values = [inclVAT / (1 + vat / 100), inclVAT, inclVAT * (1 / (100 / inclVAT + 1))]
        }

        values.collect({ BigDecimal value -> value.setScale(2, RoundingMode.HALF_UP) })

    }

    public static void analize(String filePath, String plafonMaximString, String tvaImplicit) {

        plafonMaxim = plafonMaximString ? plafonMaximString as BigDecimal : plafonMaximString
        defaultVatValue = tvaImplicit ? tvaImplicit : defaultVatValue

        def report = [:]

        def csvFile = new File(filePath)

        csvFile.withReader { Reader csvFileReader ->

            String header = csvFileReader.readLine()

            SEPARATOR = header.contains(',') ? ',' as Character : ';' as Character

            def (String cpvName, String productName, String vatName, String noVatName___, String inclVatName, nothing) = header.split("$SEPARATOR") + ['', '', '','', '', '']

            CSVReader reader = new CSVReader(csvFileReader, SEPARATOR);



            reader.readAll().each { String[] lineValues ->

                String cpv = lineValues[0]

                if(!cpv) return

                String vatValue = lineValues[2]
                String noVAT = lineValues[3]
                String inclVAT = lineValues[4]

                if(!noVAT && !inclVAT) return

                List<BigDecimal> bigDecimalValues = getBigDecimalValues(noVAT, inclVAT, vatValue)
                BigDecimal noVATDecimal = bigDecimalValues[0]
                BigDecimal inclVATDecimal = bigDecimalValues[1]

                Map<String, BigDecimal> previous = report[cpv]
                if (!previous) {
                    previous = [:]
                    previous.put(noVatName___, 0)
                    previous.put(inclVatName, 0)
                }

                Map<String, BigDecimal> actual = [:]
                actual.put(noVatName___, previous[noVatName___] + noVATDecimal)
                actual.put(inclVatName, previous[inclVatName] + inclVATDecimal)

                report[cpv] = actual
            }

            println '===================================================================================='
            println "         CPV      ${pL(noVatName___, 23)}   ${pL(inclVatName, 23)}      Depaseste   "
            println '===================================================================================='
            report.each { k, value ->
                BigDecimal noVatValue__ = value[noVatName___]
                BigDecimal includingVat = value[inclVatName]
                def d = { noVatValue__ > plafonMaxim ? '**********' : ' ' }
            println " ${pL(k, 11)}  ${pL(noVatValue__, 23)}  ${pL(includingVat, 23)}  ${pL(d(), 13)} "
            }
            println '===================================================================================='
        }
    }

    public static String padRight(s, int n) {
        return String.format("%1\$-" + n + "s", s + '');
    }

    public static String pL(s, int n) {
        return String.format("%1\$" + n + "s", s + '');
    }

}
