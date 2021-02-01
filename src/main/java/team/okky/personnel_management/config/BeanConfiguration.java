package team.okky.personnel_management.config;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Configuration
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper;
    }

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }

    @Component
    public class GeoReader{
        private static final String DATABASE_CITY_PATH = "C:\\Users\\user\\study\\GeoLite2-Country.mmdb";
        private DatabaseReader reader;

        public GeoReader() throws IOException {
            File dbFile = new File(DATABASE_CITY_PATH);
            reader = new DatabaseReader.Builder(dbFile).build();
        }

        public CityResponse city(InetAddress ipAddress) {
            CityResponse response = null;

            try {
                response = reader.city(ipAddress);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GeoIp2Exception e) {
                e.printStackTrace();
            }

            return response;
        }
    }
}
