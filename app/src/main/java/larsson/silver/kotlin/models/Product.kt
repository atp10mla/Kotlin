package larsson.silver.kotlin.models

import java.util.*
import larsson.silver.kotlin.models.Unit

/**
 * Created by ossi on 10/09/16.
 */
data class Product(val name: String, val UUID: UUID) {
    var amount: Int = 0
        set
    var unit: Unit = Unit.Piece
        set
        get
    var category: Category? = null
        set
        get
    var date: String? = null
        set
        get
}

