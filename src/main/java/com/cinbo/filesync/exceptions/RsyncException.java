package com.cinbo.filesync.exceptions;

public class RsyncException extends RuntimeException {
   public RsyncException(String msg){
        super(String.format("Rsync Exception:%s",msg));
    }
}
