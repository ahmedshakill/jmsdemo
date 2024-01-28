package jms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class ReceiverService {

//    Logger log = LoggerFactory.getLogger(ReceiverService.class);

    @Autowired
    DummyDataService dummyDataService;

    @Autowired
    ReportGenerationService reportGenerationService;

    @JmsListener(destination = "${jms.queue}" , containerFactory = "myFactory")
    public void receivedMessage(String message){
        System.out.println("Received message : "+ message);
        if("GenerateData".equals(message)){
            dummyDataService.generateAndStoreDummyData();
            dummyDataService.generateAndStoreDummySales();
            System.out.println("Generating Dummy Data");
        } else if ("GenerateReport".equals(message)) {
            reportGenerationService.generateReport();
            System.out.println("Generating Report");
        }
    }

}
