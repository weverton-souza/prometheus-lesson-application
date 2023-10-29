package com.prometheus.lesson.enumeration

enum class OriginType {
    ENADE, ENEM, CREATED_BY_ME, CREATED_FROM_MY_INSTITUTION, CREATED_BY_TEACHER;

    companion object {
        fun getAuthorialCreation(): MutableList<String> {
            return mutableListOf("CREATED_BY_ME", "CREATED_FROM_MY_INSTITUTION", "CREATED_BY_TEACHER")
        }

        fun getNationalExam(): MutableList<String> {
            return mutableListOf("ENADE", "ENEM")
        }
    }
}
