package by.bockroir.app.components;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import static java.nio.file.StandardCopyOption.*;

@ConditionalOnExpression("${app.backup.enabled}")
@Component
public class ScheduledTasks {

    @Autowired
    Environment env;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd-HH-mm-ss");

    @Scheduled(fixedDelayString = "${app.backup.interval:60}000")
    private void reportCurrentTime() throws IOException {
        String date = dateFormat.format(new Date());
        File source = new File(env.getProperty("app.database.file") + ".mv.db");
        Files.copy(source.toPath(), Paths.get("./data/backup/backup" + date), REPLACE_EXISTING);
        log.info("The time is now {}", date);
    }
}
