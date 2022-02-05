package com.jcdecaux.velolib.view.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jcdecaux.velolib.R
import com.jcdecaux.velolib.config.Constants
import com.jcdecaux.velolib.dagger.AppDelegate
import com.jcdecaux.velolib.extension.circularReveal
import com.jcdecaux.velolib.extension.generatePalette
import com.jcdecaux.velolib.extension.loadUrl
import com.jcdecaux.velolib.model.StationItem
import com.jcdecaux.velolib.presenter.StationDetailPresenter
import com.jcdecaux.velolib.view.interfaces.StationDetailView
import org.parceler.Parcels
import timber.log.Timber
import javax.inject.Inject

class StationActivityDetail : BaseActivity(), StationDetailView{

    @Inject
    lateinit var stationDetailPresenter: StationDetailPresenter

    companion object {
        val REPOSITORY_KEY = "stationItem_key"

        @JvmStatic fun getIntent(context: Context, stationItem: StationItem): Intent {
            val intent = Intent(context, StationActivityDetail::class.java)
            intent.putExtra(REPOSITORY_KEY, Parcels.wrap(StationItem::class.java, stationItem))
            return intent
        }
    }

    private val toolbarLayout: CollapsingToolbarLayout? by lazy {
        findViewById(R.id.toolbarLayout) as CollapsingToolbarLayout?
    }

    private val nameTextView: TextView? by lazy {
        findViewById(R.id.stationNameTextView) as TextView?
    }
    private val addressTextView: TextView?by lazy {
        findViewById(R.id.addressTextView) as TextView?
    }

    private val bankingTextView: TextView?by lazy {
        findViewById(R.id.bankingTextView) as TextView?
    }

    private val contractTextView: TextView?by lazy {
        findViewById(R.id.contractTextView) as TextView?
    }

    private val stationDetailImage: ImageView? by lazy {
        findViewById(R.id.stationDetailImage) as ImageView?
    }

    private val stationDetailFab: FloatingActionButton by lazy {
        findViewById(R.id.stationDetailFab) as FloatingActionButton
    }

    private val toolbar: Toolbar by lazy {
        findViewById(R.id.toolbar) as Toolbar
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        inject()

        val stationItem = Parcels.unwrap <StationItem> (intent.getParcelableExtra<Parcelable> (REPOSITORY_KEY));

        supportActionBar?.title = stationItem.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        stationDetailPresenter.onViewCreated(this)
        stationDetailPresenter.loadStationDetail(stationItem)
    }

    private fun inject() {
        (application as AppDelegate).injector?.inject(this)
    }

    override fun onStationDetailDisplayed(stationItem: StationItem) {
        loadRepositoryDetails(stationItem)
        loadRepositoryImage(Constants.IMAGE_LINK)
        stationDetailFab.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GOOGLE_MAPS))) }
    }

    private fun loadRepositoryDetails(stationItem: StationItem) {
        nameTextView?.text = stationItem.name
        addressTextView?.text = stationItem.address
        bankingTextView?.text = stationItem.banking.toString()
        contractTextView?.text = stationItem.contractName
    }

    fun loadRepositoryImage(imageUrl: String) {
        stationDetailImage?.loadUrl(imageUrl) {
            onSuccess {
                setToolbarColorFromImage()
            }
            onError {
                Timber.e("Failed to load image")
                toolbarLayout!!.visibility = View.VISIBLE
            }
        }
    }

    fun setToolbarColorFromImage() {
        stationDetailImage!!.generatePalette gen@ {
            val swatch = it.mutedSwatch ?: it.vibrantSwatch ?: it.lightMutedSwatch ?: it.lightVibrantSwatch ?: return@gen
            val backgroundColor = swatch.rgb
            val titleTextColor = swatch.titleTextColor

            toolbar.setTitleTextColor(titleTextColor)
            toolbarLayout?.circularReveal(backgroundColor)
            toolbarLayout?.setContentScrimColor(backgroundColor)
        }
    }

    override fun onError(throwable: Throwable?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}