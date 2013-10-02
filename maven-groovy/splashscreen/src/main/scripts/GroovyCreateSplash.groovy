import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
                                
//Mind Windows
def baseDir = project.basedir.absolutePath.replace('\\', '/')

def svgPath = "${basedir}/src/main/graphics/splash.svg"

def root = new XmlParser().parse(svgPath)

def inkscape = new groovy.xml.Namespace('http://www.inkscape.org/namespaces/inkscape', 'inkscape')

root?.g?.grep { it.attribute(inkscape.label) == 'version'}?.text[0]?.tspan[0]?.value = project.version

def resourcesDir = "${baseDir}/target/resources/"

def outputPath  = "${resourcesDir}sigla.png"

new File(resourcesDir).mkdirs()

StringWriter writer = new StringWriter()
def printer = new XmlNodePrinter(new PrintWriter(writer))

printer.setNamespaceAware(true)
printer.setPreserveWhitespace(true)
printer.print(root)

String result = writer.toString()

//new File("${resourcesDir}sigla.svg").setText(result)


def inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"))

// create the transcoder output
def outFile = new FileOutputStream(outputPath)

// create a JPEG transcoder
def transcoder = new PNGTranscoder();

// convert image to jpeg bytes
transcoder.transcode(new TranscoderInput(inputStream), new TranscoderOutput(outFile));