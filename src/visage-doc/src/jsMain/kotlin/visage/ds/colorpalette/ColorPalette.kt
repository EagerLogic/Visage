package visage.ds.colorpalette

import visage.core.Visage

interface IColorPalette {

    val bgColor: String

    val primaryColor: String
    val primaryLightColor: String
    val primaryDarkColor: String

    val successColor: String
    val successLightColor: String
    val successDarkColor: String

    val warningColor: String
    val warningLightColor: String
    val warningDarkColor: String

    val dangerColor: String
    val dangerLightColor: String
    val dangerDarkColor: String

    val errorColor: String
    val errorLightColor: String
    val errorDarkColor: String

    val textStrong: String
    val textMedium: String
    val textWeak: String

    val lightTextStrong: String
    val lightTextMedium: String
    val lightTextWeak: String


    val fieldNormalBgColor: String
    val fieldDisabledBgColor: String

    val fieldNormalBorderColor: String
    val fieldFocusedBorderColor: String
    val fieldErrorBorderColor: String
    val fieldDisabledBorderColor: String


    val modalHeaderBackground: String

    val menuBackground: String
    val menuGroupColor: String
    val menuItemNormalColor: String
    val menuItemSelectedColor: String
    val menuSeparatorColor: String

    val headerBgColor: String
    val headerTextColor: String

}

class DefaultLightPalette : IColorPalette {
    override val bgColor: String = "#ffffff"

    override val primaryColor: String = "#4FC0E8"
    override val primaryLightColor: String = "#66D4F1"
    override val primaryDarkColor: String = "#3AADD9"

    override val successColor: String = "#9ed36a"
    override val successLightColor: String = "#b4e080"
    override val successDarkColor: String = "#8ac05d"

    override val warningColor: String = "#fecd57"
    override val warningLightColor: String = "#fcd277"
    override val warningDarkColor: String = "#f5ba45"

    override val dangerColor: String = "#ec5564"
    override val dangerLightColor: String = "#f76d82"
    override val dangerDarkColor: String = "#d94452"

    override val errorColor: String = "#ec5564"
    override val errorLightColor: String = "#f76d82"
    override val errorDarkColor: String = "#d94452"

    override val textStrong: String = "rgba(0,0,0, 0.9)"
    override val textMedium: String = "rgba(0,0,0, 0.6)"
    override val textWeak: String = "rgba(0,0,0, 0.4)"

    override val lightTextStrong: String = "#fff"
    override val lightTextMedium: String = "rgba(255,255,255, 0.7)"
    override val lightTextWeak: String = "rgba(255,255,255, 0.4)"

    override val fieldNormalBorderColor: String = "#ccc"
    override val fieldDisabledBorderColor: String = "#888"
    override val fieldErrorBorderColor: String = errorDarkColor
    override val fieldFocusedBorderColor: String = primaryColor

    override val fieldNormalBgColor: String = "#f4f4f4"
    override val fieldDisabledBgColor: String = "#ccc"

    override val modalHeaderBackground: String = primaryDarkColor

    override val menuBackground: String = "#132b43"
    override val menuGroupColor: String = lightTextWeak
    override val menuItemNormalColor: String = lightTextMedium
    override val menuItemSelectedColor: String = primaryColor
    override val menuSeparatorColor: String = "rgba(255,255,255, 0.1)"

    override val headerBgColor: String = "#009688"
    override val headerTextColor: String = "#fff"


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