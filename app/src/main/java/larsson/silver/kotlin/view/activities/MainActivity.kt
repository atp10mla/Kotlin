package larsson.silver.kotlin.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import larsson.silver.kotlin.R
import larsson.silver.kotlin.presenter.MainPresenter
import larsson.silver.kotlin.view.views.MainView


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val mMainPresenter: MainPresenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(xToolbar);

        mMainPresenter.onCreate()
    }

    override fun onDestroy() {
        mMainPresenter.onDestroy()
        super.onDestroy()
    }



}

