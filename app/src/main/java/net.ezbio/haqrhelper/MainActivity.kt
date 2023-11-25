package net.ezbio.haqrhelper

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val baseUrl = BuildConfig.BASE_URL
    private val tag = BuildConfig.TAG
    private var haUrl= BuildConfig.HA_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Cacher la barre de titre
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)
        // Vérifiez si l'activité a été lancée avec un Intent de deep link
        if (intent?.action == Intent.ACTION_VIEW) {
            handleDeepLink(intent.data)
        }
    }

    private fun handleDeepLink(deepLinkUri: Uri?) {
        deepLinkUri?.let { uri ->
            // Vérifiez que l'URI correspond au modèle attendu
            if (uri.host == baseUrl && uri.path?.startsWith("/$tag/") == true) {
                val id = uri.lastPathSegment


                // Afficher une AlertDialog pour demander confirmation
                AlertDialog.Builder(this)
                    .setTitle("Confirm Tag")
                    .setMessage("About to open HA Companion app for tag $id. Continue?")
                    .setPositiveButton("Yes") { dialog, which ->
                        // Si confirmé, procéder à la redirection
                        val newUri = Uri.parse("https://$haUrl/$tag/$id")
                        val redirectIntent = Intent(Intent.ACTION_VIEW, newUri)
                        try {
                            startActivity(redirectIntent)
                        } catch (e: ActivityNotFoundException) {
                            AlertDialog.Builder(this)
                                .setTitle("Error")
                                .setMessage("App not found for this url")
                                .setPositiveButton("OK", null)
                                .show()
                        }
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }

}
