package tw.tcnr13.m1706

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity


class M1706 : AppCompatActivity() {
    private var mSpnLocation: Spinner? = null

    // 自建的html檔名
    private var webView: WebView? = null
    private var Lat: String? = null
    private var Lon: String? = null
    private var jcontent //地名變數
            : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.m1706)
        setupViewComponent()
    }

    private fun setupViewComponent() {
        webView = findViewById<View>(R.id.webview) as WebView
        mSpnLocation = findViewById<View>(R.id.spnLocation) as Spinner
        // ----Location-----------
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_item);
        val adapter = ArrayAdapter<String>(
            this,
            R.layout.spiner_style
        )
        for (i in locations.indices) adapter.add(locations[i][0])

//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.spiner_style)
        mSpnLocation!!.adapter = adapter
        mSpnLocation!!.onItemSelectedListener = mSpnLocationOnItemSelLis
    }

    private val mSpnLocationOnItemSelLis: OnItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?, v: View, position: Int,
            id: Long
        ) {
            setMapLocation()
        }

        override fun onNothingSelected(arg0: AdapterView<*>?) {}
    }

    private fun setMapLocation() {
        val iSelect = mSpnLocation!!.selectedItemPosition
        val sLocation = locations[iSelect][1].split(",").toTypedArray()
        Lat = sLocation[0] // 南北緯
        Lon = sLocation[1] // 東西經
        jcontent = locations[iSelect][0] //地名
        webView!!.settings.javaScriptEnabled = true //
        webView!!.addJavascriptInterface(this@M1706, "AndroidFunction") //
        webView!!.loadUrl(MAP_URL)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

//		Intent it = new Intent();
        when (item.itemId) {
            R.id.action_settings -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    //-----------------------------
    @JavascriptInterface
    fun GetLat(): String? {
        return Lat
    }

    @JavascriptInterface
    fun GetLon(): String? {
        return Lon
    }

    @JavascriptInterface
    fun Getjcontent(): String? {
        return jcontent
    } //-------------------------------

    companion object {
        private val locations = arrayOf(
            arrayOf("中區職訓", "24.172127,120.610313"),
            arrayOf("東海大學路思義教堂", "24.179051,120.600610"),
            arrayOf("台中公園湖心亭", "24.144671,120.683981"),
            arrayOf("秋紅谷", "24.1674900,120.6398902"),
            arrayOf("台中火車站", "24.136829,120.685011"),
            arrayOf("國立科學博物館", "24.1579361,120.6659828")
        )
        private const val MAP_URL = "file:///android_asset/GoogleMap.html"
    }
}
