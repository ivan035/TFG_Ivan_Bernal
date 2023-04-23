package com.example.aprendejugando.blob;

public class Blob_list {
    //We need this class to instance the blob and see if its already show in the screen

    private Integer blob_number;
    private Boolean added_already;

    public Blob_list(Integer blob_number) {
        this.blob_number = blob_number;
        this.added_already = false;
    }
    public Boolean isAdded_already() {
        return added_already;
    }

    public void setAdded_already(Boolean added_already) {
        this.added_already = added_already;
    }
}
