<template id="klock">
    <div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
        <header class="masthead mb-auto"></header>

        <main role="main" class="inner cover">
            <h1 class="cover-heading">Kotlin + Vue + Websocket<br> example</h1>
            <br>
            <p class="lead">
                <span href="#" class="btn btn-lg btn-secondary" id="currentTime">Loading..</span>
            </p>
        </main>

        <footer class="mastfoot mt-auto">
            <div class="inner">
                <p>Cover template for <a href="https://getbootstrap.com/">Bootstrap</a>, by <a href="https://twitter.com/mdo">@mdo</a>.</p>
            </div>
        </footer>
    </div>
</template>
<script>
    Vue.component("klock", {template: "#klock"});

    let id = id => document.getElementById(id);
    let ws = new WebSocket("ws://" + location.hostname + ":" + location.port + "/websocket");

    ws.onmessage = msg => {
        id("currentTime").innerHTML = msg.data
    };

    ws.onclose = () => alert("server closed connection");
</script>
<style>
</style>