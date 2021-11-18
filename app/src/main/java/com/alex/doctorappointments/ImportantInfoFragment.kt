package com.alex.doctorappointments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class ImportantInfoFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_important_info, container, false)

        val covidButton: Button = view.findViewById(R.id.covid_button)
        val covidLink = Uri.parse("https://стопкоронавирус.рф")
        val intent = Intent(Intent.ACTION_VIEW, covidLink)

        covidButton.setOnClickListener{
            startActivity(intent)
        }

        return view
    }

}