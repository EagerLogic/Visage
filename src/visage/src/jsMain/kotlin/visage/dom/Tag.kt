package visage.dom

import kotlinx.browser.document
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import org.w3c.dom.Text
import org.w3c.dom.events.Event
import visage.core.*
import kotlin.reflect.KProperty

open class CTag(private val name: String) : APureComposite(), IDomNode {

    val attr = TagAttributes()
    val style = TagStyles()
    val events = TagEvents()

    var id: String? by attr.delegate("id")
    var classes: String? by attr.delegate("class")

    final override fun Components.render(children: List<AComponent<*>>) {
        for (child in children) {
            this.addChild(child)
        }
    }

    final override fun createNode(): Node {
        val res = document.createElement(name).unsafeCast<HTMLElement>()
        attr.applyProperties(res)
        style.applyProperties(res)
        events.applyProperties(res)
        return res
    }

    final override fun updateNode(node: Node, oldComponent: AComponent<*>) {
        val element = node.unsafeCast<HTMLElement>()
        attr.updateProperties(element, (oldComponent.unsafeCast<CTag>()).attr)
        style.updateProperties(element, (oldComponent.unsafeCast<CTag>()).style)
        events.updateProperties(element, (oldComponent.unsafeCast<CTag>()).events)
    }

}

fun Components.tag(name: String, init: CTag.() -> Unit = {}) =
    this.registerComponent(CTag(name), init)

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
        return PropertiesDelegate(name, this)
    }

    class PropertiesDelegate<GType: Any>(val name: String, val properties: AMergeableProperties<GType>) {
        operator fun getValue(thisRef: AMergeableProperties<GType>, property: KProperty<*>): GType? {
            return properties[name].unsafeCast<GType?>()
        }

        operator fun setValue(thisRef: AMergeableProperties<GType>, property: KProperty<*>, value: GType?) {
            properties[name] = value.unsafeCast<GType?>()
        }

        operator fun getValue(tag: CTag, property: KProperty<*>): GType? {
            return properties[name].unsafeCast<GType?>()
        }

        operator fun setValue(tag: CTag, property: KProperty<*>, value: GType?) {
            properties[name] = value.unsafeCast<GType?>()
        }
    }

}

class TagAttributes: AMergeableProperties<String>() {

    override fun isPropertiesEquals(prop1: String, prop2: String): Boolean {
        return prop1 == prop2
    }

    override fun applyProperty(name: String, value: String, element: HTMLElement) {
        element.setAttribute(name, value)
    }

    override fun removeProperty(name: String, value: String, element: HTMLElement) {
        element.removeAttribute(name)
    }

}

typealias Listener<GEvent> = (e: GEvent) -> Unit

class TagEvents: AMergeableProperties<Listener<Event>>() {

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

open class TagStyles: AMergeableProperties<String>() {

    //<editor-fold desc="A">
    var alignContent: String? by delegate("align-content")
    var alignItems: String? by delegate("align-items")
    var alignSelf: String? by delegate("align-self")
    var animation: String? by delegate("animation")
    var animationDelay: String? by delegate("animation-delay")
    var animationDirection: String? by delegate("animation-direction")
    var animationDuration: String? by delegate("animation-duration")
    var animationFillMode: String? by delegate("animation-fill-mode")
    var animationIterationCount: String? by delegate("animation-iteration-count")
    var animationName: String? by delegate("animation-name")
    var animationPlayState: String? by delegate("animation-play-state")
    var animationTimingFunction: String? by delegate("animation-timing-function")
    //</editor-fold>

