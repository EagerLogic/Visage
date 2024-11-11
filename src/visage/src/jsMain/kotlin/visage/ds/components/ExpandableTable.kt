package visage.ds.components

import visage.core.AComponent
import visage.core.Components
import visage.dom.*
import visage.ds.colorpalette.Skin
import visage.ds.utils.EFontWeight
import visage.ds.utils.EHAlign
import visage.ds.utils.EVAlign
import visage.ds.utils.RenderMode

fun Components.ExpandableTable(init: CExpandableTable.() -> Unit) = this.registerComponent(CExpandableTable(), init)

class CExpandableTable() : AComponent<CExpandableTable.Companion.State>() {

    companion object {
        class State {
            var expandedColIndex: Int? = null
        }
    }

    private val columns = mutableListOf<ColumnDescriptor>()
    private var bodyDescriptor = ExpandableBodyDescriptor(columns, {})

    override fun initState(): State {
        return State()
    }

    override fun Components.render(children: List<AComponent<*>>) {
        tag("table") {
            classes = tableStyle

            // head
            this@CExpandableTable.renderHead(this)

            // body
            this@CExpandableTable.bodyDescriptor.render(this, this@CExpandableTable.state.expandedColIndex)
        }
    }

    private fun renderHead(parent: CTag) {
        parent.renderHead()
    }

    private fun CTag.renderHead() {
        tag("thead") {
            tag("tr") {
                classes = headRowStyle

                this@CExpandableTable.columns.forEach { col ->
                    if (RenderMode.compressed && col.collapsible) {
                        return@forEach
                    }
                    tag("th") {
                        classes = headCellStyle
                        style.width = col.width
                        style.textAlign = when (col.hAlign) {
                            EHAlign.Left -> "left"
                            EHAlign.Center -> "center"
                            EHAlign.Right -> "right"
                        }
                        style.verticalAlign = when (col.vAlign) {
                            EVAlign.Top -> "top"
                            EVAlign.Middle -> "middle"
                            EVAlign.Bottom -> "bottom"
                        }
                        +col.title
                    }
                }
            }
        }
    }

    fun columns(init: ExpandableColumnsDescriptor.() -> Unit) {
        ExpandableColumnsDescriptor(columns).init()
    }

    fun body(init: ExpandableBodyDescriptor.() -> Unit) {
        this.bodyDescriptor = ExpandableBodyDescriptor(columns) {
            this.state.expandedColIndex = it
            this.refresh()
        }
        this.bodyDescriptor.init()
    }

}

private val tableStyle by CssClass {
    width = "100%"
    borderCollapse = "collapse"
}

private val headRowStyle by CssClass {
    borderBottom = "1px solid ${Skin.palette.strongSeparatorColor}"
}

private val headCellStyle by CssClass {
    padding = "8px 16px"
    fontSize = "12px"
    color = Skin.palette.strongTextColor
    fontWeight = EFontWeight.SemiBold.cssValue
}

private val cellStyle by CssClass {
    padding = "16px 16px"
    fontSize = "14px"
    color = Skin.palette.strongTextColor
    fontWeight = EFontWeight.Regular.cssValue
}

class ExpandableColumnsDescriptor(private val columns: MutableList<ColumnDescriptor>) {

    fun column(title: String, width: String? = null, hAlign: EHAlign = EHAlign.Left, vAlign: EVAlign = EVAlign.Middle) {
        columns.add(ColumnDescriptor(title, false, hAlign, vAlign, width))
    }

}

class ExpandableBodyDescriptor(private val colums: List<ColumnDescriptor>, private val onExpandedStateChanged: Listener<Int?>) {

    private val rows = mutableListOf<ExpandableRowDescriptor>()

    fun row(init: ExpandableRowDescriptor.() -> Unit) {
        val index = rows.size
        val row = ExpandableRowDescriptor(colums) {
            onExpandedStateChanged(if (it) index else null)
        }
        row.init()
        rows.add(row)
    }

    internal fun render(parent: CTag, expandedRowIndex: Int?) {
        parent.renderBody(expandedRowIndex)
    }

    private fun CTag.renderBody(expandedRowIndex: Int?) {
        val expandable: Boolean = colums.firstOrNull {
            it.collapsible
        } != null
        tag("tbody") {
            rows.forEachIndexed { index, row ->
                row.render(this, index == expandedRowIndex, expandable)
            }
        }
    }

}

class ExpandableRowDescriptor(private val columns: List<ColumnDescriptor>, private val onExpandedStateChanged: Listener<Boolean>) {

    private val cells = mutableListOf<ACellDescriptor>()
    var onClick: Listener<Unit>? = null
    private var expandableContentRenderer: (CTag.() -> Unit)? = null

    fun cell(text: String) {
        cells.add(TextCellDescriptor(text))
    }

    fun cell(init: CTag.() -> Unit) {
        cells.add(CellDescriptor(init))
    }

    fun expandableContent(init: CTag.() -> Unit) {
        expandableContentRenderer = init
    }

    internal fun render(parent: CTag, isExpanded: Boolean, expandable: Boolean) {
        parent.renderRow(isExpanded, expandable)
    }

    private fun CTag.renderRow(isExpanded: Boolean, expandable: Boolean) {
        tag("tr") {
            if (!isExpanded) {
                style.borderBottom = "1px solid ${Skin.palette.weakSeparatorColor}"
            }

            if (this@ExpandableRowDescriptor.onClick != null) {
                classes = rowHoveredStyle
                events.onClick = {
                    this@ExpandableRowDescriptor.onClick!!.invoke(Unit)
                }
            } else if (expandableContentRenderer != null) {
                classes = rowHoveredStyle
                events.onClick = {
                    this@ExpandableRowDescriptor.onExpandedStateChanged(!isExpanded)
                }
            }

            this@ExpandableRowDescriptor.cells.forEachIndexed { index, cell ->
                tag("td") {
                    classes = cellStyle
                    style.width = columns[index].width
                    style.textAlign = when (columns[index].hAlign) {
                        EHAlign.Left -> "left"
                        EHAlign.Center -> "center"
                        EHAlign.Right -> "right"
                    }
                    style.verticalAlign = when (columns[index].vAlign) {
                        EVAlign.Top -> "top"
                        EVAlign.Middle -> "middle"
                        EVAlign.Bottom -> "bottom"
                    }
                    cell.render(this)
                }
            }
        }
        if (isExpanded) {
            tag("tr") {
                style["border-bottom"] = "1px solid ${Skin.palette.weakSeparatorColor}"

                tag("td") {
                    attr["colspan"] = "1000"

                    expandableContentRenderer?.invoke(this)
                }
            }
        }
    }

}


private val rowHoveredStyle by CssClass {
    pseudo {
        hover {
            backgroundColor = Skin.palette.weakSeparatorColor
            cursor = "pointer"
        }
    }
}

