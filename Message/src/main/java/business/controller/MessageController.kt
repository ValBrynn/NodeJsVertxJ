package business.controller


import business.model.MessageModel
import business.service.MessageService

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.text.ParseException

@Path("/message")
class MessageController {
    @GET
    @Path("/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getReceivedMessages(@PathParam("userName") name: String): List<MessageModel>? {
        var result: List<MessageModel>? = null

        result = MessageService.getReceivedMessages(name)
        return result
    }


    @GET
    @Path("/sentMsg/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getSentMessages(@PathParam("userName") name: String): List<MessageModel>? {
        var result: List<MessageModel>? = null

        result = MessageService.getSentMessages(name)
        return result
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Throws(ParseException::class)
    fun post(m: MessageModel?): Response {
        if (m == null) return Response.status(400).build()
        return if (MessageService.add(m)) Response.status(201).build() else Response.status(400).build()
    }


}
