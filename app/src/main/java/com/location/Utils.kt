package com.location

import android.location.LocationManager.GPS_PROVIDER
import com.amap.api.location.AMapLocation
import java.text.SimpleDateFormat.getDateTimeInstance

object Utils {
    fun getLocationStr(location: AMapLocation): String {
        val locStr = StringBuilder()
        if (location.errorCode == 0) { //定位成功
            locStr.append("定位成功" + "\n")
            when (location.locationType) {
                0 -> locStr.append("定位类型:失败\n")
                1 -> locStr.append("定位类型:GPS\n")
                2 -> locStr.append("定位类型:前次\n")
                4 -> locStr.append("定位类型:缓存\n")
                5 -> locStr.append("定位类型:WIFI\n")
                6 -> locStr.append("定位类型:基站\n")
                8 -> locStr.append("定位类型:离线\n")
            }
            locStr.append("经度:${location.longitude}\n")
                    .append("纬度:${location.latitude}\n")
                    .append("精度:${location.accuracy}米\n")
                    .append("提供者:${location.provider}\n".toUpperCase())
                    .append("海拔:${location.altitude}米\n")
                    .append("速度:${location.speed}米/秒\n")
                    .append("角度:${location.bearing}\n")
            if (location.provider == GPS_PROVIDER) locStr.append("星数:${location.satellites}\n")
            locStr.run {
                //逆地理信息(编码转地址)
                append("国家:").append(location.country).append("\n")
                append("省:").append(location.province).append("\n")
                append("市:").append(location.city).append("\n")
                append("城市编码:").append(location.cityCode).append("\n")
                append("区:").append(location.district).append("\n")
                append("区域码:").append(location.adCode).append("\n")
                append("地址:").append(location.address).append("\n")
                append("兴趣点:").append(location.poiName).append("\n")
                append("当前定位时间:").append(getDateTimeInstance().format(location.time) + "\n")
            }
        } else { //定位失败
            locStr.append("定位失败\n")
            locStr.append("错误码:").append(location.errorCode).append("\n")
            locStr.append("错误信息:").append(location.errorInfo).append("\n")
            locStr.append("错误描述:").append(location.locationDetail).append("\n")
        }
        return locStr.toString()
    }
}
