package visage.ds.components.page

import visage.core.AComponent
import visage.core.APureComponent
import visage.core.APureComposite
import visage.core.Components
import visage.dom.CssClass
import visage.dom.Listener
import visage.dom.div
import visage.ds.colorpalette.Skin
import visage.ds.components.*
import visage.ds.utils.EFontWeight

class Header(val title: String?, val init: (CHeader.() -> Unit) = {})

class CHeader(
    val title: String?,
    private val small: Boolean,
    private val menuVisible: Boolean,
    private val tabs: List<CTab>,
    private val selectedTabIndex: Int,
    private val isLoading: Boolean
) : APureComponent() {

    internal var onSelectedTabChanged: Listener<Int>? = null

    override fun Components.render(children: List<AComponent<*>>) {
        div {
            classes = rootHeaderStyle

            div {
                classes = mainHeaderStyle
                if (this@CHeader.small) {
                    style.height = "42px"
                }

                if (this@CHeader.isLoading) {
                    attr["disabled"] = "true"
                    style.pointerEvents = "none"
                    style.opacity = "0.5"
                }

                if (this@CHeader.menuVisible) {
                    IconButton("menu") {
                        variant = EButtonVariant.Link
                        color = EButtonColor.Secondary
                        onClick = this@CHeader::onMenuButtonClicked
                        size = if (this@CHeader.small) EButtonSize.Normal else EButtonSize.Large
                    }

                    div { style.width = "16px"; style.minWidth = "16px"; style.maxWidth = "16px" }
                }

                div {
                    classes = titleStyle
                    if (this@CHeader.small) {
                        style.fontSize = "16px"
                    }
                    if (this@CHeader.title != null) {
                        +this@CHeader.title
                    }
                }

                for (child in children) {
                    div {
                        style.width = "16px"
                        style.minWidth = "16px"
                        style.maxWidth = "16px"
                    }
                    +child
                }
            }

            // bottom load balancer
            div {
                style.apply {
                    width = "100%"
                    height = "3px"
                    minHeight = height
                }
            }

            if (this@CHeader.tabs.size > 1) {
                div {
                    classes = subHeaderStyle

                    this@CHeader.tabs.forEachIndexed { index, tab ->
                        div {
                            style.apply {
                                width = "16px"
                                minWidth = width
                                maxWidth = width
                            }
                        }
                        Tab(tab.title, index == this@CHeader.selectedTabIndex) {
                            this@CHeader.onSelectedTabChanged?.invoke(index)
                        }
                        div {
                            style.apply {
                                width = "16px"
                                minWidth = width
                                maxWidth = width
                            }
                        }

                    }
                }
            }
        }
    }

    fun IconButton(icon: String, color: EButtonColor = EButtonColor.Secondary, onClick: (() -> Unit)) {
        this.registerComponent(CButton(null, icon)) {
            size = if (this@CHeader.small) EButtonSize.Small else EButtonSize.Normal
            variant = EButtonVariant.Link
            this.color = color
            this.onClick = onClick
        }
    }

    fun ResponsiveButtonWithIcon(
        icon: String,
        title: String,
        color: EButtonColor = EButtonColor.Primary,
        onClick: (() -> Unit)
    ) =
        this.registerComponent(CResponsiveButton(icon, title)) {
            size = if (this@CHeader.small) EButtonSize.Small else EButtonSize.Normal
            variant = EButtonVariant.Filled
            isIconVisibleWhenUncompressed = true
            this.color = color
            this.onClick = onClick
        }

    fun ResponsiveButton(
        icon: String,
        title: String,
        color: EButtonColor = EButtonColor.Primary,
        onClick: (() -> Unit)
    ) =
        this.registerComponent(CResponsiveButton(icon, title)) {
            size = if (this@CHeader.small) EButtonSize.Small else EButtonSize.Normal
            variant = EButtonVariant.Filled
            isIconVisibleWhenUncompressed = false
            this.color = color
            this.onClick = onClick
        }

    private fun onMenuButtonClicked() {
        if (SideMenu.isOpened) {
            SideMenu.close()
        } else {
            SideMenu.open()
        }
    }

}

val rootHeaderStyle by CssClass {
    width = "100%"
    minWidth = width
    maxWidth = width
    overflow = "hidden"
    backgroundColor = Skin.palette.mainHeaderBgColor
}

val mainHeaderStyle by CssClass {
    width = "100%"
    minWidth = width
    maxWidth = width
    height = "48px"
    minHeight = height
    maxHeight = height
    overflow = "hidden"
    display = "flex"
    alignItems = "center"
    paddingLeft = "14px"
    paddingRight = "16px"
    backgroundColor = Skin.palette.mainHeaderBgColor
}

val titleStyle by CssClass {
    flexGrow = "1"
    overflow = "hidden"
    textOverflow = "ellipsis"
    whiteSpace = "nowrap"
    color = Skin.palette.strongTextColor
    fontSize = "18px"
    fontWeight = EFontWeight.SemiBold.cssValue
}

val subHeaderStyle by CssClass {
    width = "100%"
    minWidth = width
    maxWidth = width
    height = "48px"
    minHeight = height
    maxHeight = height
    overflow = "hidden"
    display = "flex"
    //paddingLeft = "16px"
    paddingRight = "16px"
    backgroundColor = Skin.palette.mainHeaderBgColor
}

// -------------------------------------------------------------------------------------------------

class CTab(val title: String, val body: CPageBody.() -> Unit) {

}

// -------------------------------------------------------------------------------------------------

class CPageBody() : APureComposite() {
    override fun Components.render(children: List<AComponent<*>>) {
        children.forEachIndexed { index, child ->
            if (index > 0) {
                div {
                    style.apply {
                        width = "32px"
                        minWidth = width
                        maxWidth = width
                        height = width
                        minHeight = height
                        maxHeight = height
                    }
                }
            }
            +child
        }
    }

}

// -------------------------------------------------------------------------------------------------

private fun Components.Tab(title: String, isSelected: Boolean, onClick: () -> Unit) =
    this.registerFunctionalComponent({}) {
        div {
            classes = tabStyle
            if (isSelected) {
                style.apply {
                    color = Skin.palette.primaryColor
                    borderBottom = "3px solid ${Skin.palette.primaryColor}"
                }

            }

            events.onClick = {
                onClick()
            }

            div {
                style.display = "inline-block"

                +title
            }
        }
    }

private val tabStyle by CssClass {
    height = "100%"
    minHeight = height
    maxHeight = height
    flexGrow = "0"
    flexShrink = "0"
    borderTop = "3px solid transparent"
    borderBottom = "3px solid transparent"
    fontSize = "16px"
    fontWeight = EFontWeight.SemiBold.cssValue
    color = Skin.palette.weakTextColor
    cursor = "pointer"
    overflow = "hidden"
    textOverflow = "ellipsis"
    display = "flex"
    alignItems = "center"
    pseudo {
        hover {
            opacity = "0.75"
        }
        active {
            opacity = "0.5"
        }
    }
}