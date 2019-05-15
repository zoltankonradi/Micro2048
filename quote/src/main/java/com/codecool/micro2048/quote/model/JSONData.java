package com.codecool.micro2048.quote.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JSONData {

    private Quote quotes;

    @Override
    public String toString() {
        return "JSONData{" +
                "quotes=" + quotes +
                '}';
    }
}
