package ua.dp.dmma.bird.server.service.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Singleton;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import ua.dp.dmma.bird.server.model.BirdData;
import ua.dp.dmma.bird.server.model.BirdSightingData;

/**
 * Stores data to memory and periodically makes copy to the disk
 * @author dmma
 */
@Service
@Singleton
public class StorageService implements InitializingBean
{
    public static Path storageLocationFolder;
    public static final String BIRD_STORAGE_FILE_NAME = "bird.data";
    public static final String BIRD_SIGHTING_STORAGE_FILE_NAME = "sighting.data";

    private Set<BirdData> birdData = new ConcurrentSkipListSet<>();
    private List<BirdSightingData> birdSightingData = new CopyOnWriteArrayList<>();
    private Path birdPath;
    private Path birdSightingPath;
    private boolean isDaemonWriterInitialized;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        birdPath = createStorageFile(BIRD_STORAGE_FILE_NAME);
        birdSightingPath = createStorageFile(BIRD_SIGHTING_STORAGE_FILE_NAME);

        loadDataFromTheDisk(birdPath, birdData);
        loadDataFromTheDisk(birdSightingPath, birdSightingData);
    }

    public boolean addBird(BirdData birdData)
    {
        synchronized (this)
        {
            if (!isDaemonWriterInitialized)
            {
                initializeDaemonWriter();
                isDaemonWriterInitialized = true;
            }
        }
        return this.birdData.add(birdData);
    }

    public boolean removeBird(String name)
    {
        return this.birdData.remove(new BirdData(name));
    }

    public List<BirdData> getBirdList()
    {
        return new ArrayList<>(birdData);
    }

    public void addBirdSighting(BirdSightingData birdSightingData)
    {
        synchronized (this)
        {
            if (!isDaemonWriterInitialized)
            {
                initializeDaemonWriter();
                isDaemonWriterInitialized = true;
            }
        }
        this.birdSightingData.add(birdSightingData);
    }

    public List<BirdSightingData> getBirdSightingList()
    {
        return new ArrayList<>(birdSightingData);
    }

    public synchronized void storeDataToTheDisk()
    {
        try
        {
            storeDataToTheDisk(birdPath, birdData);
            storeDataToTheDisk(birdSightingPath, birdSightingData);
        }
        catch (IOException e)
        {
            Logger.getLogger(StorageService.class.getName()).log(Level.WARNING, "Error storing data to thr disk", e);
        }
    }

    private Path createStorageFile(String fileName) throws IOException
    {
        Path path = Paths.get(storageLocationFolder + File.separator + fileName);
        if (!Files.exists(path))
        {
            return Files.createFile(path);
        }
        return path;
    }

    private <E> void storeDataToTheDisk(Path path, Collection<E> collection) throws IOException
    {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path)))
        {
            outputStream.writeObject(collection);
        }
    }

    @SuppressWarnings("unchecked")
    private <E> void loadDataFromTheDisk(Path path, Collection<E> collection) throws IOException, ClassNotFoundException
    {
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile())))
        {
            if (br.readLine() == null)
            {
                Logger.getLogger(StorageService.class.getName()).log(Level.INFO, path + " is empty");
                return;
            }
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path)))
        {
            Collection<E> storedData = (Collection<E>) inputStream.readObject();
            collection.addAll(storedData);
        }
    }

    private void initializeDaemonWriter()
    {
        Thread thread = new Thread(() ->
        {
            try
            {
                while (true)
                {
                    storeDataToTheDisk();
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5L));
                }
            }
            catch (InterruptedException e)
            {
                Logger.getLogger(StorageService.class.getName()).log(Level.INFO, "Daemon writer exception", e);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
