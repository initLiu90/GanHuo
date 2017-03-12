package com.example.lzp.ganhuo.fragment;

/**
 * Created by lzp on 2017/3/12.
 */

public class TitleItem implements BaseItem{
    private String name;

    public TitleItem(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
