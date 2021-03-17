package visage.ds.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.APureComposite
import visage.core.Components
import visage.dom.CssClass
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.components.page.CHeader
import visage.ds.components.page.CPageBody
import visage.ds.components.page.Header

fun Components.Card(init: CCard.() -> Unit) = this.registerComponent(CCard(), init)
fun CPageBody.Card(init: CCard.() -> Unit) = this.registerComponent(CCard(), init)

class CCard() : APureComponent() {

    var color: String? = Skin.palette.primaryColor
    private var header: Header? = null
    private var body: (CCardBody.() -> Unit)? = null
    private var footer: (CCardFooter.() -> Unit)? = null

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = cardRootStyle
            if (this@CCard.color != null) {
                style.borderTopColor = this@CCard.color!!
            }


            if (this@CCard.header != null) {
                this.registerComponent(CHeader(this@CCard.header!!.title, true, false, listOf(), -1), this@CCard.header!!.init)
            }

            if (this@CCard.body != null) {
                this.registerComponent(CCardBody(), this@CCard.body!!)
            }
            if (this@CCard.footer != null) {
                this.registerComponent(CCardFooter(), this@CCard.footer!!)
            }
        }
    }

    fun header(title: String = "", init: (CHeader.() -> Unit) = {}) {
        this.header = Header(title, init)
    }

    fun body(init: CCardBody.() -> Unit) {
        this.body = init
    }

    fun footer(init: CCardFooter.() -> Unit) {
        this.footer = init
    }

}

private val cardRootStyle by CssClass {
    width = "100%"
    minWidth = width
    maxWidth = width
    overflow = "hidden"
    backgroundColor = Skin.palette.cardBgColor
    boxShadow = "1px 1px 5px rgba(0,0,0, 20%)"
    borderTop = "3px solid ${Skin.palette.cardBgColor}"
}

// -------------------------------------------------------------------------------------------------------

class CCardBody() : APureComposite() {
    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = bodyRootStyle

            children.forEachIndexed { index, child ->
                if (index > 0) {
                    div {
                        style.apply {
                            height = "16px"
                            minHeight = height
                            maxHeight = height
                        }
                    }
                }
                +child
            }
        }
    }

}

private val bodyRootStyle by CssClass {
    width = "100%"
    minWidth = width
    maxWidth = width
    display = "flex"
    overflow = "hidden"
    flexDirection = "column"
}

// ---------------------------------------------------------------------------------------------------------

class CCardFooter() : APureComponent() {

    var text: String? = null

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = footerRootStyle

            div {
                classes = footerTextStyle

                if (this@CCardFooter.text != null) {
                    Text(this@CCardFooter.text!!, ETextStyles.Weak)
                }
            }

            for (child in children) {
                div {
                    style.apply {
                        width = "8px"
                        minWidth = width
                        maxWidth = width
                    }
                }
                div {
                    style.flexGrow = "0"
                    style.flexShrink = "0"
                    +child
                }
            }
        }
    }

    fun IconButton(icon: String, color: EButtonColor = EButtonColor.Secondary, onClick: (() -> Unit)) {
        this.registerComponent(CButton(null, icon)) {
            size = EButtonSize.Normal
            variant = EButtonVariant.Link
            this.color = color
            this.onClick = onClick
        }
    }

    fun ResponsiveButtonWithIcon(icon: String, title: String, color: EButtonColor = EButtonColor.Primary, onClick: (() -> Unit)) =
        this.registerComponent(CResponsiveButton(icon, title)) {
            size = EButtonSize.Normal
            variant = EButtonVariant.Filled
            isIconVisibleWhenUncompressed = true
            this.color = color
            this.onClick = onClick
        }

    fun ResponsiveButton(icon: String, title: String, color: EButtonColor = EButtonColor.Primary, onClick: (() -> Unit)) =
        this.registerComponent(CResponsiveButton(icon, title)) {
            size = EButtonSize.Normal
            variant = EButtonVariant.Filled
            isIconVisibleWhenUncompressed = false
            this.color = color
            this.onClick = onClick
        }

    fun Button(title: String, color: EButtonColor = EButtonColor.Primary, variant: EButtonVariant = EButtonVariant.Filled, onClick: (() -> Unit)) =
        this.registerComponent(CButton(title, null)) {
            this.size = EButtonSize.Normal
            this.variant = variant
            this.color = color
            this.onClick = onClick
        }

    fun Button(icon: String, title: String, color: EButtonColor = EButtonColor.Primary, variant: EButtonVariant = EButtonVariant.Filled, onClick: (() -> Unit)) =
        this.registerComponent(CButton(title, icon)) {
            this.size = EButtonSize.Normal
            this.variant = variant
            this.color = color
            this.onClick = onClick
        }

}

private val footerRootStyle by CssClass {
    width = "100%"
    minWidth = width
    maxWidth = width
    padding = "16px"
    display = "flex"
    alignItems = "center"
    overflow = "hidden"
    backgroundColor = Skin.palette.cardFooterBgColor
}

private val footerTextStyle by CssClass {
    flexGrow = "1"
    overflow = "hidden"
}