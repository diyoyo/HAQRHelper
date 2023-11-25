package net.ezbio.haqrhelper

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.CaptureActivity
import android.Manifest

class MainActivity : AppCompatActivity() {

    private val baseUrl = BuildConfig.BASE_URL
    private val tag = BuildConfig.TAG
    private var haUrl= BuildConfig.HA_URL

    private lateinit var scanQRCodeLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Cacher la barre de titre
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        scanQRCodeLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val contents = result.data?.getStringExtra("SCAN_RESULT")
                // Traiter le résultat du scan ici
                val contentUrl=Uri.parse(contents)
                handleDeepLink(contentUrl)
            }
        }

        // Initialiser le ActivityResultLauncher pour la demande de permission
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission accordée, lancer le scanner QR Code
                launchQRCodeScanner()
            } else {
                // Gérer le cas de refus de la permission
                    simpleAlert("Permission to open camera not granted!")

            }
        }

        val logoImageView = findViewById<ImageView>(R.id.logo)
        logoImageView.setOnClickListener {
            handleLogoClick()
        }

        val baseUrlTextView = findViewById<TextView>(R.id.textview_base_url)
        val text = getString(R.string.base_url_text, baseUrl)
        baseUrlTextView.text = text

        val tagTextView = findViewById<TextView>(R.id.textview_tag)
        val tagText = getString(R.string.tag_text, tag)
        tagTextView.text = tagText

        val haUrlTextView = findViewById<TextView>(R.id.textview_ha_url)
        val haText = getString(R.string.ha_url_text, haUrl)
        haUrlTextView.text = haText

        // Vérifiez si l'activité a été lancée avec un Intent de deep link
        if (intent?.action == Intent.ACTION_VIEW) {
            handleDeepLink(intent.data)
        }

        if (intent.getBooleanExtra("openQRScanner", false)) {
            // Code pour ouvrir votre scanner QR directement
            handleLogoClick()
        }
    }

    private fun handleLogoClick() {
        // Vérifier si la permission de la caméra est accordée
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permission déjà accordée, lancer directement le scanner QR Code
                launchQRCodeScanner()
            }
            else -> {
                // Demander la permission de la caméra
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun launchQRCodeScanner() {
        val intent = Intent(this, CaptureActivity::class.java)
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE")
        intent.putExtra("BEEP_ENABLED", false) // Désactive le bip après la numérisation
        //intent.putExtra("SCAN_FORMATS", "QR_CODE")

        scanQRCodeLauncher.launch(intent)
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
            }else{
                simpleAlert("Code128 barcode detected: ${uri.toString()}")
            }
        }
    }

    private fun simpleAlert(message : String?) {
        AlertDialog.Builder(this)
            .setTitle("Alert")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
    companion object {
        private const val REQUEST_CODE_SCAN = 123
    }

}
