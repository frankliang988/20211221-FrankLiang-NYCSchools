package com.frankliang.a20211221_frankliang_nycschools.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.frankliang.a20211221_frankliang_nycschools.network.SATResponse
import com.frankliang.a20211221_frankliang_nycschools.network.SchoolResponse

@Entity(tableName = "schools")
class SchoolEntity(
    @PrimaryKey
    val id: String,
    var name: String? = null,
    var borough: String? = null,
    var overView: String? = null,
    var location: String? = null,
    var phone: String? = null,
    var fax: String? = null,
    var email: String? = null,
    var link: String? = null,
    var subway: String? = null,
    var totalStudents: Int? = 0,
    var rateGraduation: Double? = 0.0,
    var satReading: Int? = 0,
    var satWriting: Int? = 0,
    var satMath: Int? = 0,
    var isSaved: Boolean = false
) {
    companion object {
        const val QUEENS = "Q"
        const val BROOKLYN = "K"
        const val BRONX = "X"
        const val STATEN_ISLAND = "R"
        const val MANHATTAN = "M"
    }

    fun setInfoData(response: SchoolResponse) {
        overView = response.overView
        location = response.location
        phone = response.phone
        fax = response.fax
        email = response.email
        link = response.link
        subway = response.subway
        response.totalStudents?.toIntOrNull()?.let { totalStudents = it }
        response.rateGraduation?.toDoubleOrNull()?.let { rateGraduation = it }
    }

    fun setSATData(response: SATResponse) {
        response.satReading?.toIntOrNull()?.let { satReading = it }?: kotlin.run { satReading = 0 }
        response.satMath?.toIntOrNull()?.let { satMath = it }?: kotlin.run { satMath = 0 }
        response.satWriting?.toIntOrNull()?.let { satWriting = it }?: kotlin.run { satWriting = 0 }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is SchoolEntity) return false
        return id == other.id
                && name == other.name
                && borough == other.borough
                && overView == other.overView
                && location == other.location
                && phone == other.phone
                && fax == other.fax
                && email == other.email
                && link == other.link
                && subway == other.subway
                && totalStudents == other.totalStudents
                && rateGraduation == other.rateGraduation
                && satReading == other.satReading
                && satWriting == other.satWriting
                && satMath == other.satMath
                && isSaved == other.isSaved
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (borough?.hashCode() ?: 0)
        result = 31 * result + (overView?.hashCode() ?: 0)
        result = 31 * result + (location?.hashCode() ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (fax?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (link?.hashCode() ?: 0)
        result = 31 * result + (subway?.hashCode() ?: 0)
        result = 31 * result + (totalStudents ?: 0)
        result = 31 * result + (rateGraduation?.hashCode() ?: 0)
        result = 31 * result + (satReading ?: 0)
        result = 31 * result + (satWriting ?: 0)
        result = 31 * result + (satMath ?: 0)
        result = 31 * result + isSaved.hashCode()
        return result
    }

    override fun toString(): String {
        return "SchoolEntity(id='$id', name=$name, borough=$borough, overView=$overView, location=$location, phone=$phone, fax=$fax, email=$email, link=$link, subway=$subway, totalStudents=$totalStudents, rateGraduation=$rateGraduation, satReading=$satReading, satWriting=$satWriting, satMath=$satMath, isSaved=$isSaved)"
    }

}