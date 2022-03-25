package com.example.android.demo.Notifications;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationsWorker extends Worker {
    private final Context mContext;

    public NotificationsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        NotificationsFactory.makeANotification(mContext);
        return Result.success();
    }
}
