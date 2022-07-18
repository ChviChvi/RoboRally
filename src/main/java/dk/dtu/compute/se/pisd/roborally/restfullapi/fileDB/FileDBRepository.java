package dk.dtu.compute.se.pisd.roborally.restfullapi.fileDB;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
}
