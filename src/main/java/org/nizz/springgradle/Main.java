package org.nizz.springgradle;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

public class Main {
    public static void main(String[] args) {
        /// Basic object instantiation
//        Triangle triangle = new Triangle("Equilateral", 15);

        /// Instantiate using XML Bean Factory
//        BeanFactory factory = new XmlBeanFactory(new FileSystemResource("spring.xml"));

        /// Instantiate using Application Context - Similar to Bean Factory but with more functionalities
        /// By default, when we initialize the context (before getBean()), the application context reads the xml
        /// file and creates all the beans and when we make the getBean() call, it hands over the created bean
//        ApplicationContext context = new ClassPathXmlApplicationContext("springApplicationContext.xml");

        /// AbstractApplicationContext helps to register a shutdown hook that tells Spring to close the application
        /// context once the Main method ends - we need to do this only for SE applications, for web applications,
        /// Spring will know when to close the context
        AbstractApplicationContext context = new
                ClassPathXmlApplicationContext("springApplicationContext.xml");
        // registers a shutdown hook which runs and closes the context once the program ends : specialized feature
        // provided by the Application Context which is not present in the Bean Factory
        context.registerShutdownHook();

        Triangle triangleConstructor = (Triangle) context.getBean("triangle-constructor");
        Triangle triangle = (Triangle) context.getBean("triangle");
        Triangle triangleonlyHeight = (Triangle) context.getBean("triangle-only-height-constructor");
        Triangle triangleonlyType = (Triangle) context.getBean("triangle-only-type-constructor");
        Triangle triangleUnordered = (Triangle) context.getBean("triangle-unordered-constructor");
        Triangle triangleObjectConstructor = (Triangle) context.getBean("triangle-object-constructor");
        Triangle triangleObjectInitializer = (Triangle) context.getBean("triangle-object-initializer");
        Triangle triangleAlias = (Triangle) context.getBean("triangle-alias");
        Triangle triangleCollectionInitializer = (Triangle) context.getBean("triangle-collection-initializer");
        Triangle triangleAutowired = (Triangle) context.getBean("triangle-autowired");
        Triangle trianglePrototypeMember = (Triangle) context.getBean("triangle-autowired");
        Triangle trianglePointInherited = (Triangle) context.getBean("triangle-point-inherited");
        Triangle triangleAngleInherited = (Triangle) context.getBean("triangle-angle-inherited");

        triangleConstructor.draw();
        triangle.draw();
        triangleonlyHeight.draw();
        triangleonlyType.draw();
        triangleUnordered.draw();
        triangleObjectConstructor.mentionPoints();
        triangleObjectInitializer.mentionPoints();
        triangleAlias.draw();
        triangleCollectionInitializer.mentionAngles();
        triangleAutowired.mentionPoints();

        // Executing twice to show that a new instance is created for the prototype member while the same instance
        // is used for the singleton members
        trianglePrototypeMember.createDynamicBeans();
        trianglePrototypeMember.mentionDynamicPoints();
        trianglePrototypeMember.createDynamicBeans();
        trianglePrototypeMember.mentionDynamicPoints();

        trianglePointInherited.mentionPoints();
        triangleAngleInherited.mentionAngles();

        /*
         * NOTE:
         * ======================
         *   BASIC BEAN SCOPES
         * ======================
         *
         * Spring has two basic bean scopes:
         * 1. Singleton : Single instance is created for each bean type, this leads to the default behavior of
         * spring to create the beans when the application context is initialized, without waiting for getBean()
         * call - thus, if two objects use the same bean id, they get the same instance of the class
         * This is different from Java Singleton because in Java, singleton creates one instance "globally" but
         * in Spring, a single instance is created only in the spring container - thus, if a JVM runs multiple
         * containers, then it creates multiple instances, one in each container
         *
         * 2. Prototype : Creates new instance/bean with every request (example: getBean()) or reference
         * (example: ref attribute in the application context's xml) - In this case, spring does not create
         * bean during application context initialization and waits until a request or reference is made for the
         * bean
         *
         *
         * =================================
         *   WEB AWARE CONTEXT BEAN SCOPES
         * =================================
         *
         * Since Spring is used for web applications, Spring can track new requests, sessions, etc.
         * Hence, there are scopes used for web applications as well:
         *
         * 1. Request : Creates a new bean per servlet request - if a bean is used multiple times in same request
         * scope, the same bean is used
         *
         * 2. Session : Creates a new bean per session - if same user makes multiple calls in a single session,
         * the same bean is used, but if a different user in different session makes the call, a new bean is
         * created
         *
         * 3. Global Session : Creates a new bean per global HTTP session (portlet context) - TODO: study more
         *
         *
         * */


        /*
        * ==========================
        *    CODING TO INTERFACE
        * ==========================
        *
        * Implementing interfaces so that the Main method is not concerned with the exact type of object
        * Example: Triangle and Circle both implement Shape interface, just mentioning shape.draw() will call their
        * respective draw methods without being concerned about whether it is of Triangle type or Circle type
        *
        * Using interfaces is good practice because if we need to update the implementation, we do not need to update
        * the main class using those implementations
        *
        * */

        System.out.println("------ CODING TO INTERFACES ------");
        Shape circleShape = (Shape) context.getBean("circle-shape");
        Shape triangleShape = (Shape) context.getBean("triangle");

        // Both are of type Shape but their draw methods belong to their respective implementations
        circleShape.draw();
        triangleShape.draw();

        System.out.println("------ CIRCLE CLASS USING ANNOTATIONS ------");
        Circle circleAnnotated = (Circle) context.getBean("circle");
        circleAnnotated.draw();


        System.out.println("------ APPLICATION CONTEXT MESSAGE SOURCE ------");
        // @param code: the code/key of the message that we want to get from the message source
        // @param args: list of arguments we want to pass to the message (check example in Circle class)
        // @param defaultMessage: the message to be displayed when the given key is not found
        // @param locale: if there are multiple message files in different languages, we can access them using locale
        // Example: messages_EN, etc.
        //
        // NOTE: If there are multiple messages with the same key, Spring will pick the first key that it finds,
        // based on the order in which the files are mentioned in the XML file - hence order of files matter
        System.out.println(context.getMessage("motivation", null,
                "You can do this (for french fries)!", null));
        circleAnnotated.getBeanMessage();


        /*
         * ==========================
         *    EVENT HANDLING
         * ==========================
         *
         * 3 core components of any event handling (not just in Spring):
         * 1. Event publisher
         * 2. Event listener
         * 3. Event itself
         *
         * Check "draw" method of Circle class for event handling example
         * */

    }
}