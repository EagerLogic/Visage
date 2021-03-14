//package visage.ds.components
//
//import visage.core.AComponent
//import visage.core.APureComponent
//import visage.core.Components
//import visage.dom.div
//import visage.ds.colorpalette.Skin
//import visage.ds.utils.EFontWeight
//import visage.ds.utils.ETextAlign
//
//class TextStyle(
//    val fontSize: Int,
//    val fontWeight: EFontWeight,
//    val color: String,
//    val textAlign: ETextAlign,
//    val uppercase: Boolean
//)
//
//enum class ETextStyles(val textStyle: TextStyle) {
//    Default(
//            TextStyle(
//                    fontSize = 14,
//                    fontWeight = EFontWeight.Regular,
//                    color = Skin.palette.textStrong,
//                    textAlign = ETextAlign.LEFT,
//                    uppercase = false
//            )
//    ),
//
//
//}
//
//class CText internal constructor(val text: String, val style: TextStyle) : APureComponent() {
//
//    var singleLine = false
//
//    override fun Components.render(children: List<AComponent<*>>) {
//        div {
//            style.apply {
//                width = "100%"
//                maxWidth = width
//                overflow = "hidden"
//                fontSize = "${this@CText.style.fontSize}px"
//                fontWeight = "${this@CText.style.fontWeight}"
//                color = this@CText.style.color
//                textAlign = when (this@CText.style.textAlign) {
//                    ETextAlign.LEFT -> "left"
//                    ETextAlign.CENTER -> "center"
//                    ETextAlign.RIGHT -> "right"
//                    ETextAlign.JUSTIFY -> "justify"
//                }
//                if (this@CText.singleLine) {
//                    textOverflow = "ellipsis"
//                }
//                if (this@CText.style.uppercase) {
//                    this["text-transform"] = "uppercase"
//                }
//            }
//
//            +this@CText.text
//
//        }
//    }
//
//}
//
//fun Components.Text(text: String, style: ETextStyles = ETextStyles.Default) =
//        this.registerComponent(CText(text, style.textStyle), {})
//
//fun Components.Text(text: String, style: TextStyle) =
//        this.registerComponent(CText(text, style), {})