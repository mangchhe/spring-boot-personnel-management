package team.okky.personnel_management.access;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccessService {
    private final AccessRepository accessRepository;

    @Transactional
    public Access save(Access access){return accessRepository.save(access);}

    public Access findOne(Long id){return accessRepository.findOne(id);}

    public List<Access> findAll(){return accessRepository.findAll();}

    @Transactional
    public Access remove(Access access){return accessRepository.remove(access);}

}
