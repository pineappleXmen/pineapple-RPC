package common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {
    public static <T> T newInstance(Class<T> clazz){
        try {
            return clazz.newInstance();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

    //获取某个类的共有方法
    public static Method[] getPublicMethods(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> pmethod = new ArrayList<>();
        for(Method m:methods){
            if(Modifier.isPublic((m.getModifiers()))){
                pmethod.add(m);
            }
        }
        return pmethod.toArray(new Method[0]);
    }

    public static Object invoke(Object obj,Method method,Object... args){
        try {
            return method.invoke(obj,args);
        }catch (Exception e){
            throw new IllegalStateException(e);
        }

    }
}
