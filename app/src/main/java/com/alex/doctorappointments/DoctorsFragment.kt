package com.alex.doctorappointments


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DoctorsFragment : Fragment() {
    companion object {
        lateinit var dbHandler: DBHandler
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_doctors, container, false)

        dbHandler = DBHandler(view.context, null, null, 1)
        fun viewDoctors(){
            val doctorsList: ArrayList<DoctorCard> = dbHandler.getDoctors(view.context)
            val adapter = DoctorCardAdapter(view.context, doctorsList)
            val doctorsRecyclerView: RecyclerView = view.findViewById(R.id.doctors_recycler_view)
            doctorsRecyclerView.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false) as RecyclerView.LayoutManager
            doctorsRecyclerView.adapter = adapter
        }
        viewDoctors()
//        val doctorCardList = mutableListOf(
//            DoctorCard()
//        )



//        val doctorsRecyclerView: RecyclerView = view.findViewById(R.id.doctors_recycler_view)
//        doctorsRecyclerView.layoutManager =
//            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
//        doctorsRecyclerView.adapter = DoctorCardAdapter(view.context, doctorCardList as ArrayList<DoctorCard>)

        val addDoctorButton: FloatingActionButton =
            view.findViewById(R.id.floating_action_button_to_add_doctor_card)
        addDoctorButton.setOnClickListener {


            val mDialogView =
                LayoutInflater.from(view.context).inflate(R.layout.add_doctor_card_dialog, null)

            val mDialogBuilder = AlertDialog.Builder(view.context)
                .setView(mDialogView)
                .setTitle("Добавьте врача")
            val mAlertDialog = mDialogBuilder.show()


//
//
//
            mDialogView.findViewById<Button>(R.id.dialog_approve).setOnClickListener {

                val doctorName = mDialogView.findViewById<EditText>(R.id.dialog_doctor_name).text.toString()
                val doctorSpec = mDialogView.findViewById<EditText>(R.id.dialog_doctor_speciality).text.toString()
                val doctorHospital = mDialogView.findViewById<EditText>(R.id.dialog_doctor_hospital).text.toString()
                val doctorPhone = mDialogView.findViewById<EditText>(R.id.dialog_doctor_phone).text.toString()


                if (doctorName.isEmpty() or doctorSpec.isEmpty() or doctorHospital.isEmpty() or doctorPhone.isEmpty()) {
                    Toast.makeText(view.context, "Зполните поля", Toast.LENGTH_SHORT).show()
                    mDialogView.findViewById<EditText>(R.id.dialog_doctor_name).requestFocus()
                    mDialogView.findViewById<EditText>(R.id.dialog_doctor_speciality).requestFocus()
                    mDialogView.findViewById<EditText>(R.id.dialog_doctor_hospital).requestFocus()
                    mDialogView.findViewById<EditText>(R.id.dialog_doctor_phone).requestFocus()
                }
                else
                {   mAlertDialog.dismiss()
                    val doctor = DoctorCard()
                    doctor.name = doctorName
                    doctor.speciality = doctorSpec
                    doctor.hospital = doctorHospital
                    doctor.phone = doctorPhone
                    dbHandler.addDoctorCard(view.context, doctor)
                    viewDoctors()
                }
//
//                doctorCardList.add(DoctorCard(0,doctorName.toString(),
//                    doctorSpec.toString(), doctorHospital.toString(), doctorPhone.toString()
//                ))

            }




            mDialogView.findViewById<Button>(R.id.dialog_cancel).setOnClickListener {
                mAlertDialog.dismiss()
            }

        }

        return view
    }





}