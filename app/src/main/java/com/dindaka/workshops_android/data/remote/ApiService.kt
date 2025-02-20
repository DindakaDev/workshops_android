package com.dindaka.workshops_android.data.remote

import com.dindaka.workshops_android.data.remote.dto.common.CommonResponse
import com.dindaka.workshops_android.data.remote.dto.mechanic.MechanicResponse
import com.dindaka.workshops_android.data.remote.dto.parts.StorageResponse
import com.dindaka.workshops_android.data.remote.dto.service.ServiceDTO
import com.dindaka.workshops_android.data.remote.dto.service.ServicesResponse
import com.dindaka.workshops_android.data.remote.dto.workshop.WorkshopsDataResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("talleres/{userId}.json")
    suspend fun getDataTaller(
        @Path("userId") employeeId: String
    ): WorkshopsDataResponse

    @GET("talleres/{userId}/inventario.json")
    suspend fun getPartOnWorkshopDetail(
        @Path("userId") employeeId: String
    ): StorageResponse

    @GET("piezas.json")
    suspend fun getParts(): StorageResponse

    @GET("mecanicos/{userId}.json")
    suspend fun getMechanic(@Path("userId") employeeId: String): MechanicResponse

    @POST("solicitudes.json")
    suspend fun addSolicitud(@Body serviceDTO: ServiceDTO): CommonResponse

    @PUT("solicitudes/{serviceUid}.json")
    suspend fun updateSolicitud(
        @Path("serviceUid") serviceUid: String,
        @Body serviceDTO: ServiceDTO
    ): CommonResponse

    @GET("solicitudes.json")
    suspend fun getSolicitudes(): ServicesResponse
}