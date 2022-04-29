package dashuaibi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypers;
    public static ServiceDescriptor from(Class clazz, Method method){
        ServiceDescriptor sdp = new ServiceDescriptor();
        sdp.setClazz(clazz.getName());
        sdp.setMethod(method.getName());
        sdp.setReturnType(method.getReturnType().getName());
        Class[] parameterClasses = method.getParameterTypes();
        String[] parameterTypes = new String[parameterClasses.length];
        for(int i=0;i<parameterClasses.length;i++){
            parameterTypes[i]=parameterClasses[i].getName();
        }
        sdp.setParameterTypers(parameterTypes);
        return sdp;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(obj==null||getClass()!= obj.getClass()) return false;
        ServiceDescriptor that = (ServiceDescriptor) obj;
        return this.toString().equals(that.toString());
    }

    @Override
    public String toString() {
        return "class"+ clazz+
                "method"+method+
                "returnTypr"+returnType+
                "parameterTypers"+Arrays.toString(parameterTypers);
    }
}
