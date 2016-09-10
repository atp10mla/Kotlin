package larsson.silver.kotlin.view.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import larsson.silver.kotlin.R
import larsson.silver.kotlin.presenter.DetailPresenter
import larsson.silver.kotlin.view.views.DetailView

class DetailActivity : AppCompatActivity(), DetailView {

    private val mDetailPresenter: DetailPresenter = DetailPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        mDetailPresenter.onCreate()
    }

    override fun onDestroy() {
        mDetailPresenter.onDestroy()
        super.onDestroy()
    }
}
