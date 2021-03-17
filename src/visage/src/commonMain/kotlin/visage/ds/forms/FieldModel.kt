package visage.ds.forms

import kotlinx.serialization.Serializable

@Serializable
abstract sealed class AFieldModel<GValue>() {

     abstract var value: GValue
     abstract var error: String?

}

@Serializable
class FieldModel<GValue>(override var value: GValue, override var error: String? = null) : AFieldModel<GValue>()

@Serializable
class MetaFieldModel<GValue, GMeta>(override var value: GValue, val meta: GMeta, override var error: String? = null) : AFieldModel<GValue>()

@Serializable
class SelectOption(
    val value: String,
    val label: String
)