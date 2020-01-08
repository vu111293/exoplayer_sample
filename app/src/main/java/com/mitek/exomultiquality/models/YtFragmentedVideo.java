package com.mitek.exomultiquality.models;

public class YtFragmentedVideo {
    public int height;
    public YtStream audioFile;
    public YtStream videoFile;

    public long getSize() {
//            try {
//                long totalSize = 0;
//                if (videoFile != null) {
//                    totalSize += new GetSizeFile().execute(videoFile.getUrl()).get();
//                }
//                if (audioFile != null) {
//                    totalSize += new GetSizeFile().execute(audioFile.getUrl()).get();
//                }
//                return totalSize;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        return 0;
    }

    public String getURL() {
        if (videoFile != null) return videoFile.url;
        return audioFile.url;
    }

    public String getFormat() {
        if (videoFile != null) return videoFile.format;
        return audioFile.format;
    }

    public String getVideoFormat() {
        return videoFile.format;
    }

    public String getAudioFormat() {
        return audioFile.format;
    }
}