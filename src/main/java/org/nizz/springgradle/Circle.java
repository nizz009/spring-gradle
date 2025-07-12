package org.nizz.springgradle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Locale;

// @Component (generic Stereotype) tells Spring to treat the class as a bean
// Creates a bean with the same name as that of the class but with first letter in lower case
// A disadvantage is that we cannot create more than one instance of the bean like we did in XML file
// Other Stereotypes include @Repository, @Controller, @Service -> tells Spring the category that they belong to
// Functioning of the annotations is similar to that of @Component, but it tells us about the role of the bean (useful
// in Aspect Oriented Programming (AOP))
@Component
// ApplicationEventPublisherAware tells Spring that the class wants access to an ApplicationEventPublisher,
// which it can use to publish events.
public class Circle implements Shape, ApplicationEventPublisherAware, ApplicationContextAware {
    // @Autowired : Used to inject dependencies in the bean - also a bean post processor method
    // It first searches for beans based on type, if multiple beans exist from the same type, it searches by name
    // If bean with the mentioned property name does not exist, it then searches based on @Qualifier (not applicable
    // on @Autowired on constructor injection
    //
    // NOTE: @Autowired annotation would not work if there is autowire tag in the xml file for even a single bean
    // from the same class (circle-xml-autowired has autowire annotation, hence, it had to be commented out so we could
    // use @Autowired annotation even for circle bean)
    //
    // NOTE: @Required annotation (during bean initialization) is executed before @Autowired annotation
    // (during dependency injection) - hence, Spring will throw error for missing properties even if we
    // include @Autowired annotation
    //
    // NOTE: XML configuration runs first then annotation processing starts - hence @Autowired and @Qualifier
    // annotations overwrite the values from XML Configuration
    // Even in @Autowired annotation, the annotation for constructors runs first, then for fields and then for setters
    // Hence, the value set in constructor can be overwritten by that of field and setters (if mentioned as @Autowired)
    // and the value in field injection can be overwritten by that in setters
    //
    // NOTE:  @Autowired is NOT recommended to be used on field injection - does not allow to mark fields as final,
    // it is hard to test (to create Mocks), breaks encapsulation and is harder to debug
    //
    // @Autowired on field injection accesses field via reflection.
    @Autowired
    @Qualifier("circleCenter")
    private Point center;
    @Value("34")
    private int radius;
    @Autowired
    private MessageSource messageSource;
    private ApplicationEventPublisher eventPublisher;
    private ApplicationContext applicationContext;

    // @Autowired is recommended to be used here - allows to mark fields as final (field injection throws error of
    // needing default constructor), if dependencies are missing, it fails fast, and we have a single point of
    // dependency declaration - it is thread-safe by default
    @Autowired
    public Circle(Point center) {
        this.center = center;
    }

    public Point getCenter() {
        return center;
    }

    // @Autowired is NOT recommended to be used here - does not allow to mark fields as final,
    // dependencies are injection after object creation (can also re-inject dependencies)
    // In this case, the object is not initialized until the setters are called - thus in our case, where we have
    // a bean defined in xml (circle-shape) as well, conflict occurs between the Autowired setter and the setter
    // in the xml bean
    //
    // However, it allows partial injection/optional dependencies with @Autowired(required=false)
//    @Autowired
    // JSR Annotation (Java standard annotation - not Spring specific) - Similar to @Autowired but this searches by
    // name first then by type
    // This does not work on collections and works with fields and setters only (not with constructors)
    // Without the name attribute, it will search for the bean with name "center"
//    @Resource(name = "circleCenter")
    public void setCenter(Point center) {
        this.center = center;
    }

    public int getRadius() {
        return radius;
    }

    // @Required : Marks 'center' as required member: Spring throws an error when it's not set during bean
    // initialization
    // We can use @Required only on setters hence they are deprecated and no longer recommended
    // Required check is done via a bean post processor which checks for all Required fields during bean initialization
    // Any bean that is missing a required field will cause an error during initialization
//    @Required
    public void setRadius(int radius) {
        this.radius = radius;
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    // @PostConstruct is part of JSR 250 annotations (Java standard annotations - not specific to Spring)
    // This runs after the initialization of beans (hence, after @Autowired annotation as well)
    @PostConstruct
    public void initializeCircle() {
        System.out.println("Init, PostConstruct, method called for Circle class");
    }

    @PreDestroy
    public void destroyCircle() {
        System.out.println("Destroy, PreDestroy, method called for Circle class");
    }

    @Override
    public void draw() {
        System.out.println("The center of circle is: " + center.getCoordinates() + " and the radius is: " + radius);
        // ApplicationEvent requires the class as the parameter
        ApplicationEvent drawEvent = new DrawEvent(this);
        // ApplicationContext implements ApplicationEventPublisher - so we could also use context.publishEvent - but this is
        // where coding to interfaces is used - we are not concerned with the underneath implementation
//        applicationContext.publishEvent(drawEvent);
        eventPublisher.publishEvent(drawEvent);
    }

    public void getBeanMessage() {
        // Since french locale is provided, Spring first searches in circlemessages_fr, if it does not find in it,
        // it searches in circlemessages, and if the key is absent in both files, it uses the default message
        System.out.println(messageSource.getMessage("circlemotivation",
                new Object[] {radius, center.getX(), center.getY()}, "You get ice cream!", Locale.forLanguageTag("fr")));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
