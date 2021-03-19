package visage.ds.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.utils.EFontWeight
import visage.ds.utils.ETextAlign

class TextStyle(
    val fontSize: Int,
    val fontWeight: EFontWeight,
    val color: String,
    val uppercase: Boolean
)

enum class ETextStyles(private val init: () -> TextStyle) {

    Strong(
        {
            TextStyle(
                fontSize = 14,
                fontWeight = EFontWeight.Medium,
                color = Skin.palette.strongTextColor,
                uppercase = false
            )
        }
    ),
    Normal(
        {
            TextStyle(
                fontSize = 14,
                fontWeight = EFontWeight.Regular,
                color = Skin.palette.normalTextColor,
                uppercase = false
            )
        }
    ),
    Weak(
        {
            TextStyle(
                fontSize = 14,
                fontWeight = EFontWeight.Regular,
                color = Skin.palette.weakTextColor,
                uppercase = false
            )
        }
    ),
    Sub(
        {
            TextStyle(
                fontSize = 12,
                fontWeight = EFontWeight.Regular,
                color = Skin.palette.weakTextColor,
                uppercase = false
            )
        }
    );

    val textStyle: TextStyle
        get() {
            return init()
        }



}

class CText internal constructor(val text: String, val style: TextStyle, val multiLine: Boolean) : APureComponent() {

    var textAlign: ETextAlign = ETextAlign.LEFT

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            style.apply {
                width = "100%"
                maxWidth = width
                overflow = "hidden"
                fontSize = "${this@CText.style.fontSize}px"
                fontWeight = this@CText.style.fontWeight.cssValue
                color = this@CText.style.color
                textAlign = when (this@CText.textAlign) {
                    ETextAlign.LEFT -> "left"
                    ETextAlign.CENTER -> "center"
                    ETextAlign.RIGHT -> "right"
                    ETextAlign.JUSTIFY -> "justify"
                }
                if (!this@CText.multiLine) {
                    textOverflow = "ellipsis"
                    whiteSpace = "nowrap"
                }
                if (this@CText.style.uppercase) {
                    this["text-transform"] = "uppercase"
                }
            }

            +this@CText.text

        }
    }

}

fun Components.Text(text: String, style: ETextStyles = ETextStyles.Normal, multiLine: Boolean = false, init: CText.() -> Unit = {}) =
        this.registerComponent(CText(text, style.textStyle, multiLine), init)

fun Components.Text(text: String, style: TextStyle, multiLine: Boolean = false, init: CText.() -> Unit = {}) =
        this.registerComponent(CText(text, style, multiLine), init)