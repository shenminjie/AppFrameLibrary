package com.monkeyshen.appframe.appframelibrary.network_engine.network_callback;

import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description:
 * Created by MonkeyShen on 2017/2/20.
 * Mail:shenminjie92@sina.com
 */

public abstract class CallBack<T> implements BaseCallBack<T> {

    /**
     * type用于方便JSON的解析
     */
    public Type mType;

    /**
     * 把type转换成对应的类，这里不用看明白也行。
     *
     * @param subclass
     * @return
     */
    static Type getSuperclassTypeParameter(Class<?> subclass) {
        try {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                return null;
            }
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构造的时候获得type的class
     */
    public CallBack() {
        mType = getSuperclassTypeParameter(getClass());
    }
}
