package visage.ds.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.APureComposite
import visage.core.Components
import visage.dom.tag
import visage.ds.utils.EHAlign
import visage.ds.utils.EVAlign

class CTable : APureComponent() {

    override fun Components.render(children: List<AComponent<*>>) {
        tag("table") {
            style.width = "100%"
            style["border-collapse"] = "collapse"

            children.forEach {
                +it
            }
        }
    }

}

fun Components.Table(init: CTable.() -> Unit) = this.registerComponent(CTable(), init)

class CTableHead : APureComposite() {
    override fun Components.render(children: List<AComponent<*>>) {
        tag("thead") {
            tag("tr") {
                style["border-bottom"] = "2px solid #ccc"

                children.forEach {
                    +it
                }
            }
        }
    }

}

fun CTable.head(init: CTableHead.() -> Unit) = this.registerComponent(CTableHead(), init)

class CTableBody : APureComponent() {

    override fun Components.render(children: List<AComponent<*>>) {
        tag("tbody") {
            children.forEach {
                +it
            }
        }
    }

}

fun CTable.body(init: CTableBody.() -> Unit) = this.registerComponent(CTableBody(), init)

class CTableRow(val height: Int?) : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        tag("tr") {
            style["border-bottom"] = "1px solid #ddd"
            style["height"] = if (this@CTableRow.height != null) " height: ${this@CTableRow.height}px;" else ""

            children.forEach {
                +it
            }
        }
    }

}

fun CTableBody.row(height: Int? = null, init: CTableRow.() -> Unit) =
        this.registerComponent(CTableRow(height), init)

class MTableCell(
    val isHead: Boolean,
    val width: String,
    val hAlign: EHAlign,
    val vAlign: EVAlign
) : APureComposite() {

    override fun Components.render(children: List<AComponent<*>>) {
        tag(if (this@MTableCell.isHead) "th" else "td") {
            style["padding"] = "8px"
            style["border"] = "none"
            style["width"] = this@MTableCell.width
            style["text-align"] = when (this@MTableCell.hAlign) {
                EHAlign.Left -> "left"
                EHAlign.Center -> "center"
                EHAlign.Right -> "right"
            }
            style["vertical-align"] = when (this@MTableCell.vAlign) {
                EVAlign.Top -> "top"
                EVAlign.Middle -> "middle"
                EVAlign.Bottom -> "bottom"
            }

            children.forEach {
                +it
            }
        }
    }

}

fun CTableRow.cell(
    width: String = "auto",
    hAlign: EHAlign = EHAlign.Left,
    vAlign: EVAlign = EVAlign.Top,
    init: MTableCell.() -> Unit
) = this.registerComponent(MTableCell(false, width, hAlign, vAlign), init)

fun CTableHead.cell(
    width: String = "auto",
    hAlign: EHAlign = EHAlign.Left,
    vAlign: EVAlign = EVAlign.Top,
    init: MTableCell.() -> Unit
) = this.registerComponent(MTableCell(true, width, hAlign, vAlign), init)