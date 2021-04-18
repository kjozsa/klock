package klock

import io.javalin.Javalin
import io.javalin.plugin.rendering.vue.JavalinVue
import io.javalin.plugin.rendering.vue.VueComponent
import io.javalin.websocket.WsContext
import io.javalin.plugin.rendering.vue.VueVersion
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime

class Server {
    private val websockets = mutableListOf<WsContext>()
    val logger = LoggerFactory.getLogger(this.javaClass)

    fun start() {
        val app = Javalin.create { config ->
            config.addStaticFiles("/web")
            config.enableWebjars()
        }.ws("/websocket") { ws ->
            ws.onConnect {
                logger.info("connected ${it.session.remoteAddress}")
                websockets.add(it)
            }
            ws.onClose {
                websockets.remove(it)
            }
        }.start(7000)

        JavalinVue.vueVersion = VueVersion.VUE_3
        JavalinVue.vueAppName("app")

        app.get("/", VueComponent("klock"))

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

fun main() {
    Server().start()
}
