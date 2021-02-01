package team.okky.personnel_management.access;

import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Subdivision;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.okky.personnel_management.config.BeanConfiguration;

import java.net.InetAddress;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final BeanConfiguration.GeoReader geoReader;

    public GeoLocationDTO findCity(InetAddress ipAddress) {
        CityResponse response = geoReader.city(ipAddress);

        Subdivision subdivision = response.getMostSpecificSubdivision();
        City city = response.getCity();

        return new GeoLocationDTO(subdivision.getName(), city.getName());
    }
}
