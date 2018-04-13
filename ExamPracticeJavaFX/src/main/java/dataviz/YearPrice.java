package dataviz;

import javax.persistence.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
public class YearPrice
{
    @Id
    @SequenceGenerator(name="year_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "year_id")
    private long id;

    private String year;

    private List<String> prices = new ArrayList<>();

    public YearPrice(String year, List<String> prices)
    {
        this.year = year;
        this.prices = prices;
    }

    public YearPrice(){};

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public List<String> getPrices()
    {
        return prices;
    }

    public void setPrices(List<String> prices)
    {
        this.prices = prices;
    }
}
