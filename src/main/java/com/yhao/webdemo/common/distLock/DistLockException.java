package com.yhao.webdemo.common.distLock;

public class DistLockException extends Exception {

    public DistLockException() {
        super();
    }

    public DistLockException(String message) {
        super(message);
    }
}
