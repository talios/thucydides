package net.thucydides.junit.steps;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.notification.RunListener;

import net.sf.cglib.proxy.Enhancer;
import net.thucydides.core.pages.Pages;

/**
 * Produces an instance of a set of requirement steps for use in the acceptance tests.
 * Requirement steps navigate through pages using a WebDriver driver.
 * 
 * @author johnsmart
 *
 */
public class StepFactory {

    private final Pages pages;
    
    private final List<RunListener> listeners = new ArrayList<RunListener>();
    
    public StepFactory(final Pages pages) {
        this.pages = pages;
    }
    
    public void addListener(final RunListener listener) {
        listeners.add(listener);
    }
    
    private static final Class<?>[] CONSTRUCTOR_ARG_TYPES = {Pages.class};
    
    /**
     * Returns a new ScenarioSteps instance, of the specified type.
     * This is actually a proxy that allows reporting and screenshots to
     * be performed at each step.
     */
    public ScenarioSteps newSteps(final Class<? extends ScenarioSteps> testClass) {
        Enhancer e = new Enhancer();
        e.setSuperclass(testClass);
        StepInterceptor stepInterceptor = new StepInterceptor(testClass, listeners);
        e.setCallback(stepInterceptor);
        
        Object[] arguments = new Object[1];
        arguments[0] = pages;
        return (ScenarioSteps) e.create(CONSTRUCTOR_ARG_TYPES, arguments);
    }
}