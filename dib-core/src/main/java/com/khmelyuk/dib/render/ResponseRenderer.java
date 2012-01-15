package com.khmelyuk.dib.render;

import java.io.IOException;
import java.io.Writer;

/**
 * The interface of the object responsible for rendering of object or primitive value.
 *
 * @author Ruslan Khmelyuk
 */
public interface ResponseRenderer {

    /**
     * Writes the object to the writer.
     *
     * @param object the object to write.
     * @param writer the writer.
     * @throws IOException error to write an object.
     */
    void write(Object object, Writer writer) throws IOException;

}
