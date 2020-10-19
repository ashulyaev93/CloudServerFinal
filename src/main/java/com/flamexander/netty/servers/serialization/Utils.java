package com.flamexander.netty.servers.serialization;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utils {
    static FileMessage createFileMessage(String fileName, String prefix) throws IOException {
        Path path = Paths.get(prefix + File.separator + fileName);
        if (!Files.isRegularFile(path)) throw new NoSuchFileException("The file " + fileName + " does not exist in the " + prefix + " directory.");
        return new FileMessage(fileName, prefix, Files.readAllBytes(path));
    }

    static void writeFileMessage(FileMessage fm) throws IOException {
        String prefix = fm.getPrefix().equals("upload") ? "download" : "upload";
        Path path = Paths.get( prefix + File.separator + fm.getFileName());
        Files.write(path, fm.getContent());
    }
}
