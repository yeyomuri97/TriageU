package edu.unicauca.aplimovil.composelble4

import android.app.Application
import com.google.firebase.FirebaseApp

class TriageUApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
