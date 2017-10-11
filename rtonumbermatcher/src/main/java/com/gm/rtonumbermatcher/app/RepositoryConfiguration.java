package com.gm.rtonumbermatcher.app;

import android.content.Context;

import com.gm.repository.ConfigRepository;
import com.gm.repository.di.module.RepositoryConfigModule;
import com.gm.repository.utils.RequestInterceptor;
import com.gm.rtonumbermatcher.BuildConfig;

import java.util.concurrent.TimeUnit;

/**
 * Author     : Gowtham
 * Email      : goutham.gm11@gmail.com
 * Github     : https://github.com/goutham106
 * Created on : 10/5/17.
 */

public class RepositoryConfiguration implements ConfigRepository {
    @Override
    public void applyOptions(Context context, RepositoryConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) //Release, Let the framework no longer print Http request and response information
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);

        builder.baseUrl("www")
                //Customize your own image loading logic
                //                .imageLoaderStrategy(new CustomLoaderStrategy())
                // Here to provide a global processing Http request and response to the results of the processing class, the client can step ahead of the server to get the results of the return
                .globalHttpHandler(new GlobalHttpHandlerImpl())
                // Used to deal with all the errors occurred in rxjava, rxjava occurred in each error will call back this interface
                // rxjava need to use ErrorHandleSubscriber (the default implementation of Subscriber's onError method), this monitor to take effect
                .responseErrorListener(new ResponseErrorListenerImpl())
                //Here you can customize the configuration of Gson parameters
                .gsonConfiguration((context1, gsonBuilder) -> {
                    gsonBuilder
                            .serializeNulls()//Supports serialization of null parameters
                            .enableComplexMapKeySerialization();//Support will be serialized key for the object of the map, the default can only serialize the key for the string map
                })
                //Here you can customize the configuration of Retrofit parameters, and even you can replace the system configuration okhttp object
                .retrofitConfiguration((context1, retrofitBuilder) -> {
                    // Such as the use of fastjson alternative gson
                    // retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());
                })
                //Here you can customize the configuration Okhttp parameters
                .okhttpConfiguration((context1, okhttpBuilder) -> {
                    // Support Https
                    // okhttpBuilder.sslSocketFactory()
                    okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS);
                })
                .roomConfiguration((context1, roomBuilder) -> {
                    //Here you can customize the configuration RoomDatabase, such as database migration upgrade
/*                    roomBuilder.addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(SupportSQLiteDatabase database) {
                            // TODO: 10/5/17
                            // Since we didn't alter the table, there's nothing else to do here.
                        }
                    });*/
                });
    }
}
