package com.roborally.restroborally.repository;
import com.roborally.restroborally.service.PlayerService;
import com.roborally.restroborally.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player,Long> {
}
