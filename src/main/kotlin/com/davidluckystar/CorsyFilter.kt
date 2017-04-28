package com.davidluckystar

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by david on 3/6/2017.
 */
@Component
@Order(org.springframework.core.Ordered.LOWEST_PRECEDENCE)
class CorsyFilter : Filter {

    override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
        if (servletResponse is HttpServletResponse) {
            servletResponse.setHeader("Access-Control-Allow-Origin", "*")
            servletResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS") // will need to enable other methods when/as implemented
            servletResponse.setHeader("Access-Control-Max-Age", "3600")
            if (servletRequest is HttpServletRequest) {
                servletResponse.setHeader("Access-Control-Allow-Headers",
                        servletRequest.getHeader("Access-Control-Request-Headers"))
            }
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }

    override fun destroy() {

    }

    override fun init(filterConfig: FilterConfig) {

    }

}