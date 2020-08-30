package visage.core

typealias TEventListener<GEventArgs> = (args: GEventArgs) -> Unit

class Event<GEventArgs> {

    private val listeners = mutableListOf<TEventListener<GEventArgs>>()

    fun addListener(listener: TEventListener<GEventArgs>) {
        if (hasListener(listener)) {
            throw Exception("This listener is already added!");
        }

        this.listeners.add(listener);
    }

    fun hasListener(listener: TEventListener<GEventArgs>): Boolean {
        return this.listeners.indexOf(listener) > -1;
    }

    fun removeListener(listener: TEventListener<GEventArgs>) {
        if (!hasListener(listener)) {
            throw Exception("Can't find listener to remove!")
        }
        this.listeners.remove(listener)
    }

    fun fire(args: GEventArgs): Unit {
        this.listeners.forEach {
            it(args)
        }
    }

}