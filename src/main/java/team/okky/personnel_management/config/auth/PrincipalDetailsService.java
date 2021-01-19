package team.okky.personnel_management.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.okky.personnel_management.domain.Manager;
import team.okky.personnel_management.repository.ManagerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final ManagerRepository managerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Manager> managers = managerRepository.findByEmail(username);
        if(managers.isEmpty()){
            throw new UsernameNotFoundException("아이디를 찾을 수 없습니다.");
        }else{
            return new PrincipalDetails(managers.get(0));
        }
    }
}
