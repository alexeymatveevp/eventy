package com.davidluckystar.model

import java.util.*

class EventTypes {
    companion object {
        val types: Array<EventType> = arrayOf(
                EventType("EVENT", "record", "#b7b7b7",
                        listOf("date", "headline", "text", "images"), true, null, null, null, null),

                EventType("WOMAN_PERIOD", "tint", "#e05d63",
                        listOf("start", "end", "text", "events"), true, null, null,
                        listOf("WOMAN_PERIOD_DAY"), null),
                EventType("WOMAN_PERIOD_DAY", "tint", "#e05d63",
                        listOf("date", "severity", "text"), false, null, null, null, null),

                EventType("HEALTH_PROBLEMS", "plus", "#a50707",
                        listOf("date", "start", "end", "severity", "images", "selectedItems"), true, null,
                        listOf("HEADACHE", "STOMACH", "BACK", "RASH", "COLD", "PENIS_TROUBLE", "TEETH", "WOMAN_TROUBLE", "HANDS", "LEGS"),
                        listOf("HEALTH_PROBLEMS_PILLS", "HEALTH_PROBLEMS_DIET"),
                        "health-icon.png"),
                EventType("HEALTH_PROBLEMS_PILLS", "", "",
                        listOf("text", "measure"), false, null, null, null, null),
                EventType("HEALTH_PROBLEMS_DIET", "", "",
                        listOf("text"), false, null, null, null, null),

                EventType("WEIGHT_SAMPLE", "scale", "#688ee2",
                        listOf("date", "measure"), true, null, null, null, null),

                EventType("PARTY", "glass", "#9a69f9",
                        listOf("date", "headline", "text", "severity", "images"), true, null, null, null, null),

                EventType("INSPIRATION", "info-sign", "#000000",
                        listOf("date", "headline", "text", "images"), true, null, null, null, null),

                EventType("SPORT", "play-circle", "#0f5398",
                        listOf("date", "text", "user", "selectedItems"), true, null,
                        listOf("EASY", "HARD"),
                        listOf("SPORT_PUSHUPS", "SPORT_PULLUPS", "SPORT_RUN", "SPORT_JYM"),
                        "sport.jpg"),
                EventType("SPORT_PUSHUPS", "", "",
                        listOf("measure"), false, null, null, null, null),
                EventType("SPORT_PULLUPS", "", "",
                        listOf("measure"), false, null, null, null, null),
                EventType("SPORT_RUN", "", "",
                        listOf("measure"), false, null, null, null, null),
                EventType("SPORT_JYM", "", "",
                        listOf("severity"), false, null, null, null, null)
        )
    }
}

class GroupEventWithId : GroupEvent() {
    lateinit var id: String
}

open class GroupEvent : Event() {
    var events: List<Event>? = null
}

open class Event {
    lateinit var type: String
    var creationDate: Date? = null
    var date: Date? = null
    var start: Date? = null
    var end: Date? = null
    var headline: String? = null
    var text: String? = null
    var measure: Float? = null
    var severity: Int? = null
    var images: List<String>? = null
    var selectedItems: List<String>? = null
    var user: String? = null
    var oneItem: String? = null
}

open class EventType() {
    var type: String = "TEXT"
    var icon: String = "check"
    var color: String = "#5bc0de"
    var fields: List<String> = listOf("date", "text")
    var main: Boolean = false // identifies is it a main event or a sub event
    var subEventType: String? = null // deprecated
    var possibleValues: List<String>? = null
    var subEventTypes: List<String>? = null
    var background: String? = null
    constructor(type: String, icon: String, color: String, fields: List<String>, main: Boolean, subEventType: String?, possibleValues: List<String>?, subEventTypes: List<String>?, background: String?): this() {
        this.type = type
        this.icon = icon
        this.color = color
        this.fields = fields
        this.main = main
        this.subEventType = subEventType
        this.possibleValues = possibleValues
        this.subEventTypes = subEventTypes
        this.background = background
    }
}