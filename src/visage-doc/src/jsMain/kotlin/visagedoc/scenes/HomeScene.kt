package visagedoc.scenes

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.APureComposite
import visage.core.Components
import visage.ds.components.BasePageContent

class CHomeScene : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        BasePageContent("Home") {

        }
    }
}

fun Components.HomeScene() = this.registerComponent(CHomeScene(), {})