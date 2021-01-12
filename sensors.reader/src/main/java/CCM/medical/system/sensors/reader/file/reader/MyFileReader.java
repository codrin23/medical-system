package CCM.medical.system.sensors.reader.file.reader;

import CCM.medical.system.sensors.reader.model.MonitoredData;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class MyFileReader {
    private List<MonitoredData> monitoredData;

    public MyFileReader() {
    }

    public MyFileReader(List<MonitoredData> monitoredData) {
        this.monitoredData = monitoredData;
    }

    // get file from classpath, resources folder
    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }

    public void readFile(String fileName) throws IOException {

        File file = this.getFileFromResources(fileName);

        List<MonitoredData> list = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        Random random = new Random();

        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer tok = new StringTokenizer(line);
                String[] readInput = new String[3];
                int n = 0;
                while (tok.hasMoreElements()) {
                    readInput[n] = tok.nextToken("\t");
                    n++;
                }
                //list.add(new MonitoredData(fmt.parseDateTime(readInput[0]).getMillis(), fmt.parseDateTime(readInput[1]).getMillis(), readInput[2], random.nextInt(10)));
                list.add(new MonitoredData(fmt.parseDateTime(readInput[0]).getMillis(), fmt.parseDateTime(readInput[1]).getMillis(), readInput[2], 1));
            }
        }

        this.monitoredData = list;
    }

    public List<MonitoredData> getMonitoredData() {
        return monitoredData;
    }

    public void setMonitoredData(List<MonitoredData> monitoredData) {
        this.monitoredData = monitoredData;
    }
}
