package visage.ds.components

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.Components
import visage.dom.CssClass
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.utils.RenderMode

fun Components.HistoryList(init: CHistoryList.() -> Unit) = this.registerComponent(CHistoryList(), init)

class CHistoryList() : APureComponent() {

    private val items = mutableListOf<HistoryListItem>()

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = rootStyle

            this@CHistoryList.items.forEach {
                div { classes = separatorStyle}

                div {
                    classes = itemRootStyle

                    // icon
                    if (it.icon != null) {
                        div {
                            style.flexGrow = "0"
                            style.flexShrink = "0"

                            Icon(it.icon!!, it.iconColor, 24) {
                            }
                        }
                        div {
                            style.apply {
                                width = "8px"
                                minWidth = width
                                maxWidth = width
                            }
                        }
                    }

                    // body
                    div {
                        classes = itemBodyStyle

                        // head
                        div {
                            classes = if (RenderMode.compressed) compressedHeadStyle else normalHeadStyle

                            div {
                                style.flexGrow = "1"
                                Text(it.title, ETextStyles.Strong)
                            }
                            div {
                                style.apply {
                                    width = "4px"
                                    minWidth = width
                                    maxWidth = width
                                }
                            }
                            div {
                                style.flexGrow = "0"
                                Text(it.date, ETextStyles.Sub)
                            }
                        }

                        if (it.message != null) {
                            div {
                                style.height = "4px"
                            }

                            Text(it.message, ETextStyles.Normal)
                        }

                    }
                }
            }
        }
    }

    fun itemWithIcon(icon: String, title: String, date: String, message: String? = null, iconColor: String = Skin.palette.primaryColor) {
        this.items.add(
            HistoryListItem(
                title,
                date,
                message,
                icon,
                iconColor
            )
        )
    }

    fun item(title: String, date: String, message: String? = null) {
        this.items.add(
            HistoryListItem(
                title,
                date,
                message,
                null,
                Skin.palette.primaryColor
            )
        )
    }

}

private class HistoryListItem(val title: String, val date: String, val message: String?, val icon: String?, val iconColor: String)

private val rootStyle by CssClass {
    width = "100%"
    minWidth = width
    maxWidth = width
}

private val separatorStyle by CssClass {
    width = "100%"
    height = "1px"
    minHeight = height
    maxHeight = height
    backgroundColor = Skin.palette.weakSeparatorColor
}

private val itemRootStyle by CssClass {
    width = "100%"
    minWidth = width
    maxWidth = width
    overflow = "hidden"
    padding = "16px"
    display = "flex"
}

private val itemBodyStyle by CssClass {
    flexGrow = "1"
}

private val normalHeadStyle by CssClass {
    display = "flex"
    width = "100%"
    minWidth = width
    maxWidth = width
    overflow = "hidden"
    alignItems = "center"
}

private val compressedHeadStyle by CssClass {
    display = "flex"
    width = "100%"
    minWidth = width
    maxWidth = width
    overflow = "hidden"
    flexDirection = "column"
}