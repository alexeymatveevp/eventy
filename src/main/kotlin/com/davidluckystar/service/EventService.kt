package com.davidluckystar.service

import com.davidluckystar.DateUtils
import com.davidluckystar.model.EventType
import com.davidluckystar.model.EventTypes
import com.davidluckystar.model.GroupEvent
import com.davidluckystar.model.GroupEventWithId
import com.fasterxml.jackson.databind.ObjectMapper
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.sort.SortOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.time.LocalDate
import java.util.*

/**
 * Created by ksavina on 1/30/2018.
 */
@Component
class EventService {

    @Autowired
    lateinit var client: TransportClient

    @Autowired
    lateinit var om: ObjectMapper

    @RequestMapping("/event", method = arrayOf(RequestMethod.POST))
    fun createEvent(@RequestBody ge: GroupEvent): String {
        ge.creationDate = Date()
        if (ge.date == null) {
            ge.date = ge.creationDate
        }
        val jsonEvent = om.writeValueAsString(ge)
        val resp = client.prepareIndex("eventy", "event").setSource(jsonEvent).execute().get()
        return resp.id
    }

    @RequestMapping("/event", method = arrayOf(RequestMethod.PUT))
    fun updateEvent(@RequestBody ge: GroupEventWithId): String {
        if (ge.creationDate == null) {
            throw Exception("no creation date from ui")
        }
        val jsonEvent = om.writeValueAsString(ge)
        val resp = client.prepareUpdate("eventy", "event", ge.id).setDoc(jsonEvent).get()
        return resp.id
    }

    @RequestMapping("/event/{id}", method = arrayOf(RequestMethod.DELETE))
    fun updateEvent(@PathVariable("id") id: String): String {
        val resp = client.prepareDelete("eventy", "event", id).get()
        return resp.id
    }

    @RequestMapping("/event", method = arrayOf(RequestMethod.GET))
    fun listEvents(): List<GroupEventWithId> {
//        client.prepareGet("")
//        return repo.findAll().asSequence().toList().toTypedArray()
//        val getResp = client.prepareGet("eventy", "event", "AVqgClh8pmm6Cbzf5uqa").get()
//        val sourceAsString = getResp.sourceAsString
//        val event = om.readValue(sourceAsString, GroupEvent::class.java)

        val query = QueryBuilders.boolQuery()
                .should(QueryBuilders.rangeQuery("date")
                        .to(DateUtils.ELASTIC_DATE_FORMAT.format(LocalDate.now())))
                .should(QueryBuilders.rangeQuery("start")
                        .to(DateUtils.ELASTIC_DATE_FORMAT.format(LocalDate.now())))
                .should(QueryBuilders.rangeQuery("end")
                        .to(DateUtils.ELASTIC_DATE_FORMAT.format(LocalDate.now())))
                .minimumShouldMatch(1)
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
        return hits.map f@{ hit ->
            val sas = om.readValue(hit.sourceAsString, GroupEventWithId::class.java)
            sas.id = hit.id
            return@f sas
        }
    }

    @RequestMapping("/event-future", method = arrayOf(RequestMethod.GET))
    fun futureEvents(): List<GroupEventWithId> {
        val query = QueryBuilders.boolQuery()
                .should(QueryBuilders.rangeQuery("date")
                        .from(DateUtils.ELASTIC_DATE_FORMAT.format(LocalDate.now())))
                .should(QueryBuilders.rangeQuery("start")
                        .from(DateUtils.ELASTIC_DATE_FORMAT.format(LocalDate.now())))
                .should(QueryBuilders.rangeQuery("end")
                        .from(DateUtils.ELASTIC_DATE_FORMAT.format(LocalDate.now())))
                .minimumShouldMatch(1)
        val resp = client.prepareSearch("eventy")
                .setTypes("event")
                .setSize(10000)
                .setQuery(query)
                .addSort("date", SortOrder.ASC)
                .addSort("start", SortOrder.ASC)
                .addSort("end", SortOrder.ASC)
                .get()
        val hits = resp.hits.hits
        return hits.map f@{ hit ->
            val sas = om.readValue(hit.sourceAsString, GroupEventWithId::class.java)
            sas.id = hit.id
            return@f sas
        }
    }

    @RequestMapping("/event-type", method = arrayOf(RequestMethod.GET))
    fun listEventTypes(): List<EventType> {
        return EventTypes.types.asList()
    }

    @RequestMapping("/insert-random", method = arrayOf(RequestMethod.GET))
    fun randomEvent(request: GroupEvent) {
//        var sampleEvent: GroupEvent = GroupEvent("WOMAN_PERIOD", Date(), Date(), Date(), Date(), "Great things", "Lazy fox jumps over the foxy dog", 42.2f, 5, listOf(
//                Event("WOMAN_PERIOD_DAY", Date(), Date(), Date(), Date(), "lalalalala", "da da da da da", 12.2f, 2)
//        ))
//        val jsonEvent = om.writeValueAsString(sampleEvent)
//        client.prepareIndex("eventy", "event").setSource(jsonEvent).execute().get()
    }

}