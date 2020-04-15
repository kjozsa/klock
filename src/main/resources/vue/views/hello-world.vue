<template id="hello-world">
    <div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
        <header class="masthead mb-auto"></header>

        <main role="main" class="inner cover">
            <h1 class="cover-heading">Kotlin + Websocket<br> example</h1>
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
    Vue.component("hello-world", {template: "#hello-world"});
</script>
<style>
    .hello-world {
        color: darkred;
    }
</style>