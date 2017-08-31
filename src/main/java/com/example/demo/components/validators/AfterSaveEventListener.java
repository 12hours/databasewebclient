package com.example.demo.components.validators;

import org.springframework.context.event.EventListener;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

@Component
public class AfterSaveEventListener extends AbstractRepositoryEventListener {
    @Override
    protected void onAfterCreate(Object entity) {
        System.out.println("CREATE EVENT");
        System.out.println(entity.toString());
    }

    @Override
    protected void onAfterSave(Object entity) {
        System.out.println("SAVING EVENT");
        System.out.println(entity.toString());
    }

    @Override
    protected void onAfterLinkSave(Object parent, Object linked) {
        System.out.println("LINK EVENT");
        System.out.println(parent.toString());
        System.out.println(linked.toString());
    }

    @Override
    protected void onAfterDelete(Object entity) {
        System.out.println("DELETE EVENT");
        System.out.println(entity.toString());
    }
}
