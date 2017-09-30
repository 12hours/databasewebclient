package by.bockroir.app.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseOpsEventListener extends AbstractRepositoryEventListener {

    private static final Logger log = LoggerFactory.getLogger(DatabaseOpsEventListener.class);

    @Override
    protected void onAfterCreate(Object entity) {
        log.debug("CREATE: " + entity.toString());
    }

    @Override
    protected void onAfterSave(Object entity) {
        log.debug("SAVE: " + entity.toString());
    }

    @Override
    protected void onAfterLinkSave(Object parent, Object linked) {
        log.debug("LINK: [parent]: " + parent.toString() + " [linked]: " + linked.toString());
    }

    @Override
    protected void onAfterDelete(Object entity) {
        log.debug("DELETE: " + entity.toString());
    }
}
