//package com.aichat.utils;
//
//import edu.cmu.sphinx.api.Configuration;
//import edu.cmu.sphinx.api.LiveSpeechRecognizer;
//
//public class SpeechRecognitionExample {
//    public static void main(String[] args) {
//        Configuration configuration = new Configuration();
//
//        // Set path to the acoustic model
//        configuration.setAcousticModelPath("/path/to/your/models/en-us"); // Use an absolute path
//        // Set path to the dictionary
//        configuration.setDictionaryPath("/path/to/your/models/cmudict-en-us.dict"); // Use an absolute path
//        // Set path to the language model
//        configuration.setLanguageModelPath("/path/to/your/models/en-us.lm.bin"); // Use an absolute path
//
//        try {
//            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
//            recognizer.startRecognition(true); // Start recognition in a blocking mode
//            System.out.println("Start speaking...");
//            String utterance = recognizer.getResult().getHypothesis(); // Get the recognized speech
//            System.out.println("You said: " + utterance); // Print the recognized speech
//        } catch (Exception e) {
//            e.printStackTrace(); // Log any errors
//        }
//    }
//}
