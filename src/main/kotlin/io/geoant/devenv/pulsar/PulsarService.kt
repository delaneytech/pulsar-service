package io.geoant.devenv.pulsar

import org.apache.pulsar.PulsarStandalone
import org.apache.pulsar.PulsarStandaloneBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.nio.charset.Charset
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class PulsarService {

    @Value("\${server.port}")
    private val port: Int? = null

    val baseDir = File("temp", "pulsar")
    val srv: PulsarStandalone = PulsarStandaloneBuilder.instance().withZkDir(File(baseDir, "zookeeper").absolutePath)
        .withBkDir(File(baseDir, "bookkeeper").absolutePath).build()

    @PostConstruct
    fun startup() {
        if (!baseDir.exists() && !baseDir.mkdirs()) {
            println("Unable to create directory")
        }
        val confFile = File(baseDir, "embedded-pulsar.conf")
        if (confFile.exists() && !confFile.delete()) {
            println("Unable to delete existing pulsar configuration")
        }
        confFile.createNewFile()
        confFile.writeText("range.store.dirs=${srv.bkDir}", Charset.forName("UTF-8"))
        confFile.writeText("webServicePort=${port}")
        confFile.deleteOnExit()
        srv.config.webServicePort = Optional.ofNullable(port)
        srv.configFile = confFile.absolutePath
        srv.start()
    }

    @PreDestroy
    fun shutdown() {
        srv.close()
    }
}