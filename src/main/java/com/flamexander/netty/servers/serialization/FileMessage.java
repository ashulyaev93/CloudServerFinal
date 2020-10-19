package com.flamexander.netty.servers.serialization;

import java.io.Serializable;

public class FileMessage  implements Serializable {
    private static final long serialVersionUID = 5193392663743561681L;

    private String fileName;
    private byte[] content;
    private String prefix = "download";
    private boolean success = true;

    public FileMessage(String fileName, String prefix, byte[] content) {
        this.fileName = fileName;
        this.prefix = prefix;
        this.content = content;
    }

    public FileMessage(String fileName, boolean success) {
        this.fileName = fileName;
        this.success = success;
    }

    public FileMessage(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName(){return this.fileName;}
    public String getPrefix(){return this.prefix;}
    public byte[] getContent(){return this.content;}
    public boolean isSuccessful(){return this.success;}

}
