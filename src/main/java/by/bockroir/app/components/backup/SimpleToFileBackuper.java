package by.bockroir.app.components.backup;

import by.bockroir.app.components.ScheduledTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Component("backupToFile")
public class SimpleToFileBackuper implements Backuper {

    Environment env;

    private static final Logger log = LoggerFactory.getLogger(SimpleToFileBackuper.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    @Autowired
    public SimpleToFileBackuper(Environment env) {
        this.env = env;
        log.info("Backuping to file initialized");
        log.info("Backup interval: {} minutes", Integer.valueOf(env.getProperty("app.backup.interval")) /1000/60);
    }

    @Override
    public void makeBackup() {
        String date = dateFormat.format(new Date());
        File source = new File(env.getProperty("app.database.file") + ".mv.db");
        try {
            Files.copy(source.toPath(), Paths.get("./data/backup/backup" + date), REPLACE_EXISTING);
            log.info("Created backup with name 'backup{}'", date);
        } catch (IOException e) {
            log.error("Can't make backup");
            e.printStackTrace();
        }
    }
}
