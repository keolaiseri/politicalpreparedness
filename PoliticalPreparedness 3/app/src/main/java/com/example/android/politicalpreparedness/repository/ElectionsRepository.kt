package com.example.android.politicalpreparedness.repository

import com.example.android.politicalpreparedness.local.ActiveElectionDatabase
import com.example.android.politicalpreparedness.local.SavedElectionDatabase
import com.example.android.politicalpreparedness.remote.CivicsApiInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionsRepository(
    private val activeElectionDatabase: ActiveElectionDatabase,
    savedElectionDatabase: SavedElectionDatabase,
    private val api: CivicsApiInstance
) {

    val activeElections = activeElectionDatabase.getAll()
    val savedElections = savedElectionDatabase.getAll()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            val electionResponse = api.getElections()
            activeElectionDatabase.insertAll(electionResponse.elections)
        }
    }
}