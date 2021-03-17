package visage.ds.components.page

import visage.core.*
import visage.dom.CssClass
import visage.dom.div
import visage.dom.tag
import visage.ds.colorpalette.Skin
import visage.ds.components.Icon
import visage.ds.components.VScrollBox
import visage.ds.utils.EFontWeight
import visage.ds.utils.ERenderMode
import visage.ds.utils.RenderMode

object SideMenu {

    var isOpened = !RenderMode.collapsed
        get
        private set(value) {
            if (field != value) {
                field = value
                Visage.rerender()
                onOpenedStateChanged.fire(value)
            }
        }
    val onOpenedStateChanged = Event<Boolean>()

    init {
        RenderMode.onChange.addListener {
            isOpened = it == ERenderMode.Full
        }
    }

    fun open() {
        if (isOpened) {
            return
        }
        isOpened = true
    }

    fun close() {
        if (!isOpened) {
            return
        }
        isOpened = false
    }
}

// ---------------------------------------------------------------------------------------------------------------------

class CMenu : APureComponent() {

    /**
     * The URL of the logo image. This will be displayed at the bottom of the menu. If null, than an empty space is rendered.
     * The image height will be scaled to 48px and the width willl be scaled proportionally.
     */
    var logoImageUrl: String? = null

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = menuRootStyle

            div {
                classes = menuHeaderStyle

                if (this@CMenu.logoImageUrl != null) {
                    tag("img") {
                        attr["src"] = this@CMenu.logoImageUrl
                        style.apply {
                            height = "48px"
                            width = "auto"
                        }
                    }
                }

            }

            div {
                style.apply {
                    width = "100%"; minHeight = "1px"; backgroundColor = Skin.palette.sideMenuSeparatorColor
                }
            }


            div {
                classes = menuTopBlockStyle

                VScrollBox("100%") {
                    padding = "0px"
                    lightScrollThumb = true

                    div { style.apply { minHeight = "16px" } }

                    var isTopItemFound = false
                    children.forEach {
                        if (isTopItemFound) {
                            return@forEach
                        }

                        if (it is CMenuItemBlock) {
                            if (!it.isBottom) {
                                isTopItemFound = true
                                +it
                            }
                        }
                    }

                    div { style.apply { minHeight = "16px" } }
                }
            }

            div {
                classes = menuBottomBlockStyle

                var isBottomItemFound = false
                children.forEach {
                    if (isBottomItemFound) {
                        return@forEach
                    }

                    if (it is CMenuItemBlock) {
                        if (it.isBottom) {
                            isBottomItemFound = true
                            div {
                                style.apply {
                                    width = "100%"; height = "1px"; backgroundColor =
                                    Skin.palette.sideMenuSeparatorColor
                                }
                            }
                            +it
                        }
                    }
                }
            }

        }
    }

}

/**
 * Use this component to render a left side menu and define it's content.
 *
 * @param init Define the content of the menu inside this init block.
 */
fun CApplication.menu(init: CMenu.() -> Unit) = this.registerComponent(CMenu(), init)

private val menuRootStyle by CssClass {
    width = "100%"
    minWidth = width
    maxWidth = width
    height = "100%"
    minHeight = height
    maxHeight = height
    display = "flex"
    flexDirection = "column"
    overflow = "hidden"
}

private val menuHeaderStyle by CssClass {
    width = "100%"
    maxWidth = "100%"
    minHeight = "64px"
    height = "64px"
    maxHeight = "64px"
    overflow = "hidden"
    display = "flex"
    alignItems = "center"
    justifyContent = "flex-start"
    padding = "8px"
}

private val menuTopBlockStyle by CssClass {
    width = "100%"
    flexGrow = "1"
    overflow = "hidden"
}

private val menuBottomBlockStyle by CssClass {
    width = "100%"
}

// ---------------------------------------------------------------------------------------------------------------------

class CMenuItemBlock(val isBottom: Boolean) : APureComponent() {

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = menuItemBlockRootStyle

            children.forEach {
                +it
            }
        }
    }

}

fun CMenu.items(init: CMenuItemBlock.() -> Unit) = this.registerComponent(CMenuItemBlock(false), init)
fun CMenu.footer(init: CMenuItemBlock.() -> Unit) = this.registerComponent(CMenuItemBlock(true), init)

private val menuItemBlockRootStyle by CssClass {
    width = "100%"
    display = "flex"
    flexDirection = "column"
}

// ---------------------------------------------------------------------------------------------------------------------

class CGroupMenuItem(
    private val title: String,
    private val icon: String,
    private val path: String
) : APureComponent() {

    override fun Components.render(children: List<AComponent<*>>) {
        val isSelected = Navigation.currentLocation.path.startsWith(this@CGroupMenuItem.path)
        div {
            style.apply {
                width = "100%"
            }

            div {
                classes = groupMenuItemRootStyle
                events.onClick = onClick@{
                    children.forEach {
                        if (it is CChildMenuItem) {
                            Navigation.pushLocation(it.href)
                            return@onClick
                        }
                    }
                }

                div {
                    style.apply {
                        width = "48px"
                    }
                    Icon(this@CGroupMenuItem.icon, Skin.palette.sideMenuInactiveTextColor, 24)
                }

                div {
                    classes = "$groupMenuItemTitleBaseStyle $groupMenuItemTitleUnselectedStyle"

                    +this@CGroupMenuItem.title
                }
            }



            if (isSelected) {
                children.forEach {
                    +it
                }
            }
        }
    }

}