    //<editor-fold desc="B">
    //    backface-visibility
    var background: String? by delegate("background")
    var backgroundAttachment: String? by delegate("background-attachment")
    var backgroundBlendMode: String? by delegate("background-blend-mode")
    var backgroundCip: String? by delegate("background-clip")
    var backgroundColor: String? by delegate("background-color")
    var backgroundImage: String? by delegate("background-image")
    var backgroundOrigin: String? by delegate("background-origin")
    var backgroundPosition: String? by delegate("background-position")
    var backgroundRepeat: String? by delegate("background-repeat")
    var backgroundSize: String? by delegate("background-size")
    var blockSize: String? by delegate("block-size")
    var border: String? by delegate("border")
    var borderBlock: String? by delegate("border-block")
    var borderBlockColor: String? by delegate("border-block-color")
    var borderBlockEnd: String? by delegate("border-block-end")
    //    border-block-end-color
    //    border-block-end-style
    //    border-block-end-width
    //    border-block-start
    //    border-block-start-color
    //    border-block-start-style
    //    border-block-start-width
    //    border-block-style
    //    border-block-width
    var borderBottom: String? by delegate("border-bottom")
    var borderBottomColor: String? by delegate("border-bottom-color")
    var borderBottomLeftRadius: String? by delegate("border-bottom-left-radius")
    var borderBottomRightRadius: String? by delegate("border-bottom-right-radius")
    var borderBottomStyle: String? by delegate("border-bottom-style")
    var borderBottomWidth: String? by delegate("border-bottom-width")
    var borderCollapse: String? by delegate("border-collapse")
    var borderColor: String? by delegate("border-color")
    var borderEndEndRadius: String? by delegate("border-end-end-radius")
    var borderEndStartRadius: String? by delegate("border-end-start-radius")
    //    border-image
    //    border-image-outset
    //    border-image-repeat
    //    border-image-slice
    //    border-image-source
    //    border-image-width
    //    border-inline
    //    border-inline-color
    //    border-inline-end
    //    border-inline-end-color
    //    border-inline-end-style
    //    border-inline-end-width
    //    border-inline-start
    //    border-inline-start-color
    //    border-inline-start-style
    //    border-inline-start-width
    //    border-inline-style
    //    border-inline-width
    var borderLeft: String? by delegate("border-left")
    var borderLeftColor: String? by delegate("border-left-color")
    var borderLeftSize: String? by delegate("border-left-size")
    var borderLeftWidth: String? by delegate("border-left-width")
    var borderRadius: String? by delegate("border-radius")
    var borderRight: String? by delegate("border-right")
    var borderRightColor: String? by delegate("border-right-color")
    var borderRightStyle: String? by delegate("border-right-style")
    var borderRightWidth: String? by delegate("border-right-width")
    var borderSpacing: String? by delegate("border-spacing")
    var borderStartEndRadius: String? by delegate("border-start-end-radius")
    var borderStartStartRadius: String? by delegate("border-start-start-radius")
    var borderStyle: String? by delegate("border-style")
    var borderTop: String? by delegate("border-top")
    var borderTopColor: String? by delegate("border-top-color")
    var borderTopLeftRadius: String? by delegate("border-top-left-radius")
    var borderTopRightRadius: String? by delegate("border-top-right-radius")
    var borderTopStyle: String? by delegate("border-top-style")
    var borderTopWidth: String? by delegate("border-top-width")
    var borderWidth: String? by delegate("border-width")
    var bottom: String? by delegate("bottom")
    //    box-decoration-break
    var boxShadow: String? by delegate("box-shadow")
    var boxSizing: String? by delegate("box-sizing")
    //    break-after
    //    break-before
    //    break-inside
    //</editor-fold>

    //<editor-fold desc="C">
    //    caption-side
    //    caret-color
    //    ch
    //    clear
    //    clip
    //    clip-path
    //    cm
    var color: String? by delegate("color")
    //    color-adjust
    //    column-count
    //    column-fill
    //    column-gap
    //    column-rule
    //    column-rule-color
    //    column-rule-style
    //    column-rule-width
    //    column-span
    //    column-width
    //    columns
    //    content
    //    counter-increment
    //    counter-reset
    //    counter-set
    var cursor: String? by delegate("cursor")
    //</editor-fold>

    //<editor-fold desc="D">
    //    deg
    //    direction
    var display: String? by delegate("display")
    //    dpcm
    //    dpi
    //    dppx
    //</editor-fold>

    //<editor-fold desc="E">
    //    em
    //    empty-cells
    //    ex
    //</editor-fold>

    //<editor-fold desc="F">
    //    filter
    var flex: String? by delegate("flex")
    var flexBasis: String? by delegate("flex-basis")
    var flexDirection: String? by delegate("flex-direction")
    var flexFlow: String? by delegate("flex-flow")
    var flexGrow: String? by delegate("flex-grow")
    var flexShrink: String? by delegate("flex-shrink")
    var flexWrap: String? by delegate("flex-wrap")
    //    float
    var font: String? by delegate("font")
    var fontFamily: String? by delegate("font-family")
    //    font-feature-settings
    //    font-feature-settings (@font-face)
    //    font-kerning
    //    font-language-override
    //    font-optical-sizing
    var fontSize: String? by delegate("font-size")
    //    font-size-adjust
    //    font-stretch
    //    font-stretch (@font-face)
    //    font-style
    //    font-style (@font-face)
    //    font-synthesis
    //    font-variant
    //    font-variant (@font-face)
    //    font-variant-alternates
    //    font-variant-caps
    //    font-variant-east-asian
    //    font-variant-ligatures
    //    font-variant-numeric
    //    font-variant-position
    //    font-variation-settings (@font-face)
    //    font-weight
    var fontWeight: String? by delegate("font-weight")
    //</editor-fold>

