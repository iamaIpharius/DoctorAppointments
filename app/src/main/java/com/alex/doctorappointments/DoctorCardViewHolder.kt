package com.alex.doctorappointments

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DoctorCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val deleteButton:Button = itemView.findViewById(R.id.doctor_card_delete_button)
    val changeButton:Button = itemView.findViewById(R.id.doctor_card_change_button)

    fun bind(doctorCard: DoctorCard) {
        val doctorCardNameView: TextView = itemView.findViewById(R.id.doctor_name)
        val doctorCardSpecialityView: TextView = itemView.findViewById(R.id.doctor_speciality)
        val doctorCardHospitalView: TextView = itemView.findViewById(R.id.hospital_name)
        val doctorCardPhoneView: TextView = itemView.findViewById(R.id.doctor_phone)



        doctorCardNameView.text = doctorCard.name
        doctorCardSpecialityView.text = doctorCard.speciality
        doctorCardHospitalView.text = doctorCard.hospital
        doctorCardPhoneView.text = doctorCard.phone





    }

}