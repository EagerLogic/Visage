package visage.core

import kotlinx.browser.window

class RunOnce(private val block: () -> Unit) {

    private var pending = false;

    fun run() {
        if (this.pending) {
            return;
        }
        this.pending = true

        window.setTimeout({
            this.pending = false
            this.block()
        }, 5)
    }
}

object IdGenerator {

    var nextId: Long = 0L
        get() {
            field += 1L
            return field
        }
        private set

}
