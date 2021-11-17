package com.alex.doctorappointments

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.lang.Exception
import java.util.prefs.PreferencesFactory

class DBHandler(context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

        companion object {
            private val DATABASE_NAME = "DoctorAppointmentsData.db"
            private val DATABASE_VERSION = 1

            val DOCTORS_TABLE_NAME = "Doctors"
            val COLUMN_DOCTOR_ID = "doctorId"
            val COLUMN_DOCTOR_NAME = "doctorName"
            val COLUMN_DOCTOR_SPEC = "doctorSpeciality"
            val COLUMN_DOCTOR_HOSPITal = "doctorHospital"
            val COLUMN_DOCTOR_PHONE = "doctorPhone"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_DOCTORS_TABLE:String = ("CREATE TABLE $DOCTORS_TABLE_NAME (" +
                "$COLUMN_DOCTOR_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_DOCTOR_NAME TEXT," +
                "$COLUMN_DOCTOR_SPEC TEXT," +
                "$COLUMN_DOCTOR_HOSPITal TEXT," +
                "$COLUMN_DOCTOR_PHONE TEXT)")
        db?.execSQL(CREATE_DOCTORS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun getDoctors(context: Context): ArrayList<DoctorCard> {
        val query = "Select * From $DOCTORS_TABLE_NAME"
        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor = db.rawQuery(query, null)
        val doctors = ArrayList<DoctorCard>()

        if (cursor.count == 0)
            Toast.makeText(context, "No records found", Toast.LENGTH_SHORT).show() else
            {
                while (cursor.moveToNext()) {
                    val doctorCard = DoctorCard()
                    doctorCard.doctorId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_ID))
                    doctorCard.name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_NAME))
                    doctorCard.speciality = cursor.getString(cursor.getColumnIndexOrThrow(
                        COLUMN_DOCTOR_SPEC))
                    doctorCard.hospital = cursor.getString(cursor.getColumnIndexOrThrow(
                        COLUMN_DOCTOR_HOSPITal))
                    doctorCard.phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_PHONE))
                    doctors.add(doctorCard)
                }
                Toast.makeText(context, "${cursor.count.toString()} Records Found", Toast.LENGTH_SHORT).show()
        }
            cursor.close()
            db.close()
            return doctors
    }

    fun addDoctorCard(context: Context, doctorCard: DoctorCard) {
        val values = ContentValues()
        values.put(COLUMN_DOCTOR_NAME, doctorCard.name)
        values.put(COLUMN_DOCTOR_SPEC, doctorCard.speciality)
        values.put(COLUMN_DOCTOR_HOSPITal, doctorCard.hospital)
        values.put(COLUMN_DOCTOR_PHONE, doctorCard.phone)
        val db: SQLiteDatabase = this.writableDatabase

        try {
            db.insert(DOCTORS_TABLE_NAME,null, values)
            Toast.makeText(context, "Доктор добавлен", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
    fun deleteDoctor(doctorID: Int) : Boolean{
        val qry = "Delete from $DOCTORS_TABLE_NAME where $COLUMN_DOCTOR_ID = $doctorID"
        val db: SQLiteDatabase = this.writableDatabase
        var result: Boolean = false

        try {
            val cursor = db.execSQL(qry)
            result = true
        }catch (e: Exception) {
            Log.e(ContentValues.TAG, "Error Deleting")
        }
        db.close()
        return result

    }

    fun changeDoctor(id: String, doctorName: String, doctorSpec: String, doctorHospital: String, doctorPhone: String): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val contentValues = ContentValues()
        var result: Boolean = false
        contentValues.put(COLUMN_DOCTOR_NAME, doctorName)
        contentValues.put(COLUMN_DOCTOR_SPEC, doctorSpec)
        contentValues.put(COLUMN_DOCTOR_HOSPITal, doctorHospital)
        contentValues.put(COLUMN_DOCTOR_PHONE, doctorPhone)
        try {
            db.update(DOCTORS_TABLE_NAME, contentValues, "$COLUMN_DOCTOR_ID = ?", arrayOf(id))
            result = true
        }catch (e: Exception){
            Log.e(ContentValues.TAG, "Error Changing")
            result = false
        }

        return result


    }
}