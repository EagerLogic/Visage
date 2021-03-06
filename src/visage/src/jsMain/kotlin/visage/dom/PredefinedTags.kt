package visage.dom

import visage.core.Components


class CATag : CTag("a") {
    /**
     * Prompts the user to save the linked URL instead of navigating to it. Can be used with or without a value
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var download: String? by attr.delegate("download")

    /**
     * The URL that the hyperlink points to. Links are not restricted to HTTP-based URLs — they can use any URL scheme
     * supported by browsers:
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var href: String? by attr.delegate("href")

    /**
     * Where to display the linked URL, as the name for a browsing context (a tab, window, or <iframe>). The following
     * keywords have special meanings for where to load the URL:
     *
     * - _self: the current browsing context. (Default)
     * - _blank: usually a new tab, but users can configure browsers to open a new window instead.
     * - _parent: the parent browsing context of the current one. If no parent, behaves as _self.
     * - _top: the topmost browsing context (the "highest" context that’s an ancestor of the current one). If no
     * ancestors, behaves as _self.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var target: String? by attr.delegate("target")

}
/**
 * The HTML <a> element (or anchor element), with its href attribute, creates a hyperlink to web pages, files, email
 * addresses, locations in the same page, or anything else a URL can address. Content within each <a> should indicate
 * the link's destination. If the href attribute is present, pressing the enter key while focused on the <a> element
 * will activate it.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.a(init: (CATag.() -> Unit) = {}) = this.registerComponent(CATag(), init)

//<abbr>
//<acronym>
//<address>
//<applet>
//<area>
//<article>
//<aside>
//<audio>



class CBTag : CTag("b")
/**
 * The HTML Bring Attention To element (<b>) is used to draw the reader's attention to the element's contents, which
 * are not otherwise granted special importance. This was formerly known as the Boldface element, and most browsers
 * still draw the text in boldface. However, you should not use <b> for styling text; instead, you should use the CSS
 * font-weight property to create boldface text, or the <strong> element to indicate that text is of special importance.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.b(init: (CBTag.() -> Unit) = {}) = this.registerComponent(CBTag(), init)

//<base>
//<basefont>
//<bdi>
//<bdo>
//<bgsound>
//<big>
//<blink>
//<blockquote>
//<body>

class CBrTag : CTag("br") {}
/**
 * The HTML <br> element produces a line break in text (carriage-return). It is useful for writing a poem or an address,
 * where the division of lines is significant.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.br(init: (CBrTag.() -> Unit) = {}) = this.registerComponent(CBrTag(), init)

class CButtonTag : CTag("button") {

    /**
     * This Boolean attribute prevents the user from interacting with the button: it cannot be pressed or focused.
     *
     * Firefox, unlike other browsers, persists the dynamic disabled state of a <button> across page loads. Use the
     * autocomplete attribute to control this feature.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var disabled: String? by attr.delegate("disabled")

    /**
     * The default behavior of the button. Possible values are:
     *
     * - submit: The button submits the form data to the server. This is the default if the attribute is not specified for buttons associated with a <form>, or if the attribute is an empty or invalid value.
     * - reset: The button resets all the controls to their initial values, like <input type="reset">. (This behavior tends to annoy users.)
     * - button: The button has no default behavior, and does nothing when pressed by default. It can have client-side scripts listen to the element's events, which are triggered when the events occur.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var type: String? by attr.delegate("type")

}
/**
 * The HTML <button> element represents a clickable button, used to submit forms or anywhere in a document for
 * accessible, standard button functionality. By default, HTML buttons are presented in a style resembling the platform
 * the user agent runs on, but you can change buttons’ appearance with CSS.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.button(init: (CButtonTag.() -> Unit) = {}) = this.registerComponent(CButtonTag(), init)




//<canvas>
//<caption>
//<center>
//<cite>
//<code>
//<col>
//<colgroup>
//<command>




//<data>
//<datalist>
//<dd>
//<del>
//<details>
//<dfn>
//<dir>

class CDivTag : CTag("div") {}
/**
 * The HTML Content Division element (<div>) is the generic container for flow content. It has no effect on the content 
 * or layout until styled in some way using CSS (e.g. styling is directly applied to it, or some kind of layout model 
 * like Flexbox is applied to its parent element).
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.div(init: (CDivTag.() -> Unit) = {}) = this.registerComponent(CDivTag(), init)

//<dl>
//<dt>




//<em>
//<embed>



//<fieldset>
//<figcaption>
//<figure>
//<font>
//<footer>

class CFormTag : CTag("form") {

    /**
     * Indicates whether input elements can by default have their values automatically completed by the browser. 
     * autocomplete attributes on form elements override it on <form>. Possible values:
     * 
     * - off: The browser may not automatically complete entries. (Browsers tend to ignore this for suspected login 
     * forms; see The autocomplete attribute and login fields.)
     * - on: The browser may automatically complete entries.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var autoComplete: String? by attr.delegate("autocomplete")

    /**
     * The URL that processes the form submission. This value can be overridden by a formaction attribute on a 
     * <button>, <input type="submit">, or <input type="image"> element.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var action: String? by attr.delegate("action")

    /**
     * The HTTP method to submit the form with. Possible (case insensitive) values:
     * 
     * - post: The POST method; form data sent as the request body.
     * - get: The GET method; form data appended to the action URL with a ? separator. Use this method when the form 
     * has no side-effects.
     * - dialog: When the form is inside a <dialog>, closes the dialog on submission.
     * 
     * This value is overridden by formmethod attributes on <button>, <input type="submit">, or <input type="image"> 
     * elements.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var method: String? by attr.delegate("method")
    
}
/**
 * The HTML <form> element represents a document section containing interactive controls for submitting information.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.form(init: (CFormTag.() -> Unit) = {}) = this.registerComponent(CFormTag(), init)

//<frame>
//<frameset>



class CH1Tag : CTag("h1") {}
/**
 * The HTML <h1>–<h6> elements represent six levels of section headings. <h1> is the highest section level and <h6> 
 * is the lowest.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.h1(init: (CH1Tag.() -> Unit) = {}) = this.registerComponent(CH1Tag(), init)

class CH2Tag : CTag("h2") {}
/**
 * The HTML <h1>–<h6> elements represent six levels of section headings. <h1> is the highest section level and <h6>
 * is the lowest.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.h2(init: (CH2Tag.() -> Unit) = {}) = this.registerComponent(CH2Tag(), init)

class CH3Tag : CTag("h3") {}
/**
 * The HTML <h1>–<h6> elements represent six levels of section headings. <h1> is the highest section level and <h6>
 * is the lowest.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.h3(init: (CH3Tag.() -> Unit) = {}) = this.registerComponent(CH3Tag(), init)

class CH4Tag : CTag("h4") {}
/**
 * The HTML <h1>–<h6> elements represent six levels of section headings. <h1> is the highest section level and <h6>
 * is the lowest.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.h4(init: (CH4Tag.() -> Unit) = {}) = this.registerComponent(CH4Tag(), init)

class CH5Tag : CTag("h5") {}
/**
 * The HTML <h1>–<h6> elements represent six levels of section headings. <h1> is the highest section level and <h6>
 * is the lowest.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.h5(init: (CH5Tag.() -> Unit) = {}) = this.registerComponent(CH5Tag(), init)

class CH6Tag : CTag("h6") {}
/**
 * The HTML <h1>–<h6> elements represent six levels of section headings. <h1> is the highest section level and <h6>
 * is the lowest.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.h6(init: (CH6Tag.() -> Unit) = {}) = this.registerComponent(CH6Tag(), init)

//<head>
//<header>
//<hgroup>
//<hr>
//<html>



//<i>
//<iframe>

class CImgTag(src: String) : CTag("img") {

    /**
     * Defines an alternative text description of the image.
     *
     * Omitting alt altogether indicates that the image is a key part of the content and no textual equivalent is
     * available. Setting this attribute to an empty string (alt="") indicates that this image is not a key part of the
     * content (it’s decoration or a tracking pixel), and that non-visual browsers may omit it from rendering. Visual
     * browsers will also hide the broken image icon if the alt is empty and the image failed to display.
     *
     * This attribute is also used when copying and pasting the image to text, or saving a linked image to a bookmark.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var alt: String? by attr.delegate("alt")

    /**
     * The image URL. Mandatory for the <img> element. On browsers supporting srcset, src is treated like a
     * candidate image with a pixel density descriptor 1x, unless an image with this pixel density descriptor is already
     * defined in srcset, or unless srcset contains w descriptors.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var src: String? by attr.delegate("src")
        get
        private set

    init {
        this.src = src
    }

}

/**
 * The HTML <img> element embeds an image into the document.
 *
 * @param src The image URL. Mandatory for the <img> element. On browsers supporting srcset, src is treated like a
 * candidate image with a pixel density descriptor 1x, unless an image with this pixel density descriptor is already
 * defined in srcset, or unless srcset contains w descriptors.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.img(src: String) = this.registerComponent(CImgTag(src), { })


class CInputTag : CTag("input") {

    /**
     * Valid for both radio and checkbox types, checked is a Boolean attribute. If present on a radio type, it indicates
     * that the radio button is the currently selected one in the group of same-named radio buttons. If present on a
     * checkbox type, it indicates that the checkbox is checked by default (when the page loads). It does not indicate
     * whether this checkbox is currently checked: if the checkbox’s state is changed, this content attribute does not
     * reflect the change. (Only the HTMLInputElement’s checked IDL attribute is updated.)
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var checked: String? by attr.delegate("checked")

    /**
     * A Boolean attribute which, if present, indicates that the user should not be able to interact with the input.
     * Disabled inputs are typically rendered with a dimmer color or using some other form of indication that the field
     * is not available for use.
     *
     * Specifically, disabled inputs do not receive the click event, and disabled inputs are not submitted with the form.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var disabled: String? by attr.delegate("disabled")

    /**
     * A string specifying a name for the input control. This name is submitted along with the control's value when
     * the form data is submitted.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var name: String? by attr.delegate("name")

    /**
     * The placeholder attribute is a string that provides a brief hint to the user as to what kind of information is
     * expected in the field. It should be a word or short phrase that provides a hint as to the expected type of data,
     * rather than an explanation or prompt. The text must not include carriage returns or line feeds. So for example if
     * a field is expected to capture a user's first name, and its label is "First Name", a suitable placeholder might
     * be "e.g. Mustafa".
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var placeholder: String? by attr.delegate("placeholder")

    /**
     * A Boolean attribute which, if present, indicates that the user should not be able to edit the value of the input.
     * The readonly attribute is supported by the text, search, url, tel, email, date, month, week, time, datetime-local,
     * number, and password input types.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var readonly: String? by attr.delegate("readonly")

    /**
     * A string specifying the type of control to render. For example, to create a checkbox, a value of checkbox is used.
     * If omitted (or an unknown value is specified), the input type text is used, creating a plaintext input field.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var type: String? by attr.delegate("type")

    /**
     * The input control's value. When specified in the HTML, this is the initial value, and from then on it can be
     * altered or retrieved at any time using JavaScript to access the respective HTMLInputElement object's value
     * property. The value attribute is always optional, though should be considered mandatory for checkbox, radio,
     * and hidden.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var value: String? by attr.delegate("value")

}
/**
 * The HTML <input> element is used to create interactive controls for web-based forms in order to accept data from
 * the user; a wide variety of types of input data and control widgets are available, depending on the device and user
 * agent. The <input> element is one of the most powerful and complex in all of HTML due to the sheer number of
 * combinations of input types and attributes.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.input(init: (CInputTag.() -> Unit) = {}) = this.registerComponent(CInputTag(), init)


//<ins>
//<isindex>



//<kbd>
//<keygen>



//<label>
//<legend>
//<li>
//<link>
//<listing>



//<main>
//<map>
//<mark>
//<marquee>
//<menu>
//<meta>
//<meter>



//<nav>
//<nobr>
//<noframes>
//<noscript>



//<object>
//<ol>
//<optgroup>

class COptionTag(label: String, value: String, selected: Boolean) : CTag("option") {

    /**
     * If this Boolean attribute is set, this option is not checkable. Often browsers grey out such control and it
     * won't receive any browsing event, like mouse clicks or focus-related ones. If this attribute is not set,
     * the element can still be disabled if one of its ancestors is a disabled <optgroup> element.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var disabled: String? by attr.delegate("disabled")

    /**
     * This attribute is text for the label indicating the meaning of the option. If the label attribute isn't defined,
     * its value is that of the element text content.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var label: String? by attr.delegate("label")
        get
        private set

    /**
     * The content of this attribute represents the value to be submitted with the form, should this option be selected.
     * If this attribute is omitted, the value is taken from the text content of the option element.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var value: String? by attr.delegate("value")
        get
        private set

    /**
     * If present, this Boolean attribute indicates that the option is initially selected. If the <option> element is
     * the descendant of a <select> element whose multiple attribute is not set, only one single <option> of this
     * <select> element may have the selected attribute.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var selected: String? by attr.delegate("selected")

    init {
        this.label = label
        this.value = value
        if (selected) {
            this.selected = "true"
        }
    }
}
/**
 * The HTML <option> element is used to define an item contained in a <select>, an <optgroup>, or a <datalist> element.
 * As such, <option> can represent menu items in popups and other lists of items in an HTML document.
 *
 * @param label This attribute is text for the label indicating the meaning of the option.
 * @param value The content of this attribute represents the value to be submitted with the form, should this option be
 * selected. If this attribute is omitted, the value is taken from the text content of the option element.
 * @param selected If present, this Boolean attribute indicates that the option is initially selected. If the <option>
 * element is the descendant of a <select> element whose multiple attribute is not set, only one single <option>
 * of this <select> element may have the selected attribute.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun CSelectTag.option(label: String, value: String, selected: Boolean = false) = this.registerComponent(COptionTag(label, value, selected), { })

//<output>



class CPTag : CTag("p") {}
/**
 * The HTML <p> element represents a paragraph. Paragraphs are usually represented in visual media as blocks of text
 * separated from adjacent blocks by blank lines and/or first-line indentation, but HTML paragraphs can be any
 * structural grouping of related content, such as images or form fields.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.p(init: (CPTag.() -> Unit) = {}) = this.registerComponent(CPTag(), init)

//<param>
//<plaintext>
//<pre>
//<progress>



//<q>



//<rp>
//<rt>
//<ruby>



//<s>
//<samp>
//<script>
//<section>

class CSelectTag : CTag("select") {

    /**
     * This Boolean attribute indicates that the user cannot interact with the control. If this attribute is not
     * specified, the control inherits its setting from the containing element, for example <fieldset>;
     * if there is no containing element with the disabled attribute set, then the control is enabled.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var disabled: String? by attr.delegate("disabled")

    /**
     * This Boolean attribute indicates that multiple options can be selected in the list. If it is not specified,
     * then only one option can be selected at a time. When multiple is specified, most browsers will show a scrolling
     * list box instead of a single line dropdown.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var multiple: String? by attr.delegate("multiple")

    /**
     * This attribute is used to specify the name of the control.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var name: String? by attr.delegate("name")

}
/**
 * The HTML <select> element represents a control that provides a menu of options:
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.select(init: (CSelectTag.() -> Unit) = {}) = this.registerComponent(CSelectTag(), init)

//<small>
//<source>
//<spacer>
//<span>
//<strike>

class CStrongTag : CTag("strong") {}
/**
 * The HTML Strong Importance Element (<strong>) indicates that its contents have strong importance, seriousness, or
 * urgency. Browsers typically render the contents in bold type.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.strong(init: (CStrongTag.() -> Unit) = {}) = this.registerComponent(CStrongTag(), init)

//<style>
//<sub>
//<summary>
//<sup>



//<table>
//<tbody>
//<td>

class CTextAreaTag : CTag("textarea") {

    /**
     * This Boolean attribute indicates that the user cannot interact with the control. If this attribute is not
     * specified, the control inherits its setting from the containing element, for example <fieldset>;
     * if there is no containing element with the disabled attribute set, then the control is enabled.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var disabled: String? by attr.delegate("disabled")

    /**
     * The name of the control.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var name: String? by attr.delegate("name")

    /**
     * A hint to the user of what can be entered in the control. Carriage returns or line-feeds within the placeholder
     * text must be treated as line breaks when rendering the hint.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var placeholder: String? by attr.delegate("placeholder")

    /**
     * This Boolean attribute indicates that the user cannot modify the value of the control. Unlike the disabled
     * attribute, the readonly attribute does not prevent the user from clicking or selecting in the control. The value
     * of a read-only control is still submitted with the form.
     *
     * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
     */
    var readOnly: String? by attr.delegate("readOnly")

}
/**
 * The HTML <textarea> element represents a multi-line plain-text editing control, useful when you want to allow users
 * to enter a sizeable amount of free-form text, for example a comment on a review or feedback form.
 *
 * Source: [Mozilla Developer Network] (https://developer.mozilla.org/en-US/docs/Web/HTML/Element)
 */
fun Components.textarea(init: (CTextAreaTag.() -> Unit) = {}) = this.registerComponent(CTextAreaTag(), init)

//<tfoot>
//<th>
//<thead>
//<time>
//<title>
//<tr>
//<track>
//<tt>



//<u>
//<ul>



//<var>
//<video>



//<wbr>



//<xmp>