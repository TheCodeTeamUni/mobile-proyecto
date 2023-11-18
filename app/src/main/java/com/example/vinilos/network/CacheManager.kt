package com.example.vinilos.network

import android.content.Context
import com.example.vinilos.data.model.InterviewResponse
import com.example.vinilos.data.model.ProjectDetailResponse
import com.example.vinilos.data.model.ProjectResponse

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
    private var projects: HashMap<Int, ProjectDetailResponse> = hashMapOf()
    fun addProject(projectId: Int, proyectos: ProjectDetailResponse) {
        if (!projects.containsKey(projectId)) {
            projects[projectId] = proyectos
        }
    }

    fun getProjects(proyectId: Int) : ProjectDetailResponse? {
        return if (projects.containsKey(proyectId)) projects[proyectId]!! else null
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