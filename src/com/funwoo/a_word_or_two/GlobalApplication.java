package com.funwoo.a_word_or_two;  

import android.app.Application;

/**  
 *   
 */
public class GlobalApplication extends Application {
    private static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static GlobalApplication getInstance() {

        if (instance == null)
            instance = new GlobalApplication();

        return instance;
    }

}