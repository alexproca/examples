/**
 * Original zip file: http://simap.europa.eu/news/new-cpv/cpv_2008_xml.zip
 */

File xmlFile = new File('cpv_2008.xml')

def entries = new XmlSlurper().parse(xmlFile)

entries.'CPV'.each { entry ->
    println "<Code>${entry.@CODE}</Code>"
    entry.'TEXT'.each { text ->
        println "<Description language=\"${text.@LANG}\">${text.text()}</Description>"
    }
}