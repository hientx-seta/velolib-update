package com.jcdecaux.velolib.view.activities

/**
 * Created by hientx on 11/16/2017.
 */

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.jcdecaux.velolib.R
import com.jcdecaux.velolib.api.StationApiInterface
import com.jcdecaux.velolib.dagger.AppDelegate
import com.jcdecaux.velolib.model.StationItem
import com.jcdecaux.velolib.presenter.StationPresenter
import com.jcdecaux.velolib.view.adapters.StationAdapter
import com.jcdecaux.velolib.view.interfaces.StationView
import java.io.IOException
import javax.inject.Inject


class MainActivity : BaseActivity(), StationView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    lateinit var stationPresenter: StationPresenter
    @Inject
    lateinit var stationAPI: StationApiInterface

    private val mProgressBar: ProgressBar by lazy {
        findViewById(R.id.progressBar) as ProgressBar
    }
    private val mStatusTextView: TextView by lazy {
        findViewById(R.id.statusTextView) as TextView
    }
    private val mRecyclerView: RecyclerView by lazy {
        findViewById(R.id.recyclerView) as RecyclerView
    }
    private val mSwipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.swipeLayout) as SwipeRefreshLayout
    }
    private val toolbar: Toolbar by lazy {
        findViewById(R.id.toolbar) as Toolbar
    }

    private val stationAdapter = StationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        inject()

        prepareSwipeRefreshLayout()
        prepareRecyclerView()
        mRecyclerView.adapter = stationAdapter
        stationPresenter.setStationApiInterface(stationAPI)
        stationPresenter.onViewCreated(this)
        stationPresenter.loadStation()
    }

    private fun inject() {
        (application as AppDelegate).injector?.inject(this)
    }

    private fun prepareSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)
        mSwipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun prepareRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.setHasFixedSize(true)
    }

    override fun onStationItemLoaded(newsItems: List<StationItem>) {
        mSwipeRefreshLayout.isRefreshing = false
        mProgressBar.visibility = View.GONE
        if(newsItems.isEmpty()) {
            mStatusTextView.setText(R.string.list_is_empty)
            return
        }
        mStatusTextView.text = null
        stationAdapter.setDataSource(newsItems)
    }

    override fun onError(throwable: Throwable?) {
        mSwipeRefreshLayout.isRefreshing = false
        mProgressBar.visibility = View.GONE
        if (throwable is IOException) {
            mStatusTextView.setText(R.string.connection_error)
        } else {
            mStatusTextView.setText(R.string.list_is_empty)
        }
    }

    override fun hideLoading() {
        mSwipeRefreshLayout.isRefreshing = false
        mProgressBar.visibility = View.GONE
    }

    override fun onRefresh() {
        stationPresenter.loadStation()
    }

    override fun onStop() {
        super.onStop()
        stationPresenter.onDestroy()
    }
}