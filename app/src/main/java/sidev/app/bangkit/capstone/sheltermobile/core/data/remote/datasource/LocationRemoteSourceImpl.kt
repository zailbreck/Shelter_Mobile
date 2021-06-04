package sidev.app.bangkit.capstone.sheltermobile.core.data.remote.datasource

import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import sidev.app.bangkit.capstone.sheltermobile.core.data.remote.api.DisasterApi
import sidev.app.bangkit.capstone.sheltermobile.core.data.remote.data.request.Requests
import sidev.app.bangkit.capstone.sheltermobile.core.domain.model.Coordinate
import sidev.app.bangkit.capstone.sheltermobile.core.domain.model.Location
import sidev.app.bangkit.capstone.sheltermobile.core.domain.repo.Fail
import sidev.app.bangkit.capstone.sheltermobile.core.domain.repo.Result
import sidev.app.bangkit.capstone.sheltermobile.core.domain.repo.Success
import sidev.app.bangkit.capstone.sheltermobile.core.util.DataMapper.toModel
import sidev.app.bangkit.capstone.sheltermobile.core.util.Util

class LocationRemoteSourceImpl(private val disasterApi: DisasterApi): LocationRemoteSource {
    override suspend fun getAllLocation(): Result<List<Location>> {
        val earthQuakeCall = disasterApi.getDisasterLocations(Requests.getGempaCity)
        val landslideCall = disasterApi.getDisasterLocations(Requests.getLongsorCity)

        val earthQuakeRes = earthQuakeCall.execute()
        if(!earthQuakeRes.isSuccessful)
            return Fail("Can't get city for earthquake from remote", earthQuakeRes.code(), null)

        val earthQuakeLocs = earthQuakeRes.body()!!.toModel()

        val landslideRes = landslideCall.execute()
        if(!landslideRes.isSuccessful)
            return Fail("Can't get city for landslide from remote", landslideRes.code(), null)

        val landslideLocs = landslideRes.body()!!.toModel()

        return Success(earthQuakeLocs + landslideLocs, 0)
    }

    override suspend fun getLocation(coordinate: Coordinate): Result<Location> =
        Util.operationNotAvailableFailResult("LocationRemoteSourceImpl.getLocation(coordinate: Coordinate)")

    override suspend fun getLocationById(id: Int): Result<Location> =
        Util.operationNotAvailableFailResult("LocationRemoteSourceImpl.getLocationById(id: Int)")

    override suspend fun saveLocationList(list: List<Location>): Result<Int> =
        Util.operationNotAvailableFailResult("LocationRemoteSourceImpl.saveLocationList(list: List<Location>)")
}