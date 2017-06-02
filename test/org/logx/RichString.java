package org.logx;

/**
 * Created by Ethan Shea on 6/2/2017.
 */
public class RichString {
    private String data;

    public RichString(String data){
        this.data = data;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(this.getClass())){
            return data.equals(((RichString)obj).data);
        }
        return false;
    }
}
