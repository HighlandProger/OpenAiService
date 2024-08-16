package ru.rusguardian.open.ai.service.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import ru.rusguardian.open.ai.service.exception.FileReadException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtils {

    private FileUtils() {
    }


    public static byte[] getBytes(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] var3;
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                try {
                    IOUtils.copy(fileInputStream, byteArrayOutputStream);
                    var3 = byteArrayOutputStream.toByteArray();
                } catch (Throwable var7) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }

                    throw var7;
                }

                byteArrayOutputStream.close();
            } catch (Throwable var8) {
                try {
                    fileInputStream.close();
                } catch (Throwable var5) {
                    var8.addSuppressed(var5);
                }

                throw var8;
            }

            fileInputStream.close();
            return var3;
        } catch (IOException var9) {
            throw new FileReadException(file.getName(), var9);
        }
    }

    public static File getTempFile(String fileType) {
        try {
            File tempFile = File.createTempFile("temp", "." + fileType);
            tempFile.deleteOnExit();
            return tempFile;
        } catch (IOException var2) {
            throw new RuntimeException("Exception during creating temp file from bytes");
        }
    }
}
