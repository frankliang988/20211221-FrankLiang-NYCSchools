package com.frankliang.a20211221_frankliang_nycschools.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {
    @GET("s3k6-pzi2.json?\$select=dbn,school_name,boro")
    suspend fun getSchoolList(): Response<List<SchoolResponse>>

    @GET("s3k6-pzi2.json?\$select=dbn,overview_paragraph,location,phone_number,fax_number,school_email,website,subway,total_students,graduation_rate")
    suspend fun getSchoolInfo(@Query("dbn") id: String): Response<List<SchoolResponse>>

    @GET("f9bf-2cp4.json?")
    suspend fun getSATInfo(@Query("dbn") id: String): Response<List<SATResponse>>
}