package com.example.hai.eventfinder.EventNLP;

import android.app.Application;

import com.sixthsolution.apex.Apex;
import com.sixthsolution.apex.nlp.english.EnglishParser;

/**
 * Created by Hai on 5/10/2017.
 */

public class ApexNLPInit extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Apex.init(new Apex.ApexBuilder()
                .addParser("en", new EnglishParser())
                .build());

    }
}
