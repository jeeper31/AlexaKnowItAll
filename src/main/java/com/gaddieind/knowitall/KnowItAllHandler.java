package com.gaddieind.knowitall;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import com.gaddieind.knowitall.yelp.YelpAPI;
import com.gaddieind.knowitall.yelp.YelpBusiness;
import com.gaddieind.knowitall.yelp.YelpResponse;
import com.gaddieind.knowitall.yelp.YelpSortingEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Precision;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by Alex on 8/15/2015.
 */
public class KnowItAllHandler implements Speechlet {

    private static final Logger log = LoggerFactory.getLogger(KnowItAllHandler.class);
    public static final double METERS_IN_MILE = 1609.34;

    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException {
        System.out.println("onSessionStarted requestId={" + request.getRequestId() + "} sessionId{" + session.getSessionId() + "}");
    }

    public SpeechletResponse onLaunch(LaunchRequest launchRequest, Session session) throws SpeechletException {
        System.out.println("onSessionStarted requestId={" + launchRequest.getRequestId() + "} sessionId{" + session.getSessionId() + "}");

        String speechOutput = "Hello sucka! I am the great and powerful know it all. Ask me something.";

        // Here we are setting shouldEndSession to false to not end the session and
        // prompt the user for input
        return buildSpeechletResponse("Welcome", speechOutput, false);
    }

    public SpeechletResponse onIntent(IntentRequest intentRequest, Session session) throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", intentRequest.getRequestId(),
                session.getSessionId());

        // Get intent from the request object.
        Intent intent = intentRequest.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;

        // Note: If the session is started with an intent, no welcome message will be rendered;
        // rather, the intent specific response will be returned.
        if ("PanderingIntent".equals(intentName)) {
            return handlePanderingIntent(intent, session);
        }else if ("YouSuckIntent".equals(intentName)) {
            return handleYouSuckIntent(intent, session);
        }else if ("FindFoodIntent".equals(intentName)) {
            return handleFindFoodIntent(intent, session);
        }else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    private SpeechletResponse handlePanderingIntent(Intent intent, Session session){
        Map<String, Slot> slots = intent.getSlots();
        Slot nameSlot = slots.get("Name");

        String speechOutput = "";
        if(nameSlot != null){
            String name = nameSlot.getValue();
            speechOutput = String.format("Hello %s. You are super cool! Go get a beer out of the fridge", name);
        }

        return buildSpeechletResponse(intent.getName(), speechOutput, true);
    }

    private SpeechletResponse handleYouSuckIntent(Intent intent, Session session){
        Map<String, Slot> slots = intent.getSlots();
        Slot nameSlot = slots.get("Name");

        String speechOutput = "";
        if(nameSlot != null){
            String name = nameSlot.getValue();

            if(StringUtils.equalsIgnoreCase(name, "rochelle")){
                speechOutput = "Rochelle is the finest wife a man could ask for and is an excellent mother";
            }else if(StringUtils.equalsIgnoreCase(name, "Alex")){
                speechOutput = "Alex is the best looking and smartest man to ever live, never doubt him.";
            }else if(StringUtils.equalsIgnoreCase(name, "wes")){
                speechOutput = "Wes is the coolest baby ever. Why did you even have to ask?";
            }else if(StringUtils.equalsIgnoreCase(name, "nathan")){
                speechOutput = "Nathan has always been into dudes, he loves the gays. Super gay, I mean, super super super super super gay.";
            }else if(StringUtils.equalsIgnoreCase(name, "jill")){
                speechOutput = "Jill is super cool, has excellent style and fine taste.";
            }else if(StringUtils.equalsIgnoreCase(name, "chica")){
                speechOutput = "Chica is a terrorist.";
            }else {
                speechOutput = String.format("I dont trust %s. Furthermore, %s fucking sucks and is a filthy douche bag!", name, name);
            }
        }

        return buildSpeechletResponse(intent.getName(), speechOutput, true);
    }

    private SpeechletResponse handleFindFoodIntent(Intent intent, Session session){
        Map<String, Slot> slots = intent.getSlots();
        Slot mealSlot = slots.get("Meal");
        Slot sortBySlot = slots.get("SortBy");

        String speechOutput = "";
        if(mealSlot != null){
            YelpAPI yelp = new YelpAPI("HHIpstEWoNBBSvysbCLZEw", "O-XjoyJJ2x-xPvUTEYgo3oawuj4", "uPbPq4zszHhGdhpucEtQvlatXnvt_JRE", "niTM1NyPaHBNeYo0H35skG54RZI");
            YelpSortingEnum sorting = sortBySlot != null ? YelpSortingEnum.getSortValue(sortBySlot.getValue()) : YelpSortingEnum.best_match;
            YelpResponse yelpResponse = yelp.searchForBusinessesByLocation(mealSlot.getValue(), "2 sunset lane, st peters mo 63376", 20, sorting.getSortValue());

            StringBuilder builder = new StringBuilder("As you wish Master: ");
            for(YelpBusiness business : yelpResponse.getBusinesses()){
                Double distance = business.getDistance().doubleValue() / METERS_IN_MILE;
                builder.append(business.getName())
                        .append( " has a rating of ").append(business.getRating()).append(" stars and is ")
                        .append(Precision.round(distance, 1) + " miles away. ");
            }

            speechOutput = builder.toString();
        }

        return buildSpeechletResponse(intent.getName(), speechOutput, true);
    }

    public void onSessionEnded(SessionEndedRequest sessionEndedRequest, Session session) throws SpeechletException {

    }

    private SpeechletResponse buildSpeechletResponse(final String title, final String output,
                                                     final boolean shouldEndSession) {
        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle(String.format("PanderingSpeechlet - %s", title));
        card.setContent(String.format("PanderingSpeechlet - %s", output));

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(output);

        // Create the speechlet response.
        SpeechletResponse response = new SpeechletResponse();
        response.setShouldEndSession(shouldEndSession);
        response.setOutputSpeech(speech);
        response.setCard(card);
        return response;
    }
}
