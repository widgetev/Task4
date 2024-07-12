package org.example.loger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LogerPostProcessor implements BeanPostProcessor {
    @Value("${task4.LogTransformation.defaultfile}") //ну так себе костыль. Но работает
    private String defaultLogFile;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        List<Method> methods = new ArrayList<>(List.of(bean.getClass().getDeclaredMethods()));
        List<Method> annotatedMethod = new ArrayList<>();



        for (Method method : methods) {
            if(method.isAnnotationPresent(LogTransformation.class)){
                annotatedMethod.add(method);
            }
        }
        if(annotatedMethod.size() < 1)  return bean;

        return Proxy.newProxyInstance(bean.getClass().getClassLoader()
                ,bean.getClass().getInterfaces()
                ,new InvocationHandler(){
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String inputArgs = Arrays.toString(args);
                        SimpleDateFormat datetimeFormatter = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss");
                        String startTime = datetimeFormatter. format(new Date());
                        Object methodRes = method.invoke(bean,args);
                        Method runMetod = bean.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes() );
                        String logFile = null;// = method.getAnnotation(LogTransformation.class).logfile();

                        for(Annotation annotation: runMetod.getAnnotations()){
                                LogTransformation s =(LogTransformation) annotation;
                                if(s.logfile().equals("${task4.LogTransformation.defaultfile}"))
                                    logFile = defaultLogFile;
                                else
                                    logFile=s.logfile();
                        }

                        try {
                            FileWriter fileWriter = new FileWriter(logFile, true);
                            String fileLine = startTime + " " +bean.getClass().getName() + " " +
                                    method.getName() +
                                    " input args: " + inputArgs + " " +
                                    " result = " + methodRes + "\n";
                            fileWriter.write(fileLine);
                            fileWriter.close();
                        }catch (IOException e) {
                            throw new RuntimeException( e);
                        }

                        return methodRes;
                    }
        });
    }
}
