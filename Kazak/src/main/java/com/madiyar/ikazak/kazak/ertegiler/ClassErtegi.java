package com.madiyar.ikazak.kazak.ertegiler;

/**
 * Created by madiyar on 12/10/14.
 */
public class ClassErtegi {
    String name;
    String content;
    int audio;

    public ClassErtegi(String name, String content, int audio) {
        this.name = name;
        this.content = content;
        this.audio = audio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }
}