    //<editor-fold desc="G">
    //    gap
    //    grad
    //    grid
    //    grid-area
    //    grid-auto-columns
    //    grid-auto-flow
    //    grid-auto-rows
    //    grid-column
    //    grid-column-end
    //    grid-column-start
    //    grid-row
    //    grid-row-end
    //    grid-row-start
    //    grid-template
    //    grid-template-areas
    //    grid-template-columns
    //    grid-template-rows
    //</editor-fold>

    //<editor-fold desc="H">
    //    Hz
    //    hanging-punctuation
    var height: String? by delegate("height")
    //    hyphens
    //</editor-fold>

    //<editor-fold desc="I">
    //    image-orientation
    //    image-rendering
    //    in
    //    inherit
    //    initial
    //    inline-size
    //    inset
    //    inset-block
    //    inset-block-end
    //    inset-block-start
    //    inset-inline
    //    inset-inline-end
    //    inset-inline-start
    //    isolation
    //</editor-fold>

    //<editor-fold desc="J">
    var justifyContent: String? by delegate("justify-content")
    var justifyItem: String? by delegate("justify-item")
    var justifySelf: String? by delegate("justifySelf")
    //</editor-fold>

    //<editor-fold desc="K">
    //</editor-fold>

    //<editor-fold desc="L">
    var left: String? by delegate("left")
    var letterSpacing: String? by delegate("letter-spacing")
    var lineBreak: String? by delegate("line-break")
    var lineHeight: String? by delegate("line-height")
    //    list-style
    //    list-style-image
    //    list-style-position
    //    list-style-type
    //</editor-fold>

    //<editor-fold desc="M">
    //    margin
    var margin: String? by delegate("margin")
    var marginBlock: String? by delegate("margin-block")
    var marginBlockEnd: String? by delegate("margin-block-end")
    var marginBlockStart: String? by delegate("margin-block-start")
    var marginBottom: String? by delegate("margin-bottom")
    //    margin-inline
    //    margin-inline-end
    //    margin-inline-start
    var marginLeft: String? by delegate("margin-left")
    var marginRight: String? by delegate("margin-right")
    var marginTop: String? by delegate("margin-top")
    //    mask
    //    mask-clip
    //    mask-composite
    //    mask-image
    //    mask-mode
    //    mask-origin
    //    mask-position
    //    mask-repeat
    //    mask-size
    //    mask-type
    var maxHeight: String? by delegate("max-height")
    var maxWidth: String? by delegate("max-width")
    //    min-block-size
    var minHeight: String? by delegate("min-height")
    //    min-inline-size
    var minWidth: String? by delegate("min-width")
    //    mix-blend-mode
    //</editor-fold>

    //<editor-fold desc="N">
    //</editor-fold>

    //<editor-fold desc="O">
    //    object-fit
    //    object-position
    var opacity: String? by delegate("opacity")
    //    order
    //    orphans
    //    outline
    //    outline-color
    //    outline-offset
    //    outline-style
    //    outline-width
    var overflow: String? by delegate("overflow")
    var overflowWrap: String? by delegate("overflow-wrap")
    var overflowX: String? by delegate("overflow-x")
    var overflowY: String? by delegate("overflow-y")
    //</editor-fold>

    //<editor-fold desc="P">
    var padding: String? by delegate("padding")
    //    padding-block
    //    padding-block-end
    //    padding-block-start
    var paddingBottom: String? by delegate("padding-bottom")
    //    padding-inline
    //    padding-inline-end
    //    padding-inline-start
    var paddingLeft: String? by delegate("padding-left")
    var paddingRight: String? by delegate("padding-right")
    var paddingTop: String? by delegate("padding-top")
    //    page-break-after
    //    page-break-before
    //    page-break-inside
    //    perspective
    //    perspective-origin
    //    place-content
    //    place-items
    //    place-self
    var pointerEvents: String? by delegate("pointer-events")
    var position: String? by delegate("position")
    //</editor-fold>

    //<editor-fold desc="Q">
    //    quotes
    //</editor-fold>

    //<editor-fold desc="R">
    //    resize
    //    revert
    var right: String? by delegate("right")
    //    rotate
    //    row-gap
    //</editor-fold>

