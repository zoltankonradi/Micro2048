package com.codecool.micro2048.speech.service;

import com.codecool.micro2048.speech.model.Quote;
import org.apache.commons.codec.language.bm.Languages;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.sound.sampled.AudioFormat;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class SpeechService {

    @Autowired
    RestTemplate restTemplate;

    public String getQuoteFromQuoteMicroservise() {
        String quoteURL = "http://localhost:8095/quote";
        Quote quote = restTemplate.getForEntity(quoteURL, Quote.class).getBody();
        System.out.println(quote.getQuote());
        return quote.getQuote();
    }

    public void sendQuoteStringToSpeechApi() throws IOException {

        String speakQuote = getQuoteFromQuoteMicroservise();
        String APIKey = "1ad1def87a334b3f8b0a4c21c36e546e";
        byte[] stream;

        URL url = new URL("http://api.voicerss.org/?" + "key="
                + URLEncoder.encode(APIKey, "UTF-8") + "&src="
                + URLEncoder.encode(speakQuote, "UTF-8") + "&hl="
                + URLEncoder.encode("en-us", "UTF-8") + "&f="
                + URLEncoder.encode("44khz_16bit_mono", "UTF-8"));

        InputStream sendText = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int i;
        while (-1 != (i = sendText.read(buffer))) {
            out.write(buffer, 0, i);
        }
        out.close();
        sendText.close();

        stream = out.toByteArray();

        FileOutputStream getSound = new FileOutputStream("home/documents/audio/voice.mp3");
        getSound.write(stream);
        getSound.close();

    }
}

//        VoiceProvider tts = new VoiceProvider("<API key>");
//
//        VoiceParameters params = new VoiceParameters("Hello, world!", Languages.English_UnitedStates);
//        params.setCodec(AudioCodec.WAV);
//        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
//        params.setBase64(false);
//        params.setSSML(false);
//        params.setRate(0);
//
//        String voice = tts.speech(params);
//
//        System.out.print(String.format("<audio src='%s' autoplay='autoplay'></audio>", voice));
//    }
//
//    }
//        String baseUrl = "https://voicerss-text-to-speech.p.rapidapi.com/?key=1ad1def87a334b3f8b0a4c21c36e546e";
//        String quote = getQuoteFromQuoteMicroservise();
//
//        try {
//            URL url = new URL(baseUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//            connection.setRequestProperty("Content-Type", "Content-Type: audio/mpeg");
//            connection.setUseCaches(false);
//            connection.setDoOutput(true);
//
//            //send quote
//            DataOutputStream sendRequest = new DataOutputStream(connection.getOutputStream());
//            sendRequest.writeBytes(quote);
//            System.out.println(sendRequest.hashCode());
//            sendRequest.close();
//
//            //get mp3
//            InputStream getResponse = connection.getInputStream();
//            System.out.println(getResponse);
//            ContentHandler handler = new DefaultHandler();
//            Metadata metadata = new Metadata();
//            Parser parser = new Mp3Parser();
//            ParseContext parseCtx = new ParseContext();
//            parser.parse(getResponse, handler, metadata, parseCtx);
//            getResponse.close();
//            System.out.println(parser);
//
//        } catch (TikaException | SAXException | IOException e) {
//            e.printStackTrace();
//        }
//    }



