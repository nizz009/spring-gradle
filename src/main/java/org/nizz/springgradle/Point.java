package org.nizz.springgradle;

import org.springframework.beans.factory.BeanNameAware;

public class Point implements BeanNameAware {
    private int x;
    private int y;
    String beanName;

    public Point() {}

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getCoordinates() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public void customGlobalBeanInit() {
        System.out.println("Custom global init method called for Point class for bean: " + beanName);
    }

    public void customGlobalBeanDestroy() {
        System.out.println("Custom global destroy method called for Point class for bean: " + beanName + "\n");
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
