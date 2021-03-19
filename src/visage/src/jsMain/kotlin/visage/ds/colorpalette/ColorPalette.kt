package visage.ds.colorpalette

import visage.core.Visage
import visage.dom.Css

interface IColorPalette {

    val primaryColor: String
    val secondaryColor: String

    val errorColor: String

    val infoColor: String
    val successColor: String
    val warningColor: String
    val dangerColor: String

    val normalTextColor: String
    val strongTextColor: String
    val weakTextColor: String

    val negativeNormalTextColor: String
    val negativeStrongTextColor: String
    val negativeWeakTextColor: String

    val bgColor: String

    val mainHeaderBgColor: String
    val subHeaderBgColor: String
    val subHeader2BgColor: String

    val cardBgColor: String
    val cardFooterBgColor: String

    val sideMenuBgColor: String
    val sideMenuInactiveTextColor: String
    val sideMenuActiveTextColor: String
    val sideMenuSeparatorColor: String
    val sideMenuGroupTextColor: String

    val strongSeparatorColor: String
    val weakSeparatorColor: String
    val negativeStrongSeparatorColor: String
    val negativeWeekSeparatorColor: String

    val fieldBgColor: String
    val blurColor: String

}

class DefaultLightPalette : IColorPalette {

    override val primaryColor: String = "#00BCD4"
    override val secondaryColor: String = "rgba(0,0,0, 0.5)"

    override val errorColor: String = "#D32F2F"

    override val infoColor: String = "#03A9F4"
    override val successColor: String = "#4CAF50"
    override val warningColor: String = "#FF9800"
    override val dangerColor: String = "#F44336"

    override val normalTextColor: String = "rgba(0,0,0, 0.8)"
    override val strongTextColor: String = "rgba(0,0,0, 0.9)"
    override val weakTextColor: String = "rgba(0,0,0, 0.5)"

    override val negativeNormalTextColor: String = "rgba(255,255,255, 0.8)"
    override val negativeStrongTextColor: String = "rgba(255,255,255, 0.9)"
    override val negativeWeakTextColor: String = "rgba(255,255,255, 0.5)"

    override val bgColor: String = "#f4f4f4"

    override val mainHeaderBgColor: String = "#fff"
    override val subHeaderBgColor: String = "#e8e8e8"
    override val subHeader2BgColor: String = "#eee"

    override val cardBgColor: String = "#fff"
    override val cardFooterBgColor: String = "#f8f8f8"

    override val sideMenuBgColor: String = "#fff"
    override val sideMenuInactiveTextColor: String = "rgba(0,0,0, 0.6)"
    override val sideMenuActiveTextColor: String = primaryColor
    override val sideMenuSeparatorColor: String = "rgba(0,0,0, 0.2)"
    override val sideMenuGroupTextColor: String = "rgba(0,0,0, 0.4)"


    override val strongSeparatorColor: String = "rgba(0,0,0, 0.3)"
    override val weakSeparatorColor: String = "rgba(0,0,0, 0.15)"
    override val negativeStrongSeparatorColor: String = "rgba(255,255,255, 0.3)"
    override val negativeWeekSeparatorColor: String = "rgba(255,255,255, 0.15)"

    override val fieldBgColor: String = "rgba(0,0,0, 0.05)"

    override val blurColor: String = "rgba(0,0,0, 0.5)"

}

class DefaultDarkPalette : IColorPalette {
    override val primaryColor: String = "#00BCD4"
    override val secondaryColor: String = "rgba(255,255,255, 0.5)"

    override val errorColor: String = "#D32F2F"

    override val infoColor: String = "#03A9F4"
    override val successColor: String = "#4CAF50"
    override val warningColor: String = "#FF9800"
    override val dangerColor: String = "#F44336"

    override val normalTextColor: String = "rgba(255,255,255, 0.8)"
    override val strongTextColor: String = "rgba(255,255,255, 0.9)"
    override val weakTextColor: String = "rgba(255,255,255, 0.5)"

    override val negativeNormalTextColor: String = "rgba(255,255,255, 0.8)"
    override val negativeStrongTextColor: String = "rgba(255,255,255, 0.9)"
    override val negativeWeakTextColor: String = "rgba(255,255,255, 0.5)"

    override val bgColor: String = "#404040"

    override val mainHeaderBgColor: String = "#303030"
    override val subHeaderBgColor: String = "#383838"
    override val subHeader2BgColor: String = "#eee"

    override val cardBgColor: String = "#303030"
    override val cardFooterBgColor: String = "#383838"

    override val sideMenuBgColor: String = "#303030"
    override val sideMenuInactiveTextColor: String = "rgba(255,255,255, 0.6)"
    override val sideMenuActiveTextColor: String = primaryColor
    override val sideMenuSeparatorColor: String = "rgba(255,255,255, 0.2)"
    override val sideMenuGroupTextColor: String = "rgba(255,255,255, 0.4)"


    override val strongSeparatorColor: String = "rgba(255,255,255, 0.3)"
    override val weakSeparatorColor: String = "rgba(255,255,255, 0.15)"
    override val negativeStrongSeparatorColor: String = "rgba(0,0,0, 0.3)"
    override val negativeWeekSeparatorColor: String = "rgba(0,0,0, 0.15)"

    override val fieldBgColor: String = "rgba(255,255,255, 0.05)"

    override val blurColor: String = "rgba(0,0,0, 0.5)"

}

object Skin {
    var palette: IColorPalette = DefaultLightPalette()
        set(value) {
            field = value
            Css.clearCache()
            Visage.rerender()
        }
}