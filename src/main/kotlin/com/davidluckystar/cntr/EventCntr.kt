package com.davidluckystar.cntr

import com.davidluckystar.model.EventType
import com.davidluckystar.model.EventTypes
import com.davidluckystar.model.GroupEvent
import com.davidluckystar.model.GroupEventWithId
import com.davidluckystar.service.EventService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by david on 3/5/2017.
 */
@RestController
class EventCntr {

    @Autowired
    lateinit var eventService: EventService

    @RequestMapping("/event", method = arrayOf(RequestMethod.POST))
    fun createEvent(@RequestBody ge: GroupEvent): String {
        return eventService.createEvent(ge)
    }

    @RequestMapping("/event", method = arrayOf(RequestMethod.PUT))
    fun updateEvent(@RequestBody ge: GroupEventWithId): String {
        return eventService.updateEvent(ge)
    }

    @RequestMapping("/event/{id}", method = arrayOf(RequestMethod.DELETE))
    fun updateEvent(@PathVariable("id") id: String): String {
        return eventService.updateEvent(id)
    }

    @RequestMapping("/event", method = arrayOf(RequestMethod.GET))
    fun listEvents(@RequestParam(value = "size", required = false) size: Int?,
                   @RequestParam(value = "type", required = false) type: String?,
                   @RequestParam(value = "user", required = false) user: String?): List<GroupEventWithId> {
        return eventService.listEvents(size, type, user)
    }

    @RequestMapping("/event-future", method = arrayOf(RequestMethod.GET))
    fun futureEvents(): List<GroupEventWithId> {
        return eventService.futureEvents()
    }

    @RequestMapping("/event-type", method = arrayOf(RequestMethod.GET))
    fun listEventTypes(): List<EventType> {
        return EventTypes.types.asList()
    }

    @RequestMapping("/insert-random", method = arrayOf(RequestMethod.GET))
    fun randomEvent(request: GroupEvent) {
        eventService.randomEvent(request)
    }
}