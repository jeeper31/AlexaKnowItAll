package com.gaddieind.knowitall;

import com.amazonaws.services.lambda.runtime.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Alex on 8/15/2015.
 */
public class KnowItAllLambda extends SpeechletLambda{
    static
    {
        System.setProperty("com.amazon.speech.speechlet.servlet.disableRequestSignatureCheck", "true");
    }

    public KnowItAllLambda()
    {
        this.setSpeechlet(new KnowItAllHandler());
    }

    @Override
    public void handleRequest(InputStream inputStream,
                              OutputStream outputStream, Context context) throws IOException
    {
        super.handleRequest(inputStream, outputStream, context);
    }
}
