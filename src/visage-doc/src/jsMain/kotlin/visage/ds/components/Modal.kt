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

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            style.apply {
                position = "fixed"
                backgroundColor = "rgba(0,0,0,0.8)"
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
                            style.apply { display = "inline-block" }
                            div {
                                style.apply { display = "inline-block"; textAlign = "left" }
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

    fun renderDialog(receiver: Components, children: List<AComponent<*>>) {
        receiver.div {
            style.apply { width = "600px"; backgroundColor = "#fff"; boxShadow = "1px 1px 20px rgba(0,0,0,0.8)" }

            div {
                style.apply {
                    display = "flex"
                    width = "100%"
                    padding = "16px"
                    backgroundColor = Skin.palette.modalHeaderBackground
                    alignItems = "center"
                }

                div {
                    style.apply { flex = "1"; color = "#fff"; fontSize = "18px"; fontWeight = "900" }
                    +this@CModal.title
                }
            }

            if (this@CModal.isLoading) {
                div {
                    style.apply {
                        width = "100%"
                    }
                    HorizontalLoadIndicator(null)
                }
            }

            if (this@CModal.error != null) {
                div {
                    style.apply {
                        padding = "16px";
                        fontSize = "14px";
                        backgroundColor = Skin.palette.errorColor
                        color = " #fff";
                        textAlign = "center"
                    }
                    +this@CModal.error!!
                }
            }


            div {
                style.apply { padding = "16px"; width = "100%"; marginTop = "16px" }
                if (this@CModal.isLoading) {
                    style.opacity = "0.5"
                    style.pointerEvents = "none"
                }

                children.forEach {
                    div {style.height = "16px"}
                    +it
                }
            }
        }
    }

}

fun Components.Modal(
        title: String,
        init: CModal.() -> Unit
) = this.registerComponent(CModal(title), init)