package com.example.aprendejugando.sauce;

public class Blob_list {

    private Integer blob_number;
    private Boolean added_already;

    public Blob_list(Integer blob_number) {
        this.blob_number = blob_number;
        this.added_already = false;
    }

    public Integer getBlob_number() {
        return blob_number;
    }

    public void setBlob_number(Integer blob_number) {
        this.blob_number = blob_number;
    }

    public Boolean isAdded_already() {
        return added_already;
    }

    public void setAdded_already(Boolean added_already) {
        this.added_already = added_already;
    }
}
