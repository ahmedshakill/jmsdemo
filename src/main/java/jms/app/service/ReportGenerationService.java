package jms.app.service;

import com.lowagie.text.DocumentException;
import jms.app.entity.Sales;
import jms.app.repository.SalesRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;


@Service
public class ReportGenerationService {

    private final SalesRepository salesRepository ;
    private final TemplateEngine templateEngine;
    private final DispatcherService dispatcherService;
    public ReportGenerationService(SalesRepository salesRepository, TemplateEngine templateEngine, DispatcherService dispatcherService) {
        this.salesRepository = salesRepository;
        this.templateEngine = templateEngine;
        this.dispatcherService = dispatcherService;
    }

    public void generateReport(){
        List<Sales> salesList =  salesRepository.findAll();

        try {
            Context context = new Context();
            context.setVariable("salesList",salesList);

            String processedHtml = templateEngine.process("pdf/template",context);

            String fileName= "report.pdf";
            String pdfOutputDirectory = "src/main/resources/pdfs";
            var pdfFilePath = Paths.get(pdfOutputDirectory, fileName);

            try(OutputStream outputStream = new FileOutputStream(pdfFilePath.toFile())) {
                ITextRenderer iTextRenderer = new ITextRenderer();
                iTextRenderer.setDocumentFromString(processedHtml);
                iTextRenderer.layout();
                iTextRenderer.createPDF(outputStream,false);
                iTextRenderer.finishPDF();

                // Send message to notify completion
                dispatcherService.sendMessage("ReportGenerated");
            }

        }
        catch (IOException | DocumentException e){
            System.out.println("failed to generate report");
        }
    }

}
