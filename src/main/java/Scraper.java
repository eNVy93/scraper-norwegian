import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraper {

    private String userAgent = "Chrome/41.0.2228.0";

    private Document getDocument(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(userAgent).timeout(30*1000).get();
        return doc;
    }

    public List<Document> getDocumentList(List<String> URLs) throws IOException {
        List<Document> documentList = new ArrayList<Document>();
        for (String url : URLs) {
            documentList.add(getDocument(url));
        }
        return documentList;
    }

    public List<String> generateURLforGivenDateRange(String departureAirport, String arrivalAirport, int startDay, int endDay) {
        List<String> listOfURLs = new ArrayList<String>();
        String baseURL = "";


        for (int i = startDay; i <= endDay; i++) {
            String day = String.format("%02d", i);
            baseURL = "https://www.norwegian.com/en/ipc/availability/avaday"
                    + "?D_City=" + departureAirport
                    + "&A_City=" + arrivalAirport
                    + "&TripType=1&D_SelectedDay=" + day
                    + "&D_Day=" + day
                    + "&D_Month=201806"
                    + "&R_SelectedDay=" + day
                    + "&R_Day=" + day
                    + "&R_Month=201806&dFare=129&IncludeTransit=false&AgreementCodeFK=-1&CurrencyCode=EUR&mode=ab";
            listOfURLs.add(baseURL);
        }
        return listOfURLs;
    }


    public String getDate(Document doc){
        Elements layout= doc.select("table.layouttable td table.layouttable td.maincontentbox table.layouttable");
        Elements date = layout.select("td:not(:has(*))");
        return date.text();
    }

    public String getTaxes(Document doc){
        doc.select("#FlightSelectOutboundStandardLowFare0").attr("checked","checked");
        Elements taxes = doc.select("#ctl00_MainContent_ipcAvaDay_upnlResSelection > div:nth-child(1) > div > table > tbody > tr:nth-child(15) > td.rightcell.emphasize");
        System.out.println(taxes.text());
        return "";
    }

    public String getDepartureAirport(Document doc){
        Elements departureAirport = doc.select("#avaday-outbound-result > div > div > div.bodybox > div > table > tbody > tr.oddrow.rowinfo2.lastrow > td.depdest > div");
        return departureAirport.text();
    }

    public String getArrivalAirport(Document doc){
        Elements arrivalAirport = doc.select("#avaday-outbound-result > div > div > div.bodybox > div > table > tbody > tr.oddrow.rowinfo2.lastrow > td.arrdest > div");
        return arrivalAirport.text();
    }

    public String getDepartureTime(Document doc){
        Elements departureTime = doc.select("#avaday-outbound-result > div > div > div.bodybox > div > table > tbody > tr.oddrow.rowinfo1 > td.depdest > div");
        return departureTime.text();
    }
    public String getArrivalTime(Document doc){
        Elements arrivalTime = doc.select("#avaday-outbound-result > div > div > div.bodybox > div > table > tbody > tr.oddrow.rowinfo1 > td.arrdest > div");
        return arrivalTime.text();
    }

    public String getCheapestTicketPrice(Document doc){
//        Elements cheapestFlight = doc.select("#avaday-outbound-result > div > div > div.bodybox > div > table > tbody > tr.oddrow.rowinfo1 > td.fareselect.standardlowfare > div > label");
        Elements cheapestFlight = doc.select("#avaday-outbound-result > div > div > div.bodybox > div > table > tbody > tr.oddrow.rowinfo1 > td.fareselect.standardlowfare > div > label");
        return cheapestFlight.text();
    }

    public FlightData saveFlightData(String date, String departureAirport, String arrivalAirport, String departureTime, String arrivalTime, String cheapestFlight, String taxes){
        return new FlightData(date,departureAirport,arrivalAirport,departureTime,arrivalTime,cheapestFlight,taxes);

    }

    public void txtOutput(List<FlightData> flightList) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("OSLtoRIXdata.txt"));
        for (FlightData flight :
                flightList) {
            writer.write(flight.toString());
        }
        writer.close();

    }

    //TODO make this method return list of elements
    public void getData(Document doc) throws IOException {

//        List<Elements>
        String departureAirport = "OSL";
        String arrivalAirport = "RIX";

        String targetTable = "table.avadaytable";
        Elements tableElements = doc.select(targetTable);
        Elements tableWithoutHeaders = tableElements.select("tbody");
        Elements tableRows = tableWithoutHeaders.select("tr");
        String result = "";
        for (Element direct :
                tableRows) {
            boolean isDirect = direct.text().contains("Direct");
//            System.out.println(direct.text());
            if (isDirect) {
                Elements dataRow = direct.select(".rowinfo1");
//                System.out.println(dataRow.text());
                String departureTime = dataRow.select("td.depdest").text();
                String arrivalTime = dataRow.select("td.arrdest").text();
                String priceRow = dataRow.select("td div label").text();
                String[] seatPrices = priceRow.split("\\s");
                String cheapestSeat = seatPrices[0];
                String taxes = "€21.05";

//                result = result + ", " + departureAirport + "," + departureTime + "," + arrivalAirport + "," + arrivalTime + ",€" + cheapestSeat + ",";

                System.out.println(getDate(doc));
                System.out.println("Departure airport: OSL");
                System.out.println("Departure time: " + departureTime);
                System.out.println("Arrival airport: RIX");
                System.out.println("Arrival time: " + arrivalTime);
                System.out.println("Cheapest seat + taxes: €" + cheapestSeat);
                System.out.println("Taxes: " + taxes);
                System.out.println("Flight type: Direct");
                System.out.println("");
            }
        }


    }

}
