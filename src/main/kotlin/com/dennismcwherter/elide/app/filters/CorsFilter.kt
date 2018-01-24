package com.dennismcwherter.elide.app.filters

import com.dennismcwherter.elide.app.security.context.ApplicationSecurityContext
import com.yahoo.elide.core.DataStore
import com.yahoo.elide.datastores.hibernate5.HibernateSessionFactoryStore
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject
import javax.ws.rs.WebApplicationException
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.container.ContainerResponseContext
import javax.ws.rs.container.ContainerResponseFilter
import com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS
import com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN
import com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS
import com.sun.corba.se.spi.presentation.rmi.StubAdapter.request
import com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS
import lombok.extern.slf4j.Slf4j


/**
 * User authentication filter
 */
@Slf4j
class CorsFilter : ContainerResponseFilter {
  val ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin"
  val ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods"
  val ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers"
  val ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials"
  val ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers"


  override fun filter(requestContext: ContainerRequestContext?, responseContext: ContainerResponseContext?) {
    requestContext?.let { req ->
      responseContext?.let { res ->
        println("Adding CORS headers")
        val headers = res.headers
        val requestedHeaders = req.getHeaderString("Access-Control-Request-Headers")

        // Responses will vary depending on the origin header
        headers.putSingle("Vary", "Origin")
        // These are the methods we allow
        headers.putSingle(ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PATCH,DELETE")
        // allow all requested headers, we validate the headers we use
        headers.putSingle(ACCESS_CONTROL_ALLOW_HEADERS, requestedHeaders ?: "")
        // disallow cookies on XHR
        headers.putSingle(ACCESS_CONTROL_ALLOW_ORIGIN, "*")
      }
    }
  }
}
