package com.alex.doctorappointments

data class DoctorCard(
    var doctorId: Int = 0,
    var name: String = "",
    var speciality: String = "",
    var hospital: String = "",
    var phone: String = ""
)