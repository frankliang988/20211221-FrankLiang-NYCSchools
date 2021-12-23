package com.frankliang.a20211221_frankliang_nycschools.network

import com.google.gson.annotations.SerializedName

/**
 * dbn,school_name,boro,overview_paragraph,location,phone_number,fax_number,school_email,website,subway,total_students,graduation_rate
 */
class SchoolResponse(
    @SerializedName("dbn")
    val id: String?,
    @SerializedName("school_name")
    val name: String?,
    @SerializedName("boro")
    val borough: String?,
    @SerializedName("overview_paragraph")
    val overView: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("phone_number")
    val phone: String?,
    @SerializedName("fax_number")
    val fax: String?,
    @SerializedName("school_email")
    val email: String?,
    @SerializedName("website")
    val link: String?,
    @SerializedName("subway")
    val subway: String?,
    @SerializedName("total_students")
    val totalStudents: String?,
    @SerializedName("graduation_rate")
    val rateGraduation: String?
) {

    override fun toString(): String {
        return "id: $id name: $name"
    }
}