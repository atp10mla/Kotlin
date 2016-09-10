package larsson.silver.kotlin.view.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import larsson.silver.kotlin.R
import larsson.silver.kotlin.presenter.MainPresenter

/**
 * Created by markus on 16-09-10.
 */
class MainActivity : AppCompatActivity() {

    lateinit var mMainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(mMainPresenter == null) {
            mMainPresenter = MainPresenter();
        }
        mMainPresenter.onCreate();

    }



}

