package com.khmelyuk.dib;

import com.khmelyuk.dib.action.ApiAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * The holder of api actions with all related metadata.
 *
 * @author Ruslan Khmelyuk
 */
public class ApiActionHolder implements ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(ApiActionHolder.class);

    /**
     * The map of actions, available to get each action quickly by it's name.
     */
    private final Map<ActionName, ApiAction> actions = new HashMap<ActionName, ApiAction>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            // ---- Scan for API request methods
            new ApiActionDetector(applicationContext).scanAndPopulate(this);
        }
        catch (Exception e) {
            throw new BeanInitializationException("Error while initialisation of api framework", e);
        }
    }

    /**
     * Adds a new action to the holder.
     *
     * @param action the action itself.
     * @throws DuplicateActionException error while adding duplicate action.
     */
    public void add(ApiAction action) throws DuplicateActionException {
        final ActionName name = action.getName();
        if (actions.containsKey(name)) {
            throw new DuplicateActionException(name);
        }

        actions.put(name, action);

        log.info("Added API Action: {}", action);
    }

    /**
     * Gets the action by it's name.
     *
     * @param name the action name.
     * @return the found action or null.
     */
    public ApiAction getAction(ActionName name) {
        return actions.get(name);
    }
}
