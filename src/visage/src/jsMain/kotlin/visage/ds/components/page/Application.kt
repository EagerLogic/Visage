package visage.ds.components.page

import visage.core.*
import visage.dom.CssClass
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.utils.RenderMode

fun Components.Application(init: CApplication.() -> Unit) = this.registerComponent(CApplication(), init)

class CApplication : APureComposite() {

    override fun Components.render(children: List<AComponent<*>>) {

        div {
            classes = basePageRootStyle

            div {
                classes = sideBarRootStyle
                style.left = if (SideMenu.isOpened) {
                    "0px"
                } else {
                    "-300px"
                }

                var menuFound = false
                children.forEach {
                    if (menuFound) {
                        return@forEach
                    }

                    if (it is CMenu) {
                        menuFound = true
                        +it
                    }
                }
            }



            if (!RenderMode.collapsed && SideMenu.isOpened) {
                div {
                    style.apply {
                        width = "280px"
                        minWidth = width
                        maxWidth = width
                        height = "100%"
                    }
                }
            }

            if (RenderMode.collapsed && SideMenu.isOpened) {
                div {
                    classes = sideBarBlenderStyle
                    events.onClick = {
                        SideMenu.close()
                    }
                }
            }

            div {
                classes = contentRootStyle
                if (RenderMode.collapsed && SideMenu.isOpened) {
                    style["filter"] = "blur(4px)"
                }

                children.forEach {
                    if (!(it is CMenu)) {
                        +it
                    }
                }
            }
        }
    }

}

private val basePageRootStyle by CssClass  {
    display = "flex"
    minHeight = "100%"
}

private val sideBarRootStyle by CssClass {
    width = "280px"
    minWidth = width
    maxWidth = width
    height = "100%"
    minHeight = height
    maxHeight = height
    position = "fixed"
    left = "0px"
    top = "0px"
    bottom = "0px"
    backgroundColor = Skin.palette.sideMenuBgColor
    boxShadow = "1px 1px 10px rgba(0,0,0, 0.5)"
    zIndex = "1010"
}

private val sideBarBlenderStyle by CssClass {
    position = "fixed"
    left = "0px"
    top = "0px"
    right = "0px"
    bottom = "0px"
    backgroundColor = Skin.palette.blurColor
    zIndex = "1000"
    cursor = "pointer"
}

private val contentRootStyle by CssClass {
    flexGrow = "1"
    minHeight = "100%"
    backgroundColor = Skin.palette.bgColor
}

