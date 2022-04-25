package com.bot

import dev.kord.core.*
import dev.kord.core.event.message.*
import dev.kord.gateway.*

suspend fun main() {
    data class Category(var name: String, var items: Array<String>)
    val categories = arrayOf(Category("Cars", arrayOf("Maluch", "Syrena", "Trabant")),
                    Category("Bikes", arrayOf("Mountain", "Street", "Race")),
                    Category("Tea", arrayOf("Green", "Black", "White")))

    val kord = Kord("OTYyNDYxMDI1NTE5Njc3NTMy.YlH3rg.cLwW-k3bQjW_knIAmGm1yqRNaTk")

    kord.on<MessageCreateEvent> {
        if (message.author?.isBot != false)
            return@on

        when (message.content) {
            "!help" -> message.channel.createMessage("""
                Available commands:
                !categories
                !category {name}
            """.trimIndent())

            "!categories" -> {
                var data = "Available categories:\n"
                for (c in categories)
                    data += c.name + "\n"
                data += "Type !category {name} to get the list of item available in the category"
                message.channel.createMessage(data)
            }

            "!category Cars" -> {
                var data = "Available items:\n"
                for (i in categories[0].items)
                    data += "$i "
                message.channel.createMessage(data)
            }

            "!category Bikes" -> {
                var data = "Available items:\n"
                for (i in categories[1].items)
                    data += "$i "
                message.channel.createMessage(data)
            }

            "!category Tea" -> {
                var data = "Available items:\n"
                for (i in categories[2].items)
                    data += "$i "
                message.channel.createMessage(data)
            }

            else -> return@on
        }
    }

    kord.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}
