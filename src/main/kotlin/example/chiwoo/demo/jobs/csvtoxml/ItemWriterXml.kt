package example.chiwoo.demo.jobs.csvtoxml

import javanet.staxutils.IndentingXMLEventWriter
import org.springframework.batch.item.xml.StaxEventItemWriter
import org.springframework.core.io.Resource
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import java.io.Writer
import javax.xml.stream.XMLEventWriter
import javax.xml.stream.XMLOutputFactory

class ItemWriterXml(marshaller: Jaxb2Marshaller?, outputXml: Resource?) : StaxEventItemWriter<ItemModel>() {

    override fun createXmlEventWriter(outputFactory: XMLOutputFactory, writer: Writer): XMLEventWriter {
        return IndentingXMLEventWriter(outputFactory.createXMLEventWriter(writer))
    }

    init {
        // version = "1.0"
        // encoding = "UTF-8"
        // rootTagName = ""
        // setName("")
        super.setMarshaller(marshaller!!)
        super.setResource(outputXml!!)
    }

}