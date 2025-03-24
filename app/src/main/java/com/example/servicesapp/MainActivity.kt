
package com.example.servicesapp

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val defaultUrls = listOf(
        "https://www.cisco.com/web/about/ac79/docs/innov/IoE.pdf",
        "https://www.cisco.com/web/about/ac79/docs/innov/IoE_Economy.pdf",
        "https://www.cisco.com/web/strategy/docs/gov/everything-for-cities.pdf",
        "https://www.cisco.com/web/offer/gist_ty2_asset/Cisco_2014_ASR.pdf",
        "https://www.cisco.com/web/offer/emear/38586/images/Presentations/P3.pdf"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pdfFields = listOf(
            findViewById(R.id.pdf1),
            findViewById(R.id.pdf2),
            findViewById(R.id.pdf3),
            findViewById(R.id.pdf4),
            findViewById<EditText>(R.id.pdf5)
        )

        val downloadButton = findViewById<Button>(R.id.startDownloadBtn)

        downloadButton.setOnClickListener {
            for (i in pdfFields.indices) {
                val url = pdfFields[i].text.toString().ifBlank { defaultUrls[i] }
                downloadFile(url)
            }
            Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadFile(url: String) {
        try {
            val request = DownloadManager.Request(Uri.parse(url)).apply {
                setTitle("Downloading PDF")
                setDescription("Downloading ${Uri.parse(url).lastPathSegment}")
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, Uri.parse(url).lastPathSegment)
                setAllowedOverMetered(true)
                setAllowedOverRoaming(true)
            }
            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Invalid URL: $url", Toast.LENGTH_SHORT).show()
        }
    }
}
