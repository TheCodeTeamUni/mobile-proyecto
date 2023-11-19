package com.example.vinilos.data.api


import com.example.vinilos.data.model.*
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("abcjobs/validate")
    suspend fun validateEmailAddress(@Body body: ValidateEmailBody): Response<UniqueEmailValidationResponse>

    @POST("abcjobs/signup")
    suspend fun registerUser(@Body body: RegisterBody): Response<RegisterResponse>

    @POST("abcjobs/login")
    suspend fun loginUser(@Body body: LoginBody): Response<LoginResponse>

    @POST("abcjobs/aspirantes/personal")
    suspend fun registerCandidatePersonalInformation(@Body body: RegisterCandidatePersonalInformationBody): Response<RegisterCandidatePersonalInformationResponse>

    @POST("abcjobs/aspirantes/workexperience")
    suspend fun registerCandidateWorkExperienceInformation(@Body body: RegisterCandidateWorkExperienceInformationBody): Response<RegisterCandidateWorkExperienceInformationResponse>

    @POST("abcjobs/aspirantes/education")
    suspend fun registerCandidateEducationInformation(@Body body: RegisterCandidateEducationInformationBody): Response<RegisterCandidateEducationInformationResponse>

    @POST("abcjobs/aspirantes/skill")
    suspend fun registerCandidateSkillInformation(@Body body: RegisterCandidateSkillInformationBody): Response<RegisterCandidateSkillInformationResponse>

    @POST("abcjobs/company/project")
    suspend fun createProject(@Body body: CreateProjectBody): Response<CreateProjectResponse>

    @GET("abcjobs/company/project")
    suspend fun getProjects(): List<ProjectResponse>

    @GET("abcjobs/company/project/{id}")
    suspend fun getProjectDetail(@Path("id") id: String): ProjectDetailResponse

    @POST("abcjobs/company/project/{id}")
    suspend fun postAssignedCandidate(@Path("id") id: String, @Body body: AssignedCandidateBody): Response<AssignedCandidateResponse>

    @POST("abcjobs/company/interview")
    suspend fun createInterview(@Body body: CreateInterviewBody): Response<CreateInterviewResponse>

    @GET("abcjobs/company/interview")
    suspend fun getInterviews(): List<InterviewResponse>

    @GET("abcjobs/company/interview/{id}")
    suspend fun getInterviewDetail(@Path("id") id: String): InterviewResponse

    //Old

    @GET("abcjobs/company/project")
    suspend fun getAllProjects(): Response<List<ProjectResponse>>

    @POST("albums")
    suspend fun createAlbum(@Body album: HashMap<String, String>): Response<ProjectResponse>


}