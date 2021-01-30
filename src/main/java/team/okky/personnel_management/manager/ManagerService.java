package team.okky.personnel_management.manager;

import java.util.List;

public interface ManagerService {

    public void save(Manager manager);
    public List<Manager> findByEmail(String email);
    public void updatePw(Manager manager, String newPw);
    
}
