package com.davidluckystar

import com.davidluckystar.model.Event
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.transport.client.PreBuiltTransportClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetAddress
import java.util.*
import javax.annotation.PostConstruct

/**
 * Created by david on 3/5/2017.
 */
@Configuration
open class Startup {

    @Bean
    open fun transportClient(): TransportClient {
        return client
    }

    @Bean
    open fun objectMapper(): ObjectMapper {
        val om: ObjectMapper = jacksonObjectMapper()
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        return om
    }

    @PostConstruct
    fun initIndex() {
//        var sampleEvent: Event = Event(Date(), "Big things", "something is happening", 42.2f, 5)
//        val jsonEvent = objectMapper().writeValueAsString(sampleEvent)
//        client.prepareIndex("eventy", "event").setSource(jsonEvent).execute().get()
    }

    companion object {
        lateinit var client: TransportClient
        init {
//            val settings: Settings = Settings.builder().put("cluster.name", "elasticsearch").build()
//            client = PreBuiltTransportClient(settings)
            client = PreBuiltTransportClient(Settings.EMPTY)
//            client.addTransportAddress(InetSocketTransportAddress(InetAddress.getByName("localhost"), 9200))
            client.addTransportAddress(InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))
        }
    }
}