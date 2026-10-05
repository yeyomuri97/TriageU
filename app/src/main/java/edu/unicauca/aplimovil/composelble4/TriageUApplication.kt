package edu.unicauca.aplimovil.composelble4

import android.app.Application
import com.google.firebase.FirebaseApp
import edu.unicauca.aplimovil.composelble4.data.local.TriageDatabase

class TriageUApplication : Application() {

    val database: TriageDatabase by lazy {
        TriageDatabase.getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}