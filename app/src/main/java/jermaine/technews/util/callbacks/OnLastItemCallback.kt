package jermaine.technews.util.callbacks

/**
 * Interface to listen to know if the list view already scrolled to its last item.
 **/
interface OnLastItemCallback {
    /**
     * Call when list view is already scrolled to its last item.
     **/
    fun onLastItem()
}