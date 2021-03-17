package visage.ds.components

import visage.core.AComponent
import visage.core.Components
import visage.dom.*
import visage.ds.colorpalette.Skin
import visage.ds.utils.EFontWeight
import visage.ds.utils.EHAlign
import visage.ds.utils.EVAlign
import visage.ds.utils.RenderMode

fun Components.Table(init: CTable.() -> Unit) = this.registerComponent(CTable(), init)

class CTable() : AComponent<CTable.Companion.State>() {

    companion object {
        class State {
            var expandedColIndex: Int? = null
        }
    }

    private val columns = mutableListOf<ColumnDescriptor>()
    private var bodyDescriptor = BodyDescriptor(columns, {})

    override fun initState(): State {
        return State()
    }

    override fun Components.render(children: List<AComponent<*>>) {
        tag("table") {
            classes = tableStyle

            // head
            this@CTable.renderHead(this)

            // body
            this@CTable.bodyDescriptor.render(this, this@CTable.state.expandedColIndex)
        }
    }

    private fun renderHead(parent: CTag) {
        parent.renderHead()
    }

    private fun CTag.renderHead() {
        tag("thead") {
            tag("tr") {
                classes = headRowStyle

                this@CTable.columns.forEach { col ->
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

    fun columns(init: ColumnsDescriptor.() -> Unit) {
        ColumnsDescriptor(columns).init()
    }

    fun body(init: BodyDescriptor.() -> Unit) {
        this.bodyDescriptor = BodyDescriptor(columns) {
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

class ColumnsDescriptor(private val columns: MutableList<ColumnDescriptor>) {

    fun fixedColumn(title: String, width: String? = null, hAlign: EHAlign = EHAlign.Left, vAlign: EVAlign = EVAlign.Middle) {
        columns.add(ColumnDescriptor(title, false, hAlign, vAlign, width))
    }

    fun responsiveColumn(title: String, width: String? = null, hAlign: EHAlign = EHAlign.Left, vAlign: EVAlign = EVAlign.Middle) {
        columns.add(ColumnDescriptor(title, true, hAlign, vAlign, width))
    }

}

class ColumnDescriptor(
    val title: String,
    val collapsible: Boolean,
    val hAlign: EHAlign,
    val vAlign: EVAlign,
    val width: String?
)

class BodyDescriptor(private val colums: List<ColumnDescriptor>, private val onExpandedStateChanged: Listener<Int?>) {

    private val rows = mutableListOf<RowDescriptor>()

    fun row(init: RowDescriptor.() -> Unit) {
        val index = rows.size
        val row = RowDescriptor(colums) {
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
                row.render(this, RenderMode.compressed && index == expandedRowIndex, expandable)
            }
        }
    }

}

class RowDescriptor(private val columns: List<ColumnDescriptor>, private val onExpandedStateChanged: Listener<Boolean>) {

    private val cells = mutableListOf<ACellDescriptor>()
    var onClick: Listener<Unit>? = null

    fun cell(text: String) {
        cells.add(TextCellDescriptor(text))
    }

    fun cell(init: CTag.() -> Unit) {
        cells.add(CellDescriptor(init))
    }

    internal fun render(parent: CTag, isExpanded: Boolean, expandable: Boolean) {
        parent.renderRow(isExpanded, expandable)
    }

    private fun CTag.renderRow(isExpanded: Boolean, expandable: Boolean) {
        tag("tr") {
            if (!isExpanded) {
                style.borderBottom = "1px solid ${Skin.palette.weakSeparatorColor}"
            }

            if (this@RowDescriptor.onClick != null) {
                classes = rowHoveredStyle
                events.onClick = {
                    this@RowDescriptor.onClick!!.invoke(Unit)
                }
            } else if (RenderMode.compressed && expandable) {
                classes = rowHoveredStyle
                events.onClick = {
                    this@RowDescriptor.onExpandedStateChanged(!isExpanded)
                }
            }

            this@RowDescriptor.cells.forEachIndexed { index, cell ->
                if (RenderMode.compressed && columns[index].collapsible) {
                    return@forEachIndexed
                }
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
        if (RenderMode.compressed && isExpanded) {
            tag("tr") {
                style["border-bottom"] = "1px solid ${Skin.palette.weakSeparatorColor}"

                tag("td") {
                    attr["colspan"] = "1000"
                    style.padding = "8px"

                    tag("table") {
                        style.apply {
                            width = "100%"
                            minWidth = width
                            maxWidth = width
                        }
                        cells.forEachIndexed { index, cell ->
                            if (!(RenderMode.compressed && columns[index].collapsible)) {
                                return@forEachIndexed
                            }

                            tag("tr") {
                                tag("td") {
                                    style.apply {
                                        fontSize = "12px"
                                        fontWeight = EFontWeight.SemiBold.cssValue
                                        color = Skin.palette.strongTextColor
                                        width = "50%"
                                        verticalAlign = "middle"
                                        padding = "8px"
                                    }
                                    +this@RowDescriptor.columns[index].title
                                }
                                tag("td") {
                                    style.apply {
                                        fontSize = "12px"
                                        fontWeight = EFontWeight.Medium.cssValue
                                        color = Skin.palette.strongTextColor
                                        width = "50%"
                                        verticalAlign = "middle"
                                        textAlign = "right"
                                        padding = "8px"
                                    }
                                    cell.render(this)
                                }
                            }
                        }
                    }

                }
            }
        }
    }

}

abstract class ACellDescriptor {

    internal fun render(parent: CTag) {
        parent.renderCell()
    }

    protected abstract fun CTag.renderCell()

}

class TextCellDescriptor(private val text: String) : ACellDescriptor() {
    override fun CTag.renderCell() {
        +this@TextCellDescriptor.text
    }

}

class CellDescriptor(private val renderer: CTag.() -> Unit) : ACellDescriptor() {
    override fun CTag.renderCell() {
        this.renderer()
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

