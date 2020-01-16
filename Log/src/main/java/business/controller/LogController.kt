package business.controller

import business.model.LogModel
import business.service.LogService


import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.text.ParseException

@Path("/log")
class LogController {


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Throws(ParseException::class)
    fun post(log: LogModel?): Response {
        if (log == null) return Response.status(400).build()
        return if (LogService.add(log)) Response.status(201).build() else Response.status(400).build()
    }

    @GET
    @Path("/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(@PathParam("userName") user: String): List<LogModel>? {

        var result: List<LogModel>? = null
        result = LogService.getAll(user)
        return result

    }


    @GET
    @Path("/{userName}/{findUser}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLogList(@PathParam("userName") name: String, @PathParam("findUser") findUser: String): List<LogModel>? {
        var result: List<LogModel>? = null
        result = LogService.getAll(findUser)
        return result
    }


}
