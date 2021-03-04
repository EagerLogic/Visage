package visagedoc.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.APureComposite
import visage.core.Components
import visage.dom.tag
import visage.dom.text
import visage.ds.components.*

class CDocPage : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        children.forEach {
            this.addChild(it)
        }
    }
}

fun Components.DocPage(init: CDocPage.() -> Unit) = this.registerComponent(CDocPage(), init)

class CDocTitle(val title: String) : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        tag("h2") {
            text(this@CDocTitle.title)
        }
    }

}

fun CDocPage.title(title: String) = this.registerComponent(CDocTitle(title), {})

class CDocParagraph() : APureComposite() {
    override fun Components.render(children: List<AComponent<*>>) {
        tag("p") {
            children.forEach {
                this.addChild(it)
            }
        }
    }

}

fun CDocPage.p(init: CDocParagraph.() -> Unit) = this.registerComponent(CDocParagraph(), init)

fun CDocPage.codeBlock(code: String) = this.registerComponent(CCodeBlock(code), {})

class CDocProperties() : APureComponent() {

    private val props = mutableListOf<DocProp>()

    fun prop(name: String, desc: String, default: String = "") {
        props.add(DocProp(name, desc, default))
    }

    override fun Components.render(children: List<AComponent<*>>) {
        tag("h2") {
            text("Properties")
        }

        tag("p") {
            Table {
                head {
                    cell {
                        text("Name")
                    }
                    cell {
                        text("Description")
                    }
                    cell {
                        text("Default value")
                    }
                }
                body {
                    this@CDocProperties.props.forEach {
                        row {
                            cell { text(it.name) }
                            cell { text(it.desc) }
                            cell { text(it.default) }
                        }
                    }
                }
            }
        }
    }

}

fun CDocPage.properties(init: CDocProperties.() -> Unit) = this.registerComponent(CDocProperties(), init)

class DocProp(val name: String, val desc: String, val default: String)

