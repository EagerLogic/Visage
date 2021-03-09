package visagedoc.components

import visage.core.*
import visage.dom.Css
import visage.dom.div
import visage.dom.tag
import visage.ds.components.*

class CDocPage : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        div {
            style.apply {
                width = "100%"
                display = "flex"
                justifyContent = "center"
            }

            div {
                style.width = "100%"
                style.maxWidth = "100%"

                children.forEach {
                    +it
                }
            }
        }
    }
}

fun Components.DocPage(init: CDocPage.() -> Unit) = this.registerComponent(CDocPage(), init)
fun CBasePageContent.DocPage(init: CDocPage.() -> Unit) = this.registerComponent(CDocPage(), init)

fun CDocPage.title(title: String) = this.registerFunctionalComponent({}) {
    tag("h2") {
        +title
    }
}

fun CDocPage.subTitle(title: String) = this.registerFunctionalComponent({}) {
    tag("h3") {
        +title
    }
}

fun CDocPage.p(init: CFunctionalComponent.() -> Unit) = this.registerFunctionalComponent(init) {
    tag("p") {
        style.textAlign = "justify"
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

private val codeBlockStyle = Css.createClass {
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
            +"Properties"
        }

        tag("p") {
            Table {
                head {
                    cell("Name")
                    cell("Description")
                    cell("Default value")
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

