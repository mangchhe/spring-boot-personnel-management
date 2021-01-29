package team.okky.personnel_management.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MangerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;

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

}
