package visage.dom

import visage.core.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import org.w3c.dom.Text
import org.w3c.dom.events.Event
import kotlinx.browser.document
import kotlin.reflect.KProperty

abstract class ATag<GAttributes : BaseTagAttributes, GStyles : BaseTagStyles, GEvents : BaseTagEvents>(
    private val name: String,
    val attr: GAttributes,
    val style: GStyles,
    val events: GEvents
) : APureComposite(), IDomNode {

    override fun Components.render(children: List<AComponent<*>>) {
        for (child in children) {
            this.addChild(child)
        }
    }

    override fun createNode(): Node {
        val res = document.createElement(name).unsafeCast<HTMLElement>()
        attr.applyProperties(res)
        style.applyProperties(res)
        events.applyProperties(res)
        return res
    }

    override fun updateNode(node: Node, oldComponent: AComponent<*>) {
        val element = node.unsafeCast<HTMLElement>()
        attr.updateProperties(element, (oldComponent.unsafeCast<ATag<*, *, *>>()).attr)
        style.updateProperties(element, (oldComponent.unsafeCast<ATag<*, *, *>>()).style)
        events.updateProperties(element, (oldComponent.unsafeCast<ATag<*, *, *>>()).events)
    }

}

abstract class AMergeableProperties<GType: Any> : MutableIterable<MutableMap.MutableEntry<String, GType>> {

    private val properties: MutableMap<String, GType> = mutableMapOf()

    operator fun get(name: String): GType? {
        return this.properties[name.toLowerCase()]
    }

    operator fun set(name: String, value: GType?) {
        if (value == null) {
            this.properties.remove(name.toLowerCase())
        } else {
            this.properties[name.toLowerCase()] = value
        }
    }

    override fun iterator(): MutableIterator<MutableMap.MutableEntry<String, GType>> {
        return this.properties.iterator()
    }

    fun applyProperties(element: HTMLElement) {
        for ((name, value) in properties) {
            applyProperty(name, value, element)
        }
    }

    fun updateProperties(element: HTMLElement, oldProps: AMergeableProperties<GType>) {
        // check what to delete or update
        for ((name, value) in oldProps.properties) {
            if (this.properties[name] == null) {
                // delete
                removeProperty(name, value, element)
            } else {
                // update if changed
                if (!this.isPropertiesEquals(value, this.properties[name]!!)) {
                    // update because changed
                    removeProperty(name, value, element)
                    applyProperty(name, this.properties[name]!!, element)
                }
            }
        }

        // check what is new
        for ((name, value) in this.properties) {
            if (oldProps[name] == null) {
                applyProperty(name, value, element)
            }
        }
    }

    protected abstract fun isPropertiesEquals(prop1: GType, prop2: GType): Boolean
    protected abstract fun applyProperty(name: String, value: GType, element: HTMLElement)
    protected abstract fun removeProperty(name: String, value: GType, element: HTMLElement)

    fun delegate(name: String): PropertiesDelegate<GType> {
        return PropertiesDelegate(name)
    }

    class PropertiesDelegate<GType: Any>(val name: String) {
        operator fun <C: Any> getValue(thisRef: AMergeableProperties<GType>, property: KProperty<*>): C? {
            return thisRef[name].unsafeCast<C?>()
        }

        operator fun <C: Any> setValue(thisRef: AMergeableProperties<GType>, property: KProperty<*>, value: C?) {
            thisRef[name] = value.unsafeCast<GType?>()
        }
    }

}

open class BaseTagAttributes: AMergeableProperties<String>() {

    override fun isPropertiesEquals(prop1: String, prop2: String): Boolean {
        return prop1 == prop2
    }

    final override fun applyProperty(name: String, value: String, element: HTMLElement) {
        element.setAttribute(name, value)
    }

    final override fun removeProperty(name: String, value: String, element: HTMLElement) {
        element.removeAttribute(name)
    }

}

typealias Listener<GEvent> = (e: GEvent) -> Unit

open class BaseTagEvents: AMergeableProperties<Listener<Event>>() {

    override fun isPropertiesEquals(prop1: Listener<Event>, prop2: Listener<Event>): Boolean {
        return false
    }

    override fun applyProperty(name: String, value: Listener<Event>, element: HTMLElement) {
        element.addEventListener(name, value)
    }

    override fun removeProperty(name: String, value: Listener<Event>, element: HTMLElement) {
        element.removeEventListener(name, value)
    }

}

open class BaseTagStyles: AMergeableProperties<String>() {

    override fun isPropertiesEquals(prop1: String, prop2: String): Boolean {
        return prop1 == prop2
    }

    override fun applyProperty(name: String, value: String, element: HTMLElement) {
        element.style.setProperty(name, value)
    }

    override fun removeProperty(name: String, value: String, element: HTMLElement) {
        element.style.removeProperty(name)
    }

    fun merge(other: BaseTagStyles) {
        for ((name, value) in other) {
            this[name] = value
        }
    }

}


class MTextNode(private val text: String) : APureComponent(), IDomNode {

    override fun createNode(): Node {
        return document.createTextNode(text)
    }

    override fun updateNode(node: Node, oldComponent: AComponent<*>) {
        (node.unsafeCast<Text>()).data = text
    }

    override fun Components.render(children: List<AComponent<*>>) {
        // nothing to do here
    }

}

fun Components.text(text: String) = this.registerComponent(MTextNode(text), {})
