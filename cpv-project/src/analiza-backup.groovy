import java.math.RoundingMode

def csvFile = new File('sample.csv')
String defaultVatValue = '24'

def getBigDecimalValues = { String noVATString, String inclVATString, String onlyVATString, String vatString ->

    BigDecimal vat = vatString as BigDecimal

    def values = []

    if (noVATString) {
        BigDecimal noVAT = noVATString as BigDecimal
        values = [noVAT, noVAT * (1 + vat / 100), noVAT * vat / 100]
    } else if (inclVATString) {
        BigDecimal inclVAT = inclVATString as BigDecimal
        values = [inclVAT / (1 + vat / 100), inclVAT, inclVAT * (1 / (100 / inclVAT + 1))]
    } else if (onlyVATString) {
        BigDecimal onlyVAT = onlyVATString as BigDecimal
        values = [onlyVAT * 100 / vat, onlyVAT * (100 + vat) / vat, onlyVAT]
    }

    values.collect({ BigDecimal value -> value.setScale(2, RoundingMode.HALF_UP) })

}

def report = [:]

csvFile.readLines().tail().each { String line ->

    def (cpv, name, localVatValue, noVAT, inclVAT, onlyVAT, nothing) = line.split(',') + ['', '', '', '', '', '', '', '', '']

    def (noVATDecimal, inclVATDecimal, onlyVATDecimal) = getBigDecimalValues(noVAT, inclVAT, onlyVAT, localVatValue ?: defaultVatValue)

    def previous = report[cpv]
    if(!previous)
    {
        previous = ['noVAT': 0, 'inclVAT': 0, 'onlyVAT': 0]
    }

    report[cpv] = ['noVAT': previous['noVAT'] + noVATDecimal, 'inclVAT': previous['inclVAT'] + inclVATDecimal, 'onlyVAT': previous['onlyVAT'] + onlyVATDecimal]
}

report.each {key, value ->

    println "$key : $value"
}
