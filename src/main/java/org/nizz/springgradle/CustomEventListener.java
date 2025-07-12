package org.nizz.springgradle;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// Listens to every event in the application
@Component
public class CustomEventListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        System.out.println("Listened to event: " + applicationEvent.toString() + "\n");
    }
}
