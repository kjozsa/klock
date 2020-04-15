package klock

import io.javalin.Javalin
import io.javalin.plugin.rendering.vue.VueComponent
import io.javalin.websocket.WsContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class Server {
    private val websockets = mutableListOf<WsContext>()

    fun start() {
        val app = Javalin
            .create { config ->
                config.addStaticFiles("/web")
                config.enableWebjars()
            }
            .ws("/websocket") { ws ->
                ws.onConnect { ctx ->
                    websockets.add(ctx)
                }
                ws.onClose { ctx ->
                    websockets.remove(ctx)
                }
            }
            .start(7000)

        app.get("/", VueComponent("<hello-world></hello-world>"))

        GlobalScope.launch { tick() }
    }

    private suspend fun tick() {
        while (true) {
            websockets.forEach {
                it.send("Server time is ${LocalDateTime.now().toLocalTime().withNano(0)}")
            }
            delay(1000)
        }
    }
}

fun main(args: Array<String>) {
    Server().start()
}
