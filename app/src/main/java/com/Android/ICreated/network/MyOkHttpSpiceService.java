package com.Android.ICreated.network;

import android.app.Application;
import com.octo.android.robospice.okhttp.OkHttpSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;

/**
 * Created by Филипп on 22.03.2015.
 */
public class MyOkHttpSpiceService extends OkHttpSpiceService{

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        return new CacheManager();
    }
}
