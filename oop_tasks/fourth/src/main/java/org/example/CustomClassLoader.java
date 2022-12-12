package org.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomClassLoader extends ClassLoader{
    private static final Logger log = Logger.getLogger(CustomClassLoader.class.getName());

    @Override
    public Class<?> findClass(String fileName) {
        byte[] b = loadClassFromFile(fileName);
        return defineClass(fileName, b, 0, b.length);
    }

    private byte[] loadClassFromFile(String fileName){
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(fileName.replace('.', File.separatorChar) + ".class");
        byte[] buffer;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = 0;
        try {
            while ((nextValue = inputStream.read()) != -1) {
                    byteStream.write(nextValue);
            }
        } catch (IOException ex){
            log.log(Level.SEVERE, "Exception: ", ex);
        }
        buffer = byteStream.toByteArray();
        return buffer;
    }
}
