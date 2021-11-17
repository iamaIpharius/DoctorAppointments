package com.alex.doctorappointments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class DoctorCardAdapter(context: Context, private val doctorCardList: ArrayList<DoctorCard>) :
    RecyclerView.Adapter<DoctorCardViewHolder>() {

    val mCtx = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorCardViewHolder {
        val doctorCardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_card_list_item, parent, false)



        return DoctorCardViewHolder(doctorCardView)
    }

    override fun onBindViewHolder(holder: DoctorCardViewHolder, position: Int) {
        val doctorCard = doctorCardList[position]
        holder.bind(doctorCard)

        holder.deleteButton.setOnClickListener{
            var alertDialog: AlertDialog? = AlertDialog.Builder(mCtx)
                .setTitle("Внимание")
                .setMessage("Вы действительно хотите удалить запись?")
                .setPositiveButton("Да", DialogInterface.OnClickListener{dialog, which ->

                    if (DoctorsFragment.dbHandler.deleteDoctor(doctorCard.doctorId)) {
                        doctorCardList.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, doctorCardList.size)
                        Toast.makeText(mCtx, "Запись удалена", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(mCtx, "Ошибка удаления", Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton("Нет", DialogInterface.OnClickListener {dialog, which -> })
                .setIcon(R.drawable.ic_baseline_warning_24)
                .show()
        }

        holder.changeButton.setOnClickListener{
            val inflater: LayoutInflater = LayoutInflater.from(mCtx)
            val view: View = inflater.inflate(R.layout.change_card_dialog, null)

            val doctorChangeNameView: TextView = view.findViewById(R.id.change_doctor_name)
            val doctorChangeSpecialityView: TextView = view.findViewById(R.id.change_doctor_speciality)
            val doctorChangeHospitalView: TextView = view.findViewById(R.id.change_doctor_hospital)
            val doctorChangePhoneView: TextView = view.findViewById(R.id.change_doctor_phone)

            doctorChangeNameView.text = doctorCard.name
            doctorChangeSpecialityView.text = doctorCard.speciality
            doctorChangeHospitalView.text = doctorCard.hospital
            doctorChangePhoneView.text = doctorCard.phone

            var builder: AlertDialog.Builder? = AlertDialog.Builder(mCtx)
                .setTitle("Измение информации")
                .setView(view)
                .setPositiveButton("Изменить", DialogInterface.OnClickListener{dialog, which ->

                    val isChange: Boolean = DoctorsFragment.dbHandler.changeDoctor(
                        doctorCard.doctorId.toString(),
                        doctorChangeNameView.text.toString(),
                        doctorChangeSpecialityView.text.toString(),
                        doctorChangeHospitalView.text.toString(),
                        doctorChangePhoneView.text.toString())

                    if (isChange == true){
                        doctorCardList[position].name = doctorChangeNameView.text.toString()
                        doctorCardList[position].speciality = doctorChangeSpecialityView.text.toString()
                        doctorCardList[position].hospital = doctorChangeHospitalView.text.toString()
                        doctorCardList[position].phone = doctorChangePhoneView.text.toString()
                        notifyDataSetChanged()
                        Toast.makeText(mCtx, "Изменения внесены", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(mCtx, "Ошибка обновления", Toast.LENGTH_SHORT).show()
                    }
                })
                .setNegativeButton("Отмена", DialogInterface.OnClickListener {dialog, which ->


                })

                val alert: AlertDialog = builder!!.create()
                alert.show()

        }


    }

    override fun getItemCount(): Int {
        return doctorCardList.size
    }


}