package br.com.pirlamps.yousetest.foundation.custom.joat;

/**
 * Created by root-matheus on 21/04/17.
 */

public class JoatObject {

//    private int bindingRes;
    private Integer bindingObjectID;
    private Object bindingObject;

    public JoatObject() {
    }

    public JoatObject( Integer bindingObjectID, Object bindingObject) {
//        this.bindingRes = bindingRes;
        this.bindingObjectID = bindingObjectID;
        this.bindingObject = bindingObject;
    }

//    public int getBindingRes() {
//        return bindingRes;
//    }
//
//    public void setBindingRes(int bindingRes) {
//        this.bindingRes = bindingRes;
//    }

    public Integer getBindingObjectID() {
        return bindingObjectID;
    }

    public void setBindingObjectID(Integer bindingObjectID) {
        this.bindingObjectID = bindingObjectID;
    }

    public Object getBindingObject() {
        return bindingObject;
    }

    public void setBindingObject(Object bindingObject) {
        this.bindingObject = bindingObject;
    }
}
