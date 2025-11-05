package com.example.listarolavel.data.worker

import android.content.Context

object AlunoSyncScheduler {

    fun enqueueNow(context: Context) {
        val req = androidx.work.OneTimeWorkRequestBuilder<AlunoSyncWorker>()
            .setConstraints(
                androidx.work.Constraints.Builder()
                    .setRequiredNetworkType(androidx.work.NetworkType.CONNECTED)
                    .build()
            )
            .build()
        androidx.work.WorkManager.getInstance(context).enqueue(req)
    }
}