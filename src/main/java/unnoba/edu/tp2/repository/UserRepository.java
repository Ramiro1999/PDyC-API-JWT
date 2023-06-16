package unnoba.edu.tp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unnoba.edu.tp2.Model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public User findFirstByEmail(String email);

}
