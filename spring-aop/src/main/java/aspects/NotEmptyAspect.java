package aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Aspect
@Slf4j
@Component
public class NotEmptyAspect {

    @Before("@annotation(NotEmpty)")
    public void verifyArguments(JoinPoint joinPoint) {
        log.info(String.format("verifyArguments called before '%s'", joinPoint.getSignature().getName()));

        Object[] arguments = joinPoint.getArgs();

        if (arguments.length == 0) {
            log.info(String.format("'%s' doesn't have any arguments, skipping", joinPoint.getSignature().getName()));
            return;
        }

        for (Object argument : arguments) {
            if (argument == null) {
                throw new IllegalArgumentException("Argument must not be null");
            }

            if (isStringType(argument)) {
                verifyString((String) argument);
            } else if (isCollectionType(argument)) {
                verifyCollection((Collection<?>) argument);
            }
        }

        log.info(String.format("'%s' successfully passed argument verification", joinPoint.getSignature().getName()));
    }

    private boolean isStringType(Object object) {
        return object instanceof String;
    }

    private void verifyString(String string) {
        if (string.isEmpty()) {
            throw new IllegalArgumentException("Provided string must not be empty");
        }
    }

    private boolean isCollectionType(Object object) {
        return object instanceof Collection<?>;
    }

    private void verifyCollection(Collection<?> collection) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("Provided collection must not be empty");
        }
    }

}
