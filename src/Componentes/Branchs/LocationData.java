package Componentes.Branchs;
public class LocationData {
    String City;
    String street;

    public LocationData(String City, String street) {
        this.City = City;
        this.street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
    
    
}
