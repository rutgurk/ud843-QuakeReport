package com.example.android.quakereport.base

import android.content.Context
import android.os.Environment
import android.util.Log
import okreplay.AssetManager
import okreplay.DefaultTapeRoot
import java.io.File
import java.io.Reader
import java.io.Writer

open class AndroidTapeRootSD(private val assetManager: AssetManager, testName: String, val context: Context) :
        DefaultTapeRoot(getSdcardDir(context, testName)) {
    constructor(context: Context, klass: Class<*>) : this(AssetManager(context), klass.simpleName.toString().toLowerCase(), context)

    private val assetsDirPrefix = "tapes/$testName"

    override fun readerFor(tapeFileName: String): Reader {
        // Instead of reading from the sdcard, we'll read tapes from the instrumentation apk assets
        // directory instead.

        val file = "$assetsDirPrefix/$tapeFileName"
        context.assets.list(assetsDirPrefix).forEach { { "asset in $assetsDirPrefix: ${it}" } }



        return assetManager.open(file)
    }

    override fun tapeExists(tapeFileName: String) = assetManager.exists(assetsDirPrefix, tapeFileName)

    override fun writerFor(tapePath: String): Writer {
        val file = File(root, tapePath)

        return super.writerFor(tapePath)
    }

    companion object {

        val TAG = "AndroidTapeRootSD"

        fun getSdcardDir(context: Context, type: String): File {
            if (!isExternalStorageWritable()) {
                throw RuntimeException("Unable to access external storage. State = ${Environment.getExternalStorageState()}")
            }

            // ${context.packageName}/ not included because it's a parent by way of getExternalFilesDir

            val parent = File(context.getExternalFilesDir(null), "okreplay/tapes/")

            if (parent.exists()) {
                Log.i(TAG, "writing to existing dir : ${parent.absoluteFile}")
            } else {
                val b = parent.mkdirs()
               Log.i(TAG, "making dir : ${parent.absoluteFile}   succes: $b")
            }
            return File(parent, type)
        }

        /* Checks if external storage is available for read and write */
        private fun isExternalStorageWritable(): Boolean {
            return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
        }
    }
}