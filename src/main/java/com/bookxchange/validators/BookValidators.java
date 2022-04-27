package com.bookxchange.validators;


import com.bookxchange.customExceptions.BooksExceptions;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BookValidators implements ValidatorsInteface {

    @Override
    public void verifyProvidedStringInfoFormat(String recievedInfo) {

        Pattern isbnPattern = Pattern.compile("^([0-9]{10})|([0-9]{13})$");
        Matcher isbnMatch = isbnPattern.matcher(recievedInfo);
        if(!isbnMatch.find()){
            throw new BooksExceptions(recievedInfo + " is not a valid isbn, please check the provided isbn. ISBN should be 10 or 13 digits only, without dashes or spaces");
        }
    }

    @Override
    public void verifyProvidedJsonBody(Gson recivedGson) {
//        recivedJson.recivedJson
    }
}
