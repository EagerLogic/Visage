package visage.ds.utils

import kotlinx.browser.window
import kotlinx.dom.createElement
import org.w3c.dom.get
import visage.core.Event
import visage.core.Visage
import kotlin.js.RegExp

object RenderMode {

    private var isInitialized = false

    /**
     * This event is fired when the render mode is changed cause of any reason, for example by rotating the phone from
     * portrait to landscape, or the browser is resized
     */
    val onChange = Event<ERenderMode>()
    get () {
        if (!isInitialized) {
            init()
        }
        return field
    }

    /**
     * Returns the current render mode
     */
    var current: ERenderMode = ERenderMode.Full
        get() {
            if (!isInitialized) {
                init()
            }
            return field
        }
        private set(value: ERenderMode) {
            val oldValue = field
            field = value
            if (oldValue != value) {
                Visage.rerender()
                onChange.fire(value)
            }
        }

    /**
     * Indicates if the content must be compressed to fit in a smaller screen.
     *
     * @return true if current render mode is `Compressed`, otherwise false
     */
    val compressed: Boolean
    get() {
        return current == ERenderMode.Compressed
    }

    /**
     * Indicates if the page must be collapsed. Please note that `Compressed` mode is also '`Collapsed'`
     *
     * @return true if the current render mode is `Collapsed` or `Compressed`, otherwise false
     */
    val collapsed: Boolean
    get() {
        return current != ERenderMode.Full
    }

    private fun init() {
        if (isInitialized) {
            return
        }
        isInitialized = true
        window.onresize = {
            onScreenSizeChanged()
        }
        window.addEventListener("orientationchange", {
            onScreenSizeChanged()
        })
        update()
    }

    private fun onScreenSizeChanged() {
        update()
    }

    private fun update() {
        val platform = readPlatformFromUserAgent()
        val width = window.innerWidth
        val height = window.innerHeight
        val isPortrait = width <= height

        var newMode = if (platform == "mobile") {
            if (isPortrait) {
                ERenderMode.Compressed
            } else {
                ERenderMode.Collapsed
            }
        } else if (platform == "tablet") {
            if (isPortrait) {
                ERenderMode.Collapsed
            } else {
                ERenderMode.Full
            }
        } else {
            if (width >= ERenderMode.Full.minWidth) {
                ERenderMode.Full
            } else if (width >= ERenderMode.Collapsed.minWidth) {
                ERenderMode.Collapsed
            } else {
                ERenderMode.Compressed
            }
        }

        if (newMode != current) {
            this.updateViewport(newMode)
        }

        this.current = newMode
    }

    private fun readPlatformFromUserAgent(): String {
        val ua = window.navigator.userAgent
        val tabletRegexp = RegExp("(tablet|ipad|playbook|silk)|(android(?!.*mobi))", "i")
        if (tabletRegexp.test(ua)) {
            return "tablet"
        }

        val mobileRegexp =
            RegExp("Mobile|iP(hone|od)|Android|BlackBerry|IEMobile|Kindle|Silk-Accelerated|(hpw|web)OS|Opera M(obi|ini)")
        if (mobileRegexp.test(ua)) {
            return "mobile"
        }

        return "desktop";
    }

    private fun updateViewport(mode: ERenderMode) {
        removePreviousViewportIfAny()
        insertNewViewport(mode)
    }

    private fun removePreviousViewportIfAny() {
        val metaElements = window.document.getElementsByTagName("meta")
        for (i in 0 until metaElements.length) {
            val e = metaElements[i]!!
            val name = e.getAttribute("name")
            if ("viewport" == name?.toLowerCase()) {
                e.remove()
            }
        }
    }

    private fun insertNewViewport(mode: ERenderMode) {
        val head = window.document.getElementsByTagName("head")[0]!!
        val meta = window.document.createElement("meta") {
            this.setAttribute("name", "viewport")
            this.setAttribute("content", "width=${mode.preferredWidth}, user-scalable=no")
        }
        head.appendChild(meta)
    }

}

/**
 * The actual required render mode based on the actual device and it's screen size.
 */
enum class ERenderMode(val minWidth: Int, val preferredWidth: Int, val preferredContentWidth: Int, val maxWidth: Int) {
    /**
     * This mode is used to render the page on small screens (Mobile).
     * Components must be compressed or collapsed to fit in the preferredContentWidth of this mode.
     *
     * This mode is used on portrait mobile devices.
     *
     * The preferred width is 500 pixels which can be fully used by content,
     * but the actual screen width may
     * vary between 500 - 999 pixels.
     */
    Compressed(0, 500, 500, 999),

    /**
     * This mode is used to render the page on a medium screen (Tablet).
     * Components must be compressed or collapsed to fit in the preferredContentWidth of this mode.
     *
     * This mode is used on portrait tablet and landscape mobile devices.
     *
     * The preferred width of this mode is 1000 pixels which can be fully used by content,
     * but the actual screen width may vary between 1000 - 1279 pixels.
     */
    Collapsed(1000, 1000, 1000, 1279),

    /**
     * This mode is used to render the page on a larger screen (Desktop).
     * Components does not need to compressed or collapsed but you need to design the components to the preferredContentWidth of this mode.
     *
     * This mode is used on desktop and landscape tablet devices.
     *
     * The preferred width of this mode is 1280 pixels but 280 pixels are reserved to the side menu, so the preferredContentWidth
     * which you can use is 1000 pixels, but the actual screen width may vary from 1280 upto infinity.
     */
    Full(1280, 1280, 1000, Int.MAX_VALUE),
}