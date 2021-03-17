package visage.dom

import kotlinx.browser.document
import org.w3c.dom.HTMLHeadElement
import org.w3c.dom.HTMLStyleElement
import org.w3c.dom.get
import visage.core.Event
import kotlin.reflect.KProperty

class CssClass(private val init: CssStyleClass.() -> Unit) {
    private var className: String? = null

    init {
        Css.onClearCache.addListener {
            this.className = null
        }
    }

    operator fun <G> getValue(instance: G?, property: KProperty<*>): String {
        if (className == null) {
            className = Css.createClass(init)
        }
        return className!!
    }

}

class CssStyleClass : TagStyles() {

    val pseudoClasses = CssPseudoClasses()

    fun pseudo(init: CssPseudoClasses.() -> Unit) {
        this.pseudoClasses.init()
    }

}

class CssBlock : TagStyles() {

}

class CssPseudoClasses {

    private val classes = mutableMapOf<String, TagStyles>()

    private fun addClass(name: String, init: TagStyles.()->Unit) {
        val tagStyles = TagStyles()
        tagStyles.init()
        this.classes[name] = tagStyles
    }

    //var active: TagStyles? by delegate(":active")
    fun active(init: TagStyles.() -> Unit) = this.addClass(":active", init)
    fun checked(init: TagStyles.() -> Unit) = this.addClass(":checked", init)
    fun default(init: TagStyles.() -> Unit) = this.addClass(":default", init)
    fun defined(init: TagStyles.() -> Unit) = this.addClass(":defined", init)
    fun disabled(init: TagStyles.() -> Unit) = this.addClass(":disabled", init)
    fun empty(init: TagStyles.() -> Unit) = this.addClass(":empty", init)
    fun enabled(init: TagStyles.() -> Unit) = this.addClass(":enabled", init)
    //:first
    //:first-child
    //:first-of-type
    fun focus(init: TagStyles.() -> Unit) = this.addClass(":focus", init)
    //:focus-within
    //:host
    fun hover(init: TagStyles.() -> Unit) = this.addClass(":hover", init)
    //:indeterminate
    //:in-range
    fun invalid(init: TagStyles.() -> Unit) = this.addClass(":invalid", init)
    //:last-child
    //:last-of-type
    //:left
    fun link(init: TagStyles.() -> Unit) = this.addClass(":link", init)
    //:only-child
    //:only-of-type
    //:optional
    //:out-of-range
    fun readOnly(init: TagStyles.() -> Unit) = this.addClass(":read-only", init)
    //:read-write
    //:required
    fun required(init: TagStyles.() -> Unit) = this.addClass(":required", init)
    //:right
    //:root
    //:scope
    //:target
    //:valid
    fun visited(init: TagStyles.() -> Unit) = this.addClass(":visited", init)

    operator fun get(name: String): TagStyles? {
        return this.classes[name.toLowerCase()]
    }

    operator fun set(name: String, value: TagStyles?) {
        if (value == null) {
            this.classes.remove(name.toLowerCase())
        } else {
            this.classes[name.toLowerCase()] = value
        }
    }

    operator fun iterator(): MutableIterator<MutableMap.MutableEntry<String, TagStyles>> {
        return this.classes.iterator()
    }
}



object Css {

    private var addedStyles = mutableMapOf<String, Boolean>()
    internal val onClearCache =  Event<Unit>()

    fun clearCache() {
        this.onClearCache.fire(Unit)
    }

    internal fun createClass(init: CssStyleClass.() -> Unit): String {
        val css = CssStyleClass()
        css.init()

        return this.applyCssClass(css)
    }

    fun createBlock(selector: String, init: CssBlock.() -> Unit) {
        val css = CssBlock()
        css.init()

        return this.applyCssBlock(selector, css)
    }

    private fun applyCssClass(cssStyle: CssStyleClass): String {
        val cssStr = getStyleString(cssStyle)
        val pseudoStrs = mutableMapOf<String, String>()
        var hashBase = cssStr
        for (item in cssStyle.pseudoClasses) {
            val pseudoStyleStr = getStyleString(item.value)
            pseudoStrs[item.key] = pseudoStyleStr
            hashBase += "|${pseudoStyleStr}"
        }
        val hash = hashBase.hash()
        val className = "vsg-$hash"

        if (addedStyles[className] != null) {
            return className
        }

        addedStyles[className] = true

        var style = ".${className} {${cssStr}}\n"
        for (item in pseudoStrs) {
            style += ".${className}${item.key} {${item.value}}\n"
        }

        this.addStyleElement(style)

        return className
    }

    private fun applyCssBlock(selector: String, block: CssBlock) {
        val cssStr = getStyleString(block)
        if (addedStyles[selector] != null) {
            throw Exception("This CSS block is alredy added!")
        }
        val style = "${selector} {${cssStr}}\n"
        this.addStyleElement(style)
    }

    private fun addStyleElement(value: String) {
        val style = document.createElement("style") as HTMLStyleElement
        style.type = "text/css"
        style.innerHTML = value
        (document.getElementsByTagName("head").get(0) as HTMLHeadElement).appendChild(style)
    }

    private fun getStyleString(styles: TagStyles): String {
        var res: String = ""
        for (item in styles) {
            res = "${res}${item.key}:${item.value};"
        }
        return res
    }

}

//fun cssBlock(selector: String, init: CssBlock.() -> Unit) {
//    Css.createBlock(selector, init)
//}
//
//fun cssClass(init: CssClass.() -> Unit): String {
//    return Css.createClass(init)
//}

private fun String.hash(): String {
    var h1 = 0xdeadbeef.toInt()
    var h2 = 0x41c6ce57.toInt()
    for (i in 0 until this.length) {
        val ch = this[i].toInt()
        h1 = h1.xor(ch) * 2654435761.toInt()
        h2 = h2.xor(ch) * 1597334677.toInt()
    }
    h1 = (h1.xor(h1 ushr  16) * 2246822507.toInt()) xor (h2.xor(h2 ushr 13) * 3266489909.toInt())
    h2 = (h2.xor(h2 ushr  16) * 2246822507.toInt()) xor (h1.xor(h1 ushr 13) * 3266489909.toInt())
    val lres = 4294967296 * (2097151 and h2) + h1
    return lres.toString(16)
}