package visagedoc.scenes

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.div
import visage.dom.text
import visage.ds.components.BasePageContent

class CComingSoonScene(val title: String) : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        BasePageContent(this@CComingSoonScene.title) {
            div {
                style.apply {
                    width = "100%"
                    padding = "32px"
                    textAlign = "center"
                    fontSize = "16px"
                    color = "#333"
                }

                text("This page is under construction. Check back later")

            }
        }
    }
}