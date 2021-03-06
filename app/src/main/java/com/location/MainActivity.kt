package com.location

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var locationClient: AMapLocationClient
    private var continueCount: Int = 0
    private var locationListener: AMapLocationListener = AMapLocationListener { location ->
        continueCount++ //连续客户端的定位监听
        locTxT.text = StringBuilder().append("定位次数$continueCount\n")
                .append(Utils.getLocationStr(location))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
        if (checkSelfPermission(this, ACCESS_FINE_LOCATION) == PERMISSION_DENIED) {
            requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), 0xc)
        } else {
            startLocation()
        }
        refreshLoc.setOnRefreshListener {
            locationClient.onDestroy()
            startLocation()
        }
    }

    private fun startLocation() {
        locationClient = AMapLocationClient(this)
        val locationClientOption = AMapLocationClientOption()
        locationClientOption.interval = 1000
        locationClientOption.isNeedAddress = true  //地址信息
        locationClient.setLocationOption(locationClientOption)
        locationClient.setLocationListener(locationListener)
        locationClient.startLocation()
        refreshLoc.isRefreshing = false
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0xc && grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
            startLocation()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.onDestroy()
    }
}
