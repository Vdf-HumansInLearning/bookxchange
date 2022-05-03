package com.bookxchange.validators;

import com.google.gson.Gson;

public interface ValidatorsInteface {

    void verifyProvidedStringInfoFormat(String recievedInfo);

    void verifyProvidedJsonBody(Gson recivedGson);
}
