public class FlightData {

    private String date;
    private String departureAirport;
    private String arrivalAirport;
    private String departureTime;
    private String arrivalTime;
    private String cheapestTicket;
    private String taxes;


    public FlightData(String date, String departureAirport, String arrivalAirport, String departureTime, String arrivalTime, String cheapestTicket, String taxes) {
        this.date = date;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.cheapestTicket = cheapestTicket;
        this.taxes = taxes;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCheapestTicket() {
        return cheapestTicket;
    }

    public void setCheapestTicket(String cheapestTicket) {
        this.cheapestTicket = cheapestTicket;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    @Override
    public String toString() {
        return "\nFlight: " +
                "date='" + date + '\'' +
                ", departureAirport='" + departureAirport + '\'' +
                ", arrivalAirport='" + arrivalAirport + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", cheapestTicket='" + cheapestTicket + '\'' +
                ", taxes='" + taxes + '\'' +
                '}';
    }
}
