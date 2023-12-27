package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class FileReader {
    private static String readFileToString(File file) {
        StringBuilder sb = new StringBuilder();
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
             FileChannel channel = randomAccessFile.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(8);
            while (channel.read(buffer) > 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    sb.append((char) buffer.get());
                }
                buffer.clear();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }

    public Profile getDataFromFile(File file) {
        String name = null;
        Integer age = 0;
        String email = null;
        Long phone = 0L;
        String[] stringsFromFile = readFileToString(file).split("\n");
        for (String string : stringsFromFile) {
            String[] rows = string.split(" ");
            if (rows.length > 1) {
                if (rows[0].equals("Name:")) {
                    name = rows[1];
                } else if (rows[0].equals("Age:")) {
                    age = Integer.parseInt(rows[1]);
                } else if (rows[0].equals("Email:")) {
                    email = rows[1];
                } else if (rows[0].equals("Phone:")) {
                    phone = Long.parseLong(rows[1]);
                }
            }
        }
        return new Profile(name, age, email, phone);
    }
}
