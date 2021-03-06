package visage.ds.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.Css
import visage.dom.div
import visage.dom.text
import visage.ds.colorpalette.Skin

class CIcon(val icon: String, val color: String, val size: Int) : APureComponent() {

    var onClick: (() -> Unit)? = null

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = "material-icons $iconClass"
            style.fontSize = "${this@CIcon.size}px"
            style.color = this@CIcon.color

            text(this@CIcon.icon)
        }
    }

}

private val iconClass = Css.createClass {
    display = "inline-flex"
}

fun Components.Icon(icon: String, color: String = Skin.palette.primaryColor, size: Int = 14, init: (CIcon.() -> Unit) = {}) =
        this.registerComponent(CIcon(icon, color, size), init)