package larsson.silver.kotlin.presenter

import larsson.silver.kotlin.view.views.DetailView

/**
 * Created by ossi on 10/09/16.
 */
class DetailPresenter(detailView: DetailView) {
    var mDetailView: DetailView?

    init {
        mDetailView = detailView
    }

    fun onCreate(){

    }

    fun onDestroy(){

    }
}