    //<editor-fold desc="S">
    //    scale
    //    scroll-behavior
    //    scroll-margin
    //    scroll-margin-block
    //    scroll-margin-block-end
    //    scroll-margin-block-start
    //    scroll-margin-bottom
    //    scroll-margin-inline
    //    scroll-margin-inline-end
    //    scroll-margin-inline-start
    //    scroll-margin-left
    //    scroll-margin-right
    //    scroll-margin-top
    //    scroll-padding
    //    scroll-padding-block
    //    scroll-padding-block-end
    //    scroll-padding-block-start
    //    scroll-padding-bottom
    //    scroll-padding-inline
    //    scroll-padding-inline-end
    //    scroll-padding-inline-start
    //    scroll-padding-left
    //    scroll-padding-right
    //    scroll-padding-top
    //    scroll-snap-align
    //    scroll-snap-stop
    //    scroll-snap-type
    //    scrollbar-color
    //    scrollbar-width
    //    shape-image-threshold
    //    shape-margin
    //    shape-outside
    //</editor-fold>

    //<editor-fold desc="T">
    //    tab-size
    //    table-layout
    var textAlign: String? by delegate("text-align")
    var textAlignLast: String? by delegate("text-align-last")
    //    text-combine-upright
    var textDecoration: String? by delegate("text-decoration")
    //    text-decoration-color
    //    text-decoration-line
    //    text-decoration-style
    //    text-emphasis
    //    text-emphasis-color
    //    text-emphasis-position
    //    text-emphasis-style
    //    text-indent
    //    text-justify
    //    text-orientation
    var textOverflow: String? by delegate("text-overflow")
    //    text-rendering
    //    text-shadow
    //    text-transform
    //    text-underline-position
    var top: String? by delegate("top")
    //    touch-action
    //    transform
    //    transform-box
    //    <transform-function>
    //    transform-origin
    //    transform-style
    //    transition
    //    transition-delay
    //    transition-duration
    //    transition-property
    //    transition-timing-function
    //    translate
    //    turn
    //</editor-fold>

    //<editor-fold desc="U">
    //    unicode-bidi
    //</editor-fold>
    var userSelect: String? by delegate("user-select")
    //<editor-fold desc="V">
    var verticalAlign: String? by delegate("vertical-align")
    var visibility: String? by delegate("visibility")
    //</editor-fold>

    //<editor-fold desc="W">
    var whiteSpace: String? by delegate("white-space")
    //    widows
    var width: String? by delegate("width")
    //    will-change
    var wordBreak: String? by delegate("word-break")
    var wordSpacing: String? by delegate("word-spacing")
    var wordWrap: String? by delegate("word-wrap")
    //    writing-mode
    //</editor-fold>

    //<editor-fold desc="X">
    //</editor-fold>

    //<editor-fold desc="Z">
    var zIndex: String? by delegate("z-index")
    //</editor-fold>

    override fun isPropertiesEquals(prop1: String, prop2: String): Boolean {
        return prop1 == prop2
    }

    override fun applyProperty(name: String, value: String, element: HTMLElement) {
        element.style.setProperty(name, value)
    }

    override fun removeProperty(name: String, value: String, element: HTMLElement) {
        element.style.removeProperty(name)
    }

    fun merge(other: TagStyles) {
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

@Deprecated("The text component is deprecated and will be removed in feature Visage release. Use the unary plus operatore following by any string.", ReplaceWith("+ \"Some text to add as a node\""))
fun Components.text(text: String) = this.registerComponent(MTextNode(text), {})



//open class TagEvents : BaseTagEvents() {
//
//    // mouse events
//    var onClick: Listener<MouseEvent>? by delegate("click")
//    var onMouseEnter: Listener<MouseEvent>? by delegate("mouseenter")
//    var onMouseLeave: Listener<MouseEvent>? by delegate("mouseleave")
//    var onMouseDown: Listener<MouseEvent>? by delegate("mousedown")
//    var onMouseUp: Listener<MouseEvent>? by delegate("mouseup")
//    var onMouseOver: Listener<MouseEvent>? by delegate("mouseover")
//    var onMouseOut: Listener<MouseEvent>? by delegate("mouseout")
//    var onMouseMove: Listener<MouseEvent>? by delegate("mousemove")
//
//    // touch event
//
//
//    // drag events
//    var onDrag: Listener<DragEvent>? by delegate("drag")
//    var onDragEnd: Listener<DragEvent>? by delegate("dragend")
//    var onDragEnter: Listener<DragEvent>? by delegate("dragenter")
//    var onDragExit: Listener<DragEvent>? by delegate("dragexit")
//    var onDragLeave: Listener<DragEvent>? by delegate("dragleave")
//    var onDragOver: Listener<DragEvent>? by delegate("dragover")
//    var onDragStart: Listener<DragEvent>? by delegate("dragstart")
//    var onDrop: Listener<DragEvent>? by delegate("drop")
//
//    // wheel events
//    var onWheel: Listener<WheelEvent>? by delegate("wheel")
//
//}
