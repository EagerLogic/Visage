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
import visage.ds.utils.ETextAlign

fun Components.Card(init: CCard.() -> Unit) = this.registerComponent(CCard(), init)
fun CPageBody.Card(init: CCard.() -> Unit) = this.registerComponent(CCard(), init)

class CCard() : APureComponent() {

    var color: String = Skin.palette.primaryColor
    var infoText: String? = null
    var isLoading: Boolean = false
    private var header: Header? = null
    private var body: (CCardBody.() -> Unit)? = null
    private var footer: (CCardFooter.() -> Unit)? = null
    private val messages = AttentionMessagesDesc()

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = cardRootStyle

            if (this@CCard.isLoading) {
                HorizontalLoadIndicator(null) {
                    color = this@CCard.color
                    bgColor = Skin.palette.cardBgColor
                    height = 3
                }
            } else {
                div {
                    style.apply {
                        width = "100%"
                        height = "3px"
                        minHeight = height
                        backgroundColor = this@CCard.color
                    }
                }
            }

            if (this@CCard.header != null) {
                val header = CHeader(this@CCard.header!!.title, true, false, listOf(), -1, this@CCard.isLoading)
                this.registerComponent(header, this@CCard.header!!.init)
            }

            if (this@CCard.infoText != null) {
                div {
                    style.padding = "16px 16px 16px 16px"

                    Text(this@CCard.infoText!!, ETextStyles.Weak, true) {
                        textAlign = ETextAlign.CENTER
                    }
                }
            }

            if (this@CCard.messages.messages.size > 0) {
                div {
                    style.apply {
                        width = "100%"
                        minWidth = width
                        padding = "0px 16px"
                    }
                    this@CCard.messages.messages.forEachIndexed { index, msg ->
                        if (index > 0) {
                            div {
                                style.apply {
                                    height = "8px"
                                    minHeight = height
                                }
                            }
                        }
                        AttentionMessage(msg.type, msg.message)
                    }
                    div {
                        style.apply {
                            height = "16px"
                            minHeight = height
                        }
                    }
                }
            }

            if (this@CCard.body != null) {
                this.registerComponent(CCardBody(this@CCard.isLoading), this@CCard.body!!)
            }
            if (this@CCard.footer != null) {
                this.registerComponent(CCardFooter(this@CCard.isLoading), this@CCard.footer!!)
            }
        }
    }

    fun header(title: String = "", init: (CHeader.() -> Unit) = {}) {
        this.header = Header(title, init)
    }

    fun messages(init: AttentionMessagesDesc.() -> Unit) {
        messages.init()
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
}

class AttentionMessagesDesc {
    internal val messages = mutableListOf<AttentionMessageDesc>()

    fun info(message: String) {
        messages.add(AttentionMessageDesc(EAttentionMessageType.Info, message))
    }

    fun success(message: String) {
        messages.add(AttentionMessageDesc(EAttentionMessageType.Success, message))
    }

    fun warning(message: String) {
        messages.add(AttentionMessageDesc(EAttentionMessageType.Warning, message))
    }

    fun danger(message: String) {
        messages.add(AttentionMessageDesc(EAttentionMessageType.Danger, message))
    }
}

internal class AttentionMessageDesc(val type: EAttentionMessageType, val message: String)

// -------------------------------------------------------------------------------------------------------

class CCardBody(private val isLoading: Boolean) : APureComposite() {
    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = bodyRootStyle
            if (this@CCardBody.isLoading) {
                attr["disabled"] = "true"
                style.opacity = "0.3"
                style.pointerEvents = "none"
            }

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

class CCardFooter(private val isLoading: Boolean) : APureComponent() {

    var text: String? = null

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = footerRootStyle
            if (this@CCardFooter.isLoading) {
                attr["disabled"] = "true"
                style.pointerEvents = "none"
            }

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
                    if (this@CCardFooter.isLoading) {
                        style.opacity = "0.3"
                    }
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