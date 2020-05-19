package com.bridge.androidtechnicaltest.db

import com.bridge.androidtechnicaltest.network.PupilApi
import io.reactivex.Completable
import io.reactivex.Single

interface IPupilRepository {
    fun getOrFetchPupils() : Single<PupilList>

}
class PupilRepository(private val database: AppDatabase, private val pupilApi: PupilApi): IPupilRepository {

    override fun getOrFetchPupils(): Single<PupilList> {
        return pupilApi.getPupils()
                .doOnSuccess { pupils ->
                    pupils.items.forEach { pupil ->  addToDb(pupil)}
                }
    }

    private fun addToDb(pupil:Pupil):Completable{
        return database.pupilDao.addPupilToDb(pupil)
    }


}