package com.example.android.politicalpreparedness.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VoterInfoResponse (
    val election: Election,
    val state: List<State>? = null,
    /* Future use
    val electionElectionOfficials: List<ElectionOfficial>? = null
    val pollingLocations: String? = null,
    val contests: String? = null,
    */
)