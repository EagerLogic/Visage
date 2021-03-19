package visage.ds.components

import visage.core.AComponent
import visage.core.APureComposite
import visage.core.Components
import visage.dom.div
import visage.ds.colorpalette.Skin

class CModal(private val title: String) : APureComposite() {

    var error: String? = null
    var infoText: String? = null
    var isLoading: Boolean = false

    private var footerInit: (CCardFooter.() -> Unit)? = null

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            style.apply {
                position = "fixed"
                backgroundColor = Skin.palette.blurColor
                overflow = "auto"
                left = "0px"
                top = "0px"
                right = "0px"
                bottom = "0px"
                zIndex = "10000"
            }

            div {
                style.apply { display = "table"; width = "100%"; minHeight = "100%"; height = "100%" }

                div {
                    style.apply { display = "table-row"; height = "40px" }
                    div {
                        style.display = "table-cell"
                    }
                }

                div {
                    style.display = "table-row"
                    div {
                        style.apply { display = "table-cell"; textAlign = "center"; verticalAlign = "middle" }
                        div {
                            style.apply { display = "inline-block"; width = "480px" }
                            div {
                                style.apply { display = "inline-block"; textAlign = "left"; width = "480px" }
                                events.onClick = {
                                    it.stopPropagation()
                                }

                                this@CModal.renderDialog(this, children)
                            }
                        }
                    }
                }

                div {
                    style.apply { display = "table-row"; height = "40px" }
                    div {
                        style.display = "table-cell"
                    }
                }
            }
        }
    }

    private fun renderDialog(receiver: Components, children: List<AComponent<*>>) {
        receiver.Card {
            infoText = this@CModal.infoText
            isLoading = this@CModal.isLoading
            if (this@CModal.error != null) {
                messages {
                    danger(this@CModal.error!!)
                }
            }
            header(this@CModal.title)
            body {
                div {
                    style.apply {
                        width = "100%"
                        minWidth = width
                        maxWidth = width
                        overflow = "hidden"
                        padding = "16px"
                        display = "flex"
                        flexDirection = "column"
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
            if (this@CModal.footerInit != null) {
                footer {
                    this@CModal.footerInit!!.invoke(this)
                }
            }
        }
    }

    fun footer(init: CCardFooter.() -> Unit) {
        this.footerInit = init
    }

}

fun Components.Modal(
        title: String,
        init: CModal.() -> Unit
) = this.registerComponent(CModal(title), init)