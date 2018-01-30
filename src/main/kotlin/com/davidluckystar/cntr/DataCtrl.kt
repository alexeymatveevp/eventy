package com.davidluckystar.cntr

import com.davidluckystar.DateUtils
import com.davidluckystar.model.GroupEvent
import com.davidluckystar.model.GroupEventWithId
import com.davidluckystar.service.EventService
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.io.IOUtils
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.sort.SortOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.FileOutputStream
import java.time.Instant
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

/**
 * Created by ksavina on 1/30/2018.
 */
@RestController
class DataCtrl {

    @Value("\${custom.dump.location}")
    val dumpLocation: String? = ""

    @Autowired
    lateinit var client: TransportClient

    @Autowired
    lateinit var om: ObjectMapper

    @Autowired
    lateinit var eventService: EventService

    @RequestMapping("/data/populate", method = arrayOf(RequestMethod.POST))
    fun populate(@RequestBody groupEvents: List<GroupEvent>): String {
//        val resourceAsStream = this.javaClass.classLoader.getResourceAsStream("export_29_jan_2018.json")
//        val toString = IOUtils.toString(resourceAsStream, "UTF-8")
//        println(toString)
//        val readValue = om.readValue<List<GroupEvent>>(toString)
        groupEvents.forEach {
            val jsonEvent = om.writeValueAsString(it)
            client.prepareIndex("eventy", "event").setSource(jsonEvent).execute().get()
        }
        return "ok" + System.currentTimeMillis()
    }

    @RequestMapping("/data/dump", method = arrayOf(RequestMethod.GET))
    fun dump(): String {
        // get all events with match all query
        val query = QueryBuilders.matchAllQuery()
        val resp = client.prepareSearch("eventy")
                .setTypes("event")
                .setSize(10000)
                .setQuery(query)
                .addSort("date", SortOrder.DESC)
                .addSort("start", SortOrder.DESC)
                .addSort("creationDate", SortOrder.DESC)
                .addSort("end", SortOrder.DESC)
                .get()
        val hits = resp.hits.hits
        val allEvents = hits.map f@ { hit ->
            val sas = om.readValue(hit.sourceAsString, GroupEventWithId::class.java)
            sas.id = hit.id
            return@f sas
        }

        allEvents.forEach {
            if (it.date == null) {
                it.date = it.creationDate
            }
        }

        val json = om.writeValueAsString(allEvents)
        val now = LocalDate.now()
        val filename = "export_" + now.dayOfMonth + "_" + now.month.getDisplayName(TextStyle.SHORT, Locale.getDefault()) + "_" + now.year + ".json"
        val fileAbsolutePath = dumpLocation + File.separator + filename
        val fos = FileOutputStream(File(fileAbsolutePath))
        IOUtils.write(json, fos, "UTF-8")
        fos.close()
        return fileAbsolutePath
    }

}