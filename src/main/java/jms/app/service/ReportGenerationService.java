package jms.app.service;

import jms.app.repository.SalesRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;


@Service
public class ReportGenerationService {

    private final SalesRepository salesRepository ;

    public ReportGenerationService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public void generateReport() throws IOException {
        System.out.println("Report Generation Service");

       String report =  salesRepository.findAll().toString();

        PDDocument document = new PDDocument();
        PDPage page = document.getPage(1);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        //Begin the Content stream
        contentStream.beginText();

        //Setting the font to the Content stream
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

        //Setting the position for the line
        contentStream.newLineAtOffset(25, 500);

        String text = "This is the sample document and we are adding content to it.";

        //Adding text in the form of string
        contentStream.showText(report);

        //Ending the content stream
        contentStream.endText();

        System.out.println("Content added");

        //Closing the content stream
        contentStream.close();
        document.save("~/Documents");
        document.close();

        //TODO : Save report into pdf, handle paging


    }
}
