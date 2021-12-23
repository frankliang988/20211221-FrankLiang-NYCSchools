package com.frankliang.a20211221_frankliang_nycschools.network

import com.google.gson.annotations.SerializedName

class SATResponse(
    @SerializedName("dbn")
    val id: String?,
    @SerializedName("sat_critical_reading_avg_score")
    val satReading: String?,
    @SerializedName("sat_math_avg_score")
    val satMath: String?,
    @SerializedName("sat_writing_avg_score")
    val satWriting: String?,
) {
}