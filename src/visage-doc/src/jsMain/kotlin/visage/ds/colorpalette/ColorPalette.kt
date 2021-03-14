package visage.ds.colorpalette

import visage.core.Visage

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
    val weekTextColor: String

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

    val strongSeparatorColor: String
    val weekSeparatorColor: String
    val negativeStrongSeparatorColor: String
    val negativeWeekSeparatorColor: String

}

class DefaultLightPalette : IColorPalette {

    override val primaryColor: String = "#00BCD4"
    override val secondaryColor: String = "#555"

    override val errorColor: String = "#D32F2F"

    override val infoColor: String = "#03A9F4"
    override val successColor: String = "#4CAF50"
    override val warningColor: String = "#FF9800"
    override val dangerColor: String = "#F44336"

    override val normalTextColor: String = "rgba(0,0,0, 0.8)"
    override val strongTextColor: String = "rgba(0,0,0, 0.9)"
    override val weekTextColor: String = "rgba(0,0,0, 0.5)"

    override val negativeNormalTextColor: String = "rgba(255,255,255, 0.8)"
    override val negativeStrongTextColor: String = "rgba(255,255,255, 0.9)"
    override val negativeWeakTextColor: String = "rgba(255,255,255, 0.5)"

    override val bgColor: String = "#f4f4f4"

    override val mainHeaderBgColor: String = "#fff"
    override val subHeaderBgColor: String = "#e8e8e8"
    override val subHeader2BgColor: String = "#eee"

    override val cardBgColor: String = "#fff"
    override val cardFooterBgColor: String = "#f8f8f8"

    override val sideMenuBgColor: String = "#202a2f"

    override val strongSeparatorColor: String = "rgba(0,0,0, 0.3)"
    override val weekSeparatorColor: String = "rgba(0,0,0, 0.15)"
    override val negativeStrongSeparatorColor: String = "rgba(255,255,255, 0.3)"
    override val negativeWeekSeparatorColor: String = "rgba(255,255,255, 0.15)"

//    override val bgColor: String = "#f8f8f8"
//
//    override val primaryColor: String = "#4FC0E8"
//    override val primaryLightColor: String = "#66D4F1"
//    override val primaryDarkColor: String = "#3AADD9"
//
//    override val successColor: String = "#9ed36a"
//    override val successLightColor: String = "#b4e080"
//    override val successDarkColor: String = "#8ac05d"
//
//    override val warningColor: String = "#fecd57"
//    override val warningLightColor: String = "#fcd277"
//    override val warningDarkColor: String = "#f5ba45"
//
//    override val dangerColor: String = "#ec5564"
//    override val dangerLightColor: String = "#f76d82"
//    override val dangerDarkColor: String = "#d94452"
//
//    override val errorColor: String = "#ec5564"
//    override val errorLightColor: String = "#f76d82"
//    override val errorDarkColor: String = "#d94452"
//
//    override val textStrong: String = "rgba(0,0,0, 0.9)"
//    override val textMedium: String = "rgba(0,0,0, 0.6)"
//    override val textWeak: String = "rgba(0,0,0, 0.4)"
//
//    override val lightTextStrong: String = "#fff"
//    override val lightTextMedium: String = "rgba(255,255,255, 0.7)"
//    override val lightTextWeak: String = "rgba(255,255,255, 0.4)"
//
//    override val fieldNormalBorderColor: String = "#ccc"
//    override val fieldDisabledBorderColor: String = "#888"
//    override val fieldErrorBorderColor: String = errorDarkColor
//    override val fieldFocusedBorderColor: String = primaryColor
//
//    override val fieldNormalBgColor: String = "#f4f4f4"
//    override val fieldDisabledBgColor: String = "#ccc"
//
//    override val modalHeaderBackground: String = primaryDarkColor
//
//    override val menuBackground: String = "#132b43"
//    override val menuGroupColor: String = lightTextWeak
//    override val menuItemNormalColor: String = lightTextMedium
//    override val menuItemSelectedColor: String = primaryColor
//    override val menuSeparatorColor: String = "rgba(255,255,255, 0.1)"
//
//    override val headerBgColor: String = "#265886"
//    override val headerTextColor: String = "#fff"
//
//    override val sectionBgColor: String = "#fff"


}

object Skin {
    var palette: IColorPalette = DefaultLightPalette()
    get () {
        return field
    }
    set (value) {
        field = value
        Visage.rerender()
    }
}