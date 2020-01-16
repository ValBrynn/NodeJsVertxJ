package business.controller
import business.model.UserModel
import business.service.UserService

import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/user")
class UserController {

    val all: List<UserModel>?
        @GET
        @Path("/")
        @Produces(MediaType.APPLICATION_JSON)
        get() = UserService.getAll()

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    fun post(user: UserModel?): Response {
        if (user == null) return Response.status(400).build()
        return if (UserService.add(user)) Response.status(201).build() else Response.status(400).build()
    }

    @GET
    @Path("/{userName}/{password}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUser(@PathParam("userName") userName: String?, @PathParam("password") password: String?): Response? {
        if (userName == null || password == null) {
            return null
        }
        val result = UserService.getUser(userName, password) ?: return Response.status(400).build()
        return Response.status(200).build()
    }

    @GET
    @Path("/{findUser}")
    @Produces(MediaType.APPLICATION_JSON)
    fun findUser(@PathParam("findUser") findUser: String?): Response? {
        if (findUser == null) {
            return null
        }
        val result = UserService.findUser(findUser)
        return if (result == false) {
            Response.status(400).build()
        } else Response.status(200).build()
    }

}