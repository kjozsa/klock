package klock

import io.javalin.Javalin
import io.javalin.websocket.WsSession
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.time.LocalDateTime

class Server {
    private val websockets = mutableListOf<WsSession>()

    fun start() {
        Javalin.create().apply {
            ws("/websocket") { ws ->
                ws.onConnect { session ->
                    websockets.add(session)
                }
                ws.onClose { session, _, _ ->
                    websockets.remove(session)
                }
            }
            enableStaticFiles("/web")
        }.start()

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
