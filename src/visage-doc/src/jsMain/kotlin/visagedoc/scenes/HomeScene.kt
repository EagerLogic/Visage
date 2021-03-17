package visagedoc.scenes

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.ds.components.page.Page

class CHomeScene : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        Page {
            head("Home")
        }
    }
}

fun Components.HomeScene() = this.registerComponent(CHomeScene(), {})