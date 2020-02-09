package visage.core

import kotlin.browser.window

class Navigation private constructor() {

    companion object {

        val onLocationChanged = Event<LocationItem>()

        val currentLocation: LocationItem
            get() = LocationItem.current

        fun init() {
            window.onpopstate = {
                fireEvent()
            }
        }

        fun pushLocation(url: String, data: Any? = null) {
            window.history.pushState(data, "", url)
            fireEvent()
        }

        fun replaceLocation(url: String, data: Any? = null) {
            window.history.replaceState(data, "", url)
            fireEvent()
        }

        fun forward() {
            window.history.forward()
            fireEvent()
        }

        fun back() {
            window.history.back()
            fireEvent()
        }

        private fun fireEvent() {
            onLocationChanged.fire(currentLocation)
        }

    }

}

class LocationItem(val path: String, val params: Map<String, String>, val data: Any?) {

    companion object {
        val current: LocationItem
            get() {
                val path = window.location.pathname
                val qStr = window.location.search
                val data = window.history.state

                val params = parseQuery(qStr)

                return LocationItem(path, params, data)
            }
    }

}

private fun parseQuery(input: String): Map<String, String> {
    var res = mutableMapOf<String, String>();

    var d = input;
    d = d.trim();

    if (d.length < 1) {
        return res;
    }

    if (d.indexOf("?") == 0) {
        d = d.substring(1);
    }

    val params = d.split("&");
    params.forEach {
        val kv = it.split("=")
        if (kv.size != 2) {
            return@forEach
        }

        res[kv[0]] = kv[1]
    }

    return res;
}
