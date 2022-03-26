package by.geekbrains.moviesguide.view.detail

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(
    text: String,
    length: Int = Snackbar.LENGTH_INDEFINITE,
) {
    Snackbar.make(this, text, length).show()
}