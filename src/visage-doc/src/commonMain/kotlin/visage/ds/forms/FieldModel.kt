package visage.ds.forms

import kotlinx.serialization.Serializable

@Serializable
class FieldModel<GValue>(var value: GValue, var error: String? = null) {



}