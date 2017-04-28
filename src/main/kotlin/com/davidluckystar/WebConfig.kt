package com.davidluckystar

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * Created by david on 3/6/2017.
 */
@Configuration
open class WebConfig : WebMvcConfigurerAdapter() {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        super.addResourceHandlers(registry)
        registry.addResourceHandler("/**")
                .addResourceLocations("file:/C:\\dev\\workspace\\eventy-ui\\")
                .addResourceLocations("file:/root/eventy-ui/");
    }

    override fun addViewControllers(registry: ViewControllerRegistry?) {
        registry?.addViewController("/")?.setViewName("forward:/index.html")
    }
}