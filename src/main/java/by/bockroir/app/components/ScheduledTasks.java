package by.bockroir.app.components;

import by.bockroir.app.components.backup.Backuper;
import by.bockroir.app.components.backup.SimpleToFileBackuper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@ConditionalOnExpression("${app.backup.enabled}")
@Component
public class ScheduledTasks {

    private Backuper backuper;

    @Autowired
    public ScheduledTasks(@Qualifier("backupToFile") Backuper backuper) {
        this.backuper = backuper;
    }

    @Scheduled(fixedDelayString = "${app.backup.interval}")
    private void makeBackup() throws IOException {
        backuper.makeBackup();
    }
}
