package org.nizz.springgradle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/*
* ================================
*    APPLICATION CONTEXT AWARE
* ================================
*
* Retrieves the application context used to create the bean so that we can use the context inside the bean
* Use Case: If the container class's bean (Triangle) is marked as singleton but the member object's bean (Point)
* has prototype scope then, in normal scenarios, Point bean for Triangle will be made only once, when Triangle's
* instantiation is done while loading container/creating instances - thus, there is no meaning of marking Point as
* prototype.
*
* With Application Context Aware, we can access context inside Triangle bean and use it to get new bean whenever
* required
*
* ================================
*        BEAN NAME AWARE
* ================================
*
* Gives the name of the bean
*
* ================================
*   INITIALIZING BEAN INTERFACE
* ================================
*
* Tells Spring to call a method when the bean is initialized - afterPropertiesSet is called automatically once the
* properties of the bean are set
*
* ================================
*   DISPOSABLE BEAN INTERFACE
* ================================
*
* Tells Spring to call a method when the bean has to be destroyed (destroy())
*
* NOTE: A disadvantage of InitializingBean and DisposableBean is that they are Spring-specific interfaces - thus,
* we need to modify our entire class based on them (all the beans)
*
* */
public class Triangle implements ApplicationContextAware, BeanNameAware, InitializingBean, DisposableBean, Shape {
    int height;
    String type;
    Point p1;
    Point p2;
    Point p3;
    List<Angle> angles;
    ApplicationContext applicationContext = null;
    String beanName;

    public Triangle() { }

    public Triangle(String type) {
        this.type = type;
    }

    public Triangle(int height) {
        this.height = height;
    }

    public Triangle(String type, int height) {
        this.type = type;
        this.height = height;
    }

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public void setP3(Point p3) {
        this.p3 = p3;
    }

    public void setAngles(List<Angle> angles) {
        this.angles = angles;
    }

    public int getHeight() {
        return this.height;
    }

    public String getType() {
        return this.type;
    }

    public Point getP1(Point p1) {
        return p1;
    }

    public Point getP2(Point p2) {
        return p2;
    }

    public Point getP3(Point p3) {
        return p3;
    }

    public List<Angle> getAngles(List<Angle> angles) {
        return angles;
    }


    public void draw() {
        System.out.println("The type of triangle: " + this.type + " and the height is: " + this.height);
    }

    public void mentionPoints() {
        System.out.println("The co-ordinates of the triangle are: \n" + this.p1.getCoordinates() + "\n"
        + this.p2.getCoordinates() + "\n" + this.p3.getCoordinates());
    }

    public void mentionAngles() {
        System.out.println("The angles of the triangle are: ");
        for(Angle angle : angles) {
            System.out.println(angle.getValue());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
        System.out.println("BeanName: " + beanName);
    }

    public void createDynamicBeans() {
        this.p1 = (Point) applicationContext.getBean("zeroPoint");
        this.p2 = (Point) applicationContext.getBean("pointTwo"); // prototype scope
        this.p3 = (Point) applicationContext.getBean("pointOne");
    }

    public void mentionDynamicPoints() {
        System.out.println("The dynamic co-ordinates of the triangle are:");
        System.out.println("Coordinate: " + p1.getCoordinates() + " HashCode: " + p1.hashCode());
        System.out.println("Coordinate: " + p2.getCoordinates() + " HashCode: " + p2.hashCode());
        System.out.println("Coordinate: " + p3.getCoordinates() + " HashCode: " + p3.hashCode());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean init method called for Triangle class");
    }

    // Runs because we registered a shutdown hook in Main.java
    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean destroy method called for Triangle class for bean: " + beanName);
    }

//    Custom Init and Destroy methods: (for bean-specific methods)
    public void customInit() {
        System.out.println("Custom init method called for Triangle class for bean: " + beanName);
    }

    public void customDestroy() {
        System.out.println("Custom destroy method called for Triangle class for bean: " + beanName + "\n");
    }

    public void customGlobalBeanInit() {
        System.out.println("Custom global init method called for Triangle class for bean: " + beanName);
    }

    public void customGlobalBeanDestroy() {
        System.out.println("Custom global destroy method called for Triangle class for bean: " + beanName + "\n");
    }
}
