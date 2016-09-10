package larsson.silver.kotlin.presenter

import larsson.silver.kotlin.view.views.MainView

/**
 * Created by markus on 16-09-10.
 */


class MainPresenter (mainView: MainView) {

    private var mMainView: MainView?

    init {
        mMainView = mainView
    }

    fun onCreate() {

    }

    fun onDestroy() {
        mMainView = null;
    }

}
