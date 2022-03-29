package by.geekbrains.moviesguide.view.detail

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import by.geekbrains.moviesguide.R

class MainBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val isConnectivity =
            intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
        if (!isConnectivity) onConnectionFound(context) else onConnectionLost(context)
    }

    fun onConnectionLost(context: Context) {
        Toast.makeText(context, context.getString(R.string.connection_lost), Toast.LENGTH_LONG).show()
    }

    fun onConnectionFound(context: Context) {
        Toast.makeText(context, context.getString(R.string.connection_found), Toast.LENGTH_LONG).show()
    }
}