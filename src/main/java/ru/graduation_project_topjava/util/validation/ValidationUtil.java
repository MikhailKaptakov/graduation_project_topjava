package ru.graduation_project_topjava.util.validation;

import org.slf4j.Logger;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.graduation_project_topjava.HasId;
import ru.graduation_project_topjava.model.AbstractBaseEntity;
import ru.graduation_project_topjava.util.exception.ErrorType;
import ru.graduation_project_topjava.util.exception.IllegalRequestDataException;
import ru.graduation_project_topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, long id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, long id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId entity) {
        if (!entity.itsNew()) {
            throw new IllegalRequestDataException(entity + " must be new (id=null)");
        }
    }

    public static void checkHaveId(AbstractBaseEntity entity) {
        if (entity.itsNew()) {
            throw new IllegalRequestDataException(entity + " haven't id");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity entity, long id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (entity.itsNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalRequestDataException(entity + " must be with id=" + id);
        }
    }

    //  https://stackoverflow.com/a/65442410/548473
    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }

    public static Throwable logAndGetRootCause(Logger log, HttpServletRequest req, Exception e, boolean logStackTrace,
                                               ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logStackTrace) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
        return rootCause;
    }

}
