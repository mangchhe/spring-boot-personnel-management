package team.okky.personnel_management.manager;

import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Subdivision;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.okky.personnel_management.access.AccessDTO;
import team.okky.personnel_management.config.BeanConfiguration;

import java.net.InetAddress;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MangerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final BeanConfiguration.GeoReader geoReader;

    /**
     * 인사담당관 등록하기
     * @param manager
     */
    @Override
    public void save(Manager manager){
        managerRepository.save(manager);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Manager> findByEmail(String email){return managerRepository.findByEmail(email);}

    @Override
    public void updatePw(Manager manager, String newPw){
        manager.updatePw(newPw);
    }

    /**
     * client ip 위치정보
     * @param ipAddress
     */
    @Override
    @Transactional(readOnly = true)
    public String findCity(InetAddress ipAddress) {
        CityResponse response = geoReader.city(ipAddress);

        Subdivision subdivision = response.getMostSpecificSubdivision();

        return subdivision.getName();
    }

}
