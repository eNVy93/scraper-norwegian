import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        String departureAirport = "OSL";
        String arrivalAirport = "RIX";
        List<FlightData> flightDataList = new ArrayList<FlightData>();
        int startDay = 1;
        int endDay = 30;
        Scraper scraper = new Scraper();

        List<String> URLs = scraper.generateURLforGivenDateRange(departureAirport, arrivalAirport, startDay,endDay);
        List<Document> documentList = scraper.getDocumentList(URLs);
        for (Document doc :
                documentList) {

            String depAir = scraper.getDepartureAirport(doc);
            String arrAir = scraper.getArrivalAirport(doc);
            String depTime = scraper.getDepartureTime(doc);
            String arrTime = scraper.getArrivalTime(doc);
            String price = scraper.getCheapestTicketPrice(doc);
            String date = scraper.getDate(doc);

            //Couldnt retrieve taxes..
            flightDataList.add(scraper.saveFlightData(date,depAir,arrAir,depTime,arrTime,price,"21.05"));

        }
        System.out.println(flightDataList.size());
        for (FlightData flight :
                flightDataList) {
            System.out.println(flight);
        }
        scraper.txtOutput(flightDataList);

    }

}
