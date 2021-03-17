package visage.ds.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.ds.utils.RenderMode

fun Components.ResponsiveButton(icon: String, title: String, init: (CResponsiveButton.() -> Unit) = {}) =
    this.registerComponent(CResponsiveButton(icon, title), init)

class CResponsiveButton(val icon: String, val title: String) : APureComponent() {

    var size = EButtonSize.Normal
    var color = EButtonColor.Primary
    var variant = EButtonVariant.Filled
        get
        set(value) {
            field = value
            compressedVariant = value
        }
    var compressedVariant = EButtonVariant.Filled
    var isIconVisibleWhenUncompressed = true
    var disabled = false

    var onClick: (() -> Unit)? = null

    override fun Components.render(children: List<AComponent<*>>) {
        if (RenderMode.compressed) {
            IconButton(this@CResponsiveButton.icon) {
                size = this@CResponsiveButton.size
                color = this@CResponsiveButton.color
                variant = this@CResponsiveButton.compressedVariant
                disabled = this@CResponsiveButton.disabled
                onClick = this@CResponsiveButton.onClick
            }
        } else {
            if (this@CResponsiveButton.isIconVisibleWhenUncompressed) {
                ButtonWithIcon(this@CResponsiveButton.title, this@CResponsiveButton.icon) {
                    size = this@CResponsiveButton.size
                    color = this@CResponsiveButton.color
                    variant = this@CResponsiveButton.variant
                    disabled = this@CResponsiveButton.disabled
                    onClick = this@CResponsiveButton.onClick
                }
            } else {
                Button(this@CResponsiveButton.title) {
                    size = this@CResponsiveButton.size
                    color = this@CResponsiveButton.color
                    variant = this@CResponsiveButton.variant
                    disabled = this@CResponsiveButton.disabled
                    onClick = this@CResponsiveButton.onClick
                }
            }
        }
    }

}