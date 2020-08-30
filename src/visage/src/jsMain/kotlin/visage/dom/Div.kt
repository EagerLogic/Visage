package visage.dom

import visage.core.Components

class MDiv: ATag<DivAttributes, DivStyles, DivEvents>("div", DivAttributes(), DivStyles(), DivEvents())

class DivAttributes: TagAttributes()

class DivStyles: TagStyles()

class DivEvents: TagEvents()

fun Components.div(init: MDiv.() -> Unit = {}) = this.registerComponent(MDiv(), init)
