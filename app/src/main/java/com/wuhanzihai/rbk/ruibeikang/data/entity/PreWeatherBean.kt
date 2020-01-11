package com.wuhanzihai.rbk.ruibeikang.data.entity

data class PreWeatherBean(
        val api_status: String,
        val api_version: String,
        val lang: String,
        val location: List<Double>,
        val result: PreResult,
        val server_time: Int,
        val status: String,
        val tzshift: Int,
        val unit: String
)

data class PreResult(
        val daily: Daily,
        val primary: Int
)

data class Daily(
        val aqi: List<Aqi>,
        val astro: List<Astro>,
        val carWashing: List<CarWashing>,
        val cloudrate: List<Cloudrate>,
        val coldRisk: List<ColdRisk>,
        val comfort: List<PreComfort>,
        val dressing: List<Dressing>,
        val dswrf: List<Dswrf>,
        val humidity: List<Humidity>,
        val pm25: List<Pm25>,
        val precipitation: List<PrePrecipitation>,
        val pres: List<Pre>,
        val skycon: List<Skycon>,
        val skycon_08h_20h: List<Skycon08h20h>,
        val skycon_20h_32h: List<Skycon20h32h>,
        val status: String,
        val temperature: List<Temperature>,
        val ultraviolet: List<PreUltraviolet>,
        val visibility: List<Visibility>,
        val wind: List<PreWind>
)

data class Aqi(
        val avg: Double,
        val date: String,
        val max: Int,
        val min: Int
)

data class Astro(
        val date: String,
        val sunrise: Sunrise,
        val sunset: Sunset
)

data class Sunrise(
        val time: String
)

data class Sunset(
        val time: String
)

data class CarWashing(
        val datetime: String,
        val desc: String,
        val index: String
)

data class Cloudrate(
        val avg: Double,
        val date: String,
        val max: Double,
        val min: Double
)

data class ColdRisk(
        val datetime: String,
        val desc: String,
        val index: String
)

data class PreComfort(
        val datetime: String,
        val desc: String,
        val index: String
)

data class Dressing(
        val datetime: String,
        val desc: String,
        val index: String
)

data class Dswrf(
        val avg: Double,
        val date: String,
        val max: Double,
        val min: Double
)

data class Humidity(
        val avg: Double,
        val date: String,
        val max: Double,
        val min: Double
)

data class Pm25(
        val avg: Double,
        val date: String,
        val max: Int,
        val min: Int
)

data class PrePrecipitation(
        val avg: Double,
        val date: String,
        val max: Double,
        val min: Double
)

data class Pre(
        val avg: Double,
        val date: String,
        val max: Double,
        val min: Double
)

data class Skycon(
        val date: String,
        val value: String
)

data class Skycon08h20h(
        val date: String,
        val value: String
)

data class Skycon20h32h(
        val date: String,
        val value: String
)

data class Temperature(
        val avg: Double,
        val date: String,
        val max: Double,
        val min: Double
)

data class PreUltraviolet(
        val datetime: String,
        val desc: String,
        val index: String
)

data class Visibility(
        val avg: Double,
        val date: String,
        val max: Double,
        val min: Double
)

data class PreWind(
        val avg: Avg,
        val date: String,
        val max: Max,
        val min: Min
)

data class Avg(
        val direction: Double,
        val speed: Double
)

data class Max(
        val direction: Double,
        val speed: Double
)

data class Min(
        val direction: Double,
        val speed: Double
)

data class PreWeatherItemBean(var time: String,
                              var date: String,
                              var index: Int,
                              var max: Int,
                              var min: Int,
                              var aqi: Int)