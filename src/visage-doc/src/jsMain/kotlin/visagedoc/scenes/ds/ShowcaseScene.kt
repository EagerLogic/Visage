package visagedoc.scenes.ds

import visage.core.Components
import visage.ds.components.EButtonColor
import visage.ds.components.EButtonVariant
import visage.ds.components.PageContent

fun Components.ShowcaseScene() = this.registerFunctionalComponent({}) {
    PageContent() {
        head("Showcase") {
            button("group") {
                variant = EButtonVariant.Link
                color = EButtonColor.Secondary
            }
            button("add", "Add vacation") {

            }
        }

        tab("General") {
            +"Tab 1 content"
        }

        tab("Users") {
            +"Tab 2 content"
        }

        tab("Teams") {
            +"Tab 3 content"
        }
    }
}