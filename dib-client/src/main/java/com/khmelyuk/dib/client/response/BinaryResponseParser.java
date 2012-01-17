package com.khmelyuk.dib.client.response;

import com.khmelyuk.dib.client.request.WrongResponseFormatException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * A JSON parser for {@link com.khmelyuk.dib.client.response.ApiResponse}.
 *
 * @author Ruslan Khmelyuk
 */
@SuppressWarnings("unchecked")
public class BinaryResponseParser {

    public ApiResponse parse(byte[] content) throws IOException, WrongResponseFormatException {
        final ApiResponse result = new ApiResponse();

        result.setStatus(ResponseStatus.Success);
        result.setDuration(0);
        result.setTimestamp(0);

        result.setResponseStream(new ByteArrayInputStream(content));

        return result;
    }

}
