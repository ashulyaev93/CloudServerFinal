package com.flamexander.netty.servers.serialization;

import java.net.Socket;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;

public class Client {
    public static void main(String[] args) {

        if(!args[0].matches("^(upload|download)$")) {
            System.out.println("Only upload and download words are allowed as a first argument");
            System.exit(1);
        }
        
        boolean isUploading = args[0].equals("upload");
        
        try (Socket socket = new Socket("localhost", 8189)) {
            FileMessage fm = isUploading ? Utils.createFileMessage(args[1], args[0]) : new FileMessage(args[1]);
            ObjectEncoderOutputStream oeos =  new ObjectEncoderOutputStream(socket.getOutputStream());
            ObjectDecoderInputStream odis = new ObjectDecoderInputStream(socket.getInputStream(), 100 * 1024 * 1024);
            System.out.println((isUploading ? "Uploading" : "Downloading" ) + " " + args[1] + "...");
            oeos.writeObject(fm);
            FileMessage reply = (FileMessage)odis.readObject();
            oeos.close();
            odis.close();
            if(!reply.isSuccessful()) throw new Exception("ERR: The " + (isUploading ? "uploading" : "downloading") + " of file "
                    + reply.getFileName() + " failed.");
            if(!isUploading) Utils.writeFileMessage(reply);
            System.out.println("OK: The file " + reply.getFileName() + " is " + (isUploading ? "uploaded" : "downloaded") + " successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }
    }

}
