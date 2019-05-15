package com.codecool.micro2048.quote.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {

    private String quote;
    private String author;
    private int id;

    @Override
    public String toString() {
        return "Quote{" +
                "quote='" + quote + '\'' +
                ", author='" + author + '\'' +
                ", id=" + id +
                '}';
    }
}
