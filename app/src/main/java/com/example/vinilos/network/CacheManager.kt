package com.example.vinilos.network

import android.content.Context
import com.example.vinilos.data.model.CandidateDetailResponse
import com.example.vinilos.data.model.InterviewResponse

class CacheManager(context: Context) {
    companion object {
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    //Projects Cache elements
    private var candidates: HashMap<Int, CandidateDetailResponse> = hashMapOf()
    fun addCandidate(candidateId: Int, candidatos: CandidateDetailResponse) {
        if (!candidates.containsKey(candidateId)) {
            candidates[candidateId] = candidatos
        }
    }

    fun getCandidates(candidateId: Int) : CandidateDetailResponse? {
        return if (candidates.containsKey(candidateId)) candidates[candidateId]!! else null
    }


    private var interviews: HashMap<Int, InterviewResponse> = hashMapOf()
    fun addInterview(interviewId: Int, entrevistas: InterviewResponse) {
        if (!interviews.containsKey(interviewId)) {
            interviews[interviewId] = entrevistas
        }
    }

    fun getInterview(interviewId: Int): InterviewResponse? {
        return if (interviews.containsKey(interviewId)) interviews[interviewId]!! else null
    }
}