package CCM.medical.system.sensors.reader;

import CCM.medical.system.sensors.reader.file.reader.MyFileReader;
import CCM.medical.system.sensors.reader.model.MonitoredData;
import CCM.medical.system.sensors.reader.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application  implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Autowired
	Producer producer;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sending message...");
		MyFileReader myFileReader = new MyFileReader();
		myFileReader.readFile("Activities.txt");

		for (MonitoredData monitoredData: myFileReader.getMonitoredData()) {
			System.out.println("Sending " + monitoredData.toString());
			producer.produce(monitoredData);
			Thread.sleep(10000);
		}
	}
}