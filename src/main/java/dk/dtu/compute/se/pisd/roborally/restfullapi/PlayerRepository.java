package dk.dtu.compute.se.pisd.roborally.restfullapi;

import dk.dtu.compute.se.pisd.roborally.restfullapi.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    // SELECT * FROM STUDENT WHERE email = ?
    @Query("Select s FROM Player  s WHERE s.id = ?1")
    Optional<Player> findPlayersById(Long id);
}
