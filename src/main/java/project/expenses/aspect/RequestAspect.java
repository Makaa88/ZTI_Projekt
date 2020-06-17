package project.expenses.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class RequestAspect {

    @Pointcut("execution(* project.expenses.contollers.*.*(..))")
    public void controller()
    {}

    @Before("controller()")
    public void before(JoinPoint joinPoint)
    {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("Requested to " + methodSignature.toShortString());
    }

}
