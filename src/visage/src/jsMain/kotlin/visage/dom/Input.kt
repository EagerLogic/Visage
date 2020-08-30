package visage.dom

import visage.core.Components
import org.w3c.dom.events.InputEvent

class InputAttributes: TagAttributes() {

    var value: String? by delegate("value")

}

class InputStyles: TagStyles()

class InputEvents: TagEvents() {
    var onInput: Listener<InputEvent>? by delegate("input")
}

class MInput: ATag<InputAttributes, InputStyles, InputEvents>("input", InputAttributes(), InputStyles(), InputEvents())

fun Components.input() = this.registerComponent(MInput(), {})
