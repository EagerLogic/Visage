package visagedoc.components

import visage.core.*
import visage.dom.CssClass
import visage.dom.div
import visage.dom.tag
import visage.ds.colorpalette.Skin
import visage.ds.components.*

class CDocPage : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        Card {
            body {
                div {
                    style.width = "100%"
                    style.padding = "16px"

                    children.forEach {
                        +it
                    }
                }
            }
        }
    }
}

fun Components.DocPage(init: CDocPage.() -> Unit) = this.registerComponent(CDocPage(), init)

fun CDocPage.title(title: String) = this.registerFunctionalComponent({}) {
    tag("h2") {
        style.color = Skin.palette.strongTextColor
        +title
    }
}

fun CDocPage.subTitle(title: String) = this.registerFunctionalComponent({}) {
    tag("h3") {
        style.color = Skin.palette.strongTextColor
        +title
    }
}

fun CDocPage.p(init: CFunctionalComponent.() -> Unit) = this.registerFunctionalComponent(init) {
    tag("p") {
        style.textAlign = "justify"
        style.fontSize = "14px"
        style.color = Skin.palette.normalTextColor
        it.forEach {
            +it
        }
    }
}

fun CDocPage.codeBlock(code: String) = this.registerFunctionalComponent({}) {
    div {
        classes = codeBlockStyle

        +code
    }
}

private val codeBlockStyle by CssClass {
    width = "100%"
    padding = "16px"
    backgroundColor = "#444548"
    color = "#fda"
    whiteSpace = "pre"
    fontSize = "14px"
    fontFamily = "monospace, monospace"
    overflow = "auto"
    borderRadius = "16px"
}

class CDocProperties() : APureComponent() {

    private val props = mutableListOf<DocProp>()

    fun prop(name: String, desc: String, default: String = "") {
        props.add(DocProp(name, desc, default))
    }

    override fun Components.render(children: List<AComponent<*>>) {
        tag("h2") {
            style.color = Skin.palette.strongTextColor
            +"Properties"
        }

        tag("p") {
            Table {
                columns {
                    fixedColumn("Name")
                    responsiveColumn("Description")
                    responsiveColumn("Default value")
                }
                body {
                    this@CDocProperties.props.forEach {
                        row {
                            cell(it.name )
                            cell(it.desc )
                            cell(it.default )
                        }
                    }
                }
            }
        }
    }

}

fun CDocPage.properties(init: CDocProperties.() -> Unit) = this.registerComponent(CDocProperties(), init)

class DocProp(val name: String, val desc: String, val default: String)

