package ru.gui.services.integration

import android.os.AsyncTask
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import ru.gui.entity.User
import ru.gui.services.integration.RequestTemplate
import java.net.URL

class SendCredentialToServer(private val username: String, private val password: String, var url: URL){




    fun generateJsonTo(token: String): String {

        val user =User(username,password)
        var objectMapper = ObjectMapper()
        var rootNode: ObjectNode = objectMapper.createObjectNode()

        rootNode.put("username",username)
        rootNode.put("password",password)
        rootNode.put("idFirebaseMessagingCloud",token)


        return  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode)

    }









}