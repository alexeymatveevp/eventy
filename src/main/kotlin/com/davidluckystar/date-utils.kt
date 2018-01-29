package com.davidluckystar

import java.time.format.DateTimeFormatter

/**
 * Created by ksavina on 5/8/2017.
 */
open class DateUtils {
    companion object {
        var ELASTIC_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }
}