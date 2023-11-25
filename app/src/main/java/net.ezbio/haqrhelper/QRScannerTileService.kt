package net.ezbio.haqrhelper

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi

class QRScannerTileService : TileService() {
    // Called when the user adds your tile.
    override fun onTileAdded() {
        super.onTileAdded()
    }
    // Called when your app can update your tile.
    override fun onStartListening() {
        super.onStartListening()
    }

    // Called when your app can no longer update your tile.
    override fun onStopListening() {
        super.onStopListening()
    }

    // Called when the user taps on your tile in an active or inactive state.
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onClick() {
        super.onClick()

        // Intention pour lancer l'activité de votre scanner QR
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("openQRScanner", true)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        // Créer un PendingIntent
        //TODO implement pendingIntent rather than just intent, since its deprecated!
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        // Lancer l'activité et fermer le panneau des paramètres rapides
        startActivityAndCollapse(intent)

    }
    // Called when the user removes your tile.
    override fun onTileRemoved() {
        super.onTileRemoved()
    }
}
