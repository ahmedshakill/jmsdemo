package jms.app.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReceiverService {

    private final DummyDataService dummyDataService;

    private final ReportGenerationService reportGenerationService;

    public ReceiverService(DummyDataService dummyDataService, ReportGenerationService reportGenerationService) {
        this.dummyDataService = dummyDataService;
        this.reportGenerationService = reportGenerationService;
    }

    @JmsListener(destination = "${jms.queue}" )
    public void receivedMessage(String message) throws IOException {
        System.out.println("Received message : "+ message);
        if ("GenerateReport".equals(message)) {
            reportGenerationService.generateReport();
            System.out.println("Generating Report");
        } else if ("ReportGenerated".equals(message)) {
            //TODO : Notify User
        }

    }

}