fun CMenuItemBlock.groupMenuItem(title: String, icon: String, path: String, init: CGroupMenuItem.() -> Unit) =
    this.registerComponent(CGroupMenuItem(title, icon, path), init)

private val groupMenuItemRootStyle by CssClass {
    padding = "4px 16px 4px 16px"
    cursor = "pointer"
    display = "flex"
    alignItems = "center"
    fontWeight = EFontWeight.SemiBold.cssValue
    pseudo {
        hover {
            opacity = "0.7"
        }
    }
}

private val groupMenuItemTitleBaseStyle by CssClass {
    flexGrow = "1"
    overflow = "hidden"
    textOverflow = "ellipsis"
    fontSize = "15px"
}

private val groupMenuItemTitleUnselectedStyle by CssClass {
    color = Skin.palette.sideMenuInactiveTextColor
}

// ---------------------------------------------------------------------------------------------------------------------

class CLeafMenuItem(val title: String, val icon: String, val href: String) : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        val isSelected = Navigation.currentLocation.path == this@CLeafMenuItem.href
        div {
            classes = leafMenuItemRootStyle
            events.onClick = {
                if (RenderMode.collapsed) {
                    SideMenu.close()
                }
                Navigation.pushLocation(this@CLeafMenuItem.href)
            }

            div {
                style.apply {
                    width = "48px"
                }
                Icon(
                    this@CLeafMenuItem.icon,
                    if (isSelected) Skin.palette.sideMenuActiveTextColor else Skin.palette.sideMenuInactiveTextColor,
                    24
                )
            }

            div {
                classes =
                    "$leafMenuItemTitleBaseStyle ${if (isSelected) leafMenuItemTitleSelectedStyle else leafMenuItemTitleUnselectedStyle}"

                +this@CLeafMenuItem.title
            }
        }
    }

}

fun CMenuItemBlock.menuItem(title: String, icon: String, href: String) =
    this.registerComponent(CLeafMenuItem(title, icon, href), { })

private val leafMenuItemRootStyle by CssClass {
    padding = "4px 16px 4px 16px"
    cursor = "pointer"
    display = "flex"
    alignItems = "center"
    fontWeight = EFontWeight.SemiBold.cssValue
    pseudo {
        hover {
            opacity = "0.7"
        }
    }
}

private val leafMenuItemTitleBaseStyle by CssClass {
    flexGrow = "1"
    overflow = "hidden"
    textOverflow = "ellipsis"
    fontSize = "15px"
}

private val leafMenuItemTitleUnselectedStyle by CssClass {
    color = Skin.palette.sideMenuInactiveTextColor
}

private val leafMenuItemTitleSelectedStyle by CssClass {
    color = Skin.palette.sideMenuActiveTextColor
}

// ---------------------------------------------------------------------------------------------------------------------

class CChildMenuItem(
    private val title: String,
    val href: String
) : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        val isSelected = Navigation.currentLocation.path == this@CChildMenuItem.href
        div {
            classes = childMenuItemRootStyle
            events.onClick = {
                if (RenderMode.collapsed) {
                    SideMenu.close()
                }
                Navigation.pushLocation(this@CChildMenuItem.href)
            }

            Icon("trip_origin", if (isSelected) Skin.palette.sideMenuActiveTextColor else Skin.palette.sideMenuInactiveTextColor, 10)

            div { style.width = "16px" }

            div {
                classes =
                    "$childMenuItemTitleBaseStyle ${if (isSelected) childMenuItemTitleSelectedStyle else childMenuItemTitleUnselectedStyle}"

                +this@CChildMenuItem.title
            }
        }
    }

}

fun CGroupMenuItem.menuItem(title: String, href: String) = this.registerComponent(CChildMenuItem(title, href), {})

private val childMenuItemRootStyle by CssClass {
    padding = "4px 16px 4px 64px"
    cursor = "pointer"
    display = "flex"
    alignItems = "center"
    fontWeight = EFontWeight.SemiBold.cssValue
    pseudo {
        hover {
            opacity = "0.7"
        }
    }
}

private val childMenuItemTitleBaseStyle by CssClass {
    flexGrow = "1"
    overflow = "hidden"
    textOverflow = "ellipsis"
    fontSize = "15px"
}

private val childMenuItemTitleUnselectedStyle by CssClass {
    color = Skin.palette.sideMenuInactiveTextColor
}

private val childMenuItemTitleSelectedStyle by CssClass {
    color = Skin.palette.sideMenuActiveTextColor
}

// ---------------------------------------------------------------------------------------------------------------------

class CGroup(val title: String) : APureComponent() {
    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = groupStyle

            +this@CGroup.title
        }
    }

}

val groupStyle by CssClass {
    padding = "24px 16px 12px 16px"
    width = "100%"
    minWidth = "100%"
    maxWidth = "100%"
    overflow = "hidden"
    textOverflow = "ellipsis"
    this["text-transform"] = "uppercase"
    fontSize = "12px"
    fontWeight = EFontWeight.Bold.cssValue
    color = Skin.palette.sideMenuGroupTextColor
}

fun CMenuItemBlock.group(title: String) = this.registerComponent(CGroup(title), {})

