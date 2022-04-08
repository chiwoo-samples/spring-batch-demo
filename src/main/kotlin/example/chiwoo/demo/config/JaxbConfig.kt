package example.chiwoo.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.oxm.jaxb.Jaxb2Marshaller
import javax.xml.bind.Marshaller

@Configuration
class JaxbConfig {

    @Bean
    fun jaxbMarshaller(): Jaxb2Marshaller {
        val props = HashMap<String, Any>()
        props[Marshaller.JAXB_FRAGMENT] = true
        props[Marshaller.JAXB_FORMATTED_OUTPUT] = true
        /*
         marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
         */
        val marshaller = Jaxb2Marshaller()
        marshaller.setPackagesToScan("example.chiwoo.demo.jobs")
        marshaller.setMarshallerProperties(props)
        // marshaller.setClassesToBeBound(ItemModel::class.java)
        return marshaller
    }

